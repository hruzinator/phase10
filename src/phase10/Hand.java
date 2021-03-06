/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */

package phase10;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import phase10.card.Card;
import phase10.card.CardColorComparator;
import phase10.card.CardValueComparator;
import phase10.exceptions.Phase10Exception;

/**
 * This class contains and manages the cards in each player's hand
 * 
 * @author Evan Forbes
 */
public final class Hand implements Serializable {

	private static final long serialVersionUID = 20121L;

	private ArrayList<Card> cards;
	private Phase10 game;
	private Player owner;

	/**
	 * Creates an empty hand object, with no cards
	 */
	Hand(Phase10 g, Player own) {
		cards = new ArrayList<Card>();
		game = g;
		owner = own;
	}

	/**
	 * Adds a card to the hand
	 * 
	 * @param card
	 *            the card to add
	 */
	void addCard(Card card) {
		cards.add(card);
	}

	/**
	 * Removes a card from the hand
	 * 
	 * @param card
	 *            the card to remove
	 */
	void removeCard(int card) {
		cards.remove(card);
	}

	/**
	 * Removes a card from the hand
	 * 
	 * @param card
	 *            the card to remove
	 * @throws Phase10Exception
	 *             if the card is not in the hand
	 */
	void removeCard(Card card) {
		boolean removed = cards.remove(card);
		if (!removed) {
			throw new Phase10Exception(
					"Attempt to remove card that does not exist in the hand");
		}
	}

	/**
	 * @return the number of cards in the hand
	 */
	public int getNumberOfCards() {
		return cards.size();
	}

	/**
	 * Gets a card at the specified index from the player's hand
	 * 
	 * @param cardIndex
	 *            the index to get the card at
	 * @return the card, if it exists (null otherwise)
	 * @throws Phase10Exception
	 *             if you try to get cards when it isn't the player's turn.
	 */
	public Card getCard(int cardIndex) {
		if (owner != game.getCurrentPlayer())
			throw new Phase10Exception(
					"Cannot get cards from player who's turn it currently isn't: player "
							+ owner);
		Card out = null;
		try {
			out = cards.get(cardIndex);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid hand index: " + cardIndex);
		}
		return out;
	}

	/**
	 * Gets a card at the specified index from the player's hand
	 * 
	 * @param cardIndex
	 *            the index to get the card at
	 * @return the card, if it exists (null otherwise)
	 */
	Card getAnyCard(int cardIndex) {
		Card out = null;
		try {
			out = cards.get(cardIndex);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid hand index: " + cardIndex);
		}
		return out;
	}

	/**
	 * Sorts the hand by color, then value
	 */
	public void sortByColor() {
		CardColorComparator colorComp = new CardColorComparator();
		Collections.sort(cards, colorComp);
	}

	/**
	 * Sorts the hand by value, then color
	 */
	public void sortByValue() {
		CardValueComparator valueComp = new CardValueComparator();
		Collections.sort(cards, valueComp);
	}

	/**
	 * Returns a string representation of the hand
	 */
	public String toString() {
		StringBuilder out = new StringBuilder("Length: " + cards.size()
				+ " Hand: ");
		for (Card e : cards) {
			out.append(e + ", ");
		}
		return out.toString();
	}

}
