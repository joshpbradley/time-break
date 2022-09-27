import java.util.ArrayList;

public class Scientist extends Character
{
    public Scientist()
    {
        super(100, 1, 1, new Pistol(), true, false);
    }

    @Override
    public void Ability(Player player)
    {
        Map map = GameScene.getGameScene().getMap();
        MapTile[][] mapTiles = map.getMapTiles();
        ArrayList<MapTile> tiles = new ArrayList<>();

        for(int x = 0; x < mapTiles.length; x++)
            for(int y = 0; y < mapTiles[0].length; y++)
                if(mapTiles[x][y].hasHeldItem())
                    tiles.add(mapTiles[x][y]);

        for(MapTile tile: tiles)
        {
            MapTile current = GameScene.getGameScene().getTileFromPlayer(player);

            int centreX = current.getXPosition();
            int centreY = current.getYPosition();
            int x = centreX;
            int y = centreY;
            int directionChange = 0;
            int depth = 1;

            while(!checkIfFree(mapTiles[x][y]))
            {
                x = centreX;
                y = centreY;
                switch(directionChange)
                {
                    case(0):
                        if(x >= 0 + depth)
                        {
                            x -= depth;
                            directionChange++;
                        }
                        else
                            directionChange++;
                        break;
                    case(1):
                        if(x < mapTiles.length - 2 - depth)
                        {
                            x += depth;
                            directionChange++;
                        }
                        else
                            directionChange++;
                        break;
                    case(2):
                        if(y > 0  + depth)
                        {
                            y -= depth;
                            directionChange++;
                        }
                        else
                            directionChange++;
                        break;
                    case(3):
                        if(y < mapTiles[0].length - 2 - depth)
                        {
                            y += depth;
                            depth++;
                            directionChange = 0;
                        }
                        else
                        {
                            directionChange = 0;
                            depth++;
                        }
                        break;
                }
            }

            mapTiles[x][y].setHeldItem(tile.getHeldItem());
            if(mapTiles[x][y].getHeldItem() instanceof Weapon)
            {
                for(int i = 0; i < GameScene.getGameScene().getMap().getWeaponSpawnLocations().length; ++i)
                {
                    if(mapTiles[x][y].getHeldItem().equals(GameScene.getGameScene().getMap().getWeaponSpawnLocations()[i].getHeldItem()))
                    {
                        GameScene.getGameScene().getMap().getWeaponSpawnLocations()[i] = mapTiles[x][y];
                    }
                }
            }
            else if(mapTiles[x][y].getHeldItem() instanceof Item)
            {
                for(int i = 0; i < GameScene.getGameScene().getMap().getItemSpawnLocations().length; ++i)
                {
                    if(mapTiles[x][y].getHeldItem().equals(GameScene.getGameScene().getMap().getItemSpawnLocations()[i].getHeldItem()))
                    {
                        GameScene.getGameScene().getMap().getItemSpawnLocations()[i] = mapTiles[x][y];
                    }
                }
            }
            tile.setHeldItem(null);
        }
    }

    private boolean checkIfFree(MapTile tile)
    {
        switch (tile.getType())
        {
            case 'c':
            case 'R':
            case 'G':
            case 'B':
            case 'Y':
            case 'I':
                return false;
        }

        return !tile.hasHeldItem();
    }
}