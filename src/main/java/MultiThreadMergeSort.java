import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.logging.Level;

public class MultiThreadMergeSort extends Thread {
    private static final Logger DEBUGGER = Logger.getLogger("DEBUG");
    private int[] array;
    private int[] result;
    private int arraySize;
    private StringBuilder leaf;

    MultiThreadMergeSort(int[] array, int arraySize, String leaf, boolean isRight) {
        this.array = array;
        this.result = array;
        this.arraySize = arraySize;
        this.leaf = new StringBuilder(String.format("%s%s", leaf, isRight ? "1" : "0"));
    }

    /**
     * Write to output.txt file
     *
     * @param outputLine text to write
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

    @Override
    public void run() {
        writeToOutputFile(String.format("Thread %s started \n", leaf.toString()));
        sort();
        writeToOutputFile(String.format("Thread %s finished: %s \n", leaf.toString(), Arrays.toString(result)));
    }

    /**
     * Sorts integer array recursively using merge sort. Each split will initialize a new thread
     * -> not efficient, but fun :)
     */
    private void sort() {
        if (arraySize == 1) return;
        int middleIndex = arraySize / 2;
        int[] leftArray = new int[middleIndex];
        int[] rightArray = new int[arraySize - middleIndex];

        System.arraycopy(array, 0, leftArray, 0, middleIndex);
        System.arraycopy(array, middleIndex, rightArray, 0, arraySize - middleIndex);

        MultiThreadMergeSort sortLeft = new MultiThreadMergeSort(leftArray, leftArray.length, leaf.toString(), false);
        sortLeft.start();
        MultiThreadMergeSort sortRight = new MultiThreadMergeSort(rightArray, rightArray.length, leaf.toString(), true);
        sortRight.start();

        try {
            sortLeft.join();
            sortRight.join();
        } catch (Exception e) {
            DEBUGGER.log(Level.SEVERE, String.format("message: %s", e.getMessage()));
        }

        merge(result, sortLeft.result, sortRight.result, middleIndex, arraySize - middleIndex);
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
