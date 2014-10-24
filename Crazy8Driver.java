//package Crazy8;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.lang.IndexOutOfBoundsException;
import java.util.InputMismatchException;
import java.util.regex.Pattern;
//*********************************WARNING! WARNING! NOT COMPLETELY IMPLEMENTED!****************************//
//*********************************WARNING! WARNING! NOT COMPLETELY IMPLEMENTED!****************************//
//*********************************WARNING! WARNING! NOT COMPLETELY IMPLEMENTED!****************************//

public class Crazy8Driver
{
    public static final int MAXRM = 3;
    public static final int CHEAT = -2;
    public static void main(String[] args)
    {
        /* Here we'll instantiate a cache of of players for the program to choose from later on. There is a better way to declare a bunch of Players
         * in bulk, but right now we'll just do it right here before it gets too large.
         */
        ArrayList<Player> playerCache = Crazy8Driver.populatePlayerCache();
        
        /* Establish the player list that will be playing the game. This list draws from playerCache, and calls instantiatePlayers() to fill the list.
         * Uncommented due to testing and fills the list with Gul'dan and Thrall.
         */
        ArrayList<Player> players = new ArrayList<Player>();
        //players = instantiatePlayers(playerCache);
        players.add(playerCache.get(1)); players.add(playerCache.get(8)); // --- This line is for debugging
        
        // Let's make our new, randomly shuffled deck, using makeDeck().
        // Let's also deal cards from this deck into player hands.
        Deck deck = new Deck();
        ArrayList<ArrayList<Card>> hands = Crazy8Driver.dealCards(deck, players.size(), players);
 
        // The current pile of cards we'll be working with will be in the card stack.
        LStack<Card> cardStack = new LStack<Card>();
        
        // Tell the player what's going on, draw a card, and put it into play.
        System.out.println("You go first.");
        Card firstCard = deck.pop();
        System.out.println("The first card drawn is a(n) " + firstCard);
        cardStack.push(firstCard);        
        
        boolean exitGame = false;
        while(!exitGame)
        {
            Crazy8Driver.playerTurn(deck, players, cardStack);
            exitGame = true;
            Crazy8Driver.playerTurn(deck, players, cardStack);
        }
    }
    
