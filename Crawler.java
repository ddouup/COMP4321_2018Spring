import java.util.Vector;
//import java.util.List;
//import java.util.Map;
import java.util.Date;
import org.htmlparser.beans.StringBean;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import java.util.StringTokenizer;
import org.htmlparser.beans.LinkBean;
import org.htmlparser.beans.FilterBean;
import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

public class Crawler
{	
	private static int Required_Number = 30;
	private static int count;
	private String url;
	Crawler(String _url)
	{
		url = _url;
		count = 1;
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
		// use stopStem to remove stop words and remove

		StopStem stopStem = new StopStem("stopwords.txt");
		Vector<String> result = new Vector<String>();
		StringBean bean = new StringBean();
		bean.setURL(url);
		bean.setLinks(false);
		String contents = bean.getStrings();
		StringTokenizer st = new StringTokenizer(contents);
		while (st.hasMoreTokens()) {
			String word = st.nextToken();
			if (!stopStem.isStopWord(word)){
				word = stopStem.stem(word);
				result.add(word);
			}
		}

		//test only
		/*System.out.println("Words in " + url + ":");
		for(int i = 0; i < result.size(); i++)
			System.out.print(result.get(i)+" ");
		System.out.println("");*/

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

		//test only
		System.out.println("Links in " + url + ":");
		for(int i = 0; i < result.size(); i++)		
			System.out.println(result.get(i));
		System.out.println("");

		return result;
	}
	
	public String extractPageInfo()
	{
		try
		{
			URL obj = new URL(url);
			URLConnection conn = obj.openConnection();
			/*
			Map<String, List<String>> map = conn.getHeaderFields();
			System.out.println("Printing Response Header...\n");

			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				System.out.println(entry.getKey() + ": " + entry.getValue());
			}
			String result = "success";
			*/
			
			long Content_Length = conn.getContentLengthLong();
			System.out.println("Content_Length: " + Content_Length);

			long Last_Modified = conn.getLastModified();
			Date Last_Modified_Date = new Date(Last_Modified);
			System.out.println("Last_Modified_Date: " + Last_Modified_Date);
			
			System.out.println("");
			String result = Long.toString(Content_Length) + ";" + Long.toString(Last_Modified);

	    	return result;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return "error";
	    }
	}

	public String extractTitle() throws ParserException
	{
		
		FilterBean bean = new FilterBean();
		bean.setURL(url);

		NodeFilter filter1 = new TagNameFilter("title");
		NodeFilter[] filters = {filter1};
		bean.setFilters(filters);
		NodeList nodes = bean.getNodes();

		String title = "";
		if (nodes.size() != 0)
			title = nodes.elementAt(0).toString();
		//test only
		System.out.println(title);
		System.out.println("");
		return title;
	}
}