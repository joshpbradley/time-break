import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import java.nio.file.Paths;

public class DroneDrop implements Item
{
    @Override
    public int getID()
    {
        return 5;
    }

    @Override
    public Player getItemOwner()
    {
        return null;
    }

    @Override
    public void setItemOwner(Player itemOwner) { }

    @Override
    public void use(Controller.Direction direction) { }

    @Override
    public Sprite getItemSprite()
    {
        Texture t = new Texture();
        try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Items/Item-Models/Drone.png")); }catch(Exception e){ e.printStackTrace(); }
        Sprite s = new Sprite(t);
        s.setScale(2f, 2f);
        s.setOrigin(s.getLocalBounds().width/2,  s.getLocalBounds().height/2);
        return s;
    }

    @Override
    public Item clone() {
        return new DroneDrop();
    }
}
