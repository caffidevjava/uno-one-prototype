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
	// We don't need to write our own logger
	public static final Logger logger = new Logger("Uno-one");
	public static Stage stage;
	protected GameService gameService;
	protected Framerate framerate;
	protected SpriteBatch batch;
	protected TextureRegion background;
	@Override
	public void create(){
		Texture backgroundTexture = new Texture("background.jpg");
		background = new TextureRegion(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		//todo: pick a color
		
		framerate = new Framerate();
		logger.setLevel(Logger.DEBUG);
		logger.debug("Logging is instantiated.");
		batch = new SpriteBatch();
		stage = new Stage();
		Player player = new Player("Kesha");
		logger.debug(player.getUuid().toString());
		
		gameService = new GameService(2);
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
		batch.draw(background, 0, 0);
		
		batch.end(); //todo: fix a bug with a wallpaper
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
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
