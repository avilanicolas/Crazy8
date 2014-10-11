package Crazy8;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.lang.IndexOutOfBoundsException;

public class Crazy8Driver
{
    public static void main(String[] args)
    {
        boolean exitGame = false;
        ArrayList<Player> playerCache = new ArrayList<Player>();
        playerCache.add(new Player("Anduin", "By the light!", "Greetings.", "The Light shall burn you!"));
        playerCache.add(new Player("Gul'dan", "Suffer!", "I greet you.", "Your soul shall suffer!"));
        playerCache.add(new Player("Valeera", "Watch. Your. Back.", "The pleasure is mine.", "I will be your death!"));
        playerCache.add(new Player("Uther", "I will fight with honor!", "Well met!", "Justice demands retribution!"));
        playerCache.add(new Player("Jaina", "You asked for it!", "Hello.", "My magic will tear you apart!"));
        playerCache.add(new Player("Malfurion", "I must protect the wilds!", "My greetings.", "Nature will rise against you!"));
        playerCache.add(new Player("Rexxar", "Let the hunt begin!", "Greetings, traveller.", "I wll hunt you down!"));
        playerCache.add(new Player("Garrosh", "Victory or death!", "Heh, greetings.", "I will crush you!"));
        playerCache.add(new Player("Thrall", "Elements guide me!", "Greetings, friend.", "The elements will destroy you!"));
        
        ArrayList<Player> players = new ArrayList<Player>();
        //players = instantiatePlayers(playerCache);
        players.add(playerCache.get(1)); players.add(playerCache.get(8));
        
        // Let's make our new, randomly shuffled deck.
        LStack<Card> deck = Crazy8Driver.makeDeck();
        ArrayList<ArrayList<Card>> hands = Crazy8Driver.dealCards(deck, 2, players);
        
        LStack<Card> cardStack = new LStack<Card>();
        System.out.println("You go first.");
        Card firstCard = deck.pop();
        System.out.println("The first card drawn is a(n) " + firstCard);
        cardStack.push(firstCard);        
        
        while(!exitGame)
        {
            Crazy8Driver.playerTurn(deck, players, cardStack);
            exitGame = true;
        }
    }
    
    private static void playerTurn(LStack<Card> deck, ArrayList<Player> playerList, LStack<Card> cardStack)
    {
        System.out.println("\n\t\t" + cardStack.peek() + "\n");
        String handText = "";
        for(Card card : playerList.get(0).hand) handText += card + " ";
        System.out.println(handText);
    }
    
    private static ArrayList<Player> instantiatePlayers(ArrayList<Player> playerCache)
    {
        ArrayList<String> legalWords = new ArrayList<String>();
        boolean kill = false;
        boolean kill2 = false;
        System.out.println("To begin, please select a character to play as.");
        String menuText = "\n\n";
        for(Player player : playerCache)
        {
            menuText += ((playerCache.indexOf(player) + 1) + ") " + player + "\n");
            legalWords.add(player.name);
        }
        
        Scanner playerChoice = new Scanner(System.in);
        int choiceID = 0;
        String choiceName = "";
        Player playerOfChoice = null;
        while(!kill)
        {
            kill2 = false;
            System.out.println(menuText);
            if(playerChoice.hasNextInt())
            {
                choiceID = playerChoice.nextInt() - 1;
                try
                {
                    playerOfChoice = playerCache.get(choiceID);
                    while(!kill2)
                    {
                        System.out.println("\n " + playerOfChoice);
                        System.out.println("Flavor text will go here.");
                        System.out.println("Maybe some other stuff too?");
                        System.out.println("Enter 'yes' to select this character, enter 'no' to go back to selection.\n");
                        choiceName = playerChoice.next();
                        if(choiceName.equals("yes")) kill2 = true;
                        else if(choiceName.equals("no"))
                        {
                            kill2 = true;
                            playerOfChoice = null;
                        }
                    }
                }
                catch(IndexOutOfBoundsException e)
                {
                    System.out.println("Invalid character ID.");
                }
            }
            else if(playerChoice.hasNext())
            {
                choiceName = playerChoice.next();
                if(legalWords.contains(choiceName))
                {
                    int charIndex = legalWords.indexOf(choiceName);
                    playerOfChoice = playerCache.get(charIndex);
                    while(!kill2)
                    {
                        System.out.println("\n " + playerOfChoice);
                        System.out.println("Flavor text will go here.");
                        System.out.println("Maybe some other stuff too?");
                        System.out.println("Enter 'yes' to select this character, enter 'no' to go back to selection.\n");
                        choiceName = playerChoice.next();
                        if(choiceName.equals("yes")) kill2 = true;
                        else if(choiceName.equals("no"))
                        {
                            kill2 = true;
                            playerOfChoice = null;
                        }
                    } 
                }
            }
            if(playerOfChoice != null) kill = true;
        }
        System.out.println("\nSelected " + playerOfChoice);
        
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(playerOfChoice);
        int numPlayers = 0;
        
        System.out.println("\n How many other players would you like to face?");
        System.out.println("1 Other player.");
        System.out.println("2 Other players.");
        System.out.println("3 Other players.");
        kill = false;
        while(!kill)
        {
            if(playerChoice.hasNextInt())
            {
                choiceID = playerChoice.nextInt();
                if(choiceID >= 1 && choiceID <= 3)
                {
                    numPlayers = 1 + choiceID;
                    kill = true;
                }
            }
        }
        System.out.println("You'll play a " + numPlayers + " player game.");
        
        String versusText = "\n" + players.get(0);
        for(int i = 0; i < numPlayers - 1;)
        {
            Random ran = new Random();
            int ranNum = ran.nextInt(playerCache.size());
            if(!players.contains(playerCache.get(ranNum)))
            {
                players.add(playerCache.get(ranNum));
                versusText += " vs. " + players.get(i + 1);
                i++;
            }
        }
        System.out.println(versusText);
        
        return players;
    }
    
