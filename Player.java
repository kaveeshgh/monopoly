import java.util.ArrayList;

public class Player 
{
    private int position = 0;
    private int balance = 0;
    private boolean inJail = false;
    private int numGetOutJail = 0;
    private ArrayList<Property> properties = new ArrayList<Property>();
    
    private String icon;
    /*Constructor
    //Pre:  int p for player's position
            int b for the player's balance
            boolean ij for if the Player is in jail
            String i for the player's icon
     Post: none
    */
    public Player(int p, int b, boolean ij, String i)
    {
        position = p;
        balance = b;
        inJail = ij;
        icon = i;
    }

    //returns player's icon
    //Pre: none
    //Post: String of player's icon
    public String getIcon() {
        return icon;
    }
    //returns player's balance
    //Pre: none
    //Post: returns int of player's balance
    public int getBalance() {
        return balance;
    }
    //sets the player's position
    //Pre: int p for player's new position
    //Post: none
    public void setPlayerPosition(int p){
        position = p;
    }
    
    //adds a value to the player's position on the board
    //Pre; int p to be added to the player's position
    //Post; none
    public void addPlayerPosition(int p) {
        position += p%40;
    }
    
    //returns player's position
    //Pre: none
    //Post; returns int position
    public int getPlayerPosition(){
        return position;
    }
    //adds a property to the players Property array list
    //Pre: Property p to be added
    //Post: none
    public void addProperty(Property p)
    {
        properties.add(p);
        //balance -= p.getPrice();
    }
    
    
    //removed a property from the player's arrayList
    //Pre: property P to be removed
    //Post: none
    public void removeProperty(Property p)
    {
        properties.remove(p);
    }
    //changes the player's balance
    //Pre: int i  to change balance by
    //Post: none
    public void changeBalance(int i) {
        balance += i;
    }
    //returns number of get out of jail (GOOJ) cards player has
    //Pre: none
    //returns int for number of GOOJ cards
    public int getNumGetOutJailCard()
    {
        return numGetOutJail;
    }
    //adds number to the number of GOOJ cards player has
    //Pre: int i to change amount by
    //Post: none
    public void changeNumGetOutJailCard(int i)
    {
        numGetOutJail += i;
    }
    //puts the player into Jail
    //Pre: boolean j if the player is in Jail
    //Post; none
    public void inPrison(boolean j)
    {
        position = 10;
        inJail = j;
    }
    //returns if the player is inJail
    //Pre: none
    //Post: returns boolean is player is in jail
    public boolean inJail(){
        return inJail;
    }

    //returns arrayList of properties
    //Pre: none
    //Post; returns property arraylist
    public ArrayList getProperties () {
        return properties;
    }
    
    //sells all of a player's properties
    // Pre: none
    //Post: none, just sells properties
    public void sellProperties() {
        for(int i = 0; i < properties.size(); i++)
        {
            properties.get(i).sellProperty(this);
        }
    }
    
    //moves Player's position and displays animation of Player moving
    //Pre: board b for the player to be moved
    //Post: returns boolean if the player is put in Jail
    public boolean movePlayer(Board b)
    {
        // creates a dice object and rolls a random amount 
        Die die = new Die();
        die.roll();
        
        // create a passed go method to check if player has passed go
        boolean passedGo = false;
        
        // rolls dice and delays output
        b.delayOutput("\nRolling dice....", 1000, 500);
        System.out.println("Rolling dice....");
        b.delayOutput(die.toString(), 0, 2000);
        
        // checks if the player isnt in jail
        if(inJail == false)
        {
            // updates thier new position
            int newPosition = position + die.getTotal();
            
            // keeps clearing and redisplaying the board every time the player 
            // moves 1 tile closer to thier new position
            for(int j = position+1; j <= newPosition; j++)
            {
                // checks if thier position is greater than equal to 40, 
                // meaning that that thier position must be reset
                if(j >= 40)
                {
                    passedGo = true;
                    position = j-40;
                }
                    
                else{
                    position = j;
                }
                
                // outputs players current state and re-displays what they rolled
                System.out.println(b.toString(this));
                b.delayOutput(die.toString(), 0, 300);
            }
            
            // checks if they passed go   
            if(passedGo == true)
            {
                // outputs players current state
                b.delayOutput(Colour.RED + "\n--You passed Go! And collected $200--" + Colour.RESET, 0, 2000);
                
                // adds 200 to thier balance and sets passedGo to false as they already passed it
                balance += 200;
                passedGo = false;
                
                // outputs players current state and re-displays what they rolled
                System.out.println(b.toString(this));
                b.delayOutput(die.toString(), 0, 300);
            }
        }
        
        // if the player is in jail
        else
        {
            // since the die roll prints where you would move to, lets them know 
            // that nothing acctually happend since they are in jail
            b.delayOutput("\nbut you are in jail so nothing happens...", 0, 2000);
            
            // the case they can get out of jail, if they roll a double
            if(die.getDie1() == die.getDie2())
            {
                // lets them know they rolled a double and got out of jail
                b.delayOutput("\nYou rolled a double! You got out of Jail!", 0, 2000);
                inJail = false;
            }
            
            // the case they didnt roll a double, lets them know that they are still in jail
            else
            {
                b.delayOutput("\nYou didn't roll a double, you are still in Jail.", 0, 2000);
            }
        }
        
        return inJail;
    }
    
    //toString for player
    //Pre: none
    //Post: returns String to be outputted
    public String toString()
    {
        String output = "";
        
        // prints the icon and current balance of player
        output += (icon + (Colour.YELLOW_BACKGROUND + "\nCurrent Balance: $" + balance + Colour.RESET));
        
        // checks that there is at least 1 property adn prints all properties
        if(properties.size() != 0)
        {
            output += "\n\n|";
            for(int i = 0; i < properties.size(); i++){
                output += properties.get(i).toString1();
            }
            output+="\n|";
            for(int i = 0; i < properties.size(); i++){
                output += properties.get(i).toString2();
            }
            output+="\n|";
            for(int i = 0; i < properties.size(); i++){
                output += properties.get(i).toString3();
            }
        }
        
        return output;
    }
    
    
   
    
    
    
}