package com.bookstore.alg;

import java.util.Comparator;
import java.util.List;


public class Sorting {

    // Insertion Sort (in-place, stable)
    public static <T> void insertionSort(List<T> list, Comparator<? super T> cmp) {
        if (list == null || list.size() <= 1) return;

        for (int i = 1; i < list.size(); i++) {
            T key = list.get(i);
            int j = i - 1;

            // Move larger elements to the right
            while (j >= 0 && cmp.compare(list.get(j), key) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }

            // Insert key at correct position
            list.set(j + 1, key);
        }
    }
}
