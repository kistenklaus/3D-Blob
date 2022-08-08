package logic.entity.level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import engine.model.ModelLoader;
import engine.model.RawModel;
import engine.objLoader.OBJFileLoader;
import logic.GameCamera;

public final class LevelLoader {
	
	public static Level loadLevel(String levelFile_loc, BlockShader shader, GameCamera camera,
			ModelLoader modelLoader) {
		File levelFile = new File(levelFile_loc);
		if(!levelFile.exists()) {
			System.err.println("The Level Filelocation : "+ levelFile_loc + " does not exist!!!");
		}
		return loadLevel(levelFile, shader, camera, modelLoader);
	}
	
	public static Level loadLevel(File levelFile, BlockShader shader, GameCamera camera,
			ModelLoader modelLoader) {
		
		RawModel solidModel = OBJFileLoader.loadOBJ("./models/blockModels/solid.obj", modelLoader);
		
		List<Block> blocks = new ArrayList<>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(levelFile));
			String line = null;
			while((line = br.readLine()) != null) {
				String[] comp = line.split(" ");
				if(comp[0].equals("Dimension")) {
					blocks.add(new BorderBlock(Integer.parseInt(comp[1]), Integer.parseInt(comp[2]), modelLoader));
				}else if(comp[0].equals("SOLID")) {
					blocks.add(new SolidBlock(Integer.parseInt(comp[1]), Integer.parseInt(comp[2]), modelLoader, solidModel));
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Level(blocks.toArray(new Block[blocks.size()]), shader, camera);
	}
	
}
