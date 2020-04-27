package com.foloke.haz.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.foloke.haz.screens.GameScreen;

public class StaticEntity extends Entity {

    public StaticEntity(TextureRegion textureRegion, World world) {
        super(textureRegion);

        BodyDef staticBodyDef = new BodyDef();
        staticBodyDef.position.set(0, 0);

        body = world.createBody(staticBodyDef);

        PolygonShape box = new PolygonShape();
        box.setAsBox(textureRegion.getRegionWidth() / GameScreen.PPM / 2f, textureRegion.getRegionHeight() / GameScreen.PPM / 2f);
        Fixture fixture = body.createFixture(box, 0.0f);
        fixture.setUserData(this);
        box.dispose();
    }
}
