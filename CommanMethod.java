//CommanMethod

package tp.xt;

import java.io.*;
import java.util.*;
public class CommanMethod
{
	public static void main(String []argc)
	{
		CommanMethod cm=new CommanMethod();
	}
	public int GetBraceMatch(String value, String searchtext,int position)
	{
		String text=value;
		int counter=-1;
		int cher=0;
		int s=text.indexOf(searchtext,0);
		if(s!=-1)
		{
			while(true)
			{
				char c=text.charAt(s);
				++cher;
				if(c=='{')
				{
					if(counter==-1)
					{
						counter=0;
					}
					++counter;
					//System.out.println("counter increase ::"+counter);
				}
				if(c=='}')
				{
					--counter;
					//System.out.println("counter decrease ::"+counter);
				}
				if(counter==0)
				{
					//System.out.println("chareter count ::"+cher);
					break;
				}
				s++;
			}
		}
		return cher;
	}
}