import java.util.Scanner;
public class Deck {
    
    private Card[] deck;
    
    /** Constructor
     *  Pre: Scanner to read card information
     *  Post: N/A
     */
    public Deck(Scanner s)
    {
        deck = new Card[10];
        
        for (int i = 0; i < 10; i++)
        {
            deck[i] = new Card(s.nextInt(), s.next(), s.next(), s.next(), s.next(), s.nextInt(), s.nextInt());
        }
    }
    
    /** Returns a card object
     *  Pre: The index of the card that is returned
     *  Post: A card
     */
    public Card getCard(int index)
    {
        return deck[index];
    }
    
    /** Shuffles the cards
     *  Pre: N/A
     *  Post: N/A
     */
     public void shuffle()
    {
        Card switchCard;
        
        for (int i = 0; i < 100; i++)
        {
            int num1 = (int)(0 + Math.random()*(9 - 0 + 1));
            int num2 = (int)(0 + Math.random()*(9 - 0 + 1));
            
            switchCard = deck[num1];
            
            deck[num1] = deck[num2];
            deck[num2] = switchCard;
        }
    }
}