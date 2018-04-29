import java.util.Collections;
import java.util.Vector;
import org.htmlparser.util.ParserException;

import jdbm.helper.FastIterator;

import java.io.IOException;

class Page
{	
	public String Id;
	public Vector<String> ChildPages;
	public Vector<String> ParentPages;
	public double output;
	public double PR;

}

public class PageRank
{	
	private static InvertedIndex Constructor;
	public	static InvertedIndex Id_Url_index;
	public	static InvertedIndex ChildLink_index;
	public	static InvertedIndex ParentLink_index;
	public static InvertedIndex Id_PageRank_index;

	PageRank() throws IOException
	{	
		Constructor = new InvertedIndex("project");
		Id_Url_index = new InvertedIndex("project","id_url");
		ChildLink_index = new InvertedIndex("project","childlink");
		ParentLink_index = new InvertedIndex("project","parentlink");
		Id_PageRank_index = new InvertedIndex("project","pagerank");
	}

	public static void calculate1(Page page)
	{

		page.output = page.PR/page.ChildPages.size();

		
	}

	public static void main (String[] args) throws IOException, ParserException
	{
		PageRank pageRank = new PageRank();
		Vector<String> linkIds = Id_Url_index.ReturnKey();
		Vector<Page> linkPage = new Vector<Page>();

		
		for (int i = 0; i < linkIds.size(); i++) {
			System.out.println(linkIds.get(i+1));
			String[] childLinks = ChildLink_index.getEntry(linkIds.get(i+1)).split(";");
			String[] parentLinks = ParentLink_index.getEntry(linkIds.get(i+1)).split(";");

			Page page = new Page();
			Vector temp = new Vector();
			System.out.println(linkIds.get(i+1));
			page.Id = linkIds.get(i+1);
			//linkPage.get(i).Id = linkIds.get(i);

			//for (int k = 0; k < childLinks.length; k++){
				//System.out.println(childLinks[k]);
				//System.out.println(childLinks[k].getClass().getSimpleName());
				//temp.addElement(childLinks[k]);
				//page.ChildPages.addElement(childLinks[k]);
			//}
			//for (int k = 0; k < parentLinks.length; k++){
				//page.ParentPages.addElement(parentLinks[k]);
			//}
			page.PR = 1;
			linkPage.addElement(page);
		}

		double d = 0.5; //determined d value
		int iteration_times = 1; //determined iteration times

		for (int i = 0; i < iteration_times; i++){
			for (int t = 0; t < linkPage.size(); t++){
				calculate1(linkPage.get(t));
			}
			for (int t = 0; t < linkPage.size(); t++){
				double j = 0;
				for (String temp : linkPage.get(t).ParentPages){
					int k = Integer.parseInt(temp);
					j = j + k;
				}
				linkPage.get(t).PR = (1-d)+(d*j);
			}
		}
		for (int i =0; i < linkIds.size();i++){
			System.out.println(linkPage.get(i).Id);
			System.out.println(linkPage.get(i).output);
			System.out.println(linkPage.get(i).PR);
		
		}
		
		Constructor.finalization();
	}

}