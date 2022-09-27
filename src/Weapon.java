import org.jsfml.graphics.Sprite;
import java.util.Random;

/**
 * Represents an abstract weapon, used by an instance of AbstractPlayer to (ideally) reduce their opponents hit points.
 */
public abstract class Weapon extends Sprite implements DropItem
{
    private int fireRate;
    private int damage;
    private final int ID;
    private AbstractPlayer weaponOwner = null;

    /**
     * Constructor for the weapon class.
     *
     * @param ID the ID of the Weapon
     * @param fireRate the minimum number of milliseconds that must pass between weapon activations
     * @param damage the damage that is dealt by a single projectile/use of this weapon
     */
    public Weapon(int ID, int fireRate, int damage)
    {
        this.ID = ID;
        this.fireRate = fireRate;
        this.damage = damage;
    }

    /**
     * Returns the weapon's ID, used to get the correct texture from the AllTextureFiles class.
     * @return
     */
    public int getID()
    {
        return ID;
    }

    /**
     * Sets the position of the Weapon Sprite relative to the Player Sprite.
     *
     */
    public void setPosition()
    {
        if(weaponOwner == null)
        {
            return;
        }

        setOrigin(getLocalBounds().left + getLocalBounds().width/2, getLocalBounds().top + getLocalBounds().height/2);

        if(!(weaponOwner instanceof Player) || !((Player)weaponOwner).getIdle())
        {
            switch (weaponOwner.getFiringDirection())
            {
                case UP:
                    setPosition(weaponOwner.getGlobalCentrePoint().x, weaponOwner.getGlobalCentrePoint().y - 30);
                    break;
                case DOWN:
                    // Differentiates between pistol-type weapons and the others.
                    if(weaponOwner.getFiringDirection() == Controller.Direction.DOWN && (weaponOwner.getWeapon().getID() != 5 && weaponOwner.getWeapon().getID() != 6))
                    {
                        setPosition(weaponOwner.getGlobalCentrePoint().x, weaponOwner.getGlobalCentrePoint().y + 10);
                    }
                    else
                    {
                        setPosition(weaponOwner.getGlobalCentrePoint().x, weaponOwner.getGlobalCentrePoint().y + 5);
                    }
                    break;
                case LEFT:
                    setPosition(weaponOwner.getGlobalCentrePoint().x - 10, weaponOwner.getGlobalCentrePoint().y);
                    break;
                case RIGHT:
                    setPosition(weaponOwner.getGlobalCentrePoint().x + 10, weaponOwner.getGlobalCentrePoint().y);
                    break;
            }
        }
    }

    /**
     * Sets the AbstractPlayer object that owns the Weapon.
     * @param weaponOwner
     */
    public void setWeaponOwner(AbstractPlayer weaponOwner)
    {
        if(weaponOwner != null)
        {
            this.weaponOwner = weaponOwner;
            setPosition(weaponOwner.getGlobalCentrePoint().x, weaponOwner.getGlobalCentrePoint().y);
            setScale(weaponOwner.getScale().x * 1.25f, weaponOwner.getScale().y * 1.25f);
        }
    }

    /**
     * Gets the AbstractPlayer that owns the weapon instance.
     * @return
     */
    public AbstractPlayer getWeaponOwner()
    {
        return weaponOwner;
    }

    /**
     * Activates the weapon in a specified direction.
     * @param direction the direction to activate the weapon in
     */
    abstract void fire(Controller.Direction direction);

    /**
     * Resets the Sprite of a weapon to the model Sprites for that weapon.
     * Since each subclass has differing Sprites to return, it is abstract
     */
    abstract void resetSpriteTexture();

    /**
     * Returns a randomised instance of one of the subclasses of Weapon.
     * @return an instance of a subclass of Weapon
     */
    public static final Weapon getRandomWeapon()
    {
        Random r = new Random();

        switch(r.nextInt(12))
        {
            case 0:
                return new AirStrike();
            case 1:
                return new Bow();
            case 2:
                return new FullAuto();
            case 3:
                return new GhostGun();
            case 4:
                return new GrenadeLauncher();
            case 7:
                return new RocketLauncher();
            case 8:
                return new Shotgun();
            case 9:
                return new SniperRifle();
            case 10:
                return new Sword();
            case 11:
                return new WaterThrower();
            default:
                // Ensures that a new weapon will be selected if a default weapon ID has been selected as the random number
                return getRandomWeapon();
        }
    }

    public int getDamage()
    {
        return damage;
    }

    public int getFireRate()
    {
        return fireRate;
    }
}

