package phase10.gui;


import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
import phase10.*;
import phase10.card.Card;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.Component;
import javax.swing.SpringLayout;
import javax.swing.Box;
import javax.swing.JScrollPane;


public class GameFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	Player current;
	ArrayList<Card> selectedCards = new ArrayList<Card>();

	//begin components
	private JPanel infoPanel = new JPanel(); //Panel that displays on the right side of GameFrame. Displays basic info about the current player
	private JPanel deckPanel = new JPanel(); //includes the deck and discard buttons. Displays on bottom-right corner
	private JPanel handPanel = new JPanel(); //displays all the cards in a player's hand
	private JPanel playersPanel = new JPanel(); //displays all of the current player's opponents
	private JPanel yourPhasesPanel = new JPanel(); //displays the current player's phases, or a button to add a phase if the current player has not yet laid down a phase in a round

	private JButton[] handButtons = new JButton[11]; //an array of buttons representing each card in the current player's hand
	private JLabel lblPlayername; //Displays the current player's name in infoPanel
	private JTextArea phaseNumber; //Displays the phase of the current player in infoPanel
	private JButton deckButton; //JButton that represents the top of the deck
	private JButton discardButton; //JButton that represents the top of the discard stack
	private JButton btnNewPhase; //a button in yourPhasesPanel that allows a player to lay down a phase
	private ArrayList<opponentPanel> oppPanels; //an arrayList of all the opponent panels
	private GuiManager gManage; //Reference to the GuiManager object
	private JButton pg1Start; //the first card of the current player's first phase group
	private JLabel lblTo; //a label that says to
	private JButton pg1End; //the last card of the current player's first phase group
	private JButton addToPG1; //a button that allows the user to add a card to their own phase group
	private JButton addToPG2; //a button that allows the user to add a card to their own phase group
	private JLabel lblTo2; //a label that says to
	private JButton pg2End; //the first card of the current player's second phase group
	private JButton pg2Start; //the last card of the current player's second phase group
	private JTextArea playerScore; //displays the current player's score in infoPanel
	private JLabel lblTurnMode; //displays right above the deck and the discard pile and tells the user if they are drawing or 
	//end components


	Language gameLang;

	/**
	 * Creates the GameFrame at the constructor
	 * 
	 * @param gManage a reference to the GuiManager
	 */
	public GameFrame(final GuiManager gManage) {
		setMinimumSize(new Dimension(600, 550));

		gameLang = gManage.getGameLang();
		this.gManage = gManage;
		current = gManage.mainManager.getGame().getCurrentPlayer();
		Phase10 currentGame = gManage.mainManager.getGame(); //added for simplicity of access
		
		//basic settings for the Game Frame
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameFrame.class.getResource("/images/GameIcon.png")));
		setTitle(current.getName() + " - " + gameLang.getEntry("PHASE_10"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1150, 668);
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, playersPanel, 11, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, playersPanel, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, playersPanel, -6, SpringLayout.NORTH, yourPhasesPanel);
		springLayout.putConstraint(SpringLayout.EAST, playersPanel, -6, SpringLayout.WEST, infoPanel);
		springLayout.putConstraint(SpringLayout.WEST, yourPhasesPanel, 0, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, yourPhasesPanel, 0, SpringLayout.WEST, infoPanel);
		springLayout.putConstraint(SpringLayout.SOUTH, infoPanel, -6, SpringLayout.NORTH, deckPanel);
		springLayout.putConstraint(SpringLayout.NORTH, infoPanel, 0, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, deckPanel, -97, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, yourPhasesPanel, -106, SpringLayout.NORTH, handPanel);
		springLayout.putConstraint(SpringLayout.SOUTH, yourPhasesPanel, 0, SpringLayout.NORTH, handPanel);
		springLayout.putConstraint(SpringLayout.WEST, deckPanel, 0, SpringLayout.EAST, handPanel);
		springLayout.putConstraint(SpringLayout.EAST, deckPanel, 0, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, handPanel, -97, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, handPanel, -162, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, infoPanel, -162, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, infoPanel, 0, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, handPanel, 0, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, deckPanel, 0, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, handPanel, 0, SpringLayout.SOUTH, getContentPane());
		getContentPane().setLayout(springLayout);
		infoPanel.setMinimumSize(new Dimension(10, 600));
		infoPanel.setMaximumSize(new Dimension(160, 32767));

		infoPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		getContentPane().add(infoPanel);
		SpringLayout sl_infoPanel = new SpringLayout();
		infoPanel.setLayout(sl_infoPanel);

		lblPlayername = new JLabel(current.getName());
		sl_infoPanel.putConstraint(SpringLayout.NORTH, lblPlayername, 20, SpringLayout.NORTH, infoPanel);
		sl_infoPanel.putConstraint(SpringLayout.EAST, lblPlayername, 156, SpringLayout.WEST, infoPanel);
		lblPlayername.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		infoPanel.add(lblPlayername);

		JLabel lblPhase = new JLabel(gameLang.getEntry("PHASE"));
		sl_infoPanel.putConstraint(SpringLayout.NORTH, lblPhase, 61, SpringLayout.NORTH, infoPanel);
		sl_infoPanel.putConstraint(SpringLayout.SOUTH, lblPlayername, -6, SpringLayout.NORTH, lblPhase);
		lblPhase.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		sl_infoPanel.putConstraint(SpringLayout.WEST, lblPhase, 21, SpringLayout.WEST, infoPanel);
		sl_infoPanel.putConstraint(SpringLayout.EAST, lblPhase, 147, SpringLayout.WEST, infoPanel);
		lblPhase.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhase.setFont(new Font("Tahoma", Font.PLAIN, 20));
		infoPanel.add(lblPhase);
		
		phaseNumber = new JTextArea();
		sl_infoPanel.putConstraint(SpringLayout.NORTH, phaseNumber, 94, SpringLayout.NORTH, infoPanel);
		sl_infoPanel.putConstraint(SpringLayout.WEST, phaseNumber, 50, SpringLayout.WEST, infoPanel);
		sl_infoPanel.putConstraint(SpringLayout.EAST, phaseNumber, -59, SpringLayout.EAST, infoPanel);
		sl_infoPanel.putConstraint(SpringLayout.SOUTH, lblPhase, -8, SpringLayout.NORTH, phaseNumber);
		sl_infoPanel.putConstraint(SpringLayout.SOUTH, phaseNumber, 147, SpringLayout.NORTH, infoPanel);
		phaseNumber.setRows(1);
		phaseNumber.setColumns(1);
		phaseNumber.setFont(new Font("Century Gothic", Font.BOLD, 36));

		//sets the phase number area with the current player's phase. Will display "null" if any error occurs
		try {
			phaseNumber.setText(Integer.toString(currentGame.getCurrentPlayer().getPhase()));
		} catch (NullPointerException e) {
			phaseNumber.setText("null");
		}
		phaseNumber.setEditable(false);
		infoPanel.add(phaseNumber);

		//the phase description button with it's button listener
		JButton btnPhaseDescription = new JButton(gameLang.getEntry("PHASE_DESCRIPTIONS"));
		btnPhaseDescription.setAlignmentY(Component.TOP_ALIGNMENT);
		sl_infoPanel.putConstraint(SpringLayout.WEST, btnPhaseDescription, 7, SpringLayout.WEST, infoPanel);
		sl_infoPanel.putConstraint(SpringLayout.EAST, btnPhaseDescription, -1, SpringLayout.EAST, infoPanel);
		sl_infoPanel.putConstraint(SpringLayout.WEST, lblPlayername, 0, SpringLayout.WEST, btnPhaseDescription);
		sl_infoPanel.putConstraint(SpringLayout.NORTH, btnPhaseDescription, 27, SpringLayout.SOUTH, phaseNumber);
		btnPhaseDescription.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				gManage.displayPhaseDescriptionFrame();
			}
		});
		infoPanel.add(btnPhaseDescription);

		
		JButton btnScoreboard = new JButton(gameLang.getEntry("SCOREBOARD"));
		sl_infoPanel.putConstraint(SpringLayout.NORTH, btnScoreboard, 285, SpringLayout.NORTH, infoPanel);
		btnScoreboard.setAlignmentY(Component.TOP_ALIGNMENT);
		sl_infoPanel.putConstraint(SpringLayout.WEST, btnScoreboard, 29, SpringLayout.WEST, infoPanel);
		sl_infoPanel.putConstraint(SpringLayout.EAST, btnScoreboard, -15, SpringLayout.EAST, infoPanel);
		btnScoreboard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				gManage.displayScoreFrame();
			}
		});
		infoPanel.add(btnScoreboard);

		JButton btnSave = new JButton(gameLang.getEntry("SAVE"));
		sl_infoPanel.putConstraint(SpringLayout.SOUTH, btnScoreboard, -13, SpringLayout.NORTH, btnSave);
		btnSave.setAlignmentY(Component.TOP_ALIGNMENT);
		sl_infoPanel.putConstraint(SpringLayout.NORTH, btnSave, 318, SpringLayout.NORTH, infoPanel);
		sl_infoPanel.putConstraint(SpringLayout.WEST, btnSave, 23, SpringLayout.WEST, infoPanel);
		sl_infoPanel.putConstraint(SpringLayout.SOUTH, btnSave, 341, SpringLayout.NORTH, infoPanel);
		sl_infoPanel.putConstraint(SpringLayout.EAST, btnSave, -15, SpringLayout.EAST, infoPanel);
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SaveFileFrame saverWindow = new SaveFileFrame(gManage);
				saverWindow.setVisible(true);

			}
		});
		infoPanel.add(btnSave);

		JButton btnExit = new JButton(gameLang.getEntry("EXIT"));
		sl_infoPanel.putConstraint(SpringLayout.NORTH, btnExit, 6, SpringLayout.SOUTH, btnSave);
		sl_infoPanel.putConstraint(SpringLayout.WEST, btnExit, 0, SpringLayout.WEST, lblPlayername);
		sl_infoPanel.putConstraint(SpringLayout.EAST, btnExit, 161, SpringLayout.WEST, infoPanel);
		btnExit.setAlignmentY(Component.TOP_ALIGNMENT);
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		infoPanel.add(btnExit);

		JLabel lblScore = new JLabel(gameLang.getEntry("SCORE"));
		lblScore.setAlignmentY(Component.TOP_ALIGNMENT);
		sl_infoPanel.putConstraint(SpringLayout.NORTH, lblScore, 203, SpringLayout.NORTH, infoPanel);
		sl_infoPanel.putConstraint(SpringLayout.SOUTH, btnPhaseDescription, -6, SpringLayout.NORTH, lblScore);
		sl_infoPanel.putConstraint(SpringLayout.WEST, lblScore, 15, SpringLayout.WEST, lblPhase);
		sl_infoPanel.putConstraint(SpringLayout.EAST, lblScore, 0, SpringLayout.EAST, lblPhase);
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setFont(new Font("Tahoma", Font.PLAIN, 16));
		infoPanel.add(lblScore);

		playerScore = new JTextArea();
		sl_infoPanel.putConstraint(SpringLayout.NORTH, playerScore, 230, SpringLayout.NORTH, infoPanel);
		sl_infoPanel.putConstraint(SpringLayout.WEST, playerScore, 50, SpringLayout.WEST, infoPanel);
		sl_infoPanel.putConstraint(SpringLayout.EAST, playerScore, -43, SpringLayout.EAST, infoPanel);
		playerScore.setText(Integer.toString(current.getScore()));
		playerScore.setRows(1);
		playerScore.setFont(new Font("Century Gothic", Font.BOLD, 36));
		playerScore.setEditable(false);
		playerScore.setColumns(1);
		infoPanel.add(playerScore);

		lblTurnMode = new JLabel(gameLang.getEntry("YOU_MAY_DRAW"));
		sl_infoPanel.putConstraint(SpringLayout.WEST, lblTurnMode, 25, SpringLayout.WEST, infoPanel);
		sl_infoPanel.putConstraint(SpringLayout.SOUTH, lblTurnMode, -8, SpringLayout.SOUTH, infoPanel);
		sl_infoPanel.putConstraint(SpringLayout.EAST, lblTurnMode, 143, SpringLayout.WEST, infoPanel);
		lblTurnMode.setHorizontalAlignment(SwingConstants.CENTER);
		lblTurnMode.setAlignmentY(Component.TOP_ALIGNMENT);
		infoPanel.add(lblTurnMode);
		handPanel.setMinimumSize(new Dimension(10, 100));
		handPanel.setMaximumSize(new Dimension(32767, 100));
		getContentPane().add(handPanel);
		handPanel.setLayout(new GridLayout(0, 11, 0, 0));

		for(int i = 0; i < handButtons.length; i++) {
			handButtons[i] = new JButton();
			
			//add an action listener
			handButtons[i].addActionListener(new HandActionListener(i));
			handPanel.add(handButtons[i]);
		}
		getContentPane().add(playersPanel);
		playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.X_AXIS));
		deckPanel.setMaximumSize(new Dimension(160, 100));
		deckPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		deckPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		getContentPane().add(deckPanel);
		deckPanel.setLayout(new GridLayout(0, 2, 0, 0));

		deckButton = new JButton("");
		deckButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		deckButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(current.getHasDrawnCard() == false) {
					gManage.mainManager.getGame().getRound().drawFromDeck();
					updateFrame(gManage.mainManager.getGame());
					discardButton.setEnabled(false);
				}
			}
		});
		deckButton.setIcon(new ImageIcon(GameFrame.class.getResource("/images/cardImages/card back.png")));
		deckPanel.add(deckButton);

		discardButton = new JButton("");
		discardButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if(!current.getHasDrawnCard()) {
					boolean isValidCard = gManage.mainManager.getGame().getRound().drawFromDiscard();
					if(isValidCard) {
						updateFrame(gManage.mainManager.getGame());
						deckButton.setEnabled(false);
						discardButton.setEnabled(false);
					}
					else {
						MessageFrame skipPickup = new MessageFrame(gameLang.getEntry("SKIP_PICKUP_MESSAGE"), gameLang.getEntry("INVALID_MOVE"), gameLang);
						skipPickup.setVisible(true);
					}
				}
				else { //player is discarding
					if(selectedCards.size() == 0) {
						MessageFrame noCard = new MessageFrame(gameLang.getEntry("NO_CARD_MESSAGE"), gameLang.getEntry("INVALID_MOVE"), gameLang);
						noCard.setVisible(true);
					}
					else {
						gManage.mainManager.getGame().getRound().discard(selectedCards.get(0));
					}
					updateFrame(gManage.mainManager.getGame());
					deckButton.setEnabled(true);
					discardButton.setEnabled(true);	
				}
			}
		});
		deckPanel.add(discardButton);

		oppPanels = new ArrayList<opponentPanel>();
		try {
			int i = 0;
			for(int x = 0; x < currentGame.getNumberOfPlayers(); x++) {
				if(currentGame.getPlayer(x) != currentGame.getCurrentPlayer()) {
					oppPanels.add(new opponentPanel(currentGame.getPlayer(x), gManage.mainManager.getGame(), this));
					playersPanel.add(oppPanels.get(i));
					i++;
				}
			}
		} catch (NullPointerException e) {
			System.out.println("null pointer exception generated when trying to display opponent Panels");
			playersPanel.add(new JLabel("Null"));
		}
		yourPhasesPanel.setMaximumSize(new Dimension(32767, 100));
		getContentPane().add(yourPhasesPanel);
		yourPhasesPanel.setLayout(null);

		btnNewPhase = new JButton(gameLang.getEntry("ADD_A_PHASE"));
		btnNewPhase.setVisible(false);
		btnNewPhase.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewPhase.addActionListener(new PhaseActionListener());
		btnNewPhase.setBounds(402, 42, 168, 23);
		yourPhasesPanel.add(btnNewPhase);

		pg1Start = new JButton("");
		pg1Start.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for(int x = 0; x < selectedCards.size(); x++) {
					boolean isValid = current.getPhaseGroup(0).addCardToBeginning(selectedCards.get(x));
					if(!isValid) {
						MessageFrame invalidAdd = new MessageFrame(gameLang.getEntry("INVALID_ADD_MESSAGE"), gameLang.getEntry("INVALID_MOVE"), gameLang);
						invalidAdd.setVisible(true);
						break;
					}
					else {
						hideAndClearSelectedCards();
						updateFrame(gManage.mainManager.getGame());
						updateYourPhasesPanel();
					}
				}
			}
		});
		pg1Start.setVisible(false);
		pg1Start.setPreferredSize(new Dimension(100, 23));
		pg1Start.setMaximumSize(new Dimension(114, 40));
		pg1Start.setMargin(new Insets(0, 0, 0, 0));
		pg1Start.setHorizontalTextPosition(SwingConstants.CENTER);
		pg1Start.setBounds(10, 0, 88, 107);
		yourPhasesPanel.add(pg1Start);

		pg1End = new JButton("");
		pg1End.setVisible(false);
		pg1End.setPreferredSize(new Dimension(100, 23));
		pg1End.setMaximumSize(new Dimension(114, 40));
		pg1End.setMargin(new Insets(0, 0, 0, 0));
		pg1End.setHorizontalTextPosition(SwingConstants.CENTER);
		pg1End.setBounds(179, 0, 88, 107);
		yourPhasesPanel.add(pg1End);

		pg2Start = new JButton("");
		pg2Start.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for(int x = 0; x < selectedCards.size(); x++) {
					boolean isValid = current.getPhaseGroup(1).addCard(selectedCards.get(x));
					if(!isValid) {
						MessageFrame invalidAdd = new MessageFrame(gameLang.getEntry("INVALID_ADD_MESSAGE"), gameLang.getEntry("INVALID_MOVE"), gameLang);
						invalidAdd.setVisible(true);
						break;
					}
					else {
						hideAndClearSelectedCards();
						updateFrame(gManage.mainManager.getGame());
						updateYourPhasesPanel();
					}
				}
			}
		});
		pg2Start.setVisible(false);
		pg2Start.setPreferredSize(new Dimension(100, 23));
		pg2Start.setMaximumSize(new Dimension(114, 40));
		pg2Start.setMargin(new Insets(0, 0, 0, 0));
		pg2Start.setHorizontalTextPosition(SwingConstants.CENTER);
		pg2Start.setBounds(707, 0, 88, 107);
		yourPhasesPanel.add(pg2Start);

		pg2End = new JButton("");
		pg2End.setVisible(false);
		pg2End.setPreferredSize(new Dimension(100, 23));
		pg2End.setMaximumSize(new Dimension(114, 40));
		pg2End.setMargin(new Insets(0, 0, 0, 0));
		pg2End.setHorizontalTextPosition(SwingConstants.CENTER);
		pg2End.setBounds(884, 0, 88, 107);
		yourPhasesPanel.add(pg2End);

		addToPG1 = new JButton(gameLang.getEntry("ADD_TO_PHASE"));
		addToPG1.addActionListener(new AddToPhaseListener(0));
		addToPG1.setVisible(false);
		addToPG1.setBounds(277, 27, 117, 52);
		yourPhasesPanel.add(addToPG1);

		addToPG2 = new JButton(gameLang.getEntry("ADD_TO_PHASE"));
		addToPG2.addActionListener(new AddToPhaseListener(1));
		addToPG2.setVisible(false);
		addToPG2.setBounds(580, 27, 117, 52);
		yourPhasesPanel.add(addToPG2);

		lblTo = new JLabel(gameLang.getEntry("TO"));
		lblTo.setVisible(false);
		lblTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTo.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTo.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblTo.setBounds(108, 25, 61, 53);
		yourPhasesPanel.add(lblTo);

		lblTo2 = new JLabel(gameLang.getEntry("TO"));
		lblTo2.setVisible(false);
		lblTo2.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTo2.setHorizontalAlignment(SwingConstants.CENTER);
		lblTo2.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblTo2.setBounds(805, 27, 61, 53);
		yourPhasesPanel.add(lblTo2);

		updateFrame(gManage.mainManager.getGame());
	}

	/**
	 * Updates the "yourPhases" sub-panel
	 */
	protected void updateYourPhasesPanel() {

		if(current.getHasDrawnCard()) {
			btnNewPhase.setVisible(true);
			addToPG1.setVisible(false);
			addToPG2.setVisible(false);
		}
		else {
			btnNewPhase.setVisible(false);
		}

		if(current.hasLaidDownPhase()) {

			btnNewPhase.setVisible(false);

			if(current.getNumberOfPhaseGroups() == 1) { //first phase group is visible
				pg1Start.setVisible(true);
				lblTo.setVisible(true);
				pg1End.setVisible(true);
				addToPG1.setVisible(true);

				lblTo.setToolTipText(current.getPhaseGroup(0).toString());

				pg2Start.setVisible(false);
				lblTo2.setVisible(false);
				pg2End.setVisible(false);
				addToPG2.setVisible(false);

				pg1Start.setIcon(new ImageIcon(GameFrame.class.getResource(
						getCardFile(current.getPhaseGroup(0).getCard(0)))));

				pg1End.setIcon(new ImageIcon(GameFrame.class.getResource(
						getCardFile(current.getPhaseGroup(0).getCard(current.getPhaseGroup(0).getNumberOfCards() - 1)))));
			}
			else { //first and second phase groups are visible

				pg1Start.setVisible(true);
				lblTo.setVisible(true);
				pg1End.setVisible(true);
				addToPG1.setVisible(true);

				lblTo.setToolTipText(current.getPhaseGroup(0).toString());

				pg2Start.setVisible(true);
				lblTo2.setVisible(true);
				pg2End.setVisible(true);
				addToPG2.setVisible(true);

				lblTo2.setToolTipText(current.getPhaseGroup(1).toString());

				pg1Start.setIcon(new ImageIcon(GameFrame.class.getResource(
						getCardFile(current.getPhaseGroup(0).getCard(0)))));

				pg1End.setIcon(new ImageIcon(GameFrame.class.getResource(
						getCardFile(current.getPhaseGroup(0).getCard(current.getPhaseGroup(0).getNumberOfCards() - 1)))));

				pg2Start.setIcon(new ImageIcon(GameFrame.class.getResource(
						getCardFile(current.getPhaseGroup(1).getCard(0)))));

				pg2End.setIcon(new ImageIcon(GameFrame.class.getResource(
						getCardFile(current.getPhaseGroup(1).getCard(current.getPhaseGroup(1).getNumberOfCards() - 1)))));
			}
		}
		else { //player has not laid down phase
			pg1Start.setVisible(false);
			lblTo.setVisible(false);
			pg1End.setVisible(false);
			addToPG1.setVisible(false);

			pg2Start.setVisible(false);
			lblTo2.setVisible(false);
			pg2End.setVisible(false);
			addToPG2.setVisible(false);
		}
	}

	/**
	 * goes through every selected card in selectedCards, unselects them and makes them invisible.
	 */
	protected void hideAndClearSelectedCards() {
		for(int i = 0; i < handButtons.length; i++) {
			handButtons[i].setVisible(true);
			handButtons[i].setSelected(false);
		}
		selectedCards.clear();
	}

	/**
	 * Will read information from a Card object and return the path to the correct
	 * image for that Card.
	 * 
	 * @param aCard the card object
	 * @return the filename to the image file of the specified card
	 */
	String getCardFile(Card aCard) {

		String filename = "/images/cardImages/";

		if(aCard.getColor().equals(Color.RED))
			filename += "Red";
		else if(aCard.getColor().equals(Color.BLUE))
			filename += "Blue";
		else if(aCard.getColor().equals(Color.YELLOW))
			filename += "Yellow";
		else if(aCard.getColor().equals(Color.GREEN))
			filename += "Green";

		if(aCard.getValue() == 13)
			filename += "Wild";
		else if(aCard.getValue() == 14)
			filename += "Skip";
		else
			filename += aCard.getValue();

		filename += ".png";

		return filename;
	}

	/**
	 * Will read information from a Card object and return the path to the correct
	 * SelectedCard image for that Card.
	 * 
	 * @param the card object
	 * @return the filename of the specified card's card image
	 */
	private String getSelectedCardFile(Card aCard) {
		String filename = getCardFile(aCard);
		filename = filename.substring(0, filename.length() - 4);
		filename += "Selected.png";

		return filename;
	}

	/**
	 * updates everything in GameFrame
	 * 
	 * @param currentGame a reference to the current game object
	 */
	public void updateFrame(Phase10 currentGame) {

		current = currentGame.getCurrentPlayer();

		//begin update of opponent panels
		int y = 0;
		for(int x = 0; x < currentGame.getNumberOfPlayers(); x++) {
			if(currentGame.getPlayer(x) != currentGame.getCurrentPlayer()) {
				oppPanels.get(y).updatePanel(currentGame.getPlayer(x));
				y++;
			}
		}

		updateCardImages();

		//end update of opponent panels

		updateYourPhasesPanel();

		/*
		 * begin update of cards
		 */

		for(int i = 0; i < handButtons.length; i++)
			handButtons[i].setSelected(false);

		selectedCards.clear();

		if(currentGame.getRound().getTopOfDiscardStack() == null) {
			discardButton.setIcon(new ImageIcon(GameFrame.class.getResource("/images/cardImages/NoCardsLeft.png")));

		}
		else {
			discardButton.setIcon(new ImageIcon(GameFrame.class.getResource(
					getCardFile(currentGame.getRound().getTopOfDiscardStack())
					)));
			discardButton.setEnabled(true);
		}

		deckButton.setEnabled(true);

		/*
		 * end update of cards
		 */


		//begin update of infoPanel

		lblPlayername.setText(current.getName());
		phaseNumber.setText(Integer.toString(current.getPhase()));
		playerScore.setText(Integer.toString(current.getScore()));

		if(current.getHasDrawnCard())
			lblTurnMode.setText(gameLang.getEntry("YOU_MAY_DISCARD"));
		else {
			lblTurnMode.setText(gameLang.getEntry("YOU_MAY_DRAW"));
		}

		//end update of infoPanel

	}


	/**
	 * Updates each of the card's selected and unselected images to the current configuration
	 * of the current player's hand
	 */
	private void updateCardImages() {

		Hand currentHand = current.getHand();

		//begin hand button update
		int i = 0;
		for(; i < currentHand.getNumberOfCards(); i++) {

			handButtons[i].setVisible(true);

			handButtons[i].setIcon(new ImageIcon(GameFrame.class.getResource(
					getCardFile(currentHand.getCard(i))
					)));

			handButtons[i].setSelectedIcon(new ImageIcon(GameFrame.class.getResource(
					getSelectedCardFile(currentHand.getCard(i))
					)));
		}

		for(; i < handButtons.length; i++) {
			handButtons[i].setVisible(false);
		}
		//end hand button update

		if(gManage.mainManager.getGame().getRound().getTopOfDiscardStack() == null){
			discardButton.setIcon(new ImageIcon(GameFrame.class.getResource("/images/cardImages/NoCardsLeft.png")));
		}
		else {	
			discardButton.setIcon(new ImageIcon(GameFrame.class.getResource(
					getCardFile(gManage.mainManager.getGame().getRound().getTopOfDiscardStack())
					)));
		}
	}

	/**
	 * 
	 * actionListener for all of the hand buttons in handPanel.
	 * 
	 * @author Matthew Hruz
	 *
	 */
	private class HandActionListener implements ActionListener {

		int i;

		/**
		 * constructor for HandActionListener
		 * 
		 * @param index of the handButton in the handButtons array
		 */
		public HandActionListener(int index) {
			i = index;
		}

		/**
		 * called when a hand button is clicked. The hand button will retrieve it's selected card image, or it's unselected
		 * card image if the card is already selected. It will also update the discard button's enabled setting as apropriate
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(handButtons[i].isSelected()) {
				selectedCards.remove(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(i));
				handButtons[i].setSelected(false);
			}
			else {
				selectedCards.add(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(i));
				handButtons[i].setSelected(true);
			}
			if(selectedCards.size() == 1) {
				discardButton.setEnabled(true);
			}
			else
				discardButton.setEnabled(false);
		}
	}

	/**
	 * 
	 * ActionListener to the "Add to Phase!" button.
	 * 
	 * @author Matthew Hruz
	 *
	 */
	private class PhaseActionListener implements ActionListener {

		private boolean isPhasing;
		private boolean isSecondPhaseGroup;
		private PhaseGroup newPhaseGroup;
		private PhaseGroup newPhaseGroup2;

		public PhaseActionListener() {
			isPhasing = false;
			isSecondPhaseGroup = false;
		}

		/**
		 * called when the "Add to Phase!" button is clicked. The button will display what cards need to be selected
		 * to add the first phase group and the second phase group if necessary. This method is also in charge of displaying
		 * an error message if the phase groups laid down are not valid.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {

			if(isPhasing) {
				if(isSecondPhaseGroup == false) {

					newPhaseGroup = new PhaseGroup(gManage.mainManager.getGame());

					for(Card c : selectedCards) {
						newPhaseGroup.addCard(c);
					}

					for(int i = 0; i < handButtons.length; i++) {
						if(handButtons[i].isSelected())
							handButtons[i].setVisible(false);
					}

					selectedCards.clear();

					switch(current.getPhase()) {
					case 1:
						btnNewPhase.setText(gameLang.getEntry("ADD_A") + " " + gameLang.getEntry("SET_OF") + " " + " 3");
						isSecondPhaseGroup = true;
						break;
					case 2:
						btnNewPhase.setText(gameLang.getEntry("ADD_A") + " " + gameLang.getEntry("RUN_OF") + " " + " 4");
						isSecondPhaseGroup = true;
						break;
					case 3:
						btnNewPhase.setText(gameLang.getEntry("ADD_A") + " " + gameLang.getEntry("RUN_OF") + " " + " 4");
						isSecondPhaseGroup = true;
						break;
					case 7:
						btnNewPhase.setText(gameLang.getEntry("ADD_A") + " " + gameLang.getEntry("SET_OF") + " " + " 4");
						isSecondPhaseGroup = true;
						break;
					case 9:
						btnNewPhase.setText(gameLang.getEntry("ADD_A") + " " + gameLang.getEntry("SET_OF") + " " + " 2");
						isSecondPhaseGroup = true;
						break;
					case 10:
						btnNewPhase.setText(gameLang.getEntry("ADD_A") + " " + gameLang.getEntry("SET_OF") + " " + " 3");
						isSecondPhaseGroup = true;
						break;
					default:
						btnNewPhase.setText(gameLang.getEntry("ADD_A_PHASE"));
						btnNewPhase.setVisible(false);
						isSecondPhaseGroup = false;
						boolean isValid = current.addPhaseGroups(newPhaseGroup);
						if(!isValid) {
							MessageFrame notAGoodPhase = new MessageFrame(gameLang.getEntry("NOT_A_GOOD_PHASE_MESSAGE"), gameLang.getEntry("INVALID_MOVE"), gameLang);
							notAGoodPhase.setVisible(true);

							for(int i = 0; i < handButtons.length; i++) {
								handButtons[i].setVisible(true);
							}

							selectedCards.clear();

							isPhasing = false;
							isSecondPhaseGroup = false;

							btnNewPhase.setText("Add a Phase!");
						}
						else { //the player played a valid phase with only one phase group in the phase
							pg1Start.setVisible(true);
							lblTo.setVisible(true);
							pg1End.setVisible(true);
							addToPG1.setVisible(true);

							pg1Start.setIcon((new ImageIcon(GameFrame.class.getResource(
									getCardFile(newPhaseGroup.getCard(0)))
									)));
							pg1End.setIcon((new ImageIcon(GameFrame.class.getResource(
									getCardFile(newPhaseGroup.getCard(newPhaseGroup.getNumberOfCards() - 1)))
									)));

							selectedCards.clear();

							btnNewPhase.setVisible(false);
							btnNewPhase.setText("Add a Phase!");

							updateFrame(gManage.mainManager.getGame());

							isPhasing = false;
							isSecondPhaseGroup = false;
							break;
						}
					}
				}
				else { //is adding the second phase group

					int secondGroupStartIndex = newPhaseGroup.getNumberOfCards();

					newPhaseGroup2 = new PhaseGroup(gManage.mainManager.getGame());
					//adding phases to the phaseGroup
					for(Card c : selectedCards)
						newPhaseGroup2.addCard(c);

					boolean isValid = current.addPhaseGroups(newPhaseGroup, newPhaseGroup2);
					System.out.println("Phase Group Type: " + newPhaseGroup.getType());
					if(!isValid) {
						MessageFrame notAGoodPhase = new MessageFrame(gameLang.getEntry("NOT_A_GOOD_PHASE_MESSAGE"), gameLang.getEntry("INVALID_MOVE"), gameLang);
						notAGoodPhase.setVisible(true);

						//set all buttons to visible and clear all selections
						hideAndClearSelectedCards();
						isPhasing = false;
						isSecondPhaseGroup = false;
						btnNewPhase.setText(gameLang.getEntry("ADD_A_PHASE"));
					}
					else { //the player played a valid phase
						pg1Start.setVisible(true);
						lblTo.setVisible(true);
						pg1End.setVisible(true);
						addToPG1.setVisible(true);

						pg1Start.setIcon((new ImageIcon(GameFrame.class.getResource(
								getCardFile(newPhaseGroup.getCard(0)))
								)));
						pg1End.setIcon((new ImageIcon(GameFrame.class.getResource(
								getCardFile(newPhaseGroup.getCard(secondGroupStartIndex - 1)))
								)));


						pg2Start.setVisible(true);
						lblTo2.setVisible(true);
						pg2End.setVisible(true);
						addToPG2.setVisible(true);

						pg2Start.setIcon((new ImageIcon(GameFrame.class.getResource(
								getCardFile(newPhaseGroup2.getCard(0)))
								)));
						pg2End.setIcon((new ImageIcon(GameFrame.class.getResource(
								getCardFile(newPhaseGroup2.getCard(newPhaseGroup2.getNumberOfCards()-1)))
								)));

						updateFrame(gManage.mainManager.getGame());
						selectedCards.clear();

						btnNewPhase.setVisible(false);
						btnNewPhase.setText("Add a Phase!");

						isPhasing = false;
						isSecondPhaseGroup = false;
					}
				}
			}
			else{ //is not phasing
				isPhasing = true;
				isSecondPhaseGroup = false;
				switch(current.getPhase()) {
				case 1:
					btnNewPhase.setText(gameLang.getEntry("ADD_A") + " " + gameLang.getEntry("SET_OF") + " " + " 3");
					break;
				case 2:
					btnNewPhase.setText(gameLang.getEntry("ADD_A") + " " + gameLang.getEntry("SET_OF") + " " + " 3");
					break;
				case 3:
					btnNewPhase.setText(gameLang.getEntry("ADD_A") + " " + gameLang.getEntry("SET_OF") + " " + " 4");
					break;
				case 4:
					btnNewPhase.setText(gameLang.getEntry("ADD_A") + " " + gameLang.getEntry("RUN_OF") + " " + " 7");
					break;
				case 5:
					btnNewPhase.setText(gameLang.getEntry("ADD_A") + " " + gameLang.getEntry("RUN_OF") + " " + " 8");
					break;
				case 6:
					btnNewPhase.setText(gameLang.getEntry("ADD_A") + " " + gameLang.getEntry("RUN_OF") + " " + " 9");
					break;
				case 7:
					btnNewPhase.setText(gameLang.getEntry("ADD_A") + " " + gameLang.getEntry("SET_OF") + " " + " 4");
					break;
				case 8:
					btnNewPhase.setText(gameLang.getEntry("PHASE_8_ADD_BUTTON"));
					break;
				case 9:
					btnNewPhase.setText(gameLang.getEntry("ADD_A") + " " + gameLang.getEntry("SET_OF") + " " + " 5");
					break;
				case 10:
					btnNewPhase.setText(gameLang.getEntry("ADD_A") + " " + gameLang.getEntry("SET_OF") + " " + " 5");
					break;

				default:
					System.out.println("Error! phase number out of bounds in GameFrame");
					break;
				}
			}
		}
	}

	/**
	 * The action listener for both of the "Add to phase" buttons in yourPhasesPanel.
	 * 
	 * 
	 * @author Matthew Hruz
	 *
	 */
	private class AddToPhaseListener implements ActionListener {

		private int phaseGroupIndex;

		/**
		 * The constructor for AddToPhaseListener
		 * 
		 * @param phaseGroup the phase group that the cards will be added to.
		 */
		public AddToPhaseListener(int phaseGroup) {
			this.phaseGroupIndex = phaseGroup;
		}
		/**
		 * called when the "add to phase" button is clicked. Will add the cards one by one to a phase group and will
		 * display a message if a card that is being added is not valid, in which case it will not add the remaining cards
		 * or the card that caused the error
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(current.getHasDrawnCard()) {
				for(int x = 0; x < selectedCards.size(); x++) {
					boolean isValid = current.getPhaseGroup(phaseGroupIndex).addCard(selectedCards.get(x));
					if(!isValid) {
						MessageFrame invalidAdd = new MessageFrame(gameLang.getEntry("INVALID_ADD_MESSAGE"), gameLang.getEntry("INVALID_MOVE"), gameLang);
						invalidAdd.setVisible(true);
						break;
					}
					else {
						hideAndClearSelectedCards();
						updateFrame(gManage.mainManager.getGame());
						updateYourPhasesPanel();
					}
				}

			}
		}
	}
}