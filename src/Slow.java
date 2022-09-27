public class Slow extends StatusEffect
{
    private final double multiplier = 0.5;
    private boolean alreadySet = false;

    public Slow(int length)
    {
        super(length);
    }

    public void apply(AbstractPlayer afflicted)
    {
        if(!alreadySet)
        {
            afflicted.removeFromMultiplier(multiplier);
            alreadySet = true;
        }
    }

    protected void endEffect(AbstractPlayer afflicted)
    {
        afflicted.addToMultiplier(multiplier);
    }
}
