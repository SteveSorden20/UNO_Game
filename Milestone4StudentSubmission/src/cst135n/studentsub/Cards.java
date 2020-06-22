package cst135n.studentsub;

public class Cards {
	public Cards(CardsValue value, CardsColor color) {
		super();
		this.value = value;
		this.color = color;
	}

	public CardsValue getValue() {
		return value;
	}

	public CardsColor getColor() {
		return color;
	}

	public boolean isMatch(Cards card) {
		if (color == card.color || value == card.value || value == CardsValue.WILD || value == CardsValue.WILD_DRAWFOUR) //requirement #5
			{
			return true;
		}

		return false;
	}
	
	
	//**** refactored for scoring points *****
	public int getScore() {
		int a = 0;
		switch (value) {
		case DRAWTWO:
		case REVERSE:
		case SKIP:
			a = 20;
			break;
		case EIGHT:
			a = 8;
			break;
		case FIVE:
			a = 5;
			break;
		case FOUR:
			a = 4;
			break;
		case NINE:
			a = 9;
			break;
		case ONE:
			a = 1;
			break;
		case SEVEN:
			a = 7;
			break;
		case SIX:
			a = 6;
			break;
		case THREE:
			a = 3;
			break;
		case TWO:
			a = 2;
			break;
		case WILD:
			a = 50;
			break;
		case WILD_DRAWFOUR:
			a = 50;
			break;
		case ZERO:
			a = 0;
			break;

		}return a;
	}

	private CardsValue value;
	private CardsColor color;

	@Override
	public String toString() {
		return "Cards [value=" + value + ", color=" + color + "]";
	}

	public void setColor(CardsColor color) {
		this.color = color;
	}

	
}
