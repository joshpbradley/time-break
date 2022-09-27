import org.jsfml.graphics.Texture;
import java.io.File;

/**
 * Represents the character sprite that indicates the state of which character a player has selected.
 */
public class CharacterUISprite extends MenuSprite
{
    /*
     * The File object used to indicate the left player's currently selected character.
     */
    private static File[] playerLeftCharacterFiles = new File[]
    {
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Character-Menu/Knight-Left.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Character-Menu/Scientist-Left.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Character-Menu/Ninja-Left.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Character-Menu/Cowboy-Left.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Character-Menu/Alien-Left.png"),

        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Map-Menu/Knight-Left.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Map-Menu/Scientist-Left.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Map-Menu/Ninja-Left.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Map-Menu/Cowboy-Left.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Map-Menu/Alien-Left.png"),

        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Knight-Left.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Scientist-Left.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Ninja-Left.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Cowboy-Left.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Alien-Left.png")
    };

    /*
     * The File object used to indicate the right player's currently selected character.
     */
    private static File[] playerRightCharacterFiles = new File[]
    {
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Character-Menu/Knight-Right.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Character-Menu/Scientist-Right.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Character-Menu/Ninja-Right.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Character-Menu/Cowboy-Right.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Character-Menu/Alien-Right.png"),

        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Map-Menu/Knight-Right.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Map-Menu/Scientist-Right.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Map-Menu/Ninja-Right.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Map-Menu/Cowboy-Right.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Map-Menu/Alien-Right.png"),

        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Knight-Right.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Scientist-Right.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Ninja-Right.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Cowboy-Right.png"),
        new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Alien-Right.png")
    };

    /**
     * Constructor for the CharacterMenuSprite class.
     * @param xPos the x-ordinate of the top-left corner of the sprite's bounding rectangle
     * @param yPos the y-ordinate of the top-left corner of the sprite's bounding rectangle
     */
    public CharacterUISprite(float xPos, float yPos)
    {
        super(xPos, yPos);
    }

    /**
     * Returns the appropriate sprite file for a given player and character selection.
     * @param characterSelected the value of the character that has been selected
     * @param playerTeam the side of the keyboard peripheral that the player is in control of
     * @param menuID the menu where the sprite is to be loaded into (the sizing of the sprites for the menus
     * means there's a need to be able to distinguish which menu the sprite is for)
     * @return the File object corresponding to the appropriate sprite image to load
     */
    public Texture getTexture(int characterSelected, GameScene.Team playerTeam, int menuID)
    {
        Texture t = new Texture();

        if(playerTeam == GameScene.Team.First)
        {
            try { t.loadFromFile(playerLeftCharacterFiles[characterSelected + menuID * 5].toPath()); }catch(Exception e){ e.printStackTrace(); }
        }
        else
        {
            try { t.loadFromFile(playerRightCharacterFiles[characterSelected + menuID * 5].toPath()); }catch(Exception e){ e.printStackTrace(); }
        }

        return t;
    }
}
