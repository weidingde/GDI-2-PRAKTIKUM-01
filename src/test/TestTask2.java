/**
 * 
 */
package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import math.IntegerCalculator;
import math.MathElement;
import math.MathList;
import math.PointNumberCalculator;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import core.Calculator;
import core.IListElement;

/**
 * Tests for task 2.
 * 
 * @author Benjamin Haettasch
 * @since 27/03/13
 * @version 1.0
 */
public class TestTask2
{
	//UUT
	MathList ml;
	static Calculator ic;
	static Calculator pnc;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		ic = new IntegerCalculator();
		pnc = new PointNumberCalculator();
	}

	@Before
	public void setUp() throws Exception
	{
		//Create a new empty MathList before each test
		ml = new MathList();
	}

	
	@Test
	public void testIsAtom()
	{
		ml.addElement(new MathElement("15"));
		assertTrue("Not recognized that list is atom", ml.isAtom());
		
		ml.addElement(new MathElement("+"));
		ml.addElement(new MathElement("3"));
		assertFalse("List is not atom (but was falsely recognized atom)", ml.isAtom());
		
		ml = new MathList();
		ml.buildList("(7)");
		assertFalse("List is not atom (but was falsely recognized atom)", ml.isAtom());
		
		ml = new MathList();
		ml.addElement(new MathElement("-18"));
		assertTrue("Not recognized that list is atom", ml.isAtom());
	}
	
	@Test
	public void testFindMulOrDivOperation()
	{
		TestDataContainer[] td = {
				new TestDataContainer("17*4", "17", 0),
				new TestDataContainer("78/14", "78", 0),
				new TestDataContainer("14+78/14", "78", 2),
				new TestDataContainer("(14+48)+4*3", "4", 6),
				new TestDataContainer("7989+78412-9875+9+62", null, -1),
				new TestDataContainer("", null, -1)
			};
		
		runTest("findMulOrDivOperation", td);
	}
	
	@Test
	public void testFindAddOrSubOperation()
	{
		TestDataContainer[] td = {
				new TestDataContainer("17+4", "17", 0),
				new TestDataContainer("78-14", "78", 0),
				new TestDataContainer("14*78-14", "78", 2),
				new TestDataContainer("(14*48)/4-3", "4", 6),
				new TestDataContainer("7989*78412/9875*9*62", null, -1),
				new TestDataContainer("", null, -1)
			};
		
		runTest("findAddOrSubOperation", td);
	}
	
	private void runTest(String op, TestDataContainer[] td)
	{
		try
		{
			Method method = MathList.class.getMethod(op);
			
			for(int i=0;i<td.length;i++)
			{
				String tcn = "Testcase "+(i+1)+": ";
				
				ml = new MathList();
				ml.buildList(td[i].input);
				ml.assembleNumbers();
				
				IListElement r = (IListElement) method.invoke(ml);
				
				if(td[i].output == null)
					assertNull("Pointer should be null", r);
				else	
				{
					assertNotNull(tcn+"Pointer should not be null", r);
					assertEquals(tcn+"Wrong element returned", td[i].output, r.data());
					assertEquals(tcn+"Do not create new pointers", ml.get(td[i].position), r);
				}
					
			}
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
			fail("Method "+op+"() could not be found. Maybe you changed method signature?");
		}
		catch(InvocationTargetException e)
		{
			e.printStackTrace();
			fail("An exception was thrown in method  "+op+"(): " + e.getCause().toString());		
		}
		catch (SecurityException | IllegalAccessException | IllegalArgumentException e)
		{
			e.printStackTrace();
			fail("Method call was not successfull. Maybe you changed any of the given interfaces?");
		}		
	}
	
	@Test (timeout = 250)
	public void testEvaluateSimpleExpression()
	{
		ml.buildList("15*3");
		ml.assembleNumbers();
		ml.evaluateSimpleExpression(ml.first());
		
		assertEquals("Not fully simplified", 1, ml.size());
		assertEquals("Wrong result", "45", ml.first().data());
		
		ml = new MathList();
		ml.buildList("(7+(15*3)-9)");
		ml.assembleNumbers();
		ml.evaluateSimpleExpression(ml.get(3));
		
		assertEquals("Not fully simplified", 7, ml.size());
		assertEquals("Wrong resulting list", "(|7|+|45|-|9|)", ml.toString());
	}
	
	@Test
	public void testFindInnermostExpression()
	{
		ml.buildList("15*3");
		ml.assembleNumbers();
		IListElement result = ml.findInnermostExpression();
		
		assertEquals("Wrong element (data)", "15", result.data());
		assertEquals("Wrong element (pointer)", ml.get(0), result);
		
		ml = new MathList();
		ml.buildList("(15+3*(93/18)-(8*(16-4)+2))");
		ml.assembleNumbers();
		result = ml.findInnermostExpression();
		
		assertEquals("Wrong element (should be parentheses)", "(", result.data());
		assertEquals("Wrong element (next)", "93", result.next().data());
		assertEquals("Wrong element (pointer)", ml.get(5), result);
	}
	
	@Test (timeout=750)
	public void testEvaluate()
	{
		String[] testCases = {
				"3+5*6-5/10*100+2",
				"3.5+5.1/17-4.7+4-2354*7/7.0-1/10",
				"(33+43)*434-45/10+((10-52)*(6-100)*(0-1)+(5*(5*(5*(((5)))))))",
				"42.0*24+(12686/6343*123.232-23+(43*4.0-(0-3)-(3/12.0*(6.9))))",
				"8923747823478892340892304+47823748927394789*2347283479233434343434344/253728457832423423424-12312312312313123123123",
				"(8923747823478892340892304+47823748927394789*2347283479233434343434344/2347283479233434343434344-12312312312313123123123)*2.3"
			};
		
		Calculator[] calculators = {
				ic, pnc, ic, pnc, ic, pnc
			};
		
		String[] results = {
				"35", "-2351.0", "29657", "1404.739", "8911877936492974499711696", "20496301785677754733877131.0"
			};
		
		for(int i=0;i<testCases.length;i++)
		{
			String tcn = "Testcase "+(i+1)+": ";
			
			ml = new MathList(calculators[i]);
			ml.buildList(testCases[i]);
			ml.evaluate();
			
			assertEquals(tcn+"Evaluation did not finish", 1, ml.size());
			assertEquals(tcn+"Wrong result", results[i], ml.first().data());
		}
	}

	@Test
	public void testIntegerCalculatorAdd()
	{
		calculatorTest(ic, "add", "578997412357+3877425744", "582874838101");
	
		calculatorTest(ic, "add", "0+578997412357", "578997412357");
	
	}
	
	@Test
	public void testPointNumberCalculatorAdd()
	{
		calculatorTest(pnc, "add", "578997.412357+38.77425744", "579036.18661444");
	
		calculatorTest(pnc, "add", "0+578997412357", "578997412357");
		
		calculatorTest(pnc, "add", "0+5.78997412357", "5.78997412357");
		
		calculatorTest(pnc, "add", "4+0.4", "4.4");
		
		calculatorTest(pnc, "add", "1+0.00000000000000000004", "1.00000000000000000004");
	
	}
	
	@Test
	public void testIntegerCalculatorSub()
	{
		calculatorTest(ic, "sub", "578997412357-3877425744", "575119986613");
	
		calculatorTest(ic, "sub", "785-3877425744", "-3877424959");	
		
		calculatorTest(ic, "sub", "425744-425744", "0");	
	}	
	
	@Test
	public void testPointNumberCalculatorSub()
	{
		calculatorTest(pnc, "sub", "578997.412357-38.77425744", "578958.63809956");
	
		calculatorTest(pnc, "sub", "785-387742574.4", "-387741789.4");	
		
		calculatorTest(pnc, "sub", "425.744-425.744", "0.000");	
		
		calculatorTest(pnc, "sub", "1.0-0.00000000000000000000000000000006", "0.99999999999999999999999999999994");	
	}	
	
	@Test
	public void testIntegerCalculatorMul()
	{
		calculatorTest(ic, "mul", "578997*3877424", "2245016863728");
	
		calculatorTest(ic, "mul", "785*0", "0");
	}
	
	@Test
	public void testPointNumberCalculatorMul()
	{
		calculatorTest(pnc, "mul", "578997*3877424", "2245016863728");
		
		calculatorTest(pnc, "mul", "578.997*38.77424", "22450.16863728");
	
		calculatorTest(pnc, "mul", "78.9655*0", "0.0000");
	}
	
	@Test
	public void testIntegerCalculatorDiv()
	{
		calculatorTest(ic, "div", "32/16", "2");
	
		calculatorTest(ic, "div", "12/8", "1");
		
		calculatorTest(ic, "div", "15/8", "1");
	}	
	
	@Test
	public void testPointNumberCalculatorDiv()
	{
		calculatorTest(pnc, "div", "32/16", "2");
	
		calculatorTest(pnc, "div", "12/8", "1.5");
		
		calculatorTest(pnc, "div", "15/8", "1.875");
		
		calculatorTest(pnc, "div", "16.00/8", "2.00");
	}	
	
	
	private void calculatorTest(Calculator cl, String op, String expr, String result)
	{
		ml = new MathList(cl);
		ml.buildList(expr);
		ml.assembleNumbers();
		
		MathElement first = ml.first();
		
		try
		{
			Method method = Calculator.class.getMethod(op, IListElement.class);
			method.invoke(cl, first);

		}
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			e.printStackTrace();
			fail("Method call caused internal error!");
		}
		
		assertEquals("Did not remove unecessary elements", 1, ml.size());
		assertEquals("Wrong result", result, ml.first().data());
		assertEquals("Wrong element overridden", first, ml.first());
	}
}
