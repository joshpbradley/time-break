import org.jsfml.graphics.Sprite;

/**
 * This class handles the representing of where the air strike is going to happen.
 */

public class Target extends Sprite implements CollisionItem, Explodable
{
    private AirStrike parent;
    private long timeCreated = 0;

    private final int AOE_SIZE = 10;
    private final int TIME_TILL_STRIKE = 2;

    /**
     * Creates a new target object with the AirStrike object that it belongs to.
     * @param parent the AirStrike class that the target belongs to.
     */

    public Target(AirStrike parent) {
        this.parent = parent;
    }

    @Override
    public void collision(CollisionItem collideWith) { }

    /**
     * Checks if 3 seconds have passed since the target has been created and if so removes the target from the drawing
     * list and creates a new explosion where the target was.
     */
    @Override
    public void update()
    {
        if (System.currentTimeMillis() > timeCreated + (TIME_TILL_STRIKE * 1000))
        {
            GameScene.getGameScene().remove(this);
            parent.setTargetIn(false);
            GameScene.getGameScene().add(new Explosion(this, 1000, AOE_SIZE, (int) (this.getPosition().x + this.getGlobalBounds().width/2), (int) (this.getPosition().y + this.getGlobalBounds().height/2)));
        }
    }

    /**
     * Returns the team that the target belongs to.
     * @return target does not need to have collision so it will always return neutral.
     */
    @Override
    public GameScene.Team getTeam() {
        return GameScene.Team.Neutral;
    }

    /**
     * Saves the time at which the target was drawn, if the target is already drawn it ignores this call.
     * @param timeCreated the time at which the target has been drawn.
     */
    public void setTimeCreated(long timeCreated)
    {
        if (System.currentTimeMillis() > this.timeCreated + (TIME_TILL_STRIKE * 1000))
            this.timeCreated = timeCreated;
    }

    /**
     * Deals damage to the player who is in the explosion, if it's the first time the player is hit by the explosion
     * then this is ignored as the initial damage from the AirStrike is 90.
     * @param playerAfflicted The player that has collided with the explosion.
     * @param firstHit Whether it's the first collision or not.
     */
    @Override
    public void applyExplosionEffects(AbstractPlayer playerAfflicted, boolean firstHit)
    {
        if(firstHit)
        {
            playerAfflicted.takeDamage(parent.getDamage());
        }
        if(!firstHit)
        {
            playerAfflicted.takeDamage(5);
        }
    }

    /**
     * Deals damage to the tiles that are caught in the explosion of the air strike.
     * @param tileAfflicted The tile that has collided with the explosion.
     * @param firstHit Whether it's the first hit.
     */
    @Override
    public void applyExplosionEffects(MapTile tileAfflicted, boolean firstHit)
    {
        if(firstHit)
        {
            tileAfflicted.takeDamage(parent.getDamage());
        }
        else
        {
            tileAfflicted.takeDamage(5);
        }

        tileAfflicted.isDestroyed();
    }

    @Override
    public void detonate() { }
}
