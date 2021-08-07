

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javafx.scene.paint.Color;

/**
 * The test class BallObjTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class BallObjTest
{
    BallObj b;
    /**
     * Default constructor for test class BallObjTest
     */
    public BallObjTest()
    {
    }
    
    // changing the direction x
    @Test 
    public void testChangeDirectionX() {
        int tmp_dirX = b.getDirX();
        b.changeDirectionX();
        assertEquals(b.getDirX(), -tmp_dirX);
    }
    
    // changing the y direction
    @Test
    public void testChangeDirectionY() {
        int tmp_dirY = b.getDirY();
        b.changeDirectionY();
        assertEquals(b.getDirY(), -tmp_dirY);
    }
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        b = new BallObj(10, 10, 10, 10, Color.RED);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        b = null;
    }
}
