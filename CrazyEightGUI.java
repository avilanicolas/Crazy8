 
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class CrazyEightGUI
{
   private static JFrame frame;
   private static BufferedImage[][] cardSprites;
   public static void main(String[] args)
   {
      frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(1000,500);
      frame.getContentPane().setBackground(Color.GREEN);
      cardSprites = new BufferedImage[4][13];
      boolean loading = true;
      int suit = 0;
      int val = 1;
      Graphics g = frame.getContentPane().getGraphics();
      String s  = "";
      String v = "";
      while(loading)
      {
         if(suit == 0)
         {
            s = "c";
         }
         else if(suit == 1)
         {
            s = "d";
         }
         else if(suit == 2)
         {
            s = "h";
         }
         else if(suit == 3)
         {
            s = "s";
         }
         if(val < 10)
         {
            v = "0"+val;
         }
         else
         {
            v = ""+val;
         }
         try
         {
            String toLoad = s+v;
            System.out.println("Trying to load "+toLoad+".");
            cardSprites[suit][val-1] = ImageIO.read(new File("Classic/"+toLoad+".png"));
         }
         catch(IOException e)
         {
            System.out.println("Error Loading Card Sprites.");
         }
         val++;
         if(val == 14)
         {
            val = 1;
         }
         if(val == 1)
         {
            suit++;
         }
         if(suit == 4)
         {
            loading = false;
         }
      }
      frame.setVisible(true);
   }
   /**
    * Called from the driver to update the GUI
    * @param players the list of Players who are in the game
    * @param pile the discard file
    */
   public void update(ArrayList<Player> players, LStack<Card> pile)
   {
      Graphics g = frame.getGraphics();
      BufferedImage drawPile = null;
      try
      {
         drawPile = ImageIO.read(new File("/Classic/Card-Back.png"));
      }
      catch(Exception e)
      {
         System.out.println("ASDFFASDFASDFASDF");
      }
      g.drawImage(drawPile,0,0,null);
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