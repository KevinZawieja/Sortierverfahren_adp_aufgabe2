package sorting.sorter;

import sorting.SortingWorkbench;

public class InsertionSorter implements Sorter {
    @Override
    public String getName() {
        return "Insertion Sort";
    }


    @Override
    public <E extends Comparable<E>> void sort(InPlaceSortableList<E> list) {
        int n = list.size();
        for (int i = 1; i < n; i++) {
            E key = list.get(i);
            int j = i - 1;
            // Shift the elements in the sorted part of the list to make space for the current element
            while (j >= 0 && list.compare(j + 1, j) < 0) {
                list.swap(j + 1, j);
                j--;
            }
            // Place the current element at the correct position in the sorted part of the list
            list.set(j + 1, key);
        }
    }
}
