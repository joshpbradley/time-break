import org.jsfml.audio.Sound;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.VideoMode;

import java.nio.file.Paths;
import java.util.ArrayList;

public class GameScene extends RenderWindow implements Runnable
{
    private static GameScene instance = new GameScene();
    private static Thread collisionThread = new Thread(instance, "Collision Detector");
    private Map map;
    public enum Team {First, Second, Neutral}

    // Separated for increased processing speed
    // Basically allows for checking collisions exclusively per team, better trade off than single array and always checking team.
    private ArrayList<CollisionItem> firstTeam = new ArrayList<>();
    private ArrayList<CollisionItem> secondTeam = new ArrayList<>();
    private ArrayList<CollisionItem> neutrals = new ArrayList<>();

    private long lastMapTilePickupUpdate = System.currentTimeMillis();
    private long lastCollisionUpdate = 0;
    private long lastDecay = 0;
    //With teams, the element at index 0 is the player.
    private ArrayList<Sound> currentAudio = new ArrayList<>();
    private long lastAudioUpdate = 0;
    private short updateStage = 0;


    private ArrayList<CollisionItem> addBuffer = new ArrayList<>();
    private ArrayList<CollisionItem> removeBuffer = new ArrayList<>();
    private boolean playing;
    private static UIBorder leftBorder;
    private static UIBorder rightBorder;
    private static UIPlayer leftUI;
    private static UIPlayer rightUI;
    private boolean collisionOccurred = false;
    private Sprite endGameBanner = null;

    private int[] player1StartPos;
    private int[] player2StartPos;

    private GameScene()
    {
        create(new VideoMode(21*90, 9*90, 16), "Time Break");
    }

    public void run()
    {
        while(true)
        {
            while (playing)
            {
                //Phase - Audio cleanup (removes any audio that has finished playing from the list)
                if (System.currentTimeMillis() > lastAudioUpdate + 100 && currentAudio.size() > 0)
                {
                    for (int i = currentAudio.size(); i > 0; i--)
                    {
                        if (currentAudio.get(i-1).getStatus() == Sound.Status.STOPPED)
                        {
                            currentAudio.remove(i-1);
                        }
                    }
                    lastAudioUpdate = System.currentTimeMillis();
                }

                boolean firstRun = true;
                //Phase - Collisions
                for (CollisionItem first : firstTeam)
                {
                    for (CollisionItem second : secondTeam)
                    {
                        first.update();
                        second.update();
                        if(first instanceof MovingBody && ((MovingBody) first).isOnGround())
                            map.getMapTileAtPosition(((MovingBody) first).getGlobalCentrePoint()).collision(first);
                        if(firstRun && second instanceof MovingBody && ((MovingBody) second).isOnGround())
                            map.getMapTileAtPosition(((MovingBody) second).getGlobalCentrePoint()).collision(second);

                        if (((Sprite) first).getGlobalBounds().intersection(((Sprite) second).getGlobalBounds()) != null)
                        {
                            first.collision(second); //First induces onto second
                            try
                            {
                                if (!(first instanceof Drone || second instanceof Drone))
                                {
                                    Thread.sleep(25);
                                }
                            } catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }

                        collisionOccurred = true;
                    }
                    firstRun = false;
                }

                for (CollisionItem neutral : neutrals)
                {
                    neutral.update();

                    if (neutral instanceof Grenade && !((Grenade) neutral).getActive())
                    {
                        continue;
                    }

                    if (((Sprite) firstTeam.get(0)).getGlobalBounds().intersection(((Sprite) neutral).getGlobalBounds()) != null)
                    {
                        neutral.collision(firstTeam.get(0));
                    }
                    if (((Sprite) secondTeam.get(0)).getGlobalBounds().intersection(((Sprite) neutral).getGlobalBounds()) != null)
                    {
                        neutral.collision(secondTeam.get(0));
                    }

                    checkDetachedCollision((Player) firstTeam.get(0), neutral);
                    checkDetachedCollision((Player) secondTeam.get(0), neutral);

                    if (neutral instanceof Sprite)
                    {
                        Vector2f neutralPosition = new Vector2f((((Sprite) neutral).getGlobalBounds().left + ((Sprite) neutral).getGlobalBounds().width / 2),
                                ((Sprite) neutral).getGlobalBounds().top + ((Sprite) neutral).getGlobalBounds().height / 2);

                        map.getMapTileAtPosition(neutralPosition).collision(neutral);
                    }
                    collisionOccurred = true;
                }

                if(collisionOccurred)
                {
                    lastCollisionUpdate = System.currentTimeMillis();
                    collisionOccurred = false;
                }

                //Phase - Status application
                getPlayer1().applyStatusEffects();
                if(getPlayer1().getItem() instanceof AbstractPlayer)
                {
                    ((AbstractPlayer) getPlayer1().getItem()).applyStatusEffects();
                }

                getPlayer2().applyStatusEffects();
                if(getPlayer2().getItem() instanceof AbstractPlayer)
                {
                    ((AbstractPlayer) getPlayer2().getItem()).applyStatusEffects();
                }

                if(updateStage == 0)
                {
                    clearAdd();
                    clearRemove();
                }

                //Run the map's decay method once every second
                if(System.currentTimeMillis() > lastDecay + 1000)
                {
                    map.decay();
                    lastDecay = System.currentTimeMillis();
                }

                map.checkPickups();

                //Phase - Player Stat Check
                if (getPlayer1().getHitPoints() <= 0 && getPlayer2().getHitPoints() <= 0)
                {
                    endRound(Team.Neutral);
                }
                else if (getPlayer1().getHitPoints() <= 0)
                {
                    endRound(Team.First);
                }
                else if (getPlayer2().getHitPoints() <= 0)
                {
                    endRound(Team.Second);
                }

                updateStage++;
                if(updateStage == 3)
                    updateStage = 0;
            }
            try{ Thread.sleep(1); }catch(Exception e){ e.printStackTrace(); }
        }
    }

