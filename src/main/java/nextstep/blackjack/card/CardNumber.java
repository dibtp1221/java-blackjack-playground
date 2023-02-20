package nextstep.blackjack.card;

public enum CardNumber {
    K(true, 10),
    Q(true, 10),
    J(true, 10),
    TEN(false, 10),
    NINE(false, 9),
    EIGHT(false, 8),
    SEVEN(false, 7),
    SIX(false, 6),
    FIVE(false, 5),
    FOUR(false, 4),
    THREE(false, 3),
    TWO(false, 2),
    A(true, 1);

    private final boolean isNamePresents;
    private final int value;

    CardNumber(boolean isNamePresents, int value) {
        this.isNamePresents = isNamePresents;
        this.value = value;
    }

    public boolean isNamePresents() {
        return isNamePresents;
    }

    public int getValue() {
        return value;
    }
}
