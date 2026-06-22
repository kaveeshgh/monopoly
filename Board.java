import java.util.Scanner;
import java.io.*;
import java.util.Date;
import java.util.ArrayList;

public class Board 
{
    public Square[] tiles;
    public Queue<Card> chanceCards;
    public Queue<Card> chestCards;
    private ArrayList<Player> players;
    
    //Constructor - reads in all information from files to create the board
    //creates players, squares, cards, decks
    // Pre: int for numPlayer
    // Post: none
    public Board(int numPlayers) throws IOException
    {
        // creates new file and scanner to read from that file
        File f = new File("Tiles.txt");
        Scanner read = new Scanner(f);
        
        File cardFile = new File ("CardInfo.txt");
        Scanner s = new Scanner(cardFile);
        
        Die d = new Die();
        
        read.useDelimiter(";|\n");
        s.useDelimiter(";|\n");
        
        tiles = new Square[40];
        
        String type = "";
        
        // reads from "Tile.txt"
        // adds properties, tiles, utility, railroads or squares to the tiles array
        for (int i = 0; i < 40; i ++) {
            
            type = read.next();
            
            if (type.equals("Square")) {
                
                tiles[i] = new Square(read.next(), i);
                tiles[i].setBalance(read.nextInt());
                
            }
            else if (type.equals("Property")) {
                
                int[] rents = new int[6];
                
                for (int j = 0; j < 6; j ++) {
                    rents[j] = read.nextInt();
                }
                
                tiles[i] = new Property(read.next(), i, read.nextInt(), read.nextInt(), rents, read.nextInt());
                
            }
            else if (type.equals("CardTile")){
                tiles[i] = new CardTile(read.next(), i);
            }
            else if (type.equals("Railroad")) {
                
                int[] railPrices = {25, 50, 100, 200};
                
                tiles[i] = new Railroad(read.next(), i, 200, 100, railPrices, 9);
                
            }
            else if (type.equals("Utility")) {
                
                int[] utilPrices = {4, 10};
                
                tiles[i] = new Utility(read.next(), i, 150, 75, utilPrices, 10, d);
                
            }
            
        }

        
        chanceCards = new Queue<Card>(10);
        chestCards = new Queue<Card>(10);
        
        Deck chanceDeck = new Deck(s);
        chanceDeck.shuffle();
        
        Deck chestDeck = new Deck(s);
        chestDeck.shuffle();
        
        // reads from "CardInfo.txt"
        // adds cards to the chanceCards and chestCards queues
        for (int i = 0; i < 10; i++)
        {
            chanceCards.enqueue(chanceDeck.getCard(i));
        }
        for (int i = 0; i < 10; i++)
        {
            chestCards.enqueue(chestDeck.getCard(i));
        }
        
        
        players = new ArrayList<Player>(numPlayers);;
        String[] icons = {"\u2605", "$", "\u2660", "\u2661", "\u265c"};
        //Note: changed the icons to simpler symbols, the emojis we were using before had lengths greater than 1
        //      and it was messing up the spacing when printing the board
        for (int i = 0; i < numPlayers; i++)
        {
            Player p = new Player(0, 1500, false, icons[i]);
            players.add(p);
        }
        
    }
    
    //returns String of what Player's icons' are on a Square
    //Pre: int position for Square position to reference
    //Post: String for player's on square
    public String players (int position) 
    {
        
        String s = "";
        
        for (int i = 0; i < players.size(); i++) {
            
            if (players.get(i).getPlayerPosition()==position)
             s+=players.get(i).getIcon();
            
        }
        
        return s;
    }
    
