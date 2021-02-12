import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.logging.Level;

public class MultiThreadMergeSort extends Thread {
    private static final Logger DEBUGGER = Logger.getLogger("DEBUG"); //debugger
    private int[] array; //array to sort
    private int[] result; //sorted array, used in merging after joining threads
    private int arraySize; //size of array to sort (not necessary but makes it easier)
    private String leaf; //holds the current position of the thread in the tree

    MultiThreadMergeSort(int[] array, int arraySize, String leaf, boolean isRight) {
        this.array = array;
        this.result = array;
        this.arraySize = arraySize;
        this.leaf = String.format("%s%s", leaf, isRight ? "1" : "0");
    }

    /**
     * Write to output.txt file
     *
     * @param outputLine text to write to output.txt
     */
    private void writeToOutputFile(String outputLine) {
        try {
            FileWriter writer = new FileWriter("output.txt", true);
            writer.write(outputLine);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * run method for multithreaded merge sort, writes thread number
     * to output file and prints result of sorted array after thread is completed
     */
    @Override
    public void run() {
        writeToOutputFile(String.format("Thread %s started \n", leaf));
        sort();
        writeToOutputFile(String.format("Thread %s finished: %s \n", leaf, Arrays.toString(result)));
    }

    /**
     * Sorts integer array recursively using merge sort. Each split will initialize a new thread
     * -> not efficient, but fun :)
     * <p>
     * EX: t1 -> 3304, 8221, 26849, 14038, 1509, 6367, 7856, 21362
     * /t10                               \t11
     * 3304, 8221, 26849, 14038             1509, 6367, 7856, 21362
     * /t100          \t101               /t110          \t111
     * 3304, 8221       26849, 14038      1509, 6367       7856, 21362
     * /t1000   \t1001   /t1010   \t1011   /t1100   \t1101    /t1110   \t1111
     * 3304     8221     26849     14038  1509      6367     7856      21362
     * at the edge of the leaf the arraysize = 1 and we can finally return with the result array which is subsequentially
     * merged with the previous thread array after forking the threads
     */
    private void sort() {
        if (arraySize == 1) return; //base case (reached a leaf)
        int middleIndex = arraySize / 2;
        int[] leftArray = new int[middleIndex];
        int[] rightArray = new int[arraySize - middleIndex];

        System.arraycopy(array, 0, leftArray, 0, middleIndex); //copy left side of array
        System.arraycopy(array, middleIndex, rightArray, 0, arraySize - middleIndex); //copy right side of array

        MultiThreadMergeSort sortLeft = new MultiThreadMergeSort(leftArray, leftArray.length, leaf, false); //left side sort thread
        sortLeft.start();
        MultiThreadMergeSort sortRight = new MultiThreadMergeSort(rightArray, rightArray.length, leaf, true); //right side sort thread
        sortRight.start();

        //try joining threads before merging the two resulting arrays
        try {
            sortLeft.join();
            sortRight.join();
        } catch (Exception e) {
            DEBUGGER.log(Level.SEVERE, String.format("message: %s", e.getMessage())); //log for debugging purposes incase thread fails
        }

        merge(result, sortLeft.result, sortRight.result, middleIndex, arraySize - middleIndex);
    }

    /**
     * Merge two sorted arrays into a sorted larger array
     *
     * @param array      array to be sorted, ex: [1,2,4,3]
     * @param leftArray  left side sorted array, ex: [1,2]
     * @param rightArray right side sorted array, ex: [3,4]
     * @param leftIndex  left index of array, ex: 2
     * @param rightIndex right side index of array, ex: 2
     */
    private void merge(int[] array, int[] leftArray, int[] rightArray, int leftIndex, int rightIndex) {
        int i = 0, j = 0, k = 0;

        while (i < leftIndex && j < rightIndex) {
            if (leftArray[i] <= rightArray[j]) array[k++] = leftArray[i++];
            else array[k++] = rightArray[j++];
        }

        while (i < leftIndex) array[k++] = leftArray[i++];
        while (j < rightIndex) array[k++] = rightArray[j++];
    }
}
