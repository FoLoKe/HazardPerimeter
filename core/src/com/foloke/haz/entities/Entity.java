package com.foloke.haz.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.foloke.haz.components.Inventory;
import com.foloke.haz.screens.GameScreen;

public abstract class Entity {
    private Sprite sprite;
    protected Body body;
    protected boolean direction;
    float hp = 0;

    public boolean destroyed;
    protected Inventory inventory;

    public Entity(TextureRegion texture) {
        this.sprite = new Sprite(texture);
    }

    public void render(SpriteBatch batch, float delta) {
        sprite.setCenterX(body.getPosition().x * GameScreen.PPM);
        sprite.setCenterY(body.getPosition().y * GameScreen.PPM);
        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        sprite.setFlip(direction, false);
        sprite.draw(batch);

    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public void setPosition(float x, float y) {
        body.setTransform(x, y, body.getAngle());
    }

    public void setPosition(Vector2 vector) {
        body.setTransform(vector, 0);
    }

    public void setX(float x) {
        body.setTransform(x, body.getPosition().y, body.getAngle());
    }

    public void setY(float y) {
        body.setTransform(body.getPosition().x, y, body.getAngle());
    }

    public void setRotation(float degrees) {
        body.setTransform(getPosition(), MathUtils.degreesToRadians * degrees);
    }

    public void fixRotation(boolean fixed) {
        body.setFixedRotation(fixed);
    }

    public void faceTo(boolean direction) {
        this.direction = direction;
    }

    public void debug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        Vector2 vector2 = new Vector2(body.getWorldCenter());
        vector2.x += direction ? -2 : 2;
        shapeRenderer.line(body.getWorldCenter().scl(GameScreen.PPM), vector2.scl(GameScreen.PPM));

    }

    public boolean getDirection() {
        return direction;
    }

    public void applyDamage(float damage) {
        if(hp > 0) {
            hp -= damage;
        }

        if(hp <= 0) {
            onDestroy();
        }
    }

    public void onDestroy() {
        destroyed = true;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void destroy() {
        body.getWorld().destroyBody(body);
    }
}
