
import java.util.*;
import java.io.*;

public class MyProgram
{
    public static void main(String[] args) throws IOException
    {
        // creates string equal to y
        String play = "y";
        
        // while play is equal to yes
        while(play.equalsIgnoreCase("y"))
        {
            // outputs the instructions to play monopoly
            System.out.print("\033[H\033[2J"); // Clear screen
            System.out.println("\u2605 " + " $ " + " \u2660 " + " \u2661 " + " \u265c ");
            System.out.println("How to play: ");
            System.out.println("  Each player starts with $1500\n  Each turn, roll the dice and");
            System.out.println("\tBuy the property\n\tSell the property (if you own it)\n\tPay rent\n\tBuild a house/hotel (must own all properties of that colour)\n\tFollow directions of the card (chance/chest).");
            System.out.println("  The winner is the person with the most money or the last person to be bankrupt");
            System.out.println("-------------------------------------------------------------------------------\n\n");
        
        
            // creates a new board while asking the number of players playing 
            Board board = new Board(Console.getInt("Enter the number of players: ", 2, 5));
            
            // calls the play method in the board
            board.play();
            
            // asks user if they want to play again
            play = Console.getString("\n\n\nWould you like to play Monopoly again?(y/n): ");
        }
    }
}