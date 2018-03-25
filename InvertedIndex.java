import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.htree.HTree;
import jdbm.helper.FastIterator;
import java.util.Vector;
import java.io.IOException;
import java.io.Serializable;

public class InvertedIndex
{
    private RecordManager recman;
    private HTree hashtable;
    private String RecordManagerName;
    private String ObjectName;

    public InvertedIndex(String recordmanager, String objectname) throws IOException
    {
        RecordManagerName = recordmanager;
        ObjectName = objectname;
        recman = RecordManagerFactory.createRecordManager(recordmanager);
        long recid = recman.getNamedObject(objectname);
            
        if (recid != 0)
            hashtable = HTree.load(recman, recid);
        else
        {
            hashtable = HTree.createInstance(recman);
            recman.setNamedObject( objectname, hashtable.getRecid());
        }
    }

    public void finalize() throws IOException
    {
        recman.commit();
        recman.close();                
    } 

    public void addEntry(String _key, String _content) throws IOException
    {
        // Add a "docX Y" entry for the key "word" into hashtable
        // ADD YOUR CODES HERE
        String content = (String)hashtable.get(_key);
        if (content == null) {
            content = _content;
        } else {
            content += ";" + _content;
        }
        hashtable.put(_key, content);
    }

    public String getEntry(String _key) throws IOException
    {
        return hashtable.get(_key).toString();
    }

    public void delEntry(String _key) throws IOException
    {
        // Delete the id and its list from the hashtable
        // ADD YOUR CODES HERE
        hashtable.remove(_key);
    }

    public void printAll() throws IOException
    {
        // Print all the data in the hashtable
        // ADD YOUR CODES HERE
        System.out.println("Database: "+ RecordManagerName + "\tHashtable: " + ObjectName);
        FastIterator iter = hashtable.keys();
        String key;
        while( (key=(String)iter.next()) != null ) {
            System.out.println(key + " = " + hashtable.get(key));
        }
        System.out.println("");
    }
}
