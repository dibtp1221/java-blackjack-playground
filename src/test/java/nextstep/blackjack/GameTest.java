package nextstep.blackjack;

import nextstep.blackjack.card.Card;
import nextstep.blackjack.card.CardNumber;
import nextstep.blackjack.card.Shape;
import nextstep.blackjack.player.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void findWinnerBtnRemains() {
        AtomicInteger dealerMoney = new AtomicInteger();

        Player dealer = new Player("딜러", 0);
        dealer.addCard(new Card(CardNumber.FIVE, Shape.HEART));
        dealer.addCard(new Card(CardNumber.SIX, Shape.CLOVER));

        List<Player> players = new ArrayList<>();
        Player pobi = new Player("pobi", 10000);
        pobi.addCard(new Card(CardNumber.A, Shape.SPADE));
        pobi.addCard(new Card(CardNumber.SIX, Shape.SPADE));

        Player jason = new Player("jason", 20000);
        jason.addCard(new Card(CardNumber.TEN, Shape.SPADE));
        jason.addCard(new Card(CardNumber.J, Shape.SPADE));

        players.add(pobi);
        players.add(jason);

        List<Player> notLosts = players.stream().filter(Player::isNotOver21).collect(Collectors.toList());
        notLosts.add(dealer);
        Game.findWinnerBtnRemains(dealerMoney, notLosts);

        ResultView.finalResult(dealerMoney.get(), players);
    }
}