import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    /**
     * Driver for reading input file, creating output.txt file and testing the multithreaded mergesort
     *
     * @param args command line args
     * @throws IOException          if input.txt is not in classpath/does not exist, or if there is a problem creating output.txt
     * @throws InterruptedException if unexpected interruption occur during seperate running threads.
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        //Scan Input file, and copy integers from input.txt to integers array
        Scanner scan = new Scanner(new File("input.txt"));
        int[] integers = new int[8];
        int iterator = 0;
        while (scan.hasNextInt()) integers[iterator++] = scan.nextInt();

        //Create or Replace Outputfile if it does not already exist
        File outputFile = new File("output.txt");
        if (outputFile.exists()) outputFile.delete();
        outputFile.createNewFile();

        //Start thread number 1 to initalize the tree
        MultiThreadMergeSort multiThreadMergeSort = new MultiThreadMergeSort(integers, integers.length, "", true);
        multiThreadMergeSort.start();
        multiThreadMergeSort.join();

        //Testing the sort
        assert(integers[0] == 1509);
        assert(integers[1] == 3304);
        assert(integers[2] == 6367);
        assert(integers[3] == 7856);
        assert(integers[4] == 8221);
        assert(integers[5] == 14038);
        assert(integers[6] == 21362);
        assert(integers[7] == 26849);
    }

}
