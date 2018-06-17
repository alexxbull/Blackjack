/*
	Author: Alexx Bull
	
    Credits to Byron Knoll for the card images:
    https://byronknoll.blogspot.com/2011/03/vector-playing-cards.html
*/
import java.util.Scanner;
import java.lang.Thread;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Blackjack{
    private int wins, games;
    private boolean stand, playAgain;
    private Deck deck;
    private Hand player, dealer;

    private JFrame window;
    private JPanel windowPanel, buttonsPanel, playerPanel;
    private JTextPane playerBox, statsBox;
    private JButton hitButton, standButton, yesButton, noButton;
    private final Color green = new Color(46,139,87); // seagreen

    public Blackjack(){
        stand = false;
        playAgain = false;
        deck = new Deck();

        window = new JFrame();
        window.setTitle("Welcome to Bull's Blackjack Table");
        window.setPreferredSize(new Dimension(800,800));
        window.setMinimumSize(window.getPreferredSize());
        window.setLocationRelativeTo(null);     // center window in middle of screen
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        windowPanel = new JPanel();
        windowPanel.setLayout(new BoxLayout(windowPanel, BoxLayout.Y_AXIS));
        windowPanel.setBackground(green);

        playerBox = new JTextPane();
        playerBox.setPreferredSize(new Dimension(400,50));
        playerBox.setMaximumSize(playerBox.getPreferredSize());
        playerBox.setEditable(false);
        playerBox.setBackground(green);
        playerBox.setFont(new Font("Courier", Font.BOLD, 16));

        statsBox = new JTextPane();
        statsBox.setPreferredSize(new Dimension(200,300));
        statsBox.setMaximumSize(statsBox.getPreferredSize());
        statsBox.setEditable(false);
        statsBox.setBackground(green);
        statsBox.setFont(new Font("Courier", Font.BOLD, 16));

        window.add(windowPanel);
        window.setVisible(true);
    }

    public static void main(String[] args){
        Blackjack bj = new Blackjack();
        bj.start();
    }

    public void start(){
        wins = 0;
        games = 0;

        while (true)
        {
            playAgain = false;
            stand = false;
            deck.shuffle();
            makeButtons();
            playPlayer();
            playDealer();
            results();
            stats(wins, games);
            retry();
        }
    }

    public void makeButtons(){
        buttonsPanel = new JPanel();
        buttonsPanel.setBackground(green);
        ButtonListener bL = new ButtonListener();

        hitButton = new JButton("HIT");
        hitButton.setBackground(Color.PINK);
        hitButton.addActionListener(bL);

        standButton = new JButton("STAND");
        standButton.setBackground(Color.yellow);
        standButton.addActionListener(bL);

        buttonsPanel.add(hitButton);
        buttonsPanel.add(standButton);

        buttonsPanel.setPreferredSize(new Dimension(150,50));
        buttonsPanel.setMaximumSize(buttonsPanel.getPreferredSize());
        windowPanel.add(buttonsPanel);

        yesButton = new JButton("Yes");
        yesButton.setBackground(Color.white);
        yesButton.addActionListener(bL);

        noButton = new JButton("No");
        noButton.setBackground(Color.white);
        noButton.addActionListener(bL);
    }

    public void playPlayer(){
        player = new Hand(deck);
        player.draw();
        player.draw();

        playerBox.setText("Player\nPoints: " + player.value());

        playerPanel = player.show();
        playerPanel.setBackground(green);

        windowPanel.add(playerBox);
        windowPanel.add(playerPanel);
        updateGUI();

        // edge case where hand dealt is two aces (normally 22 pts)
        if (player.value() > 21 && player.hasAce()){
            player.aceToOne();
            playerBox.setText("Player\nPoints: " + player.value() + " - Changing Ace Value to 1");
            delay(1);
            updateGUI();
        }


        while (!stand)    // wait until player has finished playing
            delay(1);

        if (player.value() > 21) {
            playerBox.setText("Player\nPoints: " + player.value() + " - BUST!");
            updateGUI();
        }
    }

    public void playDealer(){
        dealer = new Hand(deck);
        dealer.draw();
        dealer.draw();

        JTextPane dealerBox = new JTextPane();
        dealerBox.setPreferredSize(new Dimension(400,50));
        dealerBox.setMaximumSize(dealerBox.getPreferredSize());
        dealerBox.setEditable(false);
        dealerBox.setBackground(green);
        dealerBox.setFont(new Font("Courier", Font.BOLD, 16));
        dealerBox.setText("Dealer\nPoints: " + dealer.value());

        JPanel dealerPanel = dealer.show();
        dealerPanel.setBackground(green);

        windowPanel.add(dealerBox);
        windowPanel.add(dealerPanel);
        updateGUI();
        delay(1);

        // edge case where hand dealt is two aces (normally 22 pts)
        if (dealer.value() > 21 && dealer.hasAce()){
            dealer.aceToOne();
            dealerBox.setText("Dealer\nPoints: " + dealer.value() + " - Changing Ace Value to 1");
            updateGUI();
            delay(1);
        }

        while (dealer.value() <= player.value() && dealer.value() < 21 && player.value() <= 21 && !dealer.hasBlackjack())
        {
            dealer.draw();
            dealerPanel = replacePanel(dealerPanel, dealer.show());
            dealerBox.setText("Dealer\nPoints: " + dealer.value());
            updateGUI();

            if (dealer.value() > 21 && dealer.hasAce()){
                dealer.aceToOne();
                dealerBox.setText("Dealer\nPoints: " + dealer.value() + " - Changing Ace Value to 1");
                updateGUI();
            }
            delay(1);
        }
        if (dealer.value() > 21)
            dealerBox.setText("Dealer\nPoints: " + dealer.value() + " - BUST!");

    }

    public void results(){
        JTextPane resultsBox = new JTextPane();
        resultsBox.setPreferredSize(new Dimension(200,50));
        resultsBox.setMaximumSize(resultsBox.getPreferredSize());
        resultsBox.setEditable(false);
        resultsBox.setBackground(green);
        resultsBox.setFont(new Font("Courier", Font.BOLD, 16));

        int dVal = dealer.value();
        int pVal = player.value();
        String text = "";

        if (dealer.hasBlackjack() || dVal == 21 || pVal > 21 || dVal == pVal || (dVal > pVal && dVal <= 21))
        {
            if (dealer.hasBlackjack())
                text = "The dealer wins with Blackjack.";
            else
                text = "The dealer wins!";
        }
        else if (dVal > 21)
        {
            if (player.hasBlackjack())
                text = "You won with Blackjack!";
            else
                text = "Hooray, you win!";
            wins++;
        }

        resultsBox.setText(text);
        windowPanel.add(resultsBox);
        updateGUI();
        delay(1);
        games++;
    }

    public void stats(int wins, int games){
        statsBox.setText("\n-------------------\n"
                        + "   Statistics"
                        + "\nGames Played: " + games
                        + String.format("%nWin Rate: %.2f%% %n", ((wins * 1.0) / games) * 100)
                        + "-------------------\n");
        windowPanel.add(statsBox);
        updateGUI();
    }

    public void retry(){
        JPanel retryPanel = new JPanel();
        retryPanel.setBackground(green);

        JTextPane retryBox = new JTextPane();
        retryBox.setPreferredSize(new Dimension(120,50));
        retryBox.setMaximumSize(retryBox.getPreferredSize());
        retryBox.setEditable(false);
        retryBox.setBackground(green);
        retryBox.setFont(new Font("Courier", Font.BOLD, 16));
        retryBox.setText("Play again?");

        retryPanel.add(yesButton);
        retryPanel.add(noButton);

        windowPanel.add(retryBox);
        windowPanel.add(retryPanel);
        updateGUI();

        while(!playAgain)
            delay(1);
    }

    public void delay(int t){
        t *= 1000;  // convert t to seconds
        try{
            Thread.sleep(t);  // sleep for 2 seconds so player can see dealer's new hand
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateGUI(){
        windowPanel.repaint();
        windowPanel.revalidate();
    }

    public JPanel replacePanel(JPanel old, JPanel current){
        windowPanel.remove(old);
        current.setBackground(green);
        windowPanel.add(current);
        return current;
    }

    private class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == hitButton && !stand)
            {
                player.draw();
                playerBox.setText("Player\nPoints: " + player.value());
                playerPanel = replacePanel(playerPanel, player.show());
                updateGUI();

                if (player.value() > 21 && player.hasAce()){
                    player.aceToOne();
                    playerBox.setText("Player\nPoints: " + player.value() + " - Changing Ace Value to 1");
                    updateGUI();
                }

                if (player.value() > 21)
                    stand = true;
            }
            else if (e.getSource() == standButton)
                stand = true;
            else if (e.getSource() == yesButton)
            {
                player = null;
                dealer = null;
                windowPanel.removeAll();
                updateGUI();
                playAgain = true;
            }
            else if (e.getSource() == noButton)
                System.exit(0);
        }
    }
}
