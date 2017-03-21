package com.nc.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.nc.blackjack.Hand.RESULT;
import com.nc.blackjack.Player.TYPE;
import com.nc.blackjack.com.nc.blackjack.exception.DeckException;

/**
 * <h1>Game</h1>
 * Game represents a single game of blackjack
 * <br>
 * A game contains players stored in a list of {@link Player}, a
 * dealer and a dekc of cards stored in a {@link Deck}
 *
 * @author Patrick Sharkey
 * @version 1.0
 * @since 1.0
 */
public class Game {

    //variable declarations
    private List<Player> players;
    private Player dealer;
    private Deck deck;

    /**
     * Constructor - constructs an new game by initializing
     * a dealer, creating a new deck, and shuffling the deck.
     */
    public Game() {
        players = new ArrayList<Player>();
        dealer = new Player(Player.DEALER_USER, Player.TYPE.DEALER);
        deck = new Deck();
        deck.shuffleDeck();
    }

    /**
     * Reset the current game. All player and dealer hands will be removed
     * and a new deck will be created and shuffled.
     */
    public void reset() {

        //reset the players and dealers' hands
        resetPlayers(players);
        dealer.resetHands();
        deck = new Deck();
        deck.shuffleDeck();
    }


    /**
     * Add a player to the game with the specified username
     * @param username the username of the player to add
     */
    public void addPlayer(String username) {

        Player player = new Player(username, TYPE.PLAYER);
        players.add(player);
    }

    /**
     * Reset all hands of the players in the specified List
     * of players.
     * @param players the list of players that contain the hands
     *                to be removed
     */
    private void resetPlayers(List<Player> players) {
        for(Player p : players ) {
            p.resetHands();
        }
    }

    /**
     * Deal the initial two cards to the players and the dealer.
     * Players will always be dealt first.
     * @throws DeckException exception thrown when deck is not in
     * valid state
     */
    public void dealCards() throws DeckException {

        //loop through all players
        for (Player p : players) {

            //loop through each of the players hands
            // ** NOTE there will only ever be one hand per player for this version **
            for (Hand h : p.getHands()) {

                //deal two cards for each player
                h.insertCard(deck.dealNextCard());
                h.insertCard(deck.dealNextCard());
            }
        }

        assert (dealer.getHands().size() == 1); //assert that the dealer only has one hand

        //deal the dealer two cards last
        Hand dHand = dealer.getHands().get(0);
        dHand.insertCard(deck.dealNextCard());
        dHand.insertCard(deck.dealNextCard());
    }

    /**
     * Prompts all players in game to make a decision based on their
     * current hand or hands.
     * @param sc the Scanner object for user input
     * @throws DeckException exception thrown when deck is not in
     * valid state
     */
    public void playerDecision(Scanner sc) throws DeckException {

        //loop through all players and each hand
        for(Player p : players ) {
            for(Hand h : p.getHands() ) {

                //Show the current state of both the player and dealers hand. Dealers hand will have one card hidden as it is dealt face down
                System.out.print("\nPlayer " + p.getUsername() + " is showing [" + h.toString() + "] Total[" + h.currentHandStateTotal()
                                + "] Dealer is showing [" + dealer.getHands().get(0).toStringHidden() + "]");

                //let player make next hand decision
                nextDecision(p.getUsername(), h, sc);
            }
        }
    }

    /**
     * Execute the dealer turn.
     * <br>
     * Rules for dealear as follows
     * <ul>
     *     <li>The dealer must hit if the cards total less than 17 and stand otherwise</li>
     * </ul>
     *
     * @param sc the Scanner object for user input
     * @throws DeckException exception thrown when deck is not in
     * valid state
     */
    public void dealerTurn(Scanner sc) throws DeckException {

        //get the current dealers hand
        Hand h = dealer.getHands().get(0);

        System.out.print("\nDealer is showing [" + h.toString() + "]");

        //check if dealer should hit or Stand
        if( isDealerHit(h.getMinHandValue(), h.getMaxHandValue()) ) {

            Card c = hit(h);
            System.out.print("\nDealer must HIT. Dealer is dealt [" + c.toString() + "]");
            dealerTurn(sc);

        } else {
            //calculate optimal dealer hand value
            h.setOptimalHandValue();
            System.out.print("\nDealer must STAND with [" + h.toString() + "] Total[" + h.getHandValue() + "]");
            return;
        }
    }

    /**
     * Determine if the dealer should hit or stand based on min
     * and max hand values
     * <ul>
     *     <li>Dealer will hit if the cards total less than 17</li>
     *     <li>If Dealer is dealt an A it will always count as 11 unless
     *         future cards make total greater than 21
     *     </li>
     * </ul>
     *
     * @param minHandValue the min value of the hand
     * @param maxHandValue the max value of the hand
     * @return true if dealer should hit, false otherwise
     */
    public boolean isDealerHit(int minHandValue, int maxHandValue) {

        //if max hand value is a bust use min hand value
        if(maxHandValue > Hand.BLACKJACK) {

            //stand when hand value is 17 or greater
            if(minHandValue >= Hand.DEALER_STAND) {
                return false;
            }
            return true;
        }

        //always use max hand value to ensure A counts as 11
        //stand on 17 or greater.
        if(maxHandValue >= Hand.DEALER_STAND) {
            return false;
        }
        return true;
    }

