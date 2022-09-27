import java.util.Random;

public class CrystalMap extends Map
{
    private Player player1;
    private Player player2;

    public CrystalMap(int x, int y, Player player1, Player player2)
    {
        this.player1 = player1;
        this.player2 = player2;

        mapX = x;
        mapY = y;
        //Getting map segments
        MapSection[] sections = generateMapSections();
        //Drawing up blank map
        mapTiles = new MapTile[mapX][mapY];
        for (int j = 0; j < y; j++)
        {
            for (int i = 0; i < x; i++)
            {
                mapTiles[i][j] = new MapTile(this, i * TILE_SIZE + LEFT_INDENT, j * TILE_SIZE);
            }
        }
        //Loading a map feature in each segment
        Random r = new Random();
        for (MapSection s:sections)
        {
            switch (r.nextInt(3))
            {
                case 0:
                    generateCoverCluster(s);
                    break;
                case 1:
                    generateMaze(s);
                    break;
            }
        }
        //Adding crystals to the map
        spawnCrystals('R');
        spawnCrystals('G');
        spawnCrystals('B');
        spawnCrystals('Y');

        //Splattering some extra cover tiles across the map to add a sense of homogeny
        for (int i = 0; i < 50; i++)
        {
            placeRandomCover();
        }

        spawnInitialPickups();
    }

    private void generateMaze(MapSection s)
    {
        int startX = s.getBounds()[0];
        int endX = s.getBounds()[1];
        int startY = s.getBounds()[2];
        int endY = s.getBounds()[3];
        int[] mazeCentre = getCentrePoint(startX, endX, startY, endY);
        //Generating central cover rectangle
        for (int y = (mazeCentre[1] - (mazeCentre[1] - startY) * 3 / 4); y < ((endY - mazeCentre[1]) * 3 / 4) + mazeCentre[1]; y++)
        {
            for (int x = (mazeCentre[0] - (mazeCentre[0] - startX) * 3 / 4); x < ((endX - mazeCentre[0]) * 3 / 4 + mazeCentre[0]); x++)
            {
                mapTiles[x][y] = new CollisionTile(this, 'c', x * TILE_SIZE + LEFT_INDENT, y * TILE_SIZE);
            }
        }
        //Generating two points, then creating a line of blank tiles between them
        Random r = new Random();
        for (int i = 0; i < 15; i++)
        {
            int pointAx = r.nextInt(endX - startX) + startX;
            int pointAy = r.nextInt(endY - startY) + startY;
            int pointBx = r.nextInt(endX - startX) + startX;
            int pointBy = r.nextInt(endY - startY) + startY;
            int loopNum = 0;
            while (!(pointAx == pointBx && pointAy == pointBy) && loopNum < 100)
            {
                mapTiles[pointAx][pointAy] = new MapTile(this, pointAx * TILE_SIZE + LEFT_INDENT, pointAy * TILE_SIZE);
                if (Math.abs(pointAy - pointBy) > Math.abs(pointAx - pointBx) && pointAy != pointBy)
                {
                    if (pointAy < pointBy)
                    {
                        pointAy++;
                    }
                    else
                    {
                        pointAy--;
                    }
                }
                else if (pointAx != pointBx)
                {
                    if (pointAx < pointBx)
                    {
                        pointAx++;
                    }
                    else
                    {
                        pointAx--;
                    }
                }
                loopNum++;
            }
        }
    }

    public void decay()
    {
        placeRandomCover();
    }

    protected void spawnCrystals(char crystalType)
    {
        Random r = new Random();
        int numCrystals = r.nextInt(3) + 3;
        int pos[][] = new int[numCrystals][2];
        for (int i = 0; i < numCrystals; i++)
        {
            boolean tilePlaced = false;
            while (!tilePlaced)
            {
                pos[i][0] = r.nextInt(mapX);
                pos[i][1] = r.nextInt(mapY);
                boolean tooClose = true;
                while (tooClose)
                {
                    while (mapTiles[pos[i][0]][pos[i][1]].getType() == 'c')
                    {
                        pos[i][0] = r.nextInt(mapX);
                        pos[i][1] = r.nextInt(mapY);
                    }
                    if (i > 0)
                    {
                        for (int a = 0; a < i; a++)
                        {
                            if (Math.sqrt(Math.pow(pos[i][0] - pos[a][0], 2) + Math.pow(pos[i][1] - pos[a][1], 2)) > 5)
                            {
                                tooClose = false;
                            }
                            else
                            {
                                pos[i][0] = r.nextInt(mapX);
                                pos[i][1] = r.nextInt(mapY);
                            }
                        }
                    }
                    else
                    {
                        tooClose = false;
                    }
                }
                mapTiles[pos[i][0]][pos[i][1]] = new Crystal(this, crystalType, pos[i][0] * TILE_SIZE + LEFT_INDENT, pos[i][1] * TILE_SIZE);
                tilePlaced = true;
            }
        }
    }

    public Player getPlayer1()
    {
        return player1;
    }

    public Player getPlayer2()
    {
        return player2;
    }

    public void setPlayer1(Player player1)
    {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2)
    {
        this.player2 = player2;
    }
}
