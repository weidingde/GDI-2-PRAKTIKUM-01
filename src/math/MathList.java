package math;

import java.lang.annotation.Inherited;
import java.util.HashMap;

import core.Calculator;
import core.IListElement;
import core.LinkedList;

/**
 * Class for storing and evaluating mathematical expresions
 * 
 * @author Benjamin Haettasch, Fabian Wagner
 * @since 27/03/13
 * @version 2.0
 */
public class MathList extends LinkedList<MathElement> {

	private Calculator calc = null;

	/**
	 * Creates a new empty MathList. The Calculator will be set to an
	 * IntegerCalculator.
	 * 
	 * @see IntegerCalculator
	 * @see LinkedList
	 */
	public MathList() {
		this.calc = new IntegerCalculator();
	}

	/**
	 * Creates a new MathList with a given the first element. The Calculator
	 * will be set to an IntegerCalculator.
	 * 
	 * @param first
	 *            describes the first element of the MathList
	 * 
	 * @see IntegerCalculator
	 * @see LinkedList
	 * @see MathElement
	 */
	public MathList(MathElement first) {
		this.calc = new IntegerCalculator();
		this.first = first;
		this.last = first;
	}

	/**
	 * Creates a new MathList with a given matching Calculator
	 * 
	 * @param calculator
	 *            describes the calculator which is needed
	 * @see Calculator
	 * @see LinkedList
	 * @see MathElement
	 */
	public MathList(Calculator calculator) {
		this.calc = calculator;
	}

	/**
	 * Creates a new MathList with a given first element and a given Calculator
	 * 
	 * @param first
	 *            describes the first element of the MathList
	 * @param calculator
	 *            describes the calculator which is needed
	 * @see Calculator
	 * @see LinkedList
	 * @see MathElement
	 */
	public MathList(MathElement first, Calculator calculator) {
		this.calc = calculator;
		this.first = first;
		this.last = first;
	}

	/**
	 * {@link Inherited}
	 */
	@Override
	public void addElement(MathElement mathElement) {
		if (this.first == null) // if the list is empty
			this.first = mathElement;

		else // if the list is not empty, make pivot locate at the end of list
		{
			IListElement pivot = this.first;
			while (pivot.next() != null) {
				pivot = pivot.next();
			}
			pivot.setNext(mathElement); // Adds an element to the end of the list
		}
		
		this.last = mathElement; // reset the pointers
	}

	/**
	 * Builds a <tt>MathList</tt> by a given string. Each character will be
	 * added as one element in the list.
	 * 
	 * @param term
	 *            represents a mathematical term as a string
	 */
	public void buildList(String term) {
		char[] termArray = term.toCharArray(); //first change string to array of chars
		//then change each char to string and add to list as mathElement
		for(char termArrayElement : termArray){ 
			MathElement mathElement = new MathElement (String.valueOf(termArrayElement));
			this.addElement(mathElement);
		}
	}

	/**
	 * Assembles all parts of a number (digits and .) to a number Afterwards all
	 * numbers will be joined, each one in a MathElement
	 */
	public void assembleNumbers() {

		if(this.first != null) {
			IListElement pivot = this.first;
			
			while(pivot.hasNext()){
				// use regex to decide whether the data is number or point, 
				if(pivot.data().matches("[0-9\\.]+") && pivot.next().data().matches("[0-9\\.]")){
					// if this data and next data are all numbers than can be assembled
					pivot.setData(pivot.data() + pivot.next().data()); //connect two string
					pivot.setNext(pivot.next().next()); //delete the next data
				}
				else{ //else point to the next and check next
					pivot = pivot.next();
				}
			}
			this.last = (MathElement) pivot; //reset the pointers
		}
	}

	/**
	 * Extracts all function names in the list.
	 * 
	 * @see Calculator
	 */
	public void assembleFunctionNames() {
		// TODO implement this in task 3
	}

	/**
	 * Tells if the MathList holds only a simple atom (point-)number
	 * 
	 * @return true if the MathList is atom otherwise false.
	 * @see Calculator
	 */
	public boolean isAtom() {
		boolean flag = false;
		if(this.first.data().matches("[-]{0,1}[0-9//.]+") && !this.first.hasNext()){
			// if the data is number or - and there is no next data
			flag = true;
		}
		return flag;
	}

