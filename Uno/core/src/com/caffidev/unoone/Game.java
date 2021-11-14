package com.caffidev.unoone;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.caffidev.unoone.gui.Framerate;
import com.caffidev.unoone.gui.PackView;
import com.caffidev.unoone.gui.PlayerView;
import com.caffidev.unoone.gui.RotatingBackground;

import java.util.ArrayList;
import java.util.List;

public class Game extends ApplicationAdapter {
	private static final float MIN_GAME_WIDTH = 960;
	private static final float MIN_GAME_HEIGHT = 540;
	private static final float MAX_GAME_WIDTH = 1280;
	private static final float MAX_GAME_HEIGHT = 720;
	private static boolean IS_DEBUG = true;
	private static boolean VSYNC = false;
	
	public static final Logger logger = new Logger("Uno-one");
	protected PackView pack;
	protected GameCardService gameService;
	public static List<PlayerView> playerViews;
	protected Framerate framerate;
	protected RotatingBackground background;
	protected SpriteBatch batch;
	
	protected FreeTypeFontGenerator generator;
	protected FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	Stage stage;
	Viewport viewport;
	
	@Override
	public void create(){
		FreeTypeFontGenerator.setMaxTextureSize(2048);
		generator = new FreeTypeFontGenerator(Gdx.files.internal("JetBrainsMono-Light.ttf"));
		
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 15;
		parameter.mono = false;
		parameter.borderColor.add(Color.BLACK);
		parameter.borderWidth = 0.6f;
		
		viewport = new ExtendViewport(MIN_GAME_WIDTH, MIN_GAME_HEIGHT, MAX_GAME_WIDTH, MAX_GAME_HEIGHT);
		batch = new SpriteBatch();
		stage = new Stage(viewport, batch);
		
		Gdx.input.setInputProcessor(stage);
		
		stage.setDebugAll(IS_DEBUG);
		Gdx.graphics.setVSync(VSYNC);
		
		//Actors
		background = new RotatingBackground(new TextureRegion(new Texture("background.jpg")), 5f, stage);
		stage.addActor(background);
		framerate = new Framerate(generator.generateFont(parameter), stage);
		
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		logger.setLevel(Logger.DEBUG);
		logger.debug("Logger was instantiated.");
		
		gameService = new GameCardService("Hello", "World");
		
		playerViews = new ArrayList<>();
		var players = gameService.getPlayerInformation();
		for(int i = 0; i < players.size(); i++) {
			playerViews.add(new PlayerView(stage, players.get(i), gameService, (i+1) * 150));
		}
		
		parameter.size = 20;
		pack = new PackView(stage, gameService, generator.generateFont(parameter));
	}
	
	@Override
	public void resize(int width, int height){
		stage.getViewport().update(width,height,false);
	}
	
	@Override
	public void render() {
		// Clean screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		framerate.update();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		for (PlayerView playerView : playerViews) {
			playerView.update();
		}
		
		pack.update();
		
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){ Gdx.app.exit();}
		if(Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
			IS_DEBUG = !IS_DEBUG;
			stage.setDebugAll(IS_DEBUG);
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.F4)) {
			VSYNC = !VSYNC;
			Gdx.graphics.setVSync(VSYNC);
		}
	}
	
	@Override
	public void pause() { }
	
	@Override
	public void resume() { }
	
	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
		generator.dispose();
	}
}
