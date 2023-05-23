package sorting.sorter;

import sorting.SortingWorkbench;

import java.util.Random;

public class QuickSorter implements Sorter {

    private final MedianStrategy medianStrategy;

    public QuickSorter(MedianStrategy medianStrategy) {
        this.medianStrategy = medianStrategy;
    }

    @Override
    public String getName() {
        return "Quick Sort (" + medianStrategy.toString() + ")";
    }



    @Override
    public <E extends Comparable<E>> void sort(InPlaceSortableList<E> list) {
        quickSort(list, 0, list.size() - 1);
    }

    private <E extends Comparable<E>> void quickSort(InPlaceSortableList<E> list, int low, int high) {
        while (low < high) {
            int pivotIndex = partition(list, low, high);

            // rekursives sortieren von kleineren Teillisten
            if (pivotIndex - low < high - pivotIndex) {
                quickSort(list, low, pivotIndex - 1);
                low = pivotIndex + 1;
            } else {
                quickSort(list, pivotIndex + 1, high);
                high = pivotIndex - 1;
            }
        }
    }


    private <E extends Comparable<E>> int partition(InPlaceSortableList<E> list, int low, int high) {
        int pivotIndex = getPivotIndex(list, low, high);
        list.swap(pivotIndex, high);

        int i = low - 1;
        for (int j = low; j <= high - 1; j++) {
            if (list.compare(j, high) < 0) {
                i++;
                list.swap(i, j);
            }
        }

        list.swap(i + 1, high);

        return i + 1;
    }

    private <E extends Comparable<E>> int getPivotIndex(InPlaceSortableList<E> list, int low, int high) {
        switch (medianStrategy) {
            case FIRST:
                return low;
            case MIDDLE:
                return low + (high - low) / 2;
            case MEDIAN_OF_THREE:
                int mid = low + (high - low) / 2;
                if (list.compare(low, mid) > 0) {
                    list.swap(low, mid);
                }
                if (list.compare(low, high) > 0) {
                    list.swap(low, high);
                }
                if (list.compare(mid, high) > 0) {
                    list.swap(mid, high);
                }
                return mid;
            default:
                throw new IllegalArgumentException("Invalid median strategy");
        }
    }

    public enum MedianStrategy {
        FIRST,
        MIDDLE,
        MEDIAN_OF_THREE
    }
}
