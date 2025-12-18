import java.util.Random;    // lets us create random numbers
import java.util.Scanner;   // lets us read input from keyboard

public class Guessgame {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);  // object to read what user types
        Random rand = new Random();           // object to generate random number

        int secret = rand.nextInt(100) + 1;   // secret number 1–100
        int attempts = 0;                     // how many guesses used
        boolean guessed = false;              // have we guessed correctly yet?

        System.out.println("I chose a number between 1 and 100.");

        while (!guessed) {                    // repeat until guessed == true
            System.out.print("Enter your guess: ");
            int guess = sc.nextInt();         // read user’s number
            attempts++;                       // attempts = attempts + 1

            if (guess < secret) {             // condition 1
                System.out.println("Too low!");
            } else if (guess > secret) {      // condition 2
                System.out.println("Too high!");
            } else {                          // else means: both above are false
                System.out.println("Correct! Attempts: " + attempts);
                guessed = true;               // this will stop the while loop
            }
        }

        sc.close();
    }
}
