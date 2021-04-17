//TO DO: Add JavaDocs, you can add more things too, but don't break anything

/**
 *  The Memory Block class.
 *
 *  @author K. Raven Russell
 */
public class MemBlock {
	//******************************************************
	//****    IMPORTANT: DO NOT CHANGE/ALTER/REMOVE     ****
	//****    ANYTHING PROVIDED LIKE THESE INTS, THIS   ****
	//****    BOOLEAN, OR THE CONSTRUCTOR.              ****
	//******************************************************
	/**
	 * The address of the block.
	 */
	public final int addr;
	/**
	 * The size of the block.
	 */
	public final int size;
	/**
	 * The boolean value to represent its availability.
	 */
	public final boolean isFree;

	/**
	 *  This is a default constructor for MemBlock class.
	 * @param addr address of the block in memory
	 * @param size size of the block in memory
	 * @param isFree boolean value representing if it is free or not
	 */
	public MemBlock(int addr, int size, boolean isFree) {
		this.addr = addr;
		this.size = size;
		this.isFree = isFree;
	}
	
	//some "copy constructors" might be useful here...
}