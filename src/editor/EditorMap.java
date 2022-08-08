package editor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class EditorMap{
	
	private static final int MAXIMUM_TILES = 13;
	private static int BLOCKSIZE;
	
	private EditorConsole console;
	
	private List<EditorBlock> blocks;
	private EditorMenue menue;
	private int locx, locy;
	
	private Dimension borderDim;
	
	public EditorMap(int locx, int locy, int width, int height, EditorMenue menue, EditorConsole console) {
		super();
		this.locx = locx;
		this.locy = locy;
		BLOCKSIZE = (height-40)/MAXIMUM_TILES;
		this.menue = menue;
		this.console = console;
		this.blocks = new ArrayList<>();
		for(int i = 0; i < MAXIMUM_TILES*MAXIMUM_TILES; i++) {
			this.blocks.add(new EditorBlock(i%MAXIMUM_TILES, i/MAXIMUM_TILES, EditorBlockType.NULL));
		}
		
		this.borderDim = new Dimension(menue.getBorderDimension());
		fillBorderBlocks(EditorBlockType.BORDER);
	}
	
	private void fillBorderBlocks(EditorBlockType type) {
		if(borderDim.width<13) {
			for(int x = 0; x < borderDim.getWidth()+2; x++) {
				setBlock((int)Math.ceil(-borderDim.getWidth()/2+x-1), -(int)(borderDim.getWidth()/2+1), type);
				setBlock((int)Math.ceil(-borderDim.getWidth()/2+x-1), -(int)(borderDim.getWidth()/-2-1),type);
			}
		}
		if(borderDim.height<13) {
			for(int y = 1; y < borderDim.getWidth()+1; y++) {
				setBlock(-(int)borderDim.getWidth()/2-1, (int)Math.ceil(-borderDim.getHeight()/2+y-1), type);
				setBlock(-(int)borderDim.getWidth()/-2+1, (int)Math.ceil(-borderDim.getHeight()/2+y-1),type);
			}
		}
		
	}
	
	private void setBlock(int x, int y, EditorBlockType type) {
		int xc = x+(int)Math.floor(MAXIMUM_TILES/2);
		int yc = y+(int)Math.floor(MAXIMUM_TILES/2);
		this.blocks.get(xc + yc*MAXIMUM_TILES).setType(type);
	}
	public void updateBorder() {
		Dimension changedDim = menue.getBorderDimension();
		if(!(this.borderDim.width==changedDim.width && this.borderDim.height == changedDim.height)) {
			fillBorderBlocks(EditorBlockType.NULL);
			this.borderDim = new Dimension(changedDim);
			fillBorderBlocks(EditorBlockType.BORDER);
		}
	}
	
	public Component[] getComponents() {
		return this.blocks.toArray(new Component[this.blocks.size()]);
	}
	
	public void display() {
		for(EditorBlock comp: this.blocks) {
			comp.setEnabled(false);
			comp.setVisible(false);
			comp.setEnabled(true);
			comp.setVisible(true);
		}
	}
	
	
	private class EditorBlock extends JButton {
		private static final long serialVersionUID = -3451473027574597441L;
		private EditorBlockType type;
		private int xCoord, yCoord;
		private EditorBlock(int frampos_xCoord, int framepos_yCoord, EditorBlockType blockType) {
			super();
			super.setBounds(locx +frampos_xCoord*BLOCKSIZE, locy + framepos_yCoord*BLOCKSIZE, BLOCKSIZE, BLOCKSIZE);
			this.xCoord = frampos_xCoord-6;
			this.yCoord = framepos_yCoord-6;
			this.type = blockType;
			super.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(type.isEditable()) {
						if(type!=menue.getSelectedBlockType()) {
							type = menue.getSelectedBlockType();
							console.log("Field ["+xCoord+"|"+yCoord+"] set to >" + type.name()+"<");
						}
					}else {
						console.log("The BlockType >" + type.name()+ "< is not editable");
					}
					setIcon(createIcon());
				}
			});
			setIcon(createIcon());
		}
		private ImageIcon createIcon() {
			BufferedImage bi = new BufferedImage(BLOCKSIZE, BLOCKSIZE, BufferedImage.TYPE_INT_RGB);
			Graphics g = bi.getGraphics();
			g.setColor(type.getColor());
			g.fillRect(0, 0, BLOCKSIZE, BLOCKSIZE);
			g.dispose();
			return new ImageIcon(bi);
		}
		private void setType(EditorBlockType type) {
			this.type = type;
			setIcon(createIcon());
		}
	}
	
	public void saveToFile(File exportFile) throws IOException{
		if(exportFile.exists())exportFile.delete();
		exportFile.createNewFile();
		if(exportFile.isDirectory())console.log("ERROR: the export location is a Directory");
		BufferedWriter bw = new BufferedWriter(new FileWriter(exportFile));
		bw.append("Dimension " + borderDim.width + " " + borderDim.height + "\n");
		for(int i = 0; i < this.blocks.size(); i++) {
			EditorBlock block = this.blocks.get(i);
			if(block.type.writeOnSave()) {
				bw.append(block.type.name() + " " + block.xCoord + " " +(-block.yCoord) + "\n");
				try {
					console.log("savingBlock:" + "TYPE: >" + block.type.name() + "<" + 
				"; COORD: [" + block.xCoord + "|" + -block.yCoord +"]");
					Thread.sleep(20);
					console.log(block.toString());
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		bw.close();
	}
	
}
