import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import java.nio.file.Paths;

public class Propeller extends Sprite implements Runnable, CollisionItem
{
    private final Drone drone;
    private static final Texture texture = new Texture();
    private final int propellerNumber;
    private final int rotateAmount;

    public Propeller(Drone drone, int propellerNumber)
    {
        this.drone = drone;
        this.propellerNumber = propellerNumber;

        try{ texture.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Items/Propeller.png")); }catch(Exception e){ e.printStackTrace(); }
        setTexture(texture);
        setOrigin(getLocalBounds().width/2, getLocalBounds().height/2);
        setScale(drone.getScale().x, drone.getScale().y);
        setPosition();

        switch(propellerNumber)
        {
            case 0:
            case 2:
                rotateAmount = 10;
                break;
            default:
                rotateAmount = -10;
        }
        GameScene.getGameScene().add(this);
    }

    @Override
    public void run()
    {
        while(true)
        {
            setPosition();
            rotate(rotateAmount);
            try{Thread.sleep(10);}catch(Exception e){e.printStackTrace();}
        }
    }

    public void setPosition()
    {
        switch(propellerNumber)
        {
            case 0:
                setPosition(drone.getGlobalBounds().left + (13f/50f) * drone.getGlobalBounds().width,
                        drone.getGlobalBounds().top + (13f/50f) * drone.getGlobalBounds().height);
                break;
            case 1:
                setPosition((drone.getGlobalBounds().left + drone.getGlobalBounds().width) - ((13f/50f) * drone.getGlobalBounds().width),
                        drone.getGlobalBounds().top + (13f/50f) * drone.getGlobalBounds().height);
                break;
            case 2:
                setPosition(drone.getGlobalBounds().left + (13f/50f) * drone.getGlobalBounds().width,
                        (drone.getGlobalBounds().top + drone.getGlobalBounds().height) - ((13f/50f) * drone.getGlobalBounds().height));
                break;
            case 3:
                setPosition((drone.getGlobalBounds().left + drone.getGlobalBounds().width) - (13f/50f) * drone.getGlobalBounds().width,
                        (drone.getGlobalBounds().top + drone.getGlobalBounds().height) - ((13f/50f) * drone.getGlobalBounds().height));
                break;
        }
    }

    @Override
    public void collision(CollisionItem collideWith){}

    @Override
    public void update(){}

    @Override
    public GameScene.Team getTeam()
    {
        return drone.getTeam();
    }
}
