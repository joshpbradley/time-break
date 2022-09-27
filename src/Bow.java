import org.jsfml.graphics.Texture;

import java.nio.file.Paths;

public class Bow extends Gun
{
    public Bow()
    {
        super(1,500,25,15,7,1, AllTextureFiles.AmmoType.ARROW);
        resetSpriteTexture();
    }

    @Override
    protected boolean makeExplosiveProjectile()
    {
        return ((Player)getWeaponOwner()).getItem() instanceof Grenade;
    }

    @Override
    public void resetSpriteTexture()
    {
        Texture t = new Texture();

        if(System.currentTimeMillis() % 2 == 0)
        {
            try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Bow-Pulled-Left.png")); }catch(Exception e){ e.printStackTrace(); }
        }
        else
        {
            try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Bow-Pulled-Right.png")); }catch(Exception e){ e.printStackTrace(); }
        }

        setTexture(t, true);
        setOrigin(getGlobalBounds().width/2, getGlobalBounds().height/2);
    }
}
