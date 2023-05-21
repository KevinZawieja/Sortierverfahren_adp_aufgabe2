package sorting.sorter;

import sorting.SortingWorkbench;

public class BubbleSorter implements Sorter {

        @Override
        public String getName() {
                return "Bubble Sort";
        }


        @Override
        public <E extends Comparable<E>> void sort(InPlaceSortableList<E> list) {
                int n = list.size();
                boolean swapped;
                for (int i = 0; i < n - 1; i++) {
                        swapped = false;
                        for (int j = 0; j < n - i - 1; j++) {
                                if (list.compare(j, j + 1) > 0) {
                                        list.swap(j, j + 1);
                                        swapped = true;
                                }
                        }
                        if (!swapped) {
                                break;
                        }
                }
        }
}
