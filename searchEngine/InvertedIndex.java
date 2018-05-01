package searchEngine;

import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.htree.HTree;
import jdbm.helper.FastIterator;

import java.util.Arrays;
import java.util.Vector;

import org.htmlparser.beans.LinkBean;
import org.htmlparser.util.ParserException;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

public class InvertedIndex
{
    private static RecordManager recman;
    public HTree hashtable;
    private String RecordManagerName;
    private String ObjectName;

    public InvertedIndex(String recordmanager) throws IOException
    {
        recman = RecordManagerFactory.createRecordManager("/home/ddou/comp4321/"+recordmanager);
        RecordManagerName = "/home/ddou/comp4321/"+recordmanager;
        System.out.println("RecordManager Created: " + RecordManagerName);
    }
    public InvertedIndex(String recordmanager, String objectname) throws IOException
    {
        ObjectName = objectname;
        
        long recid = recman.getNamedObject(objectname);
            
        if (recid != 0)
            hashtable = HTree.load(recman, recid);
        else
        {
            hashtable = HTree.createInstance(recman);
            recman.setNamedObject( objectname, hashtable.getRecid());
        }
    }

    public static void commit() throws IOException
    {
        recman.commit();
    }
    
    public static void finalization() throws IOException
    {
        recman.commit();
        recman.close();                
    }

    public void addEntry(String _key, String _value) throws IOException
    {
        String content = (String)hashtable.get(_key);
        if (content == null) {
            content = _value;
        } else {
            content += ";" + _value;
        }
        hashtable.put(_key, content);
    }

    public void updateEntry(String _key, String _value) throws IOException
    {
        String content = _value;
        hashtable.put(_key, content);
    }

    public String getEntry(String _key) throws IOException
    {
        return (String) hashtable.get(_key);
    }

    public void delValue(String _key) throws IOException
    {
        String content = "";
        hashtable.put(_key, content);
    }

    public void delEntry(String _key) throws IOException
    {
        // Delete the id and its list from the hashtable
        hashtable.remove(_key);
    }

    public boolean containsKey(String _key) throws IOException
    {
        FastIterator iter = hashtable.keys();
        String key;

        while( (key=(String)iter.next()) != null ) {
            if (key.equalsIgnoreCase(_key)){
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(String _value) throws IOException
    {
        FastIterator iter = hashtable.values();
        String value;
        while( (value=(String)iter.next()) != null ) {
           if (value.equals(_value)){
            return true;
           }
        }
        return false;
    }

    public void printAll() throws IOException
    {
        // Print all the data in the hashtable
        System.out.println("Database: "+ RecordManagerName + "\tHashtable: " + ObjectName);
        FastIterator iter = hashtable.keys();
        String key;
        while( (key=(String)iter.next()) != null ) {
            System.out.println(key + " = " + hashtable.get(key));
        }
        System.out.println("");
    }
    
	public Vector<String> ReturnKey() throws ParserException, IOException
	{
		
		// ADD YOUR CODES HERE
		Vector<String> result = new Vector<String>();
        FastIterator iter = hashtable.keys();
        String key;
        while( (key=(String)iter.next()) != null ) 
        {
                result.add(key);
        }

		return result;
	}
    
    public void updateKey(String Obj) throws IOException
    {
        FastIterator iter = hashtable.values();
        FastIterator iterk= hashtable.keys();
        String value;
        String valuek;
        while( (value=(String)iter.next()) != null ) 
        {
           String result="";
           String[] tokens=value.split(";");

           for(String token:tokens)
           {
        	   if(token.equals(Obj))
        	   {
        		   
        	   }
        	   else
        	   {
        		   result=result+token+";";
        	   }
           }
           if((valuek=(String)iterk.next())!=null)
           updateEntry(valuek,result);
        }
    }

}