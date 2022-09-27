import org.jsfml.graphics.Texture;

import java.nio.file.Paths;

public class Shotgun extends Gun
{
    public Shotgun()
    {
        super(8, 500, 20, 4, 7, 5, AllTextureFiles.AmmoType.BULLET);
        setDeviation(0.5f);
        resetSpriteTexture();
    }

    @Override
    protected boolean makeExplosiveProjectile()
    {
        return false;
    }

    @Override
    public void resetSpriteTexture()
    {
        Texture t = new Texture();

        if(System.currentTimeMillis() % 2 == 0)
        {
            try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Shotgun-Left.png")); }catch(Exception e){ e.printStackTrace(); }
        }
        else
        {
            try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Shotgun-Right.png")); }catch(Exception e){ e.printStackTrace(); }
        }

        setTexture(t, true);
        setOrigin(getGlobalBounds().width/2, getGlobalBounds().height/2);
    }
}
