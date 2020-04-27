package com.foloke.haz.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.foloke.haz.controllers.Controller;
import com.foloke.haz.components.Inventory;
import com.foloke.haz.components.Weapon;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends DynamicEntity {
    private float linearSpeed = 10;
    private float jumpImpulse = 5;
    private Weapon weapon;

    private Controller controller;
    private List<Contactable> contacts;

    public Pawn(TextureRegion textureRegion, World world) {
        super(textureRegion, world);
        weapon = new Weapon(textureRegion, world, this);
        inventory = new Inventory(this);
        inventory.add(new Inventory.Item(1,10));

        contacts = new ArrayList<>();
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        super.render(batch, delta);
        controller.act(delta);

        weapon.render(batch, delta);
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

    public void shoot() {
        if(weapon != null) {
            weapon.shoot();
        }
    }

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
}
