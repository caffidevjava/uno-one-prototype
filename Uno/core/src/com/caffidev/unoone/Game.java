package com.caffidev.unoone;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ScreenUtils;

import org.w3c.dom.Text;

public class Game extends ApplicationAdapter {
	protected Framerate framerate;
	protected SpriteBatch batch;
	protected Texture background;
	
	@Override
	public void create(){
		batch = new SpriteBatch();
		framerate = new Framerate();
		background = new Texture("background.jpg");
		Player player = new Player("Кеша");
	}
	
	@Override
	public void resize(int width, int height){ 
		framerate.resize(width, height);
	}
	
	@Override
	public void render() {
		// Clean screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(background, 0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		batch.end();

		framerate.renderWithUpdate();

		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){ Gdx.app.exit();}
	}
	
	@Override
	public void pause() { }
	
	@Override
	public void resume() { }
	
	@Override
	public void dispose() {
		framerate.dispose();
	}
}
