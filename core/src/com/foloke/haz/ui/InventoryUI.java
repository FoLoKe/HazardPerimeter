package com.foloke.haz.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.foloke.haz.components.Inventory;
import com.foloke.haz.screens.GameScreen;
import com.foloke.haz.utils.ItemsDataSheet;

import static com.foloke.haz.HPGame.skin;

public class InventoryUI extends DraggableWindow {
    private Table itemsTable;
    private Inventory inventory;
    private GameScreen gameScreen;

    public InventoryUI(UIStage uiStage, GameScreen gameScreen) {
        super(uiStage);

        this.gameScreen = gameScreen;
        itemsTable = new Table();
        ScrollPane scrollPane = new ScrollPane(itemsTable, skin);
        scrollPane.setScrollingDisabled(true,false);
        scrollPane.setScrollbarsVisible(true);
        scrollPane.setFadeScrollBars(false);

        itemsTable.align(Align.top);
        itemsTable.pad(10).padRight(50).defaults().expandX().space(4);
        this.add(scrollPane).expand().fill();
    }

    private static class TableRow extends Table{
        public TableRow(Inventory.Item item) {
            Label IDLabel = new Label("" + item.ID, skin);
            Label volumeLabel = new Label("" + item.count * ItemsDataSheet.getVolume(item.ID), skin);
            Label countLabel = new Label("" + item.count, skin);
            Label nameLabel = new Label("asdasd" + ItemsDataSheet.getName(item.ID), skin);

            Container<Label> container = new Container<>(nameLabel);
            container.setClip(true);
            container.setDebug(true);
            container.fillX();
            container.width(10);

            this.setDebug(true);
            this.add(IDLabel).padRight(10).width(16);
            this.add(container).padRight(10).align(Align.left).expandX();
            this.add(countLabel).padRight(10).width(16);
            this.add(volumeLabel).width(32);

        }
    }

    public void update(Inventory inventory) {
        this.inventory = inventory;
        itemsTable.clearChildren();
        for(Inventory.Item item : inventory.getAll()){
            itemsTable.row().align(Align.left).fill();
            TableRow tableRow = new TableRow(item);
            tableRow.setTouchable(Touchable.enabled);
            tableRow.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gameScreen.drop(inventory, item, 1);
                    update(inventory);
                }
            });

            tableRow.align(Align.left);
            tableRow.padBottom(5).defaults().space(4).fill();
            itemsTable.add(tableRow);
        }
    }
}
