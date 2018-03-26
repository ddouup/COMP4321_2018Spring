import java.util.Vector;
import org.htmlparser.util.ParserException;
import java.io.IOException;

public class Launcher
{	
	private static int Required_Number = 30;
	private static int count;
	Launcher(){}
	public static void main (String[] args)
	{	
		count = 1;
		try
		{
			Crawler crawler = new Crawler("http://www.cse.ust.hk/");
					
			try
			{
				InvertedIndex Id_Url_index = new InvertedIndex("project","id_url");
				InvertedIndex Url_Id_index = new InvertedIndex("project","url_id");
				InvertedIndex Id_Title_index = new InvertedIndex("project","id_title");
				InvertedIndex Id_ContentLength_index = new InvertedIndex("project","id_contentlength");
				InvertedIndex Id_LastModified_index = new InvertedIndex("project","id_lastmodified");
				InvertedIndex ChildLink_index = new InvertedIndex("project","childlink");
				InvertedIndex ParentLink_index = new InvertedIndex("project","parentlink");

				int current_id = count;
				Id_Url_index.addEntry(Integer.toString(current_id), crawler.getURL());
				Url_Id_index.addEntry(crawler.getURL(), Integer.toString(current_id));
				String title = crawler.extractTitle();
				Id_Title_index.addEntry(Integer.toString(current_id), title);
				String[] info = crawler.extractPageInfo().split(";");
				String content_length = info[0];
				Id_ContentLength_index.addEntry(Integer.toString(current_id), content_length);
				String last_modified = info[1];
				Id_LastModified_index.addEntry(Integer.toString(current_id), last_modified);
				Id_Url_index.commit();
				Url_Id_index.commit();
				Id_Title_index.commit();
				Id_ContentLength_index.commit();
				Id_LastModified_index.commit();
				Id_Url_index.printAll();
				Url_Id_index.printAll();
				
				while (true){
					if (count < Required_Number){
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
								count++;
								System.out.println(count);
								if (count > Required_Number)
									break;
								String current_url = crawler.getURL();
								crawler.setURL(link);
								Id_Url_index.addEntry(Integer.toString(count), link);
								Url_Id_index.addEntry(link, Integer.toString(count));
								ChildLink_index.addEntry(Integer.toString(current_id), Integer.toString(count));
								ParentLink_index.addEntry(Integer.toString(count), Integer.toString(current_id));

								title = crawler.extractTitle();
								Id_Title_index.addEntry(Integer.toString(count), title);

								info = crawler.extractPageInfo().split(";");
								content_length = info[0];
								Id_ContentLength_index.addEntry(Integer.toString(count), content_length);
								last_modified = info[1];
								Id_LastModified_index.addEntry(Integer.toString(count), last_modified);

								//Call function to extract words of each page here
								//TODO:
								//Vector<String> words = crawler.extractWords();
								Id_Url_index.commit();
								Url_Id_index.commit();
								Id_Title_index.commit();
								Id_ContentLength_index.commit();
								Id_LastModified_index.commit();
								ChildLink_index.commit();
								ParentLink_index.commit();

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
				Id_Url_index.printAll();
				Id_Url_index.finalize();
				Url_Id_index.printAll();
				Url_Id_index.finalize();
				Id_Title_index.printAll();
				Id_Title_index.finalize();
				Id_ContentLength_index.printAll();
				Id_ContentLength_index.finalize();
				Id_LastModified_index.printAll();
				Id_LastModified_index.finalize();
				ChildLink_index.printAll();
				ChildLink_index.finalize();
				ParentLink_index.printAll();
				ParentLink_index.finalize();
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