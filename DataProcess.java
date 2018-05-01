import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import org.htmlparser.util.ParserException;

import java.util.Comparator;
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
		if (value!=null)
		{
	        String[] tokens=value.split(";");
	        int i=0;
	        for(String token:tokens)
	        {
	        	String[] tmp=token.split(":");
	        	if (tmp[0].equals(word))
	        	{
	        		try 
	        		{
	        			a = Integer.parseInt(tmp[1]);
	        		} 
	        		catch (NumberFormatException e)
	        		{
	        		    e.printStackTrace();
	        		}
	        		return a;
	        	}
	        }
		}
		return a;
	}
	
	public int Returntfmax(String id, boolean title) throws IOException
	{
		String value = "";
		if(title)
			value = launcher.TitleId_SortKey_index.getEntry(id);
		else
			value = launcher.Docid_SortKey_index.getEntry(id);
		return Integer.parseInt(value.split(";")[0].split(":")[1]);
	}
	
	public double Returnidf(String Word, boolean title) throws IOException
	{
		double a = 0;
		double idf = 0;
		String value="";
		if(title)
			value=launcher.Key_TitleId_index.getEntry(Word);
		else
			value=launcher.Key_Docid_index.getEntry(Word);
		System.out.println(value);
		if(value!=null)
		{
	        String[] tokens=value.split(";");
	        a=tokens.length;
	        System.out.println("Doc numbers: "+a);
	        idf=Math.log(launcher.getRequiredNumber()/a)/Math.log(2);
		}
		return idf;
	}
	
	public double TermWeightCalculate(int id, int tf, String Word, boolean title) throws IOException
	{
        double a=0.0;
		double idf=Returnidf(Word,title);
		int tfmax=Returntfmax(Integer.toString(id),title);
		if(idf == 0) {
			System.out.println(Word+"'s idf is 0");
			System.exit(0);
		}
		a=(tf*idf)/tfmax;
		return a;	
	}
	
    static class KeyComparator implements Comparator {  
        public int compare(Object obj1, Object obj2) { 
             String key1=(String)obj1;
             String key2=(String)obj2;
             Integer k2 = Integer.parseInt(key2.split(":")[1]);
             Integer k1 =Integer.parseInt(key1.split(":")[1]); 
            return k2.compareTo(k1);  
        }  
    } 
    public static void sortKey(boolean title) throws IOException
    {
    	for (int i = 1; i <= launcher.getRequiredNumber(); i++)
		{	
			Vector<String> keys = new Vector<String>();
			String result="";
			System.out.println(i);
			String value = "";
			if(title)
				value = launcher.TitleId_Key_index.getEntry(Integer.toString(i));
			else
				value = launcher.Docid_Key_index.getEntry(Integer.toString(i));
			if (value!=null)
			{
				String[] tokens = value.split(";");
				for(String token:tokens)
				{
					keys.add(token);
				}
				Collections.sort(keys, new KeyComparator());
				for(int j = 0; j < keys.size(); j++)
					result = result+keys.get(j)+";";
			}
			System.out.println(result);
			if(title)
				launcher.TitleId_SortKey_index.addEntry(Integer.toString(i), result);
			else
				launcher.Docid_SortKey_index.addEntry(Integer.toString(i), result);
		}
    }
    
	public static void main (String[] args) throws IOException, ParserException
	{	

		DataProcess Data=new DataProcess();
		
		sortKey(true);
		sortKey(false);
		
        Vector<String> Key=new Vector<String>();
        String Content="";
        String word="";
        double score=0.0;
        DecimalFormat df = new DecimalFormat( "0.000000");  
 		for(int i = 1; i <= launcher.getRequiredNumber(); i++)		
 		{
 	        Content=launcher.Docid_Key_index.getEntry(Integer.toString(i));
 	        if(Content!=null && !Content.equals(""))
 	        {
 	        	System.out.println("Keys in doc "+i+": "+Content);
 	        	double vectorLength = 0.0;
 	          String[] tokens=Content.split(";");
 	          for(String token:tokens)
 	          {
 	        	 String[] tmp=token.split(":");
 	             int tf=Integer.parseInt(tmp[1]);
 	             word=tmp[0];
 	             System.out.println("Word: "+word);
 	 			score=Data.TermWeightCalculate(i,tf,word,false);
 	 			System.out.println("Weight of "+word+": "+score);
 	 			vectorLength=vectorLength+score*score;
 	 			launcher.Docid_KeyWeight_index.addEntry(Integer.toString(i), word+","+String.valueOf(score));
 	 			//String value=String.valueOf(i)+","+String.valueOf(df.format(score));
 	 			String value=Integer.toString(i)+","+score;
 	 			launcher.Key_Weight_index.addEntry(word, value); 
 	          }
 	          launcher.Docid_VectorLength_index.addEntry(Integer.toString(i), String.valueOf(Math.sqrt(vectorLength)));
 	          System.out.println("Doc "+i+" vector length: "+Math.sqrt(vectorLength));
 	        }
 		}
 		
 		for(int i = 1; i <= launcher.getRequiredNumber(); i++)		
 		{
 	        Content=launcher.TitleId_Key_index.getEntry(Integer.toString(i));
 	        if(Content!=null && !Content.equals(""))
 	        {
 	        	System.out.println("Keys in doc "+i+"title: "+Content);
 	        	double vectorLength = 0.0;
 	          String[] tokens=Content.split(";");
 	          for(String token:tokens)
 	          {
 	        	 String[] tmp=token.split(":");
 	             int tf = Integer.parseInt(tmp[1]);
 	             word=tmp[0];
 	        	System.out.println("Word: "+word);
 	 			score=Data.TermWeightCalculate(i,tf,word,true);
 	 			System.out.println("Weight of "+word+": "+score);
 	 			vectorLength=vectorLength+score*score;
 	 			launcher.Titid_KeyWeight_index.addEntry(Integer.toString(i), word+","+String.valueOf(score));
 	 			//String value=String.valueOf(i)+","+String.valueOf(df.format(score));
 	 			String value=Integer.toString(i)+","+score;
 	 			launcher.TitlePhrase_Weight_index.addEntry(word, value); 
 	          } 	          
 	          launcher.Titid_VectorLength_index.addEntry(Integer.toString(i), String.valueOf(Math.sqrt(vectorLength)));
 	          System.out.println("Doc "+i+" vector length: "+Math.sqrt(vectorLength));
 	        }
 		}
 		
		launcher.Constructor.finalization();
 		System.out.print("Done");
	}
}