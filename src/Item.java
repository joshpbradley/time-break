import org.jsfml.graphics.Sprite;

/**
 * Class that is used to give basic functionality to all items.
 */

public interface Item extends DropItem
{
    int getID();

    Player getItemOwner();

    void setItemOwner(Player itemOwner);

    void use(Controller.Direction direction);

    Sprite getItemSprite();

    Item clone();
}
