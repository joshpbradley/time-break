import java.util.Random;

public class RiverMap extends Map
{
    private int floodProgress;

    public RiverMap(int x, int y)
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
                mapTiles[i][j] = new MapTile(this, i * TILE_SIZE + LEFT_INDENT, j *TILE_SIZE);
            }
        }
        //Loading a map feature in each segment
        Random r = new Random();
        int a = 0;
        for (MapSection s : sections)
        {
            if (a == 1 || a == 4 || a == 7)
            {
                for (int sY = s.getBounds()[2]; sY < s.getBounds()[3]; sY++)
                {
                    for (int sX = s.getBounds()[0]; sX < s.getBounds()[1]; sX++)
                    {
                        mapTiles[sX][sY] = new MapTile(this, 'r', sX * TILE_SIZE + LEFT_INDENT, sY * TILE_SIZE);
                    }
                }
            }
            else
            {
                switch (r.nextInt(5))
                {
                    case 1:
                    case 2:
                        generateCoverCluster(s);
                        break;
                    case 3:
                        generateLake(s);
                        break;
                    case 4:
                        generateMaze(s);
                        break;
                }
            }
            a++;
        }
        //Splattering some extra cover tiles across the map to add a sense of homogeny
        for (int i = 0; i < 50; i++) placeRandomCover();

        spawnInitialPickups();
    }


    protected MapSection[] generateMapSections()
    {
        Random r = new Random();
        int numY = 4;
        int numX = 4;
        int[] yLines = new int[numY];
        int[] xLines = new int[numX];
        //Get points on y axis
        yLines[0] = 0;
        for (int i = 1; i < numY - 1; i++) {
            yLines[i] = (int)(((double)i / (double)(numY - 1)) * mapY);
        }
        yLines[numY - 1] = mapY;
        //Get points on x axis
        xLines[0] = 0;
        for (int i = 1; i < numX - 1; i++) {
            xLines[i] = (int)(((double)i / (double)(numX - 1)) * mapX);
        }
        xLines[numX - 1] = mapX;
        //Get Sections
        MapSection[] sections = new MapSection[(numX - 1) * (numY - 1)];
        for (int y = 0; y < numY - 1; y++) {
            for (int x = 0; x < numX - 1; x++) {
                //Shifting the boundary lines in alternating rows and columns
                if (y % 2 == 0 && x > 0) {
                    sections[y * (numX - 1) + x] = new MapSection(xLines[x] + r.nextInt(3) - 3, xLines[x + 1], yLines[y], yLines[y + 1]);
                } else if (x % 2 ==0 && y > 0){
                    sections[y * (numX - 1) + x] = new MapSection(xLines[x], xLines[x + 1], yLines[y] + r.nextInt(3) - 3, yLines[y + 1]);
                } else {
                    sections[y * (numX - 1) + x] = new MapSection(xLines[x], xLines[x + 1], yLines[y], yLines[y + 1]);
                }
            }
        }
        return sections;
    }

    private void generateLake(MapSection s)
    {
        int startX = s.getBounds()[0];
        int endX = s.getBounds()[1];
        int startY = s.getBounds()[2];
        int endY = s.getBounds()[3];
        int[] lakeCentre = getCentrePoint(startX, endX, startY, endY);
        for (int i = 1; i <= (endX - startX) * (endY - startY) / 2; i++)
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
                if ((lakeCentre[0] + xOffset >= endX || lakeCentre[0] + xOffset < startX) || (lakeCentre[1] + yOffset >= endY || lakeCentre[1] + yOffset < startY)) {
                    xOffset = 0;
                    yOffset = 0;
                }
                else
                {
                    if (mapTiles[lakeCentre[0] + xOffset][lakeCentre[1] + yOffset].getType() == 'b')
                    {
                        if (i % 10 == 0)
                        {
                            mapTiles[lakeCentre[0] + xOffset][lakeCentre[1] + yOffset] = new CollisionTile(this, 'c', (lakeCentre[0] + xOffset) * TILE_SIZE + LEFT_INDENT, (lakeCentre[1] + yOffset) * TILE_SIZE);
                        }
                        else
                        {
                            mapTiles[lakeCentre[0] + xOffset][lakeCentre[1] + yOffset] = new MapTile(this, 'g', (lakeCentre[0] + xOffset) * TILE_SIZE + LEFT_INDENT, (lakeCentre[1] + yOffset) * TILE_SIZE);
                        }
                        placedTile = true;
                    }
                }
                loopNum++;
            }
        }
    }

    private void generateMaze(MapSection s)
    {
        int startX = s.getBounds()[0];
        int endX = s.getBounds()[1];
        int startY = s.getBounds()[2];
        int endY = s.getBounds()[3];
        int[] mazeCentre = getCentrePoint(startX, endX, startY, endY);
        //Generating central cover rectangle
        for (int y = (mazeCentre[1] - (mazeCentre[1] - startY) * 3 / 4); y < ((endY - mazeCentre[1]) * 3 / 4) + mazeCentre[1]; y++)
        {
            for (int x = (mazeCentre[0] - (mazeCentre[0] - startX) * 3 / 4); x < ((endX - mazeCentre[0]) * 3 / 4 + mazeCentre[0]); x++)
            {
                mapTiles[x][y] = new CollisionTile(this, 'c', x * TILE_SIZE + LEFT_INDENT, y * TILE_SIZE);
            }
        }
        //Generating two points, then creating a line of blank tiles between them
        Random r = new Random();
        for (int i = 0; i < 15; i++)
        {
            int pointAx = r.nextInt(endX - startX) + startX;
            int pointAy = r.nextInt(endY - startY) + startY;
            int pointBx = r.nextInt(endX - startX) + startX;
            int pointBy = r.nextInt(endY - startY) + startY;
            int loopNum = 0;
            while (!(pointAx == pointBx && pointAy == pointBy) && loopNum < 100)
            {
                mapTiles[pointAx][pointAy] = new MapTile(this, pointAx * TILE_SIZE + LEFT_INDENT, pointAy * TILE_SIZE);
                if (pointAx > 1 && pointAy > 1 && loopNum % 5 == 0)
                {
                    int rn1 = r.nextInt(2) - 1;
                    int rn2 = r.nextInt(2) - 1;
                    mapTiles[pointAx + rn1][pointAy + rn2] = new MapTile(this, 'g', (pointAx + rn1) * TILE_SIZE + LEFT_INDENT, (pointAy + rn2) * TILE_SIZE);
                }
                if (Math.abs(pointAy - pointBy) > Math.abs(pointAx - pointBx) && pointAy != pointBy)
                {
                    if (pointAy < pointBy)
                    {
                        pointAy++;
                    }
                    else
                    {
                        pointAy--;
                    }
                }
                else if (pointAx != pointBx)
                {
                    if (pointAx < pointBx)
                    {
                        pointAx++;
                    }
                    else
                    {
                        pointAx--;
                    }
                }
                loopNum++;
            }
        }
        //Generate lake in corner of the cave
        switch (r.nextInt(4))
        {
            case 1: //Top left
                generateLake(new MapSection(startX, mazeCentre[0], startY, mazeCentre[1]));
                break;
            case 2: //Top right
                generateLake(new MapSection(mazeCentre[0], endX, startY, mazeCentre[1]));
                break;
            case 3: //Bottom right
                generateLake(new MapSection(mazeCentre[0], endX, mazeCentre[1], endY));
                break;
            case 4:
                generateLake(new MapSection(startX, mazeCentre[0], mazeCentre[1], endY));
                break;
        }
    }

    int decayRuns = 0;
    public void decay()
    {
        decayRuns++;
        if (decayRuns % 3 == 0) {
            if (floodProgress < mapX / 2) {
                for (int y = 0; y < mapY; y++) {
                    mapTiles[mapX / 2 + floodProgress][y] = new MapTile(this, 'r', (mapX / 2 + floodProgress) * TILE_SIZE + LEFT_INDENT, y * TILE_SIZE);
                    mapTiles[mapX / 2 - floodProgress - 1][y] = new MapTile(this, 'r', (mapX / 2 - floodProgress - 1) * TILE_SIZE + LEFT_INDENT, y * TILE_SIZE);
                }
                floodProgress++;
            }
        }
    }
}
