package com.Optimize;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVParser {

    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';
    private static ArrayList<Stock> items = new ArrayList<Stock>();
    

    public static void main(String[] args) throws Exception
    {

        String csvFile = "csv/sample.csv";

        Scanner scanner = new Scanner(new File(csvFile));
        while (scanner.hasNext())
        {
            List<String> line = parseLine(scanner.nextLine());
            System.out.println("Date [id= " + line.get(0) 
            + " ,Open= " + line.get(1)
            + " ,High=" + line.get(3) 
            + " ,Low=" + line.get(4) 
            + " ,Close=" + line.get(5) 
            + " ,Adj Close=" + line.get(6) + "");
            
            items.add(new Stock(line.get(0),
            		Double.parseDouble(line.get(1)),
                    Double.parseDouble(line.get(2)),
                    Double.parseDouble(line.get(3)),
                    Double.parseDouble(line.get(4)),
                    Double.parseDouble(line.get(5))));

        }
        scanner.close();

    }

    public static List<String> parseLine(String cvsLine)
    {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators) 
    {
        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators, char customQuote)
    {

        List<String> result = new ArrayList<>();

        //if empty, return!
        if (cvsLine == null && cvsLine.isEmpty()) {
            return result;
        }

        if (customQuote == ' ') {
            customQuote = DEFAULT_QUOTE;
        }

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = cvsLine.toCharArray();

        for (char ch : chars) {

            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }

                }
            } else {
                if (ch == customQuote) {

                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"') {
                        curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }

                } else if (ch == separators) {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    startCollectChar = false;

                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }

        }

        result.add(curVal.toString());

        return result;
    }

}