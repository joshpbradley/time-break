import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import java.nio.file.Paths;

public class JumpGrenade extends Grenade
{
    public JumpGrenade()
    {
        super (4, 40, 3, 4, 0.95f);
        setOnGround(false);
    }

    @Override
    public Sprite getItemSprite()
    {
        Texture t = new Texture();
        try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Items/Item-Models/Jump-Grenade.png")); }catch(Exception e){ e.printStackTrace(); }
        Sprite s = new Sprite(t);
        s.setScale(2f, 2f);
        s.setOrigin(  s.getLocalBounds().width/2,  s.getLocalBounds().height/2);
        return s;
    }

    @Override
    public Item clone() {
        return new JumpGrenade();
    }
}
