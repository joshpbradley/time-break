import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.nio.file.Paths;

public class Jetpack implements Item
{
    private Player itemOwner;
    private boolean activated = false;

    @Override
    public int getID()
    {
        return 8;
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
    }

    @Override
    public void use(Controller.Direction direction){}

    public void use()
    {
        if(!activated)
        {
            getItemOwner().addStatusEffect(new Jump(5000));
            getItemOwner().addStatusEffect(new Haste(5000));
            itemOwner.setItem(null);
            activated = true;
        }
    }

    public boolean getActivated()
    {
        return activated;
    }

    @Override
    public Sprite getItemSprite()
    {
        Texture t = new Texture();
        try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Items/Item-Models/Jetpack.png")); }catch(Exception e){ e.printStackTrace(); }
        Sprite s = new Sprite(t);
        s.setScale(2f, 2f);
        s.setOrigin(  s.getLocalBounds().width/2,  s.getLocalBounds().height/2);
        return s;
    }

    @Override
    public Item clone() {
        return new Jetpack();
    }
}
