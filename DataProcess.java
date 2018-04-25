import java.util.Collections;
import java.util.Vector;
import org.htmlparser.util.ParserException;

import jdbm.helper.FastIterator;

import java.io.IOException;
import java.text.DecimalFormat;

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
		double idf = 0;
		String value="";
		value=launcher.Key_Docid_index.getEntry(Word);
		System.out.println(Word+": "+value);
		if(value!=null)
		{
	        String[] tokens=value.split(";");
	        a=tokens.length;
	        idf=Math.log(launcher.getRequiredNumber()/a)/Math.log(2);
		}
		return idf;
	}
	
	public double TermWeightCalculate(int tf,String Word) throws IOException
	{
        double a=0.0;
		double idf=Returnidf(Word);
		int tfmax=Returntfmax(Word);
		a=(tf*idf)/tfmax;
		return a;	
	}
	
	public static void main (String[] args) throws IOException, ParserException
	{	
		 DataProcess Data=new DataProcess();
         Vector<String> Key=new Vector<String>();
         String Content="";
         String word="";
        double score=0.0;
        DecimalFormat df = new DecimalFormat( "0.0000 ");  
 		for(int i = 1; i < launcher.getRequiredNumber(); i++)		
 		{
 	        Content=launcher.Docid_Key_index.getEntry(Integer.toString(i));
 	        if(Content!=null)
 	        {
 	          String[] tokens=Content.split(";");
 	          for(String token:tokens)
 	          {
 	        	 String[] tmp=token.split(":");
 	        	 boolean m=false;
 	             int tf=0;
 	        	 for(String t:tmp)
 	        	 {
 	        		 if(m==true)
 	        		 {
 	        		   tf=Integer.parseInt(t);
 	        		 }
 	        		 if(m==false)
 	        		 {
 	        		 word=t;
 	        		 m=true;
 	        		 } 
 	        	 }
 	 			score=Data.TermWeightCalculate(tf,word);
 	 			String value=String.valueOf(i)+","+String.valueOf(df.format(score));
 	 			launcher.Key_Weight_index.addEntry(word, value); 
 	          }
 	        }
 		}
 		
 		for(int i = 1; i < launcher.getRequiredNumber(); i++)		
 		{
 	        Content=launcher.TitleId_Key_index.getEntry(Integer.toString(i));
 	        if(Content!=null)
 	        {
 	          String[] tokens=Content.split(";");
 	          for(String token:tokens)
 	          {
 	        	 String[] tmp=token.split(":");
 	        	 boolean m=false;
 	             int tf=0;
 	        	 for(String t:tmp)
 	        	 {
 	        		 if(m==true)
 	        		 {
 	        		   tf=Integer.parseInt(t);
 	        		 }
 	        		 if(m==false)
 	        		 {
 	        		 word=t;
 	        		 m=true;
 	        		 } 
 	        	 }
 	 			score=Data.TermWeightCalculate(tf,word);
 	 			String value=String.valueOf(i)+","+String.valueOf(df.format(score));
 	 			launcher.TitlePhrase_Weight_index.addEntry(word, value); 
 	          }
 	        }
 		}
 		
		launcher.Constructor.finalization();
 		System.out.print("Done");
	}
}