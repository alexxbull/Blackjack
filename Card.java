/*
    Author: Alexx Bull
	
    Credits to Byron Knoll for the card images:
    https://byronknoll.blogspot.com/2011/03/vector-playing-cards.html
*/

import java.awt.*;
import javax.swing.*;

public class Card {
    private String suit, face, cardPath;
    private int value;

    public final int X = 80, Y = 116;

    public Card(String suit, String face, int value){
        this.suit = suit;
        this.face = face;
        this.value = value;
        cardPath = "Assets\\Cards\\" + face + "_of_" + suit + ".png";
    }

    public String getSuit(){
        return suit;
    }

    public String getFace(){
        return face;
    }

    public void setValue(int v){
        value = v;
    }

    public int getValue(){
        return value;
    }

    public JLabel getCardImage(){
        ImageIcon image = new ImageIcon(getClass().getResource(cardPath));
        Image scaledImage = image.getImage();
        scaledImage = scaledImage.getScaledInstance(X, Y, Image.SCALE_SMOOTH);
        image = new ImageIcon(scaledImage);
        return new JLabel(image);
    }

    public String toString(){
        return String.format("%s of %s", face, suit);
    }
}
