package Crazy8;
import java.util.ArrayList;

public interface Behavior
{
    public void consider(LStack<Card> deck, ArrayList<Player> playerList, LStack<Card> discardPile);
}
