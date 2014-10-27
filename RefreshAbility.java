import java.util.ArrayList;
import java.util.Random;

public class RefreshAbility implements Ability
{
    private Player owner;
    public String name = "Fabricate";

    public RefreshAbility(Player player)
    {
        this.owner = player;
    }
    
    public String toString()
    {
        return "Once per turn, you can shuffle your entire hand into the deck and end your turn.\n\n\n\n";
    }
    
    public int uses() { return 1; }
    
    public String name() { return this.name; }
    
    public boolean endTurn() { return true; }
    
    public void use(Deck deck, DiscardPile discardPile, ArrayList<Player> players)
    {
        System.out.println(owner + " used " + this.name + "!");
        
        int handSize = owner.hand.size();

        System.out.println(owner + " emptied their hand into the deck. The deck was reshuffled and they drew " + handSize + " cards.\n\n");     
        
        while(owner.hand.size() > 0) deck.push(owner.hand.remove(0));
        
        deck.shuffle();
        
        for(int i = 0; i < handSize; i++) owner.draw(deck);
    }
}
