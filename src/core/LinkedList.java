package core;

/**
 * @author Fabian Wagner
 * @since 03/07/12
 * @version 1.0
 */
public abstract class LinkedList<E extends IListElement> 
{
	/**
	 * Pointer to the first element of the list.
	 */
	protected E first;
	
	/**
	 * Pointer to the last element of the list.
	 */
	protected E last;
	
	/**
	 * Represents an empty list as a String.
	 */
	public static final String EMPTY_LIST_STRING = "empty";
	
	/**
	 * Sets the first element of the list.
	 * @param first
	 * 			represents the new first element.
	 */
	public void setFirst(E first)
	{
		this.first = first;
	}
	
	/**
	 * Returns the first element of the list.
	 * @return
	 * 		the first element of the list.
	 */
	public E first()
	{
		return this.first;
	}
	
	/**
	 * Adds an element to the end of the list and resets the pointers.
	 * @param element
	 * 			represents the added element.
	 */
	public abstract void addElement(E element);
	
	/**
	 * Returns an element of the LinkedList with a given position in the list.
	 * @param id
	 * 		position of the element
	 * @return
	 * 		the element
	 */
	public IListElement get(int id) {
		if(id < 0)
			throw new IllegalArgumentException("ID is negative.");
		
		IListElement pivot = this.first;
		int counter = 0;
		while(pivot != null) {
			if(id == counter)
				return pivot;
			counter++;
			pivot = pivot.next();
		}
		return null;
		
	}
	
	
	/**
	 * Returns a string which is describing the list. Between every element is a "|".
	 * @return
	 * 		a string which is representing the list.
	 */
	public String toString()
	{
		if(this.first == null)
			return LinkedList.EMPTY_LIST_STRING;

		StringBuilder out = new StringBuilder();
		IListElement pivot = this.first;

		while(pivot.hasNext()) {
			out.append(pivot.data()+"|");
			pivot = pivot.next();
		}
		out.append(pivot.data());
		return out.toString();
	}
	
}
