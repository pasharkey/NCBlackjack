package com.nc.blackjack.test;

import com.nc.blackjack.Card;
import com.nc.blackjack.Hand;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by patrick.sharkey on 2/8/2016.
 */
public class HandTest {

    public Hand hand;
    public Card cardA, cardB, cardC, cardD, cardE;

    @Before
    public void initHandTest() {

        hand = new Hand();
        cardA = new Card(Card.SUIT.SPADE, Card.RANK.ACE);
        cardB = new Card(Card.SUIT.DIAMOND, Card.RANK.TEN);
        cardC = null;
        cardD = new Card(Card.SUIT.HEART, Card.RANK.THREE);
        cardE = new Card(Card.SUIT.CLUB, Card.RANK.KING);
    }

    @Test
    public void testHandConstructor() {

        Hand h = new Hand();
        Assert.assertNotNull(h);
        Assert.assertEquals(h.getResult(), Hand.RESULT.IN_PROGRESS);
        Assert.assertEquals(h.getSize(), 0);
        Assert.assertEquals(h.getHandValue(), 0);
        Assert.assertEquals(h.getMaxHandValue(), 0);
        Assert.assertEquals(h.getMinHandValue(), 0);
    }

    @Test
    public void testInsertCard() {

        //check insert works
        Assert.assertEquals(hand.getSize(), 0);
        hand.insertCard(cardA);
        hand.insertCard(cardB);
        Assert.assertEquals(hand.getSize(), 2);

        //check that null cards are not inserted
        hand.insertCard(cardC);
        Assert.assertEquals(hand.getSize(), 2);

        //check for min and max values
        Assert.assertEquals(hand.getMinHandValue(), 11);
        Assert.assertEquals(hand.getMaxHandValue(), 21);

        resetHand();

        //insert two low cards and confirm min and max are equal
        Assert.assertEquals(hand.getSize(), 0);
        hand.insertCard(cardB);
        hand.insertCard(cardD);
        Assert.assertEquals(hand.getMinHandValue(), hand.getMaxHandValue());

        resetHand();
    }

    @Test
    public void testSetOptimalHandValue() {

        //test case when min value is > 21
        hand.insertCard(cardA);  //{1/11}
        hand.insertCard(cardB);  //{10}
        hand.insertCard(cardD);  //{3}
        hand.insertCard(cardE);  //{K}

        Assert.assertEquals(hand.getMaxHandValue(),34);
        Assert.assertEquals(hand.getMinHandValue(),24);

        hand.setOptimalHandValue();
        Assert.assertEquals(hand.getHandValue(), 24);

        resetHand();

        //test the case when max value is > 21
        hand.insertCard(cardA); //{1/11}
        hand.insertCard(cardD); //{3}
        hand.insertCard(cardB); //{10}

        Assert.assertEquals(hand.getMaxHandValue(), 24);
        Assert.assertEquals(hand.getMinHandValue(), 14);

        hand.setOptimalHandValue();
        Assert.assertEquals(hand.getHandValue(), 14);

        resetHand();

        //test case when max and min are < 21
        hand.insertCard(cardA); //{1/11}
        hand.insertCard(cardD); //{3}

        Assert.assertEquals(hand.getMaxHandValue(), 14);
        Assert.assertEquals(hand.getMinHandValue(), 4);

        hand.setOptimalHandValue();
        Assert.assertEquals(hand.getHandValue(), 14);

        resetHand();
    }

    @Test
    public void testIsBlackJack() {

        int blackjack = 21;
        int bust = 22;
        int under = 20;

        Assert.assertTrue(hand.isBlackjack(blackjack));
        Assert.assertFalse(hand.isBlackjack(bust));
        Assert.assertFalse(hand.isBlackjack(under));
    }

    @Test
    public void testIsBust() {

        int blackjack = 21;
        int bust = 22;
        int under = 20;

        Assert.assertFalse(hand.isBust(blackjack));
        Assert.assertTrue(hand.isBust(bust));
        Assert.assertFalse(hand.isBust(under));
    }

    @Test
    public void testCurrentHandStateTotal() {

        //test when two possible options
        hand.insertCard(cardA); //{1/11}
        hand.insertCard(cardD); //{3}

        String expected = "4/14";
        Assert.assertEquals(hand.currentHandStateTotal(), expected);

        resetHand();

        //test when only one possible option
        hand.insertCard(cardA); //{1/11}
        hand.insertCard(cardD); //{3}
        hand.insertCard(cardE); //{10}

        expected = "14";
        Assert.assertEquals(hand.currentHandStateTotal(), expected);

        resetHand();

        //test when min and max are the equal
        hand.insertCard(cardD); //{3}
        hand.insertCard(cardE); //{10}

        expected = "13";
        Assert.assertEquals(hand.currentHandStateTotal(), expected);

        resetHand();
    }

    @Test
    public void testToString() {

        //test empty hand
        String expected = "";
        Assert.assertEquals(hand.toString(), "");

        //test hand of one
        expected = "AS";
        hand.insertCard(cardA);
        Assert.assertEquals(hand.toString(), expected);

        //test hand of two
        expected = "AS,10D";
        hand.insertCard(cardB);
        Assert.assertEquals(hand.toString(), expected);

        //test hand of three
        expected = "AS,10D,3H";
        hand.insertCard(cardD);
        Assert.assertEquals(hand.toString(), expected);

        resetHand();
    }

    @Test
    public void testToStringHidden() {

        //test empty hand
        String expected = "";
        Assert.assertEquals(hand.toStringHidden(), "");

        //test hand of one
        expected = "AS";
        hand.insertCard(cardA);
        Assert.assertEquals(hand.toStringHidden(), expected);

        //test hand of two
        expected = "AS,XX";
        hand.insertCard(cardB);
        Assert.assertEquals(hand.toStringHidden(), expected);

        //test hand of three
        expected = "AS,XX,XX";
        hand.insertCard(cardD);
        Assert.assertEquals(hand.toStringHidden(), expected);

        resetHand();

    }

    /**
     * Helper method to reset the hand
     */
    private void resetHand() {
        hand = new Hand();
    }
}
