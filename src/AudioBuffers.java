import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import java.nio.file.Paths;

/**
 * This class is used to preload and store all of the sound effects that are needed for the game.
 */

public class AudioBuffers
{
    private static final SoundBuffer shot = new SoundBuffer();
    private static final SoundBuffer footstep1 = new SoundBuffer();
    private static final SoundBuffer footstep2 = new SoundBuffer();
    private static final SoundBuffer scream = new SoundBuffer();
    private static final SoundBuffer explosion = new SoundBuffer();
    private static final SoundBuffer menuSelect = new SoundBuffer();
    private static final SoundBuffer jet = new SoundBuffer();

    /**
     * This method preloads all the sounds into their respective buffers.
     */

    public static void loadBuffers()
    {
        try{ scream.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Audio/Willhelm.wav")); } catch(Exception e) { e.printStackTrace(); }
        try{ shot.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Audio/Gunshot.wav")); } catch(Exception e) { e.printStackTrace(); }
        try{ explosion.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Audio/Explosion.wav")); } catch(Exception e) { e.printStackTrace(); }
        try{ footstep1.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Audio/Footstep1.wav")); } catch(Exception e) { e.printStackTrace(); }
        try{ footstep2.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Audio/Footstep2.wav")); } catch(Exception e) { e.printStackTrace(); }
        try{ menuSelect.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Audio/MenuSelect.wav")); } catch(Exception e) { e.printStackTrace(); }
        try{ jet.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Audio/Jet.wav")); } catch(Exception e) { e.printStackTrace(); }
    }

    /**
     * Used to fetch specific sound buffers for playing.
     * @param name the name of the sound effect that is going to be played.
     * @return the SoundBuffer of said sound effect.
     */
    public static SoundBuffer getBuffer(String name)
    {
        switch(name)
        {
            case "shot":
                return shot;
            case "footstep1":
                return footstep1;
            case "footstep2":
                return footstep2;
            case "menuSelect":
                return menuSelect;
            case "explosion":
                return explosion;
            case "scream":
                return scream;
            case "jet":
                return jet;
            default: return null;
        }
    }

    /**
     * Creates a new AudioBuffers object.
     */
    private AudioBuffers(){}
}
