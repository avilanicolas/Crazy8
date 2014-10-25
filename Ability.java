import java.util.ArrayList;

public interface Ability
{
    public String toString();
    public int uses();
    public void use(LStack<Card> deck, LStack<Card> discardPile, ArrayList<Player> players);
}
