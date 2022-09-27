import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Texture;

/**
 * This class is used to give all grenades the base functionality of a plain grenade.
 */

public abstract class Grenade extends MovingBody implements Item, Explodable
{
    private static int INIT_VEL = 12;
    private static final int MAX_SPEED = 20;

    private boolean detonated = false;
    private int id;
    private Player itemOwner;
    private long throwTime;
    private final int damage;
    private final int AOEsize;
    private final int detonationTimer;
    private boolean active = false;

    /**
     * Creates a new grenade object.
     * @param id The id of the grenade.
     * @param damage The amount of damage that the grenade does.
     * @param detonationTimer The length after which the grenade explodes .
     * @param AOEsize The size of the area of effect when the grenade explodes.
     * @param friction The amount of friction that is applied to the grenade when it is thrown.
     */

    public Grenade(int id, int damage, int detonationTimer, int AOEsize, float friction)
    {
        super(new Texture(), GameScene.Team.Neutral);
        setOrigin(getLocalBounds().width/2, getLocalBounds().height/2);
        setScale(Player.initialScaling * 1.3f, Player.initialScaling * 1.3f);
        setMaxSpeed(MAX_SPEED);
        setFriction(friction);

        this.id = id;
        this.damage = damage;
        this.detonationTimer = detonationTimer;
        this.AOEsize = AOEsize;
    }

    public int getDamage()
    {
        return damage;
    }

    /**
     * The function used to convert the direction of the grenade from a generic value to a radian.
     * @param direction The controller direction for which way the grenade has been thrown.
     * @return The angle of direction in radians.
     */

    private double getAngle(Controller.Direction direction)
    {
        double directionRad = -1;

        if (direction == Controller.Direction.RIGHT)
        {
            directionRad = 0;
        }
        else if (direction == Controller.Direction.DOWN)
        {
            directionRad = Math.PI/2;
        }
        else if (direction == Controller.Direction.LEFT)
        {
            directionRad = Math.PI;
        }
        else if (direction == Controller.Direction.UP)
        {
            directionRad = 3*Math.PI/2;
        }
        return directionRad;
    }

    /**
     * On collision the grenade checks if it collided with it's thrower when being thrown out and if not
     * the grenade explodes.
     * @param ci
     */
    @Override
    public void collision(CollisionItem ci)
    {
        if(ci.equals(itemOwner) && (getSpeedX() != 0 || getSpeedY() != 0) || !active)
        {
            return;
        }

        detonate();
    }

    /**
     * The grenade is no longer drawn and an explosion is created where the grenade was when detonate was called.
     */
    public void detonate()
    {
        if(!detonated)
        {
            detonated = true;
            GameScene.getGameScene().remove(this);
            GameScene.getGameScene().add(new Explosion(this, 2000, AOEsize, (int) getPosition().x, (int) getPosition().y));
        }
    }

    /**
     * Allows another explodable to create an explosion in place of the grenade.
     *
     * Currently used so ExplodingProjectiles can handle a detonation event in the same way its Grenade object does,
     * and allows the created explosion to be a composed from the ExplodingProjectile and not the Grenade (So the explosion effects
     * can be customised by the exploding projectile and not the grenade).
     *
     * @param e the Explodable that will place itself as the item in the explosion
     * @param f the bounding rectangle that the explosion will centre on
     */
    public void detonate(Explodable e, FloatRect f)
    {
        if(!detonated)
        {
            detonated = true;
            GameScene.getGameScene().remove(this);
            try
            {
                GameScene.getGameScene().add(new Explosion(e, 2000, AOEsize, (int) (f.left + f.width / 2), (int) (f.top + f.height / 2)));
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                GameScene.getGameScene().add(new Explosion(this, 2000, AOEsize, (int) (f.left + f.width / 2), (int) (f.top + f.height / 2)));
            }
        }
    }

    @Override
    public void update()
    {
        if(itemOwner == null)
        {
            return;
        }

        if(!active)
        {
            if(itemOwner.getShootingMode() || itemOwner.getIdle() )
            {
                setTexture(AllTextureFiles.getTransparentTexture(), true);
            }
            else
            {
                setTexture(AllTextureFiles.getItemTexture(itemOwner), true);
            }

            setPosition();
        }
        else
        {
            if(itemOwner.getItem() != null)
            {
                setTexture(AllTextureFiles.getItemModelTexture(itemOwner), true);
                itemOwner.setMode(true);
                itemOwner.setItem(null);
            }

            boundaryHit();
            if(System.currentTimeMillis() > throwTime + detonationTimer * 1000)
            {
               detonate();
            }
        }
    }

    /**
     * When used the grenade will be 'thrown' by the owner in a given direction.
     * @param direction the direction in which the grenade has been thrown.
     */
    @Override
    public void use(Controller.Direction direction)
    {
        throwTime = System.currentTimeMillis();

        if(itemOwner != null)
        {
            setSpeedX(INIT_VEL * Math.cos(getAngle(direction)) + itemOwner.getSpeedX());
            setSpeedY(INIT_VEL * Math.sin(getAngle(direction)) + itemOwner.getSpeedY());
        }
        else
        {
            setSpeedX(INIT_VEL * Math.cos(getAngle(direction)));
            setSpeedY(INIT_VEL * Math.sin(getAngle(direction)));
        }

        active = true;
    }


    public void setPosition()
    {
        if(itemOwner != null) {
            switch (itemOwner.getFiringDirection())
            {
                case UP:
                    setPosition(itemOwner.getGlobalCentrePoint().x, itemOwner.getGlobalCentrePoint().y - 30);
                    break;
                case DOWN:
                    setPosition(itemOwner.getGlobalCentrePoint().x, itemOwner.getGlobalCentrePoint().y + 30);
                    break;
                case LEFT:
                    setPosition(itemOwner.getGlobalCentrePoint().x - 30, itemOwner.getGlobalCentrePoint().y);
                    break;
                case RIGHT:
                    setPosition(itemOwner.getGlobalCentrePoint().x + 30, itemOwner.getGlobalCentrePoint().y);
                    break;
            }
        }
    }

    @Override
    public void boundaryHit()
    {
        if(hasHitEdge() != null)
        {
            detonate();
        }
    }

    public int getID()
    {
        return id;
    }

    public boolean getActive()
    {
        return active;
    }

    @Override
    public Player getItemOwner()
    {
        return itemOwner;
    }

    public void setItemOwner(Player itemOwner)
    {
        this.itemOwner = itemOwner;
        setTexture(AllTextureFiles.getItemTexture(itemOwner), true);
        setOrigin(getLocalBounds().width/2, getLocalBounds().height/2);
    }

    public void lead(MovingBody collideWith) { }

    public void follow(MovingBody collideWith) { }

    protected void setInitVel(int initVel)
    {
       INIT_VEL = initVel;
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
    public void applyExplosionEffects(AbstractPlayer playerAfflicted, boolean firstHit)
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

    public abstract Item clone();
}