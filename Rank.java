/*
 * the enum Rank is used to assign the integer value to a card corresponding
 * to its face value (for example assigning a value of 1 to ace for cribbage or a 14 for poker)
 * each constant contains one value corresponding to the card.
 */
public enum Rank {
    ace(1),
    two(2),
    three(3),
    four(4),
    five(5),
    six(6),
    seven(7),
    eight(8),
    nine(9),
    ten(10),
    jack(10),
    queen(10),
    king(10);

    private int value;

    //constructor
    Rank(int value) {
        this.value = value;
    }

    //returnts int value of specified constant
    public int getVal() {
        return this.value;
    }

    // This method sets the value for each constant, ace through king, in order
    public static void setVals(int aceVal, int twoVal, int threeVal, int fourVal, int fiveVal, int sixVal, int sevenVal, 
                                int eightVal, int nineVal, int tenVal, int jackVal, int queenVal, int kingVal) {
        ace.value = aceVal;
        two.value = twoVal;
        three.value = threeVal;
        four.value = fourVal;
        five.value = fiveVal;
        six.value = sixVal;
        seven.value = sevenVal;
        eight.value = eightVal;
        nine.value = nineVal;
        ten.value = tenVal;
        jack.value = jackVal;
        queen.value = queenVal;
        king.value = kingVal;
    }
}
