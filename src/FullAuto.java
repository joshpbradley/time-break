import org.jsfml.graphics.Texture;
import java.nio.file.Paths;

public class FullAuto extends Gun
{
    public FullAuto()
    {
        super(2,100,8,20,7,1, AllTextureFiles.AmmoType.BULLET);
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
            try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Assault-Rifle-Left.png")); }catch(Exception e){ e.printStackTrace(); }
        }
        else
        {
            try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Assault-Rifle-Right.png")); }catch(Exception e){ e.printStackTrace(); }
        }

        setTexture(t, true);
        setOrigin(getGlobalBounds().width/2, getGlobalBounds().height/2);
    }
}
