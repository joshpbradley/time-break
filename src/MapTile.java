import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import java.nio.file.Paths;

/**
 *
 * Represents a tile for use in the Map class.
 * @author Thomas Allaker
 *
 */
public class MapTile extends Sprite
{
    private char terrainType; //Stores the type of terrain represented by this tile.
    private DropItem heldItem; //Stores the weapon present in this tile (or null, if no weapon present).
    private int hitPoints; //Stores how many hit-points the tile has remaining (-1 if tile is indestructible or cannot be hit).
    private StatusEffect effect; //Stores the effect (if any) present on this tile
    private Map map;
    private int xPos;
    private int yPos;

    /**
     * <p>Generates the tile based on the type of tile it represents.</p>
     * @param type The type of tile to be represented.
     */
    public MapTile(Map map, char type, int x, int y)
    {
        init(map, type, x, y);
    }

    public MapTile(Map map, int x, int y)
    {
        init(map, 'b', x, y);
    }

    protected void init(Map map, char t, int x, int y)
    {
        this.terrainType = t;
        //Initialising hit points and whether the tile can collide
        switch (this.terrainType) {
            case 'c':
                this.hitPoints = 100;
                break;
            case 'I':
                this.hitPoints = 50;
                break;
            case 'v':
                effect = new Slow(100);
                this.hitPoints = 25;
                break;
            case 'R':
            case 'G':
            case 'B':
            case 'Y':
                this.hitPoints = 1;
                break;
            case 'l':
                effect = new Burning(100);
                this.hitPoints = -1;
                break;
            case 's':
                effect = new Jump(1000);
                this.hitPoints = -1;
                break;
            case 'w':
                effect = new Slow(100);
                this.hitPoints = -1;
                break;
            default:
                this.hitPoints = -1;
                break;
        }
        //Set position on the screen
        this.xPos = x;
        this.yPos = y;
        //Store the map containing this tile
        this.map = map;
        //Set the graphics for this tile
        setSprite();
    }

    //Handling a CollisionItem
    public void bodyPresent(CollisionItem body)
    {
        if(body instanceof MovingBody && ((MovingBody)body).isOnGround())
        {
            if(body instanceof Player)
            {
                playerPresent((Player)body);
            }
            else if(body instanceof Projectile)
            {
                projectilePresent((Projectile)body);
            }
        }
    }

    private void playerPresent(AbstractPlayer p)
    {
        //Apply effect (if relevant)
        if (effect != null)
        {
            if (effect instanceof Burning) p.addStatusEffect(new Burning(effect.getDuration()));
            else if (effect instanceof  Jump) p.addStatusEffect(new Jump(effect.getDuration()));
            else if (effect instanceof Slow) p.addStatusEffect(new Slow(effect.getDuration()));
        }
        //Affect player movement (if relevant)
        if (p.isOnGround())
        {
            switch (this.terrainType)
            {
                case 'r':
                    if (p.getSpeedY() < 1)
                    {
                        p.setSpeedY(p.getSpeedY() + 0.075);
                    }
                    break;
                case 'i':
                    if (p.getFriction() < 0.98)
                    {
                        p.setFriction((1 - p.getFriction()) / 2 + p.getFriction());
                    }
                    break;
                case 'b':
                    p.setFriction(0.95);
                    break;
            }
        }
        //If player is in space, set friction to 1
        if (map instanceof SpaceMap) {
            p.setFriction(0.999);
        }
    }
    public void projectilePresent(Projectile p)
    {
        if(this.hitPoints > 0 && !(p instanceof ExplodingProjectile))
        {
            GameScene.getGameScene().remove(p);
            takeDamage(p.getDamage());

            if (getHitPoints() <= 0)
            {
                replaceBrokenTile();
            }
        }
    }

    /**
     * <p>Returns the type of terrain represented by this tile.
     * tile present could be one of:</p>
     * <ul>
     *  <li>b - Blank space.</li>
     *  <li>c - Generic cover.</li>
     *  <li>l - Lava.</li>
     *  <li>s - Steam vent.</li>
     *  <li>w - Swamp water.</li>
     *  <li>v - Vines.</li>
     *  <li>R - Red crystal.</li>
     *  <li>G - Green crystal.</li>
     *  <li>B - Blue crystal.</li>
     *  <li>Y - Yellow crystal.</li>
     *  <li>r - River water.</li>
     *  <li>g - Long grass.</li>
     *  <li>i - Ice.</li>
     *  <li>I - Ice boulder.</li>
     * </ul>
     * @return A character representing the terrain present.
     */
    char getType()
    {
        return this.terrainType;
    }

    /**
     * <p>Returns the remaining hit points this tile has.
     * If the value is -1, then this tile does not take damage.</p>
     * @return Remaining hit points.
     */
    public int getHitPoints()
    {
        return this.hitPoints;
    }

