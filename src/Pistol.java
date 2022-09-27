import org.jsfml.graphics.Texture;
import java.nio.file.Paths;

public class Pistol extends Gun
{
    public Pistol()
    {
        super(5, 200, 10, 1, 5, 1, AllTextureFiles.AmmoType.BULLET);
        resetSpriteTexture();
    }

    @Override
    public void reduceAmmo() { }

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
            try
            {
                t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Pistol-Left.png"));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            try
            {
                t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Pistol-Right.png"));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        setTexture(t, true);
        setOrigin(getGlobalBounds().width/2, getGlobalBounds().height/2);
    }
}
