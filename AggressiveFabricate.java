package Crazy8;

 
import java.util.ArrayList;

/**
 * AggressiveBehavior is the basic AI behavior characters will use. AggressiveBehavior favors playing the most cards
 * in any situation. If the AI can play several cards in one turn, it will always play those. It will play a matching
 * card by number than by suit. If it cannot play a card, it will draw until it has a playable card.
 */
public class AggressiveFabricate implements Behavior
{
    public Player owner;
    
    public AggressiveFabricate(Player owner)
    {
        this.owner = owner;
    }
    
    /**
     * @param deck The current deck we're playing with.
     * @param playerList The list of playrs we're playing with.
     * @param discardPile The growing discardPile in the game.
     */
    public void play(Deck deck, ArrayList<Player> playerList, DiscardPile discardPile)
    {
        ArrayList<Card> hand = owner.hand;
        Card topCard = discardPile.peek();
        ArrayList<Integer> eightSuits = new ArrayList<Integer>(); // The different suits we have for our eight cards.
        
        // NumSuits will store the number of cards we have for each suit. The order of this array is:
        //   diamond heart club space
        // So if we have 4 hearts, 2 clubs, and 2 spades, our array will be {0, 4, 2, 2}
        int[] numSuits = {0, 0, 0, 0};
        for(Card card : hand)
        {
            int suitSelector = -1;
            // Because suits are stored as strings, and we're selecting by integer, we need
            // to appropriately associate our suits with appropriate integer values.
            if(card.suit.equals("diamond")) suitSelector = 0;
            else if(card.suit.equals("heart")) suitSelector = 1;
            else if(card.suit.equals("club")) suitSelector = 2;
            else if(card.suit.equals("spade")) suitSelector = 3;
            
            // Select the suit that we'll increment.
            if(suitSelector > -1) numSuits[suitSelector]++;
            
            if(card.value == 8)
            {
                eightSuits.add(suitSelector);
            }
        }
        
        // DecisonReached will be set to 'true' as soon as any card is played.
        boolean decisionReached = false;
        boolean cardDrawn = false;
        int cardsDrawn = 0;
        int abilityUsed = 0;
        while(!decisionReached)
        {
            // Check the active suit, this will be changed later.
            String activeSuitName = discardPile.peek().suit;
            int activeSuit = -1; // The actual integer value we'll use to select a suit with.
            int suitSelector = -1; // Mirrored function above, but activeSuit may differ later on.
            if(activeSuitName.equals("diamond")) suitSelector = 0;
            else if(activeSuitName.equals("heart")) suitSelector = 1;
            else if(activeSuitName.equals("club")) suitSelector = 2;
            else if(activeSuitName.equals("spade")) suitSelector = 3;
            
            // Similar to suit.
            int activeValue = discardPile.peek().value;
            boolean multipleCardPlay = false; // Are we going to play multiple cards?
            ArrayList<Card> playableCardsByValue = new ArrayList<Card>();
            
            // Fill a list of cards that we can play from our hand that match with the current card.
            for(Card card : hand)
            {
                if(card.value == discardPile.peek().value)
                {
                   playableCardsByValue.add(card);
                }
            }
            
            // If we have playable cards from the above list, play all of them since we can play multiple
            // cards that all match the active card.
            if(playableCardsByValue.size() >= 1)
            {
                for(Card card : playableCardsByValue)
                {
                    if(owner.hand.size() == 1)
                        System.out.println("Thrall shouted, \"" + owner.tauntText + "\"");
                    owner.hand.remove(card);
                    discardPile.push(card);
                    System.out.println(owner + " played a " + card);
                }
                decisionReached = true;
            }
            else
            {
                // We didn't have any playable cards by value. Do it by suit instead.
                ArrayList<Card> playableCardsBySuit = new ArrayList<Card>();
                
                for(Card card : hand)
                {
                    if(card.suit.equals(discardPile.peek().suit))
                        playableCardsBySuit.add(card);
                }

                // Play the first card from this list if we can play from it.
                if(playableCardsBySuit.size() >= 1)
                {
                    if(owner.hand.size() == 1)
                        System.out.println(owner.say(owner.tauntText, "shouted"));
                    
                    owner.hand.remove(playableCardsBySuit.get(0));
                    discardPile.push(playableCardsBySuit.get(0));
                    System.out.println(owner + " played a " + playableCardsBySuit.get(0));
                    decisionReached = true;
                }
                else if(abilityUsed < owner.ability.uses())
                {
                    owner.ability.use(deck, discardPile, playerList);
                    abilityUsed += 1;
                }
                else if(cardsDrawn < 3)
                {
                    // We can't play a single card, so draw.
                    Card drawCard = deck.pop();
                    owner.hand.add(drawCard);
                    System.out.println(owner + " drew a card.");
                    cardDrawn = true;
                    cardsDrawn++;
                }
                else
                {
                    decisionReached = true;
                }
            }
        }
        
        System.out.println("\n\n");
    }
}
