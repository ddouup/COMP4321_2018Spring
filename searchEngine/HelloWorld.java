package searchEngine;

import java.io.IOException;

public class HelloWorld {
	public static String name="dou";
	
	public HelloWorld() throws IOException
	{
		System.out.println("Hello World");
	}
	
	public static void setName(String newname) {
		name = newname;
	}
	
	public static String getName() {
		return name;
	}
}
