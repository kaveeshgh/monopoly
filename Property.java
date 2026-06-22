public class Property extends Square
{
    
    protected int price;
    protected int mortgage;
    protected int[] rent;
    protected int set;
    protected int houses;
    //                                  red             yellow         green       light blue       blue        pink                                    orange            white
    protected final String[] colors = {"\033[0;101m", "\033[0;103m", "\033[42m", "\033[0;104m", "\u001B[44m", "\u001b[48;5;205m", "\033[0;105m", "\u001b[48;5;208m", "\033[0;100m", "\033[1;37m"+"\033[40m"};
    //"\033[44m" white
    protected String color;
    
    protected final String reset = "\033[0m";
    
    protected Player owner;
    protected boolean mortgaged;
    
    /*Constructor
    Precondition:   String n for property name
                    int pos for properties position
                    int p for the price
                    int m for the mortgage value
                    integer array for the values of rent at a number of houses (index of array)
                    int s for the set which the property belongs to
    */
    public Property(String n, int pos, int p, int m, int[] r, int s) {
  
        super(n, pos);
        price = p;
        mortgage = m;
        rent = r;
        set = s;
        
        houses = 0;
        
        color = colors[set-1];
        owner = null;
        mortgaged = false;

    }
    
    //toString for first line
    //Pre: none
    //Post: returns a String for output
    public String toString1() 
    {
        String output = "";
        
        //Line 1
        int i = 1;
        output += color+" ";
        
        for (int j = 0; j < houses; j ++) {
            output +="\u2302";
            i++;
        }
        
        for (; i < 6; i ++){
            output +=" ";
        }
            
        output +=reset;
        output +="|";
            
        return output;
    }
    
    //toString for second line
    //Pre: none
    //Post: returns a String for output
    public String toString2() 
    {
        //Line 2
        String output = "";
        output +=reset + " $" + price;
        
        if (price<100) output += " ";
        output += " |";
        
        return output;
    }
    
    //toString for third, prints players on that property
    //Pre: none
    //Post: returns a String for output
    public String toString3() 
    {
        String output = "";
        
        //Line 1
        output +="      |";
        return output;
    }

    //toString 
    //Pre: none
    //Post: returns a String for output
   /* public String toString() 
    {
        
        String output = "";
        
        //Line 1
        int i = 1;
        output += color+" ";
        
        for (int j = 0; j < houses; j ++) {
            output +="\u2302";
            i++;
        }
        
        for (; i < 6; i ++){
            output +=" ";
        }

        output +="|\n";
           
            
        //Line 2
        output +=reset + " $" + price + " |\n";
        
        
        //Line 3
        /*
        String ps = b.players(super.position);
        
        output += ps;
        for (i= (ps.length()); i <= 6; i ++){
            output +=" ";
        }
        
        
        output +="|";
        
        return output;
    } */
    
    //returns if the property is owned
    //Pre: none
    //Post: boolean of if it is owned
    public boolean isOwned () {
        if(owner == null)
        return false;
        
        return true;
    }
    //returns the Player that is the property owner
    //Pre: none
    //Post: Player who is the owner
    public Player getOwner() {
        return owner;
    }
    //deducts amount from payer's balance and adds it to the owner's balance
    //Pre: Player who is paying, Board b to be referenced
    //Post: none, changes Player's balance values
    public void payRent(Player payer, Board b) {
        
        if (mortgaged) {
            System.out.println("Property is mortgaged by the owner, no rent is collected!");
            return; }
        
        int r = rent[houses];
        
        System.out.println(Colour.RED + "\nThis property is currently owned by " + Colour.RESET + owner.getIcon());
        System.out.println("Your bank account has been deducted by $" + (r*-1));
        
        owner.changeBalance(r);
        payer.changeBalance((r*-1));
        
    }
    //adds ownership to the property
    //Pre: Player to become the owner
    //Post: none
    public void buyProperty (Player o)
    {
        o.addProperty(this);
        owner = o;
        mortgaged = false;
        owner.changeBalance((price*-1));
    }
    
    //adds(increments) a house to  the property, changes owner's balance
    //Pre: none
    //Post: none
    public void buyHouse () {
        
        int p = ((super.getPosition()) / 10) + 1;
        p *= 50;
        
        if (owner.getBalance() < p) {
            System.out.println("--Balance is too small to purchase a house--\n");
            return;
        }
        
        System.out.println("--You have bought a house!--\n");
        owner.changeBalance((p*-1));
        
        houses++;
        
    }
    
    //removes  house from property (changes owner's balance)
    //Pre: none
    //Post; none
    public void removeHouse () {
        
        int p = ((super.getPosition()) / 10) + 1;
        p *= 50;
        owner.changeBalance(p);
        
        houses--;
        
    }
    
    //returns property price
    // Pre: none
    // Post: return int of property price
    public int getPrice() {
        return price;
    }
    
    //returns rent amount
    //Pre: none
    //Post: returns int for rent amount
    public int getAmount() {
        return rent[houses];
    }
    //returns set property belongs to
    //Pre: none
    //Post: int set
    public int getSet() {
        return set;
    }
    //returns num of houses on property
    //Pre: none
    //Post; returns int houses
    public int getHouses () {
        return houses;
    }
    //mortgages house and adds the value to the owner's balance
    //Pre: none
    //Post: returns price of a house
    public int mortgage() {
        int p = ((super.getPosition()) / 10) + 1;
        p *= 50;
        
        for (int i = houses; i > 0; i --) {

            owner.changeBalance((p));
            houses--;
        }

        owner.changeBalance((mortgage));
        
        mortgaged = true;
        return p;
    }
    
    //sells property from the player
    //Pre: none
    //Post: none
    public void sellProperty(Player p){
        p.removeProperty(this);
        owner = null;
        mortgaged = false;
        houses = 0;
    }

}