package br.ol.frogger.entity;

import br.ol.frogger.FroggerEntity;
import br.ol.frogger.FroggerScene;
import br.ol.frogger.FroggerScene.SceneState;
import static br.ol.frogger.FroggerScene.SceneState.*;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * HUD class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class HUD extends FroggerEntity {
    
    public HUD(FroggerScene scene) {
        super(scene);
    }

    @Override
    public void init() {
        setCollidible(false);
        setVisible(false);
    }

    @Override
    public void draw(Graphics2D g) {
        // draw score
        for (int s = 0; s < 5; s++) {
            scene.drawText(g, scene.getScoreDigit(s), 58 - 8 * s, 8, Color.RED);
        }

        // draw hiscore
        for (int s = 0; s < 5; s++) {
            scene.drawText(g, scene.getHiscoreDigit(s), 126 - 8 * s, 8, Color.RED);
        }
        
        g.setColor(Color.BLACK);
        //time 
        g.fillRect(72, 248, (int) (120 * scene.getTime()), 8);
        // lives
        for (int l = scene.getLives(); l < 4; l++) {
            g.fillRect(1 + 8 * l, 241, 8, 7);
        }
        
        // game over
        if (scene.getState() == SceneState.GAME_OVER) {
            g.setColor(Color.BLACK);
            g.fillRect(60, 116, 104, 24);
            scene.drawText(g, "GAME OVER", 76, 124, Color.RED);
        }
    }
    
    @Override
    public void sceneStateChanged(FroggerScene.SceneState newState) {
        setVisible(scene.getState() == TITLE
            || scene.getState() == PLAYING
            || scene.getState() == LEVEL_CLEARED
            || scene.getState() == GAME_OVER);
    }
    
}
