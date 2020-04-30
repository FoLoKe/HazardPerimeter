package com.foloke.haz.ui;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import static com.foloke.haz.HPGame.skin;

public class BTreeUI<T> extends DraggableWindow {
    Table tasksTable;

    public BTreeUI(UIStage uiStage) {
        super(uiStage);

        tasksTable = new Table();
        ScrollPane scrollPane = new ScrollPane(tasksTable, skin);
        scrollPane.setScrollingDisabled(true,false);
        scrollPane.setScrollbarsVisible(true);
        scrollPane.setFadeScrollBars(false);

        tasksTable.align(Align.top);
        tasksTable.pad(10).padRight(32).defaults().expandX();
        this.add(scrollPane).expand().fill();
    }

    public void debug(BehaviorTree<T> tree) {
        tasksTable.clear();
        addTask(tasksTable, tree);

    }

    private void addTask(Table table, Task<T> task) {

        UpdatableTaskLabel<T> label = new UpdatableTaskLabel<>(task.getClass().getSimpleName(), skin, task);
        table.add(label).expandX().align(Align.left);

        int childrenCount = task.getChildCount();
        if(childrenCount > 0) {
            table.row().padLeft(32);
            Table subTable = new Table();
            table.add(subTable).expandX().fillX();

            for (int i = 0; i < childrenCount; i++) {
                subTable.row();
                Task<T> subTask = task.getChild(i);
                addTask(subTable, subTask);
            }
        }
    }

    private static class UpdatableTaskLabel<T> extends Label {
        Task<T> task;
        public UpdatableTaskLabel(CharSequence charSequence,Skin skin, Task<T> task) {
            super(charSequence, skin);
            this.task = task;
        }

        @Override
        public void act(float delta) {
            setText(task.getClass().getSimpleName());
            setColor(chooseColor(task.getStatus()));

            super.act(delta);
        }

        private Color chooseColor(Task.Status status) {
            Color color = Color.GOLD;
            switch (status) {
                case RUNNING:
                    color = Color.GREEN;
                    break;
                case FAILED:
                    color = Color.RED;
                    break;
                case CANCELLED:
                    color = Color.BROWN;
                    break;
                case SUCCEEDED:
                    color = Color.BLUE;
                    break;
            }
            return color;
        }
    }
}
