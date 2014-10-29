 

import java.lang.Comparable;
import java.util.ArrayList;

/**
 * The Card class holds information on a card's name, it's suit, value. It also includes methods to print
 * and compare.
 * 
 * @author Nicolas Avila
 * @version 1.0
 */
public class Card
{
    /** This card's suit. */
    public String suit = "";
    /** This card's value. */
    public int value = 0;
    /** This card's shorthand. A King of diamonds is Kd and a 10 of hearts is 10h, for example.*/
    public String shortText = "";
    
    /**
     * Constructor for objects of class Card
     */
    public Card(int newValue, String newSuit)
    {
        this.value = newValue;
        this.suit = newSuit;
        
        // Construct our shorthand for this card based on its values.
        // 11 -> J 13 -> K
        // 12 -> Q 14 -> A
        String faceCardType = "";
        if(this.value == 11) faceCardType = "J";
        else if(this.value == 12) faceCardType = "Q";
        else if(this.value == 13) faceCardType = "K";
        else if(this.value == 1) faceCardType = "A";
        else faceCardType += newValue;
        // The final character in the shorthand is the first character in the suit.
        this.shortText = faceCardType + newSuit.charAt(0);
    }

    /**
     * @return This card in the format of: "{Value} of {suit} ({shorthand})"
     */
    public String toString()
    {
        String retText = "";
        String faceCardType = "";
        // Because suits within this program's logic are generally lowercase, make sure to capitalize the suit
        // when we present it to the player.
        String printSuit = Character.toUpperCase(this.suit.charAt(0)) + this.suit.substring(1);
        // Make sure to print "Jack" for an actual Jack, "Queen" for a Queen, etc. etc.
        if(this.value == 11) retText = "Jack";
        else if(this.value == 12) retText = "Queen";
        else if(this.value == 13) retText = "King";
        else if(this.value == 1) retText = "Ace";
        else  retText = "" + this.value;
        retText += " of " + printSuit + "s (" + this.shortText + ")";
        return retText;
    }
    
    /**
     * Provide a method that allows us to compare to cards. Card order is determined firstly by suit in this fashion:
     * Diamonds > Hearts > Spades > Clubs
     * If the suit of two cards is the same, then compare their value.
     * e.g. A 2 of Diamonds is greater than an Ace of Spaces
     *      A 9 of Diamonds is less than a King of Diamonds
     *      A Queen of Hearts is greater than a Queen of Clubs.
     * A card not of the four cardinal suits is ALWAYS considered less than a card form these suits.
     * 
     * @param other The other card to compare this card to.
     * @return -1, 0, 1 Pending if this card are less than, equal to, or greater than the other.
     */
    public int compareTo(Card other)
    {
        // Assign numerical values to the cardinal suits. 
        int[] ranks = {0, 0};
        int ret = 0;
        if(this.suit == "diamond") ranks[0] = 4;
        if(this.suit == "heart") ranks[0] = 3;
        if(this.suit == "spade") ranks[0] = 2;
        if(this.suit == "club") ranks[0] = 1;
        if(other.suit == "diamond") ranks[1] = 4;
        if(other.suit == "heart") ranks[1] = 3;
        if(other.suit == "spade") ranks[1] = 2;
        if(other.suit == "club") ranks[1] = 1;
        // If one card is of a greater suit, return appropriately.
        // If not, work it out based on their values. Two cards are only equal if their suit matches as well as their values.
        if(ranks[0] > ranks[1])
            return 1;
        else if(ranks[1] > ranks[0])
            return -1;
        else
        {
            // We now know this to be the same suit.
            // We are now just comparing the integer value of the card, so why not reuse ranks?
            ranks[0] = this.value;
            ranks[1] = other.value;
            if(ranks[0] > ranks[1]) return 1;
            if(ranks[1] > ranks[0]) return -1;
        }
        return 0;
    }
    
    //Checks if the card is a valid play.
    public boolean validPlay(Card other)
    {
        if(this.value == other.value || this.suit == other.suit)
            return true;
        else
            return false;
    }
    
    //Extra method to check that we can have multiple cards played
    //   in one turn.
    //   All of the ranks being played must be the same as the discard Pile's top card.
    public boolean sameRank(ArrayList<Card> hand, int amount)
    {
        if(amount == 0) return (this.value == hand.get(0).value);
        return (this.value == hand.get(amount).value) && sameRank(hand, amount-1);
    }
}