import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        Map<Integer, List<Integer>> playerMap= new HashMap<Integer, List<Integer>>();
        initialize(playerMap);
        Player player1 = new Player(1, playerMap.get(1));
        Player player2 = new Player(2, playerMap.get(2));
        Player player3 = new Player(3, playerMap.get(3));
        Player player4 = new Player(4, playerMap.get(4));
        play(player1, player2, player3, player4);
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
            while(!allCards.contains(curr_card)) {
                curr_card = (int) Math.floor(Math.random() * 52 + 1);
            }
            allCards.remove(curr_card);
          	if(!playerMap.keySet().contains(curr_player)){
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
    
    public static void play(Player player1, Player player2, Player player3, Player player4){
        // while no player has zero cards, continue game
        // each player plays their cards, keep track of all cards in the middle
        // also keep track of claims and the top cards 
        // when player calls, compare last play of cards to claimed 
        List<Integer> placedCard = new ArrayList<Integer>();
        int curr_match = 1;
        int time = 0;
        //while (player1.left() != 0 || player2.left() != 0 || player3.left() != 0  || player4.left() != 0 ){
        while (time <= 14)   
            if (curr_match >= 13) {
                curr_match = 1;
            }

            player1.strategy1(placedCard, curr_match, player4);
            System.out.println(player1);
            System.out.println(placedCard);
            player2.strategy2(placedCard, curr_match, player1);
            System.out.println(player2);
            System.out.println(placedCard);
            player3.strategy1(placedCard, curr_match, player2);
            System.out.println(player3);
            System.out.println(placedCard);
            player4.strategy2(placedCard, curr_match, player3);
            System.out.println(player4);
            System.out.println(placedCard);
            //System.out.println("play 4 won" + time);
    } 
}

