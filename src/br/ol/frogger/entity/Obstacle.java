package br.ol.frogger.entity;

import br.ol.frogger.FroggerEntity;
import br.ol.frogger.FroggerScene;
import static br.ol.frogger.FroggerScene.SceneState.*;

/**
 * Obstacle class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Obstacle extends FroggerEntity {
    
    private String animationBaseName;
    private double velocity;
    private int framesCount;
    
    public Obstacle(FroggerScene scene, String animationBaseName, int x, int y, double velocity, int framesCount) {
        super(scene);
        this.animationBaseName = animationBaseName;
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.framesCount = framesCount;
    }

    @Override
    public void init() {
        loadAnimation(animationBaseName, framesCount);
        y += (16 - image.getHeight()) / 2;
        setVisible(false);
    }

    @Override
    public void updatePlaying() {
        x += velocity;
        if (velocity > 0 && x > 256) {
            x = -image.getWidth();
        }
        else if (velocity < 0 && x < -image.getWidth()) {
            x = 256;
        }
        setAnimationFrame((int) (System.nanoTime() * 0.000000005) % frames.length);
    }

    @Override
    public void sceneStateChanged(FroggerScene.SceneState newState) {
        setVisible(scene.getState() == PLAYING
            || scene.getState() == LEVEL_CLEARED
            || scene.getState() == GAME_OVER);
    }

}
