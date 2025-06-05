package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    Clip clip;
    Clip music;
    FloatControl fc ;
    URL soundURL[] = new URL[10];

    public Sound() {
        soundURL[0] = getClass().getResource("/sound/bgmusic.wav");
        soundURL[1] = getClass().getResource("/sound/gem.wav");
        soundURL[2] = getClass().getResource("/sound/fireball.wav");
        soundURL[3] = getClass().getResource("/sound/boots.wav");
        soundURL[4] = getClass().getResource("/sound/health.wav");
        soundURL[5] = getClass().getResource("/sound/victory.wav");
        soundURL[6] = getClass().getResource("/sound/game_over.wav");
    }

    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            if(i == 0){
                fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
                fc.setValue(-10f);
                music = clip;
            }
        }catch (Exception e) {

        }
    }

    public void play(){
        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        music.stop();
    }
}
