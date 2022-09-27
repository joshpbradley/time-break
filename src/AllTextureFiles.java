import java.nio.file.Paths;
import java.util.*;
import org.jsfml.graphics.Texture;
import java.io.File;
import java.io.IOException;

public final class AllTextureFiles
{
    public static final String PARENT_FILE_STRING;

    static
    {
        String tmp = null;
        try
        {
          tmp = new File("").getCanonicalFile().getPath();
        }
        catch (IOException ioe)
        {
          ioe.printStackTrace();
        }

        PARENT_FILE_STRING = tmp;
      }

    private static final int WEAPON_MODEL_OFFSET = 60;
    private static final int WEAPON_OFFSET = 84;
    private static final int DRONE_CONTROLLER_OFFSET = 308;
    private static final int IDLE_OFFSET = 312;
    private static final int AMMO_OFFSET = 316;
    private static final int GRENADE_MODEL_OFFSET = 326;
    private static final int GRENADE_OFFSET = 331;
    private static final int ITEM_MODEL_OFFSET = 411;
    private static final int ITEM_OFFSET = 416;
    private static final int SCORE_OFFSET = 418;

    private static final List<String> textureFiles = new ArrayList<>(Arrays.asList(
        // Knight Files
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Knight/Back.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Knight/Back-LFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Knight/Back-RFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Knight/Centre.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Knight/Centre-LFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Knight/Centre-RFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Knight/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Knight/Left-LFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Knight/Left-RFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Knight/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Knight/Right-LFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Knight/Right-RFR.png",
        // Scientist Files
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Scientist/Back.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Scientist/Back-LFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Scientist/Back-RFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Scientist/Centre.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Scientist/Centre-LFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Scientist/Centre-RFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Scientist/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Scientist/Left-LFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Scientist/Left-RFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Scientist/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Scientist/Right-LFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Scientist/Right-RFR.png",
        // Ninja Files
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Ninja/Back.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Ninja/Back-LFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Ninja/Back-RFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Ninja/Centre.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Ninja/Centre-LFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Ninja/Centre-RFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Ninja/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Ninja/Left-LFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Ninja/Left-RFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Ninja/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Ninja/Right-LFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Ninja/Right-RFR.png",
        // Cowboy Files
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Cowboy/Back.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Cowboy/Back-LFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Cowboy/Back-RFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Cowboy/Centre.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Cowboy/Centre-LFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Cowboy/Centre-RFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Cowboy/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Cowboy/Left-LFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Cowboy/Left-RFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Cowboy/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Cowboy/Right-LFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Cowboy/Right-RFR.png",
        // Alien Files
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Alien/Back.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Alien/Back-LFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Alien/Back-RFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Alien/Centre.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Alien/Centre-LFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Alien/Centre-RFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Alien/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Alien/Left-LFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Alien/Left-RFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Alien/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Alien/Right-LFR.png",
        PARENT_FILE_STRING + "/assets/Sprites/Characters/Alien/Right-RFR.png",
        // Weapon Model Files
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Airstrike-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Bow-Pulled-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Assault-Rifle-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Ghost-Gun-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Grenade-Launcher-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Pistol-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Revolver-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Rocket-Launcher-Full-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Shotgun-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Sniper-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Sword-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Water-Thrower-Right.png",

        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Airstrike-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Bow-Pulled-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Assault-Rifle-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Ghost-Gun-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Grenade-Launcher-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Pistol-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Revolver-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Rocket-Launcher-Full-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Shotgun-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Sniper-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Sword-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Weapon-Models/Water-Thrower-Left.png",

        // Air-Strike Weapon Files ID = 0
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Air-Strike/Knight/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Air-Strike/Knight/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Air-Strike/Knight/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Air-Strike/Knight/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Air-Strike/Sci-Nin/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Air-Strike/Sci-Nin/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Air-Strike/Sci-Nin/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Air-Strike/Sci-Nin/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Air-Strike/Cowboy/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Air-Strike/Cowboy/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Air-Strike/Cowboy/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Air-Strike/Cowboy/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Air-Strike/Alien/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Air-Strike/Alien/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Air-Strike/Alien/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Air-Strike/Alien/Right.png",
        // Bow-Pulled Weapon Files ID = 1
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Knight/Pulled-Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Knight/Pulled-Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Knight/Pulled-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Knight/Pulled-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Sci-Nin/Pulled-Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Sci-Nin/Pulled-Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Sci-Nin/Pulled-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Sci-Nin/Pulled-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Cowboy/Pulled-Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Cowboy/Pulled-Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Cowboy/Pulled-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Cowboy/Pulled-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Alien/Pulled-Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Alien/Pulled-Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Alien/Pulled-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Alien/Pulled-Right.png",
        // Full-Auto (Assault-Rifle) Weapon Files ID = 2
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Assault-Rifle/Knight/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Assault-Rifle/Knight/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Assault-Rifle/Knight/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Assault-Rifle/Knight/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Assault-Rifle/Sci-Nin/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Assault-Rifle/Sci-Nin/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Assault-Rifle/Sci-Nin/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Assault-Rifle/Sci-Nin/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Assault-Rifle/Cowboy/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Assault-Rifle/Cowboy/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Assault-Rifle/Cowboy/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Assault-Rifle/Cowboy/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Assault-Rifle/Alien/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Assault-Rifle/Alien/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Assault-Rifle/Alien/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Assault-Rifle/Alien/Right.png",
        // Ghost-Gun Weapon Files ID = 3
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ghost-Gun/Knight/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ghost-Gun/Knight/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ghost-Gun/Knight/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ghost-Gun/Knight/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ghost-Gun/Sci-Nin/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ghost-Gun/Sci-Nin/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ghost-Gun/Sci-Nin/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ghost-Gun/Sci-Nin/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ghost-Gun/Cowboy/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ghost-Gun/Cowboy/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ghost-Gun/Cowboy/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ghost-Gun/Cowboy/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ghost-Gun/Alien/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ghost-Gun/Alien/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ghost-Gun/Alien/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ghost-Gun/Alien/Right.png",
        // Grenade-Launcher Weapon Files ID = 4
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Grenade-Launcher/Knight/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Grenade-Launcher/Knight/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Grenade-Launcher/Knight/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Grenade-Launcher/Knight/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Grenade-Launcher/Sci-Nin/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Grenade-Launcher/Sci-Nin/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Grenade-Launcher/Sci-Nin/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Grenade-Launcher/Sci-Nin/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Grenade-Launcher/Cowboy/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Grenade-Launcher/Cowboy/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Grenade-Launcher/Cowboy/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Grenade-Launcher/Cowboy/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Grenade-Launcher/Alien/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Grenade-Launcher/Alien/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Grenade-Launcher/Alien/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Grenade-Launcher/Alien/Right.png",
        // Pistol Weapon Files ID = 5
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Default/Knight/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Default/Knight/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Default/Knight/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Default/Knight/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Default/Sci-Nin/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Default/Sci-Nin/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Default/Sci-Nin/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Default/Sci-Nin/Right.png",
        "", "", "", "",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Default/Alien/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Default/Alien/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Default/Alien/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Default/Alien/Right.png",
        // Revolver Weapon Files ID = 6
        "", "", "", "",
        "", "", "", "",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Default/Cowboy/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Default/Cowboy/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Default/Cowboy/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Default/Cowboy/Right.png",
        "", "", "", "",
        // Rocket-Launcher-Full Weapon Files ID = 7
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Knight/Full-Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Knight/Full-Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Knight/Full-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Knight/Full-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Sci-Nin/Full-Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Sci-Nin/Full-Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Sci-Nin/Full-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Sci-Nin/Full-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Cowboy/Full-Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Cowboy/Full-Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Cowboy/Full-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Cowboy/Full-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Alien/Full-Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Alien/Full-Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Alien/Full-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Alien/Full-Right.png",
        // Shotgun Weapon Files ID = 8
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Shotgun/Knight/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Shotgun/Knight/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Shotgun/Knight/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Shotgun/Knight/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Shotgun/Sci-Nin/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Shotgun/Sci-Nin/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Shotgun/Sci-Nin/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Shotgun/Sci-Nin/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Shotgun/Cowboy/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Shotgun/Cowboy/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Shotgun/Cowboy/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Shotgun/Cowboy/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Shotgun/Alien/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Shotgun/Alien/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Shotgun/Alien/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Shotgun/Alien/Right.png",
        // Sniper Weapon Files ID = 9
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sniper/Knight/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sniper/Knight/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sniper/Knight/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sniper/Knight/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sniper/Sci-Nin/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sniper/Sci-Nin/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sniper/Sci-Nin/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sniper/Sci-Nin/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sniper/Cowboy/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sniper/Cowboy/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sniper/Cowboy/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sniper/Cowboy/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sniper/Alien/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sniper/Alien/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sniper/Alien/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sniper/Alien/Right.png",
        // Sword Weapon Files ID = 10
        "", "",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sword/Knight/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sword/Knight/Right.png",
        "", "",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sword/Sci-Nin/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sword/Sci-Nin/Right.png",
        "", "",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sword/Cowboy/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sword/Cowboy/Right.png",
        "", "",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sword/Alien/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Sword/Alien/Right.png",
        // Water-Thrower Weapon Files ID = 11
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Water-Thrower/Knight/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Water-Thrower/Knight/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Water-Thrower/Knight/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Water-Thrower/Knight/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Water-Thrower/Sci-Nin/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Water-Thrower/Sci-Nin/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Water-Thrower/Sci-Nin/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Water-Thrower/Sci-Nin/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Water-Thrower/Cowboy/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Water-Thrower/Cowboy/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Water-Thrower/Cowboy/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Water-Thrower/Cowboy/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Water-Thrower/Alien/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Water-Thrower/Alien/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Water-Thrower/Alien/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Water-Thrower/Alien/Right.png",
        // Bow-Relaxed Weapon Files ID = 3
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Knight/Relaxed-Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Knight/Relaxed-Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Knight/Relaxed-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Knight/Relaxed-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Sci-Nin/Relaxed-Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Sci-Nin/Relaxed-Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Sci-Nin/Relaxed-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Sci-Nin/Relaxed-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Cowboy/Relaxed-Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Cowboy/Relaxed-Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Cowboy/Relaxed-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Cowboy/Relaxed-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Alien/Relaxed-Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Alien/Relaxed-Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Alien/Relaxed-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Bow/Alien/Relaxed-Right.png",
         // Rocket-Launcher-Empty Weapon Files ID = 6
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Knight/Empty-Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Knight/Empty-Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Knight/Empty-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Knight/Empty-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Sci-Nin/Empty-Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Sci-Nin/Empty-Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Sci-Nin/Empty-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Sci-Nin/Empty-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Cowboy/Empty-Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Cowboy/Empty-Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Cowboy/Empty-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Cowboy/Empty-Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Alien/Empty-Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Alien/Empty-Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Alien/Empty-Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Rocket-Launcher/Alien/Empty-Right.png",
        // Drone Controller Files
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Drone-Controller/Knight.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Drone-Controller/Sci-Nin.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Drone-Controller/Cowboy.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Drone-Controller/Alien.png",
        // Idle Weapon Files
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Idle/Knight.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Idle/Sci-Nin.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Idle/Cowboy.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Idle/Alien.png",
        //Ammunition files
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ammunition/Arrow.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ammunition/Bullet.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ammunition/Ghost-Bullet.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ammunition/GL-Grenade-Standard.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ammunition/Laser.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ammunition/Rocket.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ammunition/Water-Dark-Blue.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ammunition/Water-Light-Blue.png",
        PARENT_FILE_STRING + "/assets/Sprites/Weapons/Ammunition/Water-White.png",
        PARENT_FILE_STRING + "/assets/Sprites/Other/target.png",
        // Grenade Model files
        PARENT_FILE_STRING + "/assets/Sprites/Items/Item-Models/Bouncy-Grenade.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Item-Models/Bullet-Grenade.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Item-Models/Concussion-Grenade.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Item-Models/Glue-Grenade.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Item-Models/Jump-Grenade.png",
        // Bouncy-Grenade Files ID = 0
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bouncy-Grenade/Knight/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bouncy-Grenade/Knight/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bouncy-Grenade/Knight/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bouncy-Grenade/Knight/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bouncy-Grenade/Sci-Nin/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bouncy-Grenade/Sci-Nin/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bouncy-Grenade/Sci-Nin/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bouncy-Grenade/Sci-Nin/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bouncy-Grenade/Cowboy/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bouncy-Grenade/Cowboy/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bouncy-Grenade/Cowboy/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bouncy-Grenade/Cowboy/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bouncy-Grenade/Alien/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bouncy-Grenade/Alien/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bouncy-Grenade/Alien/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bouncy-Grenade/Alien/Right.png",
        // Bullet-Grenade Files ID = 1
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bullet-Grenade/Knight/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bullet-Grenade/Knight/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bullet-Grenade/Knight/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bullet-Grenade/Knight/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bullet-Grenade/Sci-Nin/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bullet-Grenade/Sci-Nin/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bullet-Grenade/Sci-Nin/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bullet-Grenade/Sci-Nin/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bullet-Grenade/Cowboy/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bullet-Grenade/Cowboy/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bullet-Grenade/Cowboy/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bullet-Grenade/Cowboy/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bullet-Grenade/Alien/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bullet-Grenade/Alien/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bullet-Grenade/Alien/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Bullet-Grenade/Alien/Right.png",
        // Concussion-Grenade Files ID = 2
        PARENT_FILE_STRING + "/assets/Sprites/Items/Concussion-Grenade/Knight/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Concussion-Grenade/Knight/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Concussion-Grenade/Knight/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Concussion-Grenade/Knight/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Concussion-Grenade/Sci-Nin/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Concussion-Grenade/Sci-Nin/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Concussion-Grenade/Sci-Nin/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Concussion-Grenade/Sci-Nin/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Concussion-Grenade/Cowboy/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Concussion-Grenade/Cowboy/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Concussion-Grenade/Cowboy/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Concussion-Grenade/Cowboy/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Concussion-Grenade/Alien/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Concussion-Grenade/Alien/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Concussion-Grenade/Alien/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Concussion-Grenade/Alien/Right.png",
        // Glue-Grenade Files ID = 3
        PARENT_FILE_STRING + "/assets/Sprites/Items/Glue-Grenade/Knight/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Glue-Grenade/Knight/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Glue-Grenade/Knight/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Glue-Grenade/Knight/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Glue-Grenade/Sci-Nin/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Glue-Grenade/Sci-Nin/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Glue-Grenade/Sci-Nin/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Glue-Grenade/Sci-Nin/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Glue-Grenade/Cowboy/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Glue-Grenade/Cowboy/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Glue-Grenade/Cowboy/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Glue-Grenade/Cowboy/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Glue-Grenade/Alien/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Glue-Grenade/Alien/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Glue-Grenade/Alien/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Glue-Grenade/Alien/Right.png",
        // Jump-Grenade Files ID = 4
        PARENT_FILE_STRING + "/assets/Sprites/Items/Jump-Grenade/Knight/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Jump-Grenade/Knight/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Jump-Grenade/Knight/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Jump-Grenade/Knight/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Jump-Grenade/Sci-Nin/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Jump-Grenade/Sci-Nin/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Jump-Grenade/Sci-Nin/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Jump-Grenade/Sci-Nin/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Jump-Grenade/Cowboy/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Jump-Grenade/Cowboy/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Jump-Grenade/Cowboy/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Jump-Grenade/Cowboy/Right.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Jump-Grenade/Alien/Up.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Jump-Grenade/Alien/Down.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Jump-Grenade/Alien/Left.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Jump-Grenade/Alien/Right.png",
        // Other Item Model Textures
        PARENT_FILE_STRING + "/assets/Sprites/Items/Item-Models/Drone.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Item-Models/Landmine.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Item-Models/Body-Armour.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Item-Models/Jetpack.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Item-Models/Taser.png",
        // Other Item Textures
        PARENT_FILE_STRING + "/assets/Sprites/Items/Drone.png",
        PARENT_FILE_STRING + "/assets/Sprites/Items/Landmine.png",
        // Round score Textures
        PARENT_FILE_STRING + "/assets/Sprites/Other/Scores/Red-0.png",
        PARENT_FILE_STRING + "/assets/Sprites/Other/Scores/Red-1.png",
        PARENT_FILE_STRING + "/assets/Sprites/Other/Scores/Red-2.png",
        PARENT_FILE_STRING + "/assets/Sprites/Other/Scores/Red-3.png",

        PARENT_FILE_STRING + "/assets/Sprites/Other/Scores/Blue-0.png",
        PARENT_FILE_STRING + "/assets/Sprites/Other/Scores/Blue-1.png",
        PARENT_FILE_STRING + "/assets/Sprites/Other/Scores/Blue-2.png",
        PARENT_FILE_STRING + "/assets/Sprites/Other/Scores/Blue-3.png"
    ));

