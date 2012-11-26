package phase10.gui;


import java.awt.EventQueue;

import phase10.*;
import phase10.gui.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;

public class ScoreFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScoreFrame frame = new ScoreFrame(new GuiManager(new GameManager()));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ScoreFrame(GuiManager gManage) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ScoreFrame.class.getResource("/images/GameIcon.png")));
		setResizable(false);
		setTitle("Scores after round + roundNum");
		setBounds(100, 100, 458, 396);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		int numPlayers = gManage.mainManager.getGame().getNumberOfPlayers();
		
		Player sortedPlayers[] = new Player[numPlayers];
		for(int i = 0; i < numPlayers; i++) {
			sortedPlayers[i] = gManage.mainManager.getGame().getPlayer(i);
		}
		
		sortedPlayers = sortPlayers(sortedPlayers);
		Object[][] playersArray = new Object[sortedPlayers.length][4];
		for(int i = 0; i < sortedPlayers.length; i++){
			playersArray[i][0] = i+1;
			playersArray[i][1] =  sortedPlayers[i].getName();
			playersArray[i][2] = sortedPlayers[i].getPhase();
			playersArray[i][3] = sortedPlayers[i].getScore();
		}
		
		table = new JTable();
		table.setRowHeight(48);
		table.setShowHorizontalLines(true);
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(
			playersArray,
			new String[] {
				"Place", "Name", "Current Phase", "Score"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, Integer.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(85);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.setBounds(36, 54, 370, 195);
		contentPane.add(table);
		
		JLabel lblPlace = new JLabel("Place");
		lblPlace.setBounds(53, 29, 46, 14);
		contentPane.add(lblPlace);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(142, 29, 46, 14);
		contentPane.add(lblName);
		
		JLabel lblCurrentPhase = new JLabel("Current Phase");
		lblCurrentPhase.setBounds(220, 29, 88, 14);
		contentPane.add(lblCurrentPhase);
		
		JLabel lblScore = new JLabel("Score");
		lblScore.setBounds(333, 29, 46, 14);
		contentPane.add(lblScore);
		
		JButton btnOkay = new JButton("Okay");
		btnOkay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			}
		});
		btnOkay.setBounds(174, 300, 89, 23);
		contentPane.add(btnOkay);
	}

	private Player[] sortPlayers(Player[] thePlayers) {

		for(int j = thePlayers.length-1; j > 0; j--){
			int max = thePlayers[0].getScore();
			Player pMax = thePlayers[0];
			int maxIndex = 0;
			for(int i = 1; i <= j; i++) {
				if(thePlayers[i].getScore() > max) {
					max = thePlayers[i].getScore();
					pMax = thePlayers[i];
					maxIndex = i;
				}
			}
			thePlayers[maxIndex] = thePlayers[j];
			thePlayers[j] = pMax;
		}
		return thePlayers;
	}
}