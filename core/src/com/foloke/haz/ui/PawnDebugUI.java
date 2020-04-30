package com.foloke.haz.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.foloke.haz.entities.Character;
import com.foloke.haz.entities.Pawn;

import static com.foloke.haz.HPGame.skin;

public class PawnDebugUI extends DraggableWindow {

    private Pawn pawn;
    private Table table;
    private Label nameLabel;
    private Label hpLabel;
    private Label staminaLabel;
    private Label bioLabel;
    private Label radiationLabel;
    private Label suitLabel;
    private Label suitDurabilityLabel;

    public PawnDebugUI(UIStage gameScreen) {
        super(gameScreen);
        table = new Table();
        ScrollPane scrollPane = new ScrollPane(table, skin);
        scrollPane.setScrollingDisabled(true,false);
        scrollPane.setScrollbarsVisible(true);
        scrollPane.setFadeScrollBars(false);

        table.align(Align.top);
        table.pad(10).padRight(32).defaults().expandX();
        this.add(scrollPane).expand().fill();
    }

    public void debug(Pawn pawn) {
        this.pawn = pawn;
        table.clear();

        nameLabel = new Label(pawn.getName(), skin);
        table.add(nameLabel);
        table.row();

        hpLabel = new Label(Float.toString(pawn.getHp()), skin);
        table.add(hpLabel);
        table.row();

        staminaLabel = new Label(Float.toString(pawn.getStamina()), skin);
        table.add(staminaLabel);
        table.row();

        bioLabel = new Label(pawn.getBio() + " / " + pawn.getBioCap(), skin);
        table.add(bioLabel);
        table.row();

        radiationLabel = new Label(pawn.getRadiation() + " / " + pawn.getRadiationCap(), skin);
        table.add(radiationLabel);
        table.row();

        table.add(new Label(pawn.getRadiation() + " / " + pawn.getRadiationCap(), skin));
        table.row();

        if(pawn instanceof Character) {
            Character character = (Character)pawn;
            suitLabel = new Label("suit", skin);
            table.add(suitLabel);
            table.row();

            suitDurabilityLabel = new Label(character.getCostume().durability + " / " + character.getCostume().maxDurability, skin);
            table.add(suitDurabilityLabel);
            table.row();
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(pawn.destroyed) {
            nameLabel.setText(pawn.getName() + " - DEAD");
        }
        hpLabel.setText(Float.toString(pawn.getHp()));
        staminaLabel.setText(Float.toString(pawn.getStamina()));
        bioLabel = new Label(pawn.getBio() + " / " + pawn.getBioCap(), skin);
        radiationLabel = new Label(pawn.getRadiation() + " / " + pawn.getRadiationCap(), skin);

        if(pawn instanceof Character) {
            Character character = (Character)pawn;
            suitDurabilityLabel = new Label(character.getCostume().durability + " / " + character.getCostume().maxDurability, skin);
        }
    }
}