    /**
     * This class should handle everything that goes on during a player's turn.
     * @param deck The deck we might be drawing from.
     * @param The list of players (their hands included) we're working with.
     * @param cardStack The growing stack of cards.
     */
    private static void playerTurn(LStack<Card> deck, ArrayList<Player> playerList, LStack<Card> cardStack)
    {
        // So we don't have to repeatedly call playerList.get(0) to refer to the player, let's just make a 
        // variable that does that for us.
        Player player = playerList.get(0);
        
        // The following using the format method of the String class to cleanly print out a spaced out message
        // that displays the top of the stack.
        System.out.println("\n\n\n\n\n\n");
        String currentSuit = Character.toUpperCase(cardStack.peek().suit.charAt(0)) + cardStack.peek().suit.substring(1);
        String outText = String.format("%-20s %-13s %-20s %-20s\n\n", " ", "Top of stack: ", cardStack.peek(), "Current suit: " + currentSuit + "s");
        System.out.println(outText);
        
        // Here we are scrolling through every card in the player's hand and printing it, using .format() to make sure
        // everything is spaced correctly.
        // Note on this format: The specific %-20s merely specifies a left-aligned message of 20 characters in length.
        //  So if the string we're passing in place of that argument is not 20 characters in length, the rest of the space
        //  will be filled in with whitespace.
        String handText = "";
        System.out.println("Cards left in deck: "+ deck.size+"\n");
        player.printHand();
        
        handText += "\n\n";
        System.out.println(handText);
        
        // Now we're going to print out the hand size of every player.
        // Hand size is formatted similarily to the hand above.
        String cardCountText = "";
        String isYou = " (You)"; // Print this first, and reset it. Will only display on the first iteration, which is the player.
        for (Player current : playerList)
        {
            String handCount = "Number of Cards: " + current.hand.size();
            cardCountText += String.format("%-15s    %-10s %-5s", current.name + isYou, handCount, " ");
            if((playerList.indexOf(current) + 1) % 2 == 0 && current != player) cardCountText += "\n"; // Print a new line for every two players.
            isYou = "";
        }
        System.out.println(cardCountText);
        
        // The following code handles user input. legalWords operates the exactly as it appears in .initializePlayers()
        // We take decision and break decision up into an array in case we need to examine the input in multiple parts or as a whole.
        // For example, the user may input "taunt Rexxar," which would mean a taunt directed at Rexxar. 
        // For input that will play cards, we'll use regex input.
        ArrayList<String> legalWords = new ArrayList<String>();
        legalWords.add("help"); legalWords.add("rules");
        legalWords.add("greeting"); legalWords.add("taunt");
        legalWords.add("forfeit"); legalWords.add("draw");
        legalWords.add("pass"); legalWords.add("discard");
        
        // When we need to decide the audience of the player's emotes, it's useful to store the each players' name in a list
        // so we can easily verify that an enterd name is a valid player.
        ArrayList<String> playerNames = new ArrayList<String>();
        for(Player character : playerList) playerNames.add(character.name);
        
        Scanner userIn = new Scanner(System.in);
        boolean decisionReached = false;
        String decision = "";
        String errorText = "Invalid input.\n"; // Print this out everytime the user enters bad input.
        // General note when using scanners that take input from System.in:
        //  If you use scanner.hasNext() when that scaner is taking input from System.in, unless it reads en EOF (Ctrl+D),
        //  .hasNext() will ALWAYS return true, because the input stream from System hasn't ended.
        while(!decisionReached)
        {
            // responseType is used to determine if the user is sending an emote, and if they are, to make sure
            // that the AI responds appropriately.
            String responseType = "none";
            decision = userIn.next();
            if(legalWords.contains(decision))
            {
                if(decision.equals("help") || decision.equals("rules"))
                {
                    // Call the helpMenu method.
                    Crazy8Driver.helpMenu();
                }
                else if(decision.equals("greeting"))
                {
                    responseType = "greeting";
                }
                else if(decision.equals("taunt"))
                {
                    responseType = "taunt";
                }
                else if(decision.equals("forfeit"))
                {
                    decisionReached = true;
                }
                else if(decision.equals("draw"))
                {
                    Card temp = deck.pop();
                    System.out.println(player.toString()+"(You) has drawn "+temp.toString()+".");
                    player.hand.add(temp);
                    player.printHand();
                }
                else if(decision.equals("pass"))
                {
                    // Flesh out this mechanic.
                    decisionReached = true;
                }
                else if(decision.equals("discard"))            //Caution: Under Construction
                {
                    if(!Crazy8Driver.discard(userIn, player, cardStack))
                    {
                        System.out.println("No cards were discarded.");
                    }
                    else
                    {
                        System.out.println("Ending turn.");
                        decisionReached = true;
                    }
                }
            }
            else
            {
                System.out.println(errorText);   
            }
            
            // Every iteration, we check to see if there is a response that should occur from the AI DURING the players' turn
            // This response, of course, only takes the form of dialogue. 
            if(!responseType.equals("none"))
            {
                // Make sure to determine an audience.
                System.out.println("To who?");
                decision = userIn.next();
                
                // If it is a legal input.
                if(playerNames.contains(decision))
                {
                    Player responder = null;
                    String audience = decision;
                    
                    // This loop iterates over the player list and finds the player that the user is sending an emote to. 
                    // responder is set equal to that player so we can easily refer to the player that we are working with.
                    // Note: We've already establish that the targeted audience IS in the game, so we won't have to worry
                    //       about not finding a matching input. The following if statements check to see if it's not null
                    //       because a compiler error will be thrown otherwise.
                    for(Player playerAI : playerList)
                    {
                        if(playerAI.name.equals(audience) && !audience.equals(player.name))
                            responder = playerAI;
                    }
                    
                    // Now we finally reply with an appropriate message.
                    if(responseType.equals("greeting") && responder != null)
                    {
                        Crazy8Driver.pacedDialogue(player.greetingText, player);
                        Crazy8Driver.pacedDialogue(responder.greetingText, responder);
                    }
                    else if(responseType.equals("taunt") && responder != null)
                    {
                        Crazy8Driver.pacedDialogue(player.tauntText, player);
                        Crazy8Driver.pacedDialogue(responder.tauntText, responder);
                    }
                }
                else
                {
                    System.out.println(errorText);
                }
            }
            responseType = "none";
        }
    }
    
