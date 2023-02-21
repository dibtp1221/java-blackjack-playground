package nextstep.blackjack;

import nextstep.blackjack.card.CardDeck;
import nextstep.blackjack.player.Player;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Game {

    static CardDeck cardDeck = new CardDeck();

    public static void main(String[] args) {
        List<Player> players = InputView.inputPlayers();
        Player dealer = new Player("딜러", 0);
        AtomicInteger dealerMoney = new AtomicInteger();

        initialSet(players, dealer);

        if (players.stream().anyMatch(Player::isBlackJack)) {
            whenBlackJack(players, dealer, dealerMoney);
            ResultView.finalResult(dealerMoney.get(), players);
            return;
        }

        givePlayersAddtCard(players, dealerMoney);

        giveDealerAddtCard(dealer);

        List<Player> notLosts = players.stream().filter(Player::isNotOver21).collect(Collectors.toList());

        if (dealer.countTotal() > 21) {
            whenDealerIsOver21(dealerMoney, notLosts);
            ResultView.finalResult(dealerMoney.get(), players);
            return;
        }

        notLosts.add(dealer);
        findWinnerBtnRemains(dealerMoney, notLosts);

        ResultView.finalResult(dealerMoney.get(), players);
    }

    public static void findWinnerBtnRemains(AtomicInteger dealerMoney, List<Player> notLosts) {
        notLosts.forEach(player -> System.out.println(player.showCards()));

        notLosts.sort(Comparator.comparingInt(Player::countTotal).reversed());

        Player winner = notLosts.get(0);
        dealerMoney.addAndGet(-1 * winner.getBettingAmt());
        winner.setRevenue(winner.getBettingAmt());

        notLosts.remove(0);
        notLosts.stream().filter(player -> !"딜러".equals(player.getName())).forEach(player -> loseMoneyToDealer(dealerMoney, player));
    }

    private static void whenDealerIsOver21(AtomicInteger dealerMoney, List<Player> notLost) {
        notLost.forEach(player -> {
                    player.setRevenue(player.getBettingAmt());
                    dealerMoney.getAndAdd(-1 * player.getBettingAmt());
                });
    }

    private static void givePlayersAddtCard(List<Player> players, AtomicInteger dealerMoney) {
        players.forEach(player -> {
            while (InputView.wantMoreCard(player)) {
                player.addCard(cardDeck.getRandomCard());
                System.out.println(player.showCards());
                if (player.isOver21()) {
                    System.out.println("21 초과했습니다. 베팅금액을 잃었습니다.");
                    loseMoneyToDealer(dealerMoney, player);
                    break;
                }
            }
        });
    }

    private static void loseMoneyToDealer(AtomicInteger dealerMoney, Player player) {
        player.setRevenue(-1 * player.getBettingAmt());
        dealerMoney.addAndGet(player.getBettingAmt());
    }

    private static void giveDealerAddtCard(Player dealer) {
        if (dealer.countTotal() < 17) {
            System.out.println("딜러는 16이하라 한장의 카드를 더 받았습니다.");
            dealer.addCard(cardDeck.getRandomCard());
            System.out.println(dealer.showCards());
        }
    }

    private static void initialSet(List<Player> players, Player dealer) {
        dealer.addCard(cardDeck.getRandomCard());
        dealer.addCard(cardDeck.getRandomCard());

        players.forEach(player -> {
            player.addCard(cardDeck.getRandomCard());
            player.addCard(cardDeck.getRandomCard());
        });

        String collectedNames = players.stream().map(Player::getName).collect(Collectors.joining(", "));
        System.out.println("딜러와 " + collectedNames + " 에게 2장의 카드를 나누었습니다.");

        System.out.println(dealer.showCards());
        players.forEach(player -> System.out.println(player.showCards()));
    }

    private static void whenBlackJack(List<Player> players, Player dealer, AtomicInteger dealerMoney) {

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
