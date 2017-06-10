package br.ol.frogger.entity;

import br.ol.frogger.FroggerEntity;
import br.ol.frogger.FroggerScene;
import static br.ol.frogger.FroggerScene.SceneState.*;
import static br.ol.frogger.entity.Frog.FrogState.*;
import br.ol.ge.core.Entity;
import br.ol.ge.core.Keyboard;
import java.awt.Color;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;

/**
 * Frog class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Frog extends FroggerEntity {
    
    public static enum FrogState { IDLE, JUMPING, DYING, DIED, WIN }
    private FrogState frogState = FrogState.IDLE;
    
    private Floatable floatable;
    
    private int jumpingDirectionX;
    private int jumpingDirectionY;
    private String lastDirection;
    
    private int frameIndex;
    
    public Frog(FroggerScene scene) {
        super(scene);
    }

    public FrogState getFrogState() {
        return frogState;
    }

    public void changeFrogState(FrogState frogState) {
        if (this.frogState != frogState) {
            this.frogState = frogState;
            frogStateChanged(frogState);
        }
    }

    @Override
    public void init() {
        loadAnimation("frog_dying", 7);
        loadAnimation("frog_left", 3);
        loadAnimation("frog_right", 3);
        loadAnimation("frog_down", 3);
        loadAnimation("frog_up", 3);
        reset();
        setVisible(false);
    }

    @Override
    public void updatePlaying() {
        switch (frogState) {
            case IDLE: updateIdle(); break;
            case JUMPING: updateJumping(); break;
            case DYING: updateDying(); break;
            case DIED: updateDied(); break;
            case WIN: updateWin(); break;
        }
        updateFloatable();
        checkCollisionWithObstacle();
        checkOutOfScreen();
        checkWin();
    }

    @Override
    public void updateLevelCleared() {
        frameIndex++;
        if (frameIndex > 180) {
            scene.nextLevel();
        }
    }
    
    @Override
    public void updateGameOver() {
        frameIndex++;
        if (frameIndex > 180 && Keyboard.isKeyPressed(KeyEvent.VK_SPACE)) {
            scene.backToTitle();
        }
        else if (frameIndex > 480) {
            scene.backToTitle();
        }
    }
    
    private void updateIdle() {
        if (Keyboard.isKeyPressed(VK_LEFT)) {
            jump(-2, 0);
        }
        else if (Keyboard.isKeyPressed(VK_RIGHT)) {
            jump(2, 0);
        }
        if (Keyboard.isKeyPressed(VK_UP)) {
            jump(0, -2);
        }
        else if (Keyboard.isKeyPressed(VK_DOWN)) {
            jump(0, 2);
        }
    }
    
    private void updateJumping() {
        frameIndex++;
        x += jumpingDirectionX;
        y += jumpingDirectionY;
        setAnimationFrame((int) ((frameIndex / 7.0) * (frames.length - 1)));
        if (frameIndex > 7) {
            changeFrogState(IDLE);
        }
    }
    
    private void updateDying() {
        frameIndex++;
        // System.out.println("dying ... " + frameIndex);
        setAnimationFrame((int) ((frameIndex / 64.0) * (frames.length - 1)));
        if (frameIndex > 64) {
            died();
        }
    }

    private void updateDied() {
        frameIndex++;
        // System.out.println("died ! " + frameIndex);
        if (frameIndex > 64) {
            if (scene.tryNextLife()) {
                reset();
            }
        }
    }

    private void updateWin() {
        frameIndex++;
        // System.out.println("win ! " + frameIndex);
        if (frameIndex > 64) {
            if (scene.isLevelCleared()) {
                scene.getMessages().show("LEVEL CLEARED", Color.CYAN);
                scene.changeState(LEVEL_CLEARED);
            }
            else {
                reset();
            }
        }
    }
 
    private void updateFloatable() {
        if (floatable != null && floatable.isSubmerged()) {
            die();
        }
        else if (floatable != null) {
            x += floatable.getVelocity();
        }
    }
    
    private void checkFloorCollision() {
        floatable = null;
        Entity collidedEntity = scene.checkCollision(this);
        if (collidedEntity != null && collidedEntity instanceof Floatable) {
            floatable = (Floatable) collidedEntity;
        }
        else if (y >= 48 && y <= 128) { // river
            die();
        }
    }
    
    private void checkCollisionWithObstacle() {
        Entity collidedEntity = scene.checkCollision(this);
        if (collidedEntity != null && collidedEntity instanceof Obstacle) {
            die();
        }
    }
    
    private void checkOutOfScreen() {
        if (x < 0) {
            x = 0;
            die();
        }
        else if (x > 224 - image.getWidth()) {
            x = 224 - image.getWidth();
            die();
        }
    }
    
    private void checkWin() {
        if (y < 48 && frogState == IDLE) {
            Entity collidedEntity = scene.checkCollision(this);
            if (collidedEntity != null && collidedEntity instanceof Riverbank) {
                if (collidedEntity.isVisible()) {
                    die();
                }
                else {
                    collidedEntity.setVisible(true);
                    win();
                }
            }
        }
    }
    
    private void reset() {
        scene.resetTime();
        scene.resetPlayingStartTime();
        setAnimation("frog_up");
        lastDirection = "up";
        setAnimationFrame(2);
        setVisible(true);
        setX(112);
        setY(226);
        changeFrogState(IDLE);
    }
    
    private void jump(int dx, int dy) {
        jumpingDirectionX = dx;
        jumpingDirectionY = dy;
        frameIndex = 0;
        lastDirection = dx > 0 ? "right" : lastDirection;
        lastDirection = dx < 0 ? "left" : lastDirection;
        lastDirection = dy > 0 ? "down" : lastDirection;
        lastDirection = dy < 0 ? "up" : lastDirection;
        setAnimation("frog_" + lastDirection);
        if (lastDirection.equals("up")) {
            scene.addScore(10);
            // TODO can't score twice (up, down, up)
        }
        changeFrogState(JUMPING);
    }
    
    public void die() {
        if (frogState == DYING || frogState  == DIED) {
            return;
        }
        frameIndex = 0;
        floatable = null;
        setAnimation("frog_dying");
        changeFrogState(DYING);
    }
    
    private void died() {
        frameIndex = 0;
        setVisible(false);
        changeFrogState(DIED);
    }
    
    private void win() {
        frameIndex = 0;
        setVisible(false);
        scene.addScore(200);
        scene.getMessages().show(scene.computeWinTime() + " SECONDS", Color.WHITE);
        changeFrogState(WIN);
    }
    
    private void frogStateChanged(FrogState newState) {
        if (newState == IDLE) {
            checkFloorCollision();
        }
    }

    @Override
    public void sceneStateChanged(FroggerScene.SceneState newState) {
        setVisible(scene.getState() == PLAYING);
        if (newState == LEVEL_CLEARED || newState == GAME_OVER) {
            frameIndex = 0;
        }
    }
    
}
