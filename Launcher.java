import java.util.Collections;
import java.util.Vector;
import org.htmlparser.util.ParserException;
import java.io.IOException;

public class Launcher
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
				Id_Url_index.addEntry(Integer.toString(current_id), crawler.getURL());
				Url_Id_index.addEntry(crawler.getURL(), Integer.toString(current_id));
				String title = crawler.extractTitle();
				Id_Title_index.addEntry(Integer.toString(current_id), title);
				String[] info = crawler.extractPageInfo().split(";");
				String content_length = info[0];
				Id_ContentLength_index.addEntry(Integer.toString(current_id), content_length);
				String last_modified = info[1];
				Id_LastModified_index.addEntry(Integer.toString(current_id), last_modified);
				
				//Title Key
				int wordcount=0;
				Vector<String> titlewords = crawler.extractTitleKey();
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
				
				//Content Key
			    wordcount=0;
				Vector<String> words = crawler.extractWords();
				Collections.sort(words);
				k="";
				if(words.size()!=0)								
				k=words.get(wordcount);
				else
				Docid_Key_index.addEntry(Integer.toString(count_url), "");
				for (int g = 0; g < words.size(); g++){
					if(!k.equals(words.get(g)))
					{
					wordcount=g-wordcount;
					String tmp=""+wordcount;
					Docid_Key_index.addEntry(Integer.toString(count_url), k+":"+tmp);
					Key_Docid_index.addEntry(k,Integer.toString(count_url));
					wordcount=g;
					k=words.get(g);
					}
				}
				Constructor.commit();

				while (true){
					if (count_url < Required_Number){
						Vector<String> links = crawler.extractLinks();
						for (int i = 0; i < links.size(); i++){
							String link = links.get(i);
							System.out.println("Processing link:"+link);
							if (Url_Id_index.containsKey(link)){
								/*String id_temp = Url_Id_index.getEntry(link);
								String old_lastmodified = Id_LastModified_index.getEntry(id_temp);

								if (!last_modified.equals(old_lastmodified)){
									System.out.println("Need Update");
								}
								else
									continue;*/
								continue;
							}
							else{
								count_url++;
								if (count_url > Required_Number)
									break;
								System.out.println(count_url);
								String current_url = crawler.getURL();
								crawler.setURL(link);
								Id_Url_index.addEntry(Integer.toString(count_url), link);
								Url_Id_index.addEntry(link, Integer.toString(count_url));
								ChildLink_index.addEntry(Integer.toString(current_id), Integer.toString(count_url));
								ParentLink_index.addEntry(Integer.toString(count_url), Integer.toString(current_id));

								title = crawler.extractTitle();
								Id_Title_index.addEntry(Integer.toString(count_url), title);

								info = crawler.extractPageInfo().split(";");
								content_length = info[0];
								Id_ContentLength_index.addEntry(Integer.toString(count_url), content_length);
								last_modified = info[1];
								Id_LastModified_index.addEntry(Integer.toString(count_url), last_modified);

								//Call function to extract words of each page here
								//This part should be called once before while(true)
								//Content Key
								wordcount=0;
								words = crawler.extractWords();
								Collections.sort(words);
								k="";
								if(words.size()!=0)								
								k=words.get(wordcount);
								else
								Docid_Key_index.addEntry(Integer.toString(count_url), "");
								for (int j = 0; j < words.size(); j++){
									if(!k.equals(words.get(j)))
									{
									wordcount=j-wordcount;
									String tmp=""+wordcount;
									Docid_Key_index.addEntry(Integer.toString(count_url), k+":"+tmp);
									Key_Docid_index.addEntry(words.get(j),Integer.toString(count_url));
									wordcount=j;
									k=words.get(j);
									}
								}
								
								//Title Key
							    wordcount=0;
							    titlewords = crawler.extractTitleKey();
								Collections.sort(titlewords);
							    k="";
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
                                
                         		Constructor.commit();
								crawler.setURL(current_url);
								words.clear();
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

			/*	Id_Url_index.printAll();
				Url_Id_index.printAll();
				Id_Title_index.printAll();
				Id_ContentLength_index.printAll();
				Id_LastModified_index.printAll();
				ChildLink_index.printAll();
				ParentLink_index.printAll();
				Docid_Key_index.printAll();
				Key_Docid_index.printAll();*/
				TitleId_Key_index.printAll();
				Key_TitleId_index.printAll();
				

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