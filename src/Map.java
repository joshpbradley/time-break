import org.jsfml.system.Vector2f;
import java.util.Random;

public abstract class Map
{
	protected MapTile[][] mapTiles; //stores all of the tiles on the map [x][y]. [40][31] once constructed
	protected int mapX; //stores the x dimension of the map
	protected int mapY; //stores the y dimension of the map
	protected MapTile[] weaponSpawnLocations = new MapTile[5]; //stores all of the tiles where weapons are currently present. [5] once constructed
	protected MapTile[] itemSpawnLocations = new MapTile[5]; // Stores all of the tiles where weapons are currently present. [5] once constructed.
	protected final int TILE_SIZE = 45;
	protected final int LEFT_INDENT = 225;
	public static final Random random = new Random();

	public Map() { }

	private Item[] itemSpawnables = new Item[]
	{
		new BodyArmour(),
		new BouncyGrenade(),
		new BulletGrenade(),
		new ConcussionGrenade(),
		new DroneDrop(),
		new GlueGrenade(),
		new Jetpack(),
		new JumpGrenade(),
		new Landmine(),
		new Taser()
	};

	protected MapSection[] generateMapSections()
	{
		Random r = new Random();
		int numY = r.nextInt(2) + 3;
		int numX = r.nextInt(2) + 3;
		int[] yLines = new int[numY];
		int[] xLines = new int[numX];
		//Get points on y axis
		yLines[0] = 0;
		for (int i = 1; i < numY - 1; i++)
		{
			yLines[i] = (int)(((double)i / (double)(numY - 1)) * mapY);
		}
		yLines[numY - 1] = mapY;
		//Get points on x axis
		xLines[0] = 0;
		for (int i = 1; i < numX - 1; i++)
		{
			xLines[i] = (int)(((double)i / (double)(numX - 1)) * mapX);
		}
		xLines[numX - 1] = mapX;
		//Get Sections
		MapSection[] sections = new MapSection[(numX - 1) * (numY - 1)];
		for (int y = 0; y < numY - 1; y++)
		{
			for (int x = 0; x < numX - 1; x++)
			{
				//Shifting the boundary lines in alternating rows and columns
				if (y % 2 == 0 && x > 0)
				{
					int xBound = xLines[x] + r.nextInt(4) - 8;
					while (xBound < 0)
					{
						xBound = xLines[x] + r.nextInt(4) - 8;
					}
					sections[y * (numX - 1) + x] = new MapSection(xBound, xLines[x + 1], yLines[y], yLines[y + 1]);
				}
				else if (x % 2 ==0 && y > 0)
				{
					int yBound = yLines[y] + r.nextInt(4) - 8;
					while (yBound < 0)
					{
						yBound = yLines[y] + r.nextInt(4) - 8;
					}
					sections[y * (numX - 1) + x] = new MapSection(xLines[x], xLines[x + 1], yBound, yLines[y + 1]);
				}
				else
				{
					sections[y * (numX - 1) + x] = new MapSection(xLines[x], xLines[x + 1], yLines[y], yLines[y + 1]);
				}
			}
		}
		return sections;
	}

	protected void generateCoverCluster(MapSection s)
	{
		int startX = s.getBounds()[0];
		int endX = s.getBounds()[1];
		int startY = s.getBounds()[2];
		int endY = s.getBounds()[3];
		int[] mountainCentre = getCentrePoint(startX, endX, startY, endY);
		int maxCover = (endX - startX) * (endY - startY) / 10;
		for (int i = 1; i <= maxCover; i++)
		{
			boolean placedTile = false;
			int xOffset = 0;
			int yOffset = 0;
			int loopNum = 0;
			while (!placedTile && loopNum < 100)
			{
				Random r = new Random();
				switch (r.nextInt(4))
				{
					case 0:
						yOffset--;
						break;
					case 1:
						xOffset++;
						break;
					case 2:
						yOffset++;
						break;
					case 3:
						xOffset--;
						break;
				}
				if ((mountainCentre[0] + xOffset >= endX || mountainCentre[0] + xOffset <= startX) || (mountainCentre[1] + yOffset >= endY || mountainCentre[1] + yOffset <= startY)) {
					xOffset = 0;
					yOffset = 0;
				}
				else
				{
					if (mapTiles[mountainCentre[0] + xOffset][mountainCentre[1] + yOffset].getType() == 'b' && (((Math.abs(xOffset) + Math.abs(yOffset)) / 2.0 > (0.25 * ((endX - startX) + (endY - startY)) / 2)) || i < maxCover * 0.75) )
					{
						mapTiles[mountainCentre[0] + xOffset][mountainCentre[1] + yOffset] = new CollisionTile(this, 'c', (mountainCentre[0] + xOffset) * TILE_SIZE + LEFT_INDENT, (mountainCentre[1] + yOffset) * TILE_SIZE);
						placedTile = true;
					}
				}
				loopNum++;
			}
		}
	}

