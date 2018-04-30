package searchEngine;

import java.util.Vector;

import searchEngine.Launcher;

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
	public boolean same;
	
	public DocCom() throws IOException
	{
		same=false;
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

public class SearchEngine
{	
	public static Launcher launcher;
	public static Vector<Integer> DocID;
	public static Vector<Integer> TitID;
	public static Vector<DocCom> Doc;
	public static Vector<DocCom> Tit;
	public static Vector<Integer> wei;
	public static double sqrtt;
	
	public SearchEngine() throws IOException
	{
		launcher=new Launcher();
		DocID=new Vector<Integer>();
		TitID=new Vector<Integer>();
		Doc=new Vector<DocCom>();
		wei=new Vector<Integer>();
		sqrtt=0.0;
	}
	
    static class CosComparator implements Comparator {  
        public int compare(Object obj1, Object obj2) { 
             DocCom D1=(DocCom)obj1;
             DocCom D2=(DocCom)obj2;
            return new Double(D2.cossin).compareTo(new Double(D1.cossin));  
        }  
    }  
	
	public void Sort(Vector<String> result,Vector<Integer> wei) throws IOException
	{
		Vector<String> tmpweight=new Vector<String>();
		Vector<String> tmpweightT=new Vector<String>();
		
		for(int i=0;i<result.size();i++)
		{
			System.out.println("Word: "+result.get(i));
			String tmp=launcher.Key_Weight_index.getEntry(result.get(i));
			String tmpT=launcher.TitlePhrase_Weight_index.getEntry(result.get(i));
			if(tmp==null)
			{
				tmpweight.add("0");
			}
			else
			{
				System.out.println("Key weight(body): "+tmp);
				tmpweight.add(tmp);
			}
			if(tmpT==null)
			{
				tmpweightT.add("0");
			}
			else
			{
				System.out.println("Key weight(title): "+tmp);
				tmpweightT.add(tmpT);
			}
			
		}
		
        update(result,wei,tmpweight,tmpweightT);
		
		for(int i=0;i<Doc.size();i++)
		{
			Doc.get(i).CalCos(sqrtt);
			//System.out.println(Doc.get(i).cossin);
			//Tit.get(i).CalCos(sqrtt);
		}
		/*
        	for(int i=0;i<Tit.size();i++)
        	{
        		for(int j=0;j<Doc.size();j++)
        		{
        			if(Doc.get(j).id==Tit.get(i).id)
        				{
        				  Doc.get(j).cossin=Tit.get(i).cossin+Doc.get(j).cossin;
        				  Tit.get(i).same=true;
        				}
        		}
        	}
        	
        	for(int i=0;i<Tit.size();i++)
        	{
        		if(!Tit.get(i).same)
        		{
        			Doc.add(Tit.get(i));
        		}
        	}*/

		Collections.sort(Doc, new CosComparator());
	}
	/*
	public boolean containsPhrase(String id, String phrase)
	{
		
	}*/
	
	public void update(Vector<String> result,Vector<Integer> wei,Vector<String> tmpweight,Vector<String> tmpweightT) throws IOException
	{
	
		for(int i=0;i<result.size();i++)
		{
			String value=tmpweight.get(i);
			String valueT=tmpweightT.get(i);
			if(!value.equals("0"))
			{
				String[] tokens=value.split(";");
		         for(String token:tokens)
		         {
			       	 String[] tmp=token.split(",");
			       	 int docID=0;
			       	 DocCom dd=new DocCom();
			       	 dd.word.add(result.get(i));
			       	 dd.wordw.add(wei.get(i));
			       	 
			       	docID=Integer.parseInt(tmp[0]);
		       		dd.id=Integer.parseInt(tmp[0]);
		       		dd.weight.add(Double.parseDouble(tmp[1]));
		       		
		       		if(!DocID.contains(docID))
		       		{
		       			DocID.add(docID);
		       			Doc.add(dd);
		       		}
		       		 else
		       			 UpdateDoc(dd); 
		         }
			}/*
			if(!valueT.equals("0"))
			{
				String[] tokens=valueT.split(";");
		        for(String token:tokens)
		        {
			       	 String[] tmp=token.split(",");
			       	 boolean m=false;
			       	 int titID=0;
			       	 DocCom dd=new DocCom();
			       	 dd.word.add(result.get(i));
			       	 dd.wordw.add(wei.get(i));
			       	
	       			titID=Integer.parseInt(tmp[0]);
	       			 dd.id=Integer.parseInt(tmp[0]);
		       		dd.weight.add(Double.parseDouble(tmp[1]));
	       			 if(!TitID.contains(titID))
	       			 {
	       				TitID.add(titID);
	       				System.out.println("Title ID: "+titID);
			        	Tit.add(dd);
	       			 }
			       	else
			       		UpdateDoc(dd);
			    }
			}*/
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
    
    public Vector<String> updateterwei(Vector<String> result)
    {
    	Vector<String> new_result = new Vector<String>();
		Collections.sort(result);
		System.out.println(result);
		String k="";
		int wordcount=0;
		if(result.size()!=0)								
		{
			k=result.get(wordcount);
			new_result.add(k);
			System.out.println("Word: "+k);
			for (int g = 0; g < result.size(); g++)
			{
				if(!k.equals(result.get(g)))
				{
					wordcount=g-wordcount;
					System.out.println("Weight: "+wordcount);
					wei.add(wordcount);
					sqrtt=sqrtt+wordcount;
					k=result.get(g);
					wordcount=g;
					new_result.add(k);
					System.out.println("Word: "+k);
				}
				if(g == result.size()-1)
				{
					wordcount=g-wordcount+1;
					System.out.println("Weight: "+wordcount);
					wei.add(wordcount);
					sqrtt=sqrtt+wordcount;
				}
			}
		}
		return new_result;
    }
    
    

	public Vector<PageList> search(String query) throws IOException
	{
		//Query Process
		Vector<PageList> list= new Vector<PageList>();
		Vector<String> result=new Vector<String>();
        result=queryprocess(query);
        result=updateterwei(result);
		
		Sort(result,wei);
		
		for(int i=0;i<Doc.size();i++)
		{   
			if(i>50)
				break;
			PageList page=new PageList();
			page.url=launcher.Id_Url_index.getEntry(String.valueOf(Doc.get(i).id));
			page.title=launcher.Id_Title_index.getEntry(String.valueOf(Doc.get(i).id));
			String tmp=launcher.Docid_SortKey_index.getEntry(String.valueOf(Doc.get(i).id));
			if(tmp!=null)
			{
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
			}
			String urls="";
			tmp=launcher.ChildLink_index.getEntry(String.valueOf(Doc.get(i).id));
			if(tmp!=null)
			{
				String []tokens=tmp.split(";");
				for(String token:tokens)
				{
					String url="";
					url=launcher.Id_Url_index.getEntry(token);
					urls=urls+url+"<br>";
				}
				page.childlink=urls;
			}
			tmp=launcher.ParentLink_index.getEntry(String.valueOf(Doc.get(i).id));
			if(tmp!=null)
			{
				String[] tokens=tmp.split(";");
				for(String token:tokens)
				{
					String url="";
					url=launcher.Id_Url_index.getEntry(token);
					urls=urls+url+"<br>";
				}
				page.parentlink=urls;
			}
			page.datesizeofpage=launcher.Id_LastModified_index.getEntry(String.valueOf(Doc.get(i).id))+","+launcher.Id_ContentLength_index.getEntry(String.valueOf(Doc.get(i).id));
			page.score=Doc.get(i).cossin;
			list.add(page);
		}
		
		return list;
	}
	
	public static void main (String[] args) throws IOException
	{
		SearchEngine searchEngine = new SearchEngine();
		Vector<PageList> result = searchEngine.search("alumni hkust hkust");
		for(int i = 0; i < result.size(); i++)
		{	
			System.out.println("Score: "+result.get(i).score);
			System.out.println(result.get(i).title);
			System.out.println(result.get(i).url);
			System.out.println(result.get(i).key);
			System.out.println(result.get(i).datesizeofpage);
			System.out.println("P_link: "+result.get(i).parentlink);
			System.out.println("C_link: "+result.get(i).childlink);
			//System.out.println("");
		}
	}
}