	/**
	 * Returns a pointer to the left operand of a multiplication or a division.
	 * This method does not stop if a parentheses is found.
	 * 
	 * @return a pointer to a left operand
	 */
	public IListElement findMulOrDivOperation() {
		IListElement leftOperand = this.first;
		IListElement flag = null; //set a flag to return
		while(leftOperand != null && leftOperand.hasNext()){
			if((leftOperand.next().data().equals(Calculator.SIGN_MUL) || 
					leftOperand.next().data().equals(Calculator.SIGN_DIV))
					&& leftOperand.data().matches("[-]{0,1}[0-9\\.]+")){
				//if the data is number and Mul or Div are found in the next position
				flag = leftOperand; //set flag to the position
				break; //find Mul or Div, break
			}
			else {
				leftOperand = leftOperand.next();
			}
		}
		return flag;
	}

	/**
	 * creat my method to run the findMulOrDivOperation in interval
	 * @param begin: interval begin
	 * @param end: interval end
	 * @return
	 */
	public IListElement myFindMulOrDivOperation(IListElement begin, IListElement end) {
		IListElement leftOperand = begin;
		IListElement flag = null;
		while(leftOperand != null && leftOperand.hasNext() && leftOperand != end){ 
			//add an condition for stop search if the interval is end
			if(leftOperand.data().matches("[-]{0,1}[0-9\\.]+") &&
					(leftOperand.next().data().equals(Calculator.SIGN_MUL) || 
					leftOperand.next().data().equals(Calculator.SIGN_DIV))){
				flag = leftOperand;
				break;
			}
			else {
				leftOperand = leftOperand.next();
			}
		}
		return flag;
	}
	
	/**
	 * creat my method to run the findAddOrSubOperation in interval
	 * @param begin: interval begin
	 * @param end: interval end
	 * @return
	 */
	public IListElement myFindAddOrSubOperation(IListElement begin, IListElement end) {
		IListElement leftOperand = begin;
		IListElement flag = null;
		while(leftOperand != null && leftOperand.hasNext() && leftOperand != end){
			if(leftOperand.data().matches("[-]{0,1}[0-9\\.]+") &&
					(leftOperand.next().data().equals(Calculator.SIGN_ADD) || 
					leftOperand.next().data().equals(Calculator.SIGN_SUB))){
				flag = leftOperand;
				break;
			}
			else {
				leftOperand = leftOperand.next();
			}
		}
		return flag;
	}
	
	/**
	 * Returns a pointer to the left operand of an addition or a subtraction.
	 * This method does not stop if a parentheses is found.
	 * 
	 * @return a pointer to a left operand
	 */
	public IListElement findAddOrSubOperation() {
		IListElement leftOperand = this.first;
		IListElement flag = null;
		while(leftOperand != null && leftOperand.hasNext()){
			if(leftOperand.data().matches("[-]{0,1}[0-9\\.]+") &&
					(leftOperand.next().data().equals(Calculator.SIGN_ADD) || 
					leftOperand.next().data().equals(Calculator.SIGN_SUB))){
				//if the data is number and ADD or SUB are found in the next position
				flag = leftOperand;
				break;
			}
			else {
				leftOperand = leftOperand.next();
			}
		}
		return flag;
	}

	/**
	 * Returns a pointer to the beginning of the first innermost expression of
	 * the entire expression.
	 * 
	 * @return the beginning of the first innermost expression.
	 */
	public IListElement findInnermostExpression() {
		
		IListElement begin = null;
		
		if (this.first != null){
			begin = this.first; // to store the position of the innerst parenthesis_open
			IListElement pivot = this.first;
			
			while (!pivot.data().equals(Calculator.SIGN_PARENTHESIS_CLOSE) 
					&& pivot.hasNext()){// end if meet the first parenthesis_close or list is empty
				if (pivot.data().equals(Calculator.SIGN_PARENTHESIS_OPEN)) {
					begin = pivot;
				}
				pivot = pivot.next();
			}
		}
		
		return begin;
	}

