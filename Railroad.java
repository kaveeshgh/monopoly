public class Railroad extends Property
{
   /** Constructor
     *  Pre: String for the name of the tile
     *       An integer for the position of the tile
     *       An integer for the price of the railroad
     *       An integer for the mortgage 
     *       An integer array for rent prices
     *       An integer for the colour set it is part of
     *  Post: N/A
     */
    public Railroad(String n, int pos, int p, int m, int[] r, int s) 
    {
        super (n, pos, p, m, r, s);
    }
    
    /** sends a player to a railroad
     *  Pre: A board object
     *       A player object
     *  Post: N/A
     */
    public void travelToRail(Board b, Player p)
    {
        int pos = position;
        int nextRail = 0;
    
        for (int i = 0; i < 40; i ++) 
        {
            if(b.getTile(pos) instanceof Railroad)
            {
                nextRail = pos;
            }
            
            pos++;
            pos = pos%40;
        }
        
        p.setPlayerPosition(pos);
    }
    
    /** toString
     *  Pre: N/A
     *  Post: String
     */
    public String toString1 () {
        String output = "";
        
        //Line 1
        int i = 1;
        output += "\033[40m" + "      " + reset + "|";
        return output;
        
    }
    
    /** toString
     *  Pre: N/A
     *  Post: String
     */
    public String toString2 () {
        //Line 2
        String output = "";
         output +=" RAIL |";
         return output;
    }
    
    //deducts amount from payer's balance and adds it to the owner's balance
    //Pre: Player who is paying
    //Post: none, changes Player's balance values
    public void payRent(Player payer, Board b) {
        
        if (mortgaged) {
            System.out.println("Property is mortgaged by the owner, no rent is collected!");
            return; }

        int numOwned = 0;
        //5, 15, 25, 35
        int nextRail = 0;
        nextRail = position;
        
        for (int i = 0; i < 3; i ++) {
            
            nextRail = (nextRail+5)%40;
            if (((Property)b.getTile(nextRail)).getOwner().getIcon().equals(super.owner.getIcon())) numOwned++;
        }
        
        int r = rent[numOwned];
        //25, 50, 100, 200
        
        System.out.println(Colour.RED + "\nThis property is currently owned by " + Colour.RESET + owner.getIcon());
        System.out.println("Your bank account has been deducted by $" + (r*-1));
        
        owner.changeBalance(r);
        payer.changeBalance((r*-1));
        
    }
    
}