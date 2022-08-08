package editor;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Frame extends JFrame{
	private static final long serialVersionUID = 5165407886613463992L;

	public Frame(int width, int height, String title) {
		super();
		super.setTitle(title);
		Dimension dim = new Dimension(width,height);
		super.setMinimumSize(dim);
		super.setMaximumSize(dim);
		super.setPreferredSize(dim);
		super.setSize(dim);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setLocationRelativeTo(null);
		super.setResizable(false);
		super.setLayout(null);
		super.setBackground(Color.gray);
		super.setVisible(true);
	}
	
}
