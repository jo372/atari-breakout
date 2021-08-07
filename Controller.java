
import javafx.scene.input.KeyEvent;

// The breakout controller receives KeyPress events from the GUI (via
// the KeyEventHandler). It maps the keys onto methods in the model and
// calls them appropriately
public class Controller
{
  public Model model;
  public View  view;
  
  // we don't really need a constructor method, but include one to print a 
  // debugging message if required
  public Controller()
  {
    Debug.trace("Controller::<constructor>");
  }
  
  // This is how the View talks to the Controller
  // AND how the Controller talks to the Model
  // This method is called by the View to respond to key presses in the GUI
  // The controller's job is to decide what to do. In this case it converts
  // the keypresses into commands which are run in the model
  public void userKeyInteraction(KeyEvent event )
  {
    Debug.trace("Controller::userKeyInteraction: keyCode = " + event.getCode() );
      
    switch ( event.getCode() )             
    {
      case CLOSE_BRACKET:
           model.songId += 1;
           if (model.songId > model.musicPlayer.getMediaList().size()-1) {
               model.songId = 0;
           }
           model.musicPlayer.playSongById(model.songId);
           break;
      case OPEN_BRACKET:
           model.songId -= 1;
            if (model.songId < 0) {
               model.songId = model.musicPlayer.getMediaList().size()-1;
           }
           model.musicPlayer.playSongById(model.songId);
           break;
      case LEFT:                     
        // Left Arrow
        if (!(model.getBat().getTopX() < 0)) {
            model.moveBat( -1);          // move bat left
        }
        break;
      case RIGHT:                    
        // Right arrow
        if(!((model.getBat().getTopX() + model.getBat().getWidth()) > model.windowWidth))  {
            model.moveBat( +1 );         // Move bat right
        }
        break;
      case TAB:
        // getting the value of tab pressed and flipping it.
        model.isTabPressed = !model.isTabPressed;
        model.getScoreboard().setVisiblity(model.isTabPressed);
        break;
      case M:
        if(model.musicPlayer.getPlayer().isMute()) {
            model.musicPlayer.unmute();
        } else { 
            model.musicPlayer.mute();
        }  
        break;
      case F:
        model.gameFinished = true;
        break;
      case R: 
        break;
      case S :
        // stop the game
        model.setGameRunning(false);
        break;
        
    }
  }
}
