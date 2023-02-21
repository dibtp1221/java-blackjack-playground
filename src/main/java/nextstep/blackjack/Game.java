package nextstep.blackjack;

import nextstep.blackjack.card.CardDeck;
import nextstep.blackjack.player.Dealer;
import nextstep.blackjack.player.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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
            while (inputView.wantMoreCard(player)) {
                player.addCard(cardDeck.getRandomCard());
                System.out.println(player.showCards());
                if (player.isOver21()) {
                    System.out.println("21 초과했습니다. 베팅금액을 잃었습니다.");
                    player.setRevenue(-1 * player.getBettingAmt());
                    dealerMoney.getAndAdd(player.getBettingAmt());
                    break;
                }
            }
        });

        if (dealer.countTotal() < 17) {
            System.out.println("딜러는 16이하라 한장의 카드를 더 받았습니다.");
            dealer.addCard(cardDeck.getRandomCard());
            System.out.println("딜러: "+dealer.showCards());
        }

        List<Player> notLost = players.stream().filter(Player::isNotOver21).collect(Collectors.toList());

        if (dealer.countTotal() > 21) {
            notLost.forEach(player -> {
                        player.setRevenue(player.getBettingAmt());
                        dealerMoney.getAndAdd(-1 * player.getBettingAmt());
                    });
            resultView.finalResult(dealerMoney.get(), players);
            return;
        }

        System.out.println("딜러: "+dealer.showCards());
        notLost.forEach(player -> System.out.println(player.showCards()));

        notLost.sort(Comparator.comparingInt(Player::countTotal));

        Player winner = notLost.get(0);
        dealerMoney.getAndAdd(-1 * winner.getBettingAmt());
        winner.setRevenue(winner.getBettingAmt());

        notLost.remove(1);

        notLost.forEach(player -> {
            player.setRevenue(-1 * player.getBettingAmt());
            dealerMoney.getAndAdd(player.getBettingAmt());
        });

        resultView.finalResult(dealerMoney.get(), players);
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
