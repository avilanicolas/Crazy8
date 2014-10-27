//package Crazy8;
import java.util.*;

public class Deck extends LStack<Card>
{
	/**
	 * Inherits: push(...), pop(), isEmpty(), get(...), peek(), search(...)
     * @return A new, randomly filled deck of the four suits, 1 through Ace.
     */
	public Deck()
	{
        // First we need to establish the cards we are using. It's easier to just load the cards
        // into suits, and then randomly pick from within the suits when we're filling the deck.
        // Here, we're just declaring lists of Cards, and filling them sequentially with 14 cards.
        //LinkedList<Card> newDeck = new LinkedList<Card>();
        ArrayList<Card> diamonds = new ArrayList<Card>();
        ArrayList<Card> hearts = new ArrayList<Card>();
        ArrayList<Card> clubs = new ArrayList<Card>();
        ArrayList<Card> spades = new ArrayList<Card>();
        for(int i = 2; i <= 14; i++) diamonds.add(new Card(i, "diamond"));
        for(int i = 2; i <= 14; i++) hearts.add(new Card(i, "heart"));
        for(int i = 2; i <= 14; i++) clubs.add(new Card(i, "club"));
        for(int i = 2; i <= 14; i++) spades.add(new Card(i, "spade"));
        
        // Now we need to randomly select from the above suits and add each one to the deck
        // removing from their suit's list. We do this until all suits are empty.
        boolean suitsUsed = false; // If all suits are used up, exit the loop.
        Random ran = new Random();
        int ranNum = 0; // This determines the random index from the suit list we'll take from.
        int ranSuit = 0; // This determines the random suit we'll select.
        // We'll store the suits that are currently empty, so we have a nice collection of empty suits
        // as they are used up.
        ArrayList<Integer> emptySuits = new ArrayList<Integer>();
        while(!suitsUsed)
        {
            // Here, we declare which suit we'll be working with -- however, what if we recieve a 
            // random integer for a matching suit that is EMPTY? The following while loop examines
            // what we get, and makes sure we only progress until we know we'll be working with a suit
            // that is not empty.
            ranSuit = ran.nextInt(4);
            while(emptySuits.contains(ranSuit)) ranSuit = ran.nextInt(4);
            
            // Here, suit will be pointing to one of the non-empty declared suits above. This way,
            // we don't need to hardcode for each suit, but we can just refer to the suit we're
            // working with as "suit."
            ArrayList<Card> suit = new ArrayList<Card>();
            switch(ranSuit)
            {
                case 0:
                    suit = diamonds;
                    break;
                case 1:
                    suit = hearts;
                    break;
                case 2:
                    suit = clubs;
                    break;
                case 3:
                    suit = spades;
                    break;
            }
            
            // Currently, if we randomly select an empty suit that hasn't been added to emptySuits,
            // this is where we'd add that suit to emptySuits. 
            //
            // The first else condition is the portion of the method that actually adds a randomly
            // selected card, and pushes that card into LStack deck. Once we do that, we're then sure
            // to remove that card from its suit.
            //
            // The final else statement flips the kill condition "suitsUsed" once every suit is emptied.
            if(suit.size() == 0) emptySuits.add(ranSuit);
            else if(suit.size() != 0)
            {
                // We declare ranNum to be a number from 0 to the size of the current suit we're working with.
                // This size decreases overtime as we remove cards from that particular suit, so in this way,
                // we can randomly select a card from that decreasing list.
                ranNum = ran.nextInt(suit.size());
                this.push(suit.remove(ranNum));
                //suit.remove(ranNum);
                //System.out.println("Deck Size: " + this.size);
            }
            if(diamonds.size() == 0 && hearts.size() == 0 && clubs.size() == 0 && spades.size() == 0)
                suitsUsed = true;
        }
    }
    
    public void shuffle()
	{
		ArrayList<Card> tempDeck = new ArrayList<Card>();
		// Fill the new list of cards with the cards still in the deck.
		while(!this.isEmpty())
		{
			tempDeck.add(this.pop());
		}
		// Randomly push the cards from tempDeck to the original Deck.
		Random randy = new Random();
		int ranInt;
		while(!tempDeck.isEmpty())
		{
			ranInt = randy.nextInt(tempDeck.size());
			this.push(tempDeck.remove(ranInt));
		}
	}
}
