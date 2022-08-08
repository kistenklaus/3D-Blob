package editor;

import java.awt.Color;

public enum EditorBlockType {
	NULL(Color.white,true,false),
	BORDER(Color.BLACK,false,false),
	SOLID(Color.GRAY,true,true),
	GOAL(Color.GREEN,true,true);
	
	private Color color;
	private boolean editable;
	private boolean writeOnSave;
	EditorBlockType(Color color,boolean editable, boolean writeOnSave){
		this.color = color;
		this.editable = editable;
		this.writeOnSave = writeOnSave;
	}
	public Color getColor() {
		return this.color;
	}
	public boolean isEditable() {
		return this.editable;
	}
	public boolean writeOnSave() {
		return this.writeOnSave;
	}
}
