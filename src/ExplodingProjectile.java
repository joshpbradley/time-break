import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

public class ExplodingProjectile extends Projectile implements Explodable
{
    private final Grenade grenadeItem;
    private int bouncesLeft = 0;

    public ExplodingProjectile(Texture t, double spX, double spY, int dmg, AbstractPlayer playerFiring)
    {
        super(t, spX, spY, dmg, playerFiring.getTeam());

        int playerFiringItemID = -1;

        if(!(playerFiring instanceof Player))
        {
            grenadeItem = null;
            return;
        }

        Item playerItem = ((Player) playerFiring).getItem();

        if(playerFiring instanceof Player && playerItem != null)
        {
            playerFiringItemID = playerItem.getID();
        }

        switch(playerFiringItemID)
        {
            case 0:
                grenadeItem = new BouncyGrenade();
                GameScene.getGameScene().remove((CollisionItem)((Player)playerFiring).getItem());
                ((Player)playerFiring).setItem(null);
                bouncesLeft = 3;
                break;
            case 1:
                grenadeItem = new BulletGrenade();
                GameScene.getGameScene().remove((CollisionItem)((Player)playerFiring).getItem());
                ((Player)playerFiring).setItem(null);
                break;
            case 2:
                grenadeItem = new ConcussionGrenade();
                GameScene.getGameScene().remove((CollisionItem)((Player)playerFiring).getItem());
                ((Player)playerFiring).setItem(null);
                break;
            case 3:
                grenadeItem = new GlueGrenade();
                GameScene.getGameScene().remove((CollisionItem)((Player)playerFiring).getItem());
                ((Player)playerFiring).setItem(null);
                break;
            case 4:
                grenadeItem = new JumpGrenade();
                GameScene.getGameScene().remove((CollisionItem)((Player)playerFiring).getItem());
                ((Player)playerFiring).setItem(null);
                break;
            default:
                grenadeItem = null;
        }

        if(grenadeItem != null)
        {
            grenadeItem.setTexture(new Texture());
            grenadeItem.setScale(4, 4);
        }

        setOnGround(!(grenadeItem != null && grenadeItem instanceof JumpGrenade));
    }

    @Override
    public void applyExplosionEffects(AbstractPlayer playerAfflicted, boolean firstHit)
    {
        if(grenadeItem != null)
        {
            if(firstHit)
            {
                grenadeItem.applyExplosionEffects(playerAfflicted, true);
                if(grenadeItem.getDamage() < damage)
                {
                    playerAfflicted.takeDamage(damage - grenadeItem.getDamage());
                }
            }
            else
            {
                grenadeItem.applyExplosionEffects(playerAfflicted, false);
            }
        }
        else
        {
            if(firstHit)
            {
                playerAfflicted.takeDamage(damage);
            }
            else
            {
                playerAfflicted.takeDamage(5);
            }
        }
    }

    @Override
    public void applyExplosionEffects(MapTile tileAfflicted, boolean firstHit)
    {
        if(firstHit)
        {
            tileAfflicted.takeDamage(damage);
        }
        else
        {
            tileAfflicted.takeDamage(5);
        }

        tileAfflicted.isDestroyed();
    }

    @Override
    public void detonate()
    {
        GameScene.getGameScene().remove(this);

        if(grenadeItem != null)
        {
            grenadeItem.detonate(this, getGlobalBounds());
        }
        else
        {
            GameScene.getGameScene().add(new Explosion(this,2, 4, (int)this.getPosition().x, (int)this.getPosition().y));
        }
    }

    public void detonate(FloatRect overlap)
    {
        GameScene.getGameScene().remove(this);

        if(grenadeItem != null)
        {
            grenadeItem.detonate(this, overlap);
        }
        else
        {
            GameScene.getGameScene().add(new Explosion(this,2, 4, (int)this.getPosition().x, (int)this.getPosition().y));
        }
    }

    @Override
    public void collision(CollisionItem collideWith)
    {
        FloatRect overlap = getGlobalBounds().intersection(((Sprite) collideWith).getGlobalBounds());

        if(bouncesLeft-- <= 0 || collideWith instanceof AbstractPlayer)
        {
            detonate(overlap);
            return;
        }

        if(overlap.width > overlap.height)
        {
            setSpeedY(-getSpeedY());
        }
        else
        {
            setSpeedX(-getSpeedX());
        }

        handleSpriteOnHit();
    }

    @Override
    public void update()
    {
        Controller.Direction edgeHit = hasHitEdge();
        if(edgeHit == null)
        {
            return;
        }
        else
        {
            if(bouncesLeft-- <= 0)
            {
                detonate();
                return;
            }

            unclipGameBorders(edgeHit);

            if(edgeHit.equals(Controller.Direction.UP) || edgeHit.equals(Controller.Direction.DOWN))
            {
                setSpeedY(-getSpeedY());
            }
            else
            {
                setSpeedX(-getSpeedX());
            }

            handleSpriteOnHit();
        }
    }

    @Override
    public double getSpeedX()
    {
        return super.getSpeedX() * 4;
    }

    @Override
    public double getSpeedY()
    {
        return super.getSpeedY() * 4;
    }

    private void handleSpriteOnHit()
    {
        if(Math.abs(getSpeedX()) > Math.abs(getSpeedY()))
        {
            if(getSpeedX() < 0)
            {
                setRotation(180);
            }
            else
            {
                setRotation(0);
            }
        }
        else
        {
            if(getSpeedY() < 0)
            {

                setRotation(270);
            }
            else
            {
                setRotation(90);
            }
        }
    }

    protected void hit(MovingBody collideWit)
    {

    }
}
