package com.foloke.haz.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.foloke.haz.screens.GameScreen;

public abstract class DynamicEntity extends Entity {

    public DynamicEntity(TextureRegion textureRegion, World world) {
        super(textureRegion);
        createBody(textureRegion.getRegionWidth() / (float)GameScreen.PPM, textureRegion.getRegionHeight() / (float)GameScreen.PPM, world);

    }

    public DynamicEntity(TextureRegion textureRegion) {
        super(textureRegion);
    }

    public DynamicEntity(float w, float h, World world) {
        super(null);
        createBody(w, h, world);
    }

    private void createBody(float w, float h, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);
        body = world.createBody(bodyDef);
        body.setLinearDamping(0);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w, h);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;


        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        shape.dispose();

        body.setLinearDamping(0.1f);
    }
}
