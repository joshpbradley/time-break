public class CollisionTile extends MapTile implements CollisionItem
{
    public CollisionTile(Map map, char type, int x, int y)
    {
        super(map, type, x, y);
    }

    @Override
    public void update() { }

    @Override
    public GameScene.Team getTeam()
    {
        return GameScene.Team.Neutral;
    }

    @Override
    public void collision(CollisionItem collideWith)
    {
        if(!(collideWith instanceof MovingBody && ((MovingBody)collideWith).isOnGround()))
        {
            return;
        }

        if(collideWith instanceof Projectile && !(collideWith instanceof Explodable))
        {
            GameScene.getGameScene().remove(collideWith);
            takeDamage(((Projectile)collideWith).getDamage());
            isDestroyed();
        }
        else if(collideWith instanceof Explodable)
        {
            collideWith.collision(this);
        }
    }
}
