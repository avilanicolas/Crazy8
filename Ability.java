import java.util.ArrayList;

public interface Ability
{
    public String toString();
    public String name();
    public int uses();
    public boolean endTurn();
    public void use(Deck deck, DiscardPile discardPile, ArrayList<Player> players);
}
