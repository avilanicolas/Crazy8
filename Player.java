//package Crazy8;

import java.util.ArrayList;

/**
 * A class that provides framework for a Player character in the Crazy 8s game.
 */
public class Player
{
    /** The Player name. */
    public String name = "";
    /** For use at the beginning of the game, the character will have a unique line of dialogue to say. */
    public String openingText = "";
    /** Their greeting response to the player. */
    public String greetingText = "";
    /** Their taunt to the player. */
    public String tauntText = "";
    /** Their hand of cards. */
    public ArrayList<Card> hand;
    
    /**
     * @param newName Their new name.
     * @param opening Their opening line.
     * @param greeting Their greeting line.
     * @param taunt Their taunt response.
     */
    public Player(String newName, String opening, String greeting, String taunt)
    {
        this.name = newName;
        this.greetingText = greeting;
        this.tauntText = taunt;
        this.openingText = opening;
        //this.hand = newHand;
    }
    
    public Player() {}
    
    /**
     * @return This player's name.
     */
     
    //public Card hand(int index)
    //{
    //    if(hand != null && hand.size() > 0)
    //        return hand.get(index);
    //    else
    //    {
    //       System.out.println("Your hand is empty. You should have won already!");
    //       return new Card(0,"");
    //    }
    //}
    
    public String toString()
    {
        return this.name;
    }
}
