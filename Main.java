import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Rank.setVals(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10);

        Stack testHand = new Stack("testHand");
        Deck testDeck = new Deck("testDeck");
        Stack discard = new Stack("discard");

        Scanner input = new Scanner(System.in);

        String[] cards = new String[6];
        for (int i = 0; i < 6; i++) {
            System.out.println("Enter your card (Muhahahahaha): ");
            cards[i] = (input.nextLine());
        }

        System.out.println("cards");

        testDeck.specificDeal(cards, testHand);

        discard.stackCards.addAll(findBestDiscard(testDeck, testHand));

        System.out.println(testHand.stackCards);
        System.out.println(discard.stackCards);

        System.out.println("Skibidi Toilet");


    }

    /*
     * deals 6 cards from deck to hand
     */
    public static void dealFullHand(Deck deck, Stack hand) {
        deck.randDeal(6, hand);
    }

    /*
     * Deals 4 cards from deck to hand
     */
    public static void dealFinalHand(Deck deck, Stack hand) {
        deck.randDeal(4, hand);
    }

    /*
     * Scores a hand of four cards
     * does not account for flips
     * returns a double which is the final score
     * will not function with over 4 cards in hand
     */
    public static double scoreFourCards(Stack hand) {
        double totalScore = 0;
        boolean runOfFourPresent = false;
        // Calculating 15s and pairs for combinations of 2 cards
        // this will handle all pairs, but not all 15s
        for (int i = 0; i < 3; i++) {  // this first for loop will execute once for each card
            for (int cardIndex = i + 1; cardIndex < 4; cardIndex++) { // this second for loop will execute one for each card after the
                                                                      // Card in the first for loop, ensuring double counts will not occur
                // determining 15
                if (hand.stackCards.get(i).value + hand.stackCards.get(cardIndex).value == 15) {
                    totalScore += 2;
                }

                // determining pairs
                // must use string comparison to avoid counting different cards with same rank value to be counted as pairs (face cards and 10s)
                if (hand.stackCards.get(i).faceVal.equals(hand.stackCards.get(cardIndex).faceVal)) {
                    totalScore += 2;
                }
            }
        }
        // determining if a run of four is present, and adding four points if it is
        // the boolean runOfFourPresent will be used later to avoid double counting runs of 3 if there is a run of four
        if (findRunOfFour(hand)) {
            totalScore += 4;
            runOfFourPresent = true;
        }

        // determing if all four cards are of the same suit, and adding four points if they are
        if (findCommonSuit(hand)) {
            totalScore += 4;
        }

        // determining 15s involving all 4 cards
        if (hand.stackCards.get(0).value + hand.stackCards.get(1).value + hand.stackCards.get(2).value + hand.stackCards.get(3).value == 15) {
            totalScore += 2;
        }

        //determining 15s involving 3 cards
        for (int Card1 = 0; Card1 < 2; Card1++) {
            for (int Card2 = Card1 + 1; Card2 < 3; Card2++) {
                for (int Card3 = Card2 + 1; Card3 < 4; Card3++) {
                    if (hand.stackCards.get(Card1).value + hand.stackCards.get(Card2).value + hand.stackCards.get(Card3).value == 15) {
                        totalScore += 2;
                    }
                }
            }
        }
        // determining runs of 3
        // only scoring them if there is no run of four present
        if (!runOfFourPresent) {
            totalScore += scoreRunsOfThree(hand);
        }

        return totalScore;
    }
    
    public static double findAvgScoreOfFlip(Deck deck, Stack hand) {
        double totalAverageScore = 0;
        for (Card flip : deck.stackCards) {
            totalAverageScore += scoreFlip(flip, hand) / deck.stackCards.size();
        }

        return totalAverageScore;
    }

    public static double scoreFlip(Card flip, Stack hand) {
        double flipScore = 0;
        boolean runOfFourPresent = findRunOfFour(hand);
        boolean runOfThreePresent = findRunOfThree(hand);
        for (int index = 0; index < 4; index++) {
            // determining 15s with 2 cards
            if (flip.value + hand.stackCards.get(index).value == 15) {
                flipScore += 2;
            }
                
            // determining pairs
            if (flip.faceVal.equals(hand.stackCards.get(index).faceVal)) {
                flipScore += 2;
            }
        }

        // determining nobs (jack in your hand is same suit as flip)
        for (Card card : hand.stackCards) {
            if ((card.suit == flip.suit) && card.faceVal.equals("jack")) {
                flipScore += 1;
            }
        }

        // Adding correct amount of score for a run of five depending on the size of run already present in the hand
        if (findRunOfFive(hand, flip)) {
            if (runOfFourPresent) {
                flipScore += 1;
            }
            else if (runOfThreePresent) {
                flipScore += 2;
            }
            else {
                flipScore += 5;
            }
        }
        else {
            // Scoring runs of 4 by checking groups of three cards for runs of three and then checking if a run of 4 was created
            // Use a temporary list to avoid concurrent modification (Thanks chatGPT!)
            List<Card> handCopy = new ArrayList<>(hand.stackCards);
            
            boolean runOf4 = false;    // Used to determine if runs of 3 should be added to score
            for (Card discard : handCopy) {
                boolean runOf3; // Used to determine how many points should be added for a run of 4
                hand.stackCards.remove(discard);

                if (findRunOfThree3Cards(hand)) {
                    runOf3 = true;
                } 
                else {
                    runOf3 = false;
                }
                    
                hand.stackCards.add(flip);
                    
                if (findRunOfFour(hand) && !runOf3) {
                    flipScore += 4;
                    runOf4 = true;
                } 
                else if (findRunOfFour(hand) && runOf3) {
                    flipScore += 1;
                    runOf4 = true;
                }

                // Scoring 15s of 4 cards while we have sets of 4 cards ready
                if (hand.stackCards.get(0).value + hand.stackCards.get(1).value + hand.stackCards.get(2).value + hand.stackCards.get(3).value == 15) {
                    flipScore += 2;
                }
                    
                hand.stackCards.remove(flip);
                hand.stackCards.add(discard);
            }

            // Scoring runs of 3
            if (!runOf4) {
                flipScore += scoreRunsOfThree5Cards(hand, flip) - scoreRunsOfThree(hand);
            }
        }

        // Scoring 15s of three cards
        for (int card1 = 0; card1 < 4; card1++) {
            for (int card2 = card1 + 1; card2 < 4; card2 ++) {
                if (hand.stackCards.get(card1).value + hand.stackCards.get(card2).value + flip.value == 15) {
                    flipScore += 2;
                    //System.out.println("I found a 15 involving 3 cards");
                }
            }
        } 

        // Scoring 15s of five cards
        if (hand.stackCards.get(0).value + hand.stackCards.get(1).value + hand.stackCards.get(2).value + hand.stackCards.get(3).value + flip.value == 15) {
            flipScore += 2;
            //System.out.println("I found a 15 involving all 5 cards");
        }

        // Scoring 5 card flush
        if (findCommonSuit(hand) && hand.stackCards.get(0).suit == flip.suit) {
            flipScore += 1;
            //System.out.println("I found a flush 5");
        }

        return flipScore;

    }

    /*
     * Accepts a hand of 6 cards and the deck that those cards were dealt from
     * Returns the 2 best cards to discard in order to maximize average score
     * Currently does not account for crib
     */
    public static List<Card> findBestDiscard(Deck deck, Stack hand) {
        List<Double> discardScores = new ArrayList<>();
        List<Stack> discards = new ArrayList<>();
        for (int discard1 = 0; discard1 < hand.stackCards.size(); discard1++) {
            for (int discard2 = discard1 + 1; discard2 < hand.stackCards.size(); discard2++) {
                Stack discard = new Stack("tempDiscardStack");

                discard.stackCards.add(hand.stackCards.get(discard1));
                discard.stackCards.add(hand.stackCards.get(discard2));
                hand.stackCards.removeAll(discard.stackCards);

                double handScore = scoreFourCards(hand) + findAvgScoreOfFlip(deck, hand);

                discardScores.add(handScore);
                discards.add(discard);
                
                hand.stackCards.addAll(discard.stackCards);
            }
        }

        int maxIndex = 0;
        double maxScore = discardScores.get(0);
        int currentIndex = -1;

        for (double score : discardScores) {
            currentIndex++;

            if (score > maxScore) {
                maxScore = score;
                maxIndex = currentIndex;
            }
        }

        return discards.get(maxIndex).stackCards;
    }

    /*
     * Takes a stack of 4 cards as an input
     * Returns the score of all runs of 3 present
     * will double count for runs of 4
     * use in conjunction with findRunOfFour to account for this
     */
    public static int scoreRunsOfThree(Stack hand) {
        int runsOf3 = 0;
        for (int Card1 = 0; Card1 < 4; Card1++) {
            for (int Card2 = 0; Card2 < 4; Card2++) {
                for (int Card3 = 0; Card3 < 4; Card3++) {
                    if ((hand.stackCards.get(Card1).runValue == (hand.stackCards.get(Card2).runValue + 1)) && (hand.stackCards.get(Card1).runValue == (hand.stackCards.get(Card3).runValue + 2))) {
                        runsOf3 += 1;
                    }
                }
            }
        }
        return runsOf3 * 3;
    }

     /*
     * Takes a stack of 4 cards and a single card as an input
     * Returns the score of all runs of 3 present
     * will double count for runs of 4
     * use in conjunction with findRunOfFour to account for this
     */
    public static int scoreRunsOfThree5Cards(Stack hand, Card flip) {
        int runsOf3 = 0;
        Stack fiveHand = new Stack("fiveHand");
        fiveHand.stackCards.addAll(hand.stackCards);
        fiveHand.stackCards.add(flip);
        for (int Card1 = 0; Card1 < 5; Card1++) {
            for (int Card2 = 0; Card2 < 5; Card2++) {
                for (int Card3 = 0; Card3 < 5; Card3++) {
                    if ((fiveHand.stackCards.get(Card1).runValue == (fiveHand.stackCards.get(Card2).runValue + 1)) && (fiveHand.stackCards.get(Card1).runValue == (fiveHand.stackCards.get(Card3).runValue + 2))) {
                        runsOf3 += 1;
                    }
                }
            }
        }
        return runsOf3 * 3;
    }

    /*
     * Takes a stack of 4 cards as an input
     * outputs a boolean, true if the stack contains a run of 3
     * false if it does not contain a run of 3
     * useful if number of runs is irrelevant
     * otherwise use findRunsOfThree
     */
    public static boolean findRunOfThree(Stack hand) {
        boolean runOf3 = false;
        for (int Card1 = 0; Card1 < 4; Card1++) {
            for (int Card2 = 0; Card2 < 4; Card2++) {
                for (int Card3 = 0; Card3 < 4; Card3++) {
                    if ((hand.stackCards.get(Card1).runValue == (hand.stackCards.get(Card2).runValue + 1)) && (hand.stackCards.get(Card1).runValue == (hand.stackCards.get(Card3).runValue + 2))) {
                        runOf3 = true;
                    }
                }
            }
        }
        return runOf3;
    }

    public static boolean findRunOfThree3Cards(Stack threeCardHand) {
        threeCardHand.sortStack();
        if ((threeCardHand.stackCards.get(0).runValue == (threeCardHand.stackCards.get(1).runValue - 1)) && (threeCardHand.stackCards.get(0).runValue == (threeCardHand.stackCards.get(2).runValue - 2))) {
            return true;
        }
        return false;
    }

    /*
     * returns true if the stack contains a run of 4, otherwise returns false
     * function will not work properly if runHand contians more than 4 cards
     */
    public static boolean findRunOfFour(Stack hand) {
        hand.sortStack();
        return ((hand.stackCards.get(0).runValue == (hand.stackCards.get(1).runValue - 1)) && (hand.stackCards.get(0).runValue == (hand.stackCards.get(2).runValue - 2)) && (hand.stackCards.get(0).runValue == (hand.stackCards.get(3).runValue - 3)));
    }


    /*
     * returns true if a run of 5 is present
     * otherwise, returns false
     * hand should be a stack containing 4 cards
     * flip should be a single card
     */
    public static boolean findRunOfFive(Stack hand, Card flip) {
        Stack runStack = new Stack("runStack");
        runStack.stackCards.addAll(hand.stackCards);
        runStack.stackCards.add(flip);
        runStack.sortStack();
        return ((runStack.stackCards.get(0).runValue == (runStack.stackCards.get(1).runValue - 1)) && (runStack.stackCards.get(0).runValue == (runStack.stackCards.get(2).runValue - 2)) && (runStack.stackCards.get(0).runValue == (runStack.stackCards.get(3).runValue - 3)) && (runStack.stackCards.get(0).runValue == (runStack.stackCards.get(4).runValue - 4)));
    }

    
    /*
     * returns true if first four cards of stack are of the same suit
     * otherwise returns false
     * should only be used for stacks containing 4 cards
     */
    public static boolean findCommonSuit(Stack hand) {
        return ((hand.stackCards.get(0).suit == hand.stackCards.get(1).suit) && (hand.stackCards.get(0).suit == hand.stackCards.get(2).suit) && (hand.stackCards.get(0).suit == hand.stackCards.get(3).suit));
    }

}
