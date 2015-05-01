package core;

/**
 * Class executes primitive mathematical operations.
 * @author Benjamin Haettasch, Fabian Wagner
 * @since 27/03/13
 * @version 2.0
 */
public abstract class Calculator {

	/**
	 * This sign is describing an addition.
	 */
	public final static String SIGN_ADD = "+";
	
	/**
	 * This sign is describing a subtraction.
	 */
	public final static String SIGN_SUB = "-";
	
	/**
	 * This sign is describing a multiplication.
	 */
	public final static String SIGN_MUL = "*";
	
	/**
	 * This sign is describing a division.
	 */
	public final static String SIGN_DIV = "/";
	
	/**
	 * This sign is describing a swap.
	 */
	public final static String SIGN_SWAP= "#";	
	
	/**
	 * This sign is describing a beginning of a function definition.
	 */
	public final static String SIGN_BEGIN_FUNCTION_DEFINITION = ":";
	
	/**
	 * This sign is describing an opening clip.
	 */
	public final static String SIGN_PARENTHESIS_OPEN = "(";	
	
	/**
	 * This sign is describing a closing clip.
	 */
	public final static String SIGN_PARENTHESIS_CLOSE = ")";
	

	
	/**
	 * Method is doing an addition given by an IListElement. While computing the result the method is accessing
	 * the next and the after next IListElement.<br>
	 * <b>Short</b> description of the method:<br>
	 * <i>IListElement</i>: ->leftOperand->operator->rightOperand-> => <i>IListElement</i>: ->result-> <br>
	 * with: result=leftOperand+rightOperand
	 * 
	 * @param leftOperand describes the left operand of the addition and maintains the pointer for the operator 
	 * (the operator has to maintain the pointer of the right operand)
	 * 
	 * @throws IllegalArgumentException throws an IllegalArgumentException if the left operand or right operand is no number OR the operand sign is no SIGN_ADD
	 * @see SIGN_ADD
	 * @see IListElement
	 */
	public void add(IListElement leftOperand) throws IllegalArgumentException
	{
		checkArguments(SIGN_ADD, leftOperand, "add");	
	}
	
	/**
	 * Method is doing a subtraction given by an IListElement. While computing the result the method is accessing
	 * the next and the after next IListElement.<br>
	 * <b>Short</b> description of the method:<br>
	 * <i>IListElement</i>: ->leftOperand->operator->rightOperand-> => <i>IListElement</i>: ->result-> <br>
	 * with: result=leftOperand-rightOperand
	 * 
	 * @param leftOperand
	 * 			describes the left operand of the subtraction and maintains the pointer for the operator 
	 * (the operator has to maintain the pointer of the right operand)
	 * 
	 * @throws IllegalArgumentException
	 * 			throws an IllegalArgumentException if the left operand or right operand is no number
	 * 			OR the operand sign is no SIGN_SUB
	 * @see SIGN_SUB
	 * @see IListElement
	 */
	public void sub(IListElement leftOperand) throws IllegalArgumentException
	{
		checkArguments(SIGN_SUB, leftOperand, "sub");	
	}
	
	/**
	 * Method is doing a multiplication given by an IListElement. While computing the result the method is accessing
	 * the next and the after next IListElement.<br>
	 * <b>Short</b> description of the method:<br>
	 * <i>IListElement</i>: ->leftOperand->operator->rightOperand-> => <i>IListElement</i>: ->result-> <br>
	 * with: result=leftOperand*rightOperand
	 * 
	 * @param leftOperand
	 * 			describes the left operand of the multiplication and maintains the pointer for the operator 
	 * (the operator has to maintain the pointer of the right operand)
	 * 
	 * @throws IllegalArgumentException
	 * 			throws an IllegalArgumentException if the left operand or right operand is no number
	 * 			OR the operand sign is no SIGN_MUL
	 * @see SIGN_MUL
	 * @see IListElement
	 */
	public void mul(IListElement leftOperand) throws IllegalArgumentException
	{
		checkArguments(SIGN_MUL, leftOperand, "mul");	
	}
	
	/**
	 * Method is doing a division given by an IListElement. While computing the result the method is accessing
	 * the next and the after next IListElement.<br>
	 * <b>Short</b> description of the method:<br>
	 * <i>IListElement</i>: ->leftOperand->operator->rightOperand-> => <i>IListElement</i>: ->result-> <br>
	 * with: result=leftOperand/rightOperand
	 * 
	 * @param leftOperand
	 * 			describes the left operand of the division and maintains the pointer for the operator 
	 * (the operator has to maintain the pointer of the right operand)
	 * 
	 * @throws IllegalArgumentException
	 * 			throws an IllegalArgumentException if the left operand or right operand is no number
	 * 			OR the operand sign is no SIGN_DIV
	 * @see SIGN_DIV
	 * @see IListElement
	 */
	public void div(IListElement leftOperand) throws IllegalArgumentException
	{
		checkArguments(SIGN_DIV, leftOperand, "div");
	}

	
	/**
	 * Method is doing a swap operation given by an IListElement. While doing the swap the method is accessing
	 * the next and the after next IListElement.<br>
	 * <b>Short</b> description of the method:<br>
	 * <i>IListElement</i>: ->leftOperand->#->operator->rightOperand-> => <i>rightOperand->operator->leftOperand</i>: ->result-> <br>
	 * 
	 * @param leftOperand
	 * 			describes the left operand of the swap operation and maintains the pointer for the operator 
	 * (the operator has to maintain the pointer of operation and so on)
	 * 
	 * @throws IllegalArgumentException
	 * 			throws an IllegalArgumentException if the left operand or right operand is no number
	 * 			OR the operand sign is no SIGN_SWAP
	 * @see SIGN_SWAP
	 * @see IListElement
	 */
	public void swap(IListElement leftOperand) throws IllegalArgumentException {
		checkArguments(SIGN_SWAP, leftOperand, "swap");
		
		// TODO implement this method in task 3.
	}
	
	
	/**
	 * Checks if the arguments given are correct and throws an exception if not
	 * 
	 * @param opSymbol Operation symbol that should be in the expression
	 * @param leftOperand Pointer to the left operand
	 * 
	 * @throws IllegalArgumentException if the pointers are not correct or the expression does not contain the right operation symbol
	 */
	protected void checkArguments(String opSymbol, IListElement leftOperand, String opName) throws IllegalArgumentException
	{
		if(leftOperand == null || !leftOperand.hasNext() || !leftOperand.next().hasNext())
			throw new IllegalArgumentException("Syntax Error");
		
		if(!leftOperand.next().data().equals(opSymbol))
			throw new IllegalArgumentException("Wrong operator in "+opName+" operation.");
	}	
}
