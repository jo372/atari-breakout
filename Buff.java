import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;
import java.io.InputStream;

public class Buff extends GameObj
{
    private Image image = null;
    private int type = 0; 
    
    /**
     @param GraphicsContext gc
     a render function for this specified class
    */
    public void render(GraphicsContext gc) {
        if(this.getImage() == null) {
            gc.fillRect(this.getTopX(), this.getTopY(), this.getWidth(), this.getHeight() );
            gc.setFill(this.getColor());
        } else {
             gc.drawImage(image, this.getTopX(), this.getTopY());
        }
    }
    
    /**
     * @return Image
     */
    public Image getImage() { return this.image; }
    
    /**
     * @param Image the sprite to be used for the buff
     */
    public void setImage(Image newImage) { this.image = newImage;}

    /**
     * @param int the powerup type which is declared in the {@link Main} class
     */
    public void setType(int powerupType) { this.type = powerupType;}
    
    /**
     * @returns int the type of buff / debuff.
     */
    public int getType() { return this.type; }

    /**
     * the constructor information is the same as {@link GameObj#constructor()}.
     * @see GameObj
     */
    public Buff(int x, int y, int width, int height, Color color)
    {
        super(x, y, width, height, color);
    }
}

