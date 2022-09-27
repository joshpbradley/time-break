public class Burning extends StatusEffect
{
    private int damageOverDuration;
    private int damageDealt = 0;
    private long lastDamaged;

    public Burning(int length)
    {
        super(length);
        damageOverDuration = (int) Math.floor((double) length / 100);
        lastDamaged = System.currentTimeMillis();
    }

    public void apply(AbstractPlayer afflicted)
    {
        int damageToDeal;
        if(System.currentTimeMillis() - lastDamaged - 100 > 0)
        {
            damageToDeal = (int) Math.ceil((System.currentTimeMillis() - lastDamaged) / 100);
            lastDamaged += damageToDeal * 100;
        }
        else
            return;

        if(damageToDeal > damageOverDuration)
            damageToDeal = damageOverDuration;

        afflicted.takeDamage(damageToDeal);
        damageDealt += damageToDeal;
    }

    protected void endEffect(AbstractPlayer afflicted)
    {
        if(damageOverDuration > damageDealt)
        {
            afflicted.takeDamage(damageOverDuration - damageDealt);
        }
    }

    public void replace(StatusEffect effect)
    {
        super.replace(effect);
        damageOverDuration = (int) Math.floor(((double) getDuration() - (System.currentTimeMillis() - getStartTime())) / 100);
    }
}
