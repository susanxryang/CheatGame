import java.util.*;

public class App {
    public static void main(String[] args) {
        // double total_prob2 = 0.0;
        // double total_prob4 = 0.0;
        double[] average = new double[100];
        Map<Integer, double[]> convergence = new HashMap<Integer, double[]>();
        for (int j = 0; j < 100; j++) {
            int num_of_run = 1000;
            // int[] winners = { 0, 0, 0, 0 };
            // double[] run = new double[200];

            // while(num_of_run <= 1000){
                int[] winners = { 0, 0, 0, 0};
                for (int i = 0; i < num_of_run; i++) {
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
                }
                // run[num_of_run/50 - 1] = 1.0 * winners[1] / num_of_run;
                // num_of_run += 50;
                // System.out.print(String.format("%.3f", 1.0 * winners[1] / num_of_run)  + ", ");
                average[j] = 1.0 * winners[1] / 1000.0;
    
            // }

            // convergence.put(j, run);
            // total_prob2 += winners[1];
            // total_prob4 += winners[3];
        }
        for (int i = 0; i < average.length; i++) {
            System.out.print(average[i] + ", ");
        }
        // for(int key : convergence.keySet()){
        //     for(double prob : convergence.get(key)) {
        //         System.out.print(String.format("%.3f", prob) + ", ");
        //     }
        //     System.out.println();
        // }
        // double prob = winners[1]/1000.0;
        // System.out.printf("Probability of player 2 winning ");
        // System.out.println(String.format("%.5f", prob));
        // System.out.println("Probability of 2 winning " + total_prob2 / 10000);
        // System.out.println("Probability of 4 winning " + total_prob4 / 10000);

        System.out.println("Finished");
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

    // 2222, 3333
    // 2211, 1212, 2121
    // 1133, 3311, 1313, 3131
    // 2233, 3322, 2323, 3232

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
        // int round = 1;
        while (player1.left() != 0 && player2.left() != 0 && player3.left() != 0 && player4.left() != 0) {
            if (curr_match >= 14) {
                curr_match = 1;
            }

            player1.strategy2(placedCard, curr_match, player4);
            if (player1.left() == 0) {
                // System.out.println("Player 1 Won");
                // System.out.println(round);
                return 1;
            }
            player2.strategy3(placedCard, curr_match, player1);
            if (player2.left() == 0) {
                // System.out.println("Player 2 Won");
                // System.out.println(round);
                return 2;
            }
            player3.strategy2(placedCard, curr_match, player2);
            if (player3.left() == 0) {
                // System.out.println("Player 3 Won");
                // System.out.println(round);
                return 3;
            }

            player4.strategy2(placedCard, curr_match, player3);
            if (player4.left() == 0) {
                // System.out.println("Player 4 Won");
                // System.out.println(round);
                return 4;
            }

            // round++;
            curr_match++;
        }
        return -1;
    }
}

// player 1,3 use strategy 1, player 2,4 uses strategy 2, then 2 wins about 40%,
// 4 wins about 60%, 1, 3 occassionally wins;