	int[] getCentrePoint(int startX, int endX, int startY, int endY)
	{
		//picks centre point of the area given
		return new int[]{(startX + endX) / 2, (startY + endY) / 2};
	}

	protected void replaceWeaponWithRandomWeapon(MapTile oldWeapon)
	{
		oldWeapon.setHeldItem(Weapon.getRandomWeapon());
	}

	protected MapTile spawnNewWeapon()
	{
		int[] freeTile = findRandomFreeTile();
		int x = freeTile[0];
		int y = freeTile[1];
		mapTiles[x][y].setHeldItem(Weapon.getRandomWeapon());
		return mapTiles[x][y];
	}

	protected MapTile spawnNewItem()
	{
		int[] freeTile = findRandomFreeTile();
		int x = freeTile[0];
		int y = freeTile[1];
		mapTiles[x][y].setHeldItem(itemSpawnables[random.nextInt(itemSpawnables.length)].clone());
		return mapTiles[x][y];
	}

	/**
	 *	<p>Causes the map to begin decaying. This means usable play space begins to decrease.</p>
	 */
	public abstract void decay();

	protected int[] findRandomFreeTile()
	{
		Random r = new Random();
		int x = r.nextInt(mapX);
		int y = r.nextInt(mapY);
		while (mapTiles[x][y].getType() == 'c'
				||  mapTiles[x][y].getType() == 'R'
				||  mapTiles[x][y].getType() == 'G'
				||  mapTiles[x][y].getType() == 'B'
				||  mapTiles[x][y].getType() == 'Y'
				||  mapTiles[x][y].getType() == 'I')
		{
			x = r.nextInt(mapX);
			y = r.nextInt(mapY);
		}
		return new int[]{x, y};
	}

	void placeRandomCover()
	{
		int[] freeTile = findRandomFreeTile();
		int x = freeTile[0];
		int y = freeTile[1];
		mapTiles[x][y] = new CollisionTile(this, 'c', x * TILE_SIZE + LEFT_INDENT, y * TILE_SIZE);
	}

	/**
	 *	<p>Returns the two dimensional array used to store all of the map tiles.</p>
	 *  @return The grid of map tiles accessible in the format [x][y].
	 */
	MapTile[][] getMapTiles()
	{
		return this.mapTiles;
	}

	public MapTile getMapTileAtPosition(Vector2f position)
	{
		float mapTileWidth = mapTiles[0][0].getGlobalBounds().width;
		float mapTileHeight = mapTiles[0][0].getGlobalBounds().height;

		int mapTileYIndex = (int)(position.y/mapTileHeight);
		int mapTileXIndex = (int)(((position.x - LEFT_INDENT))/mapTileWidth);

		if(mapTileYIndex < 0)
		{
			mapTileYIndex = 0;
		}
		if(mapTileXIndex < 0)
		{
			mapTileXIndex = 0;
		}
		if(mapTileYIndex >= mapTiles[0].length)
		{
			mapTileYIndex = mapTiles[0].length - 1;
		}
		if(mapTileXIndex >= mapTiles.length)
		{
			mapTileXIndex = mapTiles.length - 1;
		}

		return mapTiles[mapTileXIndex][mapTileYIndex];
	}

