import java.util.Random;

public class Die 
{
    private int die1;
    private int die2;
    private int total;
    
    private final String[] dieRolls = {"\u2680", "\u2681", "\u2682", "\u2683", "\u2684", "\u2685"};
    
    //constructor
    //Pre: none
    //Post: none
    public Die () {
        die1 = 0;
        die2 = 0;
    }
    
    //rolls the dice, randomizing die1 and die2 and returning the total
    //Pre: none
    //Post; returns int of the die total
    public int roll () 
    {
        Random r = new Random();
        
        die1 = r.nextInt(6)+1;
        die2 = r.nextInt(6)+1;
        total = die1 + die2;
        
        return total;
    }
    //returns value of first die
    //Pre: none
    //Post: dice value
    public int getDie1(){
        return die1;
    }
    //returns value of second die
    //Pre: none
    //Post: dice value
    public int getDie2(){
        return die2;
    }
    //returns value of both dice together
    //Pre: none
    //Post: int dice total value
    public int getTotal() {
        return total;
    }
    //returns String of dice values
    //Pre: none
    //Post: String to be outputted
    public String toString () {
        
        String output = "";
        
        output += dieRolls[(die1-1)] + " " + dieRolls[(die2-1)] + "\n";
        output += "\nYou rolled a " + die1 + " and a " + die2;
        output += "\nYou moved " + total + " spaces forward!";
        
        return output;
    }
}