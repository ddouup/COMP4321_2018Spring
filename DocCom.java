import java.io.IOException;
import java.util.Vector;

public class DocCom {
	public Vector<String> word;
	public Vector<Double> wordw;
	public Vector<Double> weight;
	public Double cossin;
	public int id;
	public double sqrtw;
	public double sqrtt;
	public double addwt;
	DocCom() throws IOException
	{
		id=0;
		word=new Vector<String>();
		wordw=new Vector<Double>();
		weight=new Vector<Double>();
		cossin=0.0;
		sqrtw=0.0;
		sqrtt=0.0;
		addwt=0.0;
	}
	
	public void CalCos()
	{
		for(int i=0;i<word.size();i++)
		{
		   sqrtw=sqrtw+weight.get(i);
		   sqrtt=sqrtt+wordw.get(i);
		   addwt=addwt+weight.get(i)*wordw.get(i);
		}
		cossin=(addwt)/(Math.sqrt(sqrtw)*Math.sqrt(sqrtt));
	}

}
