package com.foloke.haz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.foloke.haz.screens.GameScreen;
import com.foloke.haz.screens.MenuScreen;

public class HPGame extends Game {

	public static Skin skin;
	@Override
	public void create () {
		skin = new Skin(Gdx.files.internal("ui/ui.json"));
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {

	}
}
