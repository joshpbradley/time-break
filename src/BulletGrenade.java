import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.nio.file.Paths;

public class BulletGrenade extends Grenade
{
    public BulletGrenade()
    {
        super (1, 0, 3, 1, 0.95f);
    }

    public void detonate()
    {
        float projectileSpeed = 3f;
        float diagonalSpeed = projectileSpeed * (float)Math.sin(45);

        Projectile[] explosionProjectiles = new Projectile[]
        {
            new Projectile(AllTextureFiles.getAmmoTexture(AllTextureFiles.AmmoType.BULLET), 0, -projectileSpeed, 10, GameScene.Team.Neutral),
            new Projectile(AllTextureFiles.getAmmoTexture(AllTextureFiles.AmmoType.BULLET), diagonalSpeed, -diagonalSpeed, 10, GameScene.Team.Neutral),
            new Projectile(AllTextureFiles.getAmmoTexture(AllTextureFiles.AmmoType.BULLET), projectileSpeed, 0, 10, GameScene.Team.Neutral),
            new Projectile(AllTextureFiles.getAmmoTexture(AllTextureFiles.AmmoType.BULLET), diagonalSpeed, diagonalSpeed, 10, GameScene.Team.Neutral),
            new Projectile(AllTextureFiles.getAmmoTexture(AllTextureFiles.AmmoType.BULLET), 0, projectileSpeed, 10, GameScene.Team.Neutral),
            new Projectile(AllTextureFiles.getAmmoTexture(AllTextureFiles.AmmoType.BULLET), -diagonalSpeed, diagonalSpeed, 10, GameScene.Team.Neutral),
            new Projectile(AllTextureFiles.getAmmoTexture(AllTextureFiles.AmmoType.BULLET), -projectileSpeed, 0, 10, GameScene.Team.Neutral),
            new Projectile(AllTextureFiles.getAmmoTexture(AllTextureFiles.AmmoType.BULLET), -diagonalSpeed, -diagonalSpeed, 10, GameScene.Team.Neutral)
        };

        for(int i = 0; i < 8; ++i)
        {
            explosionProjectiles[i].setOrigin(explosionProjectiles[i].getLocalBounds().width/2, explosionProjectiles[i].getLocalBounds().height/2);
            explosionProjectiles[i].setPosition(getGlobalBounds().left + getGlobalBounds().width/2, getGlobalBounds().top + getGlobalBounds().height/2);
            explosionProjectiles[i].setRotation((270 + (i * 45)) % 360);
            explosionProjectiles[i].setScale(4, 4);
            GameScene.getGameScene().add(explosionProjectiles[i]);
        }

        super.detonate();
    }

    /**
     * Allows another explodable to create an explosion in place of the grenade.
     *
     * Currently used so ExplodingProjectiles can handle a detonation event in the same way its Grenade object does,
     * and allows the created explosion to be a composed from the ExplodingProjectile and not the Grenade (So the explosion effects
     * can be customised by the exploding projectile and not the grenade).
     *
     * @param e the Explodable that will place itself as the item in the explosion
     * @param f the bounding rectangle that the explosion will centre on
     */
    @Override
    public void detonate(Explodable e, FloatRect f)
    {
        float projectileSpeed = 3f;
        float diagonalSpeed = projectileSpeed * (float)Math.sin(45);

        Projectile[] explosionProjectiles = new Projectile[]
                {
                        new Projectile(AllTextureFiles.getAmmoTexture(AllTextureFiles.AmmoType.BULLET), 0, -projectileSpeed, 10, GameScene.Team.Neutral),
                        new Projectile(AllTextureFiles.getAmmoTexture(AllTextureFiles.AmmoType.BULLET), diagonalSpeed, -diagonalSpeed, 10, GameScene.Team.Neutral),
                        new Projectile(AllTextureFiles.getAmmoTexture(AllTextureFiles.AmmoType.BULLET), projectileSpeed, 0, 10, GameScene.Team.Neutral),
                        new Projectile(AllTextureFiles.getAmmoTexture(AllTextureFiles.AmmoType.BULLET), diagonalSpeed, diagonalSpeed, 10, GameScene.Team.Neutral),
                        new Projectile(AllTextureFiles.getAmmoTexture(AllTextureFiles.AmmoType.BULLET), 0, projectileSpeed, 10, GameScene.Team.Neutral),
                        new Projectile(AllTextureFiles.getAmmoTexture(AllTextureFiles.AmmoType.BULLET), -diagonalSpeed, diagonalSpeed, 10, GameScene.Team.Neutral),
                        new Projectile(AllTextureFiles.getAmmoTexture(AllTextureFiles.AmmoType.BULLET), -projectileSpeed, 0, 10, GameScene.Team.Neutral),
                        new Projectile(AllTextureFiles.getAmmoTexture(AllTextureFiles.AmmoType.BULLET), -diagonalSpeed, -diagonalSpeed, 10, GameScene.Team.Neutral)
                };

        for(int i = 0; i < 8; ++i)
        {
            explosionProjectiles[i].setOrigin(explosionProjectiles[i].getLocalBounds().width/2, explosionProjectiles[i].getLocalBounds().height/2);
            explosionProjectiles[i].setPosition(((Sprite)e).getGlobalBounds().left + ((Sprite)e).getGlobalBounds().width/2, ((Sprite)e).getGlobalBounds().top + ((Sprite)e).getGlobalBounds().height/2);
            explosionProjectiles[i].setRotation((270 + (i * 45)) % 360);
            explosionProjectiles[i].setScale(4, 4);
            GameScene.getGameScene().add(explosionProjectiles[i]);
        }

        super.detonate(e, f);
    }

    @Override
    public Sprite getItemSprite()
    {
        Texture t = new Texture();
        try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Items/Item-Models/Bullet-Grenade.png")); }catch(Exception e){ e.printStackTrace(); }
        Sprite s = new Sprite(t);
        s.setScale(2f, 2f);
        s.setOrigin(  s.getLocalBounds().width/2,  s.getLocalBounds().height/2);
        return s;
    }

    @Override
    public Item clone() {
        return new BulletGrenade();
    }
}