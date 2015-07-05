import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by Neal on 2/9/2015.
 */

public class GameOfLifeTester {
    public static void main(String[] args) throws InterruptedException, IOException {
        String fileName = "grid.txt";

        Scanner columnsAndRowsCount = new Scanner(Paths.get(fileName));
        int columns = columnsAndRowsCount.nextLine().length();

        Scanner fileInput = new Scanner(Paths.get(fileName));
        int rows = countLines(columnsAndRowsCount);

        String cellValue = "";
        fileInput.useDelimiter("");

        int[][] matrix = new int[rows][columns];
        for (int i = 0; i < rows; i++) { //set input file to grid
            for (int j = 0; j < columns; j++) {
                if (fileInput.hasNextLine()) {
                    cellValue = fileInput.next();
                }
                if (cellValue.equals("#")) {
                    matrix[i][j] = 1;
                }
                else if (cellValue.equals("0")) {
                    matrix[i][j] = 0;
                }
            }
            if (fileInput.hasNextLine()) {
                fileInput.nextLine();
            }
        }

        int current = 0;

        while (true) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {

                    current = matrix[i][j];

                    if (current == 1) {
                        System.out.print("\u2588");
                    } else {
                        System.out.print(" ");
                    }

                    int[] neighbors = new int[8]; //ORDER: top, bottom, left, right, topLeft, topRight, bottomLeft, bottomRight

                    int x = 0;

                    while (true) {  //assign the value of the current cell's neighbors to neighbors array
                        try {
                            switch (x) {
                                case 0: neighbors[x++] = matrix[i - 1][j];
                                case 1: neighbors[x++] = matrix[i + 1][j];
                                case 2: neighbors[x++] = matrix[i][j - 1];
                                case 3: neighbors[x++] = matrix[i][j + 1];
                                case 4: neighbors[x++] = matrix[i - 1][j - 1];
                                case 5: neighbors[x++] = matrix[i - 1][j + 1];
                                case 6: neighbors[x++] = matrix[i + 1][j - 1];
                                case 7: neighbors[x++] = matrix[i + 1][j + 1];
                            }
                            break;
                        }
                        catch (ArrayIndexOutOfBoundsException e) { //set neighbor to zero if outside of grid
                            neighbors[x - 1] = 0;
                        }
                    }

                    int neighborAmount = 0;
                    for (int y = 0; y < 8; y++) { //add up the amount of neighbors
                        if (neighbors[y] == 1 || neighbors[y] == -1) {
                            neighborAmount++;
                        }
                    }

                    //mark cells for creation or deletion
                    if (neighborAmount == 3 && current == 0) {       // cell created
                        matrix[i][j] = 2;
                    } else if (neighborAmount < 2 && current == 1) { //underpopulated - dies
                        matrix[i][j] = -1;
                    } else if (neighborAmount > 3 && current == 1) { //overpopulated  - dies
                        matrix[i][j] = -1;
                    }
                }
                System.out.println();
            }

            for (int i = 0; i < rows; i++) { //update cells that have been marked for deletion or creation
                for (int j = 0; j < columns; j++) {
                    current = matrix[i][j];
                    if (current == -1) {
                        matrix[i][j] = 0;
                    }
                    else if (current == 2) {
                        matrix[i][j] = 1;
                    }
                }
            }

            System.out.println();
            Thread.sleep(250);
        }
    }

    public static int countLines(Scanner fileInput) {
        int lines = 1;
        while (fileInput.hasNextLine()) {
            fileInput.nextLine();
            lines++;
        }
        return lines;
    }

}