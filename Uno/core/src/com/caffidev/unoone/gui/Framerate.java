package com.caffidev.unoone.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.caffidev.unoone.Game;

public class Framerate {
    private Label label;
    long lastTimeCounted;
    private float sinceChange;
    private float framerate;

    public Framerate(BitmapFont font, Stage stage){
        label = new Label("0 fps", new Label.LabelStyle(font, Color.WHITE));
        label.setPosition(3f, stage.getHeight()-30f);
        stage.addActor(label);
        lastTimeCounted = TimeUtils.millis();
        sinceChange = 0;
        framerate = Gdx.graphics.getFramesPerSecond();
    }
    
    public void update() {
        long delta = TimeUtils.timeSinceMillis(lastTimeCounted);
        lastTimeCounted = TimeUtils.millis();

        sinceChange += delta;
        if(sinceChange >= 1000) {
            sinceChange = 0;
            framerate = Gdx.graphics.getFramesPerSecond();
        }
        label.setText("Debug:\n"+(int)framerate + " fps");
    }
}
