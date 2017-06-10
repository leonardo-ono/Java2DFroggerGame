package br.ol.frogger;

import br.ol.frogger.FroggerScene.SceneState;
import br.ol.ge.core.Entity;
import java.awt.Rectangle;

/**
 * FrEntity class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class FroggerEntity extends Entity<FroggerScene> {
    
    private final Rectangle collider = new Rectangle();
    private boolean collidible = true;
    
    protected long waitTime;
    protected int instructionPointer;
    
    public FroggerEntity(FroggerScene scene) {
        super(scene);
    }

    public Rectangle getCollider() {
        return collider;
    }

    public boolean isCollidible() {
        return collidible;
    }

    public void setCollidible(boolean collidible) {
        this.collidible = collidible;
    }
    
    public void updateCollider() {
        if (image != null) {
            collider.setBounds((int) x, (int) y, image.getWidth(), image.getHeight());
        }
    }

    @Override
    public void update() {
        switch (scene.getState()) {
            case INITIALIZING: updateInitializing(); break;
            case OL_PRESENTS: updateOLPresents(); break;
            case TITLE: updateTitle(); break;
            case PLAYING: updatePlaying(); break;
            case LEVEL_CLEARED: updateLevelCleared(); break;
            case GAME_OVER: updateGameOver(); break;
        }
    }
    
    public void updateInitializing() {
    }

    public void updateOLPresents() {
    }

    public void updateTitle() {
    }

    public void updatePlaying() {
    }

    public void updateLevelCleared() {
    }

    public void updateGameOver() {
    }
    
    public void sceneStateChanged(SceneState newState) {
    }
    
}
