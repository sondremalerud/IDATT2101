package Ovinger.Oving3;

import java.util.Arrays;
import java.util.Random;

public class Oving3 {
    // Driver code
    public static void main(String[] args)
    {
        //hvilken er raskest med n = 100 000 000?
        // tilfeldige, duplikater, og forhåndssortert tabbell.
        // sjekk sum før og etter, og sjekk at de er sorterte

        System.out.println("Beviser først at metodene fungerer: \ndualPivotQuickSort():");
        int[] arr = { 24, 8, 42, 75, 65,34, 54, 12, 33 };
        System.out.println("Tabell før sortering: " + Arrays.toString(arr));
        System.out.println("Tabell er sortert: " + GFG.isSorted(arr));
        System.out.println("Sum før sortering: " + GFG.sum(arr));
        GFG.dualPivotQuickSort(arr, 0, arr.length-1);
        System.out.println("Sum etter sortering: " + GFG.sum(arr));
        System.out.println("Tabell etter sortering: " + Arrays.toString(arr));
        System.out.println("Tabell er sortert: " + GFG.isSorted(arr));

        System.out.println("\nquicksort():");
        arr = new int[]{24, 8, 42, 75, 65, 34, 54, 12, 33};
        System.out.println("Tabell før sortering: " + Arrays.toString(arr));
        System.out.println("Tabell er sortert: " + GFG.isSorted(arr));
        System.out.println("Sum før sortering: " + GFG.sum(arr));
        GFG.quicksort(arr, 0, arr.length-1);
        System.out.println("Sum etter sortering: " + GFG.sum(arr));
        System.out.println("Tabell etter sortering: " + Arrays.toString(arr));
        System.out.println("Tabell er sortert: " + GFG.isSorted(arr));

        //lager tabeller til testing
        int[] tilfeldig1 = GFG.createRandomArray(100000000);
        int[] tilfeldig2 = tilfeldig1;

        System.out.println("\n----------\nTilfeldig med dualPivotQuickSort():\nSum før sortering: " + GFG.sum(tilfeldig1));
        long start = System.currentTimeMillis();
        GFG.dualPivotQuickSort(tilfeldig1, 0, tilfeldig1.length-1);
        long end = System.currentTimeMillis();
        System.out.println("Sum etter sortering: " + GFG.sum(tilfeldig1));
        System.out.println("Tabell er sortert: " + GFG.isSorted(tilfeldig1));
        System.out.println("Tid brukt: " + (double)(end - start)/1000 + "s.");

        System.out.println("----------\nTilfeldig med quicksort():\nSum før sortering: " + GFG.sum(tilfeldig2));
        start = System.currentTimeMillis();
        GFG.quicksort(tilfeldig2, 0, tilfeldig2.length-1);
        end = System.currentTimeMillis();
        System.out.println("Sum etter sortering: " + GFG.sum(tilfeldig2));
        System.out.println("Tabell er sortert: " + GFG.isSorted(tilfeldig2));
        System.out.println("Tid brukt: " + (double)(end - start)/1000 + "s.");



        int[] duplikater1 = GFG.createArrayWithDuplicates(100000000);
        int[] duplikater2 = duplikater1;

        System.out.println("----------\nDuplikater med dualPivotQuickSort():\nSum før sortering: " + GFG.sum(duplikater1));
        start = System.currentTimeMillis();
        GFG.dualPivotQuickSort(duplikater1, 0, duplikater1.length-1);
        end = System.currentTimeMillis();
        System.out.println("Sum etter sortering: " + GFG.sum(duplikater1));
        System.out.println("Tabell er sortert: " + GFG.isSorted(duplikater1));
        System.out.println("Tid brukt: " + (double)(end - start)/1000 + "s.");

        System.out.println("----------\nDuplikater med quicksort():\nSum før sortering: " + GFG.sum(duplikater2));
        start = System.currentTimeMillis();
        GFG.quicksort(duplikater2, 0, duplikater2.length-1);
        end = System.currentTimeMillis();
        System.out.println("Sum etter sortering: " + GFG.sum(duplikater2));
        System.out.println("Tabell er sortert: " + GFG.isSorted(duplikater2));
        System.out.println("Tid brukt: " + (double)(end - start)/1000 + "s.");



        int[] sortert1 = GFG.createSortedArray(100000000);
        int[] sortert2 = sortert1;

        System.out.println("----------\nSortert med dualPivotQuickSort():\nSum før sortering: " + GFG.sum(sortert1));
        start = System.currentTimeMillis();
        GFG.dualPivotQuickSort(sortert1, 0, sortert1.length-1);
        end = System.currentTimeMillis();
        System.out.println("Sum etter sortering: " + GFG.sum(sortert1));
        System.out.println("Tabell er sortert: " + GFG.isSorted(sortert1));
        System.out.println("Tid brukt: " + (double)(end - start)/1000 + "s.");

        System.out.println("----------\nSortert med quicksort():\nSum før sortering: " + GFG.sum(sortert2));
        start = System.currentTimeMillis();
        GFG.quicksort(sortert2, 0, sortert2.length-1);
        end = System.currentTimeMillis();
        System.out.println("Sum etter sortering: " + GFG.sum(sortert2));
        System.out.println("Tabell er sortert: " + GFG.isSorted(sortert2));
        System.out.println("Tid brukt: " + (double)(end - start)/1000 + "s.");

    }
}

