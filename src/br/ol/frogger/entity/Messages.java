package br.ol.frogger.entity;

import br.ol.frogger.FroggerEntity;
import br.ol.frogger.FroggerScene;
import br.ol.frogger.FroggerScene.SceneState;
import br.ol.ge.core.Display;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Messages class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Messages extends FroggerEntity {
    
    private String text = "";
    private int textLenght;
    private int textX;
    private long startTime;
    private Color textColor = Color.WHITE;
    
    public Messages(FroggerScene scene) {
        super(scene);
    }

    @Override
    public void init() {
        setCollidible(false);
    }

    @Override
    public void update() {
        setVisible(System.currentTimeMillis() - startTime < 3000);
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(textX - 8, 130, 8 * textLenght + 16, 12);
        scene.drawText(g, text, textX, 132, textColor);
    }

    @Override
    public void sceneStateChanged(SceneState newState) {
        if (newState != SceneState.PLAYING) {
            setVisible(false);
        }
    }

    public void show(String text, Color textColor) {
        this.text = text;
        this.textColor = textColor;
        textLenght = text.length();
        textX = (Display.SCREEN_WIDTH - 8 * textLenght) / 2;
        startTime = System.currentTimeMillis();
    }
    
}
