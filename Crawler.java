import java.util.Vector;
import org.htmlparser.beans.StringBean;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import java.util.StringTokenizer;
import org.htmlparser.beans.LinkBean;
import java.net.URL;
import java.io.IOException;

public class Crawler
{
	private static int count;
	private String url;
	Crawler(String _url)
	{
		url = _url;
		count = 0;
	}
	public void setURL(String _url)
	{
		url = _url;
	}
	public String getURL()
	{
		return url;
	}

	public Vector<String> extractWords() throws ParserException
	{
		// extract words in url and return them
		// use StringTokenizer to tokenize the result from StringBean
		// ADD YOUR CODES HERE
		Vector<String> result = new Vector<String>();
		StringBean bean = new StringBean();
		bean.setURL(url);
		bean.setLinks(false);
		String contents = bean.getStrings();
		StringTokenizer st = new StringTokenizer(contents);
		while (st.hasMoreTokens()) {
		    result.add(st.nextToken());
		}
		return result;
	}

	public Vector<String> extractLinks() throws ParserException
	{
		// extract links in url and return them
		// ADD YOUR CODES HERE
		Vector<String> result = new Vector<String>();
		LinkBean bean = new LinkBean();
		bean.setURL(url);
		URL[] urls = bean.getLinks();
		for (URL s : urls) {
		    result.add(s.toString());
		}
		return result;
	}
	
	public static void main (String[] args)
	{
		try
		{
			Crawler crawler = new Crawler("http://www.cs.ust.hk/~dlee/4321/");

			/*
			Vector<String> words = crawler.extractWords();		
			
			System.out.println("Words in "+crawler.url+":");
			for(int i = 0; i < words.size(); i++)
				System.out.print(words.get(i)+" ");
			System.out.println("\n\n");
			*/

			Vector<String> links = crawler.extractLinks();
			System.out.println("Links in "+crawler.url+":");
			for(int i = 0; i < links.size(); i++)		
				System.out.println(links.get(i));
			System.out.println("");

			try
	        {
	        	InvertedIndex Id_Url_index = new InvertedIndex("project","id_url");
	            InvertedIndex ChildLink_index = new InvertedIndex("project","childlink");
	            InvertedIndex ParentLink_index = new InvertedIndex("project","parentlink");

	            int current_id = count;
	            Id_Url_index.addEntry(Integer.toString(current_id), crawler.getURL());
	    		for(int i = 0; i < links.size(); i++){
	    			if(true){ //TODO: detect duplicate
		    			count++;
		    			Id_Url_index.addEntry(Integer.toString(count), links.get(i));
		            	ChildLink_index.addEntry(Integer.toString(current_id), Integer.toString(count));
		            	ParentLink_index.addEntry(Integer.toString(count), Integer.toString(current_id));
	    			}
	    		}

	            Id_Url_index.printAll();
	            Id_Url_index.finalize();
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
	
