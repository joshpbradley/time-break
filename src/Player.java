import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import java.nio.file.Paths;

public class Player extends AbstractPlayer
{
    private Sprite playerIndicator = null;
    private Item item;
    private Sprite idleHands;
    private long lastItem = 0;
    private boolean idle = true;
    private boolean shootingMode = true;
    private boolean abilityReady;
    private boolean canChangeMode = true;
    public static final float initialScaling = 0.9f;
    public static final Texture death = new Texture();
    private int roundWins = 0;
    private Texture lastWeaponUsedTexture = null;

    public Player(Character c, GameScene.Team tm)
    {
        super(c, tm);

        setScale(initialScaling, initialScaling);
        weapon.setScale(initialScaling * 1.3f, initialScaling * 1.3f);
        setOrigin(getGlobalCentrePoint());

        setWeapon(c.getDefaultWeapon());
        setItem(null);

        abilityReady = c.hasAbility();

        try { death.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Dead.png")); }catch(Exception e){ e.printStackTrace(); }

        idleHands = new Sprite(AllTextureFiles.getIdleTexture(c));
        idleHands.setOrigin(this.getLocalBounds().width / 2, this.getLocalBounds().height / 2);
        idleHands.scale(weapon.getScale().x, weapon.getScale().y);
    }

    public void lead(MovingBody collideWith)
    {
        if(getLastPushed() - System.currentTimeMillis() < -100)
        {
            tweakX(-(getSpeedX() * 1.5));
            tweakY(-(getSpeedY() * 1.5));
        }
    }
    
    public void follow(MovingBody collideWith)
    {
        if(getLastPushed() - System.currentTimeMillis() < -100)
        {
            if(getSpeedX() == 0 && collideWith.getSpeedX() == 0)
            {
                if(collideWith.getPosition().x > getPosition().x)
                    tweakX(-1);
                else
                    tweakX(1);
            }
            else
            {
                double calcX = collideWith.getSpeedX() * 2.5;
                tweakX((calcX > 1 || calcX < -1) ? calcX : (getSpeedX() > 0) ? -2 : 2);
            }

            if(getSpeedY() == 0 && collideWith.getSpeedY() == 0)
            {
                if(collideWith.getPosition().y > getPosition().y)
                {
                    tweakY(-1);
                }
                else
                {
                    tweakY(1);
                }
            }
            else
            {
                double calcY = collideWith.getSpeedY() * 2.5;
                tweakY((calcY > 1 || calcY < -1) ? calcY : (getSpeedY() > 0) ? -2 : 2);
            }
        }
    }

    public void fire(Controller.Direction direction)
    {
        if(shootingMode && System.currentTimeMillis() > lastWeaponDisposed + 200)
        {
            weapon.fire(direction);
        }
        else
        {
            if (System.currentTimeMillis() > lastItem + 500 && item != null)
            {
                item.use(direction);
                lastItem = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void setSpriteTextures(boolean[] keyboard)
    {
        Controller.Direction currentFiring = firingDirection;

        weapon.setRotation(0);

        boolean[] keyboardMovementCopy = new boolean[4];
        System.arraycopy(keyboard, 0, keyboardMovementCopy, 0, keyboardMovementCopy.length);

        //If opposite keys are pressed, their effects are nullified
        if(keyboardMovementCopy[0] && keyboardMovementCopy[1])
        {
            keyboardMovementCopy[0] = false;
            keyboardMovementCopy[1] = false;
        }

        if(keyboardMovementCopy[2] && keyboardMovementCopy[3])
        {
            keyboardMovementCopy[2] = false;
            keyboardMovementCopy[3] = false;
        }

        int facingKeysPressed = 0;
        for(int i = 0; i < 4; i++)
        {
            if(keyboardMovementCopy[i])
            {
                ++facingKeysPressed;
            }
        }

        if(facingKeysPressed == 1)
        {
            if(keyboardMovementCopy[0])
            {
                facingDirection = Controller.Direction.UP;
            }
            else if(keyboardMovementCopy[1])
            {
                facingDirection = Controller.Direction.DOWN;
            }
            else if(keyboardMovementCopy[2])
            {
                facingDirection = Controller.Direction.LEFT;
            }
            else
            {
                facingDirection = Controller.Direction.RIGHT;
            }
        }
        else
        {
            // If the velocity of the player is below a threshold, the central stance is taken
            if(Math.sqrt(Math.pow(getSpeedX(), 2) + Math.pow(getSpeedY(), 2)) < 1.5)
            {
                facingDirection = Controller.Direction.DOWN;
                character.setWalkingPhase(Character.walkingPhase.NEITHER);
            }
            else if(Math.abs(getSpeedX()) == Math.abs(getSpeedY()))
            {
                if(getSpeedX() > 0 )
                {
                    facingDirection = Controller.Direction.RIGHT;
                }
                else
                {
                    facingDirection = Controller.Direction.LEFT;
                }
            }
            else if (Math.abs(getSpeedX()) < Math.abs(getSpeedY()))
            {
                if(keyboardMovementCopy[1] || getSpeedY() > 0)
                {
                    facingDirection = Controller.Direction.DOWN;
                }
                else
                {
                    facingDirection = Controller.Direction.UP;
                }
            }
            else
            {
                if(keyboardMovementCopy[3] || getSpeedX() > 0)
                {
                    facingDirection = Controller.Direction.RIGHT;
                }
                else
                {
                    facingDirection = Controller.Direction.LEFT;
                }
            }
        }

        boolean[] keyboardFiringCopy = new boolean[4];
        System.arraycopy(keyboard, 4, keyboardFiringCopy, 0, keyboardFiringCopy.length);

        int firingKeysPressed = 0;
        for(int i = 0; i < 4; ++i)
        {
            if(keyboardFiringCopy[i])
            {
                ++firingKeysPressed;
            }
        }
        if(firingKeysPressed > 1)
        {
            firingDirection = facingDirection;
        }
        else if(keyboardFiringCopy[0])
        {
            firingDirection = Controller.Direction.UP;
        }
        else if(keyboardFiringCopy[1])
        {
            firingDirection = Controller.Direction.DOWN;
        }
        else if(keyboardFiringCopy[2])
        {
            firingDirection = Controller.Direction.LEFT;
        }
        else if(keyboardFiringCopy[3])
        {
            firingDirection = Controller.Direction.RIGHT;
        }
        else
        {
            firingDirection = facingDirection;
        }

        // Rotates Weapon if there is lateral movement and vertical firing
        // Not appropriate for the sword weapon
        if(weapon.getID() != 12)
        {
            if(facingDirection == Controller.Direction.RIGHT)
            {
                if (firingDirection == Controller.Direction.UP)
                {
                    firingDirection = Controller.Direction.RIGHT;
                    weapon.setRotation(270);
                }
                else if(firingDirection == Controller.Direction.DOWN)
                {
                    firingDirection = Controller.Direction.RIGHT;
                    weapon.setRotation(90);
                }
            }
            else if(facingDirection == Controller.Direction.LEFT)
            {
                if(firingDirection == Controller.Direction.UP)
                {
                    firingDirection = Controller.Direction.LEFT;
                    weapon.setRotation(90);
                }
                else if(firingDirection == Controller.Direction.DOWN)
                {
                    firingDirection = Controller.Direction.LEFT;
                    weapon.setRotation(270);
                }
            }
        }

        // Forces central stance if firing laterally in opposite direction to movement
        if(firingDirection == Controller.Direction.LEFT && facingDirection == Controller.Direction.RIGHT || firingDirection == Controller.Direction.RIGHT && facingDirection == Controller.Direction.LEFT)
        {
            facingDirection = Controller.Direction.DOWN;
        }
        // Forces central stance if firing laterally in opposite direction to movement
        else if(firingDirection == Controller.Direction.DOWN && facingDirection == Controller.Direction.UP)
        {
            facingDirection = Controller.Direction.DOWN;
        }
        // Forces central stance if firing laterally in opposite direction to movement
        else if(firingDirection == Controller.Direction.UP && facingDirection == Controller.Direction.DOWN)
        {
            facingDirection = Controller.Direction.UP;
        }

        // change player texture on changing facing direction
        setTexture(AllTextureFiles.getPlayerTexture(this), true);

        boolean shouldBeIdle;

        if((firingKeysPressed == 0  && facingKeysPressed == 0) && facingDirection == Controller.Direction.DOWN)
        {
            shouldBeIdle = true;
        }
        else
        {
            shouldBeIdle = false;
        }

        if(currentFiring != firingDirection || idle && !shouldBeIdle || hasActiveDrone())
        {
            idle = false;
            weapon.setTexture(AllTextureFiles.getWeaponTexture(this), true);
        }
        else if(!idle && shouldBeIdle)
        {
            idle = true;
        }
    }

    public boolean hasActiveDrone()
    {
        return item != null && item instanceof Drone && ((Drone)item).getActivated();
    }

    public boolean getIdle()
    {
        return idle;
    }

    @Override
    public void takeDamage(int damage)
    {
        if(item != null)
        {
            if (item instanceof BodyArmour)
            {
                ((BodyArmour)item).use();
                return;
            }
        }
        hitPoints -= damage;
    }

    public void forceTakeDamage(int damage)
    {
        Item holder = item;
        item = null;
        takeDamage(damage);
        item = holder;
    }

    /**
     * Changes the mode from shooting to using items or vice versa. Also updates the UI elements to reflect the
     * change. If the player has no item then it ignores this call.
     */

    public void changeMode()
    {
        if(canChangeMode)
        {
            shootingMode = !shootingMode;

            if(item instanceof Grenade && !shootingMode)
            {
                GameScene.getGameScene().add((CollisionItem)item);
            }
            else if(item instanceof Grenade && shootingMode)
            {
                GameScene.getGameScene().remove((CollisionItem)item);
            }

            if(item instanceof Drone)
            {
                ((Drone)item).use();
            }
            else if(item instanceof Jetpack)
            {
                ((Jetpack)item).use();
            }
            else if(item instanceof Taser)
            {
                ((Taser)item).use();
            }
            else if(item instanceof Landmine)
            {
                ((Landmine)item).use();
            }
        }
    }

    public void setMode(boolean b)
    {
        if(canChangeMode)
        {
            shootingMode = b;
        }
    }

    public void setItem(Item item)
    {
        this.item = item;
        if(item != null)
        {
            canChangeMode = true;
            item.setItemOwner(this);
        }
        else
        {
            canChangeMode = false;
            shootingMode = true;
        }
    }

    public void drawPlayerAssets(RenderWindow window)
    {
        if(playerIndicator != null)
        {
            playerIndicator.setPosition(getGlobalCentrePoint().x, getGlobalBounds().top - 30);
            window.draw(playerIndicator);
        }

        if(idle)
        {
            idleHands.setPosition(getGlobalCentrePoint().x, getGlobalCentrePoint().y + 30);
            weapon.setPosition(getGlobalCentrePoint());
            window.draw(idleHands);
        }
        else if(shootingMode)
        {
            if(!hasActiveDrone())
            {
                weapon.setPosition();
                window.draw(weapon);
            }
            else
            {
                Vector2f holdOrigin = weapon.getOrigin();
                weapon.setOrigin(weapon.getLocalBounds().width/2, weapon.getLocalBounds().height/2);
                weapon.setPosition(getGlobalCentrePoint());
                window.draw(weapon);
                weapon.setOrigin(holdOrigin);
            }
        }
    }

    public boolean getShootingMode()
    {
        return shootingMode;
    }

    /**
     * Calls use ability of the character that the player is playing as, if the ability has already been used
     * then it ignores this call.
     */
    public void useAbility()
    {
        if(abilityReady)
        {
            character.Ability(this);
            abilityReady = false;
        }
    }

    public void reset()
    {
        setWeapon(character.getDefaultWeapon());
        hitPoints = character.getHitPoints();
        item = null;
        idleHands.setTexture(AllTextureFiles.getIdleTexture(character));
        lastWeaponUsedTexture = null;
        if(character.canResetAbility())
        {
            abilityReady = true;
        }
    }

    public void setPlayerIndicator()
    {
        Texture t = new Texture();
        try
        {
            if(getTeam() == GameScene.Team.First)
            {
                t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Player-Indicator-1.png"));
            }
            else
            {
                t.loadFromFile(Paths.get(AllTextureFiles.PARENT_FILE_STRING + "/assets/Sprites/Other/Player-Indicator-2.png"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        playerIndicator = new Sprite(t);
        playerIndicator.setOrigin(getLocalBounds().width/2, getLocalBounds().height/2);
        playerIndicator.setScale(0.4f, 0.4f);
    }

    public Item getItem()
    {
        return item;
    }

    public boolean getAbilityReady()
    {
        return abilityReady;
    }
    
    public void setCanChangeMode(boolean b)
    {
        canChangeMode = b;
    }

    @Override
    public void draw(RenderWindow window)
    {
        super.draw(window);
        drawPlayerAssets(window);
    }

    public void swapKeypress()
    {
        MapTile current = GameScene.getGameScene().getTileFromPlayer(this);

        if(current.hasHeldItem())
        {
            DropItem found = current.getHeldItem();
            if (found instanceof Weapon)
            {
                if (!weapon.getClass().equals(character.getDefaultWeapon().getClass()))
                {
                    current.setHeldItem(weapon);
                }
                else
                {
                    current.setHeldItem(null);
                }

                setWeapon((Weapon)found);
            }
            else if(found instanceof Item)
            {
                if(item instanceof Drone)
                {
                    current.setHeldItem(new DroneDrop());
                }
                else
                {
                    current.setHeldItem(item);
                }

                if(found instanceof DroneDrop)
                {
                    setItem(new Drone(getTeam()));
                }
                else
                {
                    setItem((Item)found);
                }
            }
        }
        else if(!weapon.getClass().equals(character.getDefaultWeapon().getClass()))
        {
            current.setHeldItem(weapon);
            setWeapon(character.getDefaultWeapon());
        }
    }

    @Override
    public void update()
    {
        //If on map is a river map, kill a player if they touch the bottom of the screen while standing on a river tile
        if (GameScene.getGameScene().getMap() instanceof RiverMap)
        {
            if (hasHitEdge() == Controller.Direction.DOWN && GameScene.getGameScene().getTileFromPlayer(this).getType() == 'r')
            {
                forceTakeDamage(this.hitPoints + 1);
            }
        }

        //Space map, all corners kill players
        if (GameScene.getGameScene().getMap() instanceof SpaceMap)
        {
            if (hasHitEdge() != null)
            {
                forceTakeDamage(hitPoints + 1);
            }
        }

        super.update();
        handleMapCollision();
    }

    public Texture getDead()
    {
        if(!getTexture().equals(death))
        {
            idleHands.setTexture(AllTextureFiles.getTransparentTexture());
            weapon.setTexture(AllTextureFiles.getTransparentTexture());
        }

        return death;
    }

    public void handleMapCollision()
    {
        if(!isOnGround())
        {
            return;
        }

        MapTile[][] possibleCollisionTiles = GameScene.getGameScene().getMap().getMapTileAreaSurroundingPosition(3, getGlobalCentrePoint());

        for (int x = 0; x < 3; ++x)
        {
            for (int y = 0; y < 3; ++y)
            {
                if (possibleCollisionTiles[x][y] instanceof CollisionTile)
                {
                    FloatRect overlap = getGlobalBounds().intersection(possibleCollisionTiles[x][y].getGlobalBounds());
                    if (overlap != null && overlap.height > 5)
                    {
                        if (overlap.width > overlap.height)
                        {
                            setSpeedY(0);

                            if (getGlobalBounds().top > possibleCollisionTiles[x][y].getGlobalBounds().top)
                            {
                                move(0, overlap.height);
                            }
                            // Player bottom edge collision.
                            else
                            {
                                move(0, -overlap.height);
                            }
                        }

                        //Checks if still in collision after the first move.
                        possibleCollisionTiles = GameScene.getGameScene().getMap().getMapTileAreaSurroundingPosition(3, getGlobalCentrePoint());
                        for (int x2 = 0; x2 < 3; ++x2)
                        {
                            for (int y2 = 0; y2 < 3; ++y2)
                            {
                                if (possibleCollisionTiles[x][y] instanceof CollisionTile)
                                {
                                    overlap = getGlobalBounds().intersection(possibleCollisionTiles[x][y].getGlobalBounds());
                                    if (overlap != null)
                                    {
                                        setSpeedX(0);

                                        // Player left edge collision.
                                        if (getGlobalBounds().left >
                                                possibleCollisionTiles[x][y].getGlobalBounds().left)
                                        {
                                            move(overlap.width, 0);
                                        }
                                        // Player right edge collision.
                                        else
                                        {
                                            move(-overlap.width, 0);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void wonRound()
    {
        ++roundWins;
    }

    public int getRoundWins()
    {
        return roundWins;
    }

    public void setLastWeaponUsedTexture(Texture t)
    {
        lastWeaponUsedTexture = t;
    }

    public Texture getLastWeaponUsedTexture()
    {
        return lastWeaponUsedTexture;
    }
}
