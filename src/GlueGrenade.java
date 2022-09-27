import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.nio.file.Paths;

public class GlueGrenade extends Grenade
{
    public GlueGrenade()
    {
        super (3, 20, 3, 4, 0.95f);
    }

    @Override
    public void applyExplosionEffects(AbstractPlayer playerAfflicted, boolean firstHit)
    {
        if(firstHit)
        {
            playerAfflicted.takeDamage(getDamage());
            playerAfflicted.addStatusEffect(new Slow(3000));
        }
        else
        {
            playerAfflicted.takeDamage(5);
        }
    }

    @Override
    public Sprite getItemSprite()
    {
        Texture t = new Texture();
        try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Items/Item-Models/Glue-Grenade.png")); }catch(Exception e){ e.printStackTrace(); }
        Sprite s = new Sprite(t);
        s.setScale(2f, 2f);
        s.setOrigin(  s.getLocalBounds().width/2,  s.getLocalBounds().height/2);
        return s;
    }

    public Item clone()
    {
        return new GlueGrenade();
    }
}
