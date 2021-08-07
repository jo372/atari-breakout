

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

/**
 * The test class BuffTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class BuffTest
{
    Buff buff;
    Image img;
    /**
     * Default constructor for test class BuffTest
     */
    public BuffTest()
    {
    }
   
    // testing the set image 
    @Test
    public void testsetImage() {
       Image newImage = new Image("file:resources/buffs/speed.png", 10, 10, false, false);
       buff.setImage(newImage);
       assertEquals(buff.getImage(), newImage);
    }
    
    // testing the get image function
    @Test 
    public void testgetImage() {
        assertEquals(buff.getImage(), img);
    }
    
    // testing the set type
    @Test
    public void testsetType() {
        int buffId = 0;
        buff.setType(buffId);
        assertEquals(buff.getType(), buffId);
    }
    
    
    @Test
    public void testgetType() {
        assertEquals(buff.getType(), 0);
    }
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        buff = new Buff(0, 10, 10, 10, Color.RED);
        img = new Image("file:resources/buffs/heart.png", 10, 10, false, false);
        buff.setImage(img);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        buff = null;
        img = null;
    }
}
