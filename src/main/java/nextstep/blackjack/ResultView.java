package nextstep.blackjack;

import nextstep.blackjack.player.Player;

import java.util.List;

public class ResultView {

    public static void finalResult(int dealerMoney, List<Player> players) {
        System.out.println("## 최종 수익");
        System.out.println("딜러: " + dealerMoney);
        players.forEach(player -> System.out.println(player.getName() + ": " + player.getRevenue()));
    }
}
