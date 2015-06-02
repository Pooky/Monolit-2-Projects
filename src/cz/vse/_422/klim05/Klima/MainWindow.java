package cz.vse._422.klim05.Klima;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

public class MainWindow {

	private JFrame frame;
	private JTextField textField;
	private JButton btnNewButton;
	private JComboBox comboBox;
	
	public MonolitManager manager;
	private JLabel lblDone;

	

	/**
	 * Create the application.
	 */
	public MainWindow() {
		
		this.manager = new MonolitManager();
		
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void createWindow(){
		
		initialize();

		this.frame.setVisible(true);

	}
	
	public void generateChapter(){
		
		this.manager.setDestinationFolder(textField.getText());
		Chapter chapter = (Chapter)comboBox.getSelectedItem();
		
		this.manager.CreateChapter(chapter.getName());
		lblDone.setVisible(true);
		
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Generate chapter");
		
		comboBox = new JComboBox();
		comboBox.setBounds(32, 69, 219, 24);
		frame.getContentPane().add(comboBox);
		
		for(Chapter chapter : this.manager.getAllChapters()){
			comboBox.addItem(chapter);
		}
		
		
		btnNewButton = new JButton("Generate");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateChapter();
			}
		});
		btnNewButton.setBounds(265, 223, 117, 25);
		btnNewButton.setEnabled(false);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Select chapter");
		lblNewLabel.setBounds(32, 42, 145, 15);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();

		textField.setBounds(32, 154, 219, 24);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblDestination = new JLabel("Destination");
		lblDestination.setBounds(32, 127, 117, 15);
		frame.getContentPane().add(lblDestination);
		
		JButton btnSelect = new JButton("Search");
		
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFileChooser();
			}
		});
		
		btnSelect.setBounds(265, 153, 117, 25);
		frame.getContentPane().add(btnSelect);
		
		lblDone = new JLabel("Done.");
		lblDone.setBounds(32, 228, 70, 15);
		lblDone.setVisible(false);
		frame.getContentPane().add(lblDone);

	}
	
	public void openFileChooser(){
		
		
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
		chooser.setDialogTitle("Select directory to generate output.");
				
		int returnVal = chooser.showOpenDialog(this.frame);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			textField.setText(chooser.getSelectedFile().getAbsolutePath());
			btnNewButton.setEnabled(true);

		}
		
	}
}
