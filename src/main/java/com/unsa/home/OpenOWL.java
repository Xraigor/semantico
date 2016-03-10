// package yourPack;
package com.unsa.home;


import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.ontology.OntClass;


//
//import org.apache.jena.ontology.OntModel;
//import org.apache.jena.ontology.OntModelSpec;
//import org.apache.jena.query.QueryExecution;
//import org.apache.jena.query.QueryExecutionFactory;
//import org.apache.jena.query.QueryFactory;
//import org.apache.jena.query.ResultSetFormatter;
//import org.apache.jena.rdf.model.ModelFactory;
//import org.apache.jena.util.FileManager;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


class OpenOWL {

  
    private String filePath;
    
  
    OpenOWL(String filePath){
        this.filePath = filePath;
    }
    
    //Open a connection to MonFichierOwl.OWL
    public OntModel OpenConnectOWL() {

        OntModel mode = null;
        mode = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
                                                     
        java.io.InputStream in = FileManager.get().open(filePath);  //MyFile

        if (in == null) {
            throw new IllegalArgumentException("There is not file to connect."); 
        }
        return (OntModel) mode.read(in, "");
    }

    // jena.query.ResultSet  return
    public  ResultSet ExecSparQl(String Query) {

        Query query = QueryFactory.create(Query);
        QueryExecution qe = QueryExecutionFactory.create(query, OpenConnectOWL());
        ResultSet results = qe.execSelect();
        return results;  // Retrun jena.query.ResultSet 

    }

//    public List<String> getSimilarClasses(String wordToSearch) {
//    	
//        String stringQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
//                "PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
//                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
//                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
//                "SELECT ?s ?p ?o WHERE { " +
//                "  ?s ?p ?o .FILTER regex(str(?o),\""+wordToSearch+"\") ."+
////                            "FILTER regex(str(?p),\"subClassOf\") . "+
//                              "} LIMIT 5";
//    	
//        List<String> actualEntries = new ArrayList<String>();
//
//        Query query = QueryFactory.create(stringQuery);
//        QueryExecution qe = QueryExecutionFactory.create(query, OpenConnectOWL());
//        ResultSet results = qe.execSelect();
//        System.out.println("bring something");
//        List <String>  listResult = results.getResultVars();
//        System.out.println("Tam: "+listResult.size());
//        System.out.println("Tam: "+listResult.get(0));
//       
//        while(results.hasNext()) {
//            QuerySolution currentRes=results.next();
//            if(currentRes.get("?s").isURIResource()) {
//                    String name = currentRes.getResource("s").getURI().toString();
//                    name = name.substring(name.indexOf('#')+1,name.length());
//                    actualEntries.add(name);
//            }
//        }
//
//        return actualEntries;  
//    }
//    
    
    public List<String> getSubClasses(String wordToSearch) {
    	
    	String stringQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
                "SELECT ?s ?p ?o WHERE { " +
                "  ?s ?p ?o .FILTER regex(str(?o),\""+wordToSearch+"\") ."+
//                           "FILTER regex(str(?p),\"subClassOf\") . "+
                              "} LIMIT 30";
    	
    	
        List<String> actualEntries = new ArrayList<String>();

        Query query = QueryFactory.create(stringQuery);
        OntModel model = OpenConnectOWL();
        QueryExecution qe = QueryExecutionFactory.create(query, OpenConnectOWL());
        ResultSet results = qe.execSelect();
        System.out.println("bring something");
        List <String>  listResult = results.getResultVars();
        System.out.println("Tam: "+listResult.size());
        System.out.println("Tam: "+listResult.get(0));
       
        while(results.hasNext()) {
            QuerySolution currentRes=results.next();
            if(currentRes.get("?s").isURIResource()) {
                    System.out.println(currentRes.getResource("s").getURI().toString() );
                    actualEntries.add(currentRes.getResource("s").getURI().toString() );
                    // ****************************************************************
                    /*OntClass sup= model.getOntClass(currentRes.getResource("s").getURI());
                    if (sup.hasSubClass()) {
                        for (Iterator i = sup.listSubClasses(); i.hasNext();) {
                            OntClass c = (OntClass) i.next();
                            actualEntries.add(c.getLocalName());
                        }
                      }
                      */
                    // ****************************************************************
            }
        }
            

        return actualEntries;  
    }
    


}