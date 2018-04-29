package searchEngine;

import java.io.IOException;

public class HelloWorld {
	public static String name;
	
	HelloWorld() throws IOException
	{
		System.out.println("Hello World");
	}
	
	public static void setName(String newname) {
		name = newname;
	}
	
	public static void getName() {
		System.out.println(name);
	}
}