    /** toString 
     *  Pre: the player
     *  Post: String
     */
    public String toString(Player p)
    {
        System.out.print("\033[H\033[2J"); // Clear screen
        String output = "";
    
        output += "\nPlayer " + p.getIcon() + "'s turn:\n ";
        for (int l = 0; l < (76); l++) output+="-";
        
        //top line of squares
        //-----------------------------------------------------
        output+="\n|";
        for(int i = 10; i >= 0; i--){
        output += tiles[30-i].toString1();
        }
        output+="\n|";
        for(int i = 10; i >= 0; i--){
            output += tiles[30-i].toString2();
        }
        output+="\n|";
        for(int i = 10; i >= 0; i--){
            output += tiles[30-i].toString3(this);

        }
        //-----------------------------------------------------
        
        //squares in between
        //-----------------------------------------------------
        for (int i = 8; i >= 0; i--)
        {
            output+="\n|";
            output+=tiles[11+i].toString1();
            
            for (int l = 0; l < (62); l++) output+=" ";
            
            output+="|" + tiles[39-i].toString1();
            
            output+="\n|";
            output+=tiles[11+i].toString2();
            
            for (int l = 0; l < (62); l++) output+=" ";
            
            output+="|" + tiles[39-i].toString2();
            
            output+="\n|";
            output+=tiles[11+i].toString3(this);
            
            for (int l = 0; l < (62); l++) output+=" ";
            
            output+="|" + tiles[39-i].toString3(this);
        }
        //-----------------------------------------------------
        
        //bottom line of squares
        //-----------------------------------------------------
        output+="\n|";
        for(int i = 10; i >= 0; i--){
        output += tiles[i].toString1();
        }
        output+="\n|";
        for(int i = 10; i >= 0; i--){
            output += tiles[i].toString2();
        }
        output+="\n|";
        for(int i = 10; i >= 0; i--){
            output += tiles[i].toString3(this);
        }
        output+="\n ";
        //-----------------------------------------------------

        for (int l = 0; l < (76); l++) output+="-";
        output += "\n\n" + p.toString() + "\n";
        
        return output;
    }
    
     /** Returns a square
     *  Pre: int for the index
     *  Post: Returns a square
     */
    public Square getTile(int i)
    {
        return tiles[i];
    }
    
     /**delays the output
     *  Pre: a string to display, time to deplay before, time to delay after
     *  Post: none
     */
    public void delayOutput(String k, int time1, int time2)
    {
        try
        {
            Thread.sleep(time1);
            System.out.println(k);
            Thread.sleep(time2);
        }
        
        catch(InterruptedException ex){}
    }

    
    //removes a player from the game
    //Pre: int i of which player (position in Player arraylist)
    //Post: boolean if there are still enough players to play the game
    public boolean quit(int i)
    {
        boolean play = true;
        
        // prints player's final balances
        for(int k = 0; k < players.size(); k++){
        System.out.println("\n" + players.get(k).toString());}
        
        // lets the players know which player quit
        delayOutput("\n--Player " + players.get(i).getIcon() + " quit the game! Their properties have been sold to the bank!--", 1000, 2000);
        
        // sells thier properties and removes them from the player array list
        players.get(i).sellProperties();
        players.remove(i);
        
        // checks in there is at least 2 players and if not sets play to false to end the game
        if(players.size() <= 1)
        {
            play = false;
        }
        
        return play;
    }
    
