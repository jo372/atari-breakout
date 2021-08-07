

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javafx.scene.paint.Color;

/**
 * The test class GameObjTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class GameObjTest
{
    GameObj go;
    int x = 0,
        y = 0,
        w = 10,
        h = 10;
    Color c = Color.RED;
    /**
     * Default constructor for test class GameObjTest
     */
    public GameObjTest()
    {
    }
    
    @Test
    public void testConstructor() {
        assertEquals(go.getTopX(), x);
        assertEquals(go.getTopY(), y);
        assertEquals(go.getWidth(), w);
        assertEquals(go.getHeight(), h);
        assertEquals(go.getColor(), c);
    }
    
    @Test 
    public void testisVisible() {
        assertEquals(go.isVisible(), true);
    }
    
    
    @Test
    public void testsetVisible() {
        go.setVisible(false);
        assertEquals(go.isVisible(), false);
    }
    
    @Test
    public void testgetTopX() {
        assertEquals(go.getTopX(), x);
    }
    
    @Test
    public void testsetTopX() {
        int newTopX = 20;
        go.setTopX(newTopX);
        assertEquals(go.getTopX(), newTopX);
    }
    
    @Test
    public void testgetTopY() {
        assertEquals(go.getTopY(), y);
    }
    
    @Test
    public void testsetTopY() {
        int newTopY = 20;
        go.setTopY(newTopY);
        assertEquals(go.getTopY(), newTopY);
    }
    
    @Test
    public void testgetWidth() {
        assertEquals(go.getWidth(), w);
    }
    
    @Test
    public void testsetWidth() {
        int newWidth = 20;
        go.setWidth(20);
        assertEquals(go.getWidth(), newWidth);
    }
    
    @Test
    public void testgetHeight() {
        assertEquals(go.getHeight(), h);
    }
    
    @Test
    public void testsetHeight() {
        int newHeight = 20;
        go.setHeight(newHeight);
        assertEquals(go.getHeight(), newHeight);
    }
    
    @Test 
    public void testgetColor() {
        assertEquals(go.getColor(), c);
    }
    
    @Test
    public void testsetColor() {
        Color nc = Color.BLUE;
        go.setColor(nc);
        assertEquals(go.getColor(), nc);
    }
   
    @Test
    public void testgetDirX() {
        int newDirXVal = -1;
        go.setDirX(newDirXVal);
        assertEquals(go.getDirX(), newDirXVal);
    }

    @Test
    public void testgetDirY() {
        int newDirYVal = -1;
        go.setDirY(newDirYVal);
        assertEquals(go.getDirY(), newDirYVal);
    }
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        go = new GameObj(x, y, w, h, c);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        go = null;
    }
}
