import java.io.*;
import java.util.*;

public class Anagram
{
	Vocab vocab=new Vocab(37,10);
	int size;
	
	public Anagram(String filename)
	{
		try
		{
			Scanner s = new Scanner(new File(filename));
			size=s.nextInt();
			int size2=size;
			for(int iter=0;iter<size2;iter++)
			{
				String str=s.next();
				int len=str.length();
				if(len<3||len>12)
				{
					size--;
					continue;
				}
				else
				{
					len=len-3;
				}
				if(str.charAt(0)<='9' && str.charAt(0)>='0')
				{
					vocab.v.get(str.charAt(0)%48).get(len).add(str);
				}
				else if(str.charAt(0)>='a'&&str.charAt(0)<='z')
				{
					vocab.v.get(str.charAt(0)%87).get(len).add(str);
				}
				else if(str.charAt(0)=='\'')
				{
					vocab.v.get(36).get(len).add(str);
				}
				else
				{
					size--;
				}
			}
			vocab.emptyLetter();
			s.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
		}
	}
	
	static Vocab refVocab(Vocab unrefVocab, String str)
	{
		int b=0;
		Vocab refV=new Vocab(unrefVocab);
		for(int i=0;i<unrefVocab.a;i++)
		{
			char ch=unrefVocab.getChar(i);
			b=unrefVocab.b;
			if(str.indexOf(ch)==-1)
			{
				for(int j=0;j<b;j++)
				refV.v.get(i).get(j).clear();
			}
			for(int j=0;j<b;j++)
			{
				if(j>str.length()-3)
				{
					refV.v.get(i).remove(j);
					j--;
					b--;
				}
			}
		}
		refV.b=b;
		refV.emptyLetter();
		for(int i=0;i<refV.a;i++)
		{
			for(int j=0;j<refV.b;j++)
			{
				for(int k=0;k<refV.v.get(i).get(j).size();k++)
				{
					String subStr=refV.v.get(i).get(j).get(k);
					int[] freq = new int[256];
					for (int l = 0; l < str.length(); l++)
					{
						freq[str.charAt(l)]++;
					}
					for (int l = 0; l < subStr.length(); l++) 
					{
						freq[subStr.charAt(l)]--;
						if (freq[subStr.charAt(l)] < 0) 
						{
							refV.v.get(i).get(j).remove(k);
							k--;
							break;
						}
					}
				}
			}
		}
		refV.emptyLetter();
		return refV;
	}
	
	ArrayList<String> recAnagram(Vocab v,String str,int point)
	{
		//System.out.println("yo");
		ArrayList<String> lis=new ArrayList<String>();
		if(str.length()==0)
		{
			lis.add("");
			return lis;
		}
		if(str.length()<3)
		{
			return lis;
		}
		if(point==3)
		{
			return lis;
		}
		//Vocab refV=refVocab(v,str);
		/*for(int j=0;j<refV.b;j++)
		{
			System.out.println(refV.v.get(0).get(j).toString());
		}*/
		boolean x=true;
		for(int i=0;i<v.a;i++)
		{
			for(int j=0;j<v.b;j++)
			{
				for(int k=0;k<v.v.get(i).get(j).size();k++)
				{
					String ana=v.v.get(i).get(j).get(k);
					String modstr=new String(str);
					int[] freq=new int[256];
					x=true;
					if(point!=0)
					{
						for (int l = 0; l < str.length(); l++)
						{
							freq[str.charAt(l)]++;
						}
						for (int l = 0; l < ana.length(); l++) 
						{
							freq[ana.charAt(l)]--;
							if (freq[ana.charAt(l)] < 0) 
							{
								x=false;
								break;
							}
						}
					}
					//System.out.println(ana+" "+modstr);
					if(x)
					{
						for (int l = 0; l < ana.length(); l++)
						{
							modstr=modstr.replaceFirst(""+ana.charAt(l), ""); //checkpoint
						
						}
						//System.out.println(ana+" "+modstr+modstr.length());
						ArrayList<String> subAna= recAnagram(v,modstr,point+1);
						for (int l = 0; l < subAna.size(); l++)
							lis.add(ana+" "+subAna.get(l));
					}
				}
			}
		}
		
		return lis;
	}
	
	public static void main(String args[])
	{
		try
		{
			Scanner s = new Scanner(new File(args[1]));
			Anagram anagram=new Anagram(args[0]);
			/*for(int j=0;j<anagram.vocab.b;j++)
			{
				System.out.println(anagram.vocab.v.get(11).get(j).toString());
			}*/
			int n=s.nextInt();
			for(int i=0;i<n;i++)
			{
				String str=s.next().toString();
				if(str.length()>12)
					continue;
				str.replaceAll(" ", "");
				ArrayList<String> ar=anagram.recAnagram(refVocab(anagram.vocab,str), str,0);
				ar.sort(null);
				//Set<String> abc=new HashSet<String>();
				//abc.addAll(ar);
				for(int j=0;j<ar.size();j++)
				{
					if(j==0);
					else if(ar.get(j).equals(ar.get(j-1)))
						continue;
					System.out.println(ar.get(j).trim());
				}
				System.out.println("-1");
			}
			s.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
}