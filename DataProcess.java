import java.util.Collections;
import java.util.Vector;
import org.htmlparser.util.ParserException;

import jdbm.helper.FastIterator;

import java.io.IOException;

public class DataProcess
{	
	public	static Launcher launcher;



	DataProcess() throws IOException
	{	
		launcher=new Launcher();
	}
	
	
	public int Returntf(String _key,String word) throws IOException
	{
		int a=0;
		String value="";
		value=launcher.Docid_Key_index.getEntry(_key);
		if (!(value==null))
		{
        String[] tokens=value.split(";");
        int i=0;
        for(String token:tokens)
        {
        	String[]tmp=token.split(":");
        	boolean m=false;
        	for(String t:tmp)
        	{
        	if(m)
        	{
        		try 
        		{
        		a = Integer.parseInt(t);
        		} 
        		catch (NumberFormatException e)
        		{
        		    e.printStackTrace();
        		}
        		return a;
        	}
        	if(t.equals(word))
        	{
        	  m=true;	
        	}
        	}
        }
		}
		return a;
	}
	
	public int Returntfmax(String Word) throws IOException
	{
		int a=0;
		for(int i=1;i<launcher.getRequiredNumber();i++)
		{
		a=a+Returntf(Integer.toString(i),Word);
		}
		return a;
	}
	
	public double Returnidf(String Word) throws IOException
	{
		int a=0;
		String value="";
		value=launcher.Key_Docid_index.getEntry(Word);
        String[] tokens=value.split(";");
        a=tokens.length;
        double idf=Math.log(launcher.getRequiredNumber()/a)/Math.log(2);
		return idf;
	}
	
	public double TermWeightCalculate(String _key,String Word) throws IOException
	{
        double a=0.0;
		int tf=Returntf(_key,Word);
		double idf=Returnidf(Word);
		int tfmax=Returntfmax(Word);
		a=(tf*idf)/tfmax;
		return a;	
	}
	
	public static void main (String[] args) throws IOException, ParserException
	{	
		 DataProcess Data=new DataProcess();
         Vector<String> Key=new Vector<String>();
         Key=launcher.Key_Docid_index.ReturnKey();
         String word="";
         double score=0.0;
 		for(int i = 1; i < Key.size(); i++)		
 		{
 			word=Key.get(i);
 			score=Data.TermWeightCalculate(Integer.toString(i),word);
 			launcher.Key_Weight_index.delEntry(Integer.toString(i));
 			launcher.Key_Weight_index.addEntry(word, String.valueOf(score));		
 		}
		launcher.Constructor.finalization();
 		System.out.print("Done");
	}
}