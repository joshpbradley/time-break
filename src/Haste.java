public class Haste extends StatusEffect
{
    private final double multiplier = 0.5;
    private boolean alreadySet = false;

    public Haste(int length)
    {
        super(length);
    }

    public void apply(AbstractPlayer afflicted)
    {
        if(!alreadySet)
        {
            afflicted.addToMultiplier(multiplier);
            alreadySet = true;
        }
    }

    public void endEffect(AbstractPlayer afflicted)
    {
        afflicted.removeFromMultiplier(multiplier);
    }
}
