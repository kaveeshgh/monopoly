public class Square {
    
    protected String name;
    protected int position;
    
    protected int balance;
    
    //Constructor
    //Pre: String n for Square's name
            //int p for position
    //Post: none
    public Square(String n, int p)
    {
        name = n;
        position = p;
    }
    //sets balance of the square (how landing on the square changes player's balance)
    //Pre: int b for the balance
    //Post: none
    public void setBalance(int b) {
        balance = b;
    }
    
    //returns position of the square
    //Pre: none
    //Post; returns int of Square's position
    public int getPosition() {
        return position;
    }
    
    //adds Square's balance to a player's balance
    //Pre: Player p to change balance\
    //Post: none
    public void addBalance (Player p) {
        p.changeBalance(balance);
    }
    
    //sets Square's name
    //Pre: String n for new name
    //Post: none
    public void setName(String n){
        name = n;
    }
    
    //returns Square's name
    //Pre: none
    //Post: returns String name
    public String getName(){
        return name;
    }
    
    //toString for first line
    //Pre: none
    //Post: String to be outputted
    public String toString1 () {
        String output = "";
        
        //Line 1
        output +="      |";
        return output;
        
    }
    
    //toString for second line
    //Pre: none
    //Post: String to be outputted
    public String toString2 () {
        //Line 2
        String output = "";
        output += name+"|";
        return output;
    }
    
    //toString for third line
    //Pre: Board b to be referenced for player positions
    //Post: String to be outputted
    public String toString3 (Board b) {
        
        String output = "";
        //Line 3
        String ps = b.players(position); //String of what player icons are on the Square
        output += ps;
        for (int i = 0; i < (6-ps.length()); i ++) output += " ";
        
        output +="|";
        
        return output;
    }
    
    public int getSet() {
        return -1;
    }
    
    //toString for whole Square
    // Pre: Board b to be referenced
    // Post: String to be outputted
    public String toString (Board b) {
        
        String output = "";
        
        //Line 1
        output +="      |\n";
        //Line 2
        
        output += " FREE |\n";
        
        //Line 3
        String ps = b.players(position);
        
        output += ps;
        for (int i = (ps.length()); i < 6; i ++)
        {
            output +=" ";
        }
        output +="|\n";
        
        return output;
    }
}