public class Frozen extends StatusEffect
{
    public Frozen(int length)
    {
        super(length);
    }

    public void apply(AbstractPlayer afflicted)
    {
        afflicted.lockout(getDuration());
    }

    protected void endEffect(AbstractPlayer afflicted)
    {
        // No effect needed - Lockout handles automatically.
    }
}