    public static Texture getPlayerTexture(Player p)
    {
        if(p.getHitPoints() <= 0)
        {
            return p.getDead();
        }

        Texture t = new Texture();
        Character c = p.getCharacter();
        try
        {
            t.loadFromFile(Paths.get(
                textureFiles.get(
                    c.getID() * 12 +
                    getDirectionFacingID(p.getFacingDirection()) * 3 +
                    c.getWalkingPhaseID()
                )
            ));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return t;
    }

    private static Texture getDroneController(Player p)
    {
        Texture t = new Texture();

        int cID = p.getCharacter().getID();

        // This conditional takes into account that ninja and scientist sprites have identical weapon sprites.
        if(cID > 1)
        {
            --cID;
        }

        try{ t.loadFromFile(Paths.get(textureFiles.get(DRONE_CONTROLLER_OFFSET + cID))); }catch(Exception e){ e.printStackTrace(); }
        return t;
    }

    public static Texture getWeaponTexture(Player p)
    {
        Texture t = new Texture();
        Character c = p.getCharacter();
        int cID = c.getID();

        // This conditional takes into account that ninja and scientist sprites have identical weapon sprites.
        if(cID > 1)
        {
            --cID;
        }

        try
        {
            if(p.hasActiveDrone())
            {
                return getDroneController(p);
            }
            else
            {

                t.loadFromFile(
                        Paths.get(
                        textureFiles.get(
                                WEAPON_OFFSET +
                                        p.getWeapon().getID() * 16 +
                                        cID * 4 +
                                        // Handles sword weapon having fewer sprites available.
                                        (((getDirectionFiringID(p.getFiringDirection())) + 2 > 3 || p.getWeapon().getID() != 10)
                                        ? getDirectionFiringID(p.getFiringDirection())
                                        : getDirectionFiringID(p.getFiringDirection()) + 2)
                        )
                    )
                );
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return t;
    }

    public static Texture getStartingTexture(Character c)
    {
        Texture t = new Texture();
        try
        {
            // Select the character, then the central facing position, then neither foot raised.
            t.loadFromFile(Paths.get(textureFiles.get(c.getID() * 12 + 3)));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return t;
    }

    public enum AmmoType
    {
        ARROW, GHOST, GRENADE, LASER,
        ROCKET, WATER_DARK, WATER_LIGHT, WATER_WHITE,
        BULLET, TARGET
    }

    public static final Texture getAmmoTexture(AmmoType at)
    {
        Texture t = new Texture();
        try
        {
            switch(at)
            {
                case ARROW:
                    t.loadFromFile(Paths.get(textureFiles.get(AMMO_OFFSET)));
                    break;
                case BULLET:
                    t.loadFromFile(Paths.get(textureFiles.get(AMMO_OFFSET + 1)));
                    break;
                case GHOST:
                    t.loadFromFile(Paths.get(textureFiles.get(AMMO_OFFSET + 2)));
                    break;
                case GRENADE:
                    t.loadFromFile(Paths.get(textureFiles.get(AMMO_OFFSET + 3)));
                    break;
                case LASER:
                    t.loadFromFile(Paths.get(textureFiles.get(AMMO_OFFSET + 4)));
                    break;
                case ROCKET:
                    t.loadFromFile(Paths.get(textureFiles.get(AMMO_OFFSET + 5)));
                    break;
                case WATER_DARK:
                    t.loadFromFile(Paths.get(textureFiles.get(AMMO_OFFSET + 6)));
                    break;
                case WATER_LIGHT:
                    t.loadFromFile(Paths.get(textureFiles.get(AMMO_OFFSET + 7)));
                    break;
                case WATER_WHITE:
                    t.loadFromFile(Paths.get(textureFiles.get(AMMO_OFFSET + 8)));
                    break;
                case TARGET:
                    t.loadFromFile(Paths.get(textureFiles.get(AMMO_OFFSET + 9)));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return t;
    }

    public static final Texture getIdleTexture(Character c)
    {
        Texture t = new Texture();
        try
        {
            int cID = c.getID();
            if(cID > 1)
            {
                --cID;
            }
            // load idle textures
            t.loadFromFile(Paths.get(textureFiles.get(IDLE_OFFSET + cID)));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return t;
    }

    private static final Texture getGrenadeModelTexture(Player p)
    {
        Texture t = new Texture();
        try
        {
            t.loadFromFile(Paths.get(textureFiles.get(GRENADE_MODEL_OFFSET + p.getItem().getID())));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return t;
    }

    private static Texture getGrenadeTexture(Player p)
    {
        Texture t = new Texture();
        try
        {
            int cID = p.getCharacter().getID();
            if(cID > 1)
            {
                --cID;
            }
            t.loadFromFile(
                    Paths.get(textureFiles.get(
                            GRENADE_OFFSET +
                            p.getItem().getID() * 16 +
                            cID * 4 +
                            getDirectionFiringID(p.getFiringDirection())
            )));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return t;
    }

    public static Texture getWeaponModelTexture(Player p)
    {
        Texture weaponTexture = new Texture();

        int weaponID = p.getWeapon().getID();

        if(p.getTeam() == GameScene.Team.Second)
        {
            weaponID += 12;
        }

        try{ weaponTexture.loadFromFile(Paths.get(textureFiles.get(WEAPON_MODEL_OFFSET + weaponID))); }catch(Exception e){ e.printStackTrace(); }

        return weaponTexture;
    }

    public static Texture getItemTexture(Player p)
    {
        /*
         * If a new round occurs and items are equipped, the item may try to update texture but the player
         * will have their item unequipped, so a null error would occur. This condition handles it.
         */
        if(p == null|| p.getItem() == null)
        {
            return new Texture();
        }

        if(p.getItem() instanceof Grenade)
        {
            return getGrenadeTexture(p);
        }
        else
        {
            Texture t = new Texture();
            try{ t.loadFromFile(Paths.get(textureFiles.get(ITEM_OFFSET + p.getItem().getID() - 5))); }catch(Exception e){e.printStackTrace();}
            return t;
        }
    }

    public static Texture getItemModelTexture(Player p)
    {
        if(p.getItem() == null)
        {
            return new Texture();
        }

        if(p.getItem() instanceof Grenade)
        {
            return getGrenadeModelTexture(p);
        }
        else
        {
            Texture t = new Texture();
            try{ t.loadFromFile(Paths.get(textureFiles.get(ITEM_MODEL_OFFSET + p.getItem().getID() - 5))); }catch(Exception e){e.printStackTrace();}
            return t;
        }
    }

    private static int getDirectionFacingID(Controller.Direction facingDirection)
    {
        switch(facingDirection)
        {
            case UP:
                return 0;
            case DOWN:
                return 1;
            case LEFT:
                return 2;
            case RIGHT:
                return 3;
            default:
                return -1;
        }
    }

    private static int getDirectionFiringID(Controller.Direction firingDirection)
    {
        switch(firingDirection)
        {
            case UP:
                return 0;
            case DOWN:
                return 1;
            case LEFT:
                return 2;
            case RIGHT:
                return 3;
            default:
                return -1;
        }
    }

    public static Texture getTransparentTexture()
    {
        Texture transparentTexture = new Texture();
        try{ transparentTexture.loadFromFile(Paths.get(PARENT_FILE_STRING + "/assets/Sprites/Other/Transparent-Pixel.png")); }catch(Exception e){ e.printStackTrace(); }
        return transparentTexture;
    }

    public static Texture getRoundWins(Player p)
    {
        Texture t = new Texture();
        try{ t.loadFromFile(Paths.get(textureFiles.get(SCORE_OFFSET + p.getRoundWins() + (p.getTeam() == GameScene.Team.First ? 0 : 4)))); }catch(Exception e){e.printStackTrace();}
        return t;
    }
}
