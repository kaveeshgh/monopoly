import java.util.Scanner;

public class Console 
{
    // gets a valid string from the user
    //Pre: String prompt to prompt the user
    //Post: returns a String
    public static String getString(String propmt)
    {
        String line = "";
        Scanner input = new Scanner(System.in);

        
        System.out.print(propmt);
        line = input.nextLine().trim();
        
        
        // checks if there is input
        while(line.isEmpty())
        {
            System.out.println(Colour.RED + "missing input . . . please re-enter: " + Colour.RESET);
            
            System.out.print(propmt);
            line = input.nextLine().trim();
        }
        
        return line;
    }


    //--------------------------------------------------------------------------
    
    
    // gets a valid int, ensures only numbers are taken as input
    //Pre: none
    //Post: returns an int from user
    public static int getInt()
    {
        int num = 0;
        Scanner input = new Scanner(System.in);
        
        
        while(!(input.hasNextInt()))
        {
            System.out.print(Colour.RED + "Not a valid interger...re-enter: " + Colour.RESET);
            input.nextLine();
        }
        
        
        num = input.nextInt();
        return num;
    }
    
    
    // ouputs a prompt and calls the getInt() method
    //Pre: String prompt to prompt the user
    //Post: returns an int from the user
    public static int getInt(String prompt)
    {
        int num = 0;
        
        System.out.print(prompt);
        num = getInt();
        
        return num;
    }
    
    
    // get a valid integer that is equal to or greater than a min
    //Pre: String prompt for the user, and a minimum value for the integer
    //Post: returns integer from user
    public static int getInt(String prompt, int min)
    {
        int num = getInt(prompt);
        
        while(num < min)
        {
            System.out.print(Colour.RED + "Number must be greater than or equal to " + min + Colour.RESET);
            num = getInt(prompt);
        }
        
        return num;
    }
    
    
    // get a valid integer between low and high range
    //Pre: String prompt for the user, and a minimum and maximum value for the integer
    //Post: returns integer from user
    public static int getInt(String prompt, int min, int max)
    {
        int num = getInt(prompt);
        
        while(num > max || num < min)
        {
            System.out.print(Colour.RED + "Number must be between " + min + " to " + max + "\n" + Colour.RESET);
            num = getInt(prompt);
        }
        
        return num;
    }
}