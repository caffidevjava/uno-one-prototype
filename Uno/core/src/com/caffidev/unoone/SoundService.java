package com.caffidev.unoone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundService {
    public Sound cardDrawSound;
    public Sound cardPlaySound;
    
    public SoundService(){
        cardDrawSound = Gdx.audio.newSound(Gdx.files.internal("draw_card.mp3"));
        cardPlaySound = Gdx.audio.newSound(Gdx.files.internal("play_card.mp3"));
    }
}
