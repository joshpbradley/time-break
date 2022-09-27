import org.jsfml.graphics.Texture;

import java.nio.file.Paths;

public class Sword extends Weapon
{
    private long lastHit = 0;

    public Sword()
    {
        super(10,15,5);
        resetSpriteTexture();
    }

    @Override
    void fire(Controller.Direction direction)
    {
        if(System.currentTimeMillis() - (lastHit + getFireRate()) > 0)
        {
            Player opponent = GameScene.getGameScene().getOtherPlayer((Player) getWeaponOwner()); //Only a player will hold a sword, drones won't.
            if (opponent.getGlobalBounds().intersection(getGlobalBounds()) != null)
            {
                opponent.takeDamage(getDamage());
            }
            lastHit = System.currentTimeMillis();
        }
    }


    @Override
    public void resetSpriteTexture()
    {
        Texture t = new Texture();

        if(System.currentTimeMillis() % 2 == 0)
        {
            try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Sword-Left.png")); }catch(Exception e){ e.printStackTrace(); }
        }
        else
        {
            try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Sword-Right.png")); }catch(Exception e){ e.printStackTrace(); }
        }

        setTexture(t, true);
        setOrigin(getGlobalBounds().width/2, getGlobalBounds().height/2);
    }
}
