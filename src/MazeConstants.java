import java.awt.Color;

public interface MazeConstants
{
   public final int SOLID = 0;  // code numbers for cells' status 
   public final int HOLLOW = 1;

   public final int START_MODE = 0;  // if you click on a cell, which node (start?/end?)
   public final int END_MODE = 1;    //     will get moved? See MBP's selectionMode variable.
   
   public final int CELL_SIZE = 10;  // how many pixels wide and tall is each cell
   public final int NUM_ROWS = 35;   // how many cells in the grid
   public final int NUM_COLS = 35;
   
   public final Color SOLID_COLOR = Color.DARK_GRAY;           // appearance (colors)
   public final Color HOLLOW_COLOR = new Color(192,192,168);
   public final Color START_COLOR = Color.ORANGE;
   public final Color END_COLOR = Color.GREEN;
   public final Color OUTLINE_COLOR = Color.WHITE;
   public final Color WALL_COLOR = Color.BLACK;
   public final Color POPPED_COLOR = Color.BLUE;
   public final Color PUSHED_COLOR = new Color(0, 168, 64);
   
   public final int OPTIMAL_PATH_CIRCLE_RADIUS = 3;

   public final  int NORTH = 1;
   public final  int EAST = 2;
   public final  int SOUTH = 4;
   public final  int WEST = 8;
   public final  int NORTHEAST = 16;
   public final  int SOUTHEAST = 32;
   public final  int SOUTHWEST = 64;
   public final  int NORTHWEST = 128;

   public final int ACTION_MODE_WAITING = 0;
   public final int ACTION_MODE_REBUILDING = 1;
   public final int ACTION_MODE_SOLVING = 2;

   public final int BUILD_DELAY_MS = 2;
   public final int SOLVE_DELAY_MS = 5;

   public final boolean SHOULD_DRAW_WALLS_AS_LINES = false;
}


