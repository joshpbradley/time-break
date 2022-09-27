public class Cowboy extends Character
{
    public Cowboy()
    {
        super(100, 1, 3, new Revolver(), false, false);
    }

    @Override
    public void Ability(Player player)
    {
        // The Cowboy does not have an active ability.
    }
}