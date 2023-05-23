package sorting.sorter;

import sorting.SortingWorkbench;

public class BubbleSorter implements Sorter {

        @Override
        public String getName() {
                return "Bubble Sort";
        }


        @Override
        public <E extends Comparable<E>> void sort(InPlaceSortableList<E> list) {
                int size = list.size();
                for (int i = 0; i < size - 1; i++) {
                        for (int j = 0; j < size - i - 1; j++) {
                                if (list.compare(j, j + 1) > 0) {
                                        list.swap(j, j + 1);
                                }
                        }

                }
        }
}
