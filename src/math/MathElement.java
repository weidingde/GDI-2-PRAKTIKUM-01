package math;

import core.IListElement;

/**
 * A MathElement maintains mathematical signs.
 * @author Fabian Wagner
 * @since 03/07/12
 * @version 1.0
 */
public class MathElement implements IListElement{

	private IListElement next;
	private String termElement;

	public MathElement(String termElement)
	{
		this.termElement = termElement;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IListElement next() {
		return this.next;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setData(String termElement) {
		this.termElement = termElement;		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String data() {
		return this.termElement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasNext() {
		return this.next != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setNext(IListElement next) {
		this.next = next;
	}

}
