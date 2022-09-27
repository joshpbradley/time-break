import org.jsfml.graphics.Texture;

import java.nio.file.Paths;

public class GrenadeLauncher extends Gun
{
    public GrenadeLauncher()
    {
        super(4,1000,60,8,7,1, AllTextureFiles.AmmoType.GRENADE);
    }

    @Override
    protected boolean makeExplosiveProjectile()
    {
        return true;
    }

    @Override
    public void resetSpriteTexture()
    {
        Texture t = new Texture();

        if(System.currentTimeMillis() % 2 == 0)
        {
            try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Grenade-Launcher-Left.png")); }catch(Exception e){ e.printStackTrace(); }
        }
        else
        {
            try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Grenade-Launcher-Right.png")); }catch(Exception e){ e.printStackTrace(); }
        }

        setTexture(t, true);
        setOrigin(getGlobalBounds().width/2, getGlobalBounds().height/2);
    }
}
