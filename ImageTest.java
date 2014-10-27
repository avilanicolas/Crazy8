import java.util.Random;
import java.util.ArrayList;
public class ImageTest
{
   public static void main(String[] args)
   {
      Card card = new Card(0,"c");
      Player p = new Player();
      Random r = new Random();
      ArrayList<Card> al = new ArrayList<Card>();
      for(int i = 0; i < 25; i++)
      {
         String s = "";
         switch(r.nextInt(4))
         {
            case 0: s = "c"; break;
            case 1: s = "d"; break;
            case 2: s = "h"; break;
            case 3: s = "s"; break;
            default: s = "c"; break;
         }
         al.add(new Card(r.nextInt(14),s));
      }
      p.hand = al;
      CrazyEightGUI yes = new CrazyEightGUI(card,p);
      Card top = new Card(13,"h");
      yes.drawPiles(top);
      al.remove(3);
      al.remove(4);
      al.remove(5);
      al.remove(6);
      al.remove(7);
      al.remove(8);
      p.hand = al;
      ArrayList<Player> alp = new ArrayList<Player>();
      alp.add(p);
      yes.update(alp,top);
   }
}