    /**
     * @param deck A collection of cards to randomly deal cards from.
     * @param players The number of players to deal a hand to.
     * @param playerList The list of players to deal cards to.
     * @return A collection of hands filled with 8 cards from the deck.
     * 
     * @precondition That the deck passed into is greater than the number of players times eight.
     * @postcondition That the deck passed into has decreased in size by a multiple of eight.
     */
    private static ArrayList<ArrayList<Card>> dealCards(LStack<Card> deck, int players, ArrayList<Player> playerList)
    {
        ArrayList<ArrayList<Card>> hands = new ArrayList<ArrayList<Card>>();
        for(int i = 0; i < players; i++) hands.add(new ArrayList<Card>());
        for(int i = 0; i < 8; i++)
        {
            for(ArrayList<Card> hand : hands)
            {
                hand.add(deck.pop());
            }
        }
        
        for(int i = 0; i < players; i++) playerList.get(i).hand = hands.get(i);
        return hands;
    }
    
    /**
     * @return A new, randomly filled deck of the four suits, 1 through Ace.
     */
    private static LStack<Card> makeDeck()
    {
        // First we need to establish the cards we are using. It's easier to just load the cards
        // into suits, and then randomly pick from within the suits when we're filling the deck.
        // Here, we're just declaring lists of Cards, and filling them sequentially with 14 cards.
        LStack<Card> deck = new LStack<Card>();
        ArrayList<Card> diamonds = new ArrayList<Card>();
        ArrayList<Card> hearts = new ArrayList<Card>();
        ArrayList<Card> clubs = new ArrayList<Card>();
        ArrayList<Card> spades = new ArrayList<Card>();
        for(int i = 2; i <= 14; i++) diamonds.add(new Card(i, "diamond"));
        for(int i = 2; i <= 14; i++) hearts.add(new Card(i, "heart"));
        for(int i = 2; i <= 14; i++) clubs.add(new Card(i, "club"));
        for(int i = 2; i <= 14; i++) spades.add(new Card(i, "spade"));
        
        // Now we need to randomly select from the above suits and add each one to the deck
        // removing from their suit's list. We do this until all suits are empty.
        boolean suitsUsed = false; // If all suits are used up, exit the loop.
        Random ran = new Random();
        int ranNum = 0; // This determines the random index from the suit list we'll take from.
        int ranSuit = 0; // This determines the random suit we'll select.
        // We'll store the suits that are currently empty, so we have a nice collection of empty suits
        // as they are used up.
        ArrayList<Integer> emptySuits = new ArrayList<Integer>();
        while(!suitsUsed)
        {
            // Here, we declare which suit we'll be working with -- however, what if we recieve a 
            // random integer for a matching suit that is EMPTY? The following while loop examines
            // what we get, and makes sure we only progress until we know we'll be working with a suit
            // that is not empty.
            ranSuit = ran.nextInt(4);
            while(emptySuits.contains(ranSuit)) ranSuit = ran.nextInt(4);
            
            // Here, suit will be pointing to one of the non-empty declared suits above. This way,
            // we don't need to hardcode for each suit, but we can just refer to the suit we're
            // working with as "suit."
            ArrayList<Card> suit = new ArrayList<Card>();
            switch(ranSuit)
            {
                case 0:
                    suit = diamonds;
                    break;
                case 1:
                    suit = hearts;
                    break;
                case 2:
                    suit = clubs;
                    break;
                case 3:
                    suit = spades;
                    break;
            }
            
            // Currently, if we randomly select an empty suit that hasn't been added to emptySuits,
            // this is where we'd add that suit to emptySuits. 
            //
            // The first else condition is the portion of the method that actually adds a randomly
            // selected card, and pushes that card into LStack deck. Once we do that, we're then sure
            // to remove that card from its suit.
            //
            // The final else statement flips the kill condition "suitsUsed" once every suit is emptied.
            if(suit.size() == 0) emptySuits.add(ranSuit);
            else if(suit.size() != 0)
            {
                // We declare ranNum to be a number from 0 to the size of the current suit we're working with.
                // This size decreases overtime as we remove cards from that particular suit, so in this way,
                // we can randomly select a card from that decreasing list.
                ranNum = ran.nextInt(suit.size());
                deck.push(suit.get(ranNum));
                suit.remove(ranNum);
            }
            if(diamonds.size() == 0 && hearts.size() == 0 && clubs.size() == 0 && spades.size() == 0)
                suitsUsed = true;
        }
        return deck;
    }
}
