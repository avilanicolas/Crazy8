import java.util.ArrayList;
import java.util.Random;

/**
 * The Cycle ability allows a character to discard randomly from their hand onto the pile and draw a card.
 */
public class CycleAbility implements Ability
{
    private Player owner;
    public String name = "Cycle";

    /**
     * @param player The player that 'owns' this skill.
     */
    public CycleAbility(Player player)
    {
        this.owner = player;
    }
    
    /**
     * Cycle is supposed to end your turn right away, so its uses doesn't matter.
     */
    public int uses() { return 2;}
    
    public String name() { return this.name; }
    
    public boolean endTurn() { return true; }
    
    /**
     * A description of Cycle. Call ability.name instead for its name.
     */
    public String toString()
    {
        return "Cycle a card from your hand into the discard pile, and draw a card from the deck. End your turn.\n\n\n\n";
    }
    
    /**
     * Generate a random integer to select from the hand, discard it, draw a card.
     * @param deck          Refer to interface documentation.
     * @param discardPile   Refer to interface documentation.
     * @param players       Refer to interface documentation.
     */
    public void use(Deck deck, DiscardPile discardPile, ArrayList<Player> players)
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
