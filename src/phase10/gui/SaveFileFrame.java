package phase10.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import phase10.GameManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
import javax.swing.JScrollPane;

public class SaveFileFrame extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField filenameField;
	private String filename;

	public String getFilename() {
		return filename;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SaveFileFrame dialog = new SaveFileFrame(new GuiManager(new GameManager()));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SaveFileFrame(GuiManager guiM) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SaveFileFrame.class.getResource("/images/GameIcon.png")));
		
		final GuiManager gManage = guiM;
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			filenameField = new JTextField();
			filenameField.setBounds(114, 204, 315, 20);
			contentPanel.add(filenameField);
			filenameField.setColumns(10);
		}
		{
			JTextPane promptField = new JTextPane();
			promptField.setBounds(5, 204, 101, 20);
			promptField.setEditable(false);
			promptField.setText("Enter a filename:");
			contentPanel.add(promptField);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						
						filename = filenameField.getText();
						boolean flag = gManage.mainManager.saveGame("test.txt");
						
						while(flag == false) 
						{
							MessageFrame invalidMessage = new MessageFrame("That filename is invalid. Please input another filename", "Invalid filename");
							flag = gManage.mainManager.saveGame("test.txt");
						}
						
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
