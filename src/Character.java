public abstract class Character
{
    private int ID;
    private int hitPoints;
    private float speedMultiplier;
    private Weapon defaultWeapon;
    private walkingPhase currentWalkingPhase = walkingPhase.NEITHER;
    private boolean firstStep = true;
    private long lastStep = 0;

    private boolean hasAbility;
    private boolean canResetAbility;

    public Character(int hitPoints, float speedMultiplier, int ID, Weapon defaultWeapon, boolean hasAb, boolean resetAb)
    {
        this.hitPoints = hitPoints;
        this.speedMultiplier = speedMultiplier;
        this.ID = ID;
        this.defaultWeapon = defaultWeapon;

        hasAbility = hasAb;
        canResetAbility = resetAb;
    }

    public boolean hasAbility() { return hasAbility; }

    public boolean canResetAbility() { return canResetAbility; }

    public int getHitPoints()
    {
        return hitPoints;
    }

    public float getSpeedMultiplier()
    {
        return speedMultiplier;
    }

    public int getID()
    {
        return ID;
    }

    public Weapon getDefaultWeapon()
    {
        return defaultWeapon;
    }

    public int getWalkingPhaseID()
    {
        switch(currentWalkingPhase)
        {
            case NEITHER:
                return 0;
            case LEFT:
                return 1;
            case RIGHT:
                return 2;
            default:
                return -1;
        }
    }

    public void updateWalkingPhase()
    {
        if(currentWalkingPhase != walkingPhase.RIGHT)
        {
            currentWalkingPhase = walkingPhase.RIGHT;
        }
        else
        {
            currentWalkingPhase = walkingPhase.LEFT;
        }
    }

    public void setWalkingPhase(walkingPhase c)
    {
        currentWalkingPhase = c;
    }

    public void Ability(Player player) {}

    public enum walkingPhase { LEFT, RIGHT, NEITHER }

    public void makeFootstep()
    {
        if (System.currentTimeMillis() - lastStep < 200)
        {
            return;
        }

        updateWalkingPhase();

        lastStep = System.currentTimeMillis();

        if(firstStep)
        {
            AudioHandler.playSound("footstep1");
            firstStep = false;
        }
        else
        {
            AudioHandler.playSound("footstep2");
            firstStep = true;
        }
    }
}