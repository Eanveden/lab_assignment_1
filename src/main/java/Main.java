import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("input.txt"));
        int[] integers = new int[8];
        int i = 0;
        while (scan.hasNextInt())
            integers[i++] = scan.nextInt();
    }
}
