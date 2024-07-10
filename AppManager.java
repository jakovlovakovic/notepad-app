import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
/**
 * A class that helps managing my note taking app.
 * @author Jakov Lovaković
 *
 */
public class AppManager {
	 /**
     * Creates a file with the given text, on the given path.
     *
     * @param  file
     *         File location
     * @param  fileName
     *         Name of the given file
     * @param  text
     *         Text to be written in the file
     *
     * @throws  IOException
     *          If an I/O error occurred
     *
     * @throws  SecurityException
     *          If a security manager exists and its {@link
     *          java.lang.SecurityManager#checkWrite(java.lang.String)}
     *          method denies write access to the file
     */
	public static void saveTxtFile(String file, String text) throws IOException {
		File savedFile = new File(file);
		List<String> fileContainer = new LinkedList<>();

		Files.walkFileTree(Paths.get(savedFile.getParent()), new SimpleFileVisitor<Path>() {
			public FileVisitResult visitFile(Path fileVisited, BasicFileAttributes attrs) {
		    	fileContainer.add(fileVisited.toString());
				return FileVisitResult.CONTINUE;
			}
		});
		
		if(fileContainer.contains(file)) {
			int select = JOptionPane.showConfirmDialog(null, "Jesi siguran da zelis da nadjacati vec postojeci file?");
			if(select == 0) {
				savedFile.createNewFile();
				
				BufferedWriter writer = new BufferedWriter(new FileWriter(savedFile));
				writer.write(text);
				writer.close();
			}
		}
		else {
			savedFile.createNewFile();
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(savedFile));
			writer.write(text);
			writer.close();
		}
	}
	/**
     * Copies a text from the given JTextArea to the system's clipboard.
     *
     * @param  textArea
     *         JTextArea which containts a text to be copied
     *        
     */
	public static void copyToClipboard(JTextArea textArea) {
		String stringToBeCopied = textArea.getText();
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection stringSelector = new StringSelection(stringToBeCopied);
		clip.setContents(stringSelector, stringSelector);
		JOptionPane.showMessageDialog(null, "Tekst kopiran!");
	}
	/**
     * Paste the text from the system's clipboard on the given JTextArea .
     *
     * @param  textArea
     *         JTextArea on which the text will be pasted
     *        
     * @throws NullPointerException if {@code flavor} is {@code null}
     * @throws IllegalStateException if this clipboard is currently unavailable
     * @throws UnsupportedFlavorException if the requested {@code DataFlavor} is
     *         not available
     * @throws IOException if the data in the requested {@code DataFlavor} can
     *         not be retrieved
     */
	public static void pasteOnTextArea(JTextArea textArea) throws UnsupportedFlavorException, IOException  {
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
		String textFromClipboard = (String) clip.getData(DataFlavor.stringFlavor);
		textArea.setText(textFromClipboard);
	}
	/**
     * Opens the selected file.
     *
     * @param  file
     *         Path of the desired file.
     * @param  textArea
     *         JTextArea on which the text from the selected file will be pasted.
     *        
     * @throws NullPointerException if {@code flavor} is {@code null}
     * @throws IllegalStateException if this clipboard is currently unavailable
     * @throws  IOException
     *          if an I/O error occurs reading from the file or a malformed or
     *          unmappable byte sequence is read
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkRead(String) checkRead}
     *          method is invoked to check read access to the file.
     */
	public static void openFile(String file, JTextArea textArea) throws IOException {
		List<String> fileContent = Files.readAllLines(Path.of(file));
		textArea.setText(String.join("\n", fileContent));
	}
	
}