    /**
     * Check if the current hand of the user has blackjack or busted. If not,
     * it will prompt user to either hit or stand.
     * @param username the username of the player who owns the hand
     * @param hand the hand that is currently being played
     * @param sc the Scanner object for user input
     */
    private void nextDecision(String username, Hand hand, Scanner sc) throws DeckException {

        //check for BLACKJACK and BUST
        if( Hand.isBlackjack(hand.getMaxHandValue()) ) {
            hand.setOptimalHandValue();
            System.out.println("\nPlayer " + username + " must STAND with [" + hand.toString() + "] Total[" + hand.getHandValue() + "]");
            return;
        }
        else if( Hand.isBust(hand.getMinHandValue()) ) {
            hand.setOptimalHandValue();
            System.out.println("\nPlayer " + username + " must STAND with [" + hand.toString() + "] Total[" + hand.getHandValue() + "]");
            return;
        }

        //Show the current state of both the player and dealers hand. Dealers hand will have one card hidden as it is dealt face down
        System.out.print("\nPlayer " + username + " type h to HIT or s to STAND? [h/s]");

        String input = sc.nextLine(); //read in user input

        if(input.equalsIgnoreCase("h")) {

            //player decided to hit deal a new card to the hand
            Card c = hit(hand);
            System.out.print("\nPlayer " + username + " is dealt a [" + c.toString() + "]");

            //Show the current state of both the player and dealers hand. Dealers hand will have one card hidden as it is dealt face down
            System.out.print("\nPlayer " + username + " is showing [" + hand.toString() + "] Total[" + hand.currentHandStateTotal()
                    + "] Dealer is showing [" + dealer.getHands().get(0).toStringHidden() + "]");

            nextDecision(username, hand, sc); //get next decision
        }
        else if(input.equalsIgnoreCase("s")) {

            //player decide to stand do nothing calculate optimal hand value
            hand.setOptimalHandValue();
            System.out.println("Player " + username + " will STAND with [" + hand.toString() + "] Total[" + hand.getHandValue() + "]");
            return;
        }
        else {
            System.out.print("\nInvalid user input");
            nextDecision(username, hand, sc); //retry
        }
    }

    /**
     * Compute if each player has won or lost the current game
     */
    public void computeWinners() {

        //get the dealer hand
        Hand dHand = dealer.getHands().get(0);

        //loop through each player and each hand
        for(Player p: players ) {
            for(Hand h : p.getHands() ) {
                computeHandResult(p.getUsername(), h, dHand);
            }
        }

    }

    /**
     * Compute the final hand value and determine the winner between current player hand
     * and dealer hand. Player hand RESULT will be updated with final result of the hand.
     * <br>
     * Winner is determined based on the following rules:
     * <ul>
     *     <li>If both players bust, the dealer wins</li>
     *     <li>If both players have the same score, they tie</li>
     *     <li>If both players have 21 on their first two cards, in which case it is a tie</li>
     *     <li>If the dealer's hand is greater than the player hand, dealer wins</li>
     *     <li>If the players hand is greater than the dealer hand, player wins</li>
     * </ul>
     * @param username the username of the player who hand is being played
     * @param pHand the hand of the player being played
     * @param dHand the hand of the dealer being played
     */
    public void computeHandResult(String username, Hand pHand, Hand dHand) {

        //Save both hand values for comparison
        int pHandValue = pHand.getHandValue();
        int dHandValue = dHand.getHandValue();

        System.out.print("\n\nPlayer " + username + " is showing [" + pHand.toString() +
                "] Total[" + pHandValue + "]. Dealer is showing [" + dHand.toString()
                + "] Total[" + dHandValue + "]");

        //if both dealer and player get blackjack on initial deal they tie
        if( (pHand.getSize() == 2 && pHandValue == Hand.BLACKJACK) && (dHand.getSize() == 2  && dHandValue == Hand.BLACKJACK) ) {
            pHand.setResult(RESULT.TIE);
            System.out.println("\n\nRESULT: Player " + username + " TIES with the dealer");
            return;
        }
        //if player busts dealer wins
        else if(pHandValue > Hand.BLACKJACK) {
            pHand.setResult(RESULT.LOSS);
            System.out.println("\n\nRESULT: Player " + username + " BUSTED dealer wins");
            return;
        }
        //if player and dealer have the same hand value they tie
        else if(pHandValue == dHandValue ) {
            pHand.setResult(RESULT.TIE);
            System.out.println("\n\nRESULT: Player " + username + " TIES with the dealer");
            return;
        }
        //if player did not bust bust has a greater hand than the dealer or if the dealer busts and the player does not
        else if(pHandValue > dHandValue || dHandValue > Hand.BLACKJACK) {
            pHand.setResult(RESULT.WIN);
            System.out.println("\n\nRESULT: Congratulations " + username + " you WIN");
            return;
        }
        else {
            //dealer wins in all other cases
            pHand.setResult(RESULT.LOSS);
            System.out.println("\n\nRESULT: Player " + username + " LOSES to the dealer");
            return;
        }
    }

    /**
     *    
     * Return the next card off the top of the deck and
     * insert it into the hand
     * @param hand the hand that will add the next card
     * @return the next card in the deck
     * @throws DeckException exception thrown when deck is not in
     * valid state
     */
    private Card hit(Hand hand) throws DeckException {
            Card c = deck.dealNextCard();
            hand.insertCard(c);
            return c;
    }

    /**
     * @return the number of players in the game
     */
    public int getNumPlayers() { return players.size(); }
}
