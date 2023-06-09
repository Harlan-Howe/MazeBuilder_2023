import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
/**
 * This class is one that you can run to test out the methods you are writing in the MazeBuilderPanel class. The
 * behavior is a little bit different than the normal public static void main mode. See Mr. Howe if you
 * have any questions!
 */
public class MazeBuilderPanelTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testInBounds()
	{
		MazeBuilderPanel mbp = new MazeBuilderPanel();
		assertTrue(mbp.inBounds(new Place(1,1)));
		assertFalse(mbp.inBounds(new Place(0,0)));
		assertTrue(mbp.inBounds(new Place(MazeBuilderPanel.NUM_ROWS-5, MazeBuilderPanel.NUM_COLS-5)));
		assertFalse(mbp.inBounds(new Place(5,MazeBuilderPanel.NUM_COLS)));
		assertFalse(mbp.inBounds(new Place(MazeBuilderPanel.NUM_ROWS-1, MazeBuilderPanel.NUM_COLS-1)));
		assertTrue(mbp.inBounds(new Place(MazeBuilderPanel.NUM_ROWS-2, MazeBuilderPanel.NUM_COLS-2)));
	}

	@Test
	public void testInGrid()
	{
		MazeBuilderPanel mbp = new MazeBuilderPanel();
		assertTrue(mbp.inGrid(new Place(1,1)));
		assertTrue(mbp.inGrid(new Place(0,0)));
		assertFalse(mbp.inGrid(new Place(-1,0)));
		assertFalse(mbp.inGrid(new Place(4,-1)));
		assertTrue(mbp.inGrid(new Place(MazeBuilderPanel.NUM_ROWS-5, MazeBuilderPanel.NUM_COLS-5)));
		assertFalse(mbp.inGrid(new Place(5,MazeBuilderPanel.NUM_COLS)));
		assertFalse(mbp.inGrid(new Place(MazeBuilderPanel.NUM_ROWS, MazeBuilderPanel.NUM_COLS-1)));
		assertTrue(mbp.inGrid(new Place(MazeBuilderPanel.NUM_ROWS-1, MazeBuilderPanel.NUM_COLS-1)));
		assertFalse(mbp.inGrid(new Place(MazeBuilderPanel.NUM_ROWS-1, MazeBuilderPanel.NUM_COLS)));
		assertTrue(mbp.inGrid(new Place(MazeBuilderPanel.NUM_ROWS/2, MazeBuilderPanel.NUM_COLS/2)));
	}

	@Test
	public void testPickPlaceOffList()
	{
		MazeBuilderPanel mbp = new MazeBuilderPanel();
		ArrayList<Place> items = new ArrayList<Place>();
		items.add(new Place(1,2));
		items.add(new Place(3,4));
		items.add(new Place(5,6));
		items.add(new Place(7,8));
		
		Place p1 = mbp.pickPlaceOffList(items);
		assertEquals(3,items.size());
		assertFalse(items.contains(p1));
		assertTrue(p1.equals(new Place(1,2)) || p1.equals(new Place(3,4)) || p1.equals(new Place(5,6)) || p1.equals(new Place(7,8)));
		Place p2 = mbp.pickPlaceOffList(items);
		assertEquals(2,items.size());
		assertFalse(p1.equals(p2));
		Place p3 = mbp.pickPlaceOffList(items);
		assertFalse(p1.equals(p3) || p2.equals(p3));
		Place p4 = mbp.pickPlaceOffList(items);
		assertFalse(p1.equals(p4) || p2.equals(p4) || p3.equals(p4));
		Place p5 = mbp.pickPlaceOffList(items);
		assertNull(p5);
	}
	
}
