package com.nc.blackjack.test;

import com.nc.blackjack.Card;
import org.junit.Test;
import org.junit.Assert;

import com.nc.blackjack.Card.SUIT;
import com.nc.blackjack.Card.RANK;

/**
 * Created by patrick.sharkey on 2/8/2016.
 */
public class CardTest {

    @Test
    public void testSuitEnum() {
        Assert.assertEquals(SUIT.CLUB.toString(), "C");
    }

    @Test
    public void testRankEnum() {
        Assert.assertEquals(RANK.ACE.toString(), "A");
        Assert.assertNotEquals(RANK.ACE.getValue(), 11);
        Assert.assertEquals(RANK.ACE.getValue(), 1);
        Assert.assertEquals(RANK.QUEEN.toString(), "Q");
        Assert.assertEquals(RANK.QUEEN.name(), "QUEEN");
        Assert.assertEquals(RANK.QUEEN.getValue(), 10);
    }

    @Test
    public void testCardConstructor() {

        Card card = new Card(SUIT.SPADE, RANK.TEN);
        Assert.assertNotNull(card);
        Assert.assertEquals(card.getRank(), RANK.TEN);
        Assert.assertEquals(card.getSuit(), SUIT.SPADE);
    }

    @Test
    public void testCardToString() {
        Card card = new Card(SUIT.HEART, RANK.KING);
        String expected = "KH";
        Assert.assertEquals(card.toString(), expected);
    }
}
