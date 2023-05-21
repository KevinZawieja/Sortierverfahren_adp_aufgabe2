package sorting.sorter;

import sorting.SortingWorkbench;

import java.util.ArrayList;
import java.util.List;

public class MergeSorter implements Sorter {

    @Override
    public String getName() {
        return "Merge Sort";
    }


    @Override
    public <E extends Comparable<E>> void sort(InPlaceSortableList<E> list) {
        mergeSort(list, 0, list.size() - 1);
    }

    private <E extends Comparable<E>> void mergeSort(InPlaceSortableList<E> list, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(list, left, mid);
            mergeSort(list, mid + 1, right);
            merge(list, left, mid, right);
        }
    }

    private <E extends Comparable<E>> void merge(InPlaceSortableList<E> list, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<E> leftList = new ArrayList<>();
        List<E> rightList = new ArrayList<>();

        for (int i = 0; i < n1; i++) {
            leftList.add(list.get(left + i));
        }
        for (int j = 0; j < n2; j++) {
            rightList.add(list.get(mid + 1 + j));
        }

        int i = 0;
        int j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            if (leftList.get(i).compareTo(rightList.get(j)) <= 0) {
                list.set(k, leftList.get(i));
                i++;
            } else {
                list.set(k, rightList.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            list.set(k, leftList.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            list.set(k, rightList.get(j));
            j++;
            k++;
        }
    }
}
