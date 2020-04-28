package com.foloke.haz.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class UIStage extends Stage {
    private boolean uiTouched;

    @Override
    public void addActor(Actor actor) {
        super.addActor(actor);
        actor.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                uiTouched = true;
                System.out.println("uiStage");
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    public void addUntouchableActor(Actor actor) {
        super.addActor(actor);
    }

    public boolean isUiTouched() {
        return uiTouched;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        uiTouched = false;
    }
}
