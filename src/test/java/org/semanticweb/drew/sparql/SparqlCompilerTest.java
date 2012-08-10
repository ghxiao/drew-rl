package org.semanticweb.drew.sparql;

import org.junit.Test;
import org.semanticweb.drew.dlprogram.Clause;
import org.semanticweb.drew.ldlp.reasoner.LDLPCompilerManager;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.Syntax;

public class SparqlCompilerTest {

	@Test
	public void test() {
		System.out.println("----------------Query#3------------------");
		String sparql3 = "PREFIX bi: <http://edimine.ec.tuwien.ac.at/EdiOnto#> " //
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " //
				+ "SELECT ?X " //
				+ "WHERE {"//
				+ "?X rdf:type bi:Message ." //
				+ "?X bi:containsValue ?Y ." //
				+ "?Y rdf:type bi:RECADV_Identification_Function_original ." //
				+ "?X bi:containsValue ?Z ." //
				+ "?Z rdf:type bi:RECADV_DateTime_documentMessageDateTime ." //
				+ "?Z bi:hasValue '20110407'" + "}";

		Query query3 = QueryFactory.create(sparql3, Syntax.syntaxARQ);

		SparqlCompiler sparqlCompiler = new SparqlCompiler();

		Clause clause = sparqlCompiler.compileQuery(query3);
		System.out.println(clause);

		// System.out.println(query1);
	}

}
