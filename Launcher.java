import java.util.Collections;
import java.util.Vector;
import org.htmlparser.util.ParserException;
import java.io.IOException;

public class Launcher
{	
	private static int Required_Number = 652;
	private static int count_url;
	private static int count_term;
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
	



	Launcher() throws IOException
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

	}
	
	public int getRequiredNumber() throws IOException
	{
		return Required_Number;
	}
	
	public void addData(Crawler crawler, int current_id) throws IOException
	{
		Id_Url_index.addEntry(Integer.toString(current_id), crawler.getURL());
		Url_Id_index.addEntry(crawler.getURL(), Integer.toString(current_id));
		System.out.println("Id_Url_index added");
		String title="";
		try {
			title = crawler.extractTitle();
		} catch (ParserException e) {
			e.printStackTrace();
		}
		Id_Title_index.addEntry(Integer.toString(current_id), title);
		System.out.println("Id_Title_index added");
		String[] info = crawler.extractPageInfo().split(";");
		String content_length = info[0];
		Id_ContentLength_index.addEntry(Integer.toString(current_id), content_length);
		String last_modified = info[1];
		Id_LastModified_index.addEntry(Integer.toString(current_id), last_modified);
		System.out.println("Id_ContentLength_index and Id_LastModified_index_added");
	}
	
	public void addTitleterm(Crawler crawler) throws IOException
	{
		int wordcount=0;
		Vector<String> titlewords=null;
		try {
			titlewords = crawler.extractTitleKey();
		} catch (ParserException e) {
			e.printStackTrace();
		}
		Collections.sort(titlewords);
		String k="";
		if(titlewords.size()!=0)								
		k=titlewords.get(wordcount);
		else
		TitleId_Key_index.addEntry(Integer.toString(count_url),"");
		for (int g = 0; g < titlewords.size(); g++){
			if(!k.equals(titlewords.get(g)))
			{
			wordcount=g-wordcount;
			String tmp=""+wordcount;
			TitleId_Key_index.addEntry(Integer.toString(count_url),k+":"+tmp);
			Key_TitleId_index.addEntry(k,Integer.toString(count_url));
			wordcount=g;
			k=titlewords.get(g);
			}
		}
		titlewords.clear();
	}
	
	public void addContentterm(Crawler crawler) throws IOException
	{
		int wordcount=0;
		Vector<String> words=null;
		try {
			words = crawler.extractWords();
		} catch (ParserException e) {
			e.printStackTrace();
		}
		Collections.sort(words);
		String k="";
		if(words.size()!=0)								
		k=words.get(wordcount);
		else
		Docid_Key_index.addEntry(Integer.toString(count_url), "");
		for (int g = 0; g < words.size(); g++)
		{
			if(!k.equals(words.get(g)))
			{
			wordcount=g-wordcount;
			if(wordcount>2)
			{
			String tmp=""+wordcount;
			Docid_Key_index.addEntry(Integer.toString(count_url), k+":"+tmp);
			Key_Docid_index.addEntry(k,Integer.toString(count_url));
			}
			wordcount=g;
			k=words.get(g);
			}
		}
		words.clear();
	}
	
	public void updateData(Crawler crawler, String current_id) throws IOException
	{
		Id_Url_index.updateEntry(current_id, crawler.getURL());
		Url_Id_index.updateEntry(crawler.getURL(), current_id);
		String title="";
		try {
			title = crawler.extractTitle();
		} catch (ParserException e) {
			e.printStackTrace();
		}
		Id_Title_index.updateEntry(current_id, title);
		String[] info = crawler.extractPageInfo().split(";");
		String content_length = info[0];
		Id_ContentLength_index.updateEntry(current_id, content_length);
		String last_modified = info[1];
		Id_LastModified_index.updateEntry(current_id, last_modified);
	}
	
	public void updateTitleterm(Crawler crawler) throws IOException
	{
		TitleId_Key_index.delValue(Integer.toString(count_url));
		Key_TitleId_index.updateKey(Integer.toString(count_url));
		int wordcount=0;
		Vector<String> titlewords=null;
		try {
			titlewords = crawler.extractTitleKey();
		} catch (ParserException e) {
			e.printStackTrace();
		}
		Collections.sort(titlewords);
		String k="";
		if(titlewords.size()!=0)								
		k=titlewords.get(wordcount);
		else
		TitleId_Key_index.updateEntry(Integer.toString(count_url),"");
		for (int g = 0; g < titlewords.size(); g++){
			if(!k.equals(titlewords.get(g)))
			{
			wordcount=g-wordcount;
			String tmp=""+wordcount;
			TitleId_Key_index.updateEntry(Integer.toString(count_url),k+":"+tmp);
			Key_TitleId_index.updateEntry(k,Integer.toString(count_url));
			wordcount=g;
			k=titlewords.get(g);
			}
		}
		titlewords.clear();
	}
	
	public void updateContentterm(Crawler crawler) throws IOException
	{
		Docid_Key_index.delValue(Integer.toString(count_url));
		Key_Docid_index.updateKey(Integer.toString(count_url));
		int wordcount=0;
		Vector<String> words=null;
		try {
			words = crawler.extractWords();
		} catch (ParserException e) {
			e.printStackTrace();
		}
		Collections.sort(words);
		String k="";
		if(words.size()!=0)								
		k=words.get(wordcount);
		else
		Docid_Key_index.updateEntry(Integer.toString(count_url), "");
		for (int g = 0; g < words.size(); g++){
			if(!k.equals(words.get(g)))
			{
			wordcount=g-wordcount;
			String tmp=""+wordcount;
			Docid_Key_index.updateEntry(Integer.toString(count_url), k+":"+tmp);
			Key_Docid_index.updateEntry(k,Integer.toString(count_url));
			wordcount=g;
			k=words.get(g);
			}
		}
		words.clear();
	}
	
	public static boolean Parent_ChildExists(String parentId, String childId) throws IOException
	{
		if(ChildLink_index.containsKey(parentId))
		{
			String[] childLinks = ChildLink_index.getEntry(parentId).split(";");
			for (int i = 0; i < childLinks.length; i++)
			{
				if(childLinks[i].equals(childId))
				{
					System.out.println("Parent_Child link already exists.");
					return true;
				}
			}
			return false;
		}
		else
			return false;
	}

	public static void main (String[] args)
	{	
		count_url = 1;
		count_term = 1;
		try
		{
			Crawler crawler = new Crawler("http://www.cse.ust.hk/");
					
			try
			{
				Launcher launcher = new Launcher();
				int current_id = count_url;
				if (Url_Id_index.containsKey(crawler.getURL())){
					String id_temp = Url_Id_index.getEntry(crawler.getURL());
					String old_lastmodified = Id_LastModified_index.getEntry(id_temp);

					String[] info = crawler.extractPageInfo().split(";");
					String last_modified = info[1];
					if (!last_modified.equals(old_lastmodified))
					{
						launcher.updateData(crawler,id_temp);
						launcher.updateTitleterm(crawler);
						launcher.updateContentterm(crawler);
					}
				}
				else
				{
				launcher.addData(crawler,current_id);
				launcher.addTitleterm(crawler);
				launcher.addContentterm(crawler);
				}
				Constructor.commit();

				while (true){
					if (count_url < Required_Number){
						Vector<String> links = crawler.extractLinks();
						for (int i = 0; i < links.size(); i++){
							String link = links.get(i);
							System.out.println("Processing link:"+link+", Id: " + current_id);
							if (Url_Id_index.containsKey(link))
							{
								String id_temp = Url_Id_index.getEntry(link);
								System.out.println("Id_temp: "+id_temp);
								if(!Parent_ChildExists(Integer.toString(current_id), id_temp))
								{
									ChildLink_index.addEntry(Integer.toString(current_id), id_temp);
									ParentLink_index.addEntry(id_temp, Integer.toString(current_id));
								}
								String old_lastmodified = Id_LastModified_index.getEntry(id_temp);
								
								String current_url = crawler.getURL();
								crawler.setURL(link);
								
								String[] info = crawler.extractPageInfo().split(";");
								String last_modified = info[1];
								
								if (!last_modified.equals(old_lastmodified))
								{
									launcher.updateData(crawler,id_temp);
									launcher.updateTitleterm(crawler);
									launcher.updateContentterm(crawler);
								}
								
								crawler.setURL(current_url);
							}
							else{
								count_url++;
								if (count_url > Required_Number)
									break;
								System.out.println(count_url);
								String current_url = crawler.getURL();
								crawler.setURL(link);
								ChildLink_index.addEntry(Integer.toString(current_id), Integer.toString(count_url));
								ParentLink_index.addEntry(Integer.toString(count_url), Integer.toString(current_id));
								launcher.addData(crawler, count_url);

								//Content Key
								launcher.addContentterm(crawler);
								
								//Title Key
                                launcher.addTitleterm(crawler);
                         		Constructor.commit();
								crawler.setURL(current_url);
							}
						}
					}

					System.out.println(Integer.toString(current_id) + "parent pages finished.");
					System.out.println("");

					current_id++;
					if (current_id > Required_Number)
						break;
					String next_url = Id_Url_index.getEntry(Integer.toString(current_id));
					crawler.setURL(next_url);
				}

				System.out.println("Finish");
				System.out.println("");

				Constructor.finalization();		
			}
			catch(IOException ex)
			{
				System.err.println(ex.toString());
			}
		}			
		catch (ParserException e)
		{
			e.printStackTrace ();
		}
	}
}