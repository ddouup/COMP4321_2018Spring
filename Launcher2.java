import java.io.IOException;
import java.util.Vector;
import org.htmlparser.util.ParserException;

public class Launcher2 {
	
	private static int Required_Number = 1000;
	private static int count_url;
	public  static InvertedIndex Constructor;
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

	Launcher2() throws IOException
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
		//Newly added table
		Key_Weight_index =new InvertedIndex("project","key_weight");
		TitlePhrase_Weight_index = new InvertedIndex("project","titlephrase_weight");
		Docid_SortKey_index = new InvertedIndex("project","docid_sortkey");
		TitleId_SortKey_index = new InvertedIndex("project","titleid_sortkey");
		Docid_KeyPos_index = new InvertedIndex("project","docid_keypos"); //stored words with position, used for phrase search
		Docid_String_index = new InvertedIndex("project","docid_string");
	}
	
	public int getRequiredNumber() throws IOException
	{
		return Required_Number;
	}

	public static void main(String[] args) throws IOException, ParserException
	{
		Launcher2 launcher2 = new Launcher2();
		Crawler crawler = new Crawler("http://www.cse.ust.hk/");
		
		Vector<String> old_ids = Docid_String_index.ReturnKey();
		System.out.println(old_ids);
		for (int i = 0; i < old_ids.size(); i++)
		{
			Docid_String_index.delEntry(old_ids.get(i));
			System.out.println(old_ids.get(i)+" deleted.");
		}
		
		Vector<String> words=null;
		Vector<String> ids = Id_Url_index.ReturnKey();
		System.out.println(ids);
		String url = "";
		String KeyPos = "";
		for (int i = 0; i < ids.size(); i++)
		{
			url = Id_Url_index.getEntry(ids.get(i));
			System.out.println("Processing url: "+ids.get(i));
			crawler.setURL(url);
			words = crawler.extractWords();
			if(words.size()!=0)
			{
				for (int j = 0; j < words.size(); j++)
				{
					KeyPos = words.get(j)+":"+Integer.toString(j);
					Docid_KeyPos_index.addEntry(ids.get(i), KeyPos);
				}
			}
			else
				Docid_KeyPos_index.addEntry(ids.get(i), "");
		}
		/*
		for (int i = 0; i < ids.size(); i++)
		{
			System.out.println("i: "+ids.get(i));
			url = Id_Url_index.getEntry(ids.get(i));
			System.out.println("Processing url: "+url);
			crawler.setURL(url);
			String result = crawler.extractString();
			Docid_String_index.addEntry(ids.get(i), result);
		}*/
		Constructor.finalization();
		System.out.println("done");
	}
}
