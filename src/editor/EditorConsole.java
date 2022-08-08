package editor;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

public class EditorConsole extends JTextArea{
	
	private static final long serialVersionUID = -8331915922958055596L;
	private static final int MAX_LINES = 15;
	
	private List<String> lines;
	
	public EditorConsole(int tx, int ty, int width, int height) {
		super();
		super.setBounds(tx, ty, width, height);
		super.setLayout(null);
		this.lines = new ArrayList<>();
		super.setBackground(Color.black);
		super.setForeground(Color.white);
		super.setFont(new Font("consolas", Font.PLAIN, 12));
	}
	
	public void log(String log) {
		lines.add(log);
		if(lines.size()>MAX_LINES) {
			lines.remove(0);
		}
		upadteText();
	}
	
	private void upadteText() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < lines.size(); i++) {
			sb.append(lines.get(i));
			sb.append("\n");
		}
		super.setText(sb.toString());
	}
	
	public void display() {
		super.setEnabled(false);
		super.setVisible(false);
		super.setEnabled(true);
		super.setVisible(true);
	}
}
