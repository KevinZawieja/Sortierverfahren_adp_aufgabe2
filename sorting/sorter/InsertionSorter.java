package sorting.sorter;

import sorting.SortingWorkbench;

public class InsertionSorter implements Sorter {
    @Override
    public String getName() {
        return "Insertion Sort";
    }


    @Override
    public <E extends Comparable<E>> void sort(InPlaceSortableList<E> list) {
        int size = list.size();
        for (int i = 1; i < size; i++) {
            E key = list.get(i);
            int j = i - 1;
            // Elemente im sortierten Bereich bewegen -> Platz machen für das Neue
            while (j >= 0 && list.compare(j + 1, j) < 0) {
                list.swap(j + 1, j);
                j--;
            }
            // das Element am richigen Platz setzen
            list.set(j + 1, key);
        }
    }
}
