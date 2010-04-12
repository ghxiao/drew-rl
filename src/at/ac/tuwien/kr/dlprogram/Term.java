package at.ac.tuwien.kr.dlprogram;

/**
 * The implementation of term.
 * 
 */
public interface Term extends Cloneable {
	/**
	 * Get the name of the term.
	 * 
	 * @return name of the term
	 */
	public String getName();

	/**
	 * Clone the term.
	 * 
	 * @return a deep copy of the term
	 */
	public Term clone();
}
