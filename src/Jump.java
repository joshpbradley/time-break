public class Jump extends StatusEffect
{
    public Jump(int length)
    {
        super(length);
    }

    private boolean effectApplied = false;
    private static final float scaleMultiplier = 1.3f;

    public synchronized void apply(AbstractPlayer afflicted)
    {
        if(!(afflicted instanceof Player) || effectApplied)
        {
            return;
        }

        afflicted.setScale(Player.initialScaling * scaleMultiplier, Player.initialScaling * scaleMultiplier);
        afflicted.getWeapon().setScale(Player.initialScaling * 1.3f * scaleMultiplier, Player.initialScaling * 1.3f * scaleMultiplier);
        afflicted.setOnGround(false);

        effectApplied = true;
    }
    
    protected void endEffect(AbstractPlayer afflicted)
    {
        if(!(afflicted instanceof Player))
        {
            return;
        }

        afflicted.setScale(Player.initialScaling, Player.initialScaling);
        afflicted.getWeapon().setScale(Player.initialScaling * 1.3f, Player.initialScaling * 1.3f);
        afflicted.setOnGround(true);

        if(((Player)afflicted).getItem() instanceof Jetpack && ((Jetpack) ((Player)afflicted).getItem()).getActivated())
        {
            ((Player)afflicted).setItem(null);
        }
    }
}
