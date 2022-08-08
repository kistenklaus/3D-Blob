package engine;

import engine.Engine;

public final class Clock {
	
	private static double dT;
	private static double tsl;
	public static double INV_FPS;
	private static long lupd;
	private static Engine engine;
	
	public static void init(Engine engine, int FPS) {
		Clock.INV_FPS = 1/FPS;
		Clock.engine = engine;
		lupd = System.currentTimeMillis();
	}
	
	public static void update() {
		long cupd = System.currentTimeMillis();
		tsl += (cupd-lupd)/1000d;
		lupd = cupd;
		if(tsl>INV_FPS) {
			dT = tsl;
			tsl = 0;
			engine.tick();
		}
	}
	
	public static float getDT() {
		return (float)Math.min(Clock.dT, 0.1f);
	}
}
