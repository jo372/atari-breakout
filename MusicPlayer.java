import java.io.File;
import java.net.URLEncoder;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections; 

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;

public class MusicPlayer
{
    private ArrayList<String> mediaPaths;
    private MediaPlayer mediaPlayer;
    private Media media; 
    private boolean isPaused = false;
    private boolean isMuted = false;
    //private String[] whitelistedExtensions = new String[] {"mp3"};
    
    public void playSongById(int songId) {
        // checking for out of bounds.
        if (songId > this.getMediaList().size()) 
        {
        } 
        else 
        {
            
             media = new Media(this.mediaPaths.get(songId));
             mediaPlayer = new MediaPlayer(media);
             
             
             if (this.isMuted) { this.mute(); }
             this.play();
        }
    }
    
    public Media getMedia() { return this.media; }
    
    public MediaPlayer getPlayer() { return this.mediaPlayer; }
    
    public void resumeSong() {
        if(isPaused) { 
            this.play();
            isPaused = false;
        }
    }
    
    public void pause() {
        this.getPlayer().pause();
        isPaused = true;
    }
    
    public void stop() {
        this.getPlayer().stop();
    }
    
    public void play() {
        this.getPlayer().play();
    }
    
    public void mute() {
        this.getPlayer().setMute(true);
        isMuted = true;
    }
    
    public void unmute() {
        this.getPlayer().setMute(false);
        isMuted = false;
    }
    
    public ArrayList<String> getMediaList() {
        return this.mediaPaths;
    }
    
    public boolean hasSongs() {
        return this.getMediaList().size() > 0;
    }
    
    public MusicPlayer(String baseDirectory)
    {
        File songDirectory = new File(baseDirectory); 
        this.mediaPaths = new ArrayList<String>();
        for (String fileName: songDirectory.list()) {
            String path = baseDirectory + fileName;
            this.mediaPaths.add(new File(path).toURI().toString());
        }
        Collections.shuffle(mediaPaths); 
    }
    
    
}
