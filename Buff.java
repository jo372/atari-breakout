import javafx.scene.paint.Color;

public class Buff extends GameObj
{
    private String iconUrl = "";
    private double weight = 1.0;
    
    public String getIconUrl() {
        return this.iconUrl;
    }

    public void setIconUrl(String newIconUrl) { this.iconUrl = newIconUrl; }
    
    public void setWeight(double newWeight) { this.weight = newWeight; }
    
    public double getWeight() { return this.weight; }
    
    public Buff(int x, int y, int width, int height, Color color)
    {
        super(x, y, width, height, color);
    }
}

