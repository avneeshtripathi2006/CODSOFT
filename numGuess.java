import java.util.Scanner;
class numGuess{
    //in this program there are 5 levels , in  each level the number of chances decreases. level 1 has 10 chance , level 2 has 8 , level 3 has 6 , level 4 has 4 , level 5 has 2 chances.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int level = 1;
        int maxLevel = 5;
        int[] chances = {10, 8, 6, 4, 2};
        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("You have 5 levels to complete. Each level has a different number of chances.");
        
        while (level <= maxLevel) {
            int numberToGuess = (int)(Math.random() * 100) + 1;
            int attempts = chances[level - 1];
            boolean hasGuessedCorrectly = false;
            
            System.out.println("\nLevel " + level + ": You have " + attempts + " chances to guess the number between 1 and 100.");
            
            while (attempts > 0) {
                System.out.print("Enter your guess: ");
                int userGuess = sc.nextInt();
                
                if (userGuess < 1 || userGuess > 100) {
                    System.out.println("Please guess a number between 1 and 100.");
                    continue;
                }
                
                if (userGuess == numberToGuess) {
                    System.out.println("Congratulations! You've guessed the correct number.");
                    hasGuessedCorrectly = true;
                    break;
                } else if (userGuess < numberToGuess) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }
                
                attempts--;
                System.out.println("You have " + attempts + " chances left.");
            }
            
            if (!hasGuessedCorrectly) {
                System.out.println("Sorry, you've run out of chances. The correct number was: " + numberToGuess);
                System.out.println("Game Over!");
                break;
            }
            
            if (level == maxLevel) {
                System.out.println("Congratulations! You've completed all levels!");
            } else {
                System.out.println("Great job! Moving on to the next level.");
            }
            
            level++;
        }
        
        sc.close();
    }
}