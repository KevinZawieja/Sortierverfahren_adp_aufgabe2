package sorting;

import sorting.sorter.InPlaceSortableList;
import sorting.config.SortingMetrics;

import java.util.*;

/**
 * An Adapter that adapts an arbitrary ArrayList to the interface
 * InPlaceSortableList. It also counts the calls to the major operations:
 * set and swap as move operations, get and compare as comparison operations.
 * 
 * 
 * @author Axel Schmolitzky 
 * @version 2023
 */ 
class ListAdapter<E extends Comparable<E>> implements InPlaceSortableList<E> 
{
    private ArrayList<E> _list;
    private SortingMetrics _metrics;
    
    public ListAdapter(ArrayList<E> list, SortingMetrics metrics)
    {
        _list = list;
        _metrics = metrics;
    }
    
    public int size()
    {
        return _list.size();
    }
    
    /**
     * Checks whether the given position is a valid position
     * in this list.
     * 
     * @param position the position under test
     * @return true if (position >= 0) && (position < gibLaenge())
     */
    public boolean exists(int position)
    {
        return position >= 0 && position < _list.size();
    }
    
    public E get(int position)
    {
        _metrics.incrementCompares();
        return _list.get(position);
    }
        
    public void set(int position, E elem)
    {
        _list.set(position,elem);
        _metrics.incrementMoves();
    }
        
    /**
     * Swap the two elements at the given positions in this list.
     * 
     * @param i position of first element
     * @param k Position of second element
     * @throws IndexOutOfBoundsException if !(exists(i) && exists(k))
     */
    public void swap(int i, int k)
    {
        E tmp = _list.get(i);
        _list.set(i,_list.get(k));
        _list.set(k,tmp);
        
        _metrics.incrementMoves();
    }
    
    /**
     * Return the result of the comparison of the two elements
     * at the given positions in this list.
     * 
     * @param i position of first element
     * @param k Position of second element
     * @throws IndexOutOfBoundsException if !(exists(i) && exists(k))
     */
    public int compare(int i, int k)
    {
        _metrics.incrementCompares();
        
        return _list.get(i).compareTo(_list.get(k));
    }
    
    public List<E> getList()
    {
        return _list;
    }
}
