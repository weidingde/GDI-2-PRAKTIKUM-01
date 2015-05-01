package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import math.MathElement;
import math.MathList;
import math.PointNumberCalculator;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import core.Calculator;

/**
 * Tests for task 4.
 * 
 * @author Benjamin Haettasch
 * @since 27/03/13
 * @version 1.0
 */
public class TestTask4
{
	MathList ml;
	static Calculator pnc;
	
	static MathList functions;
	
	static String exp1 = "(10+((30-(40.5))))*doubledif(54.3,85.3)+square(2)-doubledif(3,4)";
	static String exp2 = "(square(100)*21+((doubledif(100000,100000))))";

	static String fun1 = "x#*x";
	static String fun2 = "@parameter1@*2-2*@parameter2@";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		pnc = new PointNumberCalculator();
		
		functions = new MathList();
		functions.addElement(new MathElement("square"));
		functions.addElement(new MathElement("x"));
		functions.addElement(new MathElement(":"));
		functions.addElement(new MathElement(fun1));


		functions.addElement(new MathElement("doubledif"));
		functions.addElement(new MathElement("@parameter1@"));
		functions.addElement(new MathElement("@parameter2@"));
		functions.addElement(new MathElement(":"));
		functions.addElement(new MathElement(fun2));
	}

	@Before
	public void setUp() throws Exception
	{
		ml = new MathList(pnc);
	}

	@Test
	public void testAssembleFunctionNames1()
	{
		MathList list = new MathList();
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("10"));
		list.addElement(new MathElement("+"));
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("30"));
		list.addElement(new MathElement("-"));
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("40.5"));
		list.addElement(new MathElement(")"));
		list.addElement(new MathElement(")"));
		list.addElement(new MathElement(")"));
		list.addElement(new MathElement(")"));
		list.addElement(new MathElement("*"));
		list.addElement(new MathElement("doubledif"));
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("54.3"));
		list.addElement(new MathElement(","));
		list.addElement(new MathElement("85.3"));
		list.addElement(new MathElement(")"));
		list.addElement(new MathElement("+"));
		list.addElement(new MathElement("square"));
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("2"));
		list.addElement(new MathElement(")"));
		list.addElement(new MathElement("-"));
		list.addElement(new MathElement("doubledif"));
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("3"));
		list.addElement(new MathElement(","));
		list.addElement(new MathElement("4"));
		list.addElement(new MathElement(")"));
		
		runAssembleTest(exp1, 32, list);	
	}
	
	@Test
	public void testAssembleFunctionNames2()
	{
		MathList list = new MathList();
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("square"));
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("100"));
		list.addElement(new MathElement(")"));
		list.addElement(new MathElement("*"));
		list.addElement(new MathElement("21"));
		list.addElement(new MathElement("+"));
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("doubledif"));
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("100000"));
		list.addElement(new MathElement(","));
		list.addElement(new MathElement("100000"));
		list.addElement(new MathElement(")"));
		list.addElement(new MathElement(")"));
		list.addElement(new MathElement(")"));
		list.addElement(new MathElement(")"));
		
		runAssembleTest(exp2, 19, list);	
	}	
	
	
	private void runAssembleTest(String testCase, int size, MathList result)
	{
		ml = new MathList(pnc);
		ml.buildList(testCase);
		ml.assembleNumbers();
		ml.assembleFunctionNames();
		
		assertEquals("Wrong size", size, ml.size());
		assertEquals("Wrong elements", result, ml);
	}
	
	@Test
	public void testInterpretFunctionDefinitions()
	{
		HashMap<String, String> fmap = ml.interpretFunctionDefinitions(functions);
		
		assertTrue("Key missing (first function)", fmap.containsKey("square"));
		assertTrue("Key missing (second function)", fmap.containsKey("doubledif"));
		
		assertEquals("Wrong data (first function)", "x|x#*x", fmap.get("square")); 
		assertEquals("Wrong data (second function)", "@parameter1@|@parameter2@|@parameter1@*2-2*@parameter2@", fmap.get("doubledif")); 
	}

	@Test
	public void testInlineFunctions1()
	{
		MathList list = new MathList();
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("10"));
		list.addElement(new MathElement("+"));
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("30"));
		list.addElement(new MathElement("-"));
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("40.5"));
		list.addElement(new MathElement(")"));
		list.addElement(new MathElement(")"));
		list.addElement(new MathElement(")"));
		list.addElement(new MathElement(")"));
		list.addElement(new MathElement("*"));
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("54.3"));
		list.addElement(new MathElement("*"));
		list.addElement(new MathElement("2"));
		list.addElement(new MathElement("-"));
		list.addElement(new MathElement("2"));
		list.addElement(new MathElement("*"));
		list.addElement(new MathElement("85.3"));
		list.addElement(new MathElement(")"));
		list.addElement(new MathElement("+"));
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("2"));
		list.addElement(new MathElement("#"));
		list.addElement(new MathElement("*"));
		list.addElement(new MathElement("2"));
		list.addElement(new MathElement(")"));
		list.addElement(new MathElement("-"));
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("3"));
		list.addElement(new MathElement("*"));
		list.addElement(new MathElement("2"));
		list.addElement(new MathElement("-"));
		list.addElement(new MathElement("2"));
		list.addElement(new MathElement("*"));
		list.addElement(new MathElement("4"));
		list.addElement(new MathElement(")"));
		
		assertEquals("Wrong size. Maybe addElement() is not working correctly?", 40, list.size());
		
		runInlineFunctionsTest(list, exp1);
	}
	
	@Test
	public void testInlineFunctions2()
	{
		MathList list = new MathList();
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("100"));
		list.addElement(new MathElement("#"));
		list.addElement(new MathElement("*"));
		list.addElement(new MathElement("100"));
		list.addElement(new MathElement(")"));
		list.addElement(new MathElement("*"));
		list.addElement(new MathElement("21"));
		list.addElement(new MathElement("+"));
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("("));
		list.addElement(new MathElement("100000"));
		list.addElement(new MathElement("*"));
		list.addElement(new MathElement("2"));
		list.addElement(new MathElement("-"));
		list.addElement(new MathElement("2"));
		list.addElement(new MathElement("*"));
		list.addElement(new MathElement("100000"));
		list.addElement(new MathElement(")"));
		list.addElement(new MathElement(")"));
		list.addElement(new MathElement(")"));
		list.addElement(new MathElement(")"));
		
		assertEquals("Wrong size. Maybe addElement() is not working correctly?", 24, list.size());

		
		runInlineFunctionsTest(list, exp2);
	}
	
	private void runInlineFunctionsTest(MathList result, String testCase)
	{
		ml.buildList(testCase);
		
		assertEquals("Wrong size. Maybe buildList() is not working correctly?", testCase.length(), ml.size());
		
		ml.assembleNumbers();
		ml.assembleFunctionNames();
		
		ml.inlineFunctions(functions);
		
		assertEquals("Wrong list", result, ml);
	}
	
	@Test(timeout=500)
	public void testEvaluate1()
	{
		runEvaluateTest(exp1, "37.00");
	}
	
	@Test(timeout=500)
	public void testEvaluate2()
	{
		runEvaluateTest(exp2, "210000");
	}
	
	private void runEvaluateTest(String testCase, String result)
	{
		ml.buildList(testCase);
		ml.evaluate(functions);
		
		assertEquals("Not fully simplified", 1, ml.size());
		assertEquals("Wrong result", result, ml.first().data());		
	}
}
