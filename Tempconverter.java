import java.util.Scanner;

public class Tempconverter {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== TEMPERATURE CONVERTER ===");
            System.out.println("1. Celsius to Fahrenheit");
            System.out.println("2. Fahrenheit to Celsius");
            System.out.println("3. Exit");
            System.out.print("Choose option (1/2/3): ");

            int choice = sc.nextInt();

            if (choice == 3) {
                System.out.println("Exiting...");
                break;
            }

            if (choice != 1 && choice != 2) {
                System.out.println("Invalid option. Try again.");
                continue; // go back to menu
            }

            System.out.print("Enter temperature value: ");
            double temp = sc.nextDouble();   // input temperature

            double result;

            if (choice == 1) { // C to F
                result = temp * 9 / 5 + 32;
                System.out.println(temp + " °C = " + result + " °F");
            } else {           // F to C
                result = (temp - 32) * 5 / 9;
                System.out.println(temp + " °F = " + result + " °C");
            }
        }

        sc.close();
    }
}
