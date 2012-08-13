import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;

import org.semanticweb.drew.dlprogram.Literal;
import org.semanticweb.drew.ldlp.reasoner.LDLPReasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;


import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.Syntax;


public class QueryJenaDReW {
	public void query(String file) throws FileNotFoundException, URISyntaxException, OWLOntologyCreationException {
		long startTime = System.currentTimeMillis();
		// Model ontology = FileManager.get().loadModel(file);
		// System.out.println(1);
		// Reasoner reasoner = PelletReasonerFactory.theInstance().create();
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		//OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new File(file));
		OWLOntology ontology = manager.loadOntologyFromOntologyDocument(IRI.create(file));

		LDLPReasoner reasoner = new LDLPReasoner(ontology);

		// System.out.println(2);
		// InfModel inferModel = ModelFactory.createInfModel(reasoner,
		// ontology);
		// OntModel inferModel =
		// ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC,
		// ontology);

		// System.out.println("----------------Query#1------------------");
		// String sparql1 =
		// "PREFIX bi: <http://www.semanticweb.org/ontologies/2012/6/untitled-ontology-7#> "
		// + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
		// + "SELECT ?X "
		// + "WHERE {"
		// + "?X rdf:type bi:Woman ."
		// + "}";
		//
		// Query query1 = QueryFactory.create(sparql1, Syntax.syntaxARQ);
		// QueryExecution qe1 = QueryExecutionFactory.create(query1,
		// inferModel);
		// ResultSet result1 = qe1.execSelect();
		// ResultSetFormatter.out(result1);
		// qe1.close();

		// System.out.println("----------------Query#0------------------");
		// String sparql =
		// "PREFIX bi: <http://edimine.ec.tuwien.ac.at/EdiOnto#> "
		// + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
		// + "SELECT ?X "
		// + "WHERE {"
		// + "?X rdf:type bi:Message ."
		// + "}";
		//
		// Query query = QueryFactory.create(sparql, Syntax.syntaxARQ);
		// QueryExecution qe = QueryExecutionFactory.create(query, inferModel);
		// ResultSet result = qe.execSelect();
		// ResultSetFormatter.out(result);
		// qe.close();
		// long midTime1 = System.currentTimeMillis();
		// System.out.println((midTime1-startTime));

		System.out.println("----------------Query#1------------------");
		String sparql1 = "PREFIX bi: <http://edimine.ec.tuwien.ac.at/EdiOnto#> " //
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "//
				+ "SELECT ?X "//
				+ "WHERE {"//
				+ "?X rdf:type bi:RECADV_DateTime ."//
				+ "}";//

		Query query1 = QueryFactory.create(sparql1, Syntax.syntaxARQ);
		// QueryExecution qe1 = QueryExecutionFactory.create(query1,
		// inferModel);
		// ResultSet result1 = qe1.execSelect();
		// ResultSetFormatter.out(result1);
		// qe1.close();
		List<Literal> results = reasoner.executeQuery(query1);
		for(Literal lit: results)System.out.println(lit);
		long midTime1 = System.currentTimeMillis();
		System.out.println((midTime1 - startTime));

//		System.out.println("----------------Query#2------------------");
//		String sparql2 = "PREFIX bi: <http://edimine.ec.tuwien.ac.at/EdiOnto#> "//
//				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "//
//				+ "SELECT ?X "//
//				+ "WHERE {"//
//				+ "?X rdf:type bi:RECADV_DateTime_documentMessageDateTime ."//
//				+ "}";
//
//		Query query2 = QueryFactory.create(sparql2, Syntax.syntaxARQ);
//		QueryExecution qe2 = QueryExecutionFactory.create(query2, inferModel);
//		ResultSet result2 = qe2.execSelect();
//		ResultSetFormatter.out(result2);
//		qe2.close();
//		long midTime2 = System.currentTimeMillis();
//		System.out.println((midTime2 - midTime1));
//
//		System.out.println("----------------Query#3------------------");
//		String sparql3 = "PREFIX bi: <http://edimine.ec.tuwien.ac.at/EdiOnto#> "//
//				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "//
//				+ "SELECT ?X "//
//				+ "WHERE {"//
//				+ "?X rdf:type bi:Message ."//
//				+ "?X bi:containsValue ?Y ."//
//				+ "?Y rdf:type bi:RECADV_Identification_Function_original ."//
//				+ "?X bi:containsValue ?Z ."//
//				+ "?Z rdf:type bi:RECADV_DateTime_documentMessageDateTime ."//
//				+ "?Z bi:hasValue '20110407'" + "}";
//
//		Query query3 = QueryFactory.create(sparql3, Syntax.syntaxARQ);
//		QueryExecution qe3 = QueryExecutionFactory.create(query3, inferModel);
//		ResultSet result3 = qe3.execSelect();
//		ResultSetFormatter.out(result3);
//		qe3.close();
//		long endTime = System.currentTimeMillis();
//		System.out.println((endTime - midTime2));
	}

	public static void main(String[] args) throws FileNotFoundException, URISyntaxException, OWLOntologyCreationException {
		QueryJenaDReW qjd = new QueryJenaDReW();
		qjd.query("file:src/test/resources/MessageBI.owl");
	}

}
