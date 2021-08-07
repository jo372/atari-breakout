public class ScoreboardUser
{
    private String username = "";
    private int score = 0;
    private int position = 0;
    
    public ScoreboardUser(int playerLeaderboardPosition, String playerUsername, int playerScore)
    {
       this.position = playerLeaderboardPosition;
       this.username = playerUsername;
       this.score = playerScore;
    }
    
    public int getPosition() { return this.position; }
    
    public String getUsername() {
        return this.username;
    }
    
    public void updateScore(int newScore){ this.score = newScore; } 
    
    public void setUsername(String newPlayerName) { this.username = newPlayerName; }
    
    public int getPlayerScore() { 
        return this.score; 
    }
}
