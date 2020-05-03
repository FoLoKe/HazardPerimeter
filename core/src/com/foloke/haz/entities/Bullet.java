package com.foloke.haz.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.foloke.haz.components.Damage;
import com.foloke.haz.components.Weapon;
import com.foloke.haz.screens.GameScreen;

import static com.foloke.haz.screens.GameScreen.sound;

public class Bullet extends DynamicEntity {
    public boolean active;
    private float speed;
    private float lifetime = 60 * 2f;
    private Weapon weapon;
    private Damage damage;

    public Bullet(TextureRegion textureRegion, World world, Weapon weapon) {
        super(textureRegion);
        this.weapon = weapon;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);
        body = world.createBody(bodyDef);
        body.setLinearDamping(0);
        speed = 20f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(textureRegion.getRegionWidth() / GameScreen.PPM / 8f, textureRegion.getRegionHeight() / GameScreen.PPM / 8f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
        fixtureDef.isSensor = true;
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;


        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        shape.dispose();

        body.setLinearDamping(0f);
        body.setGravityScale(0.01f);

        active = true;

        damage = new Damage(Damage.Type.PENETRATION, 10f);
        damage.damageClass = 1;
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        super.render(batch, delta);
        lifetime--;

        if(lifetime <= 0) {
            active = false;
        }
    }

    public void shoot(Vector2 position, Vector2 direction) {
        sound.play();
        direction.sub(position);
        body.setTransform(position, direction.angle() * MathUtils.degreesToRadians);
        body.setLinearVelocity(direction.nor().scl(speed));
    }

    public void hit(Entity entity) {
        if(entity != null) {
            if (entity != weapon.holder) {
                entity.applyDamage(damage);
                active = false;
            }
        } else {
            active = false;
        }
    }

    @Override
    public void applyDamage(Damage damage) {

    }
}
