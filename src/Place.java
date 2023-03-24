
public class Place
{
	private int r, c;
	
	public Place(int inR, int inC)
	{
		r = inR;
		c = inC;
	}
	
	public Place()
	{
		this(-1,-1);
	}
	
	public int row()
	{
		return r;
	}
	
	public int column()
	{
		return c;
	}

	public String toString()
	{
		return "["+r+","+c+"]";
	}
	
	public boolean equals(Object other)
	{
		if (! (other instanceof Place))
			return false;
		Place otherPlace = (Place)other;
		boolean match = false;
		//check whether the data in otherPlace matches the data in this Place.
		//--------------
		//TODO: insert your code here.

		//--------------
		return match;
	}	
	
	/**
	 * returns the Place that is one cell north of this one.
	 * @return the Place north of this one.
	 */
	public Place north()
	{
		Place result = null;
		// TODO: write the code for this.
		
		return result;
	}
	
	/**
	 * returns the Place that is one cell south of this one.
	 * @return the Place south of this one.
	 */
	public Place south()
	{
		Place result = null;
		// TODO: write the code for this.

		return result;
	}
	/**
	 * returns the Place that is one cell east of this one.
	 * @return the Place east of this one.
	 */
	public Place east()
	{
		Place result = null;
		// TODO: write the code for this.
		return result;
	}
	/**
	 * returns the Place that is one cell west of this one.
	 * @return the Place west of this one.
	 */
	public Place west()
	{
		Place result = null;
		// TODO: write the code for this.

		return result;
	}

	/**
	 * determines whether the given Place is adjacent in cardinal direction from this Place.
	 * @param candidate - a location to consider.
	 * @return - whether the candidate is immediately to the north, south, east or west.
	 */
	public boolean isNeighbor(Place candidate)
	{
		boolean isNextDoor = false;
		
		// TODO: write the code for this.
			
		return isNextDoor;
		
	}
	
}
