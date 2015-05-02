package math;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;

import core.Calculator;
import core.IListElement;

/**
 * Class for computing primitive operations with point numbers.
 * @author Benjamin Haettasch, Fabian Wagner
 * @since 27/03/13
 * @version 2.0
 */
public class PointNumberCalculator extends Calculator
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(IListElement leftOperand) throws IllegalArgumentException {
		super.add(leftOperand);
		
		BigDecimal operand1 = new BigDecimal(leftOperand.data()); // change string to bigdecimal
		BigDecimal operand2 = new BigDecimal (leftOperand.next().next().data());
		BigDecimal result = operand1.add(operand2); // count the result of two decimals
		leftOperand.setData(result.toString()); // set the value to leftoperand
		leftOperand.setNext(leftOperand.next().next().next()); //set the next value

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sub(IListElement leftOperand) throws IllegalArgumentException {
		super.sub(leftOperand);
		
		BigDecimal operand1 = new BigDecimal(leftOperand.data()); // change string to bigdecimal
		BigDecimal operand2 = new BigDecimal (leftOperand.next().next().data());
		BigDecimal result = operand1.subtract(operand2); // count the result of two decimals
		leftOperand.setData(result.toString()); // set the value to leftoperand
		leftOperand.setNext(leftOperand.next().next().next()); //set the next value
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mul(IListElement leftOperand) throws IllegalArgumentException {
		super.mul(leftOperand);
		
		BigDecimal operand1 = new BigDecimal(leftOperand.data()); // change string to bigdecimal
		BigDecimal operand2 = new BigDecimal (leftOperand.next().next().data());
		BigDecimal result = operand1.multiply(operand2); // count the result of two decimals
		leftOperand.setData(result.toString()); // set the value to leftoperand
		leftOperand.setNext(leftOperand.next().next().next()); //set the next value
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void div(IListElement leftOperand) throws IllegalArgumentException {
		super.div(leftOperand);
		
		BigDecimal operand1 = new BigDecimal(leftOperand.data()); // change string to bigdecimal
		BigDecimal operand2 = new BigDecimal (leftOperand.next().next().data());
		BigDecimal result = operand1.multiply(operand2); // count the result of two decimals
		leftOperand.setData(result.toString()); // set the value to leftoperand
		leftOperand.setNext(leftOperand.next().next().next()); //set the next value
	}
}
