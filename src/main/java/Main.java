import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    /**
     * Main Method for scanning and initiating mergesort
     *
     * @param args command line args
     * @throws FileNotFoundException if input.txt is not in classpath/does not exist.
     */
    public static void main(String[] args) throws FileNotFoundException {
        //Scan Input file, and copy integers from input.txt to integers array
        Scanner scan = new Scanner(new File("input.txt"));
        int[] integers = new int[8];
        int iterator = 0;
        while (scan.hasNextInt()) integers[iterator++] = scan.nextInt();

        //Print Values prior to sorting
        System.out.println(String.format("Value Before Sort: %s ", Arrays.toString(integers)));

        //initialize mergesort
        mergeSort(integers, iterator);

        //Print Values after sorting
        System.out.println(String.format("Value Before Sort: %s ", Arrays.toString(integers)));
    }

    /**
     * merge sort method, sort int[] array of size arraySize. For example,if input array is [2,3,1] then the sorted
     * array would be [1,2,3]
     *
     * @param array     of integers to be sorted
     * @param arraySize size of integer array to be sorted
     */
    private static void mergeSort(int[] array, int arraySize) {
        if (arraySize == 1) return;
        int middleIndex = arraySize / 2;

        int[] leftArray = new int[middleIndex];
        int[] rightArray = new int[arraySize - middleIndex];

        System.arraycopy(array, 0, leftArray, 0, middleIndex);
        System.arraycopy(array, middleIndex, rightArray, 0, arraySize - middleIndex);

        mergeSort(leftArray, middleIndex);
        mergeSort(rightArray, arraySize - middleIndex);

        merge(array, leftArray, rightArray, middleIndex, arraySize - middleIndex);
    }

    /**
     * Merge two split arrays into the larger array
     *
     * @param array      array to be sorted, ex: [1,2,4,3]
     * @param leftArray  left side sorted array, ex: [1,2]
     * @param rightArray right side sorted array, ex: [3,4]
     * @param leftIndex  left index of array, ex: 2
     * @param rightIndex right side index of array, ex: 2
     */
    private static void merge(int[] array, int[] leftArray, int[] rightArray, int leftIndex, int rightIndex) {
        int i = 0, j = 0, k = 0;

        while (i < leftIndex && j < rightIndex) {
            if (leftArray[i] <= rightArray[j]) array[k++] = leftArray[i++];
            else array[k++] = rightArray[j++];
        }

        while (i < leftIndex) array[k++] = leftArray[i++];
        while (j < rightIndex) array[k++] = rightArray[j++];

    }
}
