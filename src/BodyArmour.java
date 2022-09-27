import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import java.nio.file.Paths;

public class BodyArmour implements Item
{
    private Player itemOwner;

    @Override
    public int getID()
    {
        return 7;
    }

    @Override
    public Player getItemOwner()
    {
        return itemOwner;
    }

    @Override
    public void setItemOwner(Player itemOwner)
    {
        this.itemOwner = itemOwner;
        if(itemOwner != null)
        {
            itemOwner.setMode(true);
            itemOwner.setCanChangeMode(false);
        }
    }

    @Override
    public void use(Controller.Direction direction){ itemOwner.setCanChangeMode(true); }

    @Override
    public Sprite getItemSprite()
    {
        Texture t = new Texture();
        try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Items/Item-Models/Body-Armour.png")); }catch(Exception e){ e.printStackTrace(); }
        Sprite s = new Sprite(t);
        s.setScale(2f, 2f);
        s.setOrigin(  s.getLocalBounds().width/2,  s.getLocalBounds().height/2);
        return s;
    }

    @Override
    public Item clone() {
        return new BodyArmour();
    }

    public void use()
    {
        itemOwner.setItem(null);
    }
}
