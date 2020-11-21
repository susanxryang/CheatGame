import java.util.*;

public class Player {
    private int id;
    // cards holding in the hand
    private List<Integer> cards;
    // card put down by the player
    private List<Integer> putCard;

    public Player(int id, List<Integer> cards) {
        this.id = id;
        this.cards = cards;
    }
    
    // return the id of player
    public int getId() {
        return id;
    }
    
    // return cards that player has
    public List<Integer> getCards() {
        return cards;
    }
    
    // check how many cards left for each player
    public int left() {
        return cards.size();
    }

    // add a card to player's cards
    public void add(int card) {
        cards.add(card);
    }

    // add a list of cards to player's cards
    public void addAll(List<Integer> list) {
        cards.addAll(list);
    }

    // remove a card from player's cards
    public void remove(int card) {
        cards.remove(card);
    }
    
    // remove a list of cards from player's cards
    public void removeAll(List<Integer> list) {
        cards.removeAll(list);
    }

    public void strategy1(List<Integer> placedCard, int curr_match, Player previousPlayer) {
        if (!placedCard.isEmpty()) {
            doubt1();
        }
        put1(placedCard, curr_match);
    } 

    public void strategy2(List<Integer> placedCard, int curr_match, Player previousPlayer) {
        if (!placedCard.isEmpty()) {
            doubt2(placedCard, curr_match, previousPlayer);
        }
        put2(placedCard, curr_match);
    } 
    
    public void strategy3(List<Integer> placedCard, int curr_match, Player previousPlayer) {
        if (placedCard.isEmpty()) {
            doubt3(placedCard, curr_match, previousPlayer);
        }
        put3(placedCard, curr_match);
    } 
    

    // strategy 1 never doubts
    public void doubt1(){
        return;
    }

    // strategy 2 doubts randomly
    public void doubt2(List<Integer> placedCard, int curr_match, Player previousPlayer){
        if(Math.random() > 0.5) {
            // if guess correct, previous person take all
            // if guess wrong take all cards
            boolean all_match = true;
            // check with the previous round if a round has begun
            if(id == 1){
                curr_match --;
            }
            // check wether the previous player has the correct claim 
            for (int last_play : previousPlayer.putCard){
                if((last_play - curr_match) % 13 != 0){
                    all_match = false;
                    break;
                }
            }
            if(all_match){ // wrong doubt (true claim), players has all the placedCard
                cards.addAll(placedCard);
            } else{      // correct doubt (false claim), previous player has all the placedCard
                previousPlayer.getCards().addAll(placedCard);
            }
            placedCard.clear();
        }
    }
    
    // strategy 3 doubts when previous placed card + players current match is greater than 4
    public void doubt3(List<Integer> placedCard, int curr_match, Player previousPlayer){
        int count = 0;
        for (int i = 0; i < cards.size(); i++) {
            if ((cards.get(i) - curr_match) % 13 == 0) {
                count++;
            }
        }
        if(previousPlayer.putCard != null && count + previousPlayer.putCard.size() > 4){
            boolean all_match = true;
            if(id == 1){
                curr_match --;
            }
            for (int last_play : previousPlayer.putCard){
                if((last_play - curr_match) % 13 != 0){
                    all_match = false;
                    break;
                }
            }
            if(all_match){ // doubt wrongly
                cards.addAll(placedCard);
            } else{ // doubt correct
                previousPlayer.getCards().addAll(placedCard);
            }
            placedCard.clear();
        }
    }

    // if current player has the card that matches the current round, place one matching card
    // if not, place one of the smallest card  
    public void put1(List<Integer> placedCard, int curr_match){
        ArrayList<Integer> matches = contain(curr_match);
        // no finding 
        if(matches.isEmpty()){
            int find_match = 0;
            while (find_match <= 13 && matches.isEmpty()) {
                find_match ++;
                matches = contain(find_match);
            }           
            matches = new ArrayList<>(matches.get(0));
        }
        cards.removeAll(matches);
        placedCard.addAll(matches);
        putCard = matches;
    }

    // if match, put all matching cards
    // if no match, put 1 or 2 random cards
    public void put2(List<Integer> placedCard, int curr_match){
        ArrayList<Integer> matches = contain(curr_match);
        // no finding 
        if(matches.isEmpty()){
            int rd1 = (int) Math.floor(Math.random() * cards.size());
            int card1  = cards.get(rd1);
            matches.add(card1);
            if (Math.random() > 0.5) {
                int rd2 = (int) Math.floor(Math.random() * (cards.size() - 1));
                int card2  = cards.get(rd2);  
                matches.add(card2);
            }
        }
        cards.removeAll(matches);
        placedCard.addAll(matches);
        putCard = matches;
    }

    // if found matches with the current round, put all matching cards
    // if not, put all hands of the smallest card 
    // For example, if the current round is 5, and the player has no 5's and
    // the smallest card he has are two Aces, he will put all two Aces down
    public void put3(List<Integer> placedCard, int curr_match){
        // find matches
        ArrayList<Integer> matches = contain(curr_match);
        // no matches 
        if(matches.isEmpty()){
            // find the smallest cards
            int find_match = 0;
            while (find_match <= 13 && matches.isEmpty()) {
                find_match ++;
                matches = contain(find_match);
            }            
        }
        cards.removeAll(matches);
        placedCard.addAll(matches);
        putCard = matches;
    }
    
    // return all matching cards, return empty arraylist if no match
    public ArrayList<Integer> contain(int match) { 
        ArrayList<Integer> all_match = new ArrayList<Integer>();
        for (int i = 0; i < cards.size(); i++) {
            if ((cards.get(i)-match) % 13 == 0) {
                all_match.add(cards.get(i));
            }
        }
        return all_match;
    }
}
