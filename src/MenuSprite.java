import org.jsfml.graphics.Sprite;

/**
 * Represents an abstract sprite that appears in the character menu.
 */
public abstract class MenuSprite extends Sprite
{
    /*
     * The x-Ordinate of the top-left corner of the Sprite's bounding rectangle.
     * The value is given from the side of the window being zero and increases as the position
     * moves to the right.
     */
    private float xPos;
    /*
     * The y-Ordinate of the top-left corner of the Sprite's bounding rectangle.
     * The value is given from the top of the window being zero and increases as the position
     * moves to the bottom.
     */
    private float yPos;
    /*
     * The multiplier that the original image file is multiplied by in a the X and Y dimension
     * to reach the resulting size.
     */
    private float scaleMultiplier;

    /**
     * Constructor for the MenuSprite class.
     * @param xPos the x-ordinate of the top-left corner of the sprite's bounding rectangle
     * @param yPos the y-ordinate of the top-left corner of the sprite's bounding rectangle
     */
    public MenuSprite(float xPos, float yPos)
    {
        this.xPos = xPos;
        this.yPos = yPos;

        setPosition(xPos, yPos);
    }

    /**
     * Sets the scaleMultiplier instance variable.
     * @param scaleMultiplier the value to set the scaleVector instance variable to
     */
    public void setScaleMultiplier(float scaleMultiplier)
    {
        this.scaleMultiplier = scaleMultiplier;
    }

    /**
     * Returns the scaleMultiplier instance variable.
     * @return the scaleMultiplier instance variable
     */
    public float getScaleMultiplier()
    {
        return scaleMultiplier;
    }

    /**
     * Returns the x-ordinate of the top-left corner of the sprite's bounding rectangle.
     * @return the x-ordinate instance variable
     */
    public float getXPos()
    {
        return xPos;
    }

    /**
     * Returns the y-ordinate of the top-left corner of the sprite's bounding rectangle.
     * @return the y-ordinate instance variable
     */
    public float getYPos()
    {
        return yPos;
    }
}
