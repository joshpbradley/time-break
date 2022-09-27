import org.jsfml.graphics.Sprite;

/**
 * This class is used to represent a laser for the AirStrike.
 */

public class Laser extends Sprite implements CollisionItem
{
    private boolean[] ownerKeyboard;
    private AirStrike parent;

    /**
     * Creates a new laser object with the owner of AirStrike and the AirStrike itself as parameters.
     * @param owner The holder of the air strike weapon.
     * @param parent The AirStrike class that Laser belongs to.
     */

    public Laser(AbstractPlayer owner, AirStrike parent)
    {
        this.parent = parent;
        Controller ownerController = owner.getController();
        ownerKeyboard = ownerController.getKeyboard();
    }

    @Override
    public void collision(CollisionItem collideWith) { }

    /**
     * Updates the drawing of the laser, if no shooting keys are being pressed, or if the target has been drawn
     * the laser is removed.
     */
    @Override
    public void update()
    {
        if(!(ownerKeyboard[4] || ownerKeyboard[5] || ownerKeyboard[6] || ownerKeyboard[7]) || parent.getTargetIn())
        {
            GameScene.getGameScene().remove(this);
            parent.setLaserIn(false);
        }
    }

    /**
     * Returns the team that the laser belongs when calculating collisions.
     * @return The laser will never have collisions so the team returned will always be neutral.
     */
    @Override
    public GameScene.Team getTeam() {
        return GameScene.Team.Neutral;
    }
}
