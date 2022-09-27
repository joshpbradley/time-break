import org.jsfml.graphics.Texture;
import java.io.File;

/**
 * Represents the arrow sprite that indicates the state of which character a player
 * has selected and whether the player is ready to initiate a game.
 */
public class ArrowMenuSprite extends MenuSprite
{
    // The File object used for the left player's arrow while this player is not ready.
    private static File redArrowLeft = new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Arrow-Red-Left.png");
    // The File object used for the left player's arrow while this player is not ready.
    private static File redArrowRight = new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Arrow-Red-Right.png");
    // The File object used for the right player's arrow while this player is not ready.
    private static File blueArrow = new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Character-Menu/Arrow-Blue.png");
    // The File object used for the left player's arrow while this player is ready.
    private static File yellowArrowLeft = new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Arrow-Yellow-Left.png");
    // The File object used for the right player's arrow while this player is ready.
    private static File yellowArrowRight = new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Arrow-Yellow-Right.png");
    /*
     * Contains all of the potential floating point values of y-ordinate's that an arrow may have.
     * The position indicates which character has been selected by the player.
     * The y-ordinates are taken from the top-left corner of the sprite's bounding rectangle.
     */
    private float[] yPosPotentials;
    private float[] xPosPotentials;

    /**
     * Constructor for the ArrowMenuSprite class.
     * @param xPos the x-ordinate of the top-left corner of the sprite's bounding rectangle
     * @param yPosPotentials the array of potential y-ordinates that the sprite may have
     */
    public ArrowMenuSprite(float xPos, float[] yPosPotentials)
    {
        super(xPos, yPosPotentials[0]);
        this.yPosPotentials = yPosPotentials;
    }

    /**
     * Constructor for the ArrowMenuSprite class.
     * @param xPosPotentials the array of potential x-ordinates that the sprite may have
     * @param yPosPotentials the array of potential y-ordinates that the sprite may have
     */
    public ArrowMenuSprite(float[] xPosPotentials, float[] yPosPotentials)
    {
        super(xPosPotentials[0], yPosPotentials[0]);
        this.xPosPotentials = xPosPotentials;
        this.yPosPotentials = yPosPotentials;
    }

    /**
     * Gets the x-ordinate of the sprite to one of the potential values in the yPosPotentials array.
     * @param index the index of the value to set as the y-ordinate in the yPosPotentials array
     */
    public float getXPos(int index)
    {
        return xPosPotentials[index];
    }

    /**
     * Gets the y-ordinate of the sprite to one of the potential values in the yPosPotentials array.
     * @param index the index of the value to set as the y-ordinate in the yPosPotentials array
     */
    public float getYPos(int index)
    {
        return yPosPotentials[index];
    }

    /**
     * Selects the appropriate sprite File object for the player, given their readiness and which side
     * they are on in the character menu.
     * @param readyState indicates the readiness state of the player. True means the player
     * is ready to begin the game
     * @param playerTeam the side of the keyboard peripheral that the player is in control of
     * @return the File object corresponding to the appropriate sprite image to load
     */
    public Texture getTexture(boolean readyState, GameScene.Team playerTeam)
    {
        Texture t = new Texture();
        if(readyState)
        {
            if(playerTeam == GameScene.Team.First)
            {
                try{ t.loadFromFile(yellowArrowLeft.toPath()); }catch(Exception e){ e.printStackTrace(); }
            }
            else
            {
                try{ t.loadFromFile(yellowArrowRight.toPath()); }catch(Exception e){ e.printStackTrace(); }
            }
        }
        else
        {
            if(playerTeam == GameScene.Team.First)
            {
                try{ t.loadFromFile(redArrowLeft.toPath()); }catch(Exception e){ e.printStackTrace(); }
            }
            else
            {
                try{ t.loadFromFile(blueArrow.toPath()); }catch(Exception e){ e.printStackTrace(); }
            }
        }
        return t;
    }

    /**
     * Selects the appropriate sprite File object for the player in the map menu,
     * calls to this method have alternating booleans, which will switch which arrow facing direction
     * for each successive call
     * @param mapSelected the map that is currently under selection in the map menu
     * @param readyState indicates the readiness state of the player. True means the player
     * is ready to begin the game
     */
    public Texture getTexture(int mapSelected, boolean readyState)
    {
        Texture t = new Texture();
        if(readyState)
        {
            if(mapSelected % 2 == 0)
            {
                try{ t.loadFromFile(yellowArrowLeft.toPath()); }catch(Exception e){ e.printStackTrace(); }
            }
            else
            {
                try{ t.loadFromFile(yellowArrowRight.toPath()); }catch(Exception e){ e.printStackTrace(); }
            }
        }
        else
        {
            if(mapSelected % 2 == 0)
            {
                try{ t.loadFromFile(redArrowLeft.toPath()); }catch(Exception e){ e.printStackTrace(); }
            }
            else
            {
                try{ t.loadFromFile(redArrowRight.toPath()); }catch(Exception e){ e.printStackTrace(); }
            }
        }
        return t;
    }
}
