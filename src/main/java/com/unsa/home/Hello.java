package com.unsa.home;

import java.util.Iterator;

//import org.apache.jena.iri.impl.Main;
//import org.apache.jena.query.Query;
//import org.apache.jena.query.QueryExecution;
//import org.apache.jena.query.QueryExecutionFactory;
//import org.apache.jena.query.QueryFactory;
//import org.apache.jena.query.QuerySolution;
//import org.apache.jena.query.ResultSet;
//import org.apache.jena.rdf.model.Literal;
//import org.apache.jena.rdf.model.Model;
//import org.apache.jena.util.FileManager;




import org.apache.log4j.chainsaw.Main;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.query.QuerySolution;

public class Hello {
	public static void main(String[] args) {

		sparqlTest();
	}

	static void sparqlTest()
	{
	FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
	Model model = FileManager.get().loadModel("/home/jhoedmon/workspace/home/src/main/java/com/unsa/home/data.rdf");
	String queryString =
			"PREFIX rdf:<http://www.w3.org/1999/22-rdf-syntax-ns#>" +
			"PREFIX foaf:<http://xmlns.com/foaf/0.1/>"+
					"SELECT * WHERE {" +
					"?person foaf:name ?x ."+
					"}";
	
	
	
//	String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
//            "PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
//            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
//            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
//            "SELECT ?s ?p ?o WHERE { " +
//          "  ?s ?p ?o .FILTER regex(str(?o)) ."+
////                       "FILTER regex(str(?p),\"subClassOf\") . "+
//                          "} LIMIT 5";
	
	Query query = QueryFactory.create(queryString);
	QueryExecution qexec = QueryExecutionFactory.create(query, model);
	try{
		ResultSet results = qexec.execSelect();
		while (results.hasNext()){
			QuerySolution soln = results.nextSolution();
			Literal name = soln.getLiteral("x");
			System.out.println(name);
		}
		
	}finally{
			qexec.close();
		}
	
		
	}
}



