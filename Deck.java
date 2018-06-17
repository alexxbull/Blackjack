/*
    Author: Alexx Bull
	
    Credits to Byron Knoll for the card images:
    https://byronknoll.blogspot.com/2011/03/vector-playing-cards.html
*/

import java.util.ArrayList;
import java.util.Random;

public class Deck{
    private ArrayList<Card> deck;

    public Deck(){
        deck = new ArrayList<>(52);
        shuffle();
    }

    public void makeDeck()
    {
        String suit = "";
        for (int i = 0; i < 4; i++)
        {
            if (i == 0)
                suit = "spades";
            else if (i == 1)
                suit = "hearts";
            else if (i == 2)
                suit = "diamonds";
            else if (i == 3)
                suit = "clubs";

            for (int k = 0; k < 13; k++)
            {
                if (k == 0 || k % 13 == 0)
                    deck.add(new Card(suit, "ace", 11));
                else if (k < 10)
                    deck.add(new Card(suit, "" + (k+1), (k+1)));

                else if (k >= 10)
                {
                    switch(k)
                    {
                        case 10:
                            deck.add(new Card(suit, "jack", 10));
                            break;
                        case 11:
                            deck.add(new Card(suit, "queen", 10));
                            break;
                        case 12:
                            deck.add(new Card(suit, "king", 10));
                            break;
                    }
                }
            }
        }
    }

    public Card draw(){
        Random rand = new Random();
        int i = rand.nextInt(deck.size());
        Card c = deck.get(i);
        deck.remove(i);
        return c;
    }

    public void shuffle(){
        deck.clear();
        makeDeck();
        Random rand = new Random();
        for (int i = 0, k = 0; i < deck.size(); i++)
        {
            i = rand.nextInt(deck.size());
            k = rand.nextInt(deck.size());
            swap(i, k);
        }
    }

    private void swap(int i, int k){
        Card c = deck.get(i);
        deck.set(i, deck.get(k));
        deck.set(k, c);
    }

    public String toString(){
        String spades = "Spades", hearts = "Hearts", diamonds = "Diamonds", clubs = "Clubs";
        String s = "", h = "", d = "", c = "";

        for (int i = 0; i < deck.size(); i++)
        {
            if (deck.get(i).getSuit() == spades)
                s += deck.get(i).getFace() + " ";
            else if (deck.get(i).getSuit() == hearts)
                h += deck.get(i).getFace() + " ";
            else if (deck.get(i).getSuit() == diamonds)
                d += deck.get(i).getFace() + " ";
            else if (deck.get(i).getSuit() == clubs)
                c += deck.get(i).getFace() + " ";
        }
        return String.format("Spades: %s %nHearts: %s %nDiamonds: %s %nClubs: %s", s, h, d, c);
    }
}
