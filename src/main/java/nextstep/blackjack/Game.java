package nextstep.blackjack;

import nextstep.blackjack.card.CardDeck;
import nextstep.blackjack.player.Dealer;
import nextstep.blackjack.player.Player;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Game {

    private static final InputView inputView = new InputView();
    private static final ResultView resultView = new ResultView();


    public static void main(String[] args) {
        List<Player> players = inputView.inputPlayers();
        Dealer dealer = new Dealer();
        AtomicInteger dealerMoney = new AtomicInteger();

        CardDeck cardDeck = new CardDeck();

        dealer.addCard(cardDeck.getRandomCard());
        dealer.addCard(cardDeck.getRandomCard());

        players.forEach(player -> {
            player.addCard(cardDeck.getRandomCard());
            player.addCard(cardDeck.getRandomCard());
        });

        String collectedNames = players.stream().map(Player::getName).collect(Collectors.joining(", "));
        System.out.println("딜러와 " + collectedNames + " 에게 2장의 카드를 나누었습니다.");

        System.out.println("딜러: "+dealer.showCards());
        players.forEach(player -> System.out.println(player.showCards()));

        if (players.stream().anyMatch(Player::isBlackJack)) {
            whenBlackJack(players, dealer, dealerMoney);
            resultView.finalResult(dealerMoney.get(), players);
            return;
        }

        players.forEach(player -> {
            if (inputView.wantMoreCard(player)) {
                player.addCard(cardDeck.getRandomCard());
                System.out.println(player.showCards());
            }
        });

        if (players.stream().anyMatch(Player::isOver21)) {
            whenOver21(players, dealer, dealerMoney);
            resultView.finalResult(dealerMoney.get(), players);
            return;
        }

    }

    private static void whenBlackJack(List<Player> players, Dealer dealer, AtomicInteger dealerMoney) {
        players.stream().filter(player -> !player.isBlackJack()).forEach(player -> {
            dealerMoney.addAndGet(player.getBettingAmt());
            player.setRevenue(-1 * player.getBettingAmt());
        });

        if (dealer.isBlackJack()) {
            players.stream().filter(Player::isBlackJack).forEach(player -> player.setRevenue(0));
        } else {
            players.stream().filter(Player::isBlackJack).forEach(player -> {
                int prize = (int) (1.5 * player.getBettingAmt());
                player.setRevenue(prize);
                dealerMoney.addAndGet(-1 * prize);
            });
        }
    }

    private static void whenOver21(List<Player> players, Dealer dealer, AtomicInteger dealerMoney) {
        players.stream().filter(player -> !player.isBlackJack()).forEach(player -> {
            dealerMoney.addAndGet(player.getBettingAmt());
            player.setRevenue(-1 * player.getBettingAmt());
        });

        if (dealer.isBlackJack()) {
            players.stream().filter(Player::isBlackJack).forEach(player -> player.setRevenue(0));
        } else {
            players.stream().filter(Player::isBlackJack).forEach(player -> {
                int prize = (int) (1.5 * player.getBettingAmt());
                player.setRevenue(prize);
                dealerMoney.addAndGet(-1 * prize);
            });
        }
    }
}
