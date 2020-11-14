import java.util.*;

public class App {
    public static void main(String[] args) {
        Map<Integer, List<Integer>> playerMap= new HashMap<Integer, List<Integer>>();
        int[] winners = {0, 0, 0, 0};
        initialize(playerMap);
        Player player1 = new Player(1, playerMap.get(1));
        Player player2 = new Player(2, playerMap.get(2));
        Player player3 = new Player(3, playerMap.get(3));
        Player player4 = new Player(4, playerMap.get(4));
        for (int i = 0; i <= 50; i++) {
            int winner = play(player1, player2, player3, player4);
            winners[winner - 1]++;
        }
        for (int i: winners) {
            System.out.println(i);
        }
        System.out.println("finished");
    }
    
    // distrubuting the cards to players (52 cards total for 4 players)
    public static void initialize(Map<Integer, List<Integer>> playerMap){
      	HashSet<Integer> allCards = new HashSet<>();
        for (int i = 1; i < 53; i++){
            allCards.add(i);
        }
        for (int i = 1; i < 53; i++){
            int curr_card = (int) Math.floor(Math.random() * 52 + 1);
            int curr_player = (i-1)/13+1;
            while (!allCards.contains(curr_card)) {
                curr_card = (int) Math.floor(Math.random() * 52 + 1);
            }
            allCards.remove(curr_card);
          	if(!playerMap.containsKey(curr_player)){
              	playerMap.put(curr_player, new ArrayList<Integer>());
            }
            playerMap.get(curr_player).add(curr_card);
        }
    }

    // put cards: keep track of the total number of cards in the middle, and the last put cards
    // comparison 
        
    // strategy 1: No doubt at call, put one matching card, when no matching, put smallest card possible
    // strategy 2: Call randomly, put all matching cards, when no matching, put smallest card possible
    // strategy 3: Call when total is greater than 4, put all matching cards, when no matching, put one or two smallest cards
    
    public static int play(Player player1, Player player2, Player player3, Player player4){
        // while no player has zero cards, continue game
        // each player plays their cards, keep track of all cards in the middle
        // also keep track of claims and the top cards 
        // when player calls, compare last play of cards to claimed 
        List<Integer> placedCard = new ArrayList<Integer>();
        int curr_match = 1;
        int time = 1;
        while (player1.left() != 0 && player2.left() != 0 && player3.left() != 0  && player4.left() != 0 ) {
            if (curr_match >= 14) {
                curr_match = 1;
            }

            player1.strategy1(placedCard, curr_match, player4);
            player2.strategy2(placedCard, curr_match, player1);
            player3.strategy1(placedCard, curr_match, player2);
            player4.strategy2(placedCard, curr_match, player3);
            System.out.println(time);
            curr_match++;
            time++;
        }

        if (player1.left() == 0) {
            System.out.println("Player 1 Won");
            return 1;
        } else if (player2.left() == 0) {
            System.out.println("Player 2 Won");
            return 2;
        } else if (player3.left() == 0) {
            System.out.println("Player 3 Won");
            return 3;
        } else {
            System.out.println("Player 4 Won");
            return 4;
        }
    } 
}

