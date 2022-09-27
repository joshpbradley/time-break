import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import java.nio.file.Paths;

public class Drone extends AbstractPlayer implements Item
{
    private long timeActivated;
    private Player itemOwner;
    private static Texture texture;
    private final Propeller[] propellerArr = new Propeller[4];
    public boolean activated = false;
    //public static final float initialScaling = GameScene.getGameScene().getMap().mapTiles[0][0].getGlobalBounds().width/50;
    public static final float initialScaling = 0.9f;

    public Drone(GameScene.Team t)
    {
        super(new DroneCharacter(), t);


        getWeapon().setPosition(getGlobalCentrePoint());
        setOnGround(false);

        setScale(initialScaling, initialScaling);
        getWeapon().setScale(getScale().x * 2, getScale().y * 2);
    }

    @Override
    public int getID()
    {
        return 5;
    }

    @Override
    public Player getItemOwner() { return itemOwner; }

    @Override
    public void setItemOwner(Player itemOwner)
    {
        this.itemOwner = itemOwner;
        texture = AllTextureFiles.getItemTexture(itemOwner);
    }

    @Override
    public void use(Controller.Direction direction)
    {
        // movement controls here, ensure the player stops moving while the drone is active
    }

    public void use()
    {
        if(!activated)
        {
            timeActivated = System.currentTimeMillis();

            Texture droneGunTexture = new Texture();
            getWeapon().setTexture(AllTextureFiles.getTransparentTexture());

            setTexture(texture, true);
            setOrigin(getLocalBounds().left + getLocalBounds().width/2, getLocalBounds().top + getLocalBounds().height/2);
            setPosition(itemOwner.getGlobalCentrePoint());
            GameScene.getGameScene().add(this);

            itemOwner.setMode(true);
            itemOwner.setCanChangeMode(false);

            // These statements force the player to become idle and take a central stance
            activated = true;
            itemOwner.setSpeedX(0);
            itemOwner.setSpeedY(0);
            itemOwner.setSpriteTextures(new boolean[] { false, false, false, false, false, false, false, false, false, false });

            initialisePropellers();

            addController(itemOwner.getController());
            getController().setBody(this);
        }
    }

    private void initialisePropellers()
    {
        for(int i = 0; i < 4; ++i)
        {
            propellerArr[i] = new Propeller(this, i);
            new Thread(propellerArr[i]).start();
        }
    }

    @Override
    public void collision(CollisionItem collideWith)
    {
        collideWith.collision(this);
    }

    @Override
    public void update()
    {
        super.update();

        if(activated && (timeActivated + 5000 < System.currentTimeMillis()) || getHitPoints() <= 0)
        {
            removeDrone();
        }
    }

    @Override
    public void setSpriteTextures(boolean[] keyboard)
    {
        weapon.setPosition(getGlobalCentrePoint());

        boolean[] keyboardMovementCopy = new boolean[4];
        System.arraycopy(keyboard, 0, keyboardMovementCopy, 0, keyboardMovementCopy.length);

        //If opposite keys are pressed, their effects are nullified
        if(keyboardMovementCopy[0] && keyboardMovementCopy[1])
        {
            keyboardMovementCopy[0] = false;
            keyboardMovementCopy[1] = false;
        }

        if(keyboardMovementCopy[2] && keyboardMovementCopy[3])
        {
            keyboardMovementCopy[2] = false;
            keyboardMovementCopy[3] = false;
        }

        int facingKeysPressed = 0;
        for(int i = 0; i < 4; i++)
        {
            if(keyboardMovementCopy[i])
            {
                ++facingKeysPressed;
            }
        }

        if(facingKeysPressed == 1)
        {
            if(keyboardMovementCopy[0])
            {
                facingDirection = Controller.Direction.UP;
            }
            else if(keyboardMovementCopy[1])
            {
                facingDirection = Controller.Direction.DOWN;
            }
            else if(keyboardMovementCopy[2])
            {
                facingDirection = Controller.Direction.LEFT;
            }
            else
            {
                facingDirection = Controller.Direction.RIGHT;
            }
        }
        else
        {
            facingDirection = firingDirection;
        }

        boolean[] keyboardFiringCopy = new boolean[4];
        System.arraycopy(keyboard, 4, keyboardFiringCopy, 0, keyboardFiringCopy.length);

        int firingKeysPressed = 0;
        for(int i = 0; i < 4; ++i)
        {
            if(keyboardFiringCopy[i])
            {
                ++firingKeysPressed;
            }
        }
        if(firingKeysPressed > 1)
        {
            firingDirection = facingDirection;
        }
        else if(firingKeysPressed == 1)
        {
            if(keyboardFiringCopy[0])
            {
                firingDirection = Controller.Direction.UP;
            }
            else if (keyboardFiringCopy[1])
            {
                firingDirection = Controller.Direction.DOWN;
            }
            else if (keyboardFiringCopy[2])
            {
                firingDirection = Controller.Direction.LEFT;
            }
            else
            {
                firingDirection = Controller.Direction.RIGHT;
            }
        }
        else
        {
            firingDirection = facingDirection;
        }

        switch(firingDirection)
        {
            case UP:
                setRotation(0);
                break;
            case DOWN:
                setRotation(180);
                break;
            case LEFT:
                setRotation(270);
                break;
            case RIGHT:
                setRotation(90);
        }
    }

    @Override
    public void lead(MovingBody collideWith){}

    @Override
    public void follow(MovingBody collideWith){}

    public boolean getActivated()
    {
        return activated;
    }

    @Override
    public void takeDamage(int damage)
    {
        hitPoints -= damage;
    }

    public void fire(Controller.Direction direction)
    {
        weapon.fire(direction);
    }

    public void removeDrone()
    {
        GameScene.getGameScene().remove(this);
        getController().setBody(getItemOwner());
        for(int i = 0; i < 4; ++i)
        {
            GameScene.getGameScene().remove(propellerArr[i]);
        }
        itemOwner.setCanChangeMode(false);
        itemOwner.setMode(true);
        itemOwner.setItem(null);
    }

    @Override
    public Sprite getItemSprite()
    {
        Texture t = new Texture();
        try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Items/Item-Models/Drone.png")); }catch(Exception e){ e.printStackTrace(); }
        Sprite s = new Sprite(t);
        s.setScale(2f, 2f);
        s.setOrigin(  s.getLocalBounds().width/2,  s.getLocalBounds().height/2);
        return s;
    }

    @Override
    public Item clone() {
        return new Drone(getTeam());
    }
}
