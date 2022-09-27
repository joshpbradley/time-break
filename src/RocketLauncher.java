import org.jsfml.graphics.Texture;

import java.nio.file.Paths;

public class RocketLauncher extends Gun
{
    public RocketLauncher()
    {
        super(7,1000, 90, 2, 3.75f, 1, AllTextureFiles.AmmoType.ROCKET);
        resetSpriteTexture();
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
            try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Rocket-Launcher-Full-Left.png")); }catch(Exception e){ e.printStackTrace(); }
        }
        else
        {
            try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Rocket-Launcher-Full-Right.png")); }catch(Exception e){ e.printStackTrace(); }
        }

        setTexture(t, true);
        setOrigin(getGlobalBounds().width/2, getGlobalBounds().height/2);
    }
}
