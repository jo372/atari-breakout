import java.util.ArrayList;
import javafx.scene.paint.*;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
// The model represents all the actual content and functionality of the app
// For Breakout, it manages all the game objects that the View needs
// (the bat, ball, bricks, and the score), provides methods to allow the Controller
// to move the bat (and a couple of other fucntions - change the speed or stop 
// the game), and runs a background process (a 'thread') that moves the ball 
// every 20 milliseconds and checks for collisions 
public class Model 
{
    // First,a collection of useful values for calculating sizes and layouts etc.
    public int BALL_SIZE      = 10;     // Ball side
    public int BRICK_WIDTH    = 40;     // Brick size
    public int BRICK_HEIGHT   = 20;
    
    public int HIT_BRICK      = 50;     // Score for hitting a brick
    public int HIT_BOTTOM     = -200;   // Score (penalty) for hitting the bottom of the screen

    // The other parts of the model-view-controller setup
    View view;
    Controller controller;

    // The game 'model' - these represent the state of the game
    // and are used by the View to display it
    public BallObj ball;                // The ball
    public int ballX = 0;
    public int ballY = 0;
    
    public ArrayList<BrickObj> bricks;   // The bricks
    public BatObj bat;                 // The bat
    public int score = 0;               // The score
 
    // variables that control the game 
    public boolean gameRunning = true;  // Set false to stop the game
    public boolean fast = false;        // Set true to make the ball go faster

    // initialisation parameters for the model
    public int windowWidth;                   // Width of game
    public int windowHeight;                  // Height of game
    
    MusicPlayer musicPlayer;
    boolean musicInitalised = false;
    int songId = 0;
    // CONSTRUCTOR - needs to know how big the window will be
    public Model( int w, int h )
    {
        Debug.trace("Model::<constructor>");  
        windowWidth = w; 
        windowHeight = h;
    }

    // Initialise the game - reset the score and create the game objects 
    public void initialiseGame()
    {       
        score = 0;
        ball   = new BallObj(windowWidth/2, windowHeight/2, BALL_SIZE, BALL_SIZE, Color.RED );
        ball.setMoveSpeed(3);
        
        bat    = new BatObj(windowWidth/2, windowHeight - BRICK_HEIGHT*3/2, BRICK_WIDTH*3, 
            BRICK_HEIGHT/4, Color.GRAY);
        // setting the bat move speed.
        bat.setMoveSpeed(3); 
        
        bricks = new ArrayList<>();
        // declaring a new media player.
        musicPlayer = new MusicPlayer("resources/music/");

        // TODO: add code to add songs 
        
        // *[1]******************************************************[1]*
        // * Fill in code to add the bricks to the arrayList            *
        // **************************************************************
        int NUM_BRICKS = windowWidth/BRICK_WIDTH;     // how many bricks fit on screen
        
        int rowCounter = 0;
        int maxRowsCounter = 4;
        int columnCounter = 0;
        int maxColumnCounter = 10;
        BRICK_WIDTH = windowWidth/maxColumnCounter;
        
        for (rowCounter = 0; rowCounter < maxRowsCounter; rowCounter++) { 
            for (columnCounter = 0; columnCounter < maxColumnCounter; columnCounter++) {
                BrickObj brick = new BrickObj(BRICK_WIDTH*columnCounter, rowCounter*BRICK_HEIGHT, BRICK_WIDTH, BRICK_HEIGHT,Color.BLUE);
                bricks.add(brick);      // add this brick to the list of bricks
            }
        }

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
    
    // The main animation loop
    
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
                //Thread.sleep( getFast() ? 10 : 20 ); // wait a few milliseconds
            }
        } catch (Exception e) 
        { 
            Debug.error("Model::runAsSeparateThread error: " + e.getMessage() );
        }
    }

    // updating the game - this happens about 50 times a second to give the impression of movement
    public synchronized void updateGame()
    {
        if (!musicInitalised) { 
            musicPlayer.playSongById(songId);
            musicInitalised = true;
        } else {
            // checking the duration and making sure the song has finished.
            if(musicPlayer.getMediaPlayer().getCurrentTime() != musicPlayer.getMediaPlayer().totalDurationProperty()) 
            {
                if(songId > musicPlayer.getMediaList().size()) { 
                    songId = 0; 
                } else { 
                    songId ++; 
                }
                musicPlayer.playSongById(songId);
            }
        }
        
        
        // move the ball one step (the ball knows which direction it is moving in)
        ball.moveX(ball.getMoveSpeed());                      
        ball.moveY(ball.getMoveSpeed());
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
            
       // check whether ball has hit a (visible) brick
        boolean hit = false;

        // *[2]******************************************************[2]*
        // * Fill in code to check if a visible brick has been hit      *
        // * The ball has no effect on an invisible brick               *
        // * If a brick has been hit, change its 'visible' setting to   *
        // * false so that it will 'disappear'                          * 
        // **************************************************************
        for(int i=0; i < bricks.size(); i++) {
            BrickObj brick = bricks.get(i);
            if(brick.isVisible() && brick.hitBy(ball)) {
                hit = true;
                brick.setVisible(false); // set the brick invisible
                addToScore(HIT_BRICK); // add to score for hitting a brick
                bricks.remove(i); // removing the brick from the array, it's not longer needed and garbage collection can clear it up.
            }
        }
    
        if (hit)
            ball.changeDirectionY();

        // check whether ball has hit the bat
        if ( ball.hitBy(bat) )
            ball.changeDirectionY();
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

    // Change game speed - false is normal speed, true is fast
    public synchronized void setFast(Boolean value)
    {  
        fast = value;
    }
    
    // Return game speed - false is normal speed, true is fast
    public synchronized Boolean getFast()
    {  
        return(fast);
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
    