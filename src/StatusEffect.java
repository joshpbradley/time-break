public abstract class StatusEffect
{
    private long startTime;
    private int duration;

    public StatusEffect(int length)
    {
        startTime = System.currentTimeMillis();
        duration = length;
    }

    public boolean checkIfEnded(AbstractPlayer afflicted)
    {
        boolean ended = !(startTime + duration - System.currentTimeMillis() > 0);
        if(ended)
        {
            endEffect(afflicted);
        }
        return ended;
    }

    public void forceEnd(AbstractPlayer afflicted)
    {
        startTime = 0;
        endEffect(afflicted);
    }

    public void replace(StatusEffect effect)
    {
        int elapsed = (int) (System.currentTimeMillis() - startTime);

        if(duration - elapsed > effect.getDuration()) //If this has more time to go than the new duration, don't bother
        {
            return;
        }
        else
        {
            duration = effect.duration + elapsed;
        }
    }

    public long getStartTime() { return startTime; }
    public int getDuration() { return duration; }
    public abstract void apply(AbstractPlayer afflicted);
    protected abstract void endEffect(AbstractPlayer afflicted);
}
