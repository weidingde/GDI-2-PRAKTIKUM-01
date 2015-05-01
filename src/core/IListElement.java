package core;

/**
 * @author Fabian Wagner
 * @since 03/07/12
 * @version 1.0
 */
public interface IListElement 
{
	/**
	 * Returns a pointer to another IListElement.
	 * If the IListElement hasn't a pointer of another IListElement the method returns null.
	 * @return the IListElement of the pointer
	 */
	public IListElement next();
	
	/**
	 * Sets the pointer of the IListElement by using the parameter.
	 * @param next
	 * 		is the new pointer of the IListElement.
	 */
	public void setNext(IListElement next);
	
	/**
	 * Sets the data field of the IListElement by using the parameter.
	 * @param data
	 * 		is the new data in the data field.
	 */
	public void setData(String data);
	
	/**
	 * Returns the data in the data field of the IListElement.
	 * @return
	 * 		the data in the data field.
	 */
	public String data();
	
	/**
	 * Returns true if the pointer of the IListElement isn't null. Otherwise the method returns false.
	 * @return
	 * 		<i>true</i>/<i>false</i>
	 */
	public boolean hasNext();
	
	
}
