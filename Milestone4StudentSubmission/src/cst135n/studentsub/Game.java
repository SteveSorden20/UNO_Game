package cst135n.studentsub; //requirement #1

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Game {

	public void initializeGame() {
		boolean gamewinner = false;
		int matchnumber = 1;
		
		// Establishing number of players
				Scanner sc = new Scanner(System.in);
				System.out.println("\nPlease enter the number of players in the game: ");
				int num = Integer.parseInt(sc.nextLine());
		
		Integer[] scorecard = new Integer[num];
		for(int i =0; i < num; i++) {
			scorecard[i] = 0;
		}
		while(gamewinner == false) {
		// Prepping the deck
		Deck deck = new Deck();
		deck.populate();
		System.out.println(deck);
		deck.shuffle();
		System.out.println(deck);

		ArrayList<Hand> hands = new ArrayList<Hand>();
		for (int i = 0; i < num; i++) {
			hands.add(new Hand());
		}

		// adding cards into the hands of the players
		for (int i = 0; i < 7; i++) {
			for (Hand hand : hands) {
				hand.drawCard(deck.dealCard());
			}
		}
		// add a card to the discard to start it, draw until there is a normal card
		deck.discardPile();

		System.out.println(hands);
		
		gamewinner = Game.Game(num, deck, hands, scorecard, matchnumber);
		
		matchnumber++;
		}
	}

	// New Draw Four Logic
	private static void drawfour(int i, Deck deck, ArrayList<Hand> hands) {
		System.out.println(hands.get(i));
		System.out.println("****Player " + (i + 1) + " has to draw 4 cards****");
		((Hand) hands.get(i)).drawCard(deck.dealCard());
		((Hand) hands.get(i)).drawCard(deck.dealCard());
		((Hand) hands.get(i)).drawCard(deck.dealCard());
		((Hand) hands.get(i)).drawCard(deck.dealCard());
	}

	// New Draw Two Logic
	private static void drawtwo(int i, Deck deck, ArrayList<Hand> hands) {
		System.out.println(hands.get(i));
		System.out.println("****Player " + (i + 1) + " has to draw 2 cards****");
		((Hand) hands.get(i)).drawCard(deck.dealCard());
		((Hand) hands.get(i)).drawCard(deck.dealCard());
	}

	// New Skip Logic
	private static int skip(int i, boolean reverse, int num) {
		System.out.println("****Player " + (i + 1) + " has been skipped****");
		// partial reverse logic
		if (reverse == false) {
			i++;
			if (i >= num) {
				i = 0;
			}
		} else {
			i--;
			if (i < 0) {
				i = num - 1;
			}
		}
		return i;
	}

	// New Reverse Logic
	private static boolean reverse(int i, boolean reverse) {
		System.out.println("$$$$Game play has been reversed$$$$");
		if (reverse == false) {
			reverse = true;
		} else {
			reverse = false;
		}
		return reverse;
	}

	private static void nextplayer(int i, boolean reverse, int num) {
		if (reverse == false) {
			i++;
			if (i >= num) {
				i = 0;
			}
		} else {
			i--;
			if (i < 0) {
				i = num - 1;
			}
		}
	}
	
	private static int scorecard(int i, ArrayList<Hand> hands, Integer[] scorecard, int matchnumber) {
		System.out.println("+++++++++++++++++++++++++++++");
		System.out.println("++++ UNO MATCH #"+matchnumber+ " SCORE CARD +++");
		System.out.println("+++++++++++++++++++++++++++++");
		int winnerscore = 0;
		for (int k = 0; k < hands.size(); k++) {
			int playerscore = 0;
			for (int j = 0; j < hands.get(k).size(); j++) {	
				playerscore = playerscore + hands.get(k).getPoints(j);
			}
			System.out.println(" Player " + (k + 1) + " has " + playerscore + " points");
			winnerscore = winnerscore + playerscore;
		}
		System.out.println("\nPlayer " + (i + 1) + "'s winning score total is: " + winnerscore);
		int runningscore = scorecard[i] + winnerscore;
		return runningscore;
	}

	// Game logic to run game play
	private static boolean Game(int num, Deck deck, ArrayList<Hand> hands, Integer[] scorecard, int matchnumber) {

		boolean matchwinner = false;
		boolean gamewinner = false;
		boolean reverse = false;		
		
		int i = 0;

		while (matchwinner != true) { // loops through players one way

			if (i == num) {
				i = 0;
			}

			int red = 0;
			int yellow = 0;
			int blue = 0;
			int green = 0;
			System.out.println("Current top Card: " + deck.topDiscard());// requirement #3

			// skip logic
			if (deck.topDiscard().getValue() == CardsValue.SKIP) {
				i = Game.skip(i, reverse, num);
			}

			// game play draw four logic
			if (deck.topDiscard().getValue() == CardsValue.WILD_DRAWFOUR) {
				Game.drawfour(i, deck, hands);
			}

			// game play draw two logic
			if (deck.topDiscard().getValue() == CardsValue.DRAWTWO) {
				Game.drawtwo(i, deck, hands);
			}

			// game play reverse logic
			if (deck.topDiscard().getValue() == CardsValue.REVERSE) {
				reverse = Game.reverse(i, reverse);
			}

			System.out.printf("Player Number: %d \n", i + 1);
			System.out.println(hands.get(i));
			deck.replenish(); // checks if deck needs to be replenished
			Cards topDiscard = deck.topDiscard(); // getting the top card on the discard deck
			Cards card = ((Hand) hands.get(i)).hasMatch(topDiscard);
			if (card != null) { // if the player can play a card
				if (card.getValue() == CardsValue.WILD || card.getValue() == CardsValue.WILD_DRAWFOUR)
				// Requirement #5
				{
					System.out.println("***** A wild card has been played*****"); // requirement #5a
					for (int j = 0; j < hands.get(i).size(); j++) // requirement 5b
					{
						if (((Hand) hands.get(i)).eachCard(j).getColor() == CardsColor.BLUE) {
							blue++;
						} else if (((Hand) hands.get(i)).eachCard(j).getColor() == CardsColor.YELLOW) {
							yellow++;
						} else if (((Hand) hands.get(i)).eachCard(j).getColor() == CardsColor.RED) {
							red++;
						} else if (((Hand) hands.get(i)).eachCard(j).getColor() == CardsColor.GREEN) {
							green++;
						}
					}
					if (blue > green && blue > red && blue > yellow) {
						card.setColor(CardsColor.BLUE);
					} else if (green > red && green > yellow) {
						card.setColor(CardsColor.GREEN);
					} else if (red > yellow) {
						card.setColor(CardsColor.RED);
					} else {
						card.setColor(CardsColor.YELLOW);
					}
				}

				deck.addToDiscard(card); // takes the card the player played and puts it at the top of the
											// discard deck
				System.out.println("Card Played: " + deck.topDiscard());// requirement #4
				System.out.println(hands.get(i) + "\n");
				if (((Hand) hands.get(i)).isUno() == true) { // checks if player can call Uno
					System.out.printf("**Player %d calls UNO!**\n \n", i + 1);
				}
				if (((Hand) hands.get(i)).isWinner() == true) { // checks if player won
					matchwinner = true;
					System.out.printf("\n***Player %d Won***\n\n", i + 1);
					break;
				}
			} else { // if the player can't match a card they pick one up
				System.out.println("Card Played: Player doesn't have a match");// requirement #4
				((Hand) hands.get(i)).drawCard(deck.dealCard()); // player draws one card
				System.out.println(hands.get(i) + "\n");
			}
			// next player logic
			Game.nextplayer(i, reverse, num);
		}

		//scorecard functionality
		
		int runningtotal = Game.scorecard(i, hands, scorecard, matchnumber);
		scorecard[i] = runningtotal; 
		
		System.out.println("+++++++++++++++++++++++++++++");
		System.out.println("++++ UNO GAME SCORE CARD +++");
		System.out.println("+++++++++++++++++++++++++++++");
		for(int a = 0; a < num; a++) {
		System.out.println(scorecard[a]);
		}
		
		if(scorecard[i] >= 500 ) {
			gamewinner = true;
			System.out.printf("\n***Player %d Won the game with %d points***\n\n", i + 1, runningtotal);

		}
		return gamewinner;
	}
}
