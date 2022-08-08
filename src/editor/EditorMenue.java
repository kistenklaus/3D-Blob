package editor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class EditorMenue{

	private static Font font = new Font("Arial", Font.PLAIN, 12);
	
	private EditorConsole console;
	private boolean saveRequested;
	private File saveFile;
	
	private List<Component> components;
	
	private EditorBlockType selectedBlockType;
	private Dimension border;
	
	public EditorMenue(int locx, int locy, EditorConsole console) {
		super();
		this.selectedBlockType = EditorBlockType.NULL;
		this.components = new ArrayList<Component>();
		this.console = console;
		this.saveRequested = false;
		JLabel boundMenueTitle = new JLabel("select BoundSize:");
		boundMenueTitle.setBounds(10, 10, 150, 18);
		boundMenueTitle.setFont(new Font("Arial", Font.PLAIN, 12));
		this.components.add(boundMenueTitle);
		this.components.add(new BoundMenue(10, 10, 300, 10, new String[] {
				"11x11","13x13"
		}));
		JTabbedPane tabb = new JTabbedPane();
		tabb.setBounds(10, 30, 300, 250);
		JPanel selectionButtons = new JPanel();
		selectionButtons.setLayout(new FlowLayout());
		selectionButtons.add(new BlockSelectionButton(EditorBlockType.NULL));
		selectionButtons.add(new BlockSelectionButton(EditorBlockType.SOLID));
		selectionButtons.add(new BlockSelectionButton(EditorBlockType.GOAL));
		tabb.add("block Selection",selectionButtons);
		JPanel saveTabb = new JPanel();
		JButton saveButton = new JButton("save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initSave();
			}
		});
		saveTabb.add(saveButton);
		
		tabb.add("save", saveTabb);
		
		this.components.add(tabb);
		
	}
	public Component[] getComponents() {
		return this.components.toArray(new Component[this.components.size()]);
	}
	
	public void display() {
		for(Component comp: this.components) {
			comp.setEnabled(false);
			comp.setVisible(false);
			comp.setEnabled(true);
			comp.setVisible(true);
		}
	}
	
	private void initSave() {
		JFileChooser jfc = new JFileChooser("level/");
		int rv = jfc.showOpenDialog(null);
		if(rv == JFileChooser.APPROVE_OPTION) {
			saveFile = jfc.getSelectedFile();
			saveRequested = true;
		}else {
			console.log("ERROR: saving the File");
		}
	}
	
	private class BoundMenue extends JComboBox<String>{
		private static final long serialVersionUID = -1269667848310048715L;
		private BoundMenue(int x, int y, int width, int height, String[] options) {
			super(options);
			super.setFont(font);
			super.setBounds(160, 10, 150, 20);
			super.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange() == ItemEvent.SELECTED) {
						parseDimString(e.getItem().toString());
					}
				}
			});
			parseDimString(options[0]);
			repaint();
		}
		private void parseDimString(String dimString) {
			String[] split = dimString.split("x");
			border = new Dimension (Integer.parseInt(split[0]), Integer.parseInt(split[1]));
		}
	}
	
	private class BlockSelectionButton extends JButton{
		private static final long serialVersionUID = -4585142646919061795L;
		private static final int BUTTON_WIDTH = 145, BUTTON_HEIGHT = 50;
		private BlockSelectionButton(EditorBlockType blockType) {
			super(blockType.name());
//			super.setBounds(tx + (BUTTON_WIDTH+MARGIN)*buttonX, ty + (BUTTON_HEIGHT+MARGIN)*buttonY, BUTTON_WIDTH, BUTTON_HEIGHT);
			super.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
			super.setFont(font);
			super.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					selectedBlockType = blockType;
				}
			} );
		}
	}
	
	public Dimension getBorderDimension() {
		return this.border;
	}
	public EditorBlockType getSelectedBlockType() {
		return this.selectedBlockType;
	}
	public void fileRequestSatisfied() {
		this.saveRequested = false;
		this.saveFile = null;
	}
	public boolean isFileSaveRequested() {
		return this.saveRequested;
	}
	public File getSaveFile() {
		return this.saveFile;
	}
}
