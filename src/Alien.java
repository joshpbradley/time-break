public class Alien extends Character
{
    private Player opponent;

    public Alien()
    {
        super(100, 1, 4, new Pistol(), true, true);
    }

    @Override
    public void Ability(Player player)
    {
        if (opponent == null) {
            opponent = GameScene.getGameScene().getOtherPlayer(player);
        }

        opponent.addStatusEffect(new Confusion());
    }
}