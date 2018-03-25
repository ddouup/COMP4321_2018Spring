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

    public void commit() throws IOException
    {
        recman.commit();
    }

    public void finalize() throws IOException
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
            content += "; " + _value;
        }
        hashtable.put(_key, content);
    }

    public String getEntry(String _key) throws IOException
    {
        return hashtable.get(_key).toString();
    }

    public void delValue(String _key) throws IOException
    {
        String content = "";
        this.addEntry(_key,content);
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
                System.out.println("Bingo!!!!!!!!!");
                return true;
            }
        }
        System.out.println("Woopsssss!!!!!!!!!");
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
}
