/*
 * Deck is a subclass of Stack. the only difference is the constructor, which will automatically
 * add the 52 cards of a standard deck to the list stackCards and the class method rebuildDeck, which will 
 * clear the deck and then add the standard 52 cards back to the deck
 */
public class Deck extends Stack {

    // Constructor
    // names the deck and adds the 52 cards in a standard deck
    public Deck(String name) {
        super(name); // calling the constructor of the superclass, in this case Stack
        
        String[] suits = {"hearts", "diamonds", "spades", "clubs"};
        String[] faceValues = {"ace", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "jack", "queen", "king"};

        for (String suit : suits) {
            for (String faceValue : faceValues) {
                String fullName = faceValue + " of " + suit;
                char suitAbr = (char)suit.charAt(0);
                
                Card card = new Card(fullName, suitAbr, faceValue);
                this.stackCards.add(card);
            }
        }    
        
    }

    public void rebuildDeck() {
        this.stackCards.clear();

        String[] suits = {"hearts", "diamonds", "spades", "clubs"};
        String[] faceValues = {"ace", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "jack", "queen", "king"};

        for (String suit : suits) {
            for (String faceValue : faceValues) {
                String fullName = faceValue + " of " + suit;
                char suitAbr = (char)suit.charAt(0);
                
                Card card = new Card(fullName, suitAbr, faceValue);
                this.stackCards.add(card);
            }
        }    
        
    }

}
