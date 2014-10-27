package Crazy8;

//Cards have 2 extra "pixels" empty on the side
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class CrazyEightGUI extends JFrame
{
   private static JPanel panel;
   private static BufferedImage[][] cardSprites;
   private Player play;
   private Card top;
   public CrazyEightGUI(Card discard, Player player)
   {
      super("Crazy 8");
      panel = new JPanel();
      top = discard;
      play = player;
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(1000,700);
      setResizable(false);
      add(panel);
      setBackground(Color.GREEN);
      panel.setBackground(Color.GREEN);
      cardSprites = new BufferedImage[4][14];
      boolean loading = true;
      int suit = 0;
      int val = 0;
      setVisible(true);
      Graphics g = panel.getGraphics();
      if(g == null)
      {
         System.out.println("BLEH");
      }
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
         String toLoad = s+v;
         System.out.println("Trying to load "+toLoad+".");
         try
         {
            File image = new File("Cards/"+toLoad+".png");
            BufferedImage bi = ImageIO.read(image);
            cardSprites[suit][val] = bi;
         }
         catch(IOException e)
         {
            System.out.println("Error Loading Card Sprites.");
         }
         val++;
         if(val == 14)
         {
            val = 0;
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
      BufferedImage bim = null;
      BufferedImage dis = null;
      int disVal = discard.value;
      String disSuit = discard.suit.substring(0,1).toLowerCase();
      String graphix = disSuit;
      if(disVal < 10)
      {
         graphix += "0"+disVal;
      }
      else
      {
         graphix += disVal;
      }
      try
      {
         bim = ImageIO.read(new File("Cards/Card-Back.png"));
         dis = ImageIO.read(new File("Cards/"+graphix+".png"));
      }
      catch(IOException e)
      {
         System.out.println("Card back could not be loaded.");
      }
      int height = 180;
      g.drawImage(bim,360,170,(int)(height*.7225),height,null);
      g.drawImage(dis,510,170,(int)(height*.7225),height,null);
      ArrayList<Card> playerHand = new ArrayList<Card>(player.hand);
      int num = playerHand.size();
      int xpos = 500;
      int h = 250;
      int w = (int)(h * .7225);
      int ypos = 420;
      int cardDist = w;
      if(1000 < (num*(w-4)) && num > 1)
      {
         xpos = 0;
         cardDist = ((1000-w)/(num-1));
      }
      else
      {
         xpos -= (w/2)*num;
      }
      for(Card c: playerHand)
      {
         try
         {
            String st = c.suit.substring(0,1).toLowerCase();
            if(c.value < 10)
            {
               st += "0"+c.value;
            }
            else
            {
               st += c.value;
            }
            BufferedImage bfim = ImageIO.read(new File("Cards/"+st+".png"));
            g.drawImage(bfim,xpos,ypos,w,h,null);
         }
         catch(Exception e)
         {
            System.out.println("Issues adding cards to hand");
         }
         xpos += cardDist;
      }
   }
   /**
    * Called from the driver to update the GUI
    * @param players the list of Players who are in the game
    * @param top the top of the discard pile
    */
   public void update(ArrayList<Player> players, Card top)
   {
      panel.repaint();
      drawCards(players.get(0));
      drawPiles(top);
   }
   /**
    * Draws the cards onto the JFrame.
    * @param p the Player controled by the User
    */
   public void drawCards(Player p)
   {
      Graphics g = panel.getGraphics();
      ArrayList<Card> playerHand = p.hand;
      int num = playerHand.size();
      int xpos = 500;
      int h = 250;
      int w = (int)(h * .7225);
      int ypos = 420;
      int cardDist = w;
      if(1000 < (num*(w-4)) && num > 1)
      {
         xpos = 0;
         int extraPix = 1000-((cardDist*(num-1))+w);
         if(extraPix > 0)
         {
            xpos += (int)(extraPix/2);
         }
         cardDist = ((1000-w)/(num-1));
      }
      else
      {
         xpos -= (w/2)*num;
      }
      for(Card c: playerHand)
      {
         try
         {
            String st = c.suit.substring(0,1).toLowerCase();
            if(c.value < 10)
            {
               st += "0"+c.value;
            }
            else
            {
               st += c.value;
            }
            BufferedImage bfim = ImageIO.read(new File("Cards/"+st+".png"));
            g.drawImage(bfim,xpos,ypos,w,h,null);
         }
         catch(Exception e)
         {
            System.out.println("Issues adding cards to hand");
         }
         xpos += cardDist;
      }
   }
   /**
    * Updates the piles on the GUI
    * @param c the card at the top of the discard pile.
    */
   public void drawPiles(Card c)
   {
      Graphics g = panel.getGraphics();
      BufferedImage bim = null;
      BufferedImage dis = null;
      int disVal = c.value;
      String disSuit = c.suit.substring(0,1).toLowerCase();
      String graphix = disSuit;
      if(disVal < 10)
      {
         graphix += "0"+disVal;
      }
      else
      {
         graphix += disVal;
      }
      try
      {
         bim = ImageIO.read(new File("Cards/Card-Back.png"));
         dis = ImageIO.read(new File("Cards/"+graphix+".png"));
      }
      catch(IOException e)
      {
         System.out.println("Card back could not be loaded.");
      }
      int height = 180;
      g.drawImage(bim,360,170,(int)(height*.7225),height,null);
      g.drawImage(dis,510,170,(int)(height*.7225),height,null);
   }
   public void paint(Graphics g)
   {
      super.paint(g);
      drawCards(play);
      drawPiles(top);
   }
}