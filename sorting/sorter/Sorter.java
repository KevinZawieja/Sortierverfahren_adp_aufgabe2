package sorting.sorter;

/**
 * An interface for sorting algorithms that sort InPlaceSortableLists.
 *  
 * @author Axel Schmolitzky 
 * @version 2023
 */

public interface Sorter
{
    String getName();

    
    <E extends Comparable<E>> void sort(InPlaceSortableList<E> list);
    
}
