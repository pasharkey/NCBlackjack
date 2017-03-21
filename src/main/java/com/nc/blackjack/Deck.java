package com.nc.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.nc.blackjack.Card.SUIT;
import com.nc.blackjack.Card.RANK;
import com.nc.blackjack.com.nc.blackjack.exception.DeckException;

/**
 * <h1>Deck</h1>
 * Deck represents a standard deck of playing cards. Each deck will
 * contain 52 unique {@link Card} objects.
 *
 * @author Patrick Sharkey
 * @version 1.0
 * @since 1.0
 */
public class Deck {

    //variable declarations
    public static final int FULL_DECK_SIZE = 52;  //standard playing deck size
    private List<Card> deck;

    /**
     * Constructor - constructs a Deck initialized containing
     * 52 {@link Card} objects.
     */
    public Deck() {
        deck = new ArrayList<Card>();
        populateDeck();
    }

    /**
     * Create a new {@link Card} for each {@link RANK} and
     * {@link SUIT} and place add it to the deck.
     */
    private void populateDeck() {

        //loop through ranks and create card for each
        for(RANK rank : RANK.values() ) {

            //loop through suits and create a card for each
            for (SUIT suit : SUIT.values()) {

                //create a new Card and place in deck
                Card c = new Card(suit, rank);
                deck.add(c);
            }
        }

        assert(deck.size() == FULL_DECK_SIZE); //enforce a proper deck was created
    }

    /**
     * Randomly shuffle the deck of cards.
     */
    public void shuffleDeck() {

        /*
            loop through deck for each card generate random position in deck and swap
            card in current position with card in random position
         */
        for(int i = 0; i < deck.size(); i++ ) {

            int randIndex = randomDeckIndex();

            Card swap = deck.get(i);
            deck.set(i, deck.get(randIndex));
            deck.set(randIndex, swap);
        }
    }

    /**
     * @return return true if deck is empty, false otherwise
     */
    public boolean isEmpty() {
        return deck.isEmpty() ? true : false;
    }

    /**
     * Deal the next card from the top of the deck.
     * @return next card in the deck, or null if deck is empty
     * @throws DeckException exception thrown when deck is not in
     * valid state
     */
    public Card dealNextCard() throws DeckException {

        Card c = null;

        //validate the deck is not empty
        if(!deck.isEmpty()) {
            c = deck.remove(0);
        }
        else {
            //deck should never be empty
            throw new DeckException("The deck is currently empty");
        }
        return c;
    }

    /**
     * Generate a random number 1-52 for a random
     * location in the deck of cards
     * @return a int between 1 and 52
     */
    private int randomDeckIndex() {
        Random rand = new Random();
        return rand.nextInt(FULL_DECK_SIZE - 1) + 1;
    }

    /**
     * @return a comma delimited string representation of
     * the deck all the cards in a deck.
     */
    public String toString() {

        StringBuilder sb = new StringBuilder();
        String delimiter = "";

        for(Card c : getDeck() ) {
            sb.append(delimiter + c.toString() );
            delimiter = ",";
        }

        return sb.toString();
    }

    /**
     * @return the deck of cards
     */
    public List<Card> getDeck() { return deck; }
;
    /**
     * @return the size of the deck
     */
    public int getSize() { return deck.size(); }

}
