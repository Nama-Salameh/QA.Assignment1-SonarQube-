package edu.najah.cap;

import edu.najah.cap.ex.CanNotWriteFileException;
import edu.najah.cap.ex.EditorSaveAsException;
import edu.najah.cap.ex.EditorSaveException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class Editor extends JFrame implements ActionListener, DocumentListener {
	private static final Logger logger = LogManager.getLogger(Editor.class);

	public static  void main(String[] args) {
		new Editor();
	}

	public JEditorPane TP;//Text Panel
	public JMenuBar menu;//Menu
	public static final JMenuItem copy=new JMenuItem("Copy");
	public static final JMenuItem paste=new JMenuItem("Paste");
	public static final JMenuItem cut= new JMenuItem("Cut");
	public static final JMenuItem move=new JMenuItem("move");

	private boolean changed = false;

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	protected File file;
	
	private String[] actions = {"Open","Save","New","Edit","Quit", "Save as..."};
	
	protected JMenu jmfile;

	public Editor() {
		//Editor the name of our application
		super("Editor");
		TP = new JEditorPane();
		// center means middle of container.
		add(new JScrollPane(TP), "Center");
		TP.getDocument().addDocumentListener(this);

		menu = new JMenuBar();
		setJMenuBar(menu);
		buildMenu();
		//The size of window
		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void buildMenu() {
		buildFileMenu();
		buildEditMenu();
	}

	private void buildFileMenu() {
		jmfile = new JMenu("File");
		jmfile.setMnemonic('F');
		menu.add(jmfile);
		JMenuItem n = new JMenuItem(actions[2]);
		n.setMnemonic('N');
		n.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		n.addActionListener(this);
		jmfile.add(n);
		JMenuItem open = new JMenuItem(actions[0]);
		jmfile.add(open);
		open.addActionListener(this);
		open.setMnemonic('O');
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		JMenuItem save = new JMenuItem(actions[1]);
		jmfile.add(save);
		save.setMnemonic('S');
		save.addActionListener(this);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		JMenuItem saveas = new JMenuItem(actions[5]);
		saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		jmfile.add(saveas);
		saveas.addActionListener(this);
		JMenuItem quit = new JMenuItem(actions[4]);
		jmfile.add(quit);
		quit.addActionListener(this);
		quit.setMnemonic('Q');
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
	}

	private void buildEditMenu() {
		JMenu edit = new JMenu(actions[3]);
		menu.add(edit);
		edit.setMnemonic('E');
		// cut
		cut.addActionListener(this);
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		cut.setMnemonic('T');
		edit.add(cut);
		// copy

		copy.addActionListener(this);
		copy.setMnemonic('C');
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		edit.add(copy);
		// paste
		paste.setMnemonic('P');
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		edit.add(paste);
		paste.addActionListener(this);
		//move
		// find
		JMenuItem find = new JMenuItem("Find");
		find.setMnemonic('F');
		find.addActionListener(this);
		edit.add(find);
		find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
		// select all
		JMenuItem sall = new JMenuItem("Select All");
		sall.setMnemonic('A');
		sall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		sall.addActionListener(this);
		edit.add(sall);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals(actions[4])) {
			System.exit(0);
		} else if (action.equals(actions[0])) {
			loadFile();
		} else if (action.equals(actions[1])) {
			//Save file
			int ans = 0;
			if (changed) {
				// 0 means yes and no option, 2 Used for warning messages.
				ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file", 0, 2);
			}
			//1 value from class method if NO is chosen.
			if (ans != 1) {
				if (file == null) {
					saveAs(actions[1]);
				} else {
					String text = TP.getText();
					logger.info(text);
					try (PrintWriter writer = new PrintWriter(file);){
						if (!file.canWrite())
							throw new EditorSaveException("Cannot write file!");
						writer.write(text);
						changed = false;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		} else if (action.equals(actions[2])) {
			//New file
				//Save file 
				if (changed) {
					// 0 means yes and no option, 2 Used for warning messages.
					int ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file",
							0, 2);
					//1 value from class method if NO is chosen.
					if (ans == 1)
						return;
				} else {
					return;
				}
				if (file == null) {
					saveAs(actions[1]);
					return;
				}
				String text = TP.getText();
				logger.info(text);
				try (PrintWriter writer = new PrintWriter(file);){
					if (!file.canWrite())
						throw new CanNotWriteFileException("Cannot write file!");
					writer.write(text);
					changed = false;
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			file = null;
			TP.setText("");
			changed = false;
			setTitle("Editor");
		} else if (action.equals(actions[5])) {
			saveAs(actions[5]);
		} else if (action.equals("Select All")) {
			TP.selectAll();
		} else if (action.equals("Copy")) {
			TP.copy();
		} else if (action.equals("Cut")) {
			TP.cut();
		} else if (action.equals("Paste")) {
			TP.paste();
		} else if (action.equals("Find")) {
			FindDialog find = new FindDialog(this, true);
			find.showDialog();
		}
	}

	private static final String USER_HOME="user.home";
	private void loadFile() {
		JFileChooser dialog = new JFileChooser(System.getProperty(USER_HOME));
		dialog.setMultiSelectionEnabled(false);
		try {
			int result = dialog.showOpenDialog(this);
			
			if (result == 1)//1 value if cancel is chosen.
				return;
			if (result == 0) {// value if approve (yes, ok) is chosen.
					//Save file
					if (changed) {
						int ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file",
								0, 2);//0 means yes and no question and 2 mean warning dialog
						if (ans == 1)// no option 
							return;
					} else {
						Logger.getLogger("No change");
						return;
					}
					if (file == null) {
						saveAs(actions[1]);
						return;
					}
					String text = TP.getText();
					Logger.getLogger(text);
					fileWriter(file, text);
				}
				file = dialog.getSelectedFile();
				//Read file
				StringBuilder rs = new StringBuilder();
				readFileToBuilder(rs, file);

				TP.setText(rs.toString());
				changed = false;
				setTitle("Editor - " + file.getName());
			} catch (Exception e) {
			e.printStackTrace();
			//0 means show Error Dialog
			JOptionPane.showMessageDialog(null, e, "Error", 0);
		}
	}

	private void fileWriter(File file, String text) {
		try { //this one
			PrintWriter writer = new PrintWriter(file);
			if (!file.canWrite())
				throw new Exception("Cannot write file!"); //this one
			writer.write(text);
			changed = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readFileToBuilder(StringBuilder rs, File file) {
		try (
				FileReader fr = new FileReader(file);
				BufferedReader reader = new BufferedReader(fr);) {
			String line;
			while ((line = reader.readLine()) != null) {
				rs.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Cannot read file !", "Error !", 0);//0 means show Error Dialog
		}
	}

	private void saveAs(String dialogTitle) {
		dialogTitle = dialogTitle.toUpperCase();
		JFileChooser dialog = new JFileChooser(System.getProperty(USER_HOME));
		dialog.setDialogTitle(dialogTitle);
		int result = dialog.showSaveDialog(this);
		if (result != 0)//0 value if approve (yes, ok) is chosen.
			return;
		file = dialog.getSelectedFile();
		PrintWriter writer = getWriter(file);
		if (writer != null) {
				writer.write(TP.getText());
			changed = false;
			setTitle("Editor - " + file.getName());
		}
	}

	private static PrintWriter getWriter(File file) {
		try {
			return new PrintWriter(file);
		} catch (Exception e){
			return null;
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		changed = true;
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		changed = true;
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		changed = true;
	}

}