    public MapTile getTileFromPlayer(Player p)
    {
        return map.getMapTileAtPosition(p.getGlobalCentrePoint());
    }

    public void setPlayerStartPos(int[] player1Start, int[] player2Start)
    {
        this.player1StartPos = player1Start;
        this.player2StartPos = player2Start;
    }

    private void endRound(Team team)
    {
        getPlayer1().setSpeedX(0);
        getPlayer1().setSpeedY(0);

        getPlayer2().setSpeedX(0);
        getPlayer2().setSpeedY(0);

        AudioHandler.playSound("scream");
        getPlayer1().lockout(2100);
        getPlayer2().lockout(2100);

        int firstWins;
        int secondWins;

        if(team == Team.First)
        {
            getPlayer2().wonRound();
            secondWins = getPlayer2().getRoundWins();

            if(secondWins >= 3)
            {
                endGame(Team.Second);
                getPlayer1().setTexture( getPlayer1().getDead());
            }
        }
        else if(team == Team.Second)
        {
            getPlayer1().wonRound();
            firstWins = getPlayer1().getRoundWins();

            if(firstWins >= 3)
            {
                endGame(Team.First);
                getPlayer2().setTexture( getPlayer2().getDead());
            }
        }

        if(playing)
        {
            try{ Thread.sleep(2000); }catch(Exception e){ e.printStackTrace(); }

            if(getPlayer1().hasActiveDrone())
            {
                ((Drone)getPlayer1().getItem()).removeDrone();
            }
            else
            {
                if(getPlayer1().getItem() instanceof CollisionItem)
                {
                    remove((CollisionItem)getPlayer1().getItem());
                }
                getPlayer1().setItem(null);
            }

            if(getPlayer2().hasActiveDrone())
            {
                ((Drone)getPlayer2().getItem()).removeDrone();
            }
            else
            {
                if(getPlayer2().getItem() instanceof CollisionItem)
                {
                    remove((CollisionItem)getPlayer2().getItem());
                }
                getPlayer2().setItem(null);
            }

            if (map instanceof CrystalMap)
            {
                map = new CrystalMap(map.mapX, map.mapY, getPlayer1(), getPlayer2());
            }
            else if (map instanceof LavaMap)
            {
                map = new LavaMap(map.mapX, map.mapY);
            }
            else if (map instanceof IceMap)
            {
                map = new IceMap(map.mapX, map.mapY);
            }
            else if (map instanceof RiverMap)
            {
                map = new RiverMap(map.mapX, map.mapY);
            }
            else if (map instanceof SpaceMap)
            {
                map = new SpaceMap(map.mapX, map.mapY);
            }
            else if (map instanceof SwampMap)
            {
                map = new SwampMap(map.mapX, map.mapY);
            }

            this.addMapElement(map);
            int[][] playerPos = map.spawnPlayers(getPlayer1(), getPlayer2());

            getPlayer1().setPosition(playerPos[0][0], playerPos[0][1]);
            getPlayer2().setPosition(playerPos[1][0], playerPos[1][1]);
            this.setPlayerStartPos(playerPos[0], playerPos[1]);

            getPlayer1().reset();
            getPlayer2().reset();

            clearAdd();
            clearRemove();
        }
    }

