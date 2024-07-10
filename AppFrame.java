import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * A class that is responsible for the Frame.
 * @author Jakov Lovaković
 *
 */
public class AppFrame {
	private JFrame frame;
	private JTextArea textArea;
	private JMenuBar menuBar;
	private JMenu file, edit, help;
	private JMenuItem save, saveAs, open, copy, paste, selectAll, about;
	private JFileChooser fileChooser;
	private boolean isChosen;
	private String chosenFile;
	
	public AppFrame() {
		super();
		this.frame = new JFrame("Biljesko");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(frame.getContentPane().getLayout());
		isChosen = false;
		
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		
		textArea = new JTextArea(20, 60);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		
		menuBar = new JMenuBar();
		
		file = new JMenu("File");
		
		save = new JMenuItem("Save");
		save.addActionListener(e -> {
			try {
				if(isChosen) {
					AppManager.saveTxtFile(chosenFile, textArea.getText());
				}
				else {
					JOptionPane.showMessageDialog(null, "Za inicijalno spremanje odabrati Save As...");
				}
			}
			catch(Exception ex) {}
		});
		
		saveAs = new JMenuItem("Save As...");
		saveAs.addActionListener(e -> {
			fileChooser.showSaveDialog(null);
			try {
				File selectedPath = fileChooser.getSelectedFile();
				if(selectedPath.toString().split("\\.").length > 1) {
					JOptionPane.showMessageDialog(null, "Kriva selekcija");
				}
				else {
					isChosen = true;
					chosenFile = selectedPath.toString() + ".txt";
					AppManager.saveTxtFile(chosenFile, textArea.getText());
				}
			}
			catch(Exception ex) {}
		});
		
		open = new JMenuItem("Open");
		open.addActionListener(e -> {
			fileChooser.showSaveDialog(null);
			try {
				File selectedPath = fileChooser.getSelectedFile();
				if(!selectedPath.toString().endsWith(".txt")) {
					JOptionPane.showMessageDialog(null, "Kriva selekcija");
				}
				else {
					isChosen = true;
					chosenFile = selectedPath.toString();
					AppManager.openFile(chosenFile, textArea);
				}
			}
			catch(Exception ex) {}
		});
		
		file.add(save);
		file.add(saveAs);
		file.add(open);
		
		edit = new JMenu("Edit");
		
		copy = new JMenuItem("Copy");
		copy.addActionListener(e -> AppManager.copyToClipboard(textArea));
		
		paste = new JMenuItem("Paste");
		paste.addActionListener(e -> {
			try {
				AppManager.pasteOnTextArea(textArea);
			} catch (UnsupportedFlavorException | IOException e1) {}
		});
		
		selectAll = new JMenuItem("Select All");
		selectAll.addActionListener(e -> textArea.selectAll());
		
		edit.add(copy);
		edit.add(paste);
		edit.add(selectAll);
		
		help = new JMenu("Help");
		about = new JMenuItem("About Biljesko");
		about.addActionListener(e -> JOptionPane.showMessageDialog(null, "Osobni java projekt."));
		help.add(about);
		
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(help);
		
		frame.setJMenuBar(menuBar);
		frame.add(scrollPane);
		frame.pack();
		frame.setVisible(true);
	}
	
}
