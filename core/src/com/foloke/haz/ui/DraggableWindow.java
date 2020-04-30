package com.foloke.haz.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;

import static com.foloke.haz.HPGame.skin;

public class DraggableWindow extends Table {

    UIStage uiStage;
    public DraggableWindow(UIStage uiStage) {
        this.uiStage = uiStage;
        align(Align.top);

        Container<Actor> container = new Container<>();
        container.setClip(true);
        container.setDebug(true);
        container.height(32);
        container.addListener(new DragListener() {
            final Vector2 vector2 = new Vector2();

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                DraggableWindow.this.moveBy(x - vector2.x, y - vector2.y);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                vector2.set(x, y);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        container.setTouchable(Touchable.enabled);
        container.align(Align.right);

        ImageButton closeButton = new ImageButton(skin);
        closeButton.setSize(32, 32);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                DraggableWindow.this.setVisible(false);
            }
        });

        container.setActor(closeButton);

        this.add(container).expandX().fill();
        row();

    }
}
