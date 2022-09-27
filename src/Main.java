import org.jsfml.audio.*;
import org.jsfml.graphics.*;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        GameScene gameScene = GameScene.getGameScene();

        Image icon = new Image();
        try{ icon.loadFromFile(new File(AllTextureFiles.PARENT_FILE_STRING + "/assets/Icon.png").toPath());}catch(Exception e){ e.printStackTrace(); }
        gameScene.setIcon(icon);
        AudioBuffers.loadBuffers();

        Listener.setPosition(gameScene.getView().getSize().x/2, gameScene.getView().getSize().y/2, 0);
        gameScene.setKeyRepeatEnabled(false);
        gameScene.setFramerateLimit(60);

        Player player1 = null;
        Player player2 = null;

        while(true)
        {
            /* MENU PHASE */
            Music menuTheme = new Music();
            menuTheme.setVolume(20);
            menuTheme.setLoop(true);
            try
            {
                menuTheme.openFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Audio/Theme.wav"));
                menuTheme.play();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                menuTheme = null;
            }

            CharacterMenu characterMenu = new CharacterMenu();
            MapMenu mapMenu = new MapMenu();

            int[] charactersSelected;
            Character[] characterArr = new Character[2];
            int mapSelected;
            Map map = null;

            boolean startGame = false;
            while(!startGame)
            {
                characterMenu.initialiseMenu();
                if(characterMenu.detectEvents() == false)
                {
                    return;
                }
                else
                {
                    charactersSelected = characterMenu.progress();
                }

                mapMenu.initialiseMenu();
                mapMenu.setCharacterSprites(charactersSelected);

                if (mapMenu.detectEvents() == false)
                {
                    continue;
                }
                else
                {
                    mapSelected = mapMenu.progress();
                    startGame = true;
                }
                try
                {
                    menuTheme.stop();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                for (int i = 0; i < 2; ++i) {
                    switch (charactersSelected[i]) {
                        case 0:
                            characterArr[i] = new Knight();
                            break;
                        case 1:
                            characterArr[i] = new Scientist();
                            break;
                        case 2:
                            characterArr[i] = new Ninja();
                            break;
                        case 3:
                            characterArr[i] = new Cowboy();
                            break;
                        case 4:
                            characterArr[i] = new Alien();
                    }
                }

                switch (mapSelected)
                {
                    case 0:
                        map = new SpaceMap(32, 18);
                        break;
                    case 1:
                        map = new RiverMap(32, 18);
                        break;
                    case 2:
                        map = new CrystalMap(32, 18, player1, player2);
                        break;
                    case 3:
                        map = new SwampMap(32, 18);
                        break;
                    case 4:
                        map = new LavaMap(32, 18);
                        break;
                    default:
                        map = new IceMap(32, 18);
                }
            }
            try
            {
                menuTheme.stop();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            player1 = new Player(characterArr[0], GameScene.Team.First);
            player2 = new Player(characterArr[1], GameScene.Team.Second);

            if (player1.getCharacter().getID() == player2.getCharacter().getID())
            {
                player1.setPlayerIndicator();
                player2.setPlayerIndicator();
            }

            gameScene.addMapElement(map);
            int[][] playerPos = map.spawnPlayers(player1, player2);
            gameScene.setPlayerStartPos(playerPos[0], playerPos[1]);

            /* END OF MENU PHASE */

            /*
             * Setting up Controller mapping
             * This is being done in main so as to sync up with window event polling.
             */
            boolean[] keyboard1 = new boolean[11];
            boolean[] keyboard2 = new boolean[11];

            UIBorder leftBorder = new UIBorder(GameScene.Team.First);
            UIBorder rightBorder = new UIBorder(GameScene.Team.Second);

            GameScene.getGameScene().addGameBorders(leftBorder, rightBorder);

            UIPlayer player1UI = new UIPlayer(player1, leftBorder);
            UIPlayer player2UI = new UIPlayer(player2, rightBorder);

            GameScene.getGameScene().addGameUI(player1UI, player2UI);

            //Texture setup and building sprites and controllers from there.
            player1.setSpriteTextures(keyboard1);
            player2.setSpriteTextures(keyboard2);

            // Player instance variables allow the crystals in the map to track player location.
            if(map instanceof CrystalMap)
            {
                ((CrystalMap)map).setPlayer1(player1);
                ((CrystalMap)map).setPlayer2(player2);
            }

            //Sets initial textures
            player1.getWeapon().setTexture(AllTextureFiles.getWeaponTexture(player1));
            player1.getWeapon().setTexture(AllTextureFiles.getWeaponTexture(player2));

            gameScene.addLeaders(player1, player2);

            Controller control = new Controller(keyboard1, player1);
            Controller control2 = new Controller(keyboard2, player2);

            Music mainTheme = new Music();
            mainTheme.setLoop(true);
            mainTheme.setVolume(10);
            try
            {
                mainTheme.openFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Audio/Theme.wav"));
                mainTheme.play();
            }
            catch(Exception e)
            {
                e.printStackTrace();
                mainTheme = null;
            }

            gameScene.startGame();

            //Infinite loop for the duration of the window's existence. Mostly just window event handling, also includes window visual updates.
            while(gameScene.isPlaying())
            {
                gameScene.clear(new Color(0, 0, 0));
                for(Event e : gameScene.pollEvents())
                {
                    if(e.type == Event.Type.CLOSED)
                    {
                        GameScene.getGameScene().close();
                        System.exit(-1);
                    }
                    else if(e.type == Event.Type.LOST_FOCUS)
                    {
                        control.pause();
                        control2.pause();
                    }
                    else if (e.type == Event.Type.GAINED_FOCUS)
                    {
                        control.unpause();
                        control2.unpause();
                    }
                    else if(e.type == Event.Type.KEY_PRESSED)
                    {
                        // player 1 movement
                        if (((KeyEvent) e).key == Keyboard.Key.W)
                            keyboard1[0] = true;
                        else if (((KeyEvent) e).key == Keyboard.Key.S)
                            keyboard1[1] = true;
                        else if (((KeyEvent) e).key == Keyboard.Key.A)
                            keyboard1[2] = true;
                        else if (((KeyEvent) e).key == Keyboard.Key.D)
                            keyboard1[3] = true;
                            // player 1 firing direction
                        else if (((KeyEvent) e).key == Keyboard.Key.I)
                            keyboard1[4] = true;
                        else if (((KeyEvent) e).key == Keyboard.Key.K)
                            keyboard1[5] = true;
                        else if (((KeyEvent) e).key == Keyboard.Key.J)
                            keyboard1[6] = true;
                        else if (((KeyEvent) e).key == Keyboard.Key.L)
                            keyboard1[7] = true;
                        else if (((KeyEvent) e).key == Keyboard.Key.U)
                            keyboard1[8] = true;
                        else if (((KeyEvent) e).key == Keyboard.Key.O)
                            keyboard1[9] = true;
                        else if (((KeyEvent) e).key == Keyboard.Key.SEMICOLON)
                            keyboard1[10] = true;
                        // player 2 movement
                        if (((KeyEvent) e).key == Keyboard.Key.UP)
                            keyboard2[0] = true;
                        else if (((KeyEvent) e).key == Keyboard.Key.DOWN)
                            keyboard2[1] = true;
                        else if (((KeyEvent) e).key == Keyboard.Key.LEFT)
                            keyboard2[2] = true;
                        else if (((KeyEvent) e).key == Keyboard.Key.RIGHT)
                            keyboard2[3] = true;
                            // player 2 firing direction
                        else if (((KeyEvent) e).key == Keyboard.Key.NUMPAD8)
                            keyboard2[4] = true;
                        else if (((KeyEvent) e).key == Keyboard.Key.NUMPAD5)
                            keyboard2[5] = true;
                        else if (((KeyEvent) e).key == Keyboard.Key.NUMPAD4)
                            keyboard2[6] = true;
                        else if (((KeyEvent) e).key == Keyboard.Key.NUMPAD6)
                            keyboard2[7] = true;
                        else if (((KeyEvent) e).key == Keyboard.Key.NUMPAD7)
                            keyboard2[8] = true;
                        else if (((KeyEvent) e).key == Keyboard.Key.NUMPAD9)
                            keyboard2[9] = true;
                        else if(((KeyEvent) e).key == Keyboard.Key.ADD)
                            keyboard2[10] = true;
                    }
                    else if (e.type == Event.Type.KEY_RELEASED)
                    {
                        // player 1 movement
                        if (((KeyEvent) e).key == Keyboard.Key.W)
                            keyboard1[0] = false;
                        else if (((KeyEvent) e).key == Keyboard.Key.S)
                            keyboard1[1] = false;
                        else if (((KeyEvent) e).key == Keyboard.Key.A)
                            keyboard1[2] = false;
                        else if (((KeyEvent) e).key == Keyboard.Key.D)
                            keyboard1[3] = false;
                            // player 1 firing direction
                        else if (((KeyEvent) e).key == Keyboard.Key.I)
                            keyboard1[4] = false;
                        else if (((KeyEvent) e).key == Keyboard.Key.K)
                            keyboard1[5] = false;
                        else if (((KeyEvent) e).key == Keyboard.Key.J)
                            keyboard1[6] = false;
                        else if (((KeyEvent) e).key == Keyboard.Key.L)
                            keyboard1[7] = false;
                        else if (((KeyEvent) e).key == Keyboard.Key.U)
                            keyboard1[8] = false;
                        else if (((KeyEvent) e).key == Keyboard.Key.O)
                            keyboard1[9] = false;
                        else if (((KeyEvent) e).key == Keyboard.Key.SEMICOLON)
                            keyboard1[10] = false;
                            // player 2 movement
                        else if (((KeyEvent) e).key == Keyboard.Key.UP)
                            keyboard2[0] = false;
                        else if (((KeyEvent) e).key == Keyboard.Key.DOWN)
                            keyboard2[1] = false;
                        else if (((KeyEvent) e).key == Keyboard.Key.LEFT)
                            keyboard2[2] = false;
                        else if (((KeyEvent) e).key == Keyboard.Key.RIGHT)
                            keyboard2[3] = false;
                            // player 2 firing direction
                        else if (((KeyEvent) e).key == Keyboard.Key.NUMPAD8)
                            keyboard2[4] = false;
                        else if (((KeyEvent) e).key == Keyboard.Key.NUMPAD5)
                            keyboard2[5] = false;
                        else if (((KeyEvent) e).key == Keyboard.Key.NUMPAD4)
                            keyboard2[6] = false;
                        else if (((KeyEvent) e).key == Keyboard.Key.NUMPAD6)
                            keyboard2[7] = false;
                        else if (((KeyEvent) e).key == Keyboard.Key.NUMPAD7)
                            keyboard2[8] = false;
                        else if (((KeyEvent) e).key == Keyboard.Key.NUMPAD9)
                            keyboard2[9] = false;
                        else if(((KeyEvent) e).key == Keyboard.Key.ADD);
                            keyboard2[10] = false;
                    }
                }
                gameScene.drawOn();
                gameScene.display();
            }

            GameScene.getGameScene().drawEndGame();
            mainTheme.stop();
            GameScene.getGameScene().reset();
            control.reset();
            control2.reset();
        }
    }
}