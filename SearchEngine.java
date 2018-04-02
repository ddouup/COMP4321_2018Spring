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
import java.text.SimpleDateFormat;
import java.io.IOException;

public class SearchEngine
{	
	private static int Required_Number = 30;
	private static int count;
	private String url;
	public	static Launcher launcher;
	
	SearchEngine() throws IOException
	{
		launcher=new Launcher();
	}

	public int Returntf(String _key,String word) throws IOException
	{
		int a=0;
		String value="";
		value=launcher.Docid_Key_index.getEntry(_key);
        String[] tokens=value.split(":");
        int i=0;
        for(String token:tokens)
        {
        	if(i==1)
        	{
        		try {
        		a = Integer.parseInt(token);
        		} catch (NumberFormatException e) {
        		    e.printStackTrace();
        		}
        		return a;
        	}
        	if(token.equals(word))
        	{
        	  i=1;	
        	}
        }
		return a;
	}
	
	public int Returntfmax(String Word) throws IOException
	{
		int a=0;
		
		return a;
	}
	
	public double Returnidf(String Word) throws IOException
	{
		int a=0;
		String value="";
		value=launcher.Key_Docid_index.getEntry(Word);
        String[] tokens=value.split(";");
        a=tokens.length;
        double idf=Math.log(launcher.getRequiredNumber()/a)/Math.log(2);
		return idf;
	}
	
}