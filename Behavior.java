 

 
import java.util.ArrayList;

/**
 * AI is handled as seperate behaviors. We can use a Behavior type object, which has its own algorithm when it comes to playing cards.
 * This makes it so that, if we want, we can multiple drastically different AIs that are easy to use with our player class.
 */
public interface Behavior
{
    public void play(Deck deck, ArrayList<Player> playerList, DiscardPile discardPile);
}
