package com.Optimize;




import java.util.HashMap;
import java.util.Map;

/**
 * Representation of a Java class code coverage.
 * @author Lee Clement
 * @author Jonathan Lermitage
 */
public class JavaClass implements Comparable<JavaClass> {

    /** The package name (with "/" instead of "."). */
    private final String packageName;

    /** The class name (with ".java" extension). */
    private final String className;

    /** Indicate the coverage state of class instructions. */
    private final Map<Integer,Integer> coverage = new HashMap<>(256);

    /** Indicate the coverage description of class instructions. */
    private final Map<Integer,String> coverageDesc = new HashMap<>(128);

    /** Indicate the coverage state of class methods declarations. */
    private final Map<Integer,Integer> methodCoverage = new HashMap<>(32);

    /** Number of covered lines. */
    private int nbCoveredLines = 0;

    /** Number of partially covered lines. */
    private int nbPartiallyCoveredLines = 0;

    /** Number of not covered lines. */
    private int nbNotCoveredLines = 0;
    
    private double executionTime=3.0;

    public JavaClass(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
    }

    public void addCoveredLine(int lineNumber) {
        coverage.put(lineNumber,1);
        nbCoveredLines++;
    }

    public void addPartiallyCoveredLine(int lineNumber) {
        coverage.put(lineNumber,0);
        nbPartiallyCoveredLines++;
    }

    public void addNotCoveredLine(int lineNumber) {
        coverage.put(lineNumber,-1);
        nbNotCoveredLines++;
    }

    public void addMethodCoverage(int lineNumber, int coverageState) {
        methodCoverage.put(lineNumber, coverageState);
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public Map<Integer,Integer> getCoverage() {
        return coverage;
    }

    public Map<Integer, String> getCoverageDesc() {
        return coverageDesc;
    }

    public Map<Integer,Integer> getMethodCoverage() {
        return methodCoverage;
    }

    public int getNbCoveredLines() {
        return nbCoveredLines;
    }

    public int getNbPartiallyCoveredLines() {
        return nbPartiallyCoveredLines;
    }

    public int getNbNotCoveredLines() {
        return nbNotCoveredLines;
    }
    
    public int getTotalLines()
    {
    	return this.nbCoveredLines+this.nbNotCoveredLines+this.nbPartiallyCoveredLines;
    }
    public double getExecutionTime()
    {
    	return this.executionTime;
    }
    
    @Override
    public int compareTo(JavaClass o) {
        return (this.getPackageName() + this.getClassName()).compareTo(o.getPackageName() + o.getClassName());
    }
}
