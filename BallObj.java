import javafx.scene.paint.Color;

public class BallObj extends GameObj
{
    public BallObj(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }
    
     // change direction of movement in x axis (-1, 0 or +1)
    public void changeDirectionX()
    {
        this.setDirX(-this.getDirX());
    }

    // change direction of movement in y axis (-1, 0 or +1)
    public void changeDirectionY()
    {
        this.setDirY(-this.getDirY());
    }
}