/*
 * Each card has a name, suit, face value, and value
 */
public class Card {
    String name;
    /* 
    *  Full name of the card, including suit and 
    *  value or face. For Example:
    *  ace of hearts
    *  three of clubs
    */
    char suit;
    /* 
     * 1 letter abbreviation for suit
     * c - clubs
     * s - spades
     * h - hearts
     * d - diamonds
     */
    String faceVal;
    /*
     * value which would be displayed on the card
     * does not change depending on rank
     * ex: two, eight, ace, queen
     */
    int value;
    /* 
     * Integer value of each card 
     * changes depending on which rank is being used
     * EX: for cribbage cards
     * ace = 1
     * jack = 10
     * queen = 10
     * king = 10
    */
    int runValue;
    /*
     * Used for calculating runs
     * essentially signifies which card is higher than the previous in a run
     */
    


    // Constructor
    // Name first, suit second, cardVal third, rank fourth
    // value will be assigned based on cardValue and rank
    public Card(String cardName, char cardSuit, String faceValue) {
        this.name = cardName;
        this.suit = cardSuit;
        this.faceVal = faceValue;
        switch(faceValue.toLowerCase()) {
            case "ace":
                this.value = Rank.ace.getVal();
                this.runValue = 1;
                break;
            case "two":
                this.value = Rank.two.getVal();
                this.runValue = 2;
                break;
            case "three":
                this.value = Rank.three.getVal();
                this.runValue = 3;
                break;
            case "four":
                this.value = Rank.four.getVal();
                this.runValue = 4;
                break;
            case "five":
                this.value = Rank.five.getVal();
                this.runValue = 5;
                break;
            case "six":
                this.value = Rank.six.getVal();
                this.runValue = 6;
                break;
            case "seven":
                this.value = Rank.seven.getVal();
                this.runValue = 7;
                break;
            case "eight":
                this.value = Rank.eight.getVal();    
                this.runValue = 8;
                break;
            case "nine":
                this.value = Rank.nine.getVal();
                this.runValue = 9;
                break;
            case "ten":
                this.value = Rank.ten.getVal();
                this.runValue = 10;
                break;
            case "jack":
                this.value = Rank.jack.getVal();
                this.runValue = 11;
                break;
            case "queen":
                this.value = Rank.queen.getVal();
                this.runValue = 12;
                break;
            case "king":
                this.value = Rank.king.getVal();
                this.runValue = 13;
                break;
        }        
        
    }

    @Override
    public String toString() {
        return this.name;
    }

}