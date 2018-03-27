import java.io.File;  
import java.io.InputStreamReader;

import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.helper.FastIterator;
import jdbm.htree.HTree;

import java.io.BufferedReader;  
import java.io.BufferedWriter;   
import java.io.FileWriter;
import java.io.IOException;  
  
public class YmyTest {  
	public static void printdash(BufferedWriter out)
	{
	 try {
		out.write("-------------------------------------------------------------------------------------------\r\n");
	} catch (IOException e) {
		e.printStackTrace();
	}	
	 
	}
    public static void main(String args[]) {  
        try { 

        	Launcher launcher=new Launcher();
        	//Launcher.Id_Title_index.printAll();
            File file = new File("spider_result.txt");    
            file.createNewFile();   
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            
            String keyTi;
            String keyUr;
            String keyCl;
            String keyLm;
            String keyCi;
            FastIterator iterTi = Launcher.Id_Title_index.hashtable.keys();
            FastIterator iterUr = Launcher.Id_Url_index.hashtable.keys();
            FastIterator iterCl = Launcher.Id_ContentLength_index.hashtable.keys();
            FastIterator iterLm = Launcher.Id_LastModified_index.hashtable.keys();
            FastIterator iterCi = Launcher.ChildLink_index.hashtable.keys();

            for(int i=0;i<30;i++)
            {
            keyTi=(String)iterTi.next();
            keyUr=(String)iterUr.next();
            keyCl=(String)iterCl.next();
            keyLm=(String)iterLm.next();
            out.write(Launcher.Id_Title_index.hashtable.get(keyTi)+"\r\n");
            out.write(Launcher.Id_Url_index.hashtable.get(keyUr)+"\r\n");
            if(Launcher.Id_LastModified_index.hashtable.get(keyLm).toString().equals("0"))
            out.write("unknown,");
            else
            out.write(Launcher.Id_LastModified_index.hashtable.get(keyLm)+",");
            if(Launcher.Id_ContentLength_index.hashtable.get(keyCl).toString().equals("-1"))
            out.write("unknown\r\n");
            else
            out.write(Launcher.Id_ContentLength_index.hashtable.get(keyCl)+"\r\n");
            
            while((keyCi=(String)iterCi.next()) != null)
            {
            String[] tokens = ((String) Launcher.ChildLink_index.hashtable.get(keyCi)).split(";");
            for(String token:tokens)
            {
            	token=token.replaceAll(" ", ""); 
                out.write(Launcher.Id_Url_index.hashtable.get((String)token)+"\r\n");
            }
            }
            
            printdash(out);
            }


            //out.write("Testing\r\n");  
            out.flush();  
            out.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  