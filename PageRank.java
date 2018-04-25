import java.util.Collections;
import java.util.Vector;
import org.htmlparser.util.ParserException;

import jdbm.helper.FastIterator;

import java.io.IOException;

class Page
{	
	public Int Id;
	public Vector<Page> ChildPages;
	public Vector<Page> ParentPages;
	public float output;
	public float PR;

}

public class PageRank
{	
	public static Launcher launcher;

	PageRank() throws IOException
	{	
		launcher=new Launcher();
	}

	public calculate1(Page page)
	{

		page.output = page.PR/page.ChildPages.size();
		
	}

	public static void main (String[] args) throws IOException, ParserException
	{
		Vector<String> linkIds = Launcher.Id_Url_index.ReturnKey();
		Vector<Page> linkPage = new Vector<Page>();
		String[] childLinks = Launcher.ChildLink_index.getEntry(linkIds[i]).split(";");
		String[] parentLinks = Launcher.ChildLink_index.getEntry(linkIds[i]).split(";");

		
		for (int i = 0; i < linkIds.size(); i++) {
				//TODO: construct page objects
			linkPage[i].Id = linkIds[i]
			linkPage[i].ChildPages = childLinks;
			linkPage[i].ParentPages = parentLinks;
			linkPage[i].PR = 1;

		}
		int 
		int d;
		for (int i = 0; i < 20; i++){
			for (int i = 0; i < linkPage.size(); i++){
				calculate1(linkPage[i]);
			}
			for (int i = 0; i < linkPage.size(); i++){
				float j = new float;
				for (string i : linkPage[i].ParentPages){
					int k = int(i);
					j = j + k;
				}
				linkPage[i].PR = (1-d)+(d*j);
			}
		}


	}

}