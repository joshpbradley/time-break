import org.jsfml.graphics.Texture;

public class Projectile extends MovingBody
{
    protected int damage;
    private boolean hit = false;
    private boolean isWeaponTexture = false;
    private long lastUpdate = System.currentTimeMillis();

    /**
     * Constructs a Projectile object
     * @param t the texture of the Projectile
     * @param spX the x-speed of the Projectile
     * @param spY the y-speed of the Projectile
     * @param dmg the damage of the Projectile
     * @param team the team of the Projectile
     */
    public Projectile(Texture t, double spX, double spY, int dmg, GameScene.Team team)
    {
        super(t, team);
        damage = dmg;
        setSpeedX(spX);
        setSpeedY(spY);
        setFriction(1);
    }

    public double getSpeedX() { return super.getSpeedX() / 4; } //To avoid mass knockback on follow calls, to emulate weight
    public double getSpeedY() { return super.getSpeedY() / 4; }

    public void lead(MovingBody collideWith)
    {
        hit(collideWith);
    }
    public void follow(MovingBody collideWith)
    {
        hit(collideWith);
    }

    protected void hit(MovingBody collideWith)
    {
        if(!hit)
        {
            GameScene.getGameScene().remove(this);

            if (collideWith instanceof FrozenBody)
            {
                collideWith = ((FrozenBody) collideWith).getOriginal();
            }
            if (collideWith instanceof AbstractPlayer)
            {
                ((AbstractPlayer) collideWith).takeDamage(damage);
            }
        }
    }

    @Override
    public void boundaryHit()
    {
        if(hasHitEdge() != null)
        {
            GameScene.getGameScene().remove(this);
        }
    }

    @Override
    public void update()
    {
        boundaryHit();

        if(isWeaponTexture && System.currentTimeMillis() - lastUpdate > 5)
        {
            rotate(7);
            lastUpdate = System.currentTimeMillis();
        }
    }

    public int getDamage()
    {
        return damage;
    }

    public void setIsWeaponTexture()
    {
        isWeaponTexture = true;
        damage = 10;
    }
}
