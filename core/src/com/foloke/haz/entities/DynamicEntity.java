package com.foloke.haz.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.foloke.haz.screens.GameScreen;

public class DynamicEntity extends Entity {

    public DynamicEntity(TextureRegion textureRegion, World world) {
        super(textureRegion);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);
        body = world.createBody(bodyDef);
        body.setLinearDamping(0);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(textureRegion.getRegionWidth() / GameScreen.PPM / 4f, textureRegion.getRegionHeight() / GameScreen.PPM / 4f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;


        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        shape.dispose();

        body.setLinearDamping(0.1f);

        hp = 100;
    }

    public DynamicEntity(TextureRegion textureRegion) {
        super(textureRegion);
    }
}
