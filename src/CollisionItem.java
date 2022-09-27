public interface CollisionItem
{
    void collision(CollisionItem collideWith);
    void update();
    GameScene.Team getTeam();
}
