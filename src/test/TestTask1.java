package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import math.MathElement;
import math.MathList;

import org.junit.Before;
import org.junit.Test;

import core.IListElement;

/**
 * Tests for task 1.
 * 
 * @author Benjamin Haettasch
 * @since 27/03/13
 * @version 1.0
 */
public class TestTask1
{
	MathList ml;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		//Create a new empty MathList before each test
		ml = new MathList();
	}


	/**
	 * Test MathList::addElement(...);
	 */
	@Test
	public void testAddElement()
	{
		MathElement me1 = new MathElement("0");
		MathElement me2 = new MathElement("1");
		MathElement me3 = new MathElement("");
		
		ml.addElement(me1);
		assertEquals("Wrong number of elements in list", 1, ml.size());
		assertSame("Wrong element in list (first position)", me1, ml.first());
		assertSame("Wrong element in list (position: 0)", me1, ml.get(0));
		
		ml.addElement(me2);
		assertEquals("Wrong number of elements in list", 2, ml.size());
		assertSame("Wrong element in list (first position, probably overwritten)", me1, ml.first());
		assertSame("Wrong element in list (position: 1)", me2, ml.get(1));
		
		ml.addElement(me3);
		assertEquals("Wrong number of elements in list", 3, ml.size());
		assertSame("Wrong element in list (first position, probably overwritten)", me1, ml.first());
		assertSame("Wrong element in list (position: 1)", me2, ml.get(1));
		assertSame("Wrong element in list (position: 2)", me3, ml.get(2));
	}
	
	/**
	 * Test MathList::buildList();
	 */
	@Test
	public void testBuildList()
	{
		String empty = "";
		ml.buildList(empty);
		assertEquals("Wrong number of elements in list", 0, ml.size());
		
		String simple = "1234";
		ml.buildList(simple);
		assertEquals("Wrong number of elements in list", 4, ml.size());
		
		assertEquals("Wrong element in list (first position)", "1", ml.first().data());
		for(int i=0;i<4;i++)
		{
			assertEquals("Wrong element in list (position: "+i+")", Integer.toString(i+1), ml.get(i).data());
		}
	}
	
	/**
	 * Test MathList::assembleNumbers();7
	 * without floating numbers
	 */
	@Test
	public void testAssembleNumbersNormal()
	{
		//This test requires buildList() to be working
		String normal = "14+2-1765";
		ml.buildList(normal);
		checkListLength(9, ml);
		
		IListElement first = ml.first();
		IListElement third = first.next().next();
		IListElement fourth = third.next();
		IListElement fifth = fourth.next();

		ml.assembleNumbers();
		
		String[] match = {"14", "+", "2", "-", "1765"};
		assertMatch(match, ml);
		
		assertEquals("Do not create new elements but join", first, ml.first());
		assertEquals("Do not create new elements but join", third, ml.get(1));
		assertEquals("Do not create new elements but join", fourth, ml.get(2));
		assertEquals("Do not create new elements but join", fifth, ml.get(3));
	}
	
	/**
	 * Test MathList::assembleNumbers();7
	 * with floating numbers
	 */
	@Test
	public void testAssembleNumbersMixed()
	{
		//This test requires buildList() to be working
		String normal = "(14.5+2-1765*13.1/78)";
		ml.buildList(normal);
		checkListLength(21, ml);
		
		IListElement first = ml.first();
		IListElement second = first.next();
		IListElement sixth = second.next().next().next().next();

		ml.assembleNumbers();
		
		String[] match = {"(", "14.5", "+", "2", "-", "1765", "*", "13.1", "/", "78", ")"};
		assertMatch(match, ml);
		
		//Add some new elements...
		ml.addElement(new MathElement("+"));
		ml.addElement(new MathElement("3"));
		ml.addElement(new MathElement("3"));
		ml.addElement(new MathElement("."));
		ml.addElement(new MathElement("7"));
		ml.addElement(new MathElement(")"));	
		
		//...and call again (two times - the second one should not have any effect)
		ml.assembleNumbers();
		ml.assembleNumbers();
		
		String[] match2 = {"(", "14.5", "+", "2", "-", "1765", "*", "13.1", "/", "78", ")", "+", "33.7", ")"};
		assertMatch(match2, ml);
		
		assertEquals("Do not create new elements but join", first, ml.first());
		assertEquals("Do not create new elements but join", second, ml.get(1));
		assertEquals("Do not create new elements but join", sixth, ml.get(2));
	}	
	
	
	
	/**
	 * Compares a MathList to a String Array
	 * 
	 * @param match String Array to match
	 * @param ml MathList to compare
	 */
	private static void assertMatch(String[] match, MathList ml)
	{
		assertEquals("Wrong number of elements in list,", match.length, ml.size());
		
		for(int i=0;i<ml.size();i++)
		{
			assertEquals("Wrong element in list (position: "+i+")", match[i], ml.get(i).data());			
		}
	}
	
	/**
	 * Check, whether a list has the right count of elements (and thus was correctly constructed by buildList() hopefully)
	 * 
	 * @param l Length the list should have
	 * @param ml List to check
	 */
	private static void checkListLength(int l, MathList ml)
	{
		assertEquals("List was not created correctly. Fix buildList() before continuing. Wrong element count,", l, ml.size());
	}
}
