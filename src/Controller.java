import java.util.Arrays;
import java.util.Random;

/**
 * The Controller class acts as a bridge between the user and the game. Handling of motion is currently the only function, but the idea is to also handle firing projectiles, swapping, using items and using abilities.
 * The control inputs are detected while handling window events, so this class needs to be given a reference to an input mapping.
 *
 * This class will be separate from menu controllers.
 */
public class Controller implements Runnable
{
    private static int controllerCounter = 1; //Thread name debug

    private AbstractPlayer player;
    private long lastChange = 0;
    private long lastAbility = 0;
    private long lastSwapped = 0;
    private boolean scrambled = false;

    private Thread checkThread;
    private boolean[] keyboard;
    private boolean paused = false;

    private long lockoutStart = 0;
    private int lockoutFor = -1;

    private boolean running;

    public enum Direction {UP, DOWN, LEFT, RIGHT}
    
    /**
     * Create a new instance of a Controller.
     */
    public Controller(boolean[] kb, Player body)
    {
        player = body;
        keyboard = kb;
        running = true;
        checkThread = new Thread(this, "Controller " + controllerCounter++);
        checkThread.start();

        player.addController(this);
    }

    /**
     * Pauses the controller and stops handling keyboard inputs.
     */
    public synchronized void pause()
    {
        paused = true;
    }

    /**
     * Unpauses the controller. At this point, this wakes the game up and doesn't register keypress changes.
     */
    public synchronized void unpause()
    {
        paused = false;
        notifyAll();
    }

    /**
     * Runs the controller. Only to be handled by the thread inside the controller. Outside handling should not be used.
     */
    public void run()
    {
        while(running)
        {
            if(paused)
            {
                if (lockoutFor == -1)
                {
                    synchronized (this)
                    {
                        try
                        {
                            wait();
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                else if (lockoutStart - System.currentTimeMillis() < lockoutFor)
                {
                    paused = false;
                    lockoutFor = -1;
                }
            }
            else
            {
                if (keyboard[0])
                {
                    player.tweakY(-1);
                    player.getCharacter().makeFootstep();
                }
                if (keyboard[1])
                {
                    player.tweakY(1);
                    player.getCharacter().makeFootstep();
                }
                if (keyboard[2])
                {
                    player.tweakX(-1);
                    player.getCharacter().makeFootstep();
                }
                if (keyboard[3])
                {
                    player.tweakX(1);
                    player.getCharacter().makeFootstep();
                }
                if(keyboard[4])
                {
                    player.setSpriteTextures(keyboard);
                    player.fire(Direction.UP);
                }
                if(keyboard[5])
                {
                    player.setSpriteTextures(keyboard);
                    player.fire(Direction.DOWN);
                }
                if(keyboard[6])
                {
                    player.setSpriteTextures(keyboard);
                    player.fire(Direction.LEFT);
                }
                if(keyboard[7])
                {
                    player.setSpriteTextures(keyboard);
                    player.fire(Direction.RIGHT);
                }
                if(keyboard[8])
                {
                  if(System.currentTimeMillis() - lastChange > 500)
                  {
                      if(player instanceof Player)
                      {
                          ((Player)player).changeMode();
                      }
                      lastChange = System.currentTimeMillis();
                  }
                }
                if(keyboard[9])
                {
                    if(System.currentTimeMillis() - lastAbility > 500)
                    {
                        if(player instanceof Player)
                        {
                            ((Player)player).useAbility();
                        }
                        lastAbility = System.currentTimeMillis();
                    }
                }
                if(keyboard[10])
                {
                    if(System.currentTimeMillis() - lastSwapped > 200)
                    {
                        if (player instanceof Player)
                        {
                            ((Player) player).swapKeypress();
                        }
                        lastSwapped = System.currentTimeMillis();
                    }
                }
            }

            player.setSpriteTextures(keyboard);
            try{Thread.sleep(50);}catch(Exception e){ e.printStackTrace(); }
        }
    }

    public void lockoutFor(int ms)
    {
        if(ms < (lockoutStart + lockoutFor - System.currentTimeMillis()))
            return;
        lockoutStart = System.currentTimeMillis();
        lockoutFor = -ms;
        paused = true;
    }

    public boolean[] getKeyboard() { return keyboard; }

    public void setKeyboard(boolean[] set)
    {
        pause();
        keyboard = set;
        unpause();
    }

    public void scramble()
    {
        if (!scrambled)
        {
            setKeyboard(shuffle());
            scrambled = true;
        }
    }

    private boolean[] shuffle()
    {
        boolean[] scrambled = keyboard;
        Random rand = new Random();

        for (int i = 0; i < scrambled.length; i++)
        {
            int randomIndexToSwap = rand.nextInt(scrambled.length);
            boolean temp = scrambled[randomIndexToSwap];
            scrambled[randomIndexToSwap] = scrambled[i];
            scrambled[i] = temp;
        }
        return scrambled;
    }

    public void reset()
    {
        running = false;
        try{ checkThread.join(); } catch(Exception e) { e.printStackTrace(); }
        player = null;
    }

    public void setBody(AbstractPlayer p)
    {
        player = p;
    }
}