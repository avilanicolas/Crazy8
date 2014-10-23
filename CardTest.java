 



/**
 * @author  Nicolas Avila
 * @version 1.0
 */
public class CardTest extends junit.framework.TestCase
{
    /**
     * Default constructor for test class CardTest
     */
    public CardTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    protected void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    protected void tearDown()
    {
    }
    
    

	public void testCardTest1()
	{
		Crazy8.Card card1 = new Crazy8.Card(14, "club");
		System.out.println(card1);
		Crazy8.Card card2 = new Crazy8.Card(10, "club");
		System.out.println(card2);
		Crazy8.Card card3 = new Crazy8.Card(2, "diamond");
		System.out.println(card3);
		assertEquals(1, card1.compareTo(card2));
		assertEquals(-1, card1.compareTo(card3));
	}
}

