import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;
import java.io.File;
import java.util.Random;
import static org.jsfml.window.event.Event.Type.*;

/**
 * Represents the menu that allows players to choose the map before initiating a round of games.
 */
public class MapMenu implements AbstractMenu
{
    // The number of characters that a player can potentially select.
    private static int NUMBER_OF_MAPS = 7;
    // The value of mapSelected when the user is selecting the random map.
    private static int RANDOM_MAP = 3;
    // The value of the map that is currently under selection by the player.
    private int mapSelected;
    // The sprite that shows the character under selection.
    private ArrowMenuSprite arrow;
    // The sprite that shows the character selected by the player.
    private CharacterUISprite playerOneCharacter;
    // The sprite that shows the character selected by the player.
    private CharacterUISprite playerTwoCharacter;
    // Represents whether a map has been selected
    private boolean ready;
    // The ID of the menu, used to collect the correct character sprites from CharacterMenuSprites
    public static final int menuID = 1;
    // The Sprite tht forms the background of the menu
    private Sprite menuSprite;

    /**
     * Constructor for the CharacterMenu class.
     */
    public MapMenu()
    {
        playerOneCharacter = new CharacterUISprite(TransformController.transformX(25), TransformController.transformY(155));
        playerTwoCharacter = new CharacterUISprite(TransformController.transformX(1722), TransformController.transformY(155));

        arrow = new ArrowMenuSprite(
                new float[]{
                        TransformController.transformX(823),
                        TransformController.transformX(1457),
                        TransformController.transformX(823),
                        TransformController.transformX(1457),
                        TransformController.transformX(823),
                        TransformController.transformX(1457),
                        TransformController.transformX(823)
                },
                new float[]{
                        TransformController.transformY(178),
                        TransformController.transformY(278),
                        TransformController.transformY(381),
                        TransformController.transformY(481),
                        TransformController.transformY(584),
                        TransformController.transformY(684),
                        TransformController.transformY(787)
                }
        );

        arrow.setScaleMultiplier(TransformController.getScaleMultiplier(20, 240, TransformController.Domain.X));
        arrow.scale(arrow.getScaleMultiplier(), arrow.getScaleMultiplier());

        playerOneCharacter.setScaleMultiplier(TransformController.getScaleMultiplier(778, 778, TransformController.Domain.Y));
        playerOneCharacter.scale(playerOneCharacter.getScaleMultiplier(), playerOneCharacter.getScaleMultiplier());
        playerTwoCharacter.scale(playerOneCharacter.getScaleMultiplier(), playerOneCharacter.getScaleMultiplier());

        Texture menuTexture = new Texture();
        try{ menuTexture.loadFromFile(new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Map-Menu/Map-Menu.png").toPath()); }catch(Exception e){ e.printStackTrace(); }
        menuSprite = new Sprite(menuTexture);
        menuSprite.scale((float)GameScene.getGameScene().getView().getSize().x/TransformController.getFullSize().x,
                (float)GameScene.getGameScene().getView().getSize().y/TransformController.getFullSize().y);
    }

    /**
     * Selects the map selected by a player after a map change keyboard event.
     * @param asd the direction which the player has shifted their arrow to select a character
     */
    public void setMapSelected(ArrowShiftDirection asd)
    {
        if(asd == ArrowShiftDirection.UP)
        {
            --mapSelected;

            if(mapSelected < 0)
            {
                mapSelected = NUMBER_OF_MAPS - 1;
            }
        }
        else if(asd == ArrowShiftDirection.DOWN)
        {
            ++mapSelected;

            if(mapSelected > NUMBER_OF_MAPS - 1)
            {
                mapSelected = 0;
            }
        }
    }

    /**
     * Positions and draws the arrow and character sprites for a given player
     */
    public void drawAssets()
    {
        arrow.setTexture(arrow.getTexture(mapSelected, ready));
        arrow.setPosition(arrow.getXPos(mapSelected), arrow.getYPos(mapSelected));
        GameScene.getGameScene().draw(arrow);

        GameScene.getGameScene().draw(playerOneCharacter);
        GameScene.getGameScene().draw(playerTwoCharacter);
    }

    /**
     * Loads the background image of the menu to the window as well as contextual sprites.
     * Initiates the keyboard event listening to allow for menu interaction.
     * @return an integer array containing the values of the characters selected by the players if
     * both the players have made themselves ready to play, else false if the menu has been closed
     */
    public void initialiseMenu()
    {
        ready = false;
        GameScene.getGameScene().clear();
    }

    public void setCharacterSprites(int[] charactersSelected)
    {
        playerOneCharacter.setTexture(playerOneCharacter.getTexture(charactersSelected[0], GameScene.Team.First, menuID));
        playerTwoCharacter.setTexture(playerTwoCharacter.getTexture(charactersSelected[1], GameScene.Team.Second, menuID));
    }

    /**
     * Draws the background image and the contextual sprites onto the window.
     */
    public void draw()
    {
        GameScene.getGameScene().clear();
        GameScene.getGameScene().draw(menuSprite);
        drawAssets();
        GameScene.getGameScene().display();
    }

    /**
     * Initiates the game itself and exits the menu. Called if a menu has been selected.
     * @return an integer array of length 2, containing the values of the selected characters
     */
    public int progress()
    {
        draw();
        try{ Thread.sleep(1000); } catch(Exception e){ e.printStackTrace(); }
        // Random map selected
        if(mapSelected == RANDOM_MAP)
        {
            Random r = new Random();
            mapSelected = r.nextInt(NUMBER_OF_MAPS - 1);
        }
        // Removed the offset that was created by the random map value being present
        else if(mapSelected > RANDOM_MAP)
        {
            --mapSelected;
        }
        return mapSelected;
    }

    /**
     * Listens for keyboard interactions from both players, and executes the required steps for the action.
     * @return true if ready to progress, else false.
     */
    public boolean detectEvents()
    {
        draw();
        Event event;
        while(true)
        {
            // waits until an event occurs to continue program execution
            event = GameScene.getGameScene().waitEvent();

            if(event.type == KEY_PRESSED)
            {
                switch(((KeyEvent)event).key)
                {
                    case W:
                        setMapSelected(ArrowShiftDirection.UP);
                        AudioHandler.playSound("menuSelect");
                        break;
                    case S:
                        setMapSelected(ArrowShiftDirection.DOWN);
                        AudioHandler.playSound("menuSelect");
                        break;
                    case ESCAPE:
                        return false;
                    case I:
                        AudioHandler.playSound("menuSelect");
                        ready = true;
                        return true;
                }
                draw();
            }
            else if(event.type == RESIZED)
            {
                draw();
            }
            else if(event.type == CLOSED)
            {
                GameScene.getGameScene().close();
                System.exit(0);
            }
        }
    }
}