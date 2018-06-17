/*
    Author: Alexx Bull
	
    Credits to Byron Knoll for the card images:
    https://byronknoll.blogspot.com/2011/03/vector-playing-cards.html
*/

import java.util.ArrayList;

import java.awt.*;
import javax.swing.*;

public class Hand {
    private ArrayList<Card> hand;
    private Deck deck;

    public Hand(Deck deck){
        hand = new ArrayList<>();
        this.deck = deck;
    }

    public Card draw(){
        Card c = deck.draw();
        hand.add(c);
        return c;
    }

    public int value(){
        int total = 0;
        for (Card c: hand){
            total += c.getValue();
        }
        return total;
    }

    public boolean hasBlackjack(){
        if (hand.size() == 2)
        {
            boolean ace = false, ten = false;
            if (hand.get(0).getFace().equals("ace") || hand.get(1).getFace().equals("ace"))
                ace = true;

            if (hand.get(0).getValue() == 10 || hand.get(1).getValue() == 10)
                ten = true;

            if (ace && ten)
                return true;
        }
        return false;
    }

    // checks if the hand contain's an ace with value 11 (so not changed to one)
    public boolean hasAce(){
        for (Card c: hand)
        {
            if (c.getFace() == "ace" && c.getValue() == 11)
                return true;
        }
        return false;
    }

    // bonus, if the hand has bust but has an Ace, change the Ace value from 11 to 1
    public void aceToOne(){
        for (Card c: hand)
        {
            if (c.getFace() == "ace" && c.getValue() == 11)
            {
                c.setValue(1);
                break;
            }
        }
    }

    public JPanel show(){
        JPanel cards = new JPanel();
        for (Card c: hand)
            cards.add(c.getCardImage());
        return cards;
    }

    public String toString(){
        for (Card c: hand)
            System.out.println(c);
        return String.format("<%d points>", value());
    }
}
