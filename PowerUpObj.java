import javafx.scene.paint.Color;

public class PowerUpObj extends BrickObj
{
    // what do I want the powerups to be able to do? 
    // change speed
    
    public PowerUpObj(int x, int y, int width, int height, Color color)
    {
        super(x, y, width, height, color);
    }
}

// which object should the powerup extend?
// it should etend the brick object so it knows when it's invisible.
// it set visiblity is called, it should create a new object of it self and drop to the ground
// when the powerup is dropped it should then effect the bat?
// how will I keep track of these powerups?

