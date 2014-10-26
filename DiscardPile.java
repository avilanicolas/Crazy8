//package Crazy8;
import java.util.*;

public class DiscardPile extends LStack<Card>
{
    public final static int EIGHT = 8;
    public String topSuit = "";
    
    /*
     *  @return String top suit
     */
    public void push(Card other)
    {
        super.push(other);
        topSuit = this.peek().suit;
    }
    
    /**
      *  Discard a card other than an eight.
      *
      */
    public void discard(Card top)
    {
        topSuit = top.suit;
    }
    
    /**
      *  To be called by the player and interacted with by the player
      *  when said player plays an eight in the game.
      */
    public void discardEight(Card other, String suit)
    {
        Scanner input = new Scanner(System.in);
        ArrayList<String> suits = new ArrayList<>();
        suits.add("diamonds");
        suits.add("hearts");
        suits.add("clubs");
        suits.add("spades");
        String temp;
        boolean valid = false;
        while(!valid)
        {
            System.out.println("What suit would you like to choose? ");
            temp = input.nextLine();
            if(suits.contains(temp))
            {
               valid = true;
               topSuit = temp;
            }
            else
            {
               valid = false;
               input.nextLine();
               System.out.println("Invalid input for suit.");
               System.out.println("Please use all lowercase letters and no numbers.");
            }
        }
    }
}