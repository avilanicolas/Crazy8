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
   private static BufferedImage back, side;
   private ArrayList<Player> play;
   private Card top;
   public CrazyEightGUI(Card discard, ArrayList<Player> player)
   {
      super("Crazy 8");
      panel = new JPanel();
      top = discard;
      play = player;
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(1000,900);
      setResizable(false);
      add(panel);
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
            File image = new File("./Cards/"+toLoad+".png");
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
            val = -1;
         }
         if(val == -1)
         {
            val++;
            suit++;
         }
         if(suit == 4)
         {
            loading = false;
         }
      }
      try
      {
         back = ImageIO.read(new File("./Cards/Card-Back.png"));
         side = ImageIO.read(new File("./Cards/Card-BackSide.png"));
      }
      catch(IOException e)
      {
         System.out.println("Card back could not be loaded.");
      }
      update(player,top);
   }
   /**
    * Called from the driver to update the GUI
    * @param players the list of Players who are in the game
    * @param top the top of the discard pile
    */
   public void update(ArrayList<Player> players, Card topCard)
   {
      top = topCard;
      play = players;
      paint(this.getContentPane().getGraphics());
   }
   public void update(Graphics g)
   {
      paint(g);
   }
   /**
    * Draws the cards onto the JFrame.
    * @param p the Player controled by the User
    */
   public void drawCards(ArrayList<Player> p)
   {
      play = p;
      Graphics g = panel.getGraphics();
      ArrayList<Card> playerHand = p.get(0).hand;
      int num = playerHand.size();
      int xpos = 500;
      int h = 250;
      int w = (int)(h * .7225);
      int ypos = 620;
      int cardDist = w;
      if(1000 < (num*(w-4)) && num > 1)
      {
         xpos = 2;
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
      int xtra = 1000 - (cardDist*(num-1)+w);
      for(Card c: playerHand)
      {
         String st = c.suit.substring(0,1).toLowerCase();
         BufferedImage bfim = cardSprites[suitToInt(st)][c.value];
         g.drawImage(bfim,xpos,ypos,w,h,null);
         if(xtra > 0)
         {
            xpos += cardDist + 1;
            xtra--;
         }
         else
         {
            xpos += cardDist;
         }
      }
      if(play.size()==2)
      {
         fillMid(1);
      }
      else if(play.size() == 3)
      {
         fillLeft(1);
         fillMid(2);
      }
      else
      {
         fillLeft(1);
         fillMid(2);
         fillRight(3);
      }
      g.dispose();
   }
   //g.fillRect(0,200,120,400);
   public void fillLeft(int index)
   {
      Graphics g = panel.getGraphics();
      ArrayList<Card> playerHand = play.get(index).hand;
      int num = playerHand.size();
      int xpos = 2;
      int h = 120;
      int w = (int)(h * (1/.7225));
      int ypos = 400;
      int cardDist = h;
      if(400 < (num*(h-4)) && num > 1)
      {
         ypos = 200;
         int extraPix = 400-((cardDist*(num-1))+h);
         if(extraPix > 0)
         {
            ypos += (int)(extraPix/2);
         }
         cardDist = ((400-h)/(num-1));
      }
      else
      {
         ypos -= (h/2)*num;
      }
      int xtra = 400 - (cardDist*(num-1)+h);
      for(Card c: playerHand)
      {
         g.drawImage(side,xpos,ypos,w,h,null);
         if(xtra > 0)
         {
            ypos += cardDist + 1;
            xtra--;
         }
         else
         {
            ypos += cardDist;
         }
      }
      g.dispose();
   }
   //g.fillRect(880,200,120,400);
   public void fillRight(int index)
   {
      Graphics g = panel.getGraphics();
      ArrayList<Card> playerHand = play.get(index).hand;
      int num = playerHand.size();
      int h = 120;
      int w = (int)(h * (1/.7225));
      int xpos = 1000 - w-2;
      int ypos = 400;
      int cardDist = h;
      if(400 < (num*(h-4)) && num > 1)
      {
         ypos = 200;
         int extraPix = 400-((cardDist*(num-1))+h);
         if(extraPix > 0)
         {
            ypos += (int)(extraPix/2);
         }
         cardDist = ((400-h)/(num-1));
      }
      else
      {
         ypos -= (h/2)*num;
      }
      int xtra = 400 - (cardDist*(num-1)+h);
      for(Card c: playerHand)
      {
         g.drawImage(side,xpos,ypos,w,h,null);
         if(xtra > 0)
         {
            ypos += cardDist + 1;
            xtra--;
         }
         else
         {
            ypos += cardDist;
         }
      }
      g.dispose();
   }
   public void fillMid(int index)
   {
      Graphics g = panel.getGraphics();
      ArrayList<Card> playerHand = play.get(index).hand;
      int num = playerHand.size();
      int xpos = 500;
      int h = 140;
      int w = (int)(h * .7225);
      int ypos = 0;
      int cardDist = w;
      if(400 < (num*(w-4)) && num > 1)
      {
         xpos = 300;
         int extraPix = 400-((cardDist*(num-1))+w);
         if(extraPix > 0)
         {
            xpos += (int)(extraPix/2);
         }
         cardDist = ((400-w)/(num-1));
      }
      else
      {
         xpos -= (w/2)*num;
      }
      int xtra = 400 - (cardDist*(num-1)+w);
      for(Card c: playerHand)
      {
         g.drawImage(back,xpos,ypos,w,h,null);
         if(xtra > 0)
         {
            xpos += cardDist + 1;
            xtra--;
         }
         else
         {
            xpos += cardDist;
         }
      }
      g.dispose();
   }
   /**
    * Updates the piles on the GUI
    * @param c the card at the top of the discard pile.
    */
   public void drawPiles(Card c)
   {
      top = c;
      Graphics g = panel.getGraphics();
      BufferedImage bim = back;
      String disSuit = c.suit.substring(0,1).toLowerCase();
      BufferedImage dis = cardSprites[suitToInt(disSuit)][c.value];
      int height = 180;
      g.drawImage(bim,360,270,(int)(height*.7225),height,null);
      g.drawImage(dis,510,270,(int)(height*.7225),height,null);
      g.dispose();
   }
   public int suitToInt(String s)
   {
      switch(s){
         case "c": return 0;
         case "d": return 1;
         case "h": return 2;
         case "s": return 3;
         default: return 0;
      }
   }
   public void paint(Graphics g)
   {
      super.paint(g);
      Graphics x = panel.getGraphics();
      x.setColor(Color.GREEN);
      x.fillRect(0,0,panel.getWidth(),panel.getHeight());
      drawCards(play);
      drawPiles(top);
   }
}