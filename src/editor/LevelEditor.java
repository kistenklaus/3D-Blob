package editor;

import java.awt.Component;
import java.io.IOException;

public class LevelEditor{
	public static void main(String[] args) {
		Frame frame = new Frame(960,640, "LevelEditor");

		EditorConsole console = new EditorConsole(10,300,300,300);
		frame.add(console);
		console.display();
		
		EditorMenue menue = new EditorMenue(0, 0, console);
		for(Component comp : menue.getComponents()) {
			frame.add(comp);
		}
		menue.display();
		
		
		
		EditorMap map = new EditorMap(320, 10, 640,640, menue, console);
		for(Component comp : map.getComponents()) {
			frame.add(comp);
		}
		map.display();
		
		while(true) {
			map.updateBorder();
			if(menue.isFileSaveRequested()) {
				try {
					map.saveToFile(menue.getSaveFile());
				} catch (IOException e) {
					e.printStackTrace();
				}
				menue.fileRequestSatisfied();
			}
		}
	}
}
