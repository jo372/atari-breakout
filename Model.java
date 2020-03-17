    import java.util.ArrayList;
    import javafx.scene.paint.*;
    import javafx.application.Platform;
    import javafx.scene.media.Media;
    import javafx.scene.media.MediaPlayer;
    import javafx.util.Duration;
    import java.util.Arrays;
    // The model represents all the actual content and functionality of the app
    // For Breakout, it manages all the game objects that the View needs
    // (the bat, ball, bricks, and the score), provides methods to allow the Controller
    // to move the bat (and a couple of other fucntions - change the speed or stop 
    // the game), and runs a background process (a 'thread') that moves the ball 
    // every 20 milliseconds and checks for collisions 
    public class Model 
    {
        // Bat variables
        public BatObj bat;                 // The bat
        public Color BAT_COLOR = Color.web("#C84848");
        
        // Ball variables
        public BallObj ball;                // The ball
        public Color BALL_COLOR = Color.web("#C84848");
        public int BALL_SIZE      = 10;     // Ball side
        public int ballX = 0;
        public int ballY = 0;
        
        // Brick variables
        public ArrayList<BrickObj> bricks;   // The bricks
        public ArrayList<Color> BRICK_COLORS = new ArrayList<Color>(Arrays.asList(
            Color.web("#C84848"),
            Color.web("#C66C3A"),
            Color.web("#B47A30"),
            Color.web("#A2A22A"),
            Color.web("#48A048"),
            Color.web("#4248C8")
        ));
        public int brickBottomY = 0;
        public int BRICK_WIDTH = 40;     // Brick size
        public int BRICK_HEIGHT = 20;
        public int HIT_BRICK = 50;     // Score for hitting a brick
        public int HIT_BOTTOM = -200;   // Score (penalty) for hitting the bottom of the screen
        
        // Powerups variables 
        public ArrayList<PowerUpObj> powerups = new ArrayList<PowerUpObj>();
        public int powerupWidth = 10;
        public int powerupHeight = 10;
        public int POWERUP_MOVESPEED;
        public Color powerupColor = Color.PURPLE;
        // Debuff variables 
        public ArrayList<DebuffObj> debuffs = new ArrayList<DebuffObj>();
        
        // The other parts of the model-view-controller setup
        View view;
        Controller controller;
        
        // game variables 
        public int score = 0;               // The score
        public boolean gameRunning = true;  // Set false to stop the game
        public boolean gameFinished = false;
        
        // window variables
        public int windowWidth;                   // Width of game
        public int windowHeight;                  // Height of game
        
        // music player variables
        public MusicPlayer musicPlayer;
        boolean musicInitalised = false;
        int songId = 0;
        
        // CONSTRUCTOR - needs to know how big the window will be
        public Model( int w, int h )
        {
            Debug.trace("Model::<constructor>");  
            windowWidth = w; 
            windowHeight = h;
        }
        
        public ArrayList<BrickObj> createLevel(int maxColumns, int maxRows, ArrayList<Color> BrickColors) {
            
            // creating an new array list for the level
            ArrayList<BrickObj> level = new ArrayList<BrickObj>();
            
            // creating an row and column counter
            int rowCounter = 0;
            int columnCounter = 0;
            
            // reassigning brick width to fit the amount of columns we want.
            BRICK_WIDTH = windowWidth/maxColumns;
            
            // looping for how many rows we want.
            for (rowCounter = 0; rowCounter < maxRows; rowCounter++) { 
                Color brickColor = BRICK_COLORS.get(rowCounter); // what will I do about this??
                for (columnCounter = 0; columnCounter < maxColumns; columnCounter++) {
                    BrickObj brick = new BrickObj(BRICK_WIDTH*columnCounter, rowCounter*BRICK_HEIGHT, BRICK_WIDTH, BRICK_HEIGHT, brickColor);
                    level.add(brick);
                }
            }
            
            // returning the brick object array.
            return level;
        }
        
        // Initialise the game - reset the score and create the game objects 
        public void initialiseGame()
        {                
            // declaring a new media player.
            musicPlayer = new MusicPlayer("resources/music/");
           
            // creating a new ball object.
            ball   = new BallObj(windowWidth/2, windowHeight/2, BALL_SIZE, BALL_SIZE, BALL_COLOR );
            ball.setMoveSpeed(3);
            
            POWERUP_MOVESPEED = ball.getMoveSpeed();
            
            // creating a new bat object.
            bat    = new BatObj(windowWidth/2, windowHeight - BRICK_HEIGHT*3/2, BRICK_WIDTH*3, 
                BRICK_HEIGHT/4, BAT_COLOR);
            // setting the bat move speed.
            bat.setMoveSpeed(8); 
    
            // creating a new array for the bricks. 20 columns by amount of colours.
            bricks = this.createLevel(20, BRICK_COLORS.size(), BRICK_COLORS);
        }
    
        // Animating the game
        // The game is animated by using a 'thread'. Threads allow the program to do 
        // two (or more) things at the same time. In this case the main program is
        // doing the usual thing (View waits for input, sends it to Controller,
        // Controller sends to Model, Model updates), but a second thread runs in 
        // a loop, updating the position of the ball, checking if it hits anything
        // (and changing direction if it does) and then telling the View the Model 
        // changed.
    
        // When we use more than one thread, we have to take care that they don't
        // interfere with each other (for example, one thread changing the value of 
        // a variable at the same time the other is reading it). We do this by 
        // SYNCHRONIZING methods. For any object, only one synchronized method can
        // be running at a time - if another thread tries to run the same or another
        // synchronized method on the same object, it will stop and wait for the
        // first one to finish.
    
        // Start the animation thread
        public void startGame()
        {
            Thread t = new Thread( this::runGame );     // create a thread runnng the runGame method
            t.setDaemon(true);                          // Tell system this thread can die when it finishes
            t.start();                                  // Start the thread running
        }   
        
        //TODO: implement a pause game, we can do this by stop the ball and bat moving.
        // need to store their previous values elsewhere and reset when the game isn't paused anymore.
        
        // The main animation loop
        private double generateRandomNumber(int min, int max) {
            return min + Math.random() * (max - min);
        }
        
        public void runGame()
        {
            try
            {
                // set gameRunning true - game will stop if it is set false (eg from main thread)
                setGameRunning(true);
                while (getGameRunning())
                {
                    updateGame();                        // update the game state
                    modelChanged();                      // Model changed - refresh screen
                    Thread.sleep(1000/70);
                }
            } catch (Exception e) 
            { 
                Debug.error("Model::runAsSeparateThread error: " + e.getMessage() );
            }
        }
    
        // updating the game - this happens about 50 times a second to give the impression of movement
        public synchronized void updateGame()
        {   
            // if the music hasn't initalised and has songs, play first song.
            if (!musicInitalised && musicPlayer.hasSongs()) { 
                musicInitalised = true;
                musicPlayer.playSongById(songId);
            } else {
                // if the music player's time is greater than or equal to the song duration increment songid.
                if(musicPlayer.getPlayer().getCurrentTime().greaterThanOrEqualTo(musicPlayer.getMedia().getDuration())) 
                {
                    songId++;
                    musicPlayer.playSongById(songId);
                    // if the songId is equal to that of the medialist size, we're on the last song :(, time to repeat.
                    if(songId == musicPlayer.getMediaList().size() -1) { 
                        songId = 0; 
                    }
                    Debug.trace("SongId: %d PlaylistSize: %d", songId, musicPlayer.getMediaList().size());
                }
            }
            
            // move the ball one step (the ball knows which direction it is moving in)
            ball.moveX(ball.getMoveSpeed());                      
            ball.moveY(ball.getMoveSpeed());
            
            for(int j=0; j < powerups.size(); j++){
                PowerUpObj powerup = powerups.get(j);
                if(powerup.isVisible()) { 
                    powerup.moveY(POWERUP_MOVESPEED);
                    if(powerup.hitBy(bat)) {
                        powerup.setVisible(false);
                        powerups.remove(j);
                        
                        //TODO: add more powerups
                        int powerupType = (int) this.generateRandomNumber(1, 2);
                        switch(powerupType) {
                            case 1:
                                bat.setMoveSpeed(bat.getMoveSpeed()+1);
                            break;
                            case 2:
                                score += 20;
                            break;
                    }
                    // depending on what random number we get will depend on what happens to the player.
                    //bat.setMoveSpeed(bat.getMoveSpeed()+5);
                }
            }
        }
        // get the current ball possition (top left corner)
        ballX = ball.getTopX();  
        ballY = ball.getTopY();
        
        // Deal with possible edge of board hit
        if (ballX >= windowWidth - BALL_SIZE)  ball.changeDirectionX();
        
        if (ballX <= 0)  ball.changeDirectionX();
        
        if (ballY >= windowHeight - BALL_SIZE)  // Bottom
        { 
            ball.changeDirectionY(); 
            addToScore( HIT_BOTTOM );     // score penalty for hitting the bottom of the screen
        }
        
        if (ballY <= 0)  ball.changeDirectionY();
        
        // looping through the bricks, if visible and hit by ball, change direciton of the ball, set visible false, addtoscore and remove from the bricks array for garbage collection.
        for(int i = 0; i < bricks.size(); i++) {
            
            BrickObj brick = bricks.get(i);
            
            if(brick.isVisible() && brick.hitBy(ball)) {
                
                // generating a new debuff or powerup if the threshold is greater or equal to 60%.
                if (brick.getRandomChance() >= 60) {
                    // generating a new random number between 1 & 2
                    
                    brickBottomY = (brick.getTopY() + BRICK_HEIGHT);
                    // getTopX / 2 to center the powerup,
                    PowerUpObj powerup = new PowerUpObj(brick.getTopX() + (BRICK_WIDTH/2), brickBottomY, powerupWidth, powerupHeight, powerupColor);
                    powerups.add(powerup);
                }
                
                ball.changeDirectionY();
                brick.setVisible(false);
                addToScore(HIT_BRICK);
                bricks.remove(i);
            }
        }
        
        
        // check whether ball has hit the bat
        if ( ball.hitBy(bat) ) {
            ball.changeDirectionY();
        }
            
    }

    // This is how the Model talks to the View
    // Whenever the Model changes, this method calls the update method in
    // the View. It needs to run in the JavaFX event thread, and Platform.runLater 
    // is a utility that makes sure this happens even if called from the
    // runGame thread
    public synchronized void modelChanged()
    {
        Platform.runLater(view::update);
    }

    // Methods for accessing and updating values
    // these are all synchronized so that the can be called by the main thread 
    // or the animation thread safely

    // Change game running state - set to false to stop the game
    public synchronized void setGameRunning(Boolean value)
    {  
        gameRunning = value;
    }

    // Return game running state
    public synchronized Boolean getGameRunning()
    {  
        return gameRunning;
    }
    
    // Return bat object
    public synchronized BatObj getBat()
    {
        return(bat);
    }

    // return ball object
    public synchronized BallObj getBall()
    {
        return(ball);
    }

    // return bricks
    public synchronized ArrayList<BrickObj> getBricks()
    {
        return(bricks);
    }
    
    public synchronized ArrayList<PowerUpObj> getPowerups() {
        return(powerups);
    }
    
    public synchronized ArrayList<DebuffObj> getDebuffs() {
        return(debuffs);
    }
    
    // return score
    public synchronized int getScore()
    {
        return(score);
    }
    

    // update the score
    public synchronized void addToScore(int n)    
    {
        score += n;        
    }

    // move the bat one step - -1 is left, +1 is right
    public synchronized void moveBat( int direction )
    {        
        int dist = direction * bat.getMoveSpeed();    // Actual distance to move
        Debug.trace( "Model::moveBat: Move bat = " + dist );
        bat.moveX(dist);
    }
}   
