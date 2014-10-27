import java.util.*;

public class DiscardPile extends LStack<Card>
{
    public final static int EIGHT = 8;
    public String topSuit = "";
    
    /*
     *  @return String top suit
     */
    public void push(Card other)
    {
        super.push(other);
        topSuit = this.peek().suit;
    }
    
    /**
      *  Discard a card other than an eight.
      *  Actually, this method is unnecessary for the implementation of the game.
      */
    public void discard(Card top)
    {
        this.push(top);
        topSuit = top.suit;
    }
}