import javafx.scene.paint.Color;
import java.util.Random;

public class BrickObj extends GameObj
{
    private double randomChance = generateRandomNumber(0, 100);
    //private double randomChance = 60.0; // for debugging
    public BrickObj(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }
    
    public double getRandomChance() {
        return this.randomChance;
    }
    
    private double generateRandomNumber(int min, int max) {
        return min + Math.random() * (max - min);
    }
}
