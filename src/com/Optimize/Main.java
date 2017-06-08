package com.Optimize;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Main
{
	public static void main(String [] args)
	{
		String fname =Constants.FOLDER+Constants.UNIX_SEPERATOR+Constants.FILENAME;
	File report = new File(fname);
		try 
		{
			LinkedHashMap<String, JavaClass> classMap= (LinkedHashMap) CoverageReportParser.getCoverageData(report);
			printClassData(classMap);
		}
		catch (ParserConfigurationException | SAXException | IOException e)
		{
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	public static void printClassData(Map<String,JavaClass>map)
	{
	    Iterator<?> it = map.entrySet().iterator();
	    while (it.hasNext())
	    {
	        Map.Entry pair = (Map.Entry)it.next();
	        JavaClass jc = (JavaClass) pair.getValue();
	        System.out.println(pair.getKey());
	        System.out.println("Line Coverage:"+ jc.getCoverage().entrySet());
	        System.out.println("Method Coverage:"+ jc.getMethodCoverage().entrySet() +"\n");
	       // it.remove(); // avoids a ConcurrentModificationException
	    }
	}
	
	
	

}
