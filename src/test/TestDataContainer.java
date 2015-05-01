package test;

/**
 * Simple data format for test data
 * 
 * @author Benjamin Haettasch
 * @since 27/03/13
 * @version 1.0
 */
public class TestDataContainer
{
	public String input;
	public String output;
	public int position;
	
	public TestDataContainer(String input, String output, int position)
	{
		this.input = input;
		this.output = output;
		this.position = position;
	}
}
