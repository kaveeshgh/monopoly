public class Utility extends Property 
{
    private Die die;
    
    /** Constructor
     *  Pre: String for the name of the tile
     *       An integer for the position of the tile
     *       An integer for the price of the utility
     *       An integer for the mortgage 
     *       An integer array for rent prices
     *       An integer for the colour set it is part of
     *       A die object
     *  Post: N/A
     */
    public Utility (String n, int pos, int p, int m, int[] r, int s, Die d) {
        
        super(n, pos, p, m, r, s);
        die = d;
        houses = 0;
        
    }
    
    /** toString
     *  Pre: N/A
     *  Post: String
     */
    public String toString1 () {

        return "      |";
        
    }
    
    /** toString
     *  Pre: N/A
     *  Post: String
     */
    public String toString2 () {
        //Line 2
        String output = "";
        output += name + "|";
        return output;
    }
    
    //deducts amount from payer's balance and adds it to the owner's balance
    //Pre: Player who is paying
    //Post: none, changes Player's balance values
    public void payRent(Player payer, Board b) {
        
        if (mortgaged) {
            System.out.println("Property is mortgaged by the owner, no rent is collected!");
            return; }
        //12, 28
        int numOwned = 0;
        if (super.getPosition()==12) {
            if (((Property)b.getTile(28)).getOwner() == owner) numOwned++;
        }
        
        else {
            if (((Property)b.getTile(12)).getOwner().getIcon().equals(super.owner.getIcon())) numOwned++;
        }
        int r = rent[numOwned]*die.getTotal();
        
        System.out.println(Colour.RED + "\nThis property is currently owned by " + Colour.RESET + owner.getIcon());
        System.out.println("Utilities owned by owner: "+numOwned+"  *\tDie value: "+die.getTotal()+"\t= "+r);
        System.out.println("Your bank account has been deducted by $" + (r*-1));
        
        owner.changeBalance(r);
        payer.changeBalance((r*-1));
        
    }

}