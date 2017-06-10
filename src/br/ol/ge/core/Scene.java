package br.ol.ge.core;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Scene class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Scene {
    
    private final List<Entity> entities = new ArrayList<Entity>();
    
    public void start() {
        createAllEntities();
        initAllEntities();
    }
    
    public void createAllEntities() {
    }
    
    public void initAllEntities() {
        for (Entity entity : entities) {
            entity.init();
        }
    }

    public List<Entity> getEntities() {
        return entities;
    }
    
    public void addEntity(Entity entity) {
        entities.add(entity);
    }
    
    public void update() {
        for (Entity entity : entities) {
            entity.update();
        }
    }
    
    public void draw(Graphics2D g) {
        for (Entity entity : entities) {
            if (entity.isVisible()) {
                entity.draw(g);
            }
        }
    }
    
}
