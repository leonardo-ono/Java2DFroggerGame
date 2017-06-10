package br.ol.frogger;

import static br.ol.frogger.FroggerScene.SceneState.*;
import br.ol.frogger.entity.Background;
import br.ol.frogger.entity.Floatable;
import br.ol.frogger.entity.Frog;
import static br.ol.frogger.entity.Frog.FrogState.*;
import br.ol.frogger.entity.HUD;
import br.ol.frogger.entity.Initializer;
import br.ol.frogger.entity.Messages;
import br.ol.frogger.entity.OLPresents;
import br.ol.frogger.entity.Obstacle;
import br.ol.frogger.entity.Riverbank;
import br.ol.frogger.entity.Title;
import br.ol.ge.core.*;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * FrScene class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class FroggerScene extends Scene {

    private final BitmapFontRenderer bitmapFontRenderer = new BitmapFontRenderer("/res/font8x8.png", 16, 16);
    
    public static enum SceneState { INITIALIZING, OL_PRESENTS, TITLE, PLAYING, LEVEL_CLEARED, GAME_OVER }
    private SceneState sceneState = INITIALIZING;
    
    private Frog frog;
    private Messages messages;
    private final Riverbank[] riverbanks = new Riverbank[5];
    
    private double time = 0; // 0.0~1.0
    private int lives = 4;
    private int score;
    private int hiscore;
    private long playingStartTime;
    
    public FroggerScene() {
    }

    public Messages getMessages() {
        return messages;
    }

    public SceneState getState() {
        return sceneState;
    }

    public void changeState(SceneState state) {
        if (this.sceneState != state) {
            this.sceneState = state;
            stateChanged(state);
        }
    }

    public double getTime() {
        return time;
    }
    
    public void resetTime() {
        time = 0;
    }
    
    public void resetPlayingStartTime() {
        playingStartTime = System.currentTimeMillis();
    }
    
    public void incTime(double t) {
        time += t;
        if (time > 1) {
            time = 1;
        }
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }
    
    public String getScoreDigit(int d) {
        return (int) ((score / Math.pow(10, d)) % 10) + "";
    }
    
    public void addScore(int points) {
        score += points;
    }
    
    public int getHiscore() {
        return hiscore;
    }

    public String getHiscoreDigit(int d) {
        return (int) ((hiscore / Math.pow(10, d)) % 10) + "";
    }
    
    public void updateHiscore() {
        if (score > hiscore) {
            hiscore = score;
        }
    }
    
    @Override
    public void createAllEntities() {
        addEntity(new Initializer(this));
        addEntity(new OLPresents(this));
        addEntity(new Title(this));
        
        addEntity(new Background(this));
        addEntity(messages = new Messages(this));

        addEntity(riverbanks[0] = new Riverbank(this, 0, 27 - 19));
        addEntity(riverbanks[1] = new Riverbank(this, 1, 75 - 19));
        addEntity(riverbanks[2] = new Riverbank(this, 2, 123 - 19));
        addEntity(riverbanks[3] = new Riverbank(this, 3, 171 - 19));
        addEntity(riverbanks[4] = new Riverbank(this, 4, 218 - 19));

        addEntity(new Obstacle(this, "obstacle_top_left", 0, 26, 0, 1));
        addEntity(new Obstacle(this, "obstacle_top_middle", 27, 26, 0, 1));
        addEntity(new Obstacle(this, "obstacle_top_middle", 75, 26, 0, 1));
        addEntity(new Obstacle(this, "obstacle_top_middle", 123, 26, 0, 1));
        addEntity(new Obstacle(this, "obstacle_top_middle", 171, 26, 0, 1));
        addEntity(new Obstacle(this, "obstacle_top_right", 219, 26, 0, 1));
        
        addEntity(new Obstacle(this, "car_c", 0, 144, -0.5, 1));
        addEntity(new Obstacle(this, "car_c", 48, 144, -0.5, 1));
        addEntity(new Obstacle(this, "car_c", 132, 144, -0.5, 1));
        addEntity(new Obstacle(this, "car_c", 196, 144, -0.5, 1));

        addEntity(new Obstacle(this, "car_e", 10, 160, 2, 1));
        
        addEntity(new Obstacle(this, "car_b", 0, 176, -1, 1));
        addEntity(new Obstacle(this, "car_b", 75, 176, -1, 1));
        addEntity(new Obstacle(this, "car_b", 150, 176, -1, 1));
        
        addEntity(new Obstacle(this, "car_d", 30, 192, 0.75, 1));
        addEntity(new Obstacle(this, "car_d", 80, 192, 0.75, 1));
        addEntity(new Obstacle(this, "car_d", 180, 192, 0.75, 1));
        
        addEntity(new Obstacle(this, "car_a", 0, 208, 1, 1));
        addEntity(new Obstacle(this, "car_a", 128, 208, 1, 1));
        
        addEntity(new Floatable(this, "log", 1, 0, 48, 0.5, false, 0, 0, 0));
        addEntity(new Floatable(this, "log", 1, 64, 48, 0.5, false, 0, 0, 0));
        addEntity(new Floatable(this, "log", 1, 128, 48, 0.5, false, 0, 0, 0));
        addEntity(new Floatable(this, "log", 1, 192, 48, 0.5, false, 0, 0, 0));

        addEntity(new Floatable(this, "turtle", 3, 0, 64, -0.5, false, 0, 0, 0));
        addEntity(new Floatable(this, "turtle", 3, 16, 64, -0.5, false, 0, 0, 0));
        
        addEntity(new Floatable(this, "turtle", 3, 48, 64, -0.5, false, 0, 0, 0));
        addEntity(new Floatable(this, "turtle", 3, 64, 64, -0.5, false, 0, 0, 0));

        addEntity(new Floatable(this, "turtle", 3, 96, 64, -0.5, false, 0, 0, 0));
        addEntity(new Floatable(this, "turtle", 3, 112, 64, -0.5, false, 0, 0, 0));

        addEntity(new Floatable(this, "turtle", 6, 144, 64, -0.5, true, 60, 20, 3));
        addEntity(new Floatable(this, "turtle", 6, 160, 64, -0.5, true, 60, 20, 3));
 
        addEntity(new Floatable(this, "turtle", 3, 192, 64, -0.5, false, 0, 0, 0));
        addEntity(new Floatable(this, "turtle", 3, 208, 64, -0.5, false, 0, 0, 0));
       
        addEntity(new Floatable(this, "log", 1, 0, 80, 0.5, false, 0, 0, 0));
        addEntity(new Floatable(this, "log", 1, 80, 80, 0.5, false, 0, 0, 0));
        addEntity(new Floatable(this, "log", 1, 160, 80, 0.5, false, 0, 0, 0));

        addEntity(new Floatable(this, "log", 1, 64, 96, 1, false, 0, 0, 0));
        addEntity(new Floatable(this, "log", 1, 160, 96, 1, false, 0, 0, 0));
        addEntity(new Floatable(this, "log", 1, 192, 96, 1, false, 0, 0, 0));

        addEntity(new Floatable(this, "turtle", 3, 0, 112, -1, false, 0, 0, 0));
        addEntity(new Floatable(this, "turtle", 3, 16, 112, -1, false, 0, 0, 0));
        addEntity(new Floatable(this, "turtle", 3, 32, 112, -1, false, 0, 0, 0));

        addEntity(new Floatable(this, "turtle", 6, 64, 112, -1, true, 60, 20, 3));
        addEntity(new Floatable(this, "turtle", 6, 80, 112, -1, true, 60, 20, 3));
        addEntity(new Floatable(this, "turtle", 6, 96, 112, -1, true, 60, 20, 3));

        addEntity(new Floatable(this, "turtle", 3, 128, 112, -1, false, 0, 0, 0));
        addEntity(new Floatable(this, "turtle", 3, 144, 112, -1, false, 0, 0, 0));
        addEntity(new Floatable(this, "turtle", 3, 160, 112, -1, false, 0, 0, 0));

        addEntity(new Floatable(this, "turtle", 3, 192, 112, -1, false, 0, 0, 0));
        addEntity(new Floatable(this, "turtle", 3, 208, 112, -1, false, 0, 0, 0));
        //addEntity(new Floatable(this, "turtle", 3, 224, 112, -1, false, 0, 0, 0));

        
        addEntity(frog = new Frog(this));
        
        addEntity(new HUD(this));
    }
    
    public Entity checkCollision(Entity e1) {
        FroggerEntity fe1 = (FroggerEntity) e1;
        fe1.updateCollider();
        for (Entity e2 : getEntities()) {
            FroggerEntity fe2 = (FroggerEntity) e2;
            if (e1 == e2 || !e1.isVisible() || !e1.isVisible()
                    || !fe1.isCollidible() || !fe2.isCollidible()) {
                continue;
            }
            fe2.updateCollider();
            if (fe1.getCollider().intersects(fe2.getCollider())) {
                return fe2;
            }
        }
        return null;
    }

    @Override
    public void update() {
        checkTime();
        super.update();

        //long winTimeInSeconds = (System.currentTimeMillis() - playingStartTime) / 1000;
        //System.out.println("winTimeInSeconds = " + winTimeInSeconds);
    }
    
    private void checkTime() {
        if (sceneState != PLAYING 
                || frog.getFrogState() == WIN 
                || frog.getFrogState() == DYING 
                || frog.getFrogState() == DIED) {
            return;
        }
        incTime(0.001);
        if (time >= 1) {
            messages.show("TIME OUT", Color.RED);
            frog.die();
        }
    }
    
    public void drawText(Graphics2D g, String text, int x, int y, Color overrideColor) {
        bitmapFontRenderer.drawText(g, text, x, y, overrideColor);
    }
    
    private void stateChanged(SceneState newState) {
        for (Entity entity : getEntities()) {
            ((FroggerEntity) entity).sceneStateChanged(newState);
        }
    }

    private void hideAllRiverbanks() {
        for (Riverbank riverbank : riverbanks) {
            riverbank.setVisible(false);
        }        
    }
    
    public void startGame() {
        lives = 4;
        score = 0;
        hideAllRiverbanks();
        resetTime();
        resetPlayingStartTime();
        changeState(PLAYING);
    }
    
    public int computeWinTime() {
        long winTimeInSeconds = (System.currentTimeMillis() - playingStartTime) / 1000;
        // System.out.println("winTimeInSeconds = " + winTimeInSeconds);
        return (int) winTimeInSeconds;
    }
    
    public boolean tryNextLife() {
        lives--;
        if (lives <= 0) {
            changeState(GAME_OVER);
            return false;
        }
        resetTime();
        resetPlayingStartTime();
        return true;
    }

    public void backToTitle() {
        updateHiscore();
        lives = 4;
        score = 0;
        changeState(TITLE);
    }

    public boolean isLevelCleared() {
        for (Riverbank riverbank : riverbanks) {
            if (!riverbank.isVisible()) {
                return false;
            }
        }
        return true;
    }
        
    public void nextLevel() {
        // TODO: change to next level
        resetTime();
        resetPlayingStartTime();
        hideAllRiverbanks();
        changeState(PLAYING);
    }
    
}
