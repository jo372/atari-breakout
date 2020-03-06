import java.io.File;
import java.net.URLEncoder;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.ArrayList;

public class MusicPlayer
{
    private ArrayList<Media> mediaList;

    public void playSongById(int songId) {
        if (songId > this.getMediaList().size()) 
        {
        } 
        else 
        {
             MediaPlayer mediaPlayer = new MediaPlayer(mediaList.get(songId));
             mediaPlayer.setAutoPlay(true);
        }
    }
    
    public void resumeSong() {
    
    }
    
    public void restartPlaylist() {
    
    }
    
    public ArrayList<Media> getMediaList() {
        return this.mediaList;
    }
    
    public MusicPlayer(String baseDirectory)
    {
        File songDirectory = new File(baseDirectory);
  
        for (String fileName: songDirectory.list()) {
            String path = baseDirectory + fileName;
            Media newMedia = new Media(new File(path).toURI().toString());
            mediaList.add(newMedia);
            
            Debug.trace(path);
        }
       
        
       /* 
        * 
        * String songDirectoryPath = "./resources/music/";
        File songDirectory = new File(songDirectoryPath);
        String[] files = songDirectory.list();
        
        for (String file: files) {
            Debug.trace(URLEncoder.encode(file));
        }
        * 
        */
    }
    
    
}
