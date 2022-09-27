public class MapSection
{
    private int startX;
    private int endX;
    private int startY;
    private int endY;

    public MapSection(int sX, int eX, int sY, int eY)
    {
        startX = sX;
        endX = eX;
        startY = sY;
        endY = eY;
    }

    public int[] getBounds()
    {
        int[] bounds = {startX, endX, startY, endY};
        return bounds;
    }
}