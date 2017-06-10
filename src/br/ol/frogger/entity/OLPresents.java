package br.ol.frogger.entity;


import br.ol.frogger.FroggerEntity;
import br.ol.frogger.FroggerScene;
import br.ol.frogger.FroggerScene.SceneState;
import static br.ol.frogger.FroggerScene.SceneState.*;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * OLPresents class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class OLPresents extends FroggerEntity {
    
    private final String text = "O.L. PRESENTS";
    private int textIndex;

    public OLPresents(FroggerScene scene) {
        super(scene);
    }

    @Override
    public void init() {
        setCollidible(false);
    }

    @Override
    public void updateOLPresents() {
        yield:
        while (true) {
            switch (instructionPointer) {
                case 0:
                    waitTime = System.currentTimeMillis();
                    instructionPointer = 1;
                case 1:
                    while (System.currentTimeMillis() - waitTime < 100) {
                        break yield;
                    }
                    textIndex++;
                    if (textIndex < text.length()) {
                        instructionPointer = 0;
                        break yield;
                    }
                    waitTime = System.currentTimeMillis();
                    instructionPointer = 2;
                case 2:
                    while (System.currentTimeMillis() - waitTime < 3000) {
                        break yield;
                    }
                    setVisible(false);
                    waitTime = System.currentTimeMillis();
                    instructionPointer = 3;
                case 3:
                    while (System.currentTimeMillis() - waitTime < 100) {
                        break yield;
                    }
                    scene.changeState(TITLE);
                    break yield;
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        scene.drawText(g, text.substring(0, textIndex), 60, 111, Color.WHITE);
    }
    
    @Override
    public void sceneStateChanged(SceneState newState) {
        setVisible(false);
        if (scene.getState() == OL_PRESENTS) {
            setVisible(true);
            textIndex = 0;
        }
    }
        
}
