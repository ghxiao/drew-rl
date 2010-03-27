/*
 * @(#)ComipleOntologyFromFileDemo.java 2010-3-27 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.ldlp.demo;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

import at.ac.tuwien.kr.ldlp.reasoner.LDLPCompiler;
import edu.stanford.db.lp.ProgramClause;

/**
 * TODO describe this class please.
 */
public class ComipleOntologyFromFileDemo {
	private static OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

	 public final static String uri =
	 "http://www.kr.tuwien.ac.at/staff/xiao/ldl/super.ldl";
	
	 public final static String phyUri = "file:kb/super.ldl";

	// public final static String uri =
	// "http://www.kr.tuwien.ac.at/staff/xiao/ldl/role_intersection_and_union.ldl";
	//
	// public final static String phyUri =
	// "file:kb/role_intersection_and_union.ldl";

	// public final static String uri =
	// "http://www.kr.tuwien.ac.at/staff/xiao/ldl/role_chain.ldl";
	//
	// public final static String phyUri = "file:kb/role_chain.ldl";
	//	
//	public final static String uri = "http://www.kr.tuwien.ac.at/staff/xiao/ldl/role_inverse.ldl";
//
//	public final static String phyUri = "file:kb/role_inverse.ldl";

	private static OWLOntology loadOntology(String uri, String phyUri) {
		OWLOntology ontology = null;

		System.out.println();
		System.out.println("------------------------------------------------------");

		System.out.println("Reading file " + uri + "...");
		manager.addIRIMapper(new SimpleIRIMapper(IRI.create(uri), IRI.create(phyUri)));

		try {
			ontology = manager.loadOntology(IRI.create(uri));

		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}

		return ontology;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final OWLOntology ontology = loadOntology(uri, phyUri);

		LDLPCompiler compiler = new LDLPCompiler();
		System.out.println("Ontology:");
		final Set<OWLAxiom> axioms = ontology.getAxioms();
		for (OWLAxiom axiom : axioms) {
			System.out.println(axiom);
		}

		System.out.println("-------------------------------------------------------");

		List<ProgramClause> clauses = compiler.compile(axioms);
		System.out.println("Compiled Ontoloy:");
		for (ProgramClause clause : clauses) {
			System.out.println(clause);
		}

	}

}
