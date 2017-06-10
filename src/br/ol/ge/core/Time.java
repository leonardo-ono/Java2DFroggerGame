package br.ol.ge.core;

/**
 * Time class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Time {
    
    public static double delta;
    public static double desiredFrameRate = 1000000000 / 60.0;
    private static double currentTime;
    private static double lastTime;
    private static double unprocessedTime;
    public static int updateCount;
    public static boolean needsUpdate;
    
    private static double fpsCountTime;
    private static int fpsCount;
    public static int fps;
    
    public static void update() {
        currentTime = System.nanoTime();
        if (lastTime == 0) {
            lastTime = currentTime;
        }
        delta = currentTime - lastTime;
        unprocessedTime += delta;
        if (unprocessedTime > desiredFrameRate) {
            updateCount++;
            unprocessedTime -= desiredFrameRate;
            needsUpdate = true;
            fpsCount++;
        }
        fpsCountTime += delta;
        if (fpsCountTime > 1000000000) {
            fpsCountTime -= 1000000000;
            fps = fpsCount;
            fpsCount = 0;
        }
        lastTime = currentTime;
    }
    
}
