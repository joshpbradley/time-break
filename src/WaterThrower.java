import org.jsfml.graphics.Texture;

import java.nio.file.Paths;

/**
 * Represents a water gun weapon.
 *
 * This weapon is characterised by rapid rate of fire ang high spread, but low damage
 */
public class WaterThrower extends Gun
{
    /**
     * The contructor for the WaterThrower
     */
    public WaterThrower()
    {
        super(11,5,1, 100, 5, 5,
        new AllTextureFiles.AmmoType[] {AllTextureFiles.AmmoType.WATER_DARK,
                AllTextureFiles.AmmoType.WATER_WHITE,
                AllTextureFiles.AmmoType.WATER_LIGHT});

        setDeviation(0.8f);
        resetSpriteTexture();
    }

    /**
     * Returns whether this weapon is capable of firing explosive ammunition.
     *
     * @return
     */
    @Override
    protected boolean makeExplosiveProjectile()
    {
        return false;
    }

    /**
     * Resets the Sprite of the WaterThrower weapon.
     */
    @Override
    public void resetSpriteTexture()
    {
        Texture t = new Texture();

        if(System.currentTimeMillis() % 2 == 0)
        {
            try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Water-Thrower-Left.png")); }catch(Exception e){ e.printStackTrace(); }
        }
        else
        {
            try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Water-Thrower-Right.png")); }catch(Exception e){ e.printStackTrace(); }
        }

        setTexture(t, true);
        setOrigin(getGlobalBounds().width/2, getGlobalBounds().height/2);
    }
}
