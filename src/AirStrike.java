import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import java.nio.file.Paths;

/**
 * Used to represent the AirStrike weapon in the game, it creates 2 extra objects, laser and target
 * both of which are used as the names imply.
 */

public class AirStrike extends Weapon
{
    private Laser laser;
    private Target target;
    private boolean laserIn = false;
    private boolean targetIn = false;
    private GameScene scene = GameScene.getGameScene();


    public AirStrike()
    {
        super(0,1,90);
        resetSpriteTexture();
    }

    /**
     * Because AirStrike doesn't really 'shoot' but instead looks for targets in the aiming direction fire works a bit
     * differently from regular shooting weapons. Using the laser object in order to indicate where it's 'looking'.
     * Once the opponent is located a target is spawned on them.
     * @param direction the direction to activate the weapon in.
     */
    public void fire(Controller.Direction direction)
    {
        if(laser == null) //Creating the laser object
        {
            laser = new Laser(this.getWeaponOwner(), this);
            laser.setTexture(AllTextureFiles.getAmmoTexture(AllTextureFiles.AmmoType.LASER));
        }

        if(target == null) //Creating the target object
        {
            target = new Target(this);
            target.setTexture(AllTextureFiles.getAmmoTexture(AllTextureFiles.AmmoType.TARGET));
            target.scale((float) 0.1, (float) 0.1);
        }

        Player self = (Player)(this.getWeaponOwner());
        Vector2f selfPosition = self.getGlobalCentrePoint();
        Vector2f opponentPosition = scene.getOtherPlayer(self).getGlobalCentrePoint();

        if (!laserIn) //Checks if laser is already being drawn
        {
            scene.add(laser);
            laserIn = true;
        }

        if(direction == Controller.Direction.UP)
        {
            if (opponentPosition.y < selfPosition.y && opponentPosition.x < selfPosition.x + 25 && opponentPosition.x > selfPosition.x - 25)
            {
                laser.setScale(new Vector2f(5, opponentPosition.y - selfPosition.y));
                laser.setPosition(selfPosition);
                hit(scene.getOtherPlayer(self));
            }
            else
            {
                laser.setScale(new Vector2f(5, -selfPosition.y));
                laser.setPosition(selfPosition);
            }
        }
        else if(direction == Controller.Direction.DOWN)
        {
            if (opponentPosition.y > selfPosition.y && opponentPosition.x < selfPosition.x + 25 && opponentPosition.x > selfPosition.x - 25)
            {
                laser.setScale(new Vector2f(5, -(selfPosition.y - opponentPosition.y)));
                laser.setPosition(selfPosition);
                hit(scene.getOtherPlayer(self));
            }
            else
            {
                laser.setScale(new Vector2f(5, scene.getView().getSize().y - selfPosition.y));
                laser.setPosition(selfPosition);
            }
        }
        else if(direction == Controller.Direction.LEFT)
        {
            if (opponentPosition.x < selfPosition.x && opponentPosition.y < selfPosition.y + 70 && opponentPosition.y > selfPosition.y - 50)
            {
                laser.setScale(new Vector2f(-(selfPosition.x - opponentPosition.x), 5));
                laser.setPosition(selfPosition);
                hit(scene.getOtherPlayer(self));
            }
            else
            {
                laser.setScale(new Vector2f(-selfPosition.x, 5));
                laser.setPosition(selfPosition);
            }
        }
        else
        {
            if (opponentPosition.x > selfPosition.x && opponentPosition.y < selfPosition.y + 70 && opponentPosition.y > selfPosition.y - 50)
            {
                laser.setScale(new Vector2f(opponentPosition.x - selfPosition.x, 5));
                laser.setPosition(selfPosition);
                hit(scene.getOtherPlayer(self));
            }
            else
            {
                laser.setScale(new Vector2f(scene.getView().getSize().x - selfPosition.x, 5));
                laser.setPosition(selfPosition);
            }
        }

    }

    public void setLaserIn(boolean not) {
        laserIn = not;
    }

    public void setTargetIn(boolean not) {
        targetIn = not;
    }

    public boolean getTargetIn() {
        return targetIn;
    }

    /**
     * Method for handling the passing of the target to GameScene to draw and playing the sound effects
     * @param opponent The player on which the target will be drawn.
     */
    private void hit(Player opponent)
    {
        if(!targetIn)
        {
            AudioHandler.playSound("jet");
            targetIn = true;
            target.setPosition(opponent.getGlobalCentrePoint().x - target.getGlobalBounds().width/2, opponent.getGlobalCentrePoint().y - target.getGlobalBounds().height/2);
            scene.add(target);
            target.setTimeCreated(System.currentTimeMillis());
            getWeaponOwner().setWeapon(getWeaponOwner().getCharacter().getDefaultWeapon());
        }
    }

    @Override
    public void resetSpriteTexture()
    {
        Texture t = new Texture();

        if(System.currentTimeMillis() % 2 == 0)
        {
            try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Airstrike-Left.png")); }catch(Exception e){ e.printStackTrace(); }
        }
        else
        {
            try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Airstrike-Right.png")); }catch(Exception e){ e.printStackTrace(); }
        }

        setTexture(t, true);
        setOrigin(getGlobalBounds().width/2, getGlobalBounds().height/2);
    }
}
