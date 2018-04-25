import java.util.Vector;
import java.util.Comparator;
import java.util.Collections;  
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
	public	static Launcher launcher;
	public static Vector<Integer> DocID;
	public static Vector<DocCom> Doc;
	public static Vector<Double> wei;
	
	SearchEngine() throws IOException
	{
		launcher=new Launcher();
		DocID=new Vector<Integer>();
		Doc=new Vector<DocCom>();
		wei=new Vector<Double>();
	}
	
    static class CosComparator implements Comparator {  
        public int compare(Object obj1, Object obj2) { 
             DocCom D1=(DocCom)obj1;
             DocCom D2=(DocCom)obj2;
            return new Double(D1.cossin).compareTo(new Double(D2.cossin));  
        }  
    }  
	
	public void Sort(Vector<String> result,Vector<Double> wei) throws IOException
	{
		Vector<String> tmpweight=new Vector<String>();
		for(int i=0;i<result.size();i++)
		{
			String tmp=launcher.Key_Weight_index.getEntry(result.get(i));
			if(tmp==null)
			{
				tmpweight.add("0");
			}
			else
			{
			tmpweight.add(tmp);
			}
		}
		
        update(result,wei,tmpweight);
		
		for(int i=0;i<Doc.size();i++)
		{
			Doc.get(i).CalCos();
		}
		Collections.sort(Doc, new CosComparator());
	}
	
	
	public void phraseSort(Vector<String> result,Vector<Double> wei) throws IOException
	{
		Vector<String> tmpweight=new Vector<String>();
		for(int i=0;i<result.size();i++)
		{
			String tmp=launcher.Phrase_Weight_index.getEntry(result.get(i));
			if(tmp==null)
			{
				tmpweight.add("0");
			}
			else
			{
			tmpweight.add(tmp);
			}
		}
		
        update(result,wei,tmpweight);
		
		for(int i=0;i<Doc.size();i++)
		{
			Doc.get(i).CalCos();
		}
		Collections.sort(Doc, new CosComparator());
	}
	
	
	public void update(Vector<String> result,Vector<Double> wei,Vector<String> tmpweight) throws IOException
	{
	
		for(int i=0;i<result.size();i++)
		{
			String value=tmpweight.get(i);
			if(!value.equals("0"))
			{
			String[] tokens=value.split(";");
	         for(String token:tokens)
	         {
	       	 String[] tmp=token.split(",");
	       	 boolean m=false;
	       	 int docID=0;
	       	 DocCom dd=new DocCom();
	       	 dd.word.add(result.get(i));
	       	 dd.wordw.add(wei.get(i));
	       	 for(String t:tmp)
	       	 {
	       		 if(m==true)
	       		 {
	       		   dd.weight.add(Double.parseDouble(t));
	       		 }
	       		 if(m==false)
	       		 {
	       		 docID=Integer.parseInt(t);
	       		 dd.id=Integer.parseInt(t);
	       		 m=true;
	       		 if(!DocID.contains(docID))
	       		 {
	       			 DocID.add(docID);
	       		 }
	       		 }
	       		  
	       	 }
       		 if(!DocID.contains(docID))
	       	 Doc.add(dd);
       		 else
       		 {
       		 UpdateDoc(dd); 
       		 }
	         }
		}
		}
	}
	
	public void UpdateDoc(DocCom dd) throws IOException
	{
        for(int i=0;i<Doc.size();i++)
        {
        	DocCom odd=Doc.get(i);
        	if(odd.id==dd.id)
        	{
        		Doc.get(i).weight.add(dd.weight.get(0));
        		Doc.get(i).word.add(dd.word.get(0));
        		Doc.get(i).wordw.add(dd.wordw.get(0));
        	}
        }
	}
	
    public boolean phrasequery(String query)
    {
    	int result=query.indexOf("\"");
    	if(result==-1)
    		return false;
    	String substr=query.substring(result);
    	result=substr.indexOf("\"");
    	if(result==-1)
    		return false;
    	return true;
    }
    
    public Vector<String> queryprocess(String query)
    {
    	//update wei
    	StopStem stopStem = new StopStem("stopwords.txt");
		Vector<String> result=new Vector<String>();
		StringTokenizer st = new StringTokenizer(query);
		while (st.hasMoreTokens()) 
		{
			String word = st.nextToken();
			if (!stopStem.isStopWord(word))
			{
				word = stopStem.stem(word);
				boolean isWord=word.matches("^[A-Za-z0-9]+");
				if(isWord)
				{
					if(!result.contains(word))
				    result.add(word);
				}
			}
		}
		return result;
    }

	public Vector<PageList> search(String query) throws IOException
	{
		//Phrase Search, Query Process
		Vector<PageList> list= new Vector<PageList>();
		Vector<String> result=new Vector<String>();
        result=queryprocess(query);
		
		if(phrasequery(query))
		phraseSort(result,wei);
		else
		Sort(result,wei);
		
		for(int i=0;i<Doc.size();i++)
		{   
			PageList page=new PageList();
			page.url=launcher.Id_Url_index.getEntry(String.valueOf(Doc.get(i).id));
			page.title=launcher.Id_Title_index.getEntry(String.valueOf(Doc.get(i).id));
			page.key=launcher.Docid_Key_index.getEntry(String.valueOf(Doc.get(i).id));
			page.childlink=launcher.ChildLink_index.getEntry(String.valueOf(Doc.get(i).id));
			page.parentlink=launcher.ParentLink_index.getEntry(String.valueOf(Doc.get(i).id));
			page.datesizeofpage=launcher.Id_LastModified_index.getEntry(String.valueOf(Doc.get(i).id))+","+launcher.Id_ContentLength_index.getEntry(String.valueOf(Doc.get(i).id));
			list.add(page);
		}
		return list;
	}
}