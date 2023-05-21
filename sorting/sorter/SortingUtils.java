package sorting.sorter;

import sorting.sorter.InPlaceSortableList;

public class SortingUtils {
    public static <E extends Comparable<E>> void swap(InPlaceSortableList<E> list, int i, int j) {
        list.swap(i, j);
    }
}