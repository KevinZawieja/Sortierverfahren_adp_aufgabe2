package sorting.sorter;

public class QuickInsertionSorter implements Sorter {

    private final MedianStrategy medianStrategy;
    private final int insertionThreshold;


    public QuickInsertionSorter(MedianStrategy medianStrategy, int insertionThreshold) {
        this.medianStrategy = medianStrategy;
        this.insertionThreshold = insertionThreshold;
    }

    @Override
    public String getName() {
        return "Quick Sort (" + medianStrategy.toString() + ")";
    }

    @Override
    public <E extends Comparable<E>> void sort(InPlaceSortableList<E> list) {
        quickInsertionSort(list, 0, list.size() - 1);
    }

    private <E extends Comparable<E>> void quickInsertionSort(InPlaceSortableList<E> list, int low, int high) {
        if (high - low + 1 <= insertionThreshold) {
            // Verwende Insertion-Sort für kleine Listen
            insertionSort(list, low, high);
        } else {
            // Verwende Quicksort für größere Listen
            if (low < high) {
                int pivotIndex = partition(list, low, high);
                quickInsertionSort(list, low, pivotIndex - 1);
                quickInsertionSort(list, pivotIndex + 1, high);
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

    private <E extends Comparable<E>> void insertionSort(InPlaceSortableList<E> list, int low, int high) {
        int n = high;
        for (int i = low + 1; i <= n; i++) {
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