	/**
	 * Evaluates a simple expression.
	 * 
	 * @param begin
	 *            pointer to the beginning of the simple expression. The method
	 *            expects an opening parenthesis if there are any parentheses in
	 *            the expression. Otherwise the begin must be the first left
	 *            operand of the expression.
	 */
	public void evaluateSimpleExpression(IListElement begin) {
		IListElement end = null; //set end for interval end
		if (begin.data().equals(Calculator.SIGN_PARENTHESIS_OPEN)) {
			//if the interval begin is a parenthesis_open, find the position of parenthesis_close for end
			end = begin;
			while(!end.data().equals(Calculator.SIGN_PARENTHESIS_CLOSE)){
				end = end.next();
			}
		}
		
		IListElement leftOperand = begin;
		//first calculate multiple or division
		while (this.myFindMulOrDivOperation(begin, end) != null){
			leftOperand = this.myFindMulOrDivOperation(begin, end);
			if (leftOperand.next().data().equals(Calculator.SIGN_MUL)){
				this.calc.mul(leftOperand);
			}
			else {
				this.calc.div(leftOperand);
			}
		}
		//then calculate addition or subtraction
		while (this.myFindAddOrSubOperation(begin, end) != null){
			leftOperand = this.myFindAddOrSubOperation(begin, end);
			if (leftOperand.next().data().equals(Calculator.SIGN_ADD)){
				this.calc.add(leftOperand);
			}
			else {
				this.calc.sub(leftOperand);
			}
		}
		
		if (end != null) { //delete rest and left only the result alone
			begin.setData(begin.next().data());
			begin.setNext(begin.next().next().next());
		}
	}

	/**
	 * Evaluates a math. expression without functions. The result is a MathList
	 * with one number inside. This element is the result of the expression.
	 */
	public void evaluate() {
		this.assembleNumbers(); //first, assemble the list
		while (!this.isAtom()) { 
			//if the list isn't atom, count the innermost express until list is atom
			this.evaluateSimpleExpression(this.findInnermostExpression());
		}
	}

	/**
	 * Evaluates a math. expression with functions. The result is a MathList
	 * with one number inside. This element is the result of the expression.
	 * 
	 * @param functions
	 *            A list of functions
	 * @see inlineFunctions
	 */
	public void evaluate(MathList functions) {
		// TODO implement this in task 4
	}

	/**
	 * Decomposes all functions by a given function definition. Defined
	 * functions must be a MathList with following pattern: <br>
	 * <b>function name 1</b>|parameter 1|...|parameter n|:|definition|
	 * <b>function name 2</b>|...<br>
	 * Definition describes an math. term with primitive operations and the
	 * parameters of the function.
	 * 
	 * @param functions
	 *            describes the definition of the functions.
	 */
	public void inlineFunctions(MathList functions) {
		// TODO implement this in task 4
	}

	/**
	 * Interprets the function definitions and returns a HashMap which is
	 * holding all functions.
	 * 
	 * @param functions
	 *            describes the given function definitions
	 * @return a HashMap which describes all functions with the function name as
	 *         key.
	 */
	public HashMap<String, String> interpretFunctionDefinitions(
			MathList functions) {
		// TODO implement this in task 4
		return null;
	}

	/**
	 * Returns the size of the MathList.
	 * 
	 * @return the size of the list.
	 */
	public int size() {
		IListElement pivot = this.first;
		int size = 0;
		while (pivot != null) {
			size++;
			pivot = pivot.next();
		}
		return size;
	}

	/**
	 * Tells if two MathList are equal.
	 */
	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof MathList))
			return false;
		MathList list = (MathList) obj;
		if (this.size() != list.size())
			return false;

		IListElement pivot = this.first;
		IListElement pivot2 = list.first;

		while (pivot != null) {
			if (!pivot.data().equals(pivot2.data()))
				return false;

			pivot = pivot.next();
			pivot2 = pivot2.next();
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		for (IListElement el = this.first(); el != null; el = el.next()) {
			hash += el.data().hashCode();
		}
		return hash;
	}
}