    /**
     * @param userin The scanner to read from.
     * @param player Our player.
     * @param discardPile The growing discard pile.
     * @return If a card was discarded or not.
     *
     *  Things to work on for discard: 
     *     Checking the validity of THREE cards in one go.
     */
    private static boolean discard(Scanner userin, Player player, LStack<Card> discardPile)
    {
        Scanner line;
        boolean discarded = false;
        boolean[] valid = {false,false,false};           //Store validity of cards
        ArrayList<Integer> rmNum = new ArrayList<>();    //Indexes that will be removed from hand.
        int cardsRemoved = 0, j = 0, count = 0, chosenCard = -1;
        Card disCard = new Card(0,"A");

        //while(!discarded); //Needs to be implemented to wait until cards are discarded.
        System.out.println("\nEnter the number for each card you would like to discard(max: 3).\nEnter any character to stop.");
        //Asks for index input and stores it into an ArrayList.
        
        while(count < MAXRM)
        {
            if(userin.hasNextInt() && cardsRemoved < MAXRM)
            {
               rmNum.add(userin.nextInt()-1);
               count++;
            }
            else
            {
               System.out.println("Ending 'discard' input...");
               break;
            }
        }
        
        /* 
         * This section responds to the input indexes and sets the validity of the cards in
         * 'valid' array.
         * CHEAT = -2, and it auto-plays the first card in your hand.
         */
        for(int i = 0; i < rmNum.size(); i++)
        {
            disCard = (rmNum.get(i) == CHEAT)? player.hand.get(0) : player.hand.get(rmNum.get(i));
            
            if(rmNum.get(i) == CHEAT)
            {
                System.out.println("You're cheating!!! Discarding "+player.hand.get(i).toString());
                valid[i] = discarded = true;
            }
            else if(rmNum.get(i) >= player.hand.size() || rmNum.get(i) < 0)
            {
                System.out.println("\n'" + disCard + "' not a valid card in your hand.");
                System.out.println("Will end after 3 cards max have been discarded. \n\n\n");
                valid[i] = false;
            }
            else
            {
               if((discardPile.peek().validPlay(disCard) && cardsRemoved == 0)|| (cardsRemoved > 0 && discardPile.peek().sameRank(player.hand, count)))
               {
                   System.out.println("Discarding a "+player.hand.get(rmNum.get(i)).toString());
                   valid[i] = discarded = true;
               }
               else
               {
                   System.out.println("Cannot play "+player.hand.get(rmNum.get(i)).toString()+" from your hand. Moving on...");
                   valid[i] = false;
               }
            }
        }
        //Now, we remove all of the valid cards from the hand.
        for(int i = 0; i < rmNum.size(); i++)
        {
            int offset = 0;
            if(valid[i])
            {
                if(rmNum.get(i) == CHEAT)
                   discardPile.push(player.hand.remove(0));
                else
                {
                   if(i > 0 && rmNum.get(i-1) < rmNum.get(i)) offset += 1;    //Increase offset if removing cards from a lower index.
                   if(i > 1 && rmNum.get(i-2) < rmNum.get(i)) offset += 1;
                   discardPile.push(player.hand.remove((int)rmNum.get(i)-offset));
                }
            }
        }
        //for(int ooga = 0; ooga < player.hand.size(); ooga++)
        //{
        //     System.out.println((ooga+1)+". "+player.hand.get(ooga).toString());
        //}
        System.out.println("Discard phase complete.");
        userin.nextLine();
        return discarded;
    }
    
