import java.util.ArrayList;
import java.util.Random;

public class CycleAbility implements Ability
{
    private Player owner;
    public String name = "Cycle";

    public CycleAbility(Player player)
    {
        this.owner = player;
    }
    
    public int uses() { return 2;}
    
    public String toString()
    {
        return "Cycle a card from your hand into the discard pile, and draw a card from the deck. End your turn.\n\n\n\n";
    }
    
    public void use(LStack<Card> deck, LStack<Card> discardPile, ArrayList<Player> players)
    {
        Random randomIndexGenerator = new Random();
        int randomIndex = randomIndexGenerator.nextInt(owner.hand.size());
        Card discard = owner.hand.remove(randomIndex);
        System.out.println(owner + " used 'Cycle!'");
        System.out.println(owner + " discarded a " + discard + " onto the pile.");
        discardPile.push(discard);
        System.out.println(owner + " drew a card.");
        owner.hand.add(deck.pop());
    }
}
