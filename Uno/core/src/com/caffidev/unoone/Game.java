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

import java.util.UUID;

public class Game extends ApplicationAdapter {
	public static final Logger logger = new Logger("Uno-one");
	protected Framerate framerate;
	protected SpriteBatch batch;
	protected TextureRegion background;
	protected float rotation;
	protected float rotationSpeed = 5;
	@Override
	public void create(){
		batch = new SpriteBatch();
		rotation = 0;
		framerate = new Framerate(batch);
		background = new TextureRegion(new Texture("background.jpg"));
		
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		logger.setLevel(Logger.DEBUG);
		logger.debug("Logger was instantiated.");
		
		GameCardService service = new GameCardService("Hello", "World");
		UUID playerId = service.getCurrentPlayer().getUuid();
		service.drawCard(playerId);
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
		batch.draw(background, 0, 0,Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 1.6f,1.6f,rotation);
		batch.end();
		
		rotation += rotationSpeed * Gdx.graphics.getDeltaTime();
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
