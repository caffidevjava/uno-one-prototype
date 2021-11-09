package com.caffidev.unoone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;

public class Framerate implements Disposable {
    long lastTimeCounted;
    private float sinceChange;
    private float framerate;
    private BitmapFont font;
    private SpriteBatch batch;
    private OrthographicCamera cam;

    public Framerate(){
        lastTimeCounted = TimeUtils.millis();
        sinceChange = 0;
        framerate = Gdx.graphics.getFramesPerSecond();
        font = new BitmapFont();
        batch = new SpriteBatch();
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    public void resize(int screenWidth, int screenHeight){
        cam = new OrthographicCamera(screenWidth, screenHeight);
        cam.translate(screenWidth /2, screenHeight /2 );
        cam.update();
        batch.setProjectionMatrix(cam.combined);
    }
    
    public void renderWithUpdate(){
        update();
        render();
    }
    
    public void update() {
        long delta = TimeUtils.timeSinceMillis(lastTimeCounted);
        lastTimeCounted = TimeUtils.millis();

        sinceChange += delta;
        if(sinceChange >= 1000) {
            sinceChange = 0;
            framerate = Gdx.graphics.getFramesPerSecond();
        }
    }

    public void render() {
        batch.begin();
        font.draw(batch, "Debug:\n"+(int)framerate + " fps", 3, Gdx.graphics.getHeight() - 3);
        batch.end();
    }

    public void dispose() {
        font.dispose();
        batch.dispose();
    }
}
