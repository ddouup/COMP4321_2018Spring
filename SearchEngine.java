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
	public	static Launcher launcher;
	public static Vector<Integer> DocID;
	public static Vector<DocCom> Doc;
	
	SearchEngine() throws IOException
	{
		launcher=new Launcher();
		DocID=new Vector<Integer>();
		Doc=new Vector<DocCom>();
	}
	
	public void Sort(Vector<String> result) throws IOException
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
        	}
        }
	}
	
	public void Cal()
	{
		
	}
	
	public Vector<Integer> ranksort(Vector<String> result) throws IOException
	{
		
		
		//Output ranking
		/*Vector<String> tmpweight=new Vector<String>();
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
		
		double [][] array=new double [result.size()][launcher.getRequiredNumber()+1];
		
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
	       	 for(String t:tmp)
	       	 {
	       		 if(m==true)
	       		 {
	       		   array[i][docID]=Double.parseDouble(t);
	       		 }
	       		 if(m==false)
	       		 {
	       		 docID=Integer.parseInt(t);
	       		 if(!DocID.contains(docID))
	       		 DocID.add(docID);
	       		 m=true;
	       		 } 
	       	 }
	         }
		}
		}
		
		double [][] arr=new double [1][launcher.getRequiredNumber()+1];
		//Vector<Double> value=new Vector<Double>();
		for(int i=0;i<DocID.size();i++)
		{
			Double vecsize=0.0;
			Double quesize=0.0;
			Double product=0.0;
		  for(int j=0;j<result.size();i++)
		  {
			  vecsize=vecsize+array[j][i]*array[j][i];
			  product=product+array[j][i];
		  }
		  Double cossin=(product)/(Math.sqrt(vecsize)*Math.sqrt(result.size()));
		  arr[1][DocID.get(i)]=cossin;
		}
		*/
		return null;
	}
	
	public Vector<Integer> phraseranksort(Vector<String> result) throws IOException
	{
		//Output ranking
        return null;
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

	public Vector<PageList> search(String query) throws IOException
	{
		//Phrase Search, Query Process
		StopStem stopStem = new StopStem("stopwords.txt");
		Vector<PageList> list= new Vector<PageList>();
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
		
		Vector<Integer> rank=new Vector<Integer>();
		if(phrasequery(query))
		rank=phraseranksort(result);
		else
		rank=ranksort(result);
		
		for(int i=0;i<rank.size();i++)
		{   
			PageList page=new PageList();
			page.url=launcher.Id_Url_index.getEntry(String.valueOf(rank.get(i)));
			page.title=launcher.Id_Title_index.getEntry(String.valueOf(rank.get(i)));
			page.key=launcher.Docid_Key_index.getEntry(String.valueOf(rank.get(i)));
			page.childlink=launcher.ChildLink_index.getEntry(String.valueOf(rank.get(i)));
			page.parentlink=launcher.ParentLink_index.getEntry(String.valueOf(rank.get(i)));
			page.datesizeofpage=launcher.Id_LastModified_index.getEntry(String.valueOf(rank.get(i)))+","+launcher.Id_ContentLength_index.getEntry(String.valueOf(rank.get(i)));
			list.add(page);
		}
		return list;
	}
}