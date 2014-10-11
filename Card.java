package Crazy8;
import java.lang.Comparable;

/**
 * The Card class holds information on a card's name, it's suit, value. It also includes methods to print
 * and compare.
 * 
 * @author Nicolas Avila
 * @version 1.0
 */
public class Card
{
    public String suit = "";
    public int value = 0;
    
    
    /**
     * Constructor for objects of class Card
     */
    public Card(int newValue, String newSuit)
    {
        this.value = newValue;
        this.suit = newSuit;
    }

    public String toString()
    {
        String retText = "";
        if(value == 'A') retText = "Ace";
        String faceCardType = "";
        String printSuit = Character.toUpperCase(this.suit.charAt(0)) + this.suit.substring(1);
        if(this.value == 11) faceCardType = "J";
        else if(this.value == 12) faceCardType = "Q";
        else if(this.value == 13) faceCardType = "K";
        else if(this.value == 14) faceCardType = "A";
        else faceCardType += this.value;
        retText = this.value + " of " + printSuit + "s (" + faceCardType + this.suit.charAt(0) + ")";
        return retText;
    }
    
    /**
     * @param other The other card to compare this card to.
     * @return -1, 0, 1 Pending if this card are less than, equal to, or greater than the other.
     */
    public int compareTo(Card other)
    {
        // D > H > S > C
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
        if(ranks[0] > ranks[1])
        {
            ret = 1;
        }
        else if(ranks[1] > ranks[0])
        {
            ret = -1;
        }
        else
        {
            // We now know this to be the same suit.
            // We are now just comparing the integer value of the card, so why not reuse ranks?
            ranks[0] = this.value;
            ranks[1] = other.value;
            if(ranks[0] > ranks[1]) ret = 1;
            if(ranks[1] > ranks[0]) ret = -1;
        }
        return ret;
    }
}
