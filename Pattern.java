public class Pattern {
    public static void main(String[] args) {
        int n = 5;  // size of the square (rows and columns)

        // outer loop -> rows
        for (int i = 1; i <= n; i++) {

            // inner loop -> columns
            for (int j = 1; j <= n; j++) {
                System.out.print(i);   // print the row number
            }

            System.out.println();      // move to next line
        }
    }
}
