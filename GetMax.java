package tp.xt.util;
public class GetMax
{
	static int [] array;
	public GetMax(int [] arr)
	{
		array= arr;
	}
	
	public static int findMax ()
	{
		if ((array == null) || (array.length == 0))
		{
			throw new IllegalArgumentException ("No valid array");
		}
		return findMaxR (array.length - 1);
	}
	// recursive method    
	private static int findMaxR (int index)
	{
		return (index == 0) ? array[index] : Math.max (array[index], findMaxR (index - 1));
	}
	
	public int getMaxIndex()
	{
		int max= findMax();
		int i=0;
		for(; i<array.length; i++)
		{
			if(array[i]==max)
				break;			
		}
		return i;
	}
}