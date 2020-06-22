package cst135n.studentsub;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	public Deck() {
		super();
		deck = new ArrayList<Cards>();
		discard = new ArrayList<Cards>();
	}
	//adding the needed cards to the deck
	public void populate()
	{
		int counter=0;
		for(CardsColor color : CardsColor.values())
		{
			if (color != CardsColor.BLACK) { //excluding wild cards
				for(CardsValue value : CardsValue.values())
				{
					if (value != CardsValue.WILD && value != CardsValue.WILD_DRAWFOUR) { //excluding wilds
						deck.add(new Cards(value, color));
						counter++;
					}
				}
			}
		}
		// the for loops are repeated because the uno deck has two of each card 1-9 and skip, reverse, and drawtwo
		for(CardsColor color : CardsColor.values())
		{
			if (color != CardsColor.BLACK) { //excluding wild cards
				for(CardsValue value : CardsValue.values())
				{
					if(value != CardsValue.ZERO && value != CardsValue.WILD 
							&& value != CardsValue.WILD_DRAWFOUR) { // an Uno deck only has 4 zeros also exludes wilds
						deck.add(new Cards(value, color));
						counter++;
					}
				}
			}
		}
		
		//Adding Wild Cards
		deck.add(new Cards(CardsValue.WILD, CardsColor.BLACK));
		deck.add(new Cards(CardsValue.WILD, CardsColor.BLACK));
		deck.add(new Cards(CardsValue.WILD, CardsColor.BLACK));
		deck.add(new Cards(CardsValue.WILD, CardsColor.BLACK));
		counter+=4;
		
		//Adding Wild Draw Fours
		deck.add(new Cards(CardsValue.WILD_DRAWFOUR, CardsColor.BLACK));
		deck.add(new Cards(CardsValue.WILD_DRAWFOUR, CardsColor.BLACK));
		deck.add(new Cards(CardsValue.WILD_DRAWFOUR, CardsColor.BLACK));
		deck.add(new Cards(CardsValue.WILD_DRAWFOUR, CardsColor.BLACK));
		counter+=4;
		
	
		System.out.println(counter);

	}
	

	//shuffling the deck
	public void shuffle() 
	{
		Collections.shuffle(deck);
	}
	
	//dealing the cards from the bottom of the deck 
	public Cards dealCard()
	{
		return deck.remove(deck.size()-1);
	}
	
	//creating the discard pile - the starting card
	public ArrayList<Cards> discardPile()
	{
		Cards firstcard=deck.remove(deck.size()-1);
		discard.add(firstcard);
		return discard;
		
	}
	
	
	public void replenish() {
		if (deck.size()==0) {
			Cards remainingCard=discard.remove(discard.size()-1); //getting the top card from the discard pile
			deck.addAll(discard); //moving the discard pile to the deck
			Collections.shuffle(deck); // shuffling the deck
			discard.clear();
			discard.add(remainingCard); //putting the top card back into the discard pile
		}	
	}
	
	public Cards topDiscard(){
		return discard.get(discard.size()-1);
	}
	
	public void addToDiscard(Cards card) {
		discard.add(card);
	}
	
	private ArrayList<Cards> deck;
	private ArrayList<Cards> discard;
	

	@Override
	public String toString() {
		return "Deck [deck=" + deck + "]";
	}


}
