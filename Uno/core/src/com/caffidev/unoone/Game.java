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
import com.caffidev.unoone.gui.DrawPileView;
import com.caffidev.unoone.gui.Framerate;
import com.caffidev.unoone.gui.InfoView;
import com.caffidev.unoone.gui.PackView;
import com.caffidev.unoone.gui.PlayerView;
import com.caffidev.unoone.gui.RotatingBackground;
import com.sun.source.tree.ReturnTree;

import java.util.ArrayList;
import java.util.List;

public class Game extends ApplicationAdapter {
	private static final float MIN_GAME_WIDTH = 960;
	private static final float MIN_GAME_HEIGHT = 540;
	private static final float MAX_GAME_WIDTH = 1280;
	private static final float MAX_GAME_HEIGHT = 720;
	
	private static boolean IS_DEBUG = false;
	private static boolean VSYNC = true;
	
	public static final Logger logger = new Logger("Uno-one");
	public static GameCardService gameService;
	public static SoundService soundService;
	
	public static List<PlayerView> playerViews;
	public static DrawPileView drawPileView;
	public static InfoView infoView;
	
	protected PackView pack;
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
		
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		logger.setLevel(Logger.DEBUG);
		logger.debug("Logger was instantiated.");
		
		gameService = new GameCardService("Player 1", "Player 2");
		soundService = new SoundService();
		
		playerViews = new ArrayList<>();
		var players = gameService.getPlayerInformation();
		
		// Player view adaptation
		for(int i = 0; i < players.size(); i++) {
			if(i == 0) playerViews.add(new PlayerView(stage, players.get(i), gameService, stage.getWidth() / 2f, 50f, 0));
			else if (i == 1) playerViews.add(new PlayerView(stage, players.get(i), gameService, stage.getWidth() / 2f, stage.getHeight() - 50f, 180f));
		}
		
		for (PlayerView playerView : playerViews) {
			playerView.update();
		}

		framerate = new Framerate(generator.generateFont(parameter), stage);
		
		drawPileView = new DrawPileView(stage, gameService);
		
		parameter.size = 20;
		pack = new PackView(stage, gameService, generator.generateFont(parameter));
		infoView = new InfoView(stage, gameService, generator.generateFont(parameter));
	
		infoView.updateTurn();
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
		
		if(infoView.sinceChange != -1) {
			infoView.updateError();
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
	
	public static void winnerFound(){
		
	}
}
