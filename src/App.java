import java.util.*;

public class App {
    public static void main(String[] args) {
        // double[] average = new double[10];
        Map<Integer, double[]> convergence = new HashMap<Integer, double[]>();
        int num_of_run = 10000;
        for (int j = 0; j < 10; j++) {
            int[] winners = { 0, 0, 0, 0 };
            double[] run = new double[200];

            for (int i = 1; i <= num_of_run; i++) {
                Map<Integer, List<Integer>> playerMap = new HashMap<Integer, List<Integer>>();
                initialize(playerMap);
                Player player1 = new Player(1, playerMap.get(1));
                Player player2 = new Player(2, playerMap.get(2));
                Player player3 = new Player(3, playerMap.get(3));
                Player player4 = new Player(4, playerMap.get(4));
                int winner = play(player1, player2, player3, player4);
                if (winner != -1) {
                    winners[winner - 1]++;
                } else {
                    throw new IllegalArgumentException();
                }
                if (i % 50 == 0) {
                    run[i / 50 - 1] = 1.0 * winners[1] / i;
                }
            }

            convergence.put(j, run);
            // average[j] = 1.0 * winners[1] / 20000.0;
        }
        for (int key : convergence.keySet()) {
            for (double prob : convergence.get(key)) {
                System.out.print(String.format("%.3f", prob) + ", ");
            }
            System.out.println();
        }
        // for(int i = 0; i < winners.length; i++){
        // System.out.println(winners[i]/5000.0);
        // }
        // for (int i = 0; i < average.length; i++) {
        // System.out.print(average[i] + ", ");
        // }
        // System.out.println(winners[2]/5000.0);
        // System.out.println(winners[1]/1000.0 + "Finished");
        // System.out.println(winners[3]/1000.0 + "Finished");
    }

    // distributing the cards to players (52 cards total for 4 players)
    public static void initialize(Map<Integer, List<Integer>> playerMap) {
        HashSet<Integer> allCards = new HashSet<>();
        for (int i = 1; i < 53; i++) {
            allCards.add(i);
        }
        for (int i = 1; i < 53; i++) {
            int curr_card = (int) Math.floor(Math.random() * 52 + 1);
            int curr_player = (i - 1) / 13 + 1;
            while (!allCards.contains(curr_card)) {
                curr_card = (int) Math.floor(Math.random() * 52 + 1);
            }
            allCards.remove(curr_card);
            if (!playerMap.containsKey(curr_player)) {
                playerMap.put(curr_player, new ArrayList<Integer>());
            }
            playerMap.get(curr_player).add(curr_card);
        }
    }

    // strategy 1: Never doubts, put one matching card, when no matching, put one
    // smallest card possible
    // strategy 2: Doubts randomly, put all matching cards, when no matching, put or
    // 2 random cards
    // strategy 3: Doubts when previous player placed cards and player's current
    // matches is greater than 4
    // put all matching cards, when no matching, put one or two smallest cards
    public static int play(Player player1, Player player2, Player player3, Player player4) {
        // while no player has zero cards, continue game
        // each player plays their cards, keep track of all cards in the middle
        // also keep track of claims and the top cards
        // when player calls, compare last play of cards to claimed
        List<Integer> placedCard = new ArrayList<Integer>();
        int curr_match = 1;
        while (player1.left() != 0 && player2.left() != 0 && player3.left() != 0) {
            if (curr_match >= 14) {
                curr_match = 1;
            }

            player1.strategy2(placedCard, curr_match, player4);
            // if after player 1 doubts, previous player still has no cards left
            // previous player wins the game
            if (player4.left() == 0) {
                return 4;
            }

            player2.strategy3(placedCard, curr_match, player1);
            if (player1.left() == 0) {
                return 1;
            }

            player3.strategy2(placedCard, curr_match, player2);
            if (player2.left() == 0) {
                return 2;
            }

            player4.strategy2(placedCard, curr_match, player3);
            if (player3.left() == 0) {
                return 3;
            }

            curr_match++;
        }

        return -1;
    }
}