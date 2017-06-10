package br.ol.frogger.entity;

import br.ol.frogger.FroggerEntity;
import br.ol.frogger.FroggerScene;
import static br.ol.frogger.FroggerScene.SceneState.*;
import java.awt.Graphics2D;

/**
 * Background class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Background extends FroggerEntity {

    public Background(FroggerScene scene) {
        super(scene);
    }

    @Override
    public void init() {
        loadImage("background.png");
        setCollidible(false);
        setVisible(false);
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        boolean drawGrid = false;
        if (drawGrid) {
            for (int dy=0; dy<16; dy++) {
                for (int dx=0; dx<15; dx++) {
                    g.drawRect(dx * 16 - 8, dy * 16, 16, 16);
                }
            }
        }
    }

    @Override
    public void sceneStateChanged(FroggerScene.SceneState newState) {
        setVisible(scene.getState() == PLAYING
            || scene.getState() == LEVEL_CLEARED
            || scene.getState() == GAME_OVER);
    }
    
}
