import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import java.util.Random;

public abstract class Gun extends Weapon
{
    private AllTextureFiles.AmmoType[] ammoType;
    private int ammo;
    private float projSpeed;
    private int projAmount;
    private long lastFired = 0;
    // The spread interval for projectiles if multiple are fired simultaneously
    private float deviation = 0;

    public Gun(int ID, int fireRate, int damage, int ammo, float projSpeed, int projAmount, AllTextureFiles.AmmoType ammoType)
    {
        super(ID, fireRate, damage);

        this.ammo = ammo;
        this.ammoType = new AllTextureFiles.AmmoType[] {ammoType};
        this.projSpeed = projSpeed;
        this.projAmount = projAmount;
    }

    public Gun(int ID, int fireRate, int damage, int ammo, float projSpeed, int projAmount, AllTextureFiles.AmmoType[] ammoType)
    {
        super(ID, fireRate, damage);

        this.ammo = ammo;
        this.ammoType = ammoType;
        this.projSpeed = projSpeed;
        this.projAmount = projAmount;
    }

    private synchronized void spawnProjectile(Controller.Direction direction, double mobilityCarry, GameScene.Team team, float deviation)
    {
        AudioHandler.playSound("shot");

        Projectile fired;

        Texture ammoTexture = ammoType.length == 1 ? AllTextureFiles.getAmmoTexture(ammoType[0]) :
        AllTextureFiles.getAmmoTexture(ammoType[new Random().nextInt(ammoType.length)]);

        Vector2f holdOrigin = getOrigin();
        setOrigin(getLocalBounds().width/2, getLocalBounds().height/2);

        float speed = projSpeed;


        if(getWeaponOwner() instanceof Player && ((Player) getWeaponOwner()).getLastWeaponUsedTexture() != null)
        {
            if(this.equals(getWeaponOwner().getCharacter().getDefaultWeapon()))
            {
                speed *= 0.75;
                ammoTexture = ((Player) getWeaponOwner()).getLastWeaponUsedTexture();
            }
            ((Player) getWeaponOwner()).setLastWeaponUsedTexture(null);
        }

        if(direction == Controller.Direction.LEFT)
        {
            if(makeExplosiveProjectile())
            {
                fired = new ExplodingProjectile(ammoTexture, -speed, mobilityCarry + deviation, getDamage(), getWeaponOwner());
            }
            else
            {
                fired = new Projectile(ammoTexture, -speed, mobilityCarry + deviation, getDamage(), team);
            }

            fired.setOrigin(fired.getLocalBounds().width, fired.getLocalBounds().height/2);
            fired.setRotation(180);

            fired.setPosition(getPosition().x - getGlobalBounds().width/2, getPosition().y);
        }
        else if(direction == Controller.Direction.UP)
        {
            if(makeExplosiveProjectile())
            {
                fired = new ExplodingProjectile(ammoTexture, mobilityCarry + deviation, -speed, getDamage(), getWeaponOwner());
            }
            else
            {
                fired = new Projectile(ammoTexture, mobilityCarry + deviation, -speed, getDamage(), team);
            }

            fired.setOrigin(fired.getLocalBounds().width/2, fired.getLocalBounds().height);
            fired.setRotation(270);
            fired.setPosition(getPosition().x, getPosition().y  - getGlobalBounds().height/2);
        }
        else if(direction == Controller.Direction.RIGHT)
        {
            if(makeExplosiveProjectile())
            {
                fired = new ExplodingProjectile(ammoTexture, speed, mobilityCarry + deviation, getDamage(), getWeaponOwner());
            }
            else
            {
                fired = new Projectile(ammoTexture, speed, mobilityCarry + deviation, getDamage(), team);
            }

            fired.setOrigin(fired.getLocalBounds().left, fired.getLocalBounds().height/2);
            fired.setPosition(getPosition().x + getGlobalBounds().width/2, getPosition().y);
        }
        else
        {
            if(makeExplosiveProjectile())
            {
                fired = new ExplodingProjectile(ammoTexture, mobilityCarry + deviation, speed, getDamage(), getWeaponOwner());
            }
            else
            {
                fired = new Projectile(ammoTexture, mobilityCarry + deviation, speed, getDamage(), team);
            }

            fired.setOrigin(fired.getLocalBounds().width/2, fired.getLocalBounds().top);
            fired.setRotation(90);
            fired.setPosition(getPosition().x, getPosition().y + getGlobalBounds().height/2);
        }

        if(speed != projSpeed)
        {
            fired.setIsWeaponTexture();
        }

        if(getWeaponOwner() instanceof Drone)
        {
            fired.setPosition(getWeaponOwner().getGlobalCentrePoint());
            fired.setOnGround(false);
        }

        if(ammoType[0].equals(AllTextureFiles.AmmoType.GHOST))
        {
            fired.setOnGround(false);
        }

        fired.setScale(getScale().x, getScale().y);
        setOrigin(holdOrigin);

        GameScene.getGameScene().add(fired);
    }

    public int getAmmo()
    {
        return ammo;
    }

    protected void reduceAmmo()
    {
        if(--ammo == 0)
        {
            ((Player)getWeaponOwner()).setLastWeaponUsedTexture(AllTextureFiles.getWeaponModelTexture((Player)getWeaponOwner()));
            getWeaponOwner().setWeapon(getWeaponOwner().getCharacter().getDefaultWeapon());
        }
    }

    public int getProjAmount()
    {
        return projAmount;
    }

    public void setDeviation(float deviation) { this.deviation = deviation; }

    public float getDeviation()
    {
        return deviation;
    }

    @Override
    public void fire(Controller.Direction direction)
    {
        boolean[] keyboardFiringCopy = new boolean[4];
        System.arraycopy(getWeaponOwner().getController().getKeyboard(), 4, keyboardFiringCopy, 0, keyboardFiringCopy.length);

        int firingKeysPressed = 0;
        for(int i = 0; i < 4; ++i)
        {
            if(keyboardFiringCopy[i])
            {
                ++firingKeysPressed;
            }
        }

        if(System.currentTimeMillis() - lastFired < getFireRate() || firingKeysPressed != 1)
        {
            return;
        }

        setPosition();
        float initialDeviation = (-(float) (getProjAmount() - 1) / 2f * getDeviation());

        if (direction == Controller.Direction.LEFT || direction == Controller.Direction.RIGHT)
        {
            for (int i = 0; i < getProjAmount(); ++i)
            {
                spawnProjectile(direction, getWeaponOwner().getSpeedY(), getWeaponOwner().getTeam(), initialDeviation + getDeviation() * i);
            }
        }
        else
        {
            for (int i = 0; i < getProjAmount(); ++i)
            {
                spawnProjectile(direction, getWeaponOwner().getSpeedX(), getWeaponOwner().getTeam(), initialDeviation + getDeviation() * i);
            }
        }
        lastFired = System.currentTimeMillis();
        reduceAmmo();
    }

    protected abstract boolean makeExplosiveProjectile();
}
