import org.jsfml.audio.*;

/**
 * A utility class used for playing sound effects.
 */

public class AudioHandler
{
    /**
     * Fetches the buffer of the given sound effect name and then plays it. In order to make
     * sure it is not garbage collected before it's done, the sound effect is also added to
     * GameScene. It also makes sure that the volume of loud sound effects is equalized.
     * @param sfxName the name of the sound effect that AudioBuffers should fetch.
     */

    public static void playSound(String sfxName)
    {
        SoundBuffer sb = new SoundBuffer(AudioBuffers.getBuffer(sfxName));
        Sound sfx = new Sound(sb);
        if(sfxName.equals("shot")) sfx.setVolume(30);
        else if(sfxName.equals("menuSelect")) sfx.setVolume(30);
        GameScene.getGameScene().addNewSound(sfx);
        sfx.play();
    }

    /**
     * Creates a new AudioHandler.
     */
    private AudioHandler(){}
}