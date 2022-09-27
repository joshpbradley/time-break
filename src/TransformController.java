import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * Controls the scaling of graphics onto the window.
 */
public class TransformController
{
    // The optimal width of the window that the game shall be played on
    private static final int fullSizeX = 2520;
    // The optimal height of the window that the game shall be played on
    private static final int fullSizeY = 1080;

    /**
     * Returns a vector containing updated coordinates on the window, maps the "ideal" full resolution coordinates
     * into the adjusted coordinates.
     * @param fullPosX the x-ordinate in the "ideal" screen width
     * @param fullY the y-ordinate in the "ideal" screen height
     * @return the Vector object containing the adjusted coordinates
     */
    public static Vector2f transformXAndY(int fullPosX, int fullY)
    {
        return new Vector2f(transformX(fullPosX), transformY(fullY));
    }

    /**
     * Returns an x-ordinate, relative to the actual window width as opposed to
     * the "ideal" screen width.
     * @param fullPosX the x-ordinate in the "ideal" screen width
     * @return the adjusted x-ordinate
     */
    public static float transformX(float fullPosX)
    {
        return fullPosX * (GameScene.getGameScene().getView().getSize().x/(float)fullSizeX);
    }

    /**
     * Returns a y-ordinate, relative to the actual window height as opposed to
     * the "ideal" screen height.
     * @param fullPosY the x-ordinate in the "ideal" screen width
     * @return the adjusted x-ordinate
     */
    public static float transformY(float fullPosY)
    {
        return fullPosY * (GameScene.getGameScene().getView().getSize().y/(float)fullSizeY);
    }

    /**
     * Returns the "ideal" x and y dimensions of the window as a Vector object.
     * @return the Vector object containing the "ideal" window dimensions
     */
    public static Vector2i getFullSize()
    {
        return new Vector2i(fullSizeX, fullSizeY);
    }

    /**
     * Returns the multiplier required to scale a given starting dimension to a size that is adjusted to the actual
     * window size as opposed to the "ideal" resolution.
     *
     * e.g. assume the original length is 1 unit long, and the final length you want is 10 units long.
     * The length you want in the x direction = 10 * window_length/full_length.
     * Dividing this by the original length will yield the multiplier.
     * @param originalLength the actual length/width of the image to be scaled (dimension depends on mode argument)
     * @param finalLength the length/width of the image when viewed in the "ideal" dimension
     * @param domain the dimension to perform the calculation in. X is the x-dimension and Y is the y-dimension
     * @return the multiplier that converts the original image dimension size into an adjusted dimension
     * that corresponds to the actual image size
     */
    public static float getScaleMultiplier(float originalLength, float finalLength, Domain domain)
    {
        if(domain == Domain.X)
        {
            return (GameScene.getGameScene().getView().getSize().x * finalLength)/(fullSizeX * originalLength);
        }
        else
        {
            return (GameScene.getGameScene().getView().getSize().y * finalLength)/(fullSizeY * originalLength);
        }
    }

    /**
     * Represents the X and Y domains of the screen.
     */
    public enum Domain { X, Y }
}
