public interface AbstractMenu
{
    // The directions which a player may select another map.
    enum ArrowShiftDirection
    {
        UP, DOWN
    }

    void draw();
    void initialiseMenu();
    boolean detectEvents();
}