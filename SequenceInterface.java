/**  SequenceInterface<T>
 * The goal of the interface is to make a sequence and be 
 * able to perform various tasks with this sequence
 */

public interface SequenceInterface<T>
{
	/** Add a new Object to the logical end of the SequenceInterface<T>
	 * @param item the item to be added.
	 */
	public void append(T item);

	/** Add a new Object to the logical start of the SequenceInterface<T>
	 * @param item the item to be added.
	 */
	public void prefix(T item);

	/** Delete the item at the logical start of the SequenceInterface<T>
	* @return the deleted item
	* @throws EmptySequenceException if the sequence is empty
	*/
	public T deleteHead();

	/** Delete the item at the logical end of the SequenceInterface<T>
	 * @return the deleted item
	 * @throws EmptySequenceException if the sequence is empty
	 */
	public T deleteTail();

	/**
	 * @return true if the SequenceInterface is empty, and false otherwise
	 * @throws EmptySequenceException if the sequence is empty
	 */
	public boolean isEmpty();

	/**
	 * @return the number of items currently in the SequenceInterface
	 */
	public int size();

	/**
	 * @return the the logically first item in the sequence
	 */
	public T first();

	/**
	 * @return the the logically last item in the sequence
	 */
	public T last();

	/** Checks if a given sequence is a subsequence of this sequence
	 * @param another the sequence to check
	 * @return true if another is a subsequence of this sequence or false otherwise
	 */
	public boolean hasSubsequence(SequenceInterface<T> another);

	/** Check if a given item comes right before another given item in the sequence
	 * @param first an item
	 * @param second another item
	 * @return true if first comes right before second in the sequence, or false otherwise
	 */
	public boolean predecessor(T first, T second);

	/** Return the number of occurences in the sequence of a given item
	 * @param item an item
	 * @return the number of occurences in the sequence of item
	 */
	public int getFrequencyOf(T item);

	/** Reset the SequenceInterface to empty status by reinitializing the variables
	 * appropriately
	 */
	public void clear();

	/** Return the item at a given logical position
	 * @param position the logical position starting from 0
	 * @return the item at logical position position
	 * @throws IndexOutOfBoundsException if position < 0
	                                     or position > size()-1
	 */
	public T itemAt(int position);

	/** Return an array containing the items in the sequence in their logical order
	 * @return the an array containing the items in the sequence in their logical order
	 *         or null if the sequence is empty
	 */
	public T[] toArray();

	/** Return the logical position in the sequence of the first occurrence of a given item
	 * @param item an item
	 * @return the logical position in the sequence of the first occurrence of item
	 *         or -1 if the item doesn't exist
	 */
	public int firstOccurrenceOf(T item);

	/** Return the index of a given item in the alphabet of the sequence
	 * @param item an item
	 * @return the index of item in the alphabet of the sequence
	 *         or -1 if the item doesn't exist
	 */
	public int indexInAlphabet(T item);


	/** Return the index in the alphabet of the next item in the sequence
	 * after the occurrence of item at position
	 * @param item an item
	 * @param position item's position
	 * @return the index in the alphabet of the item after the occurrence of item
	 *         at position
	 *         or -1 if the next item doesn't exist
	 */
	public int nextIndex(T item, int position);


	/** Return the index in the alphabet of the previous item in the sequence
	 *  before the occurrence of item at position
	 * @param item an item
	 * @param position item's position
	 * @return the index in the alphabet of the item before the occurrence of item
	 *         at position
	 *         or -1 if the previous item doesn't exist
	 */
	public int prevIndex(T item, int position);


}
