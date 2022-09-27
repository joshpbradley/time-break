import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;
import java.io.File;

import static org.jsfml.window.event.Event.Type.*;

/**
 * Represents the menu that allows players to choose their characters before initiating a round of games.
 */
public class CharacterMenu implements AbstractMenu
{
    // The player that interacts with the menu using the left side of the keyboard.
    private PlayerCharacterMenu playerLeft;
    // The player that interacts with the menu using the left side of the keyboard.
    private PlayerCharacterMenu playerRight;
    // The Sprite that forms the background of this menu
    private Sprite menuSprite;
    // The ID of the menu, used to collect the correct character sprites from CharacterUISprites
    public static final int menuID = 0;

    /**
     * Constructor for the CharacterMenu class.
     */
    public CharacterMenu()
    {
        playerLeft = new PlayerCharacterMenu(GameScene.Team.First);
        playerRight = new PlayerCharacterMenu(GameScene.Team.Second);

        Texture menuTexture = new Texture();
        try{ menuTexture.loadFromFile(new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/Menu/Character-Menu/Character-Menu.png").toPath()); }catch(Exception e){ e.printStackTrace(); }
        menuSprite = new Sprite(menuTexture);
        menuSprite.scale((float)GameScene.getGameScene().getView().getSize().x/TransformController.getFullSize().x,(float)GameScene.getGameScene().getView().getSize().y/TransformController.getFullSize().y);
    }

    /**
     * Loads the background image of the menu to the window as well as contextual sprites.
     * Initiates the keyboard event listening to allow for menu interaction.
     * @return an integer array containing the values of the characters selected by the players if
     * both the players have made themselves ready to play, else false if the menu has been closed
     */
    public void initialiseMenu()
    {
        GameScene.getGameScene().clear();

       if(playerLeft.getReadyState())
            playerLeft.changeReadyState();

       if(playerRight.getReadyState())
            playerRight.changeReadyState();
    }

    /**
     * Draws the background image and the contextual sprites onto the window.
     */
    public void draw()
    {
        GameScene.getGameScene().clear();
        GameScene.getGameScene().draw(menuSprite);
        playerLeft.drawAssets();
        playerRight.drawAssets();
        GameScene.getGameScene().display();
    }

    /**
     * Initiates the game itself and exits the menu. Called if both players have marked themselves as ready
     * to begin the game.
     * @return an integer array of length 2, containing the values of the selected characters
     */
    public int[] progress()
    {
        draw();
        try{ Thread.sleep(1000); } catch(Exception e){ e.printStackTrace(); }
        return new int[] {playerLeft.getCharacterSelected(), playerRight.getCharacterSelected()};
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
                        playerLeft.setCharacterSelected(ArrowShiftDirection.UP);
                        AudioHandler.playSound("menuSelect");
                        break;
                    case S:
                        playerLeft.setCharacterSelected(ArrowShiftDirection.DOWN);
                        AudioHandler.playSound("menuSelect");
                        break;
                    case UP:
                        playerRight.setCharacterSelected(ArrowShiftDirection.UP);
                        AudioHandler.playSound("menuSelect");
                        break;
                    case DOWN:
                        playerRight.setCharacterSelected(ArrowShiftDirection.DOWN);
                        AudioHandler.playSound("menuSelect");
                        break;
                    case ESCAPE:
                        return false;
                    case I:
                        AudioHandler.playSound("menuSelect");
                        if(playerLeft.changeReadyState() && playerRight.getReadyState())
                        {
                            return true;
                        }
                        break;
                    case NUMPAD8:
                        AudioHandler.playSound("menuSelect");
                        if(playerRight.changeReadyState() && playerLeft.getReadyState())
                        {
                            return true;
                        }
                        break;
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