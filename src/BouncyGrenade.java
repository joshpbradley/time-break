import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.nio.file.Paths;

public class BouncyGrenade extends Grenade
{
    private final int bounceLimit = 3;
    private int bouncesLeft = 3;

    public BouncyGrenade()
    {
        super(0,40, -1, 4, 1);
        setInitVel(3);
    }

    @Override
    public void collision(CollisionItem collideWith)
    {
        if(collideWith.equals(getItemOwner()) && bouncesLeft == bounceLimit)
        {
            return;
        }
        else if(collideWith instanceof AbstractPlayer || bouncesLeft-- <= 0)
        {
            detonate();
        }
        else
        {
            FloatRect overlap = getGlobalBounds().intersection(((Sprite) collideWith).getGlobalBounds());

            if(overlap.width > overlap.height)
            {
                setSpeedY(-getSpeedY());
            }
            else
            {
                setSpeedX(-getSpeedX());
            }
        }
    }

    @Override
    public void update()
    {
        if(getItemOwner() == null)
        {
            return;
        }

        if(!getActive())
        {
            if(getItemOwner().getShootingMode() || getItemOwner().getIdle() )
            {
                setTexture(AllTextureFiles.getTransparentTexture(), true);
            }
            else
            {
                setTexture(AllTextureFiles.getItemTexture(getItemOwner()), true);
            }

            setPosition();
        }
        else
        {
            if(getItemOwner().getItem() != null)
            {
                setTexture(AllTextureFiles.getItemModelTexture(getItemOwner()), true);
                getItemOwner().setMode(true);
                getItemOwner().setItem(null);
            }

            // Handles collision of the game borders
            if(handleMapCollisions())
            {
                if(bouncesLeft-- <= 0)
                {
                    detonate();
                    return;
                }
            }

            if(handleBorderCollisions())
            {
                if(bouncesLeft-- <= 0)
                {
                    detonate();
                }
            }
        }
    }

    @Override
    public Sprite getItemSprite()
    {
        Texture t = new Texture();
        try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Items/Item-Models/Bouncy-Grenade.png")); }catch(Exception e){ e.printStackTrace(); }
        Sprite s = new Sprite(t);
        s.setScale(2f, 2f);
        s.setOrigin(  s.getLocalBounds().width/2,  s.getLocalBounds().height/2);
        return s;
    }

    @Override
    public Item clone() {
        return new BouncyGrenade();
    }

    public boolean handleMapCollisions()
    {
        MapTile[][] possibleCollisionTiles = GameScene.getGameScene().getMap().getMapTileAreaSurroundingPosition(3, getGlobalCentrePoint());
        boolean collisionDetected = false;

        for (int x = 0; x < 3; ++x)
        {
            for (int y = 0; y < 3; ++y)
            {
                if (possibleCollisionTiles[x][y] instanceof CollisionTile)
                {
                    FloatRect overlap = getGlobalBounds().intersection(possibleCollisionTiles[x][y].getGlobalBounds());
                    if (overlap != null)
                    {
                        collisionDetected = true;
                        if (overlap.width > overlap.height)
                        {
                            setSpeedY(-getSpeedY());

                            if (getGlobalBounds().top > possibleCollisionTiles[x][y].getGlobalBounds().top)
                            {
                                move(0, overlap.height);
                            }
                            // Player bottom edge collision.
                            else
                            {
                                move(0, -overlap.height);
                            }
                        }
                        else
                        {
                            setSpeedX(-getSpeedX());

                            // Player left edge collision.
                            if (getGlobalBounds().left > possibleCollisionTiles[x][y].getGlobalBounds().left)
                            {
                                move(overlap.width, 0);
                            }
                            // Player right edge collision.
                            else
                            {
                                move(-overlap.width, 0);
                            }
                        }
                    }
                }
            }
        }
        return collisionDetected;
    }

    private boolean handleBorderCollisions()
    {
        Controller.Direction edgeHit = hasHitEdge();
        if(edgeHit == null)
        {
            return false;
        }
        else
        {
            unclipGameBorders(edgeHit);
            if(edgeHit.equals(Controller.Direction.UP) || edgeHit.equals(Controller.Direction.DOWN))
            {
                setSpeedY(-getSpeedY());
            }
            else
            {
                setSpeedX(-getSpeedX());
            }
            return true;
        }
    }
}
