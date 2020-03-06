import java.io.File;
import java.net.URLEncoder;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.ArrayList;

public class MusicPlayer
{
    private ArrayList<String> mediaPaths;
    private MediaPlayer mediaPlayer;
    private Media media; 
    private boolean isPaused = false;
    
    public void playSongById(int songId) {
        if (songId > this.getMediaList().size()) 
        {
        } 
        else 
        {
             media = new Media(this.mediaPaths.get(songId));
             mediaPlayer = new MediaPlayer(media);
             this.play();
        }
    }
    
    public Media getMedia() { return this.media; }
    
    public MediaPlayer getMediaPlayer() { return this.mediaPlayer; }
    
    public void resumeSong() {
        if(isPaused) { 
            this.play();
            isPaused = false;
        }
    }
    
    public void pause() {
        mediaPlayer.pause();
    }
    
    public void stop() {
        mediaPlayer.stop();
    }
    
    public void play() {
        mediaPlayer.play();
    }
    
    public ArrayList<String> getMediaList() {
        return this.mediaPaths;
    }

    public MusicPlayer(String baseDirectory)
    {
        File songDirectory = new File(baseDirectory); 
        this.mediaPaths = new ArrayList<String>();
        for (String fileName: songDirectory.list()) {
            String path = baseDirectory + fileName;
            this.mediaPaths.add(new File(path).toURI().toString());
        }
    }
    
    
}
