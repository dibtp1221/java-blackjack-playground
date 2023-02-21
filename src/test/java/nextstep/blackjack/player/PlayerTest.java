package nextstep.blackjack.player;

import nextstep.blackjack.card.Card;
import nextstep.blackjack.card.CardNumber;
import nextstep.blackjack.card.Shape;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void countTotal() {
        Player pobi = new Player("pobi", 10000);
        pobi.addCard(new Card(CardNumber.K, Shape.HEART));
        pobi.addCard(new Card(CardNumber.SEVEN, Shape.DIAMOND));
        assertEquals(17, pobi.countTotal());

        Player jason = new Player("jason", 10000);
        jason.addCard(new Card(CardNumber.A, Shape.HEART));
        jason.addCard(new Card(CardNumber.K, Shape.DIAMOND));
        assertEquals(21, jason.countTotal());
    }

    @Test
    void isNotOver21() {
        Player pobi = new Player("pobi", 10000);
        pobi.addCard(new Card(CardNumber.K, Shape.HEART));
        pobi.addCard(new Card(CardNumber.SEVEN, Shape.DIAMOND));
        assertTrue(pobi.isNotOver21());

        Player jason = new Player("jason", 10000);
        jason.addCard(new Card(CardNumber.A, Shape.HEART));
        jason.addCard(new Card(CardNumber.K, Shape.DIAMOND));
        assertTrue(jason.isNotOver21());
    }

    @Test
    void isOver21() {
        Player pobi = new Player("pobi", 10000);
        pobi.addCard(new Card(CardNumber.K, Shape.HEART));
        pobi.addCard(new Card(CardNumber.SEVEN, Shape.DIAMOND));
        pobi.addCard(new Card(CardNumber.SEVEN, Shape.CLOVER));
        assertTrue(pobi.isOver21());
    }

    @Test
    void isBlackJack() {
        Player jason = new Player("jason", 10000);
        jason.addCard(new Card(CardNumber.A, Shape.HEART));
        jason.addCard(new Card(CardNumber.K, Shape.DIAMOND));
        assertTrue(jason.isBlackJack());
    }

    @Test
    void diffWith21() {
        Player pobi = new Player("pobi", 10000);
        pobi.addCard(new Card(CardNumber.K, Shape.HEART));
        pobi.addCard(new Card(CardNumber.SEVEN, Shape.DIAMOND));
        assertEquals(4, pobi.diffWith21());

        Player jason = new Player("jason", 10000);
        jason.addCard(new Card(CardNumber.A, Shape.HEART));
        jason.addCard(new Card(CardNumber.K, Shape.DIAMOND));
        assertEquals(0, jason.diffWith21());
    }

    @Test
    void showCards() {
        Player jason = new Player("jason", 10000);
        jason.addCard(new Card(CardNumber.A, Shape.HEART));
        jason.addCard(new Card(CardNumber.K, Shape.DIAMOND));
        assertEquals("jason카드: A하트, K다이아몬드", jason.showCards());
    }
}