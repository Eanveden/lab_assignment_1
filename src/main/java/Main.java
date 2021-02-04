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
        MergeSort.sort(integers, iterator);

        //Print Values after sorting
        System.out.println(String.format("Value Before Sort: %s ", Arrays.toString(integers)));
    }

}
