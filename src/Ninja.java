public class Ninja extends Character
{
    private Player opponent;

    public Ninja()
    {
        super(100, 1.2f, 2, new Pistol(), true, false);
    }

    @Override
    public void Ability(Player player)
    {
        if(opponent == null)
        {
            opponent = GameScene.getGameScene().getOtherPlayer(player);
        }

        Weapon userWep = player.getWeapon();
        player.setWeapon(opponent.getWeapon());
        opponent.setWeapon(userWep);
    }
}