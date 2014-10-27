package Crazy8;

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
        return "Once per turn, you can create a valueless card of a random suit and place it on the top of the discard pile.\n\n\n\n";
    }
    
    public int uses() { return 1; }
    
    public String name() { return this.name; }
    
    public boolean endTurn() { return false; }
    
    public void use(Deck deck, DiscardPile discardPile, ArrayList<Player> players)
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
