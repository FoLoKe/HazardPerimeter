package com.foloke.haz.controllers;

import com.badlogic.gdx.math.Vector2;
import com.foloke.haz.components.Inventory;
import com.foloke.haz.entities.Pawn;

public class Controller {
    Pawn pawn;
    private boolean inAir;
    public enum MovementType {WALK, RUN, NORMAL};

    public Controller(Pawn entity) {
        pawn = entity;
        pawn.fixRotation(true);
    }

    public void act(float delta) {

    }

    public void move(MovementType  type, boolean direction) {
        if(!inAir) {
            switch (type) {
                case RUN:
                    pawn.run(direction);
                    break;
                case WALK:
                    pawn.walk(direction);
                    break;
                case NORMAL:
                    pawn.move(direction);
                    break;
            }
        } else {
            pawn.walk(direction);
        }
    }

    public void stop() {
        pawn.stop();
    }

    public Vector2 getPosition() {
        return pawn.getPosition();
    }

    public void setInAir(boolean inAir) {
        this.inAir = inAir;
    }

    public void jump() {
        if(!inAir) {
            inAir = true;
            pawn.jump();
        }
    }

    public void shoot() {
        pawn.shoot();
    }

    public Inventory getInventory() {
        return pawn.getInventory();
    }

    public void pickUp() {
        pawn.pickUp();
    }

    public void lookTo(Vector2 vector2) {
        pawn.setSight(vector2);
    }
}
