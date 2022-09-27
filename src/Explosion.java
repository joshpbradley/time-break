import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Represents explosions indicators that are capable of damaging players and tiles that come into contact with it.
 */
public class Explosion extends Sprite implements Runnable, CollisionItem
{
    private int drawTime;
    private float AoESize;
    private Random r = new Random();
    private static final String[] explosionPaths = new String[]
    {
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Explosion/1.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Explosion/1-flip.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Explosion/2.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Explosion/2-flip.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Explosion/3.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Explosion/3-flip.png"
    };
    private static final Texture[] explosionTextures = new Texture[explosionPaths.length];
    private Explodable item;
    private boolean player1BeenHit = false;
    private boolean player2BeenHit = false;
    private boolean player1ItemBeenHit = false;
    private boolean player2ItemBeenHit = false;
    private long explosionTime;
    private long lastUpdatePlayers = -1;
    private boolean updateLastTimeHitPlayers = false;
    private long lastUpdateTiles = -1;
    private boolean updateLastTimeHitTiles = false;
    private final boolean[][] mapTilesHit = new boolean[GameScene.getGameScene().getMap().mapTiles.length][GameScene.getGameScene().getMap().mapTiles[0].length];

    /**
     *The constructor for Explosion.
     *
     * @param item the explodable item that created the explosion
     * @param drawTime the number of milliseconds that the explosion shall occur before it shrinks
     * @param AoESize the maximum grid square length that the explosion can grow to
     * @param posX the x-ordinate of the centre of the explosion
     * @param posY the y-ordinate of the centre of the explosion
     */
    public Explosion(Explodable item, int drawTime, int AoESize, int posX, int posY)
    {
        AudioHandler.playSound("explosion");

        this.item = item;
        this.drawTime = drawTime;
        this.AoESize = TransformController.getScaleMultiplier(50, GameScene.getGameScene().getView().getSize().x/42*AoESize, TransformController.Domain.X);

        for(int i = 0; i < explosionPaths.length; ++i)
        {
            explosionTextures[i] = new Texture();
            try {  explosionTextures[i].loadFromFile(Paths.get(explosionPaths[i])); }catch(Exception e){ e.printStackTrace(); }
        }

        setTexture(explosionTextures[0]);
        setOrigin(getLocalBounds().width/2, getLocalBounds().height/2);
        setPosition(posX, posY);

        explosionTime = System.currentTimeMillis();

        for(int x = 0; x < mapTilesHit.length; ++x)
        {
            for(int y = 0; y < mapTilesHit[0].length; ++y)
            {
                mapTilesHit[x][y] = false;
            }
        }

        new Thread(this).start();
    }

    /**
     * run() method to comply with the Runnable interface.
     * It updates the size, sprite and rotation of the explosion.
     * The object is removed from GameScene once it shrinks to a small enough size.
     */
    public void run()
    {
        long startTime = System.currentTimeMillis();

        while(System.currentTimeMillis() < (startTime + drawTime))
        {
            setTexture(explosionTextures[r.nextInt(explosionTextures.length)], true);
            float random = AoESize / 2 + r.nextFloat() * (AoESize / 2);
            setScale(random, random);
            setRotation(r.nextInt(360));
            try{ Thread.sleep(100); }catch(Exception e){ e.printStackTrace(); }
        }
        while((getScale().x - 0.1) >= 0)
        {
            setTexture(explosionTextures[r.nextInt(explosionTextures.length)], true);
            scale(0.7f, 0.7f);
            setRotation(r.nextInt(360));
            try{ Thread.sleep(100); }catch(Exception e){ e.printStackTrace(); }
        }
        GameScene.getGameScene().remove(this);
    }

