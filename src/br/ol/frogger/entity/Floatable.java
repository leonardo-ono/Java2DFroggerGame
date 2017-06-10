package br.ol.frogger.entity;

import br.ol.frogger.FroggerEntity;
import br.ol.frogger.FroggerScene;
import static br.ol.frogger.FroggerScene.SceneState.*;
import static br.ol.frogger.entity.Floatable.FloatableState.*;

/**
 * Floatable class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Floatable extends FroggerEntity {
    
    public enum FloatableState { FLOATED, SUBMERGING, SUBMERGED, FLOATING };
    private FloatableState floatableState = FLOATED;
    private String animationBaseName;
    private double velocity;
    private int framesCount;
    private boolean submersible;
    private boolean submerged;
    private int submersibleFramesUp;
    private int submersibleFramesDown;
    private int submersibleStartFrame;
    private int submersionFrameIndex;
    private double animationFrameIndex;
    
    public Floatable(FroggerScene scene, String animationBaseName, int framesCount, int x, int y, double velocity, boolean submersible, int submersibleFramesUp, int submersibleFramesDown, int submersibleStartFrame) {
        super(scene);
        this.animationBaseName = animationBaseName;
        this.framesCount = framesCount;
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.submersible = submersible;
        this.submersibleFramesUp = submersibleFramesUp;
        this.submersibleFramesDown = submersibleFramesDown;
        this.submersibleStartFrame = submersibleStartFrame;
    }

    public double getVelocity() {
        return velocity;
    }

    public boolean isSubmerged() {
        return submerged;
    }

    public FloatableState getFloatableState() {
        return floatableState;
    }

    public void changeFloatableState(FloatableState floatableState) {
        if (this.floatableState != floatableState) {
            this.floatableState = floatableState;
            floatableStateChanged(floatableState);
        }
    }

    @Override
    public void init() {
        loadAnimation(animationBaseName, framesCount);
        setAnimationFrame(0);
        y += (16 - image.getHeight()) / 2;
        setVisible(false);
    }

    @Override
    public void updatePlaying() {
        updateAllStates();
        switch (floatableState) {
            case FLOATED: updateFloated(); break;
            case SUBMERGING: updateSubmerging(); break;
            case SUBMERGED: updateSubmerged(); break;
            case FLOATING: updateFloating(); break;
        }
        updateAnimation();    
    }
    
    private void updateAllStates() {
        x += velocity;
        if (velocity > 0 && x > 224) {
            x = -image.getWidth();
        }
        else if (velocity < 0 && x < -image.getWidth()) {
            x = 224;
        }
    }

    private void updateFloated() {
        if (submersible) {
            submersionFrameIndex++;
            animationFrameIndex = (int) (System.nanoTime() * 0.000000005) % submersibleStartFrame;
        }
        if (submersionFrameIndex > submersibleFramesUp) {
            changeFloatableState(SUBMERGING);
        }
    }

    private void updateSubmerging() {
        animationFrameIndex += 0.1;
        if (animationFrameIndex > frames.length - 1) {
            animationFrameIndex = frames.length - 1;
            changeFloatableState(SUBMERGED);
        }
    }

    private void updateSubmerged() {
        submersionFrameIndex++;
        if (submersionFrameIndex > submersibleFramesDown) {
            changeFloatableState(FLOATING);
        }
    }

    private void updateFloating() {
        animationFrameIndex -= 0.1;
        if (animationFrameIndex <= submersibleStartFrame - 1) {
            animationFrameIndex = submersibleStartFrame - 1;
            changeFloatableState(FLOATED);
        }
    }
    
    private void updateAnimation() {
        if (submersible) {
            setAnimationFrame((int) animationFrameIndex);
        }
        else {
            setAnimationFrame((int) (System.nanoTime() * 0.000000005) % frames.length);
        }
    }

    private void floatableStateChanged(FloatableState floatableState) {
        submersionFrameIndex = 0;
        switch (floatableState) {
            case FLOATED: submerged = false; break;
            case SUBMERGING: animationFrameIndex = submersibleStartFrame; break;
            case SUBMERGED: submerged = true; break;
        }
        // System.out.println("submerged = " + submerged);
    }
    
    @Override
    public void sceneStateChanged(FroggerScene.SceneState newState) {
        setVisible(scene.getState() == PLAYING
            || scene.getState() == LEVEL_CLEARED
            || scene.getState() == GAME_OVER);
    }
    
}
