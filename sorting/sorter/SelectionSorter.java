package sorting.sorter;

import sorting.SortingWorkbench;
import sorting.sorter.InPlaceSortableList;
import sorting.config.SortingMetrics;

public class SelectionSorter implements Sorter {

    @Override
    public String getName() {
        return "Selection Sort";
    }


    @Override
    public <E extends Comparable<E>> void sort(InPlaceSortableList<E> list) {
        int size = list.size();
        for (int i = 0; i < size - 1; i++) {
            int minIndex = i;
            // Suche das Minimum im unsortierten Teil der Liste
            for (int j = i + 1; j < size; j++) {
                if (list.compare(j, minIndex) < 0) {
                    minIndex = j;
                }

            }
            // Tausche das gefundene Minimum an die richtige Position
            list.swap(i, minIndex);
        }
    }
}
