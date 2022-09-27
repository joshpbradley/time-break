import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

/**
 * The MovingBody handles any object movement. It is given a sprite upon construction as the object and then manipulates the position.
 * This class will run a method on a separate thread that continuously updates the velocity figures and position of an object, causing a "Drifting slowdown" effect when a player lets go of a key.
 *
 * Plans for this class include allowing for an
 */
public abstract class MovingBody extends Sprite implements Runnable, CollisionItem
{
    private double speedX = 0;
    private double speedY = 0;
    private double maxSpeed = 5;
    private double friction = 0.95;
    private double multiplier = 1;
    private long lastPushed;
    private boolean isOnGround = true;

    private final GameScene.Team team;
    private Controller controller;
    private Thread updateThread;

    /**
     * Constructs a new MovingBody
     */
    public MovingBody(Texture tx, GameScene.Team tm)
    {
        super(tx);
        updateThread = new Thread(this);
        updateThread.start();

        team = tm;
        lastPushed = System.currentTimeMillis();
    }

    /**
     * Adjusts the velocity in the X direction
     *
     * @param adjust How much the velocity should be changed by
     */
    public void tweakX(double adjust)
    {
        if (speedX < maxSpeed && speedX > -maxSpeed)
            speedX += adjust;

        if (speedX > maxSpeed)
            speedX = maxSpeed;
        else if (speedX < -maxSpeed)
            speedX = -maxSpeed;
    }

    /**
     * Adjusts the velocity in the Y direction
     *
     * @param adjust How much the velocity should be changed by
     */
    public void tweakY(double adjust)
    {
        if (speedY < maxSpeed && speedY > -maxSpeed)
            speedY += adjust;

        if (speedY > maxSpeed)
            speedY = maxSpeed;
        else if (speedY < -maxSpeed)
            speedY = -maxSpeed;
    }

    public void setSpeedX(double spX) {
        speedX = spX;
    }

    public void setSpeedY(double spY) {
        speedY = spY;
    }

    public void setFriction(double frict) {
        friction = frict;
    }

    public void setMaxSpeed(double speed) {
        maxSpeed = speed;
    }

    public void addToMultiplier(double add) {
        multiplier += add;
    }

    public void removeFromMultiplier(double remove) {
        multiplier -= remove;
    }

    /**
     * Continuously updates the object's position and simulates loss of momentum while doing so. This is called by a thread and should not be added to a new thread outside of this class or run manually.
     */
    public void run()
    {
        while(true)
        {
            this.move((float) (speedX * multiplier), (float) (speedY * multiplier));
            this.move((float) (speedX * multiplier), (float) (speedY * multiplier));

            if (speedX < 0.5 && speedX > -0.5)
                speedX = 0;
            else
                speedX *= friction;

            if (speedY < 0.5 && speedY > -0.5)
                speedY = 0;
            else
                speedY *= friction;

            try
            {
                Thread.sleep(10);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void collision(CollisionItem collideWith)
    {
        if(!(collideWith instanceof MovingBody))
        {
            collideWith.collision(this);
            return;
        }

        MovingBody collideBody = (MovingBody) collideWith;

        int lead = 0;
        lead += Math.sqrt(Math.pow(getSpeedX(), 2)) - Math.sqrt(Math.pow(collideBody.getSpeedX(), 2));
        lead += Math.sqrt(Math.pow(getSpeedX(), 2)) - Math.sqrt(Math.pow(collideBody.getSpeedX(), 2));

        if(lead > 0)
        {
            collideBody.follow(this);
            lead(collideBody);
        }
        else if(lead < 1 && lead > -1)
        {
            FrozenBody clone = collideBody.snapshot();
            collideBody.follow(this);
            follow(clone);
        }
        else
        {
            follow(collideBody);
            collideBody.lead(this);
        }
    }

    public final boolean isOnGround()
    {
        return isOnGround;
    }

    public void setOnGround(boolean b)
    {
        isOnGround = b;
    }

    public boolean getOnGround()
    {
        return isOnGround;
    }

    //Accessors
    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public double getFriction() {return friction;}

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public long getLastPushed() {
        return lastPushed;
    }

    public GameScene.Team getTeam()
    {
        return team;
    }

    public abstract void lead(MovingBody collideWith);

    public abstract void follow(MovingBody collideWith);

    public void update() {/*Updates manually through a thread*/}

    public void addController(Controller ctrl)
    {
        controller = ctrl;
    }

    public Controller getController()
    {
        return controller;
    }

    public FrozenBody snapshot()
    {
        return new FrozenBody(speedX, speedY, this);
    }

    public void lockout(int milliseconds)
    {
        if (controller != null)
        {
            controller.lockoutFor(milliseconds);
        }
        lastPushed = System.currentTimeMillis();
    }

    public Controller.Direction hasHitEdge()
    {
        if(getGlobalBounds().top < 0)
        {
            return Controller.Direction.UP;
        }

        if(getGlobalBounds().top + getGlobalBounds().height > GameScene.getGameScene().getView().getSize().y)
        {
            return Controller.Direction.DOWN;
        }

        if(getGlobalBounds().left < (GameScene.getGameScene().getLeftBorder().getGlobalBounds().left + GameScene.getGameScene().getLeftBorder().getGlobalBounds().width))
        {
            return Controller.Direction.LEFT;
        }

        if((getGlobalBounds().left + getGlobalBounds().width) > GameScene.getGameScene().getRightBorder().getGlobalBounds().left)
        {
            return Controller.Direction.RIGHT;
        }

        return null;
    }

    public abstract void boundaryHit();

    protected void unclipGameBorders(Controller.Direction edgeHit)
    {
        if(edgeHit.equals(Controller.Direction.UP))
        {
            move(0, -(getGlobalBounds().top));
        }
        else if(edgeHit.equals(Controller.Direction.DOWN))
        {
            move(0, GameScene.getGameScene().getView().getSize().y - (getGlobalBounds().top + getGlobalBounds().height));
        }
        else if(edgeHit.equals(Controller.Direction.LEFT))
        {
            move((GameScene.getGameScene().getLeftBorder().getGlobalBounds().left + GameScene.getGameScene().getLeftBorder().getGlobalBounds().width) - getGlobalBounds().left, 0);
        }
        else if(edgeHit.equals(Controller.Direction.RIGHT))
        {
            move(-((getGlobalBounds().left + getGlobalBounds().width) - GameScene.getGameScene().getRightBorder().getGlobalBounds().left), 0);
        }
    }

    public Vector2f getGlobalCentrePoint()
    {
        return new Vector2f(getGlobalBounds().left + getGlobalBounds().width/2, getGlobalBounds().top + getGlobalBounds().height/2);
    }

    public class FrozenBody extends MovingBody
    {
        MovingBody originBody;

        public FrozenBody(double spX, double spY, MovingBody origin)
        {
            super(new Texture(), origin.getTeam());
            setSpeedX(spX);
            setSpeedY(spY);
            originBody = origin;
        }

        public void lead(MovingBody collideWith) {
            originBody.follow(collideWith);
        }

        public void follow(MovingBody collideWith) { originBody.follow(collideWith); }

        public MovingBody getOriginal() {
            return originBody;
        }

        @Override
        public void boundaryHit() {}
    }
}