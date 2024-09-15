import java.util.ArrayList;
import java.util.List;

public class Stack {

    String stackName;
    // Name of the stack
    List<Card> stackCards = new ArrayList<>();
    /*
     * List containing all cards in the stack
     */

    // constructor, only affects stackName
    public Stack(String name) {
        this.stackName = name;
    }

    @Override
    public String toString() {
        return this.stackName;
    }

    // removes the first instance of each card if removeAll is set to false
    // or all instances of each card if removeAll is set to true
    public void removeCards(String[] removeCards, boolean removeAll) {
        List<String> removedNames = new ArrayList<>(); // Track names of cards already removed
        List<Card> removeList = new ArrayList<>();
    
        for (String remove : removeCards) {
            for (Card card : this.stackCards) {
                if (card.name.equals(remove) && (!removedNames.contains(remove) || removeAll)) {
                    removeList.add(card);
                    removedNames.add(remove);
                    if (!removeAll) {
                        break;
                    }
                }
            }
        }
    
        if (removeAll) {
            this.stackCards.removeAll(removeList);
        } else {
            for (Card removeElement : removeList) {
                this.stackCards.remove(removeElement);
            }
        }
    }

    /*
     * Deals the number of cards equal to numcards from this stack to recieingStack, in the process
     * removing the cards from the original stack and adding them to recievingStack
     */
    public void randDeal(int numCards, Stack recievingStack) {
        List<Card> dealCards = new ArrayList<>();
        for (int i = 0; i < numCards; i++) {
            Card dealCard = this.stackCards.get((int) (Math.random()*this.stackCards.size()));
                while (dealCards.contains(dealCard)) {
                    dealCard = this.stackCards.get((int) (Math.random()*this.stackCards.size()));
                }
            dealCards.add(dealCard);
        }
        recievingStack.stackCards.addAll(dealCards);
        for (Card removeCard : dealCards) {
            this.stackCards.remove(removeCard);
        }

    }

    /*
    * This function will deal all of the cards in the array dealCards from the original stack
    * to recieving stack. NOTE: This function will not work if you are attempting to deal multiple of the same card
    * (for example if dealCards contains {"ace of spades", "jack of clubs", "ace of spades"} only one ace of spades
    * will be dealt) for this reason it is best used when only one deck is being utilized
    */
    public void specificDeal(String[] dealCards, Stack recievingStack) {
        List<String> dealtNames = new ArrayList<>(); // Track names of cards already removed
        List<Card> dealList = new ArrayList<>();
    
        for (String dealCard : dealCards) {
            for (Card card : this.stackCards) {
                if (card.name.equals(dealCard) && !dealtNames.contains(dealCard)) {
                    dealList.add(card);
                    dealtNames.add(dealCard);
                }
            }
        }
        
        this.removeCards(dealCards, false);
        for (Card card : dealList) {
            recievingStack.stackCards.add(card);
        }
    }

    /*
     * Sorts all cards in a stack in order of runValue, from least to greatest
     */
    public void sortStack() {
        List<Card> sortedStack = new ArrayList<>();
        int stackSize = this.stackCards.size();
        
        for (int i = 0; i < stackSize; i++) {
            int minValue = this.stackCards.get(0).runValue;
            Card minCard = this.stackCards.get(0);
            
            // Find the minimum value card in the current state of the stack
            for (Card card : this.stackCards) {
                if (card.runValue <= minValue) {
                    minValue = card.runValue;
                    minCard = card;
                }
            }
            
            // Add the minimum value card to the sorted stack and remove it from the original stack
            sortedStack.add(minCard);
            this.stackCards.remove(minCard);
        }
    
        // Assign the sorted stack back to the original stack
        this.stackCards = sortedStack;
    }
}
