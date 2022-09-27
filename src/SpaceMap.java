import java.util.Random;

public class SpaceMap extends Map
{
    public SpaceMap(int x, int y)
    {
        mapX = x;
        mapY = y;
        //Getting map segments
        MapSection[] sections = generateMapSections();
        //Drawing up blank map
        mapTiles = new MapTile[mapX][mapY];
        for (int j = 0; j < y; j++)
        {
            for (int i = 0; i < x; i++)
            {
                mapTiles[i][j] = new MapTile(this, i * TILE_SIZE + LEFT_INDENT, j * TILE_SIZE);
            }
        }
        //Loading a map feature in each segment
        Random r = new Random();
        for (MapSection s:sections)
        {
            if (r.nextInt(2) == 0)
            {
                generateCoverCluster(s);
            }
        }
        //Splattering some extra cover tiles across the map to add a sense of homogeny
        for (int i = 0; i < 50; i++)
        {
            placeRandomCover();
        }

        spawnInitialPickups();
    }

    protected void generateCoverCluster(MapSection s)
    {
        int startX = s.getBounds()[0];
        int endX = s.getBounds()[1];
        int startY = s.getBounds()[2];
        int endY = s.getBounds()[3];
        int[] mountainCentre = getCentrePoint(startX, endX, startY, endY);
        int maxCover = (endX - startX) * (endY - startY) / 15;
        for (int i = 1; i <= maxCover; i++)
        {
            boolean placedTile = false;
            int xOffset = 0;
            int yOffset = 0;
            int loopNum = 0;
            while (!placedTile && loopNum < 100)
            {
                Random r = new Random();
                switch (r.nextInt(4))
                {
                    case 0:
                        yOffset--;
                        break;
                    case 1:
                        xOffset++;
                        break;
                    case 2:
                        yOffset++;
                        break;
                    case 3:
                        xOffset--;
                        break;
                }
                if ((mountainCentre[0] + xOffset >= endX || mountainCentre[0] + xOffset < startX) || (mountainCentre[1] + yOffset >= endY || mountainCentre[1] + yOffset < startY))
                {
                    xOffset = 0;
                    yOffset = 0;
                }
                else
                {
                    if (mapTiles[mountainCentre[0] + xOffset][mountainCentre[1] + yOffset].getType() == 'b' &&
                            ((Math.abs(xOffset) + Math.abs(yOffset) / 2.0 > (0.25 * ((endX - startX) + (endY - startY)) /2 )) || i < maxCover * 0.75) )
                    {
                        mapTiles[mountainCentre[0] + xOffset][mountainCentre[1] + yOffset] = new CollisionTile(this, 'c', (mountainCentre[0] + xOffset) * TILE_SIZE + LEFT_INDENT, (mountainCentre[1] + yOffset) * TILE_SIZE);
                        placedTile = true;
                    }
                }
                loopNum++;
            }
        }
    }

    public void decay()
    {
        placeRandomCover();
    }
}
