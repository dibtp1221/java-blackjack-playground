package nextstep.blackjack;

import nextstep.blackjack.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {

    static Scanner scanner = new Scanner(System.in);

    static List<Player> inputPlayers() {
        List<Player> players = new ArrayList<>();

        System.out.println("게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)");
        String[] names = scanner.nextLine().split(",");

        Arrays.stream(names).forEach(name -> {
            System.out.println(name + "의 베팅 금액은?");
            players.add(new Player(name, scanner.nextInt()));
        });

        scanner.nextLine();
        return players;
    }

    public static boolean wantMoreCard(Player player) {
        System.out.println(player.getName() + "는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)");
        return "y".equals(scanner.nextLine());
    }
}
