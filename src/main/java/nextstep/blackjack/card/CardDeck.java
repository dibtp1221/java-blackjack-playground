package nextstep.blackjack.card;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CardDeck {

    private final List<Card> cards;
    private final Random random = new Random();

    public CardDeck() {
        cards = Arrays.stream(Shape.values()).flatMap(shape -> Arrays.stream(CardNumber.values()).map(cardNumber -> new Card(cardNumber, shape))).collect(Collectors.toCollection(LinkedList::new));
    }

    public Card getRandomCard() {
        int idx = random.nextInt(cards.size());
        return cards.remove(idx);
    }

    public List<Card> getCards() {
        return cards;
    }
}
