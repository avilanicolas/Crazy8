package Crazy8;

import java.util.ArrayList;

/**
 * A class that provides
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{
    public String name = "";
    public String openingText = "";
    public String greetingText = "";
    public String tauntText = "";
    public ArrayList<Card> hand;
    
    public Player(String newName, String openingText, String greeting, String taunt)
    {
        this.name = newName;
        this.greetingText = greeting;
        this.tauntText = taunt;
        //this.hand = newHand;
    }
    
    public String toString()
    {
        return this.name;
    }
}