    private void endGame(Team team)
    {
        playing = false;
        float scale;

        Texture endGameBannerTexture = new Texture();

        if(team == Team.First)
        {
            try { endGameBannerTexture.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/End-Game/Player-1-Wins.png")); }catch(Exception e){ e.printStackTrace(); }
            scale = TransformController.getScaleMultiplier(125, 125, TransformController.Domain.Y);
        }
        else if(team == Team.Second)
        {
            try { endGameBannerTexture.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/End-Game/Player-2-Wins.png")); }catch(Exception e){ e.printStackTrace(); }
            scale = TransformController.getScaleMultiplier(127, 127, TransformController.Domain.Y);
        }
        else
        {
            try { endGameBannerTexture.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/End-Game/Draw.png")); }catch(Exception e){ e.printStackTrace(); }
            scale = TransformController.getScaleMultiplier(125, 125, TransformController.Domain.Y);
        }

        endGameBanner = new Sprite(endGameBannerTexture);
        endGameBanner.setScale(scale, scale);
    }

    public void startGame()
    {
        playing = true;

        if(!collisionThread.isAlive())
        {
            collisionThread.start();
        }
    }

    public void reset()
    {
        //Clear all
        firstTeam.clear();
        secondTeam.clear();
        neutrals.clear();
        addBuffer.clear();
        removeBuffer.clear();
        endGameBanner = null;
    }

    public boolean isPlaying()
    {
        return playing;
    }

    public void addLeaders(Player first, Player second)
    {
        firstTeam.add(first);
        secondTeam.add(second);
    }

    public synchronized void add(CollisionItem add)
    {
        addBuffer.add(add);
    }

    public synchronized void remove(CollisionItem remove)
    {
        removeBuffer.add(remove);
    }

    private synchronized void clearAdd()
    {
        ArrayList<CollisionItem> holder = (ArrayList<CollisionItem>) addBuffer.clone();
        for(CollisionItem add: holder)
        {
            if(add instanceof MovingBody)
            {
                if (add.getTeam() == Team.First)
                {
                    firstTeam.add(add);
                }
                else if (add.getTeam() == Team.Second)
                {
                    secondTeam.add(add);
                }
                else
                {
                    neutrals.add(add);
                }
            }
            else
            {
                neutrals.add(add);
            }
        }
        addBuffer.removeAll(holder);
    }

    private synchronized void clearRemove()
    {
        ArrayList<CollisionItem> holder = (ArrayList<CollisionItem>) removeBuffer.clone();
        for(CollisionItem remove: removeBuffer)
        {
            if(remove instanceof MovingBody)
            {
                if (remove.getTeam() == Team.First)
                {
                    firstTeam.remove(remove);
                }
                else if (remove.getTeam() == Team.Second)
                {
                    secondTeam.remove(remove);
                }
                else
                {
                    neutrals.remove(remove);
                }
            }
            else
            {
                neutrals.remove(remove);
            }
        }
        removeBuffer.removeAll(holder);
    }

    public void drawOn()
    {
        ArrayList<MapTile> hoverTiles = new ArrayList<>();

        clear(new Color(0, 75, 75));
        for (MapTile[] ma : map.getMapTiles())
        {
            for (MapTile m : ma)
            {
                if(m.getType() == 'g')
                    hoverTiles.add(m);
                else
                    m.draw(this);
                if(m.getHeldItem() instanceof Item)
                {
                    Sprite itemSprite = ((Item) m.getHeldItem()).getItemSprite();
                    itemSprite.setPosition(m.getGlobalBounds().left + m.getGlobalBounds().width/2,
                            m.getGlobalBounds().top + m.getGlobalBounds().height/2);
                    draw(itemSprite);
                }
                else if(m.getHeldItem() instanceof Drawable)
                    draw((Drawable) m.getHeldItem());
            }
        }

        //Draw moving objects
        ArrayList<CollisionItem> toDraw = new ArrayList<>();
        toDraw.addAll(firstTeam);
        toDraw.addAll(secondTeam);
        toDraw.addAll(neutrals);

        //Exception being handled is a null pointer exception.
        try
        {
            for (CollisionItem b : toDraw)
            {
                if(b instanceof Drawable)
                {
                    draw((Drawable) b);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return; //Reached the end. This exception wouldn't impact anything, purely down to the iterator not knowing how long the collection is properly.
        }
        
        if(!getPlayer1().isOnGround())
        {
            getPlayer2().draw(this);
            getPlayer1().draw(this);
        }
        else
        {
            getPlayer1().draw(this);
            getPlayer2().draw(this);
        }

        //Redraw long grass or blizzard to appear in front of player
        Texture t = new Texture();
        try
        {
            if (map instanceof IceMap) t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/White-Cloud.png"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        for (MapTile[] ma : map.getMapTiles())
        {
            for (MapTile m : ma)
            {
                if(m.getType() == 'g') draw(m);
                if (map instanceof IceMap && m.getStatusEffect() instanceof Burning)
                {
                    Sprite s = new Sprite();
                    s.setTexture(t);
                    s.setScale(0.9f,0.9f);
                    s.setPosition(m.getXPos(), m.getYPos());
                    draw(s);
                }
            }
        }

        leftUI.drawAssets();
        rightUI.drawAssets();
    }

    public void addMapElement(Map map)
    {
        this.map = map;
    }

    private Player getPlayer1()
    {
        return (Player) firstTeam.get(0);
    }

    private Player getPlayer2()
    {
        return (Player) secondTeam.get(0);
    }

    /**
     * Returns the opponent of the player who was passed into the function.
     * @param player the caller of this function.
     * @return the opponent of the caller.
     */

    public Player getOtherPlayer(Player player)
    {
        if (player.getTeam() == Team.First)
        {
            return getPlayer2();
        }
        else
        {
            return getPlayer1();
        }
    }

    public static GameScene getGameScene()
    {
        return instance;
    }

    /**
     * Adds the given sound to the currentAudio list in order to protect it from garbage collection.
     * @param sound the sound that should be added.
     */
    public synchronized void addNewSound(Sound sound)
    {
        currentAudio.add(sound);
    }
    
    public void addGameBorders(UIBorder lB, UIBorder rB)
    {
        leftBorder = lB;
        rightBorder = rB;
    }

    public void addGameUI(UIPlayer lUI, UIPlayer rUI)
    {
        leftUI = lUI;
        rightUI = rUI;
    }

    public UIBorder getLeftBorder()
    {
        return leftBorder;
    }

    public UIBorder getRightBorder()
    {
        return rightBorder;
    }

    private void checkDetachedCollision(Player p, CollisionItem collideWith)
    {
        if(p.getItem() instanceof AbstractPlayer)
        {
            if(((Sprite)p.getItem()).getGlobalBounds().intersection(((Sprite)collideWith).getGlobalBounds()) != null)
            {
                collideWith.collision(((CollisionItem)p.getItem()));
            }
        }
    }

    public Map getMap()
    {
        return map;
    }

    public void drawEndGame()
    {
        drawOn();

        try{ Thread.sleep(1000); }catch(Exception e){e.printStackTrace();}

        while(endGameBanner == null || endGameBanner.getScale().x == 1)
        {
            try{ Thread.sleep(100); }catch(Exception e){e.printStackTrace();}
        }

        endGameBanner.setOrigin(endGameBanner.getLocalBounds().width/2, endGameBanner.getLocalBounds().height/2);
        endGameBanner.setPosition(getView().getSize().x/2, getView().getSize().y/2);
        draw(endGameBanner);
        display();

        try{ Thread.sleep(2000); }catch(Exception e){e.printStackTrace();}
    }
}
