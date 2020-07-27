
// Package
///////////////
package de.mpii.rdf3x;

// Imports
///////////////
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;



/**
 *
 * @author Konstantinos Makris <makris@ced.tuc.gr>
 */
public class Tester {
    // Constants
    //////////////////////////////////

    // Static variables
    //////////////////////////////////
    
    // Instance variables
    //////////////////////////////////
    
    // Constructors
    //////////////////////////////////
    
    // External signature methods
    //////////////////////////////////
    public static void main(String[] args) throws IOException, InterruptedException, SQLException {    
        long startTime = 0;    
        long elapsedTime = 0;
       // String query = readFileAsString("/home/kostas/Documents/netbeans.projects/rdf3x-test-app/files/queries/dblp.expanded.query.Q8");
        
        String A1 = "select ?GivenName ?FamilyName where {?p <http://yago-knowledge.org/resource/hasGivenName> ?GivenName. ?p <http://yago-knowledge.org/resource/hasFamilyName> ?FamilyName. ?p <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?scientist. ?scientist <http://www.w3.org/2000/01/rdf-schema#label> \"scientist\". ?p <http://yago-knowledge.org/resource/wasBornIn> ?city. ?city <http://yago-knowledge.org/resource/isLocatedIn> ?switzerland.  ?p <http://yago-knowledge.org/resource/hasAcademicAdvisor> ?a. ?a <http://yago-knowledge.org/resource/wasBornIn> ?city2. ?city2 <http://yago-knowledge.org/resource/isLocatedIn> ?germany.}";
        String A2 = "";        
        String A3 = "select distinct ?n ?co where {?p <isCalled> ?n. {?p <type> \"actor\"} "+
                    "union {?p <type> \"athlete\"} ?p <bornInLocation> ?c. ?c <locatedIn> ?s. ?s "+
                    "<locatedIn> ?co. ?p <type> ?t. filter(?t <subClassOf> \"politician\")}";
        String B1 = "select distinct ?n1 ?n2 where {?a1 <isCalled> ?n1; <livesIn> ?c1;"+
                    " <actedIn> ?movie. ?a2 <isCalled> ?n2; <livesIn> ?c2; <actedIn> ?movie. ?c1"+
                    " <locatedIn> \"England\". ?c2 <locatedIn> \"England\". filter (?a1 != ?a2)}";        
        String B2 = "select ?n1 ?n2 where {?p1 <isCalled> ?n1; <bornInLocation> ?city;"+
                    " <isMarriedTo> ?p2. ?p2 <isCalled> ?n2; <bornInLocation> ?city.}";
        String B3 = "select distinct ?n1 ?n2 where {?n1 <familyNameOf> ?p1. ?n2 <familyNameOf> ?p2."+
                    " ?p1 <type> \"scientist\"; <hasWonPrize> ?award; <bornInLocation> ?city."+
                    " ?p2 <type> \"scientist\"; <hasWonPrize> ?award; <bornInLocation> ?city. filter (?p1 != ?p2)}";
        String C1 = "select distinct ?n1 ?n2 where {?n1 <familyNameOf> ?p1. ?n2 <familyNameOf> ?p2."+
                    " ?p1 <type> \"scientist\"; [] ?city. ?p2 <type> \"scientist\"; [] ?city."+
                    " ?city <type> <site> filter (?p1 != ?p2)}";                
        String C2 = "select distinct ?n where {?p <isCalled> ?n; [] ?c1; [] ?c2. ?c1 <type>"+
                    " <village>; <isCalled> \"London\". ?c2 <type> <site>; <isCalled> \"Paris\".}";
        
        String Q1 = "select count ?c where {?a a ?c}";
        String Q2 = "select count ?bp where {?as a <http://simile.mit.edu/2006/01/ontologies/mods3#Text>; ?bp ?bo.}";
        String Q3 = "select duplicates ?bp ?bo where {?as a <http://simile.mit.edu/2006/01/ontologies/mods3#Text>; ?bp ?bo.}";
        String Q4 = "select duplicates ?bp ?bo where {?as a <http://simile.mit.edu/2006/01/ontologies/mods3#Text>; ?bp ?bo; <http://simile.mit.edu/2006/01/ontologies/mods3#language> <http://simile.mit.edu/2006/01/language/iso639-2b/fre>.}";
        String Q5 = "select ?as ?co where {?as <http://simile.mit.edu/2006/01/ontologies/mods3#origin> <info:marcorg/DLC>; <http://simile.mit.edu/2006/01/ontologies/mods3#records> ?bo. ?bo a ?co. filter (?co != <http://simile.mit.edu/2006/01/ontologies/mods3#Text>)}";
        String Q6 = "select count ?ap where {{?as a <http://simile.mit.edu/2006/01/ontologies/mods3#Text>} union {?as <http://simile.mit.edu/2006/01/ontologies/mods3#records> []; a <http://simile.mit.edu/2006/01/ontologies/mods3#Text>} ?as ?ap [].}";
        String Q7 = "select ?as ?bo ?co where {?as <http://simile.mit.edu/2006/01/ontologies/mods3#point> \"end\"; <http://simile.mit.edu/2006/01/ontologies/mods3#encoding> ?bo; a ?co}";
        
        Driver rdf3xDriver = new Driver();
        Properties properties = new Properties();

        // load a dataset
//        startTime = System.currentTimeMillis();
//        //Process pb = (new ProcessBuilder("/home/suitcase/Installations/rdf3x-0.3.7/bin/rdf3xload", "/home/suitcase/Desktop/rdf3xdb", "/home/suitcase/Downloads/yago.n3")).start();
//        Process pb = (new ProcessBuilder("/home/suitcase/Installations/rdf3x-0.3.7/bin/rdf3xload", "/home/suitcase/Desktop/rdf3xbartondb", "/home/suitcase/Deskto.nt")).start();
//        pb.waitFor();     
//        elapsedTime = System.currentTimeMillis() - startTime;
//        System.out.println("Query execution time: " + elapsedTime/1000F + " secs.");
       

        // connect to the already created db
       Connection connection = (Connection) rdf3xDriver.connect("rdf3x:///home/suitcase/Desktop/Yagodb", properties);

       Statement statement = new Statement(connection);


        startTime = System.currentTimeMillis();
        // execute a query over the already created db        
        ResultSet rs = (ResultSet) statement.executeQueryWithFunctions(A1, null);
        elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Query execution time: " + elapsedTime/1000F + " secs.");
         
    }
    
    // Internal implementation methods
    //////////////////////////////////
    
    private static String readFileAsString(String filePath) throws java.io.IOException {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        
        reader.close();
        return fileData.toString();
    }
}
