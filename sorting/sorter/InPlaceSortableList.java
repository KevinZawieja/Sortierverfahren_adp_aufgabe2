package sorting.sorter;

/**
 * An interface for generic lists of elements with a natural ordering.
 * This interface allows clients to sort 'in place' by using just the
 * two basic operations compare and swap.
 * It offers further operations for other sorting approaches.
 * 
 * @author Axel Schmolitzky
 * @version 2023
 */
public interface InPlaceSortableList<E extends Comparable<E>>
{
    /**
     * The size of this list.
     * 
     * @return the number of elements in this list.
     * @ensure result >= 0
     */
    public int size();
    
    /**
     * Checks whether the given position is a valid position
     * in this list.
     * 
     * @param position the position under test
     * @return true if (position >= 0) && (position < gibLaenge())
     */
    public boolean exists(int position);
    
    /**
     * Get the element at the given position.
     * @param position a valid position in this list.
     * @require exists(position)
     * @return the element at the given position
     * @ensure result != null
     */
    public E get(int position);
    
    /**
     * Set the given element at the given position in this list.
     * The size of the list will not change.
     * @param position a valid position in this list.
     * @param elem an element.
     * @require exists(position)
     * @require elem != null
     * @ensure size() == old size()
     */
    public void set(int position, E elem);
        
    /**
     * Swap the two elements at the given positions in this list.
     * 
     * @param i position of first element
     * @param k Position of second element
     * @throws IndexOutOfBoundsException if !(exists(i) && exists(k))
     */
    public void swap(int i, int k);
    
    /**
     * Return the result of the comparison of the two elements
     * at the given positions in this list.
     * 
     * @param i position of first element
     * @param k Position of second element
     * @throws IndexOutOfBoundsException if !(exists(i) && exists(k))
     */
    public int compare(int i, int k);
    
}
