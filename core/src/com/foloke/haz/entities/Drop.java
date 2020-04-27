package com.foloke.haz.entities;

import com.badlogic.gdx.physics.box2d.*;
import com.foloke.haz.components.Inventory;
import com.foloke.haz.screens.GameScreen;

import java.util.Random;

public class Drop extends DynamicEntity implements Contactable {
    Inventory.Item item;
    Random rnd;

    public Drop(World world, Inventory.Item item) {
        super(GameScreen.texture);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.2f, 0.2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0f;
        fixtureDef.isSensor = false;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        this.item = item;
        rnd = new Random();
        int spd = 2;
        int dx = rnd.nextBoolean() ? rnd.nextInt(spd) : - rnd.nextInt(spd);
        int dy = rnd.nextBoolean() ? rnd.nextInt(spd) : - rnd.nextInt(spd);
        body.setLinearVelocity(dx, dy);
        body.setLinearDamping(1);

        System.out.println("dropped " + item.ID + " x" + item.count);
    }

    @Override
    public void interact(Entity entity) {
        if (entity.inventory.add(item)) {
            destroyed = true;
        }
    }
}
