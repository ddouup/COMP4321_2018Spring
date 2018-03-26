import java.io.File;  
import java.io.InputStreamReader;

import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.htree.HTree;

import java.io.BufferedReader;  
import java.io.BufferedWriter;   
import java.io.FileWriter;
import java.io.IOException;  
  
public class YmyTest {  
	public void printdash(BufferedWriter out)
	{
	 try {
		out.write("-------------------------------------------------------------------------------------------");
	} catch (IOException e) {
		e.printStackTrace();
	}	
	}
    public static void main(String args[]) {  
        try { 
			InvertedIndex Id_Url_index = new InvertedIndex("project","id_url");
			InvertedIndex Id_Title_index = new InvertedIndex("project","id_title");
			InvertedIndex Id_ContentLength_index = new InvertedIndex("project","id_contentlength");
			InvertedIndex Id_LastModified_index = new InvertedIndex("project","id_lastmodified");
			InvertedIndex ChildLink_index = new InvertedIndex("project","childlink");
			InvertedIndex ParentLink_index = new InvertedIndex("project","parentlink");
            File file = new File("spider_result.txt");    
            file.createNewFile();   
            BufferedWriter out = new BufferedWriter(new FileWriter(file));  
            out.write("Testing\r\n");  
            out.flush();  
            out.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  