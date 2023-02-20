package nextstep.blackjack.player;

import nextstep.blackjack.card.Card;
import nextstep.blackjack.card.CardNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Player {
    private final String name;
    private final int bettingAmt;
    private int revenue;
    private final List<Card> cards = new ArrayList<>();

    public Player(String name, int bettingAmt) {
        this.name = name;
        this.bettingAmt = bettingAmt;
        this.revenue = 0;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public int countTotal() {
        int ace1 = cards.stream().map(Card::getNumber).reduce(0, Integer::sum);
        AtomicInteger ace11 = new AtomicInteger(ace1);
        if (cards.stream().anyMatch(card -> card.getCardNumber() == CardNumber.A)) {
            cards.stream()
                    .filter(card -> card.getCardNumber() == CardNumber.A)
                    .forEach(card -> {
                        if (ace11.get() <= 11) {
                            ace11.addAndGet(10);
                        }
                    });
        }

        return ace11.get();
    }

    public boolean isOver21() {
        return countTotal() > 21;
    }

    public boolean isBlackJack() {
        return diffWith21() == 0;
    }

    public int diffWith21() {
        return Math.abs(21 - countTotal());
    }

    public String showCards() {
        return name + "카드: " + cards.stream().map(Card::toString).collect(Collectors.joining(", "));
    }

    public String getName() {
        return name;
    }

    public int getBettingAmt() {
        return bettingAmt;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }
}
