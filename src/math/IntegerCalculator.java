package math;

import java.math.BigInteger;

import core.Calculator;
import core.IListElement;

/**
 * Class for computing primitive operations with numbers.
 * @author Benjamin Haettasch, Fabian Wagner
 * @since 27/03/13
 * @version 2.0
 */
public class IntegerCalculator extends Calculator {

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(IListElement leftOperand) throws IllegalArgumentException {
		super.add(leftOperand);
		
		BigInteger operand1 = new BigInteger(leftOperand.data()); // change string to biginteger
		BigInteger operand2 = new BigInteger(leftOperand.next().next().data());
		BigInteger result = operand1.add(operand2); // count the result of two integers
		leftOperand.setData(result.toString()); // set the value to leftoperand
		leftOperand.setNext(leftOperand.next().next().next()); //set the next value
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sub(IListElement leftOperand) throws IllegalArgumentException {
		super.sub(leftOperand);
		
		BigInteger operand1 = new BigInteger(leftOperand.data()); // change string to biginteger
		BigInteger operand2 = new BigInteger(leftOperand.next().next().data());
		BigInteger result = operand1.subtract(operand2); // count the result of two integers
		leftOperand.setData(result.toString()); // set the value to leftoperand
		leftOperand.setNext(leftOperand.next().next().next()); //set the next value
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mul(IListElement leftOperand) throws IllegalArgumentException {
		super.mul(leftOperand);
		
		BigInteger operand1 = new BigInteger(leftOperand.data()); // change string to biginteger
		BigInteger operand2 = new BigInteger(leftOperand.next().next().data());
		BigInteger result = operand1.multiply(operand2); // count the result of two integers
		leftOperand.setData(result.toString()); // set the value to leftoperand
		leftOperand.setNext(leftOperand.next().next().next()); //set the next value
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void div(IListElement leftOperand) throws IllegalArgumentException {
		super.div(leftOperand);
		
		BigInteger operand1 = new BigInteger(leftOperand.data()); // change string to biginteger
		BigInteger operand2 = new BigInteger(leftOperand.next().next().data());
		BigInteger result = operand1.divide(operand2); // count the result of two integers
		leftOperand.setData(result.toString()); // set the value to leftoperand
		leftOperand.setNext(leftOperand.next().next().next()); //set the next value
	}
}
