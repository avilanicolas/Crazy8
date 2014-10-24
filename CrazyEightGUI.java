//package Crazy8;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
public class CrazyEightGUI
{
   private static JFrame frame;
   public static void main(String[] args)
   {
      frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(1000,500);
      frame.getContentPane().setBackground(Color.GREEN);
      frame.setVisible(true);
   }
   /**
    * Called from the driver to update the GUI
    * @param players the list of Players who are in the game
    * @param pile the discard file
    */
   public void update(ArrayList<Player> players, LStack<Card> pile)
   {
      drawCards(players.get(0));
      drawPiles(pile.peek());
   }
   /**
    * Draws the cards onto the JFrame.
    * @param p the Player controled by the User
    */
   public void drawCards(Player p)
   {
      
   }
   /**
    * Updates the piles on the GUI
    * @param c the card at the top of the discard pile.
    */
   public void drawPiles(Card c)
   {
      
   }
}