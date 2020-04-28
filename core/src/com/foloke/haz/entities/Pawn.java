package com.foloke.haz.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.foloke.haz.components.Damage;
import com.foloke.haz.controllers.Controller;
import com.foloke.haz.components.Inventory;
import com.foloke.haz.components.Weapon;

import java.util.ArrayList;
import java.util.List;

public abstract class Pawn extends DynamicEntity implements Contactable {
    private float linearSpeed = 10;
    private float jumpImpulse = 5;

    protected float radiation;
    protected float bio;
    protected float hp;
    protected float stamina;

    protected float radiationCap;
    protected float bioCap;

    private Controller controller;
    private List<Contactable> contacts;

    public Pawn(TextureRegion textureRegion, World world) {
        super(textureRegion, world);
        inventory = new Inventory(this);
        inventory.add(new Inventory.Item(1,10));
        contacts = new ArrayList<>();

        hp = 100;
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        super.render(batch, delta);
        controller.act(delta);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    public void move(boolean direction) {
        this.direction = direction;
        body.setLinearVelocity(direction ? -linearSpeed : linearSpeed, body.getLinearVelocity().y);
    }

    public void jump() {
        body.applyLinearImpulse(0, jumpImpulse, body.getLocalCenter().x, body.getLocalCenter().y, true);
    }

    public void walk(boolean direction) {
        this.direction = direction;
        body.setLinearVelocity(direction ? -linearSpeed / 2 : linearSpeed / 2, body.getLinearVelocity().y);
    }

    public void run(boolean direction) {
        this.direction = direction;
        body.setLinearVelocity(direction ? -linearSpeed * 2 : linearSpeed * 2, body.getLinearVelocity().y);
    }

    public void stop() {
        body.setLinearVelocity(body.getLinearVelocity().x / 1.5f, body.getLinearVelocity().y);
    }

    public abstract void shoot();

    public void contact(Contactable entity) {
        contacts.add(entity);
    }

    public void endContact(Contactable entity) {
        contacts.remove(entity);
    }

    public void pickUp() {
        for (Contactable entity : contacts) {
            entity.interact(this);
        }
    }

    @Override
    public void interact(Entity entity) {

    }
}
