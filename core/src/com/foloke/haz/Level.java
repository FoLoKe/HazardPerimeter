package com.foloke.haz;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.foloke.haz.entities.Bullet;
import com.foloke.haz.entities.Entity;
import com.foloke.haz.screens.GameScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Level {
    private List<Entity> entityList;
    private GameScreen gameScreen;
    private MapRenderer mapRenderer;
    private TiledMap map;

    public Level(GameScreen gameScreen, String mapName) {
        this.gameScreen = gameScreen;

        map = new TmxMapLoader().load(mapName);
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get("ground");
        int w = (int)map.getProperties().get("tilewidth");
        int h = (int)map.getProperties().get("tileheight");
        float halfH = h / GameScreen.PPM / 2f;
        float halfW = w / GameScreen.PPM / 2f;


        for (int y = 0; y < collisionLayer.getHeight(); y++) {
            float blockWidth = 0;
            int startX = -1;
            int startY = y;

            for (int x = 0; x < collisionLayer.getWidth(); x++) {
                TiledMapTileLayer.Cell cell = collisionLayer.getCell(x, y);
                if (cell != null && cell.getTile() != null) {
                    if(startX == -1) {
                        startX = x;
                    }
                        blockWidth += 0.5f;
                } else {
                    if(blockWidth > 0) {
                        createCollision(startX, startY, blockWidth, 0.5f);
                        blockWidth = 0;
                        startX = -1;

                    }
                }
            }

            if(blockWidth > 0) {
                createCollision(startX, startY, blockWidth, 0.5f);
            }
        }

        entityList = new ArrayList<>();

    }

    public void createCollision(float x, float y, float w, float h) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x + w, y + h);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w,
                h);

        Body body = gameScreen.world.createBody(bodyDef);
        Fixture fixture = body.createFixture(shape, 0f);
        fixture.setUserData("collision");
        shape.dispose();
    }

    public void render(SpriteBatch batch) {
        mapRenderer.setView(gameScreen.getCamera());
        mapRenderer.render();

        Iterator<Entity> iterator = entityList.iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            if (entity.destroyed) {
                iterator.remove();
                entity.destroy();
            }
        }

        batch.begin();
        for (Entity entity : entityList) {
            entity.render(batch);
        }
        batch.end();
    }

//    private void load(FileHandle fileHandle) {
//
//        String s = fileHandle.readString();
//        String[] lines = s.split("\r\n");
//        int y = 0;
//        for (String line : lines) {
//            int x = 0;
//            for(String ch : line.split(" ")) {
//                if(ch.equals("1")) {
//                    Entity entity = new StaticEntity(gameScreen.texture, gameScreen.world);
//                    entity.setPosition(x, y);
//                    entityList.add(entity);
//                }
//                x += 1;
//            }
//
//            y -= 1;
//        }
//    }

    public void addEntity(Entity entity) {
        entityList.add(entity);
    }

    public void spawn(Entity entity) {
        addEntity(entity);
        MapObject mapObject = map.getLayers().get("spawners").getObjects().get("main");
        MapProperties prop = mapObject.getProperties();

        entity.setPosition((float)prop.get("x") / GameScreen.PPM, (float)prop.get("y") / GameScreen.PPM);
    }

    public void debug(ShapeRenderer shapeRenderer) {
        for (Entity entity : entityList) {
            entity.debug(shapeRenderer);
        }
    }
}
