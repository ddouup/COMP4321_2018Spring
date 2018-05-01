package searchEngine;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.Vector;
import org.htmlparser.util.ParserException;
import java.io.IOException;

public class Launcher
{	
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
	
	public	static InvertedIndex Docid_KeyWeight_index;
	public	static InvertedIndex Docid_VectorLength_index;
	public	static InvertedIndex Titid_KeyWeight_index;
	public	static InvertedIndex Titid_VectorLength_index;

	public Launcher() throws IOException
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
		
		Docid_KeyWeight_index = new InvertedIndex("project","docid_keyweight");
		Docid_VectorLength_index = new InvertedIndex("project","docid_vectorlength");
		Titid_KeyWeight_index = new InvertedIndex("project","titid_keyweight");
		Titid_VectorLength_index = new InvertedIndex("project","titid_vectorlength");
	}
	
	public Launcher(String path) throws IOException
	{
		Constructor = new InvertedIndex(path);
		Id_Url_index = new InvertedIndex(path,"id_url");
		Url_Id_index = new InvertedIndex(path,"url_id");
		Id_Title_index = new InvertedIndex(path,"id_title");
		Id_ContentLength_index = new InvertedIndex(path,"id_contentlength");
		Id_LastModified_index = new InvertedIndex(path,"id_lastmodified");
		ChildLink_index = new InvertedIndex(path,"childlink");
		ParentLink_index = new InvertedIndex(path,"parentlink");
		Docid_Key_index = new InvertedIndex(path,"docid_key");
		Key_Docid_index = new InvertedIndex(path,"key_docid");
		TitleId_Key_index =new InvertedIndex(path,"title_key");
		Key_TitleId_index =new InvertedIndex(path,"key_titleid");
		//Newly added table
		Key_Weight_index =new InvertedIndex(path,"key_weight");
		TitlePhrase_Weight_index = new InvertedIndex(path,"titlephrase_weight");
		Docid_SortKey_index = new InvertedIndex(path,"docid_sortkey");
		TitleId_SortKey_index = new InvertedIndex(path,"titleid_sortkey");
		Docid_KeyPos_index = new InvertedIndex(path,"docid_keypos"); //stored words with position, used for phrase search
		
		Docid_KeyWeight_index = new InvertedIndex(path,"docid_keyweight");
		Docid_VectorLength_index = new InvertedIndex(path,"docid_vectorlength");
		Titid_KeyWeight_index = new InvertedIndex(path,"titid_keyweight");
		Titid_VectorLength_index = new InvertedIndex(path,"titid_vectorlength");
	}

	public int getRequiredNumber() throws IOException
	{
		return Required_Number;
	}
	
	public Vector<String> Stop_Stem(String text) throws ParserException
	{
		StopStem stopStem = new StopStem("stopwords.txt");
		Vector<String> result = new Vector<String>();
		StringTokenizer st = new StringTokenizer(text);
		while (st.hasMoreTokens()) {
			String word = st.nextToken();
			if (!stopStem.isStopWord(word)){
				word = stopStem.stem(word);
				boolean isWord=word.matches("^[A-Za-z0-9]+");
				if(isWord)
				result.add(word);
			}
		}
		return result;
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
		try {
			if(Integer.parseInt(content_length)<1)
				content_length = Integer.toString(crawler.extractString().length());
		} catch (ParserException e) {
			e.printStackTrace();
		}
		Id_ContentLength_index.addEntry(Integer.toString(current_id), content_length);
		String last_modified = info[1];
		Id_LastModified_index.addEntry(Integer.toString(current_id), last_modified);
		System.out.println("Id_ContentLength_index and Id_LastModified_index_added");
		
		//Add title word
		int wordcount=0;
		Vector<String> titlewords=null;
		try {
			titlewords = Stop_Stem(title);
		} catch (ParserException e) {
			e.printStackTrace();
		}
		Collections.sort(titlewords);
		String k="";
		if(titlewords.size()!=0)								
			k=titlewords.get(wordcount);
		else
			TitleId_Key_index.addEntry(Integer.toString(current_id),"");
		for (int g = 0; g < titlewords.size(); g++){
			if(!k.equals(titlewords.get(g)))
			{
				wordcount=g-wordcount;
				String tmp=Integer.toString(wordcount);
				TitleId_Key_index.addEntry(Integer.toString(current_id),k+":"+tmp);
				Key_TitleId_index.addEntry(k,Integer.toString(current_id));
				wordcount=g;
				k=titlewords.get(g);
			}
		}
		titlewords.clear();
	}
	
	public void addContentterm(Crawler crawler, int current_id) throws IOException
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
			Docid_Key_index.addEntry(Integer.toString(current_id), "");
		for (int g = 0; g < words.size(); g++)
		{
			if(!k.equals(words.get(g)))
			{
				wordcount=g-wordcount;
				if(wordcount>1)
				{
					String tmp=Integer.toString(wordcount);
					Docid_Key_index.addEntry(Integer.toString(current_id), k+":"+tmp);
					Key_Docid_index.addEntry(k,Integer.toString(current_id));
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
		try {
			if(Integer.parseInt(content_length)<1)
				content_length = Integer.toString(crawler.extractString().length());
		} catch (ParserException e) {
			e.printStackTrace();
		}
		Id_ContentLength_index.updateEntry(current_id, content_length);
		String last_modified = info[1];
		Id_LastModified_index.updateEntry(current_id, last_modified);
		
		//Add title word
		int wordcount=0;
		Vector<String> titlewords=null;
		try {
			titlewords = Stop_Stem(title);
		} catch (ParserException e) {
			e.printStackTrace();
		}
		Collections.sort(titlewords);
		String k="";
		if(titlewords.size()!=0)								
			k=titlewords.get(wordcount);
		else
			TitleId_Key_index.updateEntry(current_id,"");
		for (int g = 0; g < titlewords.size(); g++){
			if(!k.equals(titlewords.get(g)))
			{
				wordcount=g-wordcount;
				String tmp=Integer.toString(wordcount);
				TitleId_Key_index.updateEntry(current_id,k+":"+tmp);
				Key_TitleId_index.updateEntry(k,current_id);
				wordcount=g;
				k=titlewords.get(g);
			}
		}
		titlewords.clear();
	}
		
	public void updateContentterm(Crawler crawler, String current_id) throws IOException
	{
		//Docid_Key_index.delValue(current_id);
		//Key_Docid_index.updateKey(current_id);
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
			Docid_Key_index.updateEntry(current_id, "");
		for (int g = 0; g < words.size(); g++){
			if(!k.equals(words.get(g)))
			{
				wordcount=g-wordcount;
				if(wordcount>1)
				{
					String tmp=Integer.toString(wordcount);
					Docid_Key_index.updateEntry(current_id, k+":"+tmp);
					Key_Docid_index.updateEntry(k,current_id);
				}
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
						//launcher.updateTitleterm(crawler, id_temp);
						launcher.updateContentterm(crawler, id_temp);
					}
				}
				else
				{
					launcher.addData(crawler,current_id);
					//launcher.addTitleterm(crawler, current_id);
					launcher.addContentterm(crawler, current_id);
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
									launcher.updateData(crawler, id_temp);
									//launcher.updateTitleterm(crawler, id_temp);
									launcher.updateContentterm(crawler, id_temp);
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
								launcher.addContentterm(crawler, count_url);
                                //launcher.addTitleterm(crawler, count_url);
                                
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