import java.util.*;

public class Player {
    private int id;
    private List<Integer> cards;
    private List<Integer> previous;

    public Player(int id, List<Integer> cards) {
        this.id = id;
        this.cards = cards;
        this.previous = new ArrayList<Integer>();
    }
    
    public int getId() {
        return id;
    }
    
    public List<Integer> getCards() {
        return cards;
    }
    
    // check how many cards left for each player
    public int left() {
        return cards.size();
    }

    public void add(int card) {
        cards.add(card);
    }

    public void addAll(List<Integer> list) {
        cards.addAll(list);
    }

    public void remove(int card) {
        cards.remove(card);
    }

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
    
    /*
    public void strategy3(int curr_match, List<Integer> placedCard, Player previousPlayer) {
        if (placedCard.empty()) {
            doubt3();
        }
        put3(placedCard, curr_match);
    } 
    */


    public void doubt1(){
        //return false;
    }

    public void doubt2(List<Integer> placedCard, int curr_match, Player previousPlayer){
        if(Math.random() > 0.5) {
            // if guess correct, previous person take all
            // if guess wrong take all cards
            boolean all_match = true;
            if(id == 1){
                curr_match --;
            }
            for (int last_play : previous){
                if((last_play - curr_match) % 13 != 0){
                    all_match = false;
                    break;
                }
            }
            if(all_match){
                cards.addAll(placedCard);
            } else{      
                previousPlayer.getCards().addAll(placedCard);
            }
            placedCard.clear();
            previous.clear();
        }else{
            return;
            //do nothing
        }

    }
    
    /*
    public boolean doubt3(int total, int curr_match){
        // if total + players current match is greater than 4 then doubt
        int count = 0;
        for (int i = 0; i < cards.size(); i++) {
            if ((cards.get(i) - curr_match) % 13 == 0) {
                count++;
            }
        }
        if(count + total > 4){
            return true;
        }
        return false;
    }
    */

    // check whether the current player contain the card that matches the match
    // if so, place that card, if not place the smallest card
                
    public void put1(List<Integer> placedCard, int curr_match){
        ArrayList<Integer> matches = contain(curr_match);
        // no finding 
        if(matches.isEmpty()){
            int find_match = curr_match;
            while (matches.isEmpty()) {
                find_match ++;
                matches = contain(find_match);
            }            
        }
        cards.remove(matches.get(0));
        placedCard.add(matches.get(0));
        previous = new ArrayList<Integer>(matches.get(0));
    }

    public void put2(List<Integer> placedCard, int curr_match){
        ArrayList<Integer> matches = contain(curr_match);
        // no finding 
        if(matches.isEmpty()){
            int find_match = curr_match;
            while (matches.isEmpty()) {
                find_match ++;
                matches = contain(find_match);
            }            
        }
        cards.removeAll(matches);
        placedCard.addAll(matches);
        previous = matches;
    }

    /*
    public int put3(List<Integer> placedCard, int curr_match){
        int contain = contain(curr_match);
        int find_match = curr_match;
        while (contain == -1) {
            find_match ++;
            contain = contain(find_match);
        }
        // placedCard.push(contain);
        cards.remove(contain);
    }

    */
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