    /**
     * Pushes MovingBody objects that collide with the explosion away, and
     * deals damage to AbstractPlayer objects.
     *
     * @param collideWith the CollisionItem that is colliding with the explosion
     */
    @Override
    public void collision(CollisionItem collideWith)
    {
        if(!(collideWith instanceof MovingBody))
        {
            return;
        }

        Vector2f thisPosition = new Vector2f(getGlobalBounds().left + getGlobalBounds().width/2,
                                            getGlobalBounds().top + getGlobalBounds().height/2);
        Vector2f thatPosition = new Vector2f(((Sprite)collideWith).getGlobalBounds().left + ((Sprite)collideWith).getGlobalBounds().width/2,
                ((Sprite)collideWith).getGlobalBounds().top + ((Sprite)collideWith).getGlobalBounds().height/2);

        Vector2f displacementVector = new Vector2f(thatPosition.x - thisPosition.x, thatPosition.y - thisPosition.y);

        float absDistance = (float)Math.sqrt(Math.pow(displacementVector.x, 2) + Math.pow(displacementVector.y, 2));
        float maxDistance = getGlobalBounds().width/2;
        // Calculates the square root of the sum of each speed component squared. a = (b^2 + c^2)^(1/2)
        float maxSpeedToTravel = (float)Math.sqrt(2 * Math.pow(((MovingBody)collideWith).getMaxSpeed(), 2));
        float minSpeedToTravel = maxSpeedToTravel/2;
        // Uses the inverse square law to calculate speed (speed = (k/distance^2))
        float speedToTravel = (float)((maxSpeedToTravel - minSpeedToTravel)/Math.pow(maxDistance, 2) + minSpeedToTravel);
        float angle;

        if(absDistance == 0)
        {
            Random r = new Random();
            angle = r.nextInt(360);
        }
        else
        {
            // displacement in top-right quadrant or bottom-left quadrant
            if((displacementVector.x >= 0 && displacementVector.y < 0) || (displacementVector.x <= 0 && displacementVector.y > 0))
            {
                angle = (float)Math.atan(Math.abs(displacementVector.x)/Math.abs(displacementVector.y));

                if(displacementVector.y > 0)
                {
                    angle += 180;
                }
            }
            else
            {
                angle = (float)Math.atan(Math.abs(displacementVector.y)/Math.abs(displacementVector.x));

                if(displacementVector.x > 0)
                {
                    angle += 90;
                }
                else
                {
                    angle += 270;
                }
            }
        }

        if(angle < 90)
        {
            ((MovingBody)collideWith).setSpeedX(speedToTravel * Math.sin(angle));
            ((MovingBody)collideWith).setSpeedY(speedToTravel * Math.cos(angle));
        }
        else if(angle < 180)
        {
            ((MovingBody)collideWith).setSpeedX(speedToTravel * Math.cos(angle - 90));
            ((MovingBody)collideWith).setSpeedY(speedToTravel * Math.sin(angle - 90));
        }
        else if(angle < 270)
        {
            ((MovingBody)collideWith).setSpeedX(-speedToTravel * Math.sin(angle - 180));
            ((MovingBody)collideWith).setSpeedY(speedToTravel * Math.cos(angle - 180));
        }
        else
        {
            ((MovingBody) collideWith).setSpeedX(-speedToTravel * Math.cos(angle - 270));
            ((MovingBody) collideWith).setSpeedY(-speedToTravel * Math.sin(angle - 270));
        }

        if(collideWith instanceof AbstractPlayer)
        {
            if(collideWith.getTeam() == GameScene.Team.First)
            {
                if((((collideWith instanceof Player) && !player1BeenHit) || ((collideWith instanceof Item) && !player1ItemBeenHit))
                        && System.currentTimeMillis() - explosionTime < 100)
                {
                    if(collideWith instanceof Item)
                    {
                        player1ItemBeenHit = true;
                    }
                    else
                    {
                        player1BeenHit = true;
                    }

                    item.applyExplosionEffects((AbstractPlayer)collideWith, true);
                }
                else if(System.currentTimeMillis() - explosionTime > 500 && System.currentTimeMillis() - lastUpdatePlayers > 200)
                {
                    item.applyExplosionEffects((AbstractPlayer)collideWith, false);
                    updateLastTimeHitPlayers = true;
                }
            }
            else
            {
                if(((collideWith instanceof Player) && !player2BeenHit || (collideWith instanceof Item) && !player2ItemBeenHit)
                        && System.currentTimeMillis() - explosionTime < 100)
                {
                    if(collideWith instanceof Item)
                    {
                        player2ItemBeenHit = true;
                    }
                    else
                    {
                        player2BeenHit = true;
                    }

                    item.applyExplosionEffects((AbstractPlayer)collideWith, true);
                }
                else if((System.currentTimeMillis() - explosionTime) > 500 && (System.currentTimeMillis() - lastUpdatePlayers) > 200)
                {
                    updateLastTimeHitPlayers = true;
                    item.applyExplosionEffects((AbstractPlayer)collideWith, false);
                }
            }
        }
        if(updateLastTimeHitPlayers)
        {
            updateLastTimeHitPlayers = false;
            lastUpdatePlayers = System.currentTimeMillis();
        }
    }

    /**
     * Checks for collisions with map tile objects on the map.
     * Since they do not move, only the initial damage of the explosion is
     * applied to the tile.
     */
    @Override
    public void update()
    {
        FloatRect tilesAffectedDiameter = getGlobalBounds();
        float tilesAffectedLength = tilesAffectedDiameter.width;
        int tileWidthToInspect = (int)(tilesAffectedLength/GameScene.getGameScene().getMap().mapTiles[0][0].getGlobalBounds().width);
        MapTile[][] tilesInBlast = GameScene.getGameScene().getMap().getMapTileAreaSurroundingPosition(tileWidthToInspect,
                new Vector2f(tilesAffectedDiameter.width/2 +  tilesAffectedDiameter.left, tilesAffectedDiameter.height/2 + tilesAffectedDiameter.top));

        for(int x = 0; x < tileWidthToInspect; ++x)
        {
            for(int y = 0; y < tileWidthToInspect; ++y)
            {
                if(tilesInBlast[x][y] instanceof CollisionTile)
                {
                    if(!mapTilesHit[tilesInBlast[x][y].getXPosition()][tilesInBlast[x][y].getYPosition()])
                    {
                        updateLastTimeHitTiles = true;
                        item.applyExplosionEffects(tilesInBlast[x][y], true);
                        mapTilesHit[tilesInBlast[x][y].getXPosition()][tilesInBlast[x][y].getYPosition()] = true;
                    }
                    else
                    {
                        item.applyExplosionEffects(tilesInBlast[x][y], false);
                    }
                }
            }
        }

        if(updateLastTimeHitTiles)
        {
            updateLastTimeHitTiles = false;
            lastUpdateTiles = System.currentTimeMillis();
        }
    }

    /**
     * Accesses the team of the object. Neutral so both players can interact with
     * a given explosion.
     * @return the team of the explosion
     */
    public GameScene.Team getTeam()
    {
        return GameScene.Team.Neutral;
    }
}