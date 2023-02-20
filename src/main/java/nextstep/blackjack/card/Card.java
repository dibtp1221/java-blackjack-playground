package nextstep.blackjack.card;

import java.util.Objects;

public class Card {

    private final CardNumber cardNumber;
    private final Shape shape;

    public Card(CardNumber cardNumber, Shape shape) {
        this.cardNumber = cardNumber;
        this.shape = shape;
    }

    public CardNumber getCardNumber() {
        return cardNumber;
    }

    public int getNumber() {
        return cardNumber.getValue();
    }

    @Override
    public String toString() {
        return (cardNumber.isNamePresents() ? cardNumber.name() : cardNumber.getValue()) + shape.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cardNumber == card.cardNumber && shape == card.shape;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, shape);
    }
}
