package dk.sdu.mmmi.swe.gtg.common.sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class QuickSort {

    public static <T> void sort(List<T> list, Comparator<T> comparator) {
        sort(list, comparator, 0, list.size() - 1);
    }

    private static <T> void sort(List<T> list, Comparator<T> comparator, int low, int high) {
        if (low < high) {
            int pivot = partition(list, low, high, comparator);
            sort(list, comparator, low, pivot - 1);
            sort(list, comparator, pivot + 1, high);
        }
    }

    private static <T> int partition(List<T> list, int low, int high, Comparator<T> comparator) {
        T pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, i + 1, high);
        return i + 1;
    }
}
