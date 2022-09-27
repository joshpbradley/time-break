import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Vector2f;

public class Crystal extends CollisionTile implements Explodable
{
    private boolean destroyed = false;

    public Crystal(Map map, char type, int x, int y)
    {
        super(map, type, x, y);
    }

    @Override
    public void applyExplosionEffects(AbstractPlayer playerAfflicted, boolean firstHit)
    {
        if(firstHit)
        {
            switch (getType())
            {
                case 'R':
                    playerAfflicted.takeDamage(40);
                    break;
                case 'Y':
                    playerAfflicted.lockout(2000);
                    break;
                case 'B':
                    playerAfflicted.addStatusEffect(new Haste(3000));
                    break;
                case 'G':
                    playerAfflicted.takeDamage(10);
            }
        }
        else if(getType() == 'R')
        {
            playerAfflicted.takeDamage(5);
        }
    }

    @Override
    public void applyExplosionEffects(MapTile tileAfflicted, boolean firstHit)
    {

        if(firstHit)
        {
            if (getType() == 'R')
            {
                tileAfflicted.takeDamage(new Pistol().getDamage());
            }
        }
        else
        {
            if (getType() == 'R')
            {
                tileAfflicted.takeDamage(5);
            }
        }
        tileAfflicted.isDestroyed();
    }

    @Override
    public void replaceBrokenTile()
    {
        super.replaceBrokenTile();
        detonate();
        destroyed = true;
    }

    @Override
    public void detonate()
    {
        Vector2f centrePoint = new Vector2f(getGlobalBounds().left + getGlobalBounds().width/2, getGlobalBounds().top + getGlobalBounds().height/2);
        switch(getType())
        {
            case 'R':
                GameScene.getGameScene().add(new Explosion(this,2000, 5, (int)centrePoint.x, (int)centrePoint.y));
                break;
            case 'G':
                GameScene.getGameScene().add(new Cloud(3, 6, (int)centrePoint.x, (int)centrePoint.y, getType()));
                break;
            case 'B':
            case 'Y':
                GameScene.getGameScene().add(new Cloud(1, 6, (int)centrePoint.x, (int)centrePoint.y, getType()));
                int tileSearchLength = 5;
                MapTile[][] tilesSurroundingCrystal = GameScene.getGameScene().getMap().getMapTileAreaSurroundingPosition(tileSearchLength, centrePoint);
                Player player1 = ((CrystalMap)GameScene.getGameScene().getMap()).getPlayer1();
                Player player2 = ((CrystalMap)GameScene.getGameScene().getMap()).getPlayer2();

                FloatRect player1Bounds = player1.getGlobalBounds();
                FloatRect player2Bounds = player2.getGlobalBounds();
                boolean player1EffectApplied = false;
                boolean player2EffectApplied = false;

                for(int x = 0; x < tileSearchLength; ++x)
                {
                    for(int y = 0; y < tileSearchLength; ++y)
                    {
                        if(!player1EffectApplied && player1Bounds.intersection(tilesSurroundingCrystal[x][y].getGlobalBounds()) != null)
                        {
                            if(getType() == 'B')
                            {
                                player1.lockout(2000);
                            }
                            else
                            {
                                player1.addStatusEffect(new Haste(3000));
                            }
                            player1EffectApplied = true;
                        }

                        if(!player2EffectApplied && player2Bounds.intersection(tilesSurroundingCrystal[x][y].getGlobalBounds()) != null)
                        {
                            if(getType() == 'B')
                            {
                                player2.lockout(2000);
                            }
                            else
                            {
                                player2.addStatusEffect(new Haste(3000));
                            }
                            player2EffectApplied = true;
                        }

                        if(player1EffectApplied && player2EffectApplied)
                        {
                            return;
                        }
                    }
                }
        }
    }
}
