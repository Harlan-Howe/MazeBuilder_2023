import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MazeBuilderPanel extends JPanel implements MazeConstants, MouseListener
{
	private MazeCell[][] theGrid;
	private int selectionMode;
	private JLabel statusLabel;
	private Place startLoc;
	private Place endLoc;
	private Stack<Place> optimal;
	private BuildSolveThread bsThread;
	
	public MazeBuilderPanel()
	{
		super();
		theGrid = new MazeCell[NUM_ROWS][NUM_COLS];
		for (int r=0; r<NUM_ROWS; r++)
			for (int c=0; c<NUM_COLS; c++)
				theGrid[r][c] = new MazeCell(r,c);
		generateWallCodes();
		startLoc = new Place(1,1);
		theGrid[1][1].setStart(true);
		endLoc = new Place(NUM_ROWS-2,NUM_COLS-2);
		theGrid[NUM_ROWS-2][NUM_COLS-2].setEnd(true);
		addMouseListener(this);
		optimal = new Stack<Place>();
		selectionMode = END_MODE;
		bsThread = new BuildSolveThread();
		bsThread.start();
	}
	
	/**
	 * determines whether the given place location is within the maze, but not on one of the edges.
	 *      +-------+
	 *      |       |
	 *      | XXXXX |
	 *      | XXXXX |
	 *      | XXXXX |
	 *      | XXXXX |
	 *      |       |
	 *      +-------+
	 * @param p - a place to consider.
	 * @return whether the place corresponds to one of the "X" locations above
	 */
	public boolean inBounds(Place p)
	{
		boolean isInBounds = false;
		//TODO: Insert your code here.

		//---------------------------
		return isInBounds;
		
	}

	/**
	 * This is very similar to the inBounds() method, but includes the edges of the board as viable locations. (In other
	 * words, you would expect that if a Place is inGrid, there would be a Cell at that location.)
	 *      +-------+
	 *      |XXXXXXX|
	 *      |XXXXXXX|
	 *      |XXXXXXX|
	 *      |XXXXXXX|
	 *      |XXXXXXX|
	 *      |XXXXXXX|
	 *      +-------+
	 * @param p - a place to consider.
	 * @return whether the place corresponds to one of the "X" locations above
	 */
	public boolean inGrid(Place p)
	{
		//TODO: Insert your code here.
		return false; // temporary stub code. Replace with something better.
	}

	/**
	 * gets the "MazeCell" item in theGrid in the given location
	 * @param p - the location of the cell we want to get.
	 * @return - the MazeCell at this location, or null if p is not in the grid.
	 */
	public MazeCell cellAt(Place p)
	{
		MazeCell mc = null;
		// TODO: Insert your code here.

	    //----------------------------
	    return mc;
	}
	
	
	/**
	 * selects one Place from the list, "choices", at random, removes it from the list and returns it.
	 * @param choices - a list of Places.
	 * @return one of the items on the list of places; if the list was initially empty, returns null.
	 * postcondition: if the list had any items, it now has one fewer item than before - the item removed
	 *                 from this list is what is returned.
	 */
	public Place pickPlaceOffList(List<Place> choices)
	{
		Place chosenItem = null;
		// TODO: Insert your code here.

		// Note #1: (int)(Math.random()*8) will give one of (0, 1, 2, 3, 4, 5, 6, 7)
        // Note #2: "List<Place>" is a parent class for "ArrayList<Place>."
		//           You can assume that size(), set(), get(), insert(), remove() etc. work for it - ArrayList
		//           overrides these methods.
		//-----------------------------
		return chosenItem;
	}
	
	
	/**
	 * returns a list of the in-bounds places immediately next to the given place where there are cells with
	 * the given SOLID/HOLLOW state.
	 * @param p - the center of the neighborhood under consideration
	 * @param state - which type of blocks (SOLID/HOLLOW) that we want to collect.
	 * @return - a list of places that a) are in-bounds, b) have the given state, and c)  are neighbors of "p."
	 */
	public List<Place> getNeighborsOfState(Place p, int state)
	{
		ArrayList<Place> result = new ArrayList<Place>();
		// TODO: Insert your code here.

		//------------------------------------------------
		return result;
	}
	

	/**
	 * fills in the entire maze with solid rock. Typically done before generating a whole new maze.
	 * 
	 */
	public void resetMazeToSolid()
	{
		for (int r=0; r<NUM_ROWS; r++)
			for (int c=0; c<NUM_COLS; c++)
				theGrid[r][c].setStatus(SOLID);
		resetSolveStates();
		generateWallCodes();
		repaint();
		setStatus("Maze refilled.");
	}
	
	/**
	 * resets all the popped/pushed "solve" states from the last time we solved the maze.
	 */
	public void resetSolveStates()
	{
		for (int r=0;r<NUM_ROWS; r++)
			for (int c=0; c<NUM_COLS; c++)
			{
				theGrid[r][c].setPopped(false);
				theGrid[r][c].setPushed(false);
			}
		optimal.clear();
		repaint();
	}

	/**
	 * loops through the entire grid and updates the wall codes for each cell.
	 * (Used for "fancy" walls. Activate via MazeConstants.SHOULD_DRAW_WALLS_AS_LINES.)
	 */
	public void generateWallCodes()
	{
		for (int r=0; r<NUM_ROWS; r++)
			for (int c=0; c<NUM_COLS; c++)
			{
				Place loc = new Place(r,c);
				cellAt(loc).setWallCode(getWallCodeForCellAt(loc));
			}
	}

	/**
	 * loops through the nine cells in the vicinity of the given location and updates their wall codes.
	 * (Used for "fancy" walls. Activate via MazeConstants.SHOULD_DRAW_WALLS_AS_LINES.)
	 * This will cause each of the nine squares to check its immediate neighbors.
	 * @param loc - the place whose neighborhood we should scan.
	 */
	public void updateWallCodesInNeighborhood(Place loc)
	{
		for (int deltaRow = -1; deltaRow <= 1; deltaRow++)
			for (int deltaCol = -1; deltaCol <= 1; deltaCol++)
			{
				Place loc2 = new Place(loc.row()+deltaRow, loc.column()+deltaCol);
				if (inGrid(loc2))
					cellAt(loc2).setWallCode(getWallCodeForCellAt(loc2));
			}
	}

	/**
	 * checks all the neighbors of this cell (N, NE, E, SE, S, SW, W, NW) to see whether they
	 * are solid. Generates a number 0-255 representing which of the directions have solids.
	 * (Used for "fancy" walls. Activate via MazeConstants.SHOULD_DRAW_WALLS_AS_LINES.)
	 * @param loc - a location whose neighbors we wish to scan.
	 * @return - an 8-bit binary number indicating which neighbors have solid states.
	 */
	public int getWallCodeForCellAt(Place loc)
	{
		int output = 0;

		if (loc.row()>0 && cellAt(loc.north()).getStatus() == SOLID)
			output+=NORTH;
		if (loc.column()< NUM_COLS-1 && cellAt(loc.east()).getStatus() == SOLID)
			output+=EAST;
		if (loc.row() <NUM_ROWS-1 && cellAt(loc.south()).getStatus() == SOLID)
			output+=SOUTH;
		if (loc.column()>0 && cellAt(loc.west()).getStatus() == SOLID)
			output+=WEST;
		if (loc.row()>0 && loc.column()<NUM_COLS-1 && cellAt(loc.north().east()).getStatus() == SOLID)
			output+=NORTHEAST;
		if (loc.row()<NUM_ROWS-1 && loc.column()<NUM_COLS-1 && cellAt(loc.south().east()).getStatus() == SOLID)
			output+=SOUTHEAST;
		if (loc.row()<NUM_ROWS-1 && loc.column()>0 && cellAt(loc.south().west()).getStatus() == SOLID)
			output+=SOUTHWEST;
		if (loc.row()>0 && loc.column()>0 && cellAt(loc.north().west()).getStatus() == SOLID)
			output+=NORTHWEST;

		return output;
	}

	/**
	 * User just pressed "Rebuild." Sets the actionMode flag in the BuildSolveThread to start building the maze.
	 */
	public void doRebuild() { bsThread.setActionMode(ACTION_MODE_REBUILDING); }

	/**
	 * User just pressed "Solve." Sets the actionMode flag in the BuildSolveThread to start solving the maze.
	 */
	public void doSolve()
	{
		bsThread.setActionMode(ACTION_MODE_SOLVING);
	}

	/**
	 * draws the maze in the main part of the window.
	 */
	public void paintComponent(Graphics g)
	{
		for (int r=0; r<NUM_ROWS; r++)
			for (int c=0; c<NUM_COLS; c++)
			{
				theGrid[r][c].drawSelf(g);
				
				// ---------------- Only used in part D ---------------------
				// draws a red circle in all "optimal" cells.
				if (optimal.contains(new Place(r,c)))
				{
					g.setColor(Color.RED);
					g.drawOval(c*CELL_SIZE+CELL_SIZE/2-OPTIMAL_PATH_CIRCLE_RADIUS, 
							   r*CELL_SIZE+CELL_SIZE/2-OPTIMAL_PATH_CIRCLE_RADIUS, 
							   2*OPTIMAL_PATH_CIRCLE_RADIUS,
							   2*OPTIMAL_PATH_CIRCLE_RADIUS);
				}
				// ----------------------------------------------------------
			}
	}

// ---------------------------   GUI stuff  ---------------------------------------------
	/**
	 * used by the frame to tell this class about the status label. You shouldn't need to mess with this.
	 * @param lab - the label we are receiving from the frame.
	 */
	public void attachStatusLabel(JLabel lab)
	{
		statusLabel = lab;
	}
	/**
	 * Allows the program to display a short status message in the bottom of the window.
	 * @param stat - a status string to show on the frame's status label.
	 */
	public void setStatus(String stat)
	{
		statusLabel.setText(stat);
	}
	
	/**
	 * accessor/modifier for the selectionMode. If this is in START_MODE, then clicking in the maze should reset the
	 * starting location in the maze. If this is in END_MODE, then clicking in the maze should reset the ending
	 * location in the maze.
	 * @return
	 */
	public int getSelectionMode()
	{
		return selectionMode;
	}
	public void setSelectionMode(int selectionMode)
	{
		System.out.println("MBP: setting selection mode: "+selectionMode);
		this.selectionMode = selectionMode;
	}
	
	// ---------------------------- used MouseListener methods -------------------------
	@Override
	/**
	 * the user just let go of the mouse inside this panel.
	 */
	public void mouseReleased(MouseEvent e)
	{
		System.out.println("dealing with mouse release in panel.");
		// TODO Auto-generated method stub
		int r = e.getY()/CELL_SIZE;
		int c = e.getX()/CELL_SIZE;
		Place clickedPlace = new Place(r,c);
		System.out.println(clickedPlace);
		if (! inBounds(clickedPlace))
		{
			setStatus("Invalid location");
			return; // this isn't eligible
		}
		if (selectionMode==START_MODE)
		{
			if (startLoc.equals(clickedPlace))
				return; // no change.
			theGrid[startLoc.row()][startLoc.column()].setStart(false);
			theGrid[clickedPlace.row()][clickedPlace.column()].setStart(true);
			startLoc = clickedPlace;
			setStatus("Start moved: "+startLoc);
			resetSolveStates();
			repaint();
		}
		else if (selectionMode==END_MODE)
		{
			if (endLoc.equals(clickedPlace))
				return; // no change.
			theGrid[endLoc.row()][endLoc.column()].setEnd(false);
			theGrid[clickedPlace.row()][clickedPlace.column()].setEnd(true);
			endLoc = clickedPlace;
			setStatus("End moved: "+endLoc);
			resetSolveStates();
			repaint();
		}
	}
	
	// ------------------------------------  unused MouseListener methods.-----------------------------
	@Override
	public void mouseClicked(MouseEvent e)
	{	}
	@Override
	public void mousePressed(MouseEvent e)
	{	}
	@Override
	public void mouseEntered(MouseEvent e)
	{ }
	@Override
	public void mouseExited(MouseEvent e)
	{ }
	
	// --------------------------------------  BuildSolveThread class ----------------------------------------
	public class BuildSolveThread extends Thread
	{
		private int actionMode;

		public BuildSolveThread()
		{
			super();
			actionMode = ACTION_MODE_WAITING;
		}

		public void setActionMode(int mode)
		{
			actionMode = mode;
		}

		/**
		 * loop forever, waiting for actionMode to change. If it has, call rebuildMaze() or
		 * findPathFromStartToFinish(). Then wait for the next time.
		 */
		public void run()
		{
			while (true)
			{
				switch (actionMode)
				{
					case ACTION_MODE_REBUILDING:
						rebuildMaze();
						actionMode = ACTION_MODE_WAITING;
						break;

					case ACTION_MODE_SOLVING:
						findPathFromStartToFinish();
						actionMode = ACTION_MODE_WAITING;
						break;

				}
				// wait for a quarter second before checking again, so we don't hog the CPU cycles.
				falconSnooze(250);
			}
		}


		/**
		 * clears the entire maze and reconstructs it.
		 */
		public void rebuildMaze()
		{
			System.out.println("Rebuilding Maze.");
			resetMazeToSolid();
			// TODO: insert your code here..... Do this AFTER you have written the TODOs above and in Place.java.
			// • I recommend you start with Stack<Place> myStack = new Stack<Place>();
			// • Every time you say myStack.pop(), you should then say falconSnooze(BUILD_DELAY_MS) to allow the
			//   animation to happen.
			// • Also, whenever you make a cell become HOLLOW, you should also tell that cell and its neighbors to
			//   update their wall codes. (This only affects the drawing when you are in "draw walls" mode.) Use
			//   updateWallCodesInNeighborhood(place) to do this for the cell and its neighbors all at once.
			// • Don't forget to call repaint() every time you change the status (HOLLOW/SOLID) of a cell, so you can
			//   see it...




			//-------------------------------
			setStatus("Maze rebuilt.");
			System.out.println("Done rebuilding.");

		}

		public void findPathFromStartToFinish()
		{

			resetSolveStates();
			optimal.clear();
			setStatus("Searching maze");
			// TODO: insert your code here.   Do this AFTER you have written rebuildMaze.
			Stack<Place> mySolutionStack = new Stack<Place>();
			// • There is already a stack of Places called "optimal" that is a class variable.
			// • Whenever you push or pull from mySolutionStack, you should say cellAt(loc).setPushed(true) or
			//   cellAt(loc).setPopped(true) so they will be marked onscreen. (and call repaint().)
			// • Whenever you pop from either stack, call falconSnooze(SOLVE_DELAY_MS) so it will animate.


			//--------------------------------------
			optimal.clear();
			repaint();
			setStatus("No Path found."); // you may want to add a happier message elsewhere in your code!

		}

		/**
		 * pauses this thread to avoid hogging the CPU and to insert a delay for animation.
		 * @param t - the amount of time to pause, in milliseconds.
		 */
		public void falconSnooze(int t)
		{
			try
			{
				Thread.sleep(t);
			}
			catch (InterruptedException iExp)
			{
				iExp.printStackTrace();
			}
		}
	}

}
