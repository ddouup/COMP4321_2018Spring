import java.util.Vector;
import java.util.Comparator;
import java.io.IOException;
import java.util.Collections;  
import java.util.StringTokenizer;


 class DocCom {
	public Vector<String> word;
	public Vector<Integer> wordw;
	public Vector<Double> weight;
	public Double cossin;
	public int id;
	public double sqrtw;
	public double addwt;
	DocCom() throws IOException
	{
		id=0;
		word=new Vector<String>();
		wordw=new Vector<Integer>();
		weight=new Vector<Double>();
		cossin=0.0;
		sqrtw=0.0;
		addwt=0.0;
	}
	
	public void CalCos(double sqrtt)
	{
		for(int i=0;i<word.size();i++)
		{
		   sqrtw=sqrtw+weight.get(i);
		   addwt=addwt+weight.get(i)*wordw.get(i);
		}
		cossin=(addwt)/(Math.sqrt(sqrtw)*Math.sqrt(sqrtt));
	}

}
 

class PageList {
	public String title;
	public String url;
	public String key;
	public String datesizeofpage;
	public String parentlink;
	public String childlink;
	public double score;
	
	PageList ()
	{
		 score=0;
		 String title="";
		 String url="";
		 String key="";
		 String datesizeofpage="";
		 String parentlink="";
		 String childlink="";
	}

}

public class SearchEngine
{	
	public static Launcher launcher;
	public static Vector<Integer> DocID;
	public static Vector<DocCom> Doc;
	public static Vector<Integer> wei;
	public static double sqrtt;
	
	SearchEngine() throws IOException
	{
		launcher=new Launcher();
		DocID=new Vector<Integer>();
		Doc=new Vector<DocCom>();
		wei=new Vector<Integer>();
		sqrtt=0.0;
	}
	
    static class CosComparator implements Comparator {  
        public int compare(Object obj1, Object obj2) { 
             DocCom D1=(DocCom)obj1;
             DocCom D2=(DocCom)obj2;
            return new Double(D1.cossin).compareTo(new Double(D2.cossin));  
        }  
    }  
	
	public void Sort(Vector<String> result,Vector<Integer> wei) throws IOException
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
			Doc.get(i).CalCos(sqrtt);
		}
		Collections.sort(Doc, new CosComparator());
	}
	
	public void update(Vector<String> result,Vector<Integer> wei,Vector<String> tmpweight) throws IOException
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
	
    public int phrasequery(String query)
    {
    	int result=query.indexOf("\"");
    	if(result==-1)
    		return -1;
    	String substr=query.substring(result+1);
    	result=substr.indexOf("\"");
    	if(result==-1)
    		return -1;
    	return result;
    }
    
    public Vector<String> queryprocess(String query)
    {
    	//update wei
    	StopStem stopStem = new StopStem("stopwords.txt");
		Vector<String> result=new Vector<String>();
		StringTokenizer st = new StringTokenizer(query);
		int endindex=phrasequery(query);
		if(endindex==-1)
		{
		while (st.hasMoreTokens()) 
		{
			String word = st.nextToken();
			if (!stopStem.isStopWord(word))
			{
				word = stopStem.stem(word);
				boolean isWord=word.matches("^[A-Za-z0-9]+");
				if(isWord)
				{
				    result.add(word);
				}
			}
		}
		}
		else
		{
			String[] tokens=query.split("\"");
            for(String token:tokens)
            {
    			if (!stopStem.isStopWord(token))
    			{
    				token = stopStem.stem(token);
    				boolean isWord=token.matches("^[A-Za-z0-9]+");
    				if(isWord)
    				{
    				    result.add(token);
    				}
    			}
            }
		}
		return result;
    }
    
    public void updateterwei(Vector<String> result)
    {
    	
		Collections.sort(result);
		String k="";
		int wordcount=0;
		if(result.size()!=0)								
		{
		k=result.get(wordcount);
		for (int g = 0; g < result.size(); g++)
		{
			if(!k.equals(result.get(g)))
			{
			wordcount=g-wordcount;
			wei.add(wordcount);
			sqrtt=sqrtt+wordcount;
			k=result.get(g);
			wordcount=g;
			}
		}
		}
    }
    
    

	public Vector<PageList> search(String query) throws IOException
	{
		//Query Process
		Vector<PageList> list= new Vector<PageList>();
		Vector<String> result=new Vector<String>();
        result=queryprocess(query);
        updateterwei(result);
		
		Sort(result,wei);
		
		for(int i=0;i<Doc.size();i++)
		{   
			if(i>50)
				break;
			PageList page=new PageList();
			page.url=launcher.Id_Url_index.getEntry(String.valueOf(Doc.get(i).id));
			page.title=launcher.Id_Title_index.getEntry(String.valueOf(Doc.get(i).id));
			String tmp=launcher.Docid_SortKey_index.getEntry(String.valueOf(Doc.get(i).id));
			String []tokens=tmp.split(";");
			int j=0;
			for(String token:tokens)
			{
				tmp=tmp+token+";";
				j++;
				if(j>4)
				break;
			}
			page.key=tmp;
			tmp=launcher.ChildLink_index.getEntry(String.valueOf(Doc.get(i).id));
			tokens=tmp.split(";");
			for(String token:tokens)
			{
				String url="";
				url=launcher.Id_Url_index.getEntry(token);
				tmp=tmp+url+"\n";
			}
			page.childlink=tmp;
			tmp=launcher.ParentLink_index.getEntry(String.valueOf(Doc.get(i).id));
			tokens=tmp.split(";");
			for(String token:tokens)
			{
				String url="";
				url=launcher.Id_Url_index.getEntry(token);
				tmp=tmp+url+"\n";
			}
			page.parentlink=tmp;
			page.datesizeofpage=launcher.Id_LastModified_index.getEntry(String.valueOf(Doc.get(i).id))+","+launcher.Id_ContentLength_index.getEntry(String.valueOf(Doc.get(i).id));
			page.score=Doc.get(i).cossin;
			list.add(page);
		}
		
		return list;
	}
}