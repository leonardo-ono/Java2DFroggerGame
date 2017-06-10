package br.ol.ge.core;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Entity class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Entity<T extends Scene> {
    
    protected T scene;
    protected double x, y;
    protected boolean visible;
    protected BufferedImage image;
    
    protected Map<String, BufferedImage[]> animations = new HashMap<String, BufferedImage[]>();
    protected BufferedImage[] frames;
    
    public Entity(T scene) {
        this.scene = scene;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public void init() {
    }
    
    public void update() {
    }
    
    public void draw(Graphics2D g) {
        if (image != null) {
            g.drawImage(image, (int) x, (int) y, null);
        }
    }
    
    public void loadImage(String resource) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/" + resource));
        } catch (IOException ex) {
            Logger.getLogger(Entity.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }
    
    public void loadAnimation(String name, int framesCount) {
        frames = new BufferedImage[framesCount];
        for (int f = 0; f < framesCount; f++) {
            loadImage(name + "_" + f + ".png");
            frames[f] = image;
        }
        animations.put(name, frames);
    }
    
    public void setAnimation(String name) {
        frames = animations.get(name);
    }
    
    public void setAnimationFrame(int f) {
        image = frames[f];
    }
    
}
