package com.foloke.haz.controllers;

import com.foloke.haz.entities.Pawn;

import java.util.Random;

public class AI extends Controller {
    public enum Behavior {AGGRESSION, SCARE, PATROL, REST}
    public Behavior state;
    private Controller controller;
    public int timer;
    public int maxTimer;
    public Random random;

    public AI(Pawn entity) {
        super(entity);
        state = Behavior.REST;
        random = new Random();
    }

    public void act() {
        if(controller != null) {
            switch (state) {
                case REST:
                    stop();
                    break;
                case PATROL:
                    move(Controller.MovementType.NORMAL, true);
                    break;
            }
        }
    }
}
