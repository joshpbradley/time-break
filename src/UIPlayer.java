import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.nio.file.Paths;

/**
 * Represents the UI placed at the side of the screen that displays character status information.
 */
public class UIPlayer
{
    RenderWindow window = GameScene.getGameScene();
    private final Player p;
    private final UIBorder b;
    private final CharacterUISprite character;
    private final Sprite healthBar = new Sprite();
    private static Texture health = new Texture();
    private final Sprite ammoBar = new Sprite();
    private static Texture ammo = new Texture();
    private final Sprite weaponSelected = new Sprite();
    private final Sprite itemSelected = new Sprite();
    private final int offset;
    private final Sprite[] statusSprites = new Sprite[6];
    private float ammoFullSizeX;
    private float healthFullSizeX;
    private static final Texture confusion = new Texture();
    private static final Texture haste = new Texture();
    private static final Texture slow = new Texture();
    private static final Texture jump = new Texture();
    private static final Texture burning = new Texture();
    private static final Texture frozen = new Texture();
    private static final Texture confusionDesaturated = new Texture();
    private static final Texture hasteDesaturated = new Texture();
    private static final Texture slowDesaturated = new Texture();
    private static final Texture jumpDesaturated = new Texture();
    private static final Texture burningDesaturated = new Texture();
    private static final Texture frozenDesaturated = new Texture();
    private final Sprite abilitySprite = new Sprite();
    private static final Texture ability = new Texture();
    private static final Texture abilityDesaturated = new Texture();
    private static final Texture selectionColour = new Texture();
    private final Sprite selectionBoxItem = new Sprite();
    private final Sprite selectionBoxWeapon = new Sprite();
    private final Sprite roundWins = new Sprite();

    private static String[] statusEffects = new String[]
    {
        AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Status/Confusion.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Status/Jump.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Status/Burning.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Status/Frozen.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Status/Slow.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Status/Haste.png",

        AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Status/Confusion-Desat.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Status/Jump-Desat.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Status/Burning-Desat.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Status/Frozen-Desat.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Status/Slow-Desat.png",
        AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Status/Haste-Desat.png"
    };

