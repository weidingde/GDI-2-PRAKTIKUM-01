package test;

import static org.junit.Assert.*;

import math.MathList;
import math.PointNumberCalculator;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import core.Calculator;

/**
 * Tests for task 3.
 * 
 * @author Benjamin Haettasch
 * @since 27/03/13
 * @version 1.0
 */
public class TestTask3
{
	MathList ml;
	static Calculator pnc;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		pnc = new PointNumberCalculator();
	}

	@Before
	public void setUp() throws Exception
	{
		ml = new MathList(pnc);
	}
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testSwap()
	{
		ml.buildList("55#+3.2");
		ml.assembleNumbers();
		pnc.swap(ml.first());
		assertEquals("Wrong list size", 3, ml.size());
		assertEquals("Wrong list", "3.2|+|55", ml.toString());
		
		ml = new MathList();
		ml.buildList("14+3");
		ml.assembleNumbers();
		
		exception.expect(IllegalArgumentException.class);
		pnc.swap(ml.first());
	}
	
	@Test(timeout=250)
	public void testEvaluateWithSwap()
	{
		String testcase = "(43#-44)#/(8-5)#*6#/5";
		ml.buildList(testcase);
		ml.evaluate();
		assertEquals("Wrong size", 1, ml.size());
		assertEquals("Wrong result", "2.5", ml.first().data());
	}

}
