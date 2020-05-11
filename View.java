import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;

// The View class creates and manages the GUI for the application.
// It doesn't know anything about the game itself, it just displays
// the current state of the Model, and handles user input
public class View implements EventHandler<KeyEvent>
{ 
    // variables for components of the user interface
    public double width;       // width of window
    public double height;      // height of window
    public Color BACKGROUND_COLOR = Color.BLACK;
    public Color FONT_COLOR = Color.WHITE;
    //https://www.youtube.com/watch?v=-vP3XSoAr4Q&list=RDQM10JdQFoApS8&index=2
    
    // usr interface objects
    public Pane pane;       // basic layout pane
    public Canvas canvas;   // canvas to draw game on
    public Label infoText;  // info at top of screen
    public Label controlText; 
    
    // The other parts of the model-view-controller setup
    public Controller controller;
    public Model model;

    public BatObj   bat;            // The bat
    public BallObj   ball;           // The ball
    public ArrayList<BrickObj> bricks;     // The bricks
    public ArrayList<Buff> powerups; // powerups
    public ArrayList<Buff> debuffs; // debuffs
    public Scoreboard scoreboard;
    public int       score =  0;     // The score
    public int lives = 0;
    
    private String strControls = "] play next song\n[ play previous song\nM to mute music\nTab for scoreboard";
    // we don't really need a constructor method, but include one to print a 
    // debugging message if required
    public View(double w, double h)
    {
        Debug.trace("View::<constructor>");
        width = w;
        height = h;
    }

    // start is called from Main, to start the GUI up
    // Note that it is important not to create controls etc here and
    // not in the constructor (or as initialisations to instance variables),
    // because we need things to be initialised in the right order
    public void start(Stage window) 
    {
        // breakout is basically one big drawing canvas, and all the objects are
        // drawn on it as rectangles, except for the text at the top - this
        // is a label which sits 'on top of' the canvas.
        
        pane = new Pane();       // a simple layout pane
        pane.setId("Breakout");  // Id to use in CSS file to style the pane if needed
        
        // canvas object - we set the width and height here (from the constructor), 
        // and the pane and window set themselves up to be big enough
        canvas = new Canvas(width,height);
        
        pane.getChildren().add(canvas);     // add the canvas to the pane
        
        // infoText box for the score - a label which we position on 
        //the canvas with translations in X and Y coordinates
        infoText = new Label("BreakOut: Score = " + score + "\nLives:" + lives);
        infoText.setTranslateX(50);
        infoText.setTranslateY(10);
        infoText.setTextFill(FONT_COLOR);
        
        controlText = new Label(strControls);
        controlText.setTranslateX((width/100)*80);
        controlText.setTranslateY((height/100)*85);
        controlText.setTextFill(FONT_COLOR);
        pane.getChildren().addAll(infoText, controlText);  // add label to the pane
        
        // add the complete GUI to the scene
        Scene scene = new Scene(pane);   
        //scene.getStylesheets().add("breakout.css"); // tell the app to use our css file
        
        // Add an event handler for key presses. We use the View object itself
        // and provide a handle method to be called when a key is pressed.
        
        //pane.getChildren().add(model.playerScores);
        scene.setOnKeyPressed(this);
        
       
        // put the scene in the winodw and display it
        window.setScene(scene);
        window.show();
              
    }

    // Event handler for key presses - it just passes th event to the controller
    public void handle(KeyEvent event)
    {
        // send the event to the controller
        controller.userKeyInteraction( event );
    }
    
    // drawing the game
    public void drawPicture()
    {
        // the ball movement is runnng 'i the background' so we have
        // add the following line to make sure
        synchronized( Model.class )   // Make thread safe (because the bal
        {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            // clear the canvas to redraw
            gc.setFill(BACKGROUND_COLOR);
            gc.fillRect( 0, 0, width, height );
            
            // update score
            if(lives < 0) {
                infoText.setText("GAME OVER!!!");
                infoText.setFont(new Font("Arial", 30));
                infoText.setTranslateX((model.windowWidth/3));
                infoText.setTranslateY((model.windowHeight/2));
            } else if (model.bricks.size() == 0) {
                infoText.setText("YOU WON!!");
            } else {
                infoText.setText("BreakOut: Score = " + score + "\nLives:" + lives);
            }
            
                
            // draw the bat and ball
            //displayGameObj( gc, ball );   // Display the Ball
            //displayGameObj( gc, bat  );   // Display the Bat
            
            bat.render(gc);
            ball.render(gc);
            // rending powerups to the screen only if they are visible.
            for(GameObj powerup: powerups) {
                if(powerup.isVisible()) {
                    powerup.render(gc);
                }
            }
            
            // rending debuffs to the screen only if they are visible.
            for(GameObj debuff: debuffs) {
                if(debuff.isVisible()) {
                    debuff.render(gc);
                }
            }
            
            // rending bricks to the screen only if they are visible.
            for (GameObj brick: bricks) {
                if (brick.isVisible()) {
                    brick.render(gc);
                }
            } 
            
            
            if(scoreboard.isVisible()) {
                scoreboard.render(gc);
            }
            
        }
    }
    
    // Display a game object - it is just a rectangle on the canvas
    /*public void displayGameObj( GraphicsContext gc, GameObj go )
    {
        gc.setFill( go.getColor());
        gc.fillRect( go.getTopX(), go.getTopY(), go.getWidth(), go.getHeight() );
    }*/

    // This is how the Model talks to the View
    // This method gets called BY THE MODEL, whenever the model changes
    // It has to do whatever is required to update the GUI to show the new model status
    public void update()
    {
        // Get from the model the ball, bat, bricks & score
        ball    = model.getBall();              // Ball
        bricks  = model.getBricks();            // Bricks
        bat     = model.getBat();               // Bat
        score   = model.getScore();             // Score
        powerups = model.getPowerups();
        debuffs = model.getDebuffs();
        scoreboard = model.getScoreboard();
        lives = model.getLives();
        //Debug.trace("Update");
        drawPicture();                     // Re draw game
    }
}
