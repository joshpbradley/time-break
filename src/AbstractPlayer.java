import org.jsfml.graphics.RenderWindow;

public abstract class AbstractPlayer extends MovingBody
{
    protected int hitPoints;
    protected long lastWeaponDisposed = 0;
    protected Controller.Direction facingDirection = Controller.Direction.DOWN;
    protected Controller.Direction firingDirection = Controller.Direction.DOWN;
    protected final Character character;
    protected Weapon weapon;
    protected StatusEffect[] activeEffects = new StatusEffect[6];

    public AbstractPlayer(Character c, GameScene.Team tm)
    {
        super(AllTextureFiles.getStartingTexture(c), tm);

        setWeapon(c.getDefaultWeapon());
        character = c;
        hitPoints = c.getHitPoints();
        setMaxSpeed((getMaxSpeed() - 3.5) * c.getSpeedMultiplier());
    }

    public void setWeapon(Weapon weapon)
    {
        this.weapon = weapon;
        weapon.setWeaponOwner(this);
        lastWeaponDisposed = System.currentTimeMillis();
    }

    public void draw(RenderWindow window)
    {
        window.draw(this);
    }

    @Override
    public void boundaryHit()
    {
        Controller.Direction edgeHit = hasHitEdge();

        if(edgeHit == null)
        {
            return;
        }

        unclipGameBorders(edgeHit);

        if(edgeHit.equals(Controller.Direction.UP) || edgeHit.equals(Controller.Direction.DOWN))
        {
            setSpeedY(0);
        }
        else
        {
            setSpeedX(0);
        }
    }

    @Override
    public void update()
    {
        boundaryHit();
    }

    abstract void takeDamage(int damage);

    public int getHitPoints()
    {
        return hitPoints;
    }

    public Character getCharacter()
    {
        return character;
    }

    public Weapon getWeapon()
    {
        return weapon;
    }

    public Controller.Direction getFacingDirection()
    {
        return facingDirection;
    }

    public Controller.Direction getFiringDirection()
    {
        return firingDirection;
    }

    public StatusEffect[] getActiveEffects()
    {
        return activeEffects;
    }

    abstract void setSpriteTextures(boolean keyboard[]);

    abstract void fire(Controller.Direction direction);

    public synchronized void disposeEffect(StatusEffect effect)
    {
        int i;
        boolean found = true;

        for(i = 0; found; i++)
        {
            if (activeEffects[i] != null && activeEffects[i].equals(effect))
            {
                activeEffects[i] = null;
                found = false;
            }
        }

        for(;i < activeEffects.length && activeEffects[i] == null; i++)
        {
            activeEffects[i-1] = activeEffects[i];
        }
    }

    public synchronized void addStatusEffect(StatusEffect effect)
    {
        for(int i = 0; i < activeEffects.length; i++)
        {
            if(activeEffects[i] == null)
            {
                activeEffects[i] = effect;
                return;
            }
            else if (activeEffects[i].getClass().equals(effect.getClass()))
            {
                activeEffects[i].replace(effect);
                return;
            }
        }
    }

    public synchronized void applyStatusEffects()
    {
        for(int i = 0; i < activeEffects.length; i++)
        {
            if(activeEffects[i] != null)
            {
                if(activeEffects[i].checkIfEnded(this))
                {
                    disposeEffect(activeEffects[i]);
                }
                else
                {
                    activeEffects[i].apply(this);
                }
            }
        }
    }
}
