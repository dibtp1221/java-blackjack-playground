package nextstep.blackjack.card;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardDeckTest {

    @Test
    void getCards() {
        CardDeck cardDeck = new CardDeck();
        List<Card> cards = cardDeck.getCards();
        assertEquals(52, cards.size());
    }
}