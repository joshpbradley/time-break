import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import java.nio.file.Paths;
import java.util.Random;

public class Cloud extends Sprite implements Runnable, CollisionItem
{
    private float drawTime;
    private float AoESize;
    private Random r = new Random();
    private char tileType;

    private static final String[] greenPaths = new String[]
    {
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Poison/1.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Poison/1-flip.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Poison/2.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Poison/2-flip.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Poison/3.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Poison/3-flip.png"
    };
    private static final Texture[] greenTextures = new Texture[greenPaths.length];
    private static final String[] bluePaths = new String[]
    {
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Blue-Crystal/1.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Blue-Crystal/1-flip.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Blue-Crystal/2.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Blue-Crystal/2-flip.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Blue-Crystal/3.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Blue-Crystal/3-flip.png"
    };
    private static final Texture[] blueTextures = new Texture[bluePaths.length];
    private static final String[] yellowPaths = new String[]
    {
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Yellow-Crystal/1.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Yellow-Crystal/1-flip.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Yellow-Crystal/2.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Yellow-Crystal/2-flip.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Yellow-Crystal/3.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Yellow-Crystal/3-flip.png"
    };
    private static final Texture[] yellowTextures = new Texture[yellowPaths.length];
    private long player1LastHit = -1;
    private long player2LastHit = -1;

    public Cloud(float drawTime, int AoESize, int posX, int posY, char tileType)
    {
        this.drawTime = drawTime;
        this.tileType = tileType;

        for(int i = 0; i < greenPaths.length; ++i)
        {
            greenTextures[i] = new Texture();
            try { greenTextures[i].loadFromFile(Paths.get(greenPaths[i])); }catch(Exception e){ e.printStackTrace(); }
        }

        for(int i = 0; i < bluePaths.length; ++i)
        {
            blueTextures[i] = new Texture();
            try { blueTextures[i].loadFromFile(Paths.get(bluePaths[i])); }catch(Exception e){ e.printStackTrace(); }
        }

        for(int i = 0; i < yellowPaths.length; ++i)
        {
            yellowTextures[i] = new Texture();
            try { yellowTextures[i].loadFromFile(Paths.get(yellowPaths[i])); }catch(Exception e){ e.printStackTrace(); }
        }

        switch(tileType)
        {
            case 'G':
                setTexture(greenTextures[0], true);
                break;
            case 'B':
                setTexture(blueTextures[0], true);
                break;
            case 'Y':
                setTexture(yellowTextures[0], true);
        }

        setTexture(new Texture());

        this.AoESize = TransformController.getScaleMultiplier(50, GameScene.getGameScene().getView().getSize().x/42*AoESize, TransformController.Domain.X);

        setOrigin(getLocalBounds().width/2, getLocalBounds().height/2);
        setPosition(posX, posY);

        new Thread(this).start();
    }

    @Override
    public void collision(CollisionItem collideWith)
    {
        if(tileType == 'G' && collideWith instanceof Player)
        {
            if(collideWith.getTeam() == GameScene.Team.First && (System.currentTimeMillis() - player1LastHit > 1000))
            {
                ((Player) collideWith).takeDamage(new Pistol().getDamage());
                player1LastHit = System.currentTimeMillis();
            }
            else if(collideWith.getTeam() == GameScene.Team.Second && (System.currentTimeMillis() - player2LastHit > 1000))
            {
                ((Player) collideWith).takeDamage(new Pistol().getDamage());
                player2LastHit = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void update() { }

    @Override
    public void run()
    {
        long startTime = System.currentTimeMillis();

        while(System.currentTimeMillis() < (startTime + (long)(drawTime * 1000)))
        {
            switch(tileType)
            {
                case 'G':
                    setTexture(greenTextures[r.nextInt(greenTextures.length)], true);
                    break;
                case 'B':
                    setTexture(blueTextures[r.nextInt(blueTextures.length)], true);
                    break;
                case 'Y':
                    setTexture(yellowTextures[r.nextInt(yellowTextures.length)], true);
            }
            float random = AoESize/2 + r.nextFloat()*(AoESize*1/2);
            setScale(random, random);
            setRotation(r.nextInt(360));
            try {Thread.sleep(120);} catch(Exception e) { e.printStackTrace(); };
        }
        while((getScale().x - 0.1) >= 0)
        {
            switch(tileType)
            {
                case 'G':
                    setTexture(greenTextures[r.nextInt(greenTextures.length)], true);
                    break;
                case 'B':
                    setTexture(blueTextures[r.nextInt(blueTextures.length)], true);
                    break;
                case 'Y':
                    setTexture(yellowTextures[r.nextInt(yellowTextures.length)], true);
            }
            scale(0.7f, 0.7f);
            setRotation(r.nextInt(360));
            try{ Thread.sleep(80); }catch(Exception e){ e.printStackTrace(); }
        }
        GameScene.getGameScene().remove(this);
    }

    public GameScene.Team getTeam()
    {
        return GameScene.Team.Neutral;
    }
}
