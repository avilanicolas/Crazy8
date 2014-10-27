package Crazy8;

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
    
    
    /** Manually set the AI behavior when you create this object. */
    public Behavior behavior;
    
    public Ability ability;
    
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
    }
    
    /**
     * A lazy way to create -somewhat- descriptive dialogue. This method will return a string like:
     *  Jaina shouted, "My magic will tear you apart!"
     *  Malfurion lazily mumbled, "This game is crazy."
     *
     * @param message       What you want the character to say.
     * @param description   How you want them to say it.
     * @return A string in the form: {Character.name} {description} "{message}"
     */
    public String say(String message, String description)
    {
        String dialogue = this.name + " " + description + " \"" + message + "\"";
        return dialogue;
    }
    
    public Player() {}
    
    /**
      *  Print the player's hand in a list from 1-8.
      *
      */
    public void printHand()
    {
        // Here we are scrolling through every card in the player's hand and printing it, using .format() to make sure
        // everything is spaced correctly.
        // Note on this format: The specific %-20s merely specifies a left-aligned message of 20 characters in length.
        //  So if the string we're passing in place of that argument is not 20 characters in length, the rest of the space
        //  will be filled in with whitespace.
        String handText = "";
        for(Card card : this.hand)
        {
            int cardIndex = this.hand.indexOf(card) + 1;
            handText += String.format("%-3s %-25s", cardIndex + ")", card);
            // Make every row 4 cards in length, for less wide displays.
            if(((this.hand.indexOf(card) + 1) % 4) == 0) handText += "\n";
        }
        handText += "\n\n";
        System.out.println(handText);
    }
    
    public Card draw(Deck deck)
    {
        Card drawCard = deck.pop();
        hand.add(drawCard);
        return drawCard;
    }    
    
    public String toString()
    {
        return this.name;
    }
}
