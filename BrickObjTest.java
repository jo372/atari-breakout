

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javafx.scene.paint.Color;
/**
 * The test class BrickObjTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class BrickObjTest
{
    BrickObj b;
    /**
     * Default constructor for test class BrickObjTest
     */
    public BrickObjTest()
    {
    }
    
    @Test 
    public void testRandomChanceWithinRange() {
        double chance = b.getRandomChance();
        assertTrue((chance >= 0 && chance <= 100));
        assertEquals(chance, b.getRandomChance(), 0.001);
    }
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        b = new BrickObj(0, 0, 40, 40, Color.RED);
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
