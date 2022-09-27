import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import java.nio.file.Paths;

/**
 * Represents the black rectangles that the UI information sits on.
 */

public class UIBorder extends Sprite
{
    /**
     * Constructor for the UIBorder class.
     * @param team the team of the Player that this UIBorder instance belongs to.
     */
    public UIBorder(GameScene.Team team)
    {
        Texture t = new Texture();

        if(team == GameScene.Team.First)
        {
            try{t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/UI-Strip-Player-1.png")); } catch(Exception e) { e.printStackTrace(); }
        }
        else
        {
            try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/UI-Strip-Player-2.png")); } catch(Exception e) { e.printStackTrace(); }
            setPosition(TransformController.transformX(TransformController.getFullSize().x - 300), 0);
        }

        setTexture(t);

        float scaleMultiplier = TransformController.getScaleMultiplier(300, 300, TransformController.Domain.X);
        setScale(scaleMultiplier, scaleMultiplier);
    }
}
