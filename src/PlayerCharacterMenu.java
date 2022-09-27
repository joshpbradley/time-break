import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;

/**
 * Represents a player that can interact with the menu.
 */
public class PlayerCharacterMenu
{
    // The number of characters that a player can potentially select.
    private static int NUMBER_OF_CHARACTERS = 5;
    // Indicates whether the player is ready to progress to the game.
    private boolean readyState;
    // Indicates the side of the keyboard that the player operates in the menu.
    private GameScene.Team playerTeam;
    // The value of the character that is currently under selection by the player.
    private int characterSelected;
    // The sprite that shows the players "ready" state and the character under selection.
    private ArrowMenuSprite arrow;
    // The sprite that shows the character currently under selection by the player.
    private CharacterUISprite character;

    /**
     * Constructor for the PlayerCharacterMenu class.
     * Scales and positions contextual sprites.
     * @param playerTeam the direction of the control scheme that the player shall interact with the keyboard
     */
    public PlayerCharacterMenu(GameScene.Team playerTeam)
    {
        // Initialises the first character selected by both players to be the highest character on the window.

        characterSelected = 0;
        readyState = false;
        this.playerTeam = playerTeam;

        float arrowXPos;
        float characterXPos;

        if(playerTeam == GameScene.Team.First)
        {
            arrowXPos = TransformController.transformX(962);
            characterXPos = TransformController.transformX(25);
        }
        else
        {
            arrowXPos = TransformController.transformX(1318);
            characterXPos = TransformController.transformX(1583);
        }

        arrow = new ArrowMenuSprite(arrowXPos, new float[] {
            TransformController.transformY((198 * 0.5f + 15) - (132f / 2f)),
            TransformController.transformY((198 * 1.5f + 30) - (132f / 2f)),
            TransformController.transformY((198 * 2.5f + 45) - (132f / 2f)),
            TransformController.transformY((198 * 3.5f + 60) - (132f / 2f)),
            TransformController.transformY((198 * 4.5f  + 75) - (132f / 2f))
        });
        character = new CharacterUISprite(characterXPos, TransformController.transformY(155));

        arrow.setScaleMultiplier(TransformController.getScaleMultiplier(20, 240, TransformController.Domain.X));
        arrow.scale(arrow.getScaleMultiplier(), arrow.getScaleMultiplier());
        character.setScaleMultiplier(TransformController.getScaleMultiplier(962, 962, TransformController.Domain.X));
        character.scale(character.getScaleMultiplier(), character.getScaleMultiplier());
    }

    /**
     * Positions and draws the arrow and character sprites for a given player
     */
    public void drawAssets()
    {
        arrow.setTexture(arrow.getTexture(readyState, playerTeam));
        arrow.setPosition(arrow.getXPos(), arrow.getYPos(characterSelected));
        GameScene.getGameScene().draw(arrow);

        character.setTexture(character.getTexture(characterSelected, playerTeam, CharacterMenu.menuID));
        character.setPosition(character.getXPos(), character.getYPos());
        GameScene.getGameScene().draw(character);
    }

    /**
     * Changes the ready state of a player and returns it.
     * @return the ready state boolean of a player. True means the player is ready to progress to the game;
     * false means the player is not ready.
     */
    public boolean changeReadyState()
    {
        readyState = !readyState;
        return readyState;
    }

    /**
     * Gets the ready state of the player.
     * @return the ready state boolean of a player. True means the player is ready to progress to the game;
     * false means the player is not ready.
     */
    public boolean getReadyState()
    {
        return readyState;
    }

    /**
     * Selects the character selected by a player after a character change keyboard event.
     * @param asd the direction which the player has shifted their arrow to select a character
     */
    public void setCharacterSelected(AbstractMenu.ArrowShiftDirection asd)
    {
        if(readyState)
        {
            return;
        }

        if(asd == AbstractMenu.ArrowShiftDirection.UP)
        {
            --characterSelected;

            if(characterSelected < 0)
            {
                characterSelected = NUMBER_OF_CHARACTERS - 1;
            }
        }
        else if(asd == AbstractMenu.ArrowShiftDirection.DOWN)
        {
            ++characterSelected;

            if(characterSelected > NUMBER_OF_CHARACTERS - 1)
            {
                characterSelected = 0;
            }
        }
    }

    /**
     * Returns the value of the character that is currently selected by the player.
     * @return the value of the character that is currently under selection by a player.
     */
    public int getCharacterSelected()
    {
        return characterSelected;
    }
}