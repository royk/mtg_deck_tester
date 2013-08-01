package com.example.mtg_deck_tester;

/**
 * Created with IntelliJ IDEA.
 * User: RoyK
 * Date: 02/08/13
 * Time: 01:03
 * To change this template use File | Settings | File Templates.
 */
public class Card {
    public CardType type;

    public enum CardType {
        Land,
        Creature,
        Other
    }

}
