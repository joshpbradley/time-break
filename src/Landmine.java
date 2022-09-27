import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import java.nio.file.Paths;

public class Landmine extends Sprite implements Item, CollisionItem, Explodable
{
    private Player itemOwner;
    private long deployTime;
    private static Texture t;

    public static final float initialScaling = 0.9f;


    public Landmine()
    {
        setScale(initialScaling, initialScaling);
    }

    @Override
    public int getID()
    {
        return 6;
    }

    @Override
    public Player getItemOwner()
    {
        return itemOwner;
    }

    @Override
    public void setItemOwner(Player itemOwner)
    {
        this.itemOwner = itemOwner;
        t = AllTextureFiles.getItemTexture(itemOwner);
    }

    @Override
    public void use(Controller.Direction direction) { }

    public void use()
    {
        deployTime = System.currentTimeMillis();
        setTexture(t);
        setOrigin(getLocalBounds().left + getLocalBounds().width, getLocalBounds().top + getLocalBounds().height);
        setPosition(itemOwner.getGlobalCentrePoint());
        GameScene.getGameScene().add(this);
        itemOwner.setItem(null);
    }

    @Override
    public void collision(CollisionItem collideWith)
    {
        if(collideWith instanceof Player && System.currentTimeMillis() - deployTime > 1000)
        {
            detonate();
        }
    }

    @Override
    public void update(){}

    @Override
    public GameScene.Team getTeam()
    {
        return GameScene.Team.Neutral;
    }

    @Override
    public void applyExplosionEffects(AbstractPlayer playerAfflicted, boolean firstHit)
    {
        if(firstHit)
        {
            playerAfflicted.takeDamage(50);
        }
        else
        {
            playerAfflicted.takeDamage(5);
        }
    }

    @Override
    public void applyExplosionEffects(MapTile tileAfflicted, boolean firstHit)
    {
        if(firstHit)
        {
            tileAfflicted.takeDamage(50);
        }
        else
        {
            tileAfflicted.takeDamage(5);
        }

        tileAfflicted.isDestroyed();
    }

    @Override
    public void detonate()
    {
        GameScene.getGameScene().remove(this);
        GameScene.getGameScene().add(new Explosion(this, 2000, 2, (int)(getGlobalBounds().left + getGlobalBounds().width/2), (int)(getGlobalBounds().top + getGlobalBounds().height/2)));
    }

    @Override
    public Sprite getItemSprite()
    {
        Texture t = new Texture();
        try{ t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Items/Item-Models/Landmine.png")); }catch(Exception e){ e.printStackTrace(); }
        Sprite s = new Sprite(t);
        s.setScale(2f, 2f);
        s.setOrigin(  s.getLocalBounds().width/2,  s.getLocalBounds().height/2);
        return s;
    }

    @Override
    public Item clone()
    {
        return new Landmine();
    }
}
