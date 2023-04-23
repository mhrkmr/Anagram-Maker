import java.util.ArrayList;

public class Vocab
{
	ArrayList<ArrayList<ArrayList<String>>> v; 
	int a;
	int b;
	
	public Vocab(int sa,int sb)
	{
		a=sa;
		b=sb;
		
		v=new ArrayList<ArrayList<ArrayList<String>>>();
		for(int i=0;i<a;i++)
		{
			v.add(new ArrayList<ArrayList<String>>());
			for(int j=0;j<b;j++)
			{
				v.get(i).add(new ArrayList<String>());
			}
		}
	}
	
	public Vocab(Vocab vocab)
	{
		a=vocab.a;
		b=vocab.b;
		v=new ArrayList<ArrayList<ArrayList<String>>>();
		for(int i=0;i<a;i++)
		{
			v.add(new ArrayList<ArrayList<String>>());
			for(int j=0;j<b;j++)
			{
				v.get(i).add(new ArrayList<String>(vocab.v.get(i).get(j)));
			}
		}
	}
	void emptyLetter()
	{
		for(int i=0;i<a;i++)
		{
			boolean x=true;
			for(int j=0;j<b;j++)
			{
				x= x && v.get(i).get(j).isEmpty();
			}
			if(x)
			{
				v.get(i).clear();
				v.remove(i);
				i--;
				a--;
			}
		}
	}
	
	char getChar(int i)
	{
		for(int j=0;j<b;j++)
		{
			if(!v.get(i).get(j).isEmpty())
			{
				return v.get(i).get(j).get(0).charAt(0);
			}
		}
		return 'a';
	}
	
}