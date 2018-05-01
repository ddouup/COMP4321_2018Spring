import java.util.Collections;
import java.util.Vector;
import org.htmlparser.util.ParserException;
import java.io.IOException;

public class PRINTOUT
{	
	private static InvertedIndex Constructor;
	public	static InvertedIndex Id_Url_index;
	public	static InvertedIndex Url_Id_index;
	public	static InvertedIndex Id_Title_index;
	public	static InvertedIndex Id_ContentLength_index;
	public	static InvertedIndex Id_LastModified_index;
	public	static InvertedIndex ChildLink_index;
	public	static InvertedIndex ParentLink_index;
	public	static InvertedIndex Docid_Key_index;
	public	static InvertedIndex Key_Docid_index;
	public	static InvertedIndex TitleId_Key_index;
	public	static InvertedIndex Key_TitleId_index;
	
	public	static InvertedIndex Key_Weight_index;
	public  static InvertedIndex TitlePhrase_Weight_index;
	public	static InvertedIndex Docid_SortKey_index;
	public	static InvertedIndex TitleId_SortKey_index;
	public	static InvertedIndex Docid_KeyPos_index;
	public	static InvertedIndex Docid_String_index;
	
	public	static InvertedIndex Docid_KeyWeight_index;
	public	static InvertedIndex Docid_VectorLength_index;
	public	static InvertedIndex Titid_KeyWeight_index;
	public	static InvertedIndex Titid_VectorLength_index;

	PRINTOUT() throws IOException
	{	
		Constructor = new InvertedIndex("project");
		Id_Url_index = new InvertedIndex("project","id_url");
		Url_Id_index = new InvertedIndex("project","url_id");
		Id_Title_index = new InvertedIndex("project","id_title");
		Id_ContentLength_index = new InvertedIndex("project","id_contentlength");
		Id_LastModified_index = new InvertedIndex("project","id_lastmodified");
		ChildLink_index = new InvertedIndex("project","childlink");
		ParentLink_index = new InvertedIndex("project","parentlink");
		Docid_Key_index = new InvertedIndex("project","docid_key");
		Key_Docid_index = new InvertedIndex("project","key_docid");
		TitleId_Key_index =new InvertedIndex("project","title_key");
		Key_TitleId_index =new InvertedIndex("project","key_titleid");
		
		Key_Weight_index =new InvertedIndex("project","key_weight");
		TitlePhrase_Weight_index = new InvertedIndex("project","titlephrase_weight");
		Docid_SortKey_index = new InvertedIndex("project","docid_sortkey");
		TitleId_SortKey_index = new InvertedIndex("project","titleid_sortkey");
		Docid_KeyPos_index = new InvertedIndex("project","docid_keypos");
		Docid_String_index = new InvertedIndex("project","docid_string");
		
		Docid_KeyWeight_index = new InvertedIndex("project","docid_keyweight");
		Docid_VectorLength_index = new InvertedIndex("project","docid_vectorlength");
		Titid_KeyWeight_index = new InvertedIndex("project","titid_keyweight");
		Titid_VectorLength_index = new InvertedIndex("project","titid_vectorlength");
	}

	public static void main (String[] args)
	{	
			try
			{
                PRINTOUT PRINT=new PRINTOUT();
				//Id_Url_index.printAll();
				//Url_Id_index.printAll();
				//Id_Title_index.printAll();
				//Id_ContentLength_index.printAll();
				//Id_LastModified_index.printAll();
				//ChildLink_index.printAll();
				//ParentLink_index.printAll();
				//Docid_Key_index.printAll();
				//Key_Docid_index.printAll();
				//TitleId_Key_index.printAll();
				//Key_TitleId_index.printAll();
                
				//Key_Weight_index.printAll();
				//TitlePhrase_Weight_index.printAll();
				//Docid_SortKey_index.printAll();
				//TitleId_SortKey_index.printAll();
                //Docid_KeyPos_index.printAll();
                //Docid_String_index.printAll();

        		Docid_KeyWeight_index.printAll();
        		//Docid_VectorLength_index.printAll();
        		//Titid_KeyWeight_index.printAll(); 
        		//Titid_VectorLength_index.printAll(); 

                /*System.out.println(Key_Docid_index.getEntry("hkust"));
                System.out.println(Key_Weight_index.getEntry("hkust"));
                System.out.println(Key_Docid_index.getEntry("pointer"));
                System.out.println(Key_Weight_index.getEntry("pointer"));*/
                //System.out.println(Docid_Key_index.getEntry("1000")+"\n"+Docid_String_index.getEntry("1000"));
				Constructor.finalization();		
			}
			catch(IOException ex)
			{
				System.err.println(ex.toString());
			}
		}	
}
	
