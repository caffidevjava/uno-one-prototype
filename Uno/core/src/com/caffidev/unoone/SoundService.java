package com.caffidev.unoone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundService {
    public Sound drawCardSound;
    public Sound playCardSound;
    
    public SoundService(){
        drawCardSound = Gdx.audio.newSound(Gdx.files.internal("draw_card.mp3"));
        playCardSound = Gdx.audio.newSound(Gdx.files.internal("play_card.mp3"));
    }
}