    /**
     * pacedDialogue is a method we use that enters input and then tells the computer to wait. This makes it so that the player can notice and process
     * the dialogue, instead of it all happening at once. Usually, .pacedDialogue() will be followed another call to this method, resembling some
     * artificial pause in speech.
     * @param dialogue The string to print out.
     * @param character The speaker for this line of dialogue.
     */
    private static void pacedDialogue(String dialogue, Player character)
    {
        System.out.print(character + ": " + dialogue + "\n\n");
        try 
        {
            Thread.sleep(1000);
        } 
        catch (Exception e)
        {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Print out and handle user input for a series of interactive menus that offer rules, context, and assistance to players.
     */
    private static void helpMenu()
    {
        String menuText = String.format("\n\n This is the card game, Crazy 8's. \n Here is a table of possible inputs:\n");
        menuText += String.format("%-10s %-20s\n", "rules", "For the rules of Crazy 8.");
        menuText += String.format("%-10s %-20s\n", "emotes", "How to use emotes in this game.");
        menuText += String.format("%-10s %-20s\n", "[misc]", "View a list of all legal commands.");
        menuText += String.format("%-10s %-20s\n", "exit", "To exit this menu.");
        menuText += String.format("%-10s %-20s\n", "menu", "Print this menu again.");
        System.out.println(menuText);
        ArrayList<String> legalWords = new ArrayList<String>();
        legalWords.add("rules"); legalWords.add("emotes"); legalWords.add("exit");
        Scanner userin = new Scanner(System.in);
        String decision = "";
        while(!decision.equals("exit"))
        {
            System.out.println("\n\n\n");
            decision = userin.next();
            if(decision.equals("rules"))
            {
                System.out.println("\n\nThe premise of Crazy 8s is to empty your hand before your opponents, the first to do so wins. \n"
                                 + "* You may only discard a card when it matches either the rank or suit of the card at the top of the pile. \n"
                                 + "\t To do this, enter: 'discard' followed by the card you would like to discard in its shorthand form.\n"
                                 + "\t For example, a 6 of Heart's shorthand would be a 6h. An Ace of diamonds would be Ad.\n"
                                 + "* You may discard several cards of the same rank, all at once. To do this, follow the input:\n"
                                 + "\t discard 4c 4d 4h"
                                 + "\t The shorthands must be seperated by spaces. The last card in the list will be the card at the top of the discard pile.\n"
                                 + "* You may play an 8 at any time. When an 8 is played, you can change the suit that the other plays must match.\n"
                                 + "\t If the first card to be discarded from the deck is an 8, any suit may be played.\n"
                                 + "* If you have no possible cards to discard, you must draw from the pile. To do this, enter 'draw.'\n"
                                 + "\t You can draw as many times from the deck as you would like.\n"
                                 + "\t Once you may have drawn at least 3 cards, you may pass your turn.\n"
                                 + "\t If the deck has been exhausted, and you have no available cards to play, you may also pass your turn.\n");
            }
            else if(decision.equals("emotes"))
            {
                System.out.println("\nYou may greet or taunt your opponent. To do this, enter either 'greeting' or 'taunt.\n"
                                 + "You will be prompted who to greet or taunt, simply enter the name of your audience.");
            }
            else if(decision.equals("[misc]"))
            {
                System.out.println(String.format("\n%-10s %-30s", "discard", "Discard a number of cards onto the discard pile."));
                System.out.println(String.format("%-10s %-30s", "draw", "Draw a card from the deck."));
                System.out.println(String.format("%-10s %-30s", "pass", "Pass your turn to the next player."));
                System.out.println(String.format("%-10s %-30s", "greeting", "Say a greeting to the AI."));
                System.out.println(String.format("%-10s %-30s", "taunt", "Taunt the AI."));
                System.out.println(String.format("%-10s %-30s", "players", "List the players in the current game."));
                System.out.println(String.format("%-10s %-30s", "forfeit", "Forfeit the game."));
            }
            else if(decision.equals("menu")) System.out.println(menuText);
        }
            
    }
    
    /**
     * This class is used to offer the player input to select a character to play as, how many AI opponents they will face,
     * and randomly select their AI opponents.
     * @param playerCache The cache of players to take a character from.
     * @return A list of players for the game.
     */
    private static ArrayList<Player> instantiatePlayers(ArrayList<Player> playerCache)
    {
        // legalWords contains the names of all possible player choices. We will use this later
        // to determine legal user input when selecting a character.
        ArrayList<String> legalWords = new ArrayList<String>();
        System.out.println("To begin, please select a character to play as.");
        // menuText should be use as an easy way to print all possible choices to the player without
        // the need for a for loop to print them all out manually.
        String menuText = "\n\n";
        // Print out all the possible characters using a simple for : each loop.
        for(Player player : playerCache)
        {
            menuText += ((playerCache.indexOf(player) + 1) + ") " + player + "\n");
            legalWords.add(player.name);
        }
        
        Scanner playerChoice = new Scanner(System.in);
        int choiceID = 0; // Store the integer ID choice or
        String choiceName = ""; // a String if an int is not the input.
        Player playerOfChoice = null; // Store possible and the final characters in this variable. If it's not null by the end of the loop, the loop will exit.
        boolean kill = false;
        boolean kill2 = false;        
        while(!kill)
        {
            kill2 = false; // Make sure to reset kill2 every loop cycle.
            System.out.println(menuText);
            // The player can either select a character by their name or by the number they're associated with in menuText.
            // Regardless, take their input and determine the player's intention--if they mean to select by ID or name. If
            // by ID, this if statement handles, else handle it by name.
            if(playerChoice.hasNextInt())
            {
                choiceID = playerChoice.nextInt() - 1;
                // If we get an IndexOutOfBoundsException, the player entered illegal input, so we disregard it in the catch.
                try
                {
                    // We use playerOfChoice to handle even on final inputs so we can reuse variables, and so we can also
                    // give the player a moment to consider if they really want to play as the character they just selected.
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
                // Similar to the above if statement, but we're instead selecting by name. A simple .indexOf() method determines the
                // character we want to work with.
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
        System.out.println("\nSelected " + playerOfChoice); // Assure the player who they'll be playing as.
 
        // Now let's instantiate the player list, since we'll now be working with it. Add our player charact to the list (whose index will ALWAYS be 0 in the list)
        // and declare our numPlayers variable.
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(playerOfChoice);
        int numPlayers = 0;
        
        // Do they enter a number between 1 and 3? Accept it and exit the loop, if not, wait until they do.
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
        System.out.println("You'll play a " + numPlayers + " player game."); // Assure the player of how many opponents they will face.
        
        String versusText = "\n" + players.get(0); // Let's build this String, which informs the player of their opponents, now.
        for(int i = 0; i < numPlayers - 1;)
        {
            // Randomly select from the player cache an opponent. If the opponent has already been seleected (before in this loop or by the playeR)
            // reroll for a new character. This ensures that there will be no repeats in our player list.
            Random ran = new Random();
            int ranNum = ran.nextInt(playerCache.size());
            if(!players.contains(playerCache.get(ranNum)))
            {
                players.add(playerCache.get(ranNum));
                versusText += " vs. " + players.get(i + 1);
                i++;
            }
        }
        Crazy8Driver.pacedDialogue(versusText, new Player());
        
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
        // Instantiate a list of dealt hands. This list isn't as important as giving these actual hands to player characters
        // but we might need the list of hands for some reason.
        // Note: A hand's index within hands is shared by a player character in the players list. We can use this to go directly
        //  from a certain hand directly to that player.
        ArrayList<ArrayList<Card>> hands = new ArrayList<ArrayList<Card>>();
        for(int i = 0; i < players; i++) hands.add(new ArrayList<Card>()); // Make a new, empty list of cards for each player.
        for(int i = 0; i < 8; i++)
        {
            // Now we fill those hands from the top of the deck, which should already be randomized in makeDeck().
            // Because at most we'll be drawing 32 cards here, we don't need to worry about popping from an empty stack.
            for(ArrayList<Card> hand : hands)
            {
                hand.add(deck.pop());
            }
        }
        
        // And lastly, give every player a dealt hand.
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
    
    private static ArrayList<Player> populatePlayerCache()
    {
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
        
        return playerCache;
    }
}