    /**
     * Contructor for the UIPlayeer class.
     * @param p the player that has its status recorded by this UIPlayer instance.
     * @param b the b
     */
    public UIPlayer(Player p, UIBorder b)
    {
        this.p = p;
        this.b = b;

        try
        {
            confusion.loadFromFile(Paths.get(statusEffects[0]));
            jump.loadFromFile(Paths.get(statusEffects[1]));
            burning.loadFromFile(Paths.get(statusEffects[2]));
            frozen.loadFromFile(Paths.get(statusEffects[3]));
            slow.loadFromFile(Paths.get(statusEffects[4]));
            haste.loadFromFile(Paths.get(statusEffects[5]));

            confusionDesaturated.loadFromFile(Paths.get(statusEffects[6]));
            jumpDesaturated.loadFromFile(Paths.get(statusEffects[7]));
            burningDesaturated.loadFromFile(Paths.get(statusEffects[8]));
            frozenDesaturated.loadFromFile(Paths.get(statusEffects[9]));
            slowDesaturated.loadFromFile(Paths.get(statusEffects[10]));
            hasteDesaturated.loadFromFile(Paths.get(statusEffects[11]));

            health.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Health-Pixel.png"));
            ammo.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Ammo-Pixel.png"));

            selectionColour.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Mode-Selected.png"));
            abilityDesaturated.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Ability-Desat.png"));
            ability.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/UI/In-Game/Ability.png"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        offset = (p.getTeam() == GameScene.Team.First ? 0 : 2220);

        character = new CharacterUISprite(TransformController.transformX(25 + offset), TransformController.transformY(92));
        character.setTexture(character.getTexture(p.getCharacter().getID(), p.getTeam(), 2));
        float characterScale = TransformController.getScaleMultiplier(370, 250, TransformController.Domain.X);
        character.setScale(characterScale, characterScale);

        healthBar.setPosition(TransformController.transformXAndY(p.getTeam() == GameScene.Team.First ? (25 + offset) :
                (TransformController.getFullSize().x - 25), 25));
        ammoBar.setPosition(TransformController.transformXAndY(p.getTeam() == GameScene.Team.First ? (25 + offset) :
                (TransformController.getFullSize().x - 25), 693));

        // sets the mode select box for when a weapon is selected
        selectionBoxWeapon.setTexture(selectionColour);
        selectionBoxWeapon.setPosition(TransformController.transformXAndY(25 + offset, 489));
        float sBoxWeaponX = TransformController.getScaleMultiplier(1, 250, TransformController.Domain.X);
        float sBoxWeaponY = TransformController.getScaleMultiplier(1, 179, TransformController.Domain.Y);
        selectionBoxWeapon.setScale(sBoxWeaponX, sBoxWeaponY);

        // Sets the mode select box for when an item is selected.
        selectionBoxItem.setTexture(selectionColour);
        selectionBoxItem.setPosition(TransformController.transformXAndY(25 + offset  + (p.getTeam() == GameScene.Team.First ? 0 : 138), 760));
        float sBoxItemX = TransformController.getScaleMultiplier(1, 112, TransformController.Domain.X);
        float sBoxItemY = TransformController.getScaleMultiplier(1, 112, TransformController.Domain.Y);
        selectionBoxItem.setScale(sBoxItemX, sBoxItemY);

        // Sets the ability available icon.
        abilitySprite.setTexture(ability);
        abilitySprite.setPosition(TransformController.transformXAndY(25 + offset + (p.getTeam() == GameScene.Team.First ? 138 : 0), 760));
        float abilityScale = TransformController.getScaleMultiplier(30, 112, TransformController.Domain.X);
        abilitySprite.setScale(abilityScale, abilityScale);

        weaponSelected.setScale(3, 3);
        weaponSelected.setPosition(TransformController.transformX(25 + offset + 125), TransformController.transformY(179/2 + 489));

        itemSelected.setScale(4, 4);
        itemSelected.setPosition(TransformController.transformXAndY(25 + 56 + offset + (p.getTeam() == GameScene.Team.First ? 0 : 138), 760 + 56));

        float barScaleX = TransformController.getScaleMultiplier(1, 250, TransformController.Domain.X);
        float barScaleY = TransformController.getScaleMultiplier(1, 42, TransformController.Domain.Y);
        healthBar.setTexture(health);
        ammoBar.setTexture(ammo);
        healthBar.setScale(barScaleX, barScaleY);
        ammoBar.setScale(barScaleX, barScaleY);
        healthFullSizeX = ammoBar.getScale().x;
        ammoFullSizeX = ammoBar.getScale().x;

        // Sets the origin to the right, to allow scaling to be relative to the right hand side of the bar.
        if(p.getTeam() == GameScene.Team.Second)
        {
            healthBar.setOrigin(healthBar.getLocalBounds().width, 0);
            ammoBar.setOrigin(ammoBar.getLocalBounds().width, 0);
        }

        // Sets the score Sprite
        roundWins.setTexture(AllTextureFiles.getRoundWins(p));
        if(p.getTeam() == GameScene.Team.First)
        {
            roundWins.setOrigin(roundWins.getLocalBounds().width, 0);
        }
        roundWins.setPosition(TransformController.transformXAndY(275 + offset - (p.getTeam() == GameScene.Team.First ? 0 : 250), 355));
        float scoreScale = TransformController.getScaleMultiplier(109, 109, TransformController.Domain.Y);
        roundWins.setScale(scoreScale, scoreScale);

        initialiseStatusEffects();
    }

    /**
     * Draws al of the sections of the UI.
     */
    public void drawAssets()
    {
        window.draw(b);
        window.draw(character);

        scaleBars();
        if(p.getHitPoints() > 0)
        {
            window.draw(healthBar);
        }
        window.draw(ammoBar);

        if(p.getShootingMode())
        {
            window.draw(selectionBoxWeapon);
            // BodyArmour and Jetpack are passive so should appear as active even though the player is in item mode.
            // Grenades should appear as selected when a grenade- compatible weapon has been selected by the player.
            if(p.getItem() instanceof BodyArmour
                    || p.getItem() instanceof Jetpack && ((Jetpack)p.getItem()).getActivated()
                    || p.getItem() instanceof Grenade &&
                    (p.getWeapon() instanceof Bow || p.getWeapon() instanceof RocketLauncher || p.getWeapon() instanceof GrenadeLauncher))
            {
                window.draw(selectionBoxItem);
            }
        }
        else
        {
            window.draw(selectionBoxItem);
        }

        window.draw(abilitySprite);

        setWeaponSprite();
        window.draw(weaponSelected);

        if(p.getItem() != null)
        {
            setItemSprite();
            window.draw(itemSelected);
        }

        setAbilitySprite();

        setStatusEffects();
        drawStatusEffects();

        setRoundWins();
        window.draw(roundWins);
    }

    /**
     * Sales the Health and Ammo bars to display current ammunition and health points remaining.
     */
    private void scaleBars()
    {
        int ammoCount = 1;

        if(p.getWeapon() instanceof Gun && !(p.getWeapon() instanceof Revolver || p.getWeapon() instanceof Pistol))
        {
            switch (p.getWeapon().getID())
            {
                case 1:
                    ammoCount = new Bow().getAmmo();
                    break;
                case 2:
                    ammoCount = new FullAuto().getAmmo();
                    break;
                case 3:
                    ammoCount = new GhostGun().getAmmo();
                    break;
                case 4:
                    ammoCount = new GrenadeLauncher().getAmmo();
                    break;
                case 5:
                case 6:
                    ammoBar.setScale(ammoFullSizeX, ammoBar.getScale().y);
                case 7:
                    ammoCount = new RocketLauncher().getAmmo();
                    break;
                case 8:
                    ammoCount = new Shotgun().getAmmo();
                    break;
                case 9:
                    ammoCount = new SniperRifle().getAmmo();
                    break;
                case 11:
                    ammoCount = new WaterThrower().getAmmo();
            }
            if(!(p.getWeapon() instanceof Revolver || p.getWeapon() instanceof Pistol))
            {
                int currentAmmo = ((Gun)p.getWeapon()).getAmmo();
                ammoBar.setScale(ammoFullSizeX * (currentAmmo / (float)(ammoCount) >= 1 ? 1 : currentAmmo / (float)(ammoCount)), ammoBar.getScale().y);
            }
        }
        else
        {
            ammoBar.setScale(ammoFullSizeX, ammoBar.getScale().y);
        }

        if(p.getHitPoints() <= 0)
        {
            return;
        }
        healthBar.setScale(healthFullSizeX * p.getHitPoints()/p.getCharacter().getHitPoints(), healthBar.getScale().y);
    }

    /**
     * Sets the Weapon Sprite in the UI.
     */
    private void setWeaponSprite()
    {
        weaponSelected.setTexture(AllTextureFiles.getWeaponModelTexture(p), true);
        weaponSelected.setOrigin(weaponSelected.getLocalBounds().width/2, weaponSelected.getLocalBounds().height/2);
    }

    /**
     * Sets the item
     * Sprite in the UI.
     */
    private void setItemSprite()
    {
        itemSelected.setTexture(AllTextureFiles.getItemModelTexture(p), true);
        itemSelected.setOrigin(itemSelected.getLocalBounds().width/2, itemSelected.getLocalBounds().height/2);
    }

    /**
     * Sets the initial Sprites that compose the status effect grid.
     */
    private void initialiseStatusEffects()
    {
        float statusScaleX = TransformController.getScaleMultiplier(30, 83, TransformController.Domain.X);
        float statusScaleY = TransformController.getScaleMultiplier(30, 79, TransformController.Domain.Y);

        for(int i = 0; i < statusSprites.length; ++i)
        {
            statusSprites[i] = new Sprite();
            statusSprites[i].setScale(statusScaleX, statusScaleY);
            statusSprites[i].setPosition(TransformController.transformXAndY(25 + offset + (i % 3) * 83, i < 3 ? 897 : 972));
        }

        statusSprites[p.getTeam() == GameScene.Team.First ? 0 : 2].setTexture(hasteDesaturated);
        statusSprites[1].setTexture(jumpDesaturated);
        statusSprites[p.getTeam() == GameScene.Team.First ? 2 : 0].setTexture(confusionDesaturated);
        statusSprites[p.getTeam() == GameScene.Team.First ? 3 : 5].setTexture(slowDesaturated);
        statusSprites[4].setTexture(frozenDesaturated);
        statusSprites[p.getTeam() == GameScene.Team.First ? 5 : 3].setTexture(burningDesaturated);
    }

    /**
     * Draws the grid of status effects.
     */
    private void drawStatusEffects()
    {
        for(int i = 0; i < 6; ++i)
        {
            window.draw(statusSprites[i]);
        }
    }

    /**
     * Sets the status Sprites (the grid of six icons) on the UI.
     */
    private void setStatusEffects()
    {
        // resets all of the status effect sprites to show as inactive
        statusSprites[p.getTeam() == GameScene.Team.First ? 0 : 2].setTexture(hasteDesaturated);
        statusSprites[1].setTexture(jumpDesaturated);
        statusSprites[p.getTeam() == GameScene.Team.First ? 2 : 0].setTexture(confusionDesaturated);
        statusSprites[p.getTeam() == GameScene.Team.First ? 3 : 5].setTexture(slowDesaturated);
        statusSprites[4].setTexture(frozenDesaturated);
        statusSprites[p.getTeam() == GameScene.Team.First ? 5 : 3].setTexture(burningDesaturated);

        for(int i = 0; i < statusSprites.length; ++i)
        {
            if(p.getActiveEffects()[i] != null)
            {
                if(p.getActiveEffects()[i] instanceof Confusion)
                {
                    statusSprites[p.getTeam() == GameScene.Team.First ? 2 : 0].setTexture(confusion);
                }
                else if(p.getActiveEffects()[i] instanceof Slow)
                {
                    statusSprites[p.getTeam() == GameScene.Team.First ? 3 : 5].setTexture(slow);
                }
                else if(p.getActiveEffects()[i] instanceof Haste)
                {
                    statusSprites[p.getTeam() == GameScene.Team.First ? 0 : 2].setTexture(haste);
                }
                else if(p.getActiveEffects()[i] instanceof Jump)
                {
                    statusSprites[1].setTexture(jump);
                }
                else if(p.getActiveEffects()[i] instanceof Burning)
                {
                    statusSprites[p.getTeam() == GameScene.Team.First ? 5 : 3].setTexture(burning);
                }
                else if(p.getActiveEffects()[i] instanceof Frozen)
                {
                    statusSprites[4].setTexture(frozen);
                }
            }
        }
    }

    /**
     * Sets the ability Sprite (the star icon) on the UI.
     */
    private void setAbilitySprite()
    {
        if(p.getAbilityReady())
        {
            abilitySprite.setTexture(ability);
        }
        else
        {
            abilitySprite.setTexture(abilityDesaturated);
        }
    }

    /**
     * Sets the number of rounds won by each player.
     */
    private void setRoundWins()
    {
        roundWins.setTexture(AllTextureFiles.getRoundWins(p), true);

        if(p.getTeam() == GameScene.Team.First)
        {
            roundWins.setOrigin(roundWins.getLocalBounds().width, 0);
        }
    }
}
