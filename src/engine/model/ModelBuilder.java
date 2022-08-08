package engine.model;

public final class ModelBuilder {
	
	public static RawModel createHardQuad(float xSIDE, float ySIDE, float zSIDE, ModelLoader modelLoader) {
		RawModel rawModel = modelLoader.loadPINToVoa(new float[] {
				-xSIDE,ySIDE,zSIDE,		//0
				xSIDE,ySIDE,zSIDE,		//1
				xSIDE,-ySIDE,zSIDE,		//2
				-xSIDE,-ySIDE,zSIDE,	//3
				
				xSIDE,ySIDE,zSIDE,		//4
				xSIDE,ySIDE,-zSIDE,		//5
				xSIDE,-ySIDE,-zSIDE,	//6
				xSIDE,-ySIDE,zSIDE,		//7
				
				xSIDE,ySIDE,-zSIDE,		//8
				-xSIDE,ySIDE,-zSIDE,	//9
				-xSIDE,-ySIDE,-zSIDE,	//10
				xSIDE,-ySIDE,-zSIDE,	//11
				
				-xSIDE,-ySIDE,-zSIDE,	//12
				-xSIDE,ySIDE,-zSIDE,	//13
				-xSIDE,ySIDE,zSIDE,		//14
				-xSIDE,-ySIDE,zSIDE,	//15
				
				-xSIDE,-ySIDE,zSIDE,	//16
				-xSIDE,-ySIDE,-zSIDE,	//17
				xSIDE,-ySIDE,-zSIDE,	//18
				xSIDE,-ySIDE,zSIDE,		//19
				
				-xSIDE,ySIDE,zSIDE,		//20
				-xSIDE,ySIDE,-zSIDE,	//21
				xSIDE,ySIDE,-zSIDE,		//22
				xSIDE,ySIDE,zSIDE,		//23
				
		}, new int[] {
				0,1,2,
				2,3,0,
				
				4,5,6,
				6,7,4,
				
				8,9,10,
				10,11,8,
				
				12,13,14,
				14,15,12,
				
				16,17,18,
				18,19,16,
				
				20,21,22,
				22,23,20
				
				
		}, new float[] {
				0,0,1,
				0,0,1,
				0,0,1,
				0,0,1,
				
				1,0,0,
				1,0,0,
				1,0,0,
				1,0,0,
				
				0,0,-1,
				0,0,-1,
				0,0,-1,
				0,0,-1,
				
				-1,0,0,
				-1,0,0,
				-1,0,0,
				-1,0,0,
				
				0,-1,0,
				0,-1,0,
				0,-1,0,
				0,-1,0,
				
				0,1,0,
				0,1,0,
				0,1,0,
				0,1,0,
		});
		return rawModel;
	}
	
	public static RawModel createSmoothCornerQuad(float xSIDE, float ySIDE, float zSIDE,
			float cSize, ModelLoader modelLoader) {
		float xSIDE_half = xSIDE/2;
		float ySIDE_half = ySIDE/2;
		float zSIDE_half = zSIDE/2;
		RawModel rawModel = modelLoader.loadPINToVoa(new float[] {
				//FRONT
				-xSIDE_half+cSize,ySIDE_half-cSize,zSIDE_half,		//0
				xSIDE_half-cSize,ySIDE_half-cSize,zSIDE_half,		//1
				xSIDE_half-cSize,-ySIDE_half+cSize,zSIDE_half,		//2
				-xSIDE_half+cSize,-ySIDE_half+cSize,zSIDE_half,	//3
				//RIGHT
				xSIDE_half,ySIDE_half-cSize, zSIDE_half-cSize,		//4
				xSIDE_half,ySIDE_half-cSize, -zSIDE_half+cSize,	//5
				xSIDE_half,-ySIDE_half+cSize, -zSIDE_half+cSize,	//6
				xSIDE_half,-ySIDE_half+cSize, zSIDE_half-cSize,	//7
				//BACK
				-xSIDE_half+cSize,ySIDE_half-cSize,-zSIDE_half,	//8
				xSIDE_half-cSize,ySIDE_half-cSize,-zSIDE_half,		//9
				xSIDE_half-cSize,-ySIDE_half+cSize,-zSIDE_half,	//10
				-xSIDE_half+cSize,-ySIDE_half+cSize,-zSIDE_half,	//11
				//LEFT
				-xSIDE_half,ySIDE_half-cSize, zSIDE_half-cSize,	//12
				-xSIDE_half,ySIDE_half-cSize, -zSIDE_half+cSize,	//13
				-xSIDE_half,-ySIDE_half+cSize, -zSIDE_half+cSize,	//14
				-xSIDE_half,-ySIDE_half+cSize, zSIDE_half-cSize,	//15
				//BOTTOM
				-xSIDE_half+cSize, -ySIDE_half, zSIDE_half-cSize,	//16
				-xSIDE_half+cSize, -ySIDE_half, -zSIDE_half+cSize,	//17
				xSIDE_half-cSize, -ySIDE_half, -zSIDE_half+cSize,	//18
				xSIDE_half-cSize, -ySIDE_half, zSIDE_half-cSize,	//19
				//TOP
				-xSIDE_half+cSize, ySIDE_half, zSIDE_half-cSize,	//20
				-xSIDE_half+cSize, ySIDE_half, -zSIDE_half+cSize,	//21
				xSIDE_half-cSize, ySIDE_half, -zSIDE_half+cSize,	//22
				xSIDE_half-cSize, ySIDE_half, zSIDE_half-cSize,	//23
				
		}, new int[] {
				0,1,2,
				2,3,0,
				
				4,5,6,
				6,7,4,
				
				8,9,10,
				10,11,8,
				
				12,13,14,
				14,15,12,
				
				16,17,18,
				18,19,16,
				
				20,21,22,
				23,22,20,
				//FRONT-TOP
				1,0,20,
				20,23,1,
				//FRONT-RIGHT
				2,1,4,
				4,7,2,
				//FRONT-LEFT
				0,3,15,
				15,12,0,
				//FRONT-BOTTOM
				3,2,19,
				19,16,3,
				//RIGHT-TOP
				5,4,23,
				23,22,5,
				//RIGHT-BOTTOM
				7,6,18,
				18,19,7,
				//RIGHT-BACK
				6,5,9,
				9,10,6,
				//LEFT-TOP
				12,13,21,
				21,20,12,
				//LEFT-BOTTOM
				14,15,16,
				16,17,14,
				//LEFT-BACK
				14,13,8,
				8,11,14,
				//BACK-TOP
				9,8,21,
				21,22,9,
				//BACK-BOTTOM
				10,11,17,
				17,18,10,
				//CORNRES:
				1,23,4,
				5,22,9,
				
				0,20,12,
				13,21,8,
				
				3,15,16,
				11,14,17,
				
				2,7,19,
				10,6,18
				
				
		}, new float[] {
				0,0,1,
				0,0,1,
				0,0,1,
				0,0,1,
				
				1,0,0,
				1,0,0,
				1,0,0,
				1,0,0,
				
				0,0,-1,
				0,0,-1,
				0,0,-1,
				0,0,-1,
				
				-1,0,0,
				-1,0,0,
				-1,0,0,
				-1,0,0,
				
				0,-1,0,
				0,-1,0,
				0,-1,0,
				0,-1,0,
				
				0,1,0,
				0,1,0,
				0,1,0,
				0,1,0,
				
		});
		return rawModel;
	}
	