	public MapTile[][] getMapTileAreaSurroundingPosition(int areaLength, Vector2f position)
	{
		float tileWidth = mapTiles[0][0].getGlobalBounds().width;
		float tileHeight = mapTiles[0][0].getGlobalBounds().height;

		MapTile[][] possibleCollisionTiles = new MapTile[areaLength][areaLength];

		for(int x = 0; x < areaLength; ++x)
		{
			for(int y = 0; y < areaLength; ++y)
			{
				possibleCollisionTiles[x][y] = getMapTileAtPosition(
					new Vector2f((position.x - (tileWidth * (areaLength - 1)/2) + (x * tileWidth)),
						(position.y - (tileWidth * (areaLength - 1)/2)) + (y * tileHeight)));
			}
		}

		return possibleCollisionTiles;
	}

	/**
	 * Sets all of the squares surrounding the player on the map initialisation to be blank, to allow no collisions
	 * on setup and for immediate freedom of movement from the player.
	 *
	 * @param p The player that the blank squares shall be based around
	 */
	public void setPlayerAreaBlank(Player p)
	{
		MapTile[][] surroundingMapTiles = getMapTileAreaSurroundingPosition(3, p.getGlobalCentrePoint());

		for(int x = 0; x < 3; ++x)
		{
			for(int y = 0; y < 3; ++y)
			{
				mapTiles[surroundingMapTiles[x][y].getXPosition()][surroundingMapTiles[x][y].getYPosition()] =
						new MapTile(this, (int)surroundingMapTiles[x][y].getGlobalBounds().left, (int)surroundingMapTiles[x][y].getGlobalBounds().top);
			}
		}
	}

	public int[][] spawnPlayers(Player player1, Player player2)
	{
		int tempX = mapX / 4 * TILE_SIZE + LEFT_INDENT + (int)(player1.getGlobalBounds().width/2);
		int tempY = mapY / 2 * TILE_SIZE;
		while (this.getMapTiles()[(tempX - LEFT_INDENT) / TILE_SIZE ][(tempY) / TILE_SIZE ].getType() != 'b')
		{
			if (tempY / TILE_SIZE >= 17)
			{
				tempX += TILE_SIZE;
				tempY = 2 * TILE_SIZE;
			}
			else
			{
				tempY += TILE_SIZE;
			}
		}
		int[] player1StartPos = {tempX,tempY};
		player1.setPosition(tempX,tempY);

		tempX = mapX * 3 / 4 * TILE_SIZE + LEFT_INDENT + (int)(player2.getGlobalBounds().width/2);
		tempY = mapY / 2 * TILE_SIZE;
		while (this.getMapTiles()[(tempX- LEFT_INDENT) / TILE_SIZE ][(tempY) / TILE_SIZE ].getType() != 'b')
		{
			if(tempY / TILE_SIZE <= 2)
			{
				tempX -= TILE_SIZE;
				tempY = 17 * TILE_SIZE;
			}
			else
			{
				tempY -= TILE_SIZE;
			}
		}
		int[] player2StartPos = {tempX,tempY};
		player2.setPosition(tempX,tempY);

		// Sets the area surrounding a player to be blank tiles upon loading the map.
		this.setPlayerAreaBlank(player1);
		this.setPlayerAreaBlank(player2);

		return new int[][]{player1StartPos, player2StartPos};
	}


	public void spawnInitialPickups()
	{
		for (int i = 0; i < 5; i++)
		{
			weaponSpawnLocations[i] = spawnNewWeapon();
			itemSpawnLocations[i] = spawnNewItem();
		}
	}

	/**
	 * Checks whether there is an vacancy for a new item or weapon,
	 * and adds a new pickup for a weapon or item if there is one.
	 */
	public void checkPickups()
	{
		for(int i = 0; i < 5; i++)
		{
			if(weaponSpawnLocations[i].getHeldItem() == null)
			{
				weaponSpawnLocations[i] = spawnNewWeapon();
			}

			if(itemSpawnLocations[i].getHeldItem() == null)
			{
				itemSpawnLocations[i] = spawnNewItem();
			}
		}
	}

	public MapTile[] getItemSpawnLocations()
	{
		return itemSpawnLocations;
	}

	public MapTile[] getWeaponSpawnLocations()
	{
		return weaponSpawnLocations;
	}
}