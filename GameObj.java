import javafx.scene.paint.*;
import javafx.scene.canvas.GraphicsContext;
// An object in the game, represented as a rectangle, with a position,
// a colour, and a direction of movement.
public class GameObj
{
    // state variables for a game object
    private boolean visible  = true;     // Can see (change to false when the brick gets hit)
    private int topX   = 0;              // Position - top left corner X
    private int topY   = 0;              // position - top left corner Y
    private int width  = 0;              // Width of object
    private int height = 0;              // Height of object
    private Color colour;                // Colour of object
    private int dirX   = 1;            // Direction X (1 or -1)
    private int dirY   = 1;            // Direction Y (1 or -1)
    private int moveSpeed = 3; 
    private double opacity = 1.0;
    
    /**
     * @param x x position of the game object
     * @param y y position of the game object
     * @param w width of the game object
     * @param h height of the game object
     * @param c Color of the game Obj
     */
    public GameObj( int x, int y, int w, int h, Color c )
    {
        this.setTopX(x);
        this.setTopY(y);
        this.setWidth(w);
        this.setHeight(h);
        this.setColor(c);
    }
    
    public void setVisible(boolean newVisibility) { this.visible = newVisibility; }
    
    public boolean isVisible() { return this.visible; }
    
    public int getTopX() { return this.topX; }
    
    public void setTopX(int newTopXValue) { this.topX = newTopXValue; }
    
    public int getTopY() { return this.topY; }
    
    public void setTopY(int newTopYValue) { this.topY = newTopYValue; }
    
    public int getWidth() { return this.width; }
   
    public void setWidth(int newWidth) { this.width = newWidth; }
    
    public int getHeight() { return this.height; }
    
    public void setHeight(int newHeight) { this.height = newHeight; }
    
    public Color getColor() { return this.colour; }
    
    public void setColor(Color newColor) { this.colour = newColor; }
    
    public int getDirX() { return this.dirX; }
    
    public void setDirX(int newDirX) { this.dirX = newDirX; }
    
    public int getDirY() { return this.dirY; }
    
    public void setDirY(int newDirY) { this.dirY = newDirY; }
    
    public int getMoveSpeed() { return this.moveSpeed; }
    
    public void setMoveSpeed(int newMoveSpeed) { this.moveSpeed = newMoveSpeed; }
    
    public void setOpacity(double newOpacity) { 
        this.opacity = newOpacity;
    }
    
    public void render(GraphicsContext gc) {
        gc.setFill(this.getColor());
        gc.fillRect(this.getTopX(), this.getTopY(), this.getWidth(), this.getHeight() );
    }
    
    public double getOpacity() { return this.opacity; }
    
    /** move in x axis */
    public void moveX( int units )
    {
        this.setTopX(this.getTopX() + (units * this.getDirX()) );
    }

    /** move in y axis */
    public void moveY( int units )
    {
        this.setTopY(this.getTopY() + (units * this.getDirY()) );
    }

    /** Detect collision between this object and the argument object
     * It's easiest to work out if they do NOT overlap, and then
      return the negative (with the ! at the beginning) */
    public boolean hitBy( GameObj obj )
    {
        return !(this.getTopX() >= (obj.getTopX() + obj.getWidth()) ||
                (this.getTopX() + this.getWidth()) <= obj.getTopX() ||
                this.getTopY() >= (obj.getTopY() + obj.getHeight()) ||
                (this.getTopY() + this.getHeight()) <= obj.getTopY());
    }

}
