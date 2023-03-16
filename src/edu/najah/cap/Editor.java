package edu.najah.cap;

import edu.najah.cap.ex.EditorException;
import edu.najah.cap.ex.EditorSaveAsException;
import edu.najah.cap.ex.EditorSaveException;

import java.awt.BorderLayout;
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

	public static  void main(String[] args) {
		new Editor();
	}

	public JEditorPane getTextPanel() {
		return textPanel;
	}

	private JEditorPane textPanel;
	public JMenuBar menu;
	public JMenuItem copy, paste, cut, move;
	public boolean changed = false;
	protected File file;
	
	private String[] actions = {"Open","Save","New","Edit","Quit", "Save as..."};
	
	protected JMenu jmfile;

	private static final String MESSAGE = "The file has changed. You want to save it?";
	public Editor() {
		//Editor the name of our application
		super("Editor");
		textPanel = new JEditorPane();
		// center means middle of container.
		add(new JScrollPane(textPanel), "Center");
		textPanel.getDocument().addDocumentListener(this);

		menu = new JMenuBar();
		setJMenuBar(menu);
		BuildMenu();
		//The size of window
		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void BuildMenu() {
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
		saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
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
		cut = new JMenuItem("Cut");
		cut.addActionListener(this);
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		cut.setMnemonic('T');
		edit.add(cut);
		// copy
		copy = new JMenuItem("Copy");
		copy.addActionListener(this);
		copy.setMnemonic('C');
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		edit.add(copy);
		// paste
		paste = new JMenuItem("Paste");
		paste.setMnemonic('P');
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		edit.add(paste);
		paste.addActionListener(this);
		//move 
		/*
		move = new JMenuItem("Move");
		move.setMnemonic('M');
		move.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		edit.add(move);
		move.addActionListener(this);
		*/
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

	private void exitChoice(String action){
		if (action.equals(actions[4])) {
			System.exit(0);
		}
	}
	private void openChoice(String action){
		if (action.equals(actions[0])) {
			loadFile();
		}
	}
	private int confirmSaveFile(){
			// 0 means yes and no option, 2 Used for warning messages.
			return JOptionPane.showConfirmDialog(null, MESSAGE, "Save file", 0, 2);
	}
	private void saveFileChoice(String action){
		if (action.equals(actions[1])) {
			//Save file
			int ans =0;
			if (changed) {
				ans = confirmSaveFile();
			}
			//1 value from class method if NO is chosen.
			if (ans != 1) {
				if (emptyFile()) {
					saveAs(actions[1]);
				} else {
					savingExistingFile();
				}
			}
		}
	}

	private boolean emptyFile(){
		return file == null;
	}
	private void savingExistingFile() {
		String text = textPanel.getText();
		System.out.println(text);
		try (PrintWriter writer = new PrintWriter(file);){
			if (!file.canWrite())
				throw new EditorSaveException("Cannot write file!");
			writer.write(text);
			changed = false;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void newFileChoice(String action){
		if (action.equals(actions[2])) {
			//New file
			if (changed) {
				//Save file
				if (changed) {
					int ans = confirmSaveFile();
					//1 value from class method if NO is chosen.
					if (ans == 1)
						return;
				} else {                                                                   //unreachable???
					return;
				}
				if (emptyFile()) {
					saveAs(actions[1]);
					return;
				}
				savingExistingFile();
			}
			file = null;
			textPanel.setText("");
			changed = false;
			setTitle("Editor");
		}
	}
	private void findChoice(String action) {
		if (action.equals("Find")) {
			FindDialog find = new FindDialog(this, true);
			find.showDialog();
		}
	}

	private void pasteChoice(String action) {
		if (action.equals("Paste")) {
			textPanel.paste();
		}
	}

	private void cutChoice(String action) {
		if (action.equals("Cut")) {
			textPanel.cut();
		}
	}

	private void copyChoice(String action) {
		if (action.equals("Copy")) {
			textPanel.copy();
		}
	}

	private void selectAllChoice(String action) {
		if (action.equals("Select All")) {
			textPanel.selectAll();
		}
	}

	private void saveAsChoice(String action) {
		if (action.equals(actions[5])) {
			saveAs(actions[5]);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		exitChoice(action);
		openChoice(action);
		saveFileChoice(action);
		newFileChoice(action);
		saveAsChoice(action);
		selectAllChoice(action);
		copyChoice(action);
		cutChoice(action);
		pasteChoice(action);
		findChoice(action);
	}


	private void loadFile() {
		JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
		dialog.setMultiSelectionEnabled(false);
		try {
			int result = dialog.showOpenDialog(this);
			cancelChoice(result);
			approveChoice(dialog,result);
		} catch (Exception e) {
			e.printStackTrace();
			//0 means show Error Dialog
			JOptionPane.showMessageDialog(null, e, "Error", 0);
		}
	}

	private void approveChoice(JFileChooser dialog,int result) {
		if (result == 0) {// value if approve (yes, ok) is chosen.
			if (changed){
				//Save file
				if (changed) {
					int ans = confirmSaveFile();
					if (ans == 1)// no option
						return;
				} else {
					System.out.println("No change");
					return;
				}
				if (emptyFile()) {
					saveAs(actions[1]);
					return;
				}
				savingExistingFile();
			}
			file = dialog.getSelectedFile();
			readFile();

			changed = false;
			setTitle("Editor - " + file.getName());
		}
	}

	private void readFile() {
		StringBuilder rs = new StringBuilder();
		try (FileReader fr = new FileReader(file);
			 BufferedReader reader = new BufferedReader(fr);) {
			String line;
			while ((line = reader.readLine()) != null) {
				rs.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Cannot read file !", "Error !", 0);//0 means show Error Dialog
		}
		textPanel.setText(rs.toString());
	}

	private void cancelChoice(int result) {
		if (result == 1)
			return;
	}


	private void saveAs(String dialogTitle) {
		dialogTitle = dialogTitle.toUpperCase();
		JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
		dialog.setDialogTitle(dialogTitle);
		int result = dialog.showSaveDialog(this);
		if (result != 0)//0 value if approve (yes, ok) is chosen.
			return;
		file = dialog.getSelectedFile();
		PrintWriter writer = getWriter(file);
		writer.write(textPanel.getText());
		changed = false;
		setTitle("Editor - " + file.getName());
	}

	private static PrintWriter getWriter(File file) {
		try {
			return new PrintWriter(file);
		} catch (Exception e){
			return null;
		}
	}

	private void saveAsText(String dialogTitle) throws EditorSaveAsException {
		JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
		dialog.setDialogTitle(dialogTitle);
		int result = dialog.showSaveDialog(this);
		if (result != 0)//0 value if approve (yes, ok) is chosen.
			return;
		file = dialog.getSelectedFile();
		try (PrintWriter writer = new PrintWriter(file);){
			writer.write(textPanel.getText());
			changed = false;
			setTitle("Save as Text Editor - " + file.getName());
		} catch (FileNotFoundException e) {
			throw new EditorSaveAsException(e.getMessage());
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