    protected void setSprite()
    {
        //Create texture
        Texture t = new Texture();

        //Load appropriate map graphic
        try {
            if (map instanceof CrystalMap) {
                switch (this.getType()) {
                    case 'b':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/Crystal/Floor-Tile.png"));
                        break;
                    case 'c':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/Crystal/Wall-Tile.png"));
                        break;
                    case 'R':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/Crystal/Red-Crystal-Tile.png"));
                        break;
                    case 'G':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/Crystal/Green-Crystal-Tile.png"));
                         break;
                    case 'B':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/Crystal/Blue-Crystal-Tile.png"));
                         break;
                    case 'Y':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/Crystal/Yellow-Crystal-Tile.png"));
                        break;
                }
            }
            else if (map instanceof IceMap)
            {
                switch (this.getType())
                {
                    case 'b':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/Ice/Snow-Tile.png"));
                        break;
                    case 'c':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/Ice/Bush-Tile.png"));
                        break;
                    case 'i':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/Ice/Ice-Tile.png"));
                        break;
                    case 'I':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/Ice/Ice-Rock-Tile.png"));
                        break;
                }
            }
            else if(map instanceof LavaMap)
            {
                switch(this.getType())
                {
                    case 'b':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/Lava/Floor-Tile[1].png"));
                        break;
                    case 'c':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/Lava/Rock-Tile.png"));
                        break;
                    case 'l':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/Lava/Lava-Tile.png"));
                        break;
                    case 's':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/Lava/Steam-Tile.png"));
                        break;
                }
            }
            else if(map instanceof RiverMap)
            {
                switch(this.getType())
                {
                    case 'b':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/River/Grass-Tile.png"));
                        break;
                    case 'c':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/River/bush-tile.png"));
                        break;
                    case 'r':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/River/Water-Tile.png"));
                        break;
                    case 'g':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/River/Long-Grass-Tile.png"));
                        break;
                }
            }
            else if (map instanceof SpaceMap)
            {
                switch (this.getType())
                {
                    case 'b':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/Space/Space-Tile.png"));
                        break;
                    case 'c':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/Space/Asteroid-Tile.png"));
                        break;
                }
            }
            else if (map instanceof SwampMap)
            {
                switch (this.getType())
                {
                    case 'b':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/Swamp/grass-short-tile.png"));
                        break;
                    case 'c':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/Swamp/bush-tile.png"));
                        break;
                    case 'w':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/Swamp/water-tile.png"));
                        break;
                    case 'v':
                        t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Map/Swamp/vine-tile.png"));
                        break;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        setTexture(t);
        setScale((float)0.9, (float)0.9);
        setPosition(this.xPos, this.yPos);
    }

    public void draw(RenderWindow window)
    {
        window.draw(this);

        // Temporary.
        if(heldItem instanceof Drawable)
        {
            window.draw((Drawable) heldItem);
        }
    }

    public boolean hasHeldItem()
    {
        return (heldItem != null);
    }

    public DropItem getHeldItem()
    {
        return heldItem;
    }

    public void setHeldItem(DropItem item)
    {
        heldItem = item;
        if(item instanceof Weapon)
        {
            ((Weapon) heldItem).resetSpriteTexture();
            ((Weapon) heldItem).setPosition(getGlobalBounds().width/2 + getGlobalBounds().left, getGlobalBounds().height/2 + getGlobalBounds().top);
            ((Weapon) heldItem).setWeaponOwner(null);
        }
        else if(item instanceof Item)
        {
            // condition put in to prevent null pointer exception on game load
            if(((Item) item).getItemOwner() != null)
            {
                ((Item)heldItem).setItemOwner(null);
            }
        }
    }

    public int getXPosition()
    {
        return (int)((xPos - 225)/getGlobalBounds().width);
    }

    public int getYPosition()
    {
        return (int)(yPos/getGlobalBounds().height);
    }

    public int getXPos()
    {
        return xPos;
    }

    public int getYPos()
    {
        return yPos;
    }

    protected void takeDamage(int damage)
    {
        hitPoints -= damage;
    }

    public void replaceBrokenTile()
    {
        GameScene.getGameScene().getMap().mapTiles[getXPosition()][getYPosition()] = new MapTile(GameScene.getGameScene().getMap(), getXPos(), getYPos());
        if (GameScene.getGameScene().getMap() instanceof IceMap) {
            if (
                    (((IceMap) GameScene.getGameScene().getMap()).edge == 0 && ((IceMap) GameScene.getGameScene().getMap()).blizzardProgress > GameScene.getGameScene().getMap().mapTiles[getXPosition()][getYPosition()].xPos / GameScene.getGameScene().getMap().mapTiles[getXPosition()][getYPosition()].getGlobalBounds().width)
                            || (((IceMap) GameScene.getGameScene().getMap()).edge == 1 && ((IceMap) GameScene.getGameScene().getMap()).blizzardProgress > GameScene.getGameScene().getMap().mapTiles[getXPosition()][getYPosition()].xPos / GameScene.getGameScene().getMap().mapTiles[getXPosition()][getYPosition()].getGlobalBounds().width - GameScene.getGameScene().getMap().mapX)) {
                GameScene.getGameScene().getMap().mapTiles[getXPosition()][getYPosition()].setStatusEffect(new Burning(100));
            }
        }
    }

    public final void isDestroyed()
    {
        if(getHitPoints() <= 0)
        {
            replaceBrokenTile();
        }
    }

    public void collision(CollisionItem collideWith)
    {
        if(collideWith instanceof Player)
        {
            playerPresent((Player)collideWith);
        }
        else if(collideWith instanceof Projectile)
        {
            projectilePresent((Projectile)collideWith);
        }
    }

    public void setStatusEffect(StatusEffect newEffect)
    {
        effect = newEffect;
    }
    public StatusEffect getStatusEffect()
    {
        return effect;
    }
}