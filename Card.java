public class Card 
{
    private String cardType;
    private String[] text;
    private String colour;
    private int money;
    private int moveTo;
    
    /** Constructor
     *  Precondition: an integer for the card type
     *                four strings representing each line of the card
     *                an integer for the amount of money that is given/taken
     *                an integer for the position that the player will be moved to
     *  Postcondition: N/A
     */
    public Card(int type, String t1, String t2, String t3, String t4, int dollar, int position)
    {
        if (type == 1)
        {
            cardType = "CHANCE";
            colour = Colour.ORANGE_BACKGROUND;
        }
        else 
        {
            cardType = "CHEST ";
            colour = Colour.blue_BACKGROUND;
        }
        
        text = new String[4];
        text[0] = t1;
        text[1] = t2;
        text[2] = t3;
        text[3] = t4;
        
        money = dollar;
        moveTo = position;
    }
    
    /** The action when a player lands on a chance or chest tile
     *  Pre: A player object
     *  Post: N/A
     */
    public void pickUpCard(Player p)
    {
        if(moveTo < 41)
        {
            if (p.getPlayerPosition() > moveTo && moveTo != 10)
            {
                p.changeBalance(200);
            }
            p.setPlayerPosition(moveTo);
            if (moveTo == 10)
            {
                p.inPrison(true);
            }
        }
        else
        {
             p.changeBalance(money);
             
             if (money == 0)
             {
                 p.changeNumGetOutJailCard(1);
             }
        }
        
    } 
    
    /** toString
     *  Pre: N/A
     *  Post: String
     */
    public String toString()
    {
        String output = "\n\n" + colour + "------------------------" + Colour.RESET + "\n";
        output += colour + "|        " + cardType + "        |" + Colour.RESET + "\n";
        
        for (int i = 0; i < 4; i++)
        {
            output += colour + "|" + text[i] + "|"  + Colour.RESET + "\n";
        }
        output += colour + "------------------------" + Colour.RESET;
        
        return output;
    }
    
}