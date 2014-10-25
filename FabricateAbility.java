import java.util.ArrayList;
import java.util.Random;

public class FabricateAbility implements Ability
{
    private Player owner;
    public String name = "Fabricate";

    public FabricateAbility(Player player)
    {
        this.owner = player;
    }
    
    public String toString()
    {
        return "Cycle a card from your hand into the discard pile, and draw a card from the deck. End your turn.\n\n\n\n";
    }
    
    public int uses() { return 1; }
    
    public void use(LStack<Card> deck, LStack<Card> discardPile, ArrayList<Player> players)
    {
        Random randomIndexGenerator = new Random();
        int randomSuitNum = randomIndexGenerator.nextInt(4);
        String randomSuit = "default";
        if(randomSuitNum == 0) randomSuit = "diamond";
        else if(randomSuitNum == 1) randomSuit = "heart";
        else if(randomSuitNum == 2) randomSuit = "spade";
        else if(randomSuitNum == 3) randomSuit = "club";
        
        Card fabricatedCard = new Card(0, randomSuit);
        discardPile.push(fabricatedCard);
        System.out.println(owner + " used 'Fabricate!'");
        System.out.println(owner + " fabricated a " + fabricatedCard);
        System.out.println(owner + " discarded it to the top of the pile.");
        
    }
}
