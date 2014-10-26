//package Crazy8;
import java.util.*;
import java.io.*;

public class DeckTest
{
   public static void main(String[] args)
   {
      Deck deck1 = new Deck();
	   System.out.println(""+ deck1.pop().toString());
	   System.out.println(""+ deck1.pop().toString());
	   System.out.println(""+ deck1.pop().toString());
	   System.out.println(""+ deck1.peek().toString());
	   deck1.shuffle();
      System.out.println("Shuffling cards...");
	   System.out.println(""+ deck1.peek().toString());
	   System.out.println(""+ deck1.pop().toString());
      System.out.println("^ These cards should be the same.");
	   System.out.println(""+ deck1.pop().toString());
	   System.out.println(""+ deck1.pop().toString());
   }
}