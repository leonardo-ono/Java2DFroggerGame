package br.ol.ge.core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

/**
 * Display class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Display extends Canvas {

    public static final int SCREEN_WIDTH = 224;
    public static final int SCREEN_HEIGHT = 256;
    
    public static final int SCALE_WIDTH = 2;
    public static final int SCALE_HEIGHT = 2;
    
    private Scene scene;
    private boolean running;
    private BufferStrategy bs;
    
    public Display(Scene scene) {
        this.scene = scene;
        setSize(SCREEN_WIDTH * SCALE_WIDTH, SCREEN_HEIGHT * SCALE_HEIGHT);
        addKeyListener(new Keyboard());
    }
    
    public void start() {
        scene.start();
        createBufferStrategy(3);
        bs = getBufferStrategy();
        running = true;
        new Thread(new MainLoop()).start();
    }
    
    private class MainLoop implements Runnable {

        @Override
        public void run() {
            boolean needsRender;
            while (running) {
                needsRender = false;
                Time.update();
                if (Time.needsUpdate) {
                    Time.needsUpdate = false;
                    while (Time.updateCount > 0) {
                        Time.updateCount--;
                        update();
                    }
                    needsRender = true;
                }
                if (needsRender) {
                    Graphics2D g = (Graphics2D) bs.getDrawGraphics();
                    g.setBackground(Color.BLACK);
                    g.clearRect(0, 0, getWidth(), getHeight());
                    g.scale(SCALE_WIDTH, SCALE_HEIGHT);
                    draw(g);
                    bs.show();
                }
            }
        }
    }
    
    private void update() {
        scene.update();
    }
    
    private void draw(Graphics2D g) {
        scene.draw(g);
        //g.setColor(Color.WHITE);
        //g.drawString("FPS: " + Time.fps, 2, 10);
    }
    
}
