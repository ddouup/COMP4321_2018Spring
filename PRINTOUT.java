import java.util.Collections;
import java.util.Vector;
import org.htmlparser.util.ParserException;
import java.io.IOException;

public class PRINTOUT
{	
	private static int Required_Number = 30;
	private static int count_url;
	private static int count_term;
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
	}

	public static void main (String[] args)
	{	
		count_url = 1;
		count_term = 1;
		
			try
			{
                PRINTOUT PRINT=new PRINTOUT();
				Id_Url_index.printAll();
				//Url_Id_index.printAll();
				//Id_Title_index.printAll();
				//Id_ContentLength_index.printAll();
				//Id_LastModified_index.printAll();
				ChildLink_index.printAll();
				ParentLink_index.printAll();
				//Docid_Key_index.printAll();
				//Key_Docid_index.printAll();
				//TitleId_Key_index.printAll();
				//Key_TitleId_index.printAll();
				//Key_Weight_index.printAll();
				
				Constructor.finalization();		
			}
			catch(IOException ex)
			{
				System.err.println(ex.toString());
			}
		}	
}
	
