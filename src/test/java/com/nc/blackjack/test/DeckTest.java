package com.nc.blackjack.test;

import com.nc.blackjack.Card;
import com.nc.blackjack.Deck;
import com.nc.blackjack.com.nc.blackjack.exception.DeckException;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

/**
 * Created by patrick.sharkey on 2/8/2016.
 */
public class DeckTest {

    public String[] expectedDeck;

    @Before
    public void initExpectedDeck() {
        expectedDeck = new String[]{"AH","AC","AD","AS","2H","2C","2D","2S","3H","3C","3D","3S",
                "4H","4C","4D","4S","5H","5C","5D","5S","6H","6C","6D","6S","7H","7C","7D","7S","8H","8C",
                "8D","8S","9H","9C","9D","9S","10H","10C","10D","10S","JH","JC","JD","JS","QH","QC","QD",
                "QS","KH","KC","KD","KS"};
    }

    @Test
    public void testDeckConstructor() {

        Deck deck = new Deck();
        Assert.assertNotNull(deck);
        Assert.assertNotNull(deck.getDeck());
        Assert.assertEquals(deck.getSize(), Deck.FULL_DECK_SIZE);
    }

    @Test
    public void testDeckIsComplete() {
        Deck deck = new Deck();
        Assert.assertNotNull(deck);
        Assert.assertEquals(deck.getSize(), expectedDeck.length);

        //each element should have a matching string representation
        for(int i = 0; i < deck.getSize(); i++ ) {
            Assert.assertEquals(deck.getDeck().get(i).toString(), expectedDeck[i]);
        }
    }

    @Test
    public void testDealNextCard() throws DeckException {
        Deck deck = new Deck();
        deck.shuffleDeck();

        //validate deck has 52 cards
        Assert.assertEquals(deck.getSize(), Deck.FULL_DECK_SIZE);

        //deal card and check deck size has decreased by one
        Card card = deck.dealNextCard();
        Assert.assertEquals(deck.getSize(), (Deck.FULL_DECK_SIZE - 1));

        //loop through all the cards in the deck and ensure that card does not exist
        for(Card c : deck.getDeck()) {
            Assert.assertFalse(c.equals(card));
        }
    }

    @Test
    public void testToString() {

        Deck deck = new Deck();
        StringBuilder sb = new StringBuilder();

        //build the expected string
        String delimiter = "";
        for(String s : expectedDeck) {
            sb.append(delimiter + s.toString());
            delimiter = ",";
        }

        //because the deck as not been shuffled, we know the expected output
        Assert.assertEquals(deck.toString(), sb.toString());
    }

    @Test(expected=DeckException.class)
    public void testRemoveOnEmptyDeck() throws DeckException {

        Deck deck = new Deck();
        deck.shuffleDeck();

        //validate deck has 52 cards
        Assert.assertEquals(deck.getSize(), Deck.FULL_DECK_SIZE);

        //remove one more card than the deck has
        for(int i = 0; i < (Deck.FULL_DECK_SIZE + 1); i++ ) {
            deck.dealNextCard();
        }
    }
}