	public static RawModel createFlatCornerQuad(float xSIDE, float ySIDE, float zSIDE, float cSize, ModelLoader modelLoader) {
		RawModel rawModel = modelLoader.loadPINToVoa(new float[] {
				//FRONT
				-xSIDE+cSize,ySIDE-cSize,zSIDE,		//0
				xSIDE-cSize,ySIDE-cSize,zSIDE,		//1
				xSIDE-cSize,-ySIDE+cSize,zSIDE,		//2
				-xSIDE+cSize,-ySIDE+cSize,zSIDE,	//3
				//RIGHT
				xSIDE,ySIDE-cSize, zSIDE-cSize,
				xSIDE,ySIDE-cSize, -zSIDE+cSize,
				xSIDE,-ySIDE+cSize, -zSIDE+cSize,
				xSIDE,-ySIDE+cSize, zSIDE-cSize,
				//BACK
				-xSIDE+cSize,ySIDE-cSize,-zSIDE,
				xSIDE-cSize,ySIDE-cSize,-zSIDE,
				xSIDE-cSize,-ySIDE+cSize,-zSIDE,
				-xSIDE+cSize,-ySIDE+cSize,-zSIDE,
				//LEFT
				-xSIDE,ySIDE-cSize, zSIDE-cSize,
				-xSIDE,ySIDE-cSize, -zSIDE+cSize,
				-xSIDE,-ySIDE+cSize, -zSIDE+cSize,
				-xSIDE,-ySIDE+cSize, zSIDE-cSize,
				//BOTTOM
				-xSIDE+cSize, -ySIDE, zSIDE-cSize,
				-xSIDE+cSize, -ySIDE, -zSIDE+cSize,
				xSIDE-cSize, -ySIDE, -zSIDE+cSize,
				xSIDE-cSize, -ySIDE, zSIDE-cSize,
				//TOP
				-xSIDE+cSize, ySIDE, zSIDE-cSize,
				-xSIDE+cSize, ySIDE, -zSIDE+cSize,
				xSIDE-cSize, ySIDE, -zSIDE+cSize,
				xSIDE-cSize, ySIDE, zSIDE-cSize,
				
		}, new int[] {
				0,1,2,
				2,3,0,
				
				4,5,6,
				6,7,4,
				
				8,9,10,
				10,11,8,
				
				12,13,14,
				14,15,12,
				
				16,17,18,
				18,19,16,
				
				20,21,22,
				23,22,20
				
		}, new float[] {
				0,0,1,
				0,0,1,
				0,0,1,
				0,0,1,
				
				1,0,0,
				1,0,0,
				1,0,0,
				1,0,0,
				
		});
		return rawModel;
	}
	
	public static RawModel createTexQuad2D(float width, float height, ModelLoader modelLoader) {
		float w = width/2f, h = height/2f;
		return modelLoader.loadPITToVoa(new float[] {
				-w,h,0f,
				w,h,0f,
				w,-h,0f,
				-w,-h,0f
		}, new float[] {
				0,1,
				1,1,
				1,0,
				0,0
		}, new int[] {
				0,1,2,
				2,3,0
		});
	}
	
}
