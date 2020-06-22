package cst135n.studentsub;
import java.util.ArrayList;

public class Hand {

	// drawing cards
		public void drawCard(Cards card) {
			hand.add(card);
		}

		// checking if the player has a card to put down
		public Cards hasMatch(Cards cardToMatch) {
			Cards match = null;
			for (int i = 0; i < hand.size(); i++) {
				if (hand.get(i).isMatch(cardToMatch) == true) {
					match = hand.get(i);
					hand.remove(i);
					return match;
				}
			}
			return match;
		}

		// checks for Uno
		public boolean isUno() {
			if (hand.size() == 1) {
				return true;
			}
			return false;
		}

		// checks for win condition
		public boolean isWinner() {
			if (hand.size() == 0) {
				return true;
			}
			return false;
		}
		
		// ***** refactored for scoring points *****
		public int getPoints(int i) {
			int a = hand.get(i).getScore();
			
			return a;	
		}
		
		public int size() {
			return hand.size();
		}
		public Cards eachCard(int j) {
			return hand.get(j);
		}

		private ArrayList<Cards> hand = new ArrayList<Cards>();

		@Override
		public String toString() {
			return "Hand [hand=" + hand + "]";
		}
}
