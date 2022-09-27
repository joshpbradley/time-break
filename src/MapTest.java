import java.util.concurrent.TimeUnit;

public class MapTest
{
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[40m";
    private static final String ANSI_GREEN = "\u001B[42m";
    private static final String ANSI_BLUE = "\u001B[44m";
    private static final String ANSI_CYAN = "\u001B[46m";

    public static void main(String[] args)
    {
        int x = 32;
        int y = 18;
        Map m = new RiverMap(x, y);
        MapTile[][] mt = m.getMapTiles();
        while(true) {
            for (int j = 0; j < y; j++)
            {
                for (int i = 0; i < x; i++)
                {
                    switch (mt[i][j].getType()) {
                        case 'b':
                            System.out.print(ANSI_GREEN + "  " + ANSI_RESET);
                            break;
                        case 'c':
                            System.out.print(ANSI_BLACK + "  " + ANSI_RESET);
                            break;
                        case 'g':
                            System.out.print(ANSI_CYAN + "  " + ANSI_RESET);
                            break;
                        case 'r':
                            System.out.print(ANSI_BLUE + "  " + ANSI_RESET);
                            break;
                    }
                }
            }

            try
            {
                TimeUnit.MILLISECONDS.sleep(250);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            m.decay();
        }
    }
}
