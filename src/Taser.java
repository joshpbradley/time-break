import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import java.nio.file.Paths;

/**
 * Represents a taser Item.
 * It forces your opponent to swap weapon with an item on the ground or drop their weapon
 * (so long as their weapon is not a default weapon - in which case this effect does nothing.)
 */
public class Taser implements Item
{
    private Player itemOwner;

    /**
     * Gets the ID of the Item. The ID for this item is 9.
     * Used to retrieve the correct Textures from AlTextureFiles.
     * @return
     */
    @Override
    public int getID()
    {
        return 9;
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
    public void use(Controller.Direction direction) { }

    public void use()
    {
        Player other = GameScene.getGameScene().getOtherPlayer(itemOwner);
        other.swapKeypress();
        itemOwner.setItem(null);
    }

    @Override
    public Sprite getItemSprite()
    {
        Texture t = new Texture();
        try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Items/Item-Models/Taser.png")); }catch(Exception e){ e.printStackTrace(); }
        Sprite s = new Sprite(t);
        s.setScale(2f, 2f);
        s.setOrigin(  s.getLocalBounds().width/2,  s.getLocalBounds().height/2);
        return s;
    }

    @Override
    public Item clone() {
        return new Taser();
    }
}