// Hentet fra https://www.geeksforgeeks.org/dual-pivot-quicksort/, men tilpasset litt
class GFG{

    public static boolean isSorted(int[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }

    // returns an array of given size, with random integers
    static int[] createRandomArray(int size){
        Random rng = new Random();
        int[] result = new int[size];
        for (int i = 0; i < size; i++){
            result[i] = rng.nextInt();
        }
        return result;
    }

    // every other number will be the alike
    static int[] createArrayWithDuplicates(int size){
        Random rng = new Random();
        int[] result = new int[size];
        int num1 = rng.nextInt();
        int num2 = rng.nextInt();
        for (int i = 0; i < size; i += 2){
            result[i] = num1;
            if (i+1 == size) break;
            result[i+1] = num2;
        }
        return result;
    }

    static int[] createSortedArray(int size){
        int[] result = new int[size];
        for (int i = 0; i < size; i++){
            result[i] = i;
        }
        return result;
    }

    static void swap(int[] arr, int i, int j)
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    static void dualPivotQuickSort(int[] arr, int low, int high) {

        if (low < high)
        {

            // piv[] stores left pivot and right pivot.
            // piv[0] means left pivot and
            // piv[1] means right pivot
            int[] piv;
            piv = partition(arr, low, high);

            dualPivotQuickSort(arr, low, piv[0] - 1);
            dualPivotQuickSort(arr, piv[0] + 1, piv[1] - 1);
            dualPivotQuickSort(arr, piv[1] + 1, high);
        }
    }

    static int[] partition(int[] arr, int low, int high)
    {
        //makes sure the algorithm runs fast even if the array is already sorted
        swap(arr, high - (high - low) / 3, high);
        swap(arr, low + (high - low) / 3, low);

        if (arr[low] > arr[high])
            swap(arr, low, high);

        // p is the left pivot, and q
        // is the right pivot.
            int j = low + 1;
            int g = high - 1, k = low + 1,
                    p = arr[low], q = arr[high];



        while (k <= g)
        {

            // If elements are less than the left pivot
            if (arr[k] < p)
            {
                swap(arr, k, j);
                j++;
            }

            // If elements are greater than or equal
            // to the right pivot
            else if (arr[k] >= q)
            {
                while (arr[g] > q && k < g)
                    g--;

                swap(arr, k, g);
                g--;

                if (arr[k] < p)
                {
                    swap(arr, k, j);
                    j++;
                }
            }
            k++;
        }
        j--;
        g++;

        // Bring pivots to their appropriate positions.
        swap(arr, low, j);
        swap(arr, high, g);

        // Returning the indices of the pivots
        // because we cannot return two elements
        // from a function, we do that using an array.
        return new int[] { j, g };
    }

    public static void bytt(int[] t, int i, int j) {
        int k = t[j];
        t[j] = t[i];
        t[i] = k;
    }

    private static int median3sort(int[] t, int v, int h){
        int m = (v + h) / 2;
        if (t[v] > t[m]) bytt(t, v, m);
        if (t[m] > t[h]) {
            bytt(t, m , h);
            if (t[v] > t[m]) bytt(t, v, m);
        }
        return m;
    }

    public static int splitt(int[] t, int v, int h) {
        int iv, ih;
        int m = median3sort(t, v, h);
        int dv = t[m];
        bytt(t, m, h - 1);
        for (iv = v, ih = h -1;;) {
            while (t[++iv] < dv) ;
            while (t[--ih] > dv) ;
            if (iv >= ih) break;
            bytt(t, iv, ih);
        }
        bytt(t, iv, h-1);
        return iv;
    }

    public static void quicksort(int[] t, int v, int h){
        if (h - v > 2) {
            int delepos = splitt(t, v, h);
            quicksort(t, v, delepos - 1);
            quicksort(t, delepos + 1, h);
        } else median3sort(t, v, h);
    }

    public static double sum(int[] t){
        return Arrays.stream(t).sum();
    }



    // Driver code
    public static void main(String[] args)
    {
        //hvilken er raskest med n = 100 000 000?
        // tilfeldige, duplikater, og forhåndssortert tabbell.
        // sjekk sum før og etter, og sjekk at de er sorterte

        System.out.println("Beviser først at metodene fungerer: \ndualPivotQuickSort():");
        int[] arr = { 24, 8, 42, 75, 65,34, 54, 12, 33 };
        System.out.println("Tabell før sortering: " + Arrays.toString(arr));
        System.out.println("Tabell er sortert: " + isSorted(arr));
        System.out.println("Sum før sortering: " + sum(arr));
        dualPivotQuickSort(arr, 0, arr.length-1);
        System.out.println("Sum etter sortering: " + sum(arr));
        System.out.println("Tabell etter sortering: " + Arrays.toString(arr));
        System.out.println("Tabell er sortert: " + isSorted(arr));

        System.out.println("\nquicksort():");
        arr = new int[]{24, 8, 42, 75, 65, 34, 54, 12, 33};
        System.out.println("Tabell før sortering: " + Arrays.toString(arr));
        System.out.println("Tabell er sortert: " + isSorted(arr));
        System.out.println("Sum før sortering: " + sum(arr));
        quicksort(arr, 0, arr.length-1);
        System.out.println("Sum etter sortering: " + sum(arr));
        System.out.println("Tabell etter sortering: " + Arrays.toString(arr));
        System.out.println("Tabell er sortert: " + isSorted(arr));

        //lager tabeller til testing
        int[] tilfeldig1 = createRandomArray(100000000);
        int[] tilfeldig2 = tilfeldig1;

        System.out.println("\n----------\nTilfeldig med dualPivotQuickSort():\nSum før sortering: " + sum(tilfeldig1));
        long start = System.currentTimeMillis();
        dualPivotQuickSort(tilfeldig1, 0, tilfeldig1.length-1);
        long end = System.currentTimeMillis();
        System.out.println("Sum etter sortering: " + sum(tilfeldig1));
        System.out.println("Tabell er sortert: " + isSorted(tilfeldig1));
        System.out.println("Tid brukt: " + (double)(end - start)/1000 + "s.");

        System.out.println("----------\nTilfeldig med quicksort():\nSum før sortering: " + sum(tilfeldig2));
        start = System.currentTimeMillis();
        quicksort(tilfeldig2, 0, tilfeldig2.length-1);
        end = System.currentTimeMillis();
        System.out.println("Sum etter sortering: " + sum(tilfeldig2));
        System.out.println("Tabell er sortert: " + isSorted(tilfeldig2));
        System.out.println("Tid brukt: " + (double)(end - start)/1000 + "s.");



        int[] duplikater1 = createArrayWithDuplicates(100000000);
        int[] duplikater2 = duplikater1;

        System.out.println("----------\nDuplikater med dualPivotQuickSort():\nSum før sortering: " + sum(duplikater1));
        start = System.currentTimeMillis();
        dualPivotQuickSort(duplikater1, 0, duplikater1.length-1);
        end = System.currentTimeMillis();
        System.out.println("Sum etter sortering: " + sum(duplikater1));
        System.out.println("Tabell er sortert: " + isSorted(duplikater1));
        System.out.println("Tid brukt: " + (double)(end - start)/1000 + "s.");

        System.out.println("----------\nDuplikater med quicksort():\nSum før sortering: " + sum(duplikater2));
        start = System.currentTimeMillis();
        quicksort(duplikater2, 0, duplikater2.length-1);
        end = System.currentTimeMillis();
        System.out.println("Sum etter sortering: " + sum(duplikater2));
        System.out.println("Tabell er sortert: " + isSorted(duplikater2));
        System.out.println("Tid brukt: " + (double)(end - start)/1000 + "s.");



        int[] sortert1 = createSortedArray(100000000);
        int[] sortert2 = sortert1;

        System.out.println("----------\nSortert med dualPivotQuickSort():\nSum før sortering: " + sum(sortert1));
        start = System.currentTimeMillis();
        dualPivotQuickSort(sortert1, 0, sortert1.length-1);
        end = System.currentTimeMillis();
        System.out.println("Sum etter sortering: " + sum(sortert1));
        System.out.println("Tabell er sortert: " + isSorted(sortert1));
        System.out.println("Tid brukt: " + (double)(end - start)/1000 + "s.");

        System.out.println("----------\nSortert med quicksort():\nSum før sortering: " + sum(sortert2));
        start = System.currentTimeMillis();
        quicksort(sortert2, 0, sortert2.length-1);
        end = System.currentTimeMillis();
        System.out.println("Sum etter sortering: " + sum(sortert2));
        System.out.println("Tabell er sortert: " + isSorted(sortert2));
        System.out.println("Tid brukt: " + (double)(end - start)/1000 + "s.");

    }
}
