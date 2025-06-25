1.Number Guessing Game:

import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        String playAgain;

        do {
            int lowerBound = 1;
            int upperBound = 100;
            int maxAttempts = 5;
            int targetNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;

            System.out.println("\n🎮 Welcome to the Number Guessing Game!");
            System.out.println("I'm thinking of a number between " + lowerBound + " and " + upperBound + ".");
            System.out.println("You have " + maxAttempts + " attempts to guess it correctly.");

            boolean isGuessed = false;

            for (int attempt = 1; attempt <= maxAttempts; attempt++) {
                System.out.print("\nAttempt " + attempt + ": Enter your guess: ");
                int userGuess = scanner.nextInt();

                if (userGuess > targetNumber) {
                    System.out.println("Too high!");
                } else if (userGuess < targetNumber) {
                    System.out.println("Too low!");
                } else {
                    System.out.println("\n🎉 Congratulations! You guessed the number " + targetNumber + " correctly in " + attempt + " attempt(s)!");
                    isGuessed = true;
                    break;
                }
            }

            if (!isGuessed) {
                System.out.println("\n😢 Game Over! You've used all " + maxAttempts + " attempts.");
                System.out.println("The correct number was: " + targetNumber);
            }

            System.out.print("\n🔁 Do you want to play again? (Y/N): ");
            playAgain = scanner.next();

        } while (playAgain.equalsIgnoreCase("Y"));

        System.out.println("\n👋 Thanks for playing! Goodbye!");
        scanner.close();
    }
}



