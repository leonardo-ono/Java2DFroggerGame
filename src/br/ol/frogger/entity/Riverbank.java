package br.ol.frogger.entity;

import br.ol.frogger.FroggerEntity;
import br.ol.frogger.FroggerScene;
import br.ol.frogger.FroggerScene.SceneState;
import static br.ol.frogger.FroggerScene.SceneState.*;

/**
 * Riverbank class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Riverbank extends FroggerEntity {
    
    private int id;
    
    public Riverbank(FroggerScene scene, int id, int x) {
        super(scene);
        this.id = id;
        this.x = x;
        this.y = 30;
    }

    public int getId() {
        return id;
    }

    @Override
    public void init() {
        loadAnimation("win", 2);
        setVisible(false);
    }

    @Override
    public void updatePlaying() {
        setAnimationFrame((int) (System.nanoTime() * 0.000000001) % frames.length);
    }
    
    @Override
    public void sceneStateChanged(SceneState newState) {
        if (newState == TITLE) {
            setVisible(false);
        }
    }
    
}