    public boolean cannotBuyHouses (Property manageTile) {
        
        boolean cannotBuy = false;
        
            ArrayList<Property> set = new ArrayList<Property>();
            
            for (int a = 0; a < 40; a ++) {
                if (tiles[a].getSet() == manageTile.getSet() && tiles[a]!=manageTile) {
                    set.add((Property)tiles[a]);
                }
            }
            for (int i = 0; i < set.size(); i ++) {
            
            if (set.get(i).getOwner()!=manageTile.getOwner() ) {
                System.out.println("--You do not own all properties of this set--\n");
                cannotBuy = true;
                i = set.size();
            }
            else  if (manageTile.getHouses()>set.get(i).getHouses()) {
                System.out.println("--Houses must be bought evenly between properties--\n");
                cannotBuy = true;
                i = set.size();
            }
            }
        
        return cannotBuy;
    }
    
    
    //loops through gameplay, going through player's turns, dice rolls, and actions
    //Pre: none
    //Post: none
    public void play()
    {
        // creates boolean variables play and invalid in order to repeat actions
        boolean play = true;
        boolean invalid = true;
        // creats 2 choice variables for 
        int choice = 0;
        int choice2 = 0;
        
        
        
        // keeps the game going unless all players have quit o r
        while(play == true)
        {
            //----------------------------------------------------------------------------------------------------------------
            /* runs the gameplay for every single player and checks if one of 
               the conditions of ending the game have been met before running 
               the gameplay for the next player */
            for(int i = 0; i < players.size() && play == true; i++)
            {
                // prints the board for the player with thier balance, and the properties they own
                System.out.println(toString(players.get(i)));
                
                
                //----------------------------------------------------------------------------------------------------------------
                /* rolls dice and moves the player that random amount of the board and 
                   .movePlayer(Board) returns a boolean to see whether the player is 
                   in jail or not since the options a player has is very different when 
                   in jail and when not in jail
                   the*/
                if(players.get(i).movePlayer(this) == false)
                {
                    // gets the position of the player on the board
                    int playerPosition = players.get(i).getPlayerPosition();
                    
                    // lets the user know the name of the tile they moved to
                    System.out.println("\nPlayer " + players.get(i).getIcon() 
                    + " moved to " + tiles[playerPosition].getName());
                    
                    
                    System.out.println("\n");
                    for (int l = 0; l < (30); l++)
                        System.out.print("-");
                    
                    //----------------------------------------------------------------------------------------------------------------
                    
                    
                    // while the player has no money
                    while(players.get(i).getBalance() < 0 && play == true) 
                    {
                        System.out.println("Balance below zero, you must sell assets!\n");
                        // gets the number of properties they own(assets)
                        int numProps = players.get(i).getProperties().size();
                        
                        // checks if they have any properties
                        if (numProps == 0) {
                            System.out.println("\nNo assets to sell! Player is bankrupt...GAME OVER");
                            // removes player from game and checks if there are enough players to keep going
                            play = quit(i);
                        }
                        
                        // if they do have assets
                        else 
                        {
                            // asks user what property to manage
                            int prop = Console.getInt("Which property would you like to manage?", 1, numProps);
                            Property manageTile = (Property)(players.get(i).getProperties().get((prop-1)));
                            
                            // displays and gets thier choice
                            System.out.println("\n\n1:Mortgage Property\n2: Sell House");
                            choice = Console.getInt("\nWhat would you like to do? ", 1, 4);
                                
                                
                            switch(choice)
                            {
                                case 1: // mortgages property and lets the user know
                                    delayOutput("\n--You mortgaged " + manageTile.getName() + "!--", 0, 2000);
                                    manageTile.mortgage();
                                    break;
                                case 2: // while checking if there are houses or if it is and instance of a 
                                        // railroad or utiity where houses aren't involved
                                    if((manageTile instanceof Railroad || manageTile instanceof Utility) || manageTile.getHouses() != 0) {
                                                        System.out.println("--Houses cannot be sold on this property--\n");
                                    }
                                    
                                    // sells house and lets the user know 
                                    else {
                                        delayOutput("\n--You sold a house on " + manageTile.getName() + "!--", 0, 2000);
                                        manageTile.removeHouse();
                                    }
                                    break;  
                            }
                        }  
                    }
                    
                    
                    //----------------------------------------------------------------------------------------------------------------
                    
                    
                    // checks if the tile the player is on is a property
                    if(tiles[playerPosition] instanceof Property)
                    {
                        // explicitly downcasts the square object into a property so that it's methods can be accessed
                        Property currentTile = (Property)tiles[playerPosition];
                        
                        // checks if the tile is owned by someone else so that rent can be paid
                        if(currentTile.isOwned() && (currentTile.getOwner() != players.get(i))){
                            currentTile.payRent(players.get(i), this);
                        }
                        
                        else
                        {
                            // loops while invalid so that the input can be error-proofed 
                            invalid = true;
                            while(invalid == true)
                            {
                                // asks user what they want to do
                                System.out.println("\n1: Buy Property\n2: Management Options\n3: End turn\n4: Quit");
                                choice = Console.getInt("\nWhat would you like to do? ", 1, 4);
                                
                                //----------------------------------------------------------------------------------------------------------------
                                
                                
                                switch(choice)
                                {
                                    // buy property
                                    case 1:
                                        // checks that the player has enough money and 
                                        // that there is no owner to prevent the player from buying a property they already own
                                        if(players.get(i).getBalance() >= currentTile.getPrice() && currentTile.getOwner() == null)
                                        {
                                            // buys property
                                            currentTile.buyProperty(players.get(i));
                                            // re-displays the updated board and player 
                                            delayOutput("\n--You bought " + currentTile.getName() + "!--", 0, 2000);
                                            System.out.print(toString(players.get(i)));
                                        }
                                        
                                        else
                                        {
                                            // outputs the correct error message depending on the reason
                                            if(currentTile.getOwner() == players.get(i)){
                                                System.out.println("\n--You already own this property--");}
                                                
                                            else{
                                                System.out.println("\n--You do not have enough money to buy this property--");}
                                        }
                                        break;
                                    
                                    
                                    // management options
                                    case 2:
                                        // gets the number of properties they own(assets)
                                        int numProps = players.get(i).getProperties().size();
                                        
                                        // checks if they have any properties
                                        if (numProps == 0) {
                                            System.out.println("\nNo properties to manage!");
                                        }
                                        
                                        // if they do have properties
                                        else 
                                        {
                                            //----------------------------------------------------------------------------------------------------------------
                                            
                                            
                                            // asks user what property to manage
                                            int prop = Console.getInt("Which property would you like to manage? ", 1, numProps);
                                            Property manageTile = (Property)(players.get(i).getProperties().get((prop-1)));
                                            
                                            // displays and gets thier choice
                                            System.out.println("\n\n1:Mortgage Property\n2: Buy House\n3: Sell House\n4: Return to previous menu");
                                            choice2 = Console.getInt("\nWhat would you like to do? ", 1, 4);
                                            
                                            
                                            switch(choice2)
                                            {
                                                case 1: // mortgages property and lets the user know
                                                    delayOutput("\n--You mortgaged " + manageTile.getName() + "!--", 0, 2000);
                                                    manageTile.mortgage();
                                                    break;
                                                    
                                                    
                                                case 2: // checks if it is a railroad or utiity where houses aren't involved
                                                   
                                                    
                                                    if(manageTile instanceof Railroad || manageTile instanceof Utility) {
                                                        System.out.println("--Houses cannot be bought on this property--\n");
                                                    }
                                                    //checks if player owns all properties or is buying houses unevenly
                                                    else if (cannotBuyHouses(manageTile));
                                                    // buy houses and lets the user know 
                                                    else if (manageTile.getHouses() > 4) {
                                                        System.out.println("--You cannot buy any more houses on this property--\n");
                                                    }
                                                    else
                                                    {
                                                        delayOutput("\n--You are buying a house on " + manageTile.getName() + "!--", 0, 2000);
                                                        manageTile.buyHouse();
                                                    }
                                                    break;
                                                    
                                                    
                                                case 3: // while checking if there are houses or if it is and instance of a 
                                                        // railroad or utiity where houses aren't involved
                                                    if((manageTile instanceof Railroad || manageTile instanceof Utility) || manageTile.getHouses() != 0) {
                                                        System.out.println("--Houses cannot be sold on this property--\n");
                                                    }
                                                    
                                                    // sells house and lets the user know adn re-displays board and player's current state
                                                    else 
                                                    {
                                                        delayOutput("\n--You sold a house on " + manageTile.getName() + "!--", 0, 2000);
                                                        manageTile.removeHouse();
                                                        System.out.print(toString(players.get(i)));
                                                    }
                                                    break;  
                                                    
                                                    
                                                case 4: 
                                                    break;
                                            }
                                            
                                            
                                            //----------------------------------------------------------------------------------------------------------------
                                        }
                                        break;
                                        
                                
                                    // skip turn
                                    case 3: 
                                        // leaves loop as the players turn is over
                                        invalid = false;
                                        break;
                                    
                                    
                                    // quit 
                                    case 4: 
                                        // removes player, sells thier properties
                                        play = quit(i);
                                        // leaves loop as the players turn is over
                                        invalid = false;
                                        break;
                                }
                                
                                
                                //----------------------------------------------------------------------------------------------------------------
                            }
                        }
                    }
                
                
                
                    // if the tile is a card tile
                    else if(tiles[playerPosition] instanceof CardTile)
                    {
                        // explicitly downcasts the square object into a card tile so that it's methods can be accessed
                        CardTile currentTile = (CardTile)tiles[playerPosition];
                        
                        // checks if the tile is a chance tile 
                        if(currentTile.getName().equalsIgnoreCase("Chance"))
                        {
                            // removes the first card in the queue and displays it
                            Card c = chanceCards.dequeue();
                            System.out.println(c.toString());
                            delayOutput("", 0, 3000); 
                            
                            // depending on the card, will follow the cards action
                            // ex. changes player balance, moves a player, etc.
                            c.pickUpCard(players.get(i));
                            chanceCards.enqueue(c);
                        }
                        
                        // if not a chance tile then it must be a chest tile
                        else
                        {
                            Card c = chestCards.dequeue();
                            System.out.println(c.toString());
                            delayOutput("", 0, 3000);
                            c.pickUpCard(players.get(i));
                            chestCards.enqueue(c);
                        }
                    }
                    
                    
                    
                    // if its not a property or a card tile then its just a square tile with many functions
                    else
                    {
                        // creates a new tile to better keep track of the square
                        Square currentTile = tiles[playerPosition];
                        
                        // if the square is a tax tile 
                        if(currentTile.getName().equalsIgnoreCase(" Tax  "))
                        {
                            delayOutput("\nYou landed on a tax tile, you are getting taxed for $200", 0, 2000); 
                            players.get(i).changeBalance(-200);
                        }
                        
                        // if its the go to jail tile
                        else if(currentTile.getName().equalsIgnoreCase("G2Jail"))
                        {
                            /* call method to set player's variable inJail to true
                               so that when its the player's turn again, they will 
                               be faced with different options now that they are in jail*/
                            players.get(i).inPrison(true);
                        }
                        
                        // else its either the free parking, jail, or go tile where nothing happens to change player's state
                        else{
                            // outputs the tile the player is on
                            delayOutput("\nYou are visiting " + currentTile.getName() + "!", 0, 2000);    
                        }
                    }
                    
                    System.out.println("\n");
                    for (int l = 0; l < (30); l++)
                    System.out.print("-");
                }
                
                //----------------------------------------------------------------------------------------------------------------
                
                // the case where the player is in jail
                else
                {
                    // loops while invalid so that the input can be error-proofed 
                    invalid = true;
                    while(invalid == true)
                    {
                        // outputs the player's current state and thier options and asks for thier choice
                        System.out.println(Colour.RED + "\n-----YOU ARE IN JAIL-----" + Colour.RESET);
                        System.out.println("\n1: Use get out of Jail Free Card\n2: Pay $50 to get out\n3: Skip Turn\n4: Quit");
                        choice = Console.getInt("\nWhat would you like to do? ", 1, 4);
                        
                        switch(choice)
                        {
                            // is
                            case 1:
                                // checks if player has a get out of jail free card
                                if(players.get(i).getNumGetOutJailCard() != 0)
                                {
                                    // removes the used card
                                    players.get(i).changeNumGetOutJailCard(-1);
                                    // sets player's state to not in prison and outputs thier current state
                                    players.get(i).inPrison(false);
                                    delayOutput(Colour.RED + "\nYou got Out!!!!!!!" + Colour.RESET, 0, 2000);
                                    // leaves loop as the players turn is over
                                    invalid = false;
                                }
                                
                                // outputs that they dont have a get out of jail free card
                                else{
                                    System.out.println("\n--You do not have a Get out of Jail Free Card--");}
                                break;
                            
                            
                            case 2: 
                                // checks if player has enough money to pay out
                                if(players.get(i).getBalance() >= 50)
                                {
                                    // deducts player's balance since they payed to get out
                                    players.get(i).changeBalance(-50);
                                    // sets player's state to not in prison and outputs thier current state
                                    players.get(i).inPrison(false);
                                    delayOutput(Colour.RED + "\nYou Broke Out!!!!!!!" + Colour.RESET, 0, 2000);
                                    // leaves loop as the players turn is over
                                    invalid = false;
                                }
                                
                                // outputs that they dont have enough money to buy themselves out
                                else{
                                    System.out.println("\n--You do not have enough money to buy this property--");}
                                break;
                                
                                
                            // skip turn
                            case 3: 
                                // leaves loop as the players turn is over
                                invalid = false;
                                break;
                            
                            
                            // quit 
                            case 4: 
                                // removes player, sells thier properties
                                play = quit(i);
                                // leaves loop as the players turn is over
                                invalid = false;
                                break;
                        }
                    }
                }
                
                // outputs the players updated state as well as the board's before turn ends 
                delayOutput(toString(players.get(i)), 0, 2000);
            }
            //----------------------------------------------------------------------------------------------------------------
        }
    }
    
}