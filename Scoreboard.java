import java.util.Arrays;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

public class Scoreboard
{
    
    private ArrayList<ScoreboardUser> users = new ArrayList<ScoreboardUser>();
    private boolean visible = false;

    public Scoreboard()
    {

    }
    
    public void render(GraphicsContext gc) {
        gc.setFill(Color.rgb(255,255,255,1));
        gc.fillText("rank:\t\tusername:\t\tscore:", 10, 100);
        for(ScoreboardUser user: users) {
            gc.fillText(user.getPosition() + "\t\t" + user.getUsername() + "\t\t" + user.getPlayerScore(), 10, (100 + (user.getPosition()*12)));
        }
        
    }
    
    /**
     * @return ArrayList<ScoreboardUser> - returns an array list of users.
     */
    public ArrayList<ScoreboardUser> getUsers() { return this.users; }
    
    /**
     * @param playerId the id of the player
     * @return ScoreboardUser from the {@link users} arraylist
    */
    public ScoreboardUser getUserById(int playerId) {
        return this.getUsers().get(playerId);
    }
    
    public boolean isVisible() { return this.visible; }
    
    public void setVisiblity(boolean newVisibility) { this.visible = newVisibility; }
    
    public void add(ScoreboardUser user) {
        users.add(user);
    }
}
