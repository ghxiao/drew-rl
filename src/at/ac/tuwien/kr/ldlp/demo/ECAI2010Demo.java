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
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.kr.dlprogram.Clause;
import at.ac.tuwien.kr.ldlp.reasoner.ClosureCompiler;
import at.ac.tuwien.kr.ldlp.reasoner.LDLPCompiler;
import at.ac.tuwien.kr.ldlp.reasoner.LDLPReasoner;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyTransitiveClosureOf;
import edu.stanford.db.lp.ProgramClause;

/**
 * TODO describe this class please.
 */
public class ECAI2010Demo {

	final static Logger logger = LoggerFactory.getLogger(ClosureCompiler.class);

	private static OWLOntologyManager manager = OWLManager
			.createOWLOntologyManager();

	public final static String uri = "http://www.kr.tuwien.ac.at/staff/xiao/ldl/super.ldl";

	public final static String phyUri = "file:src/at/ac/tuwien/kr/ldlp/demo/super.ldl";

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
	// public final static String uri =
	// "http://www.kr.tuwien.ac.at/staff/xiao/ldl/role_inverse.ldl";
	//
	// public final static String phyUri = "file:kb/role_inverse.ldl";

	private static OWLOntology loadOntology(String uri, String phyUri) {
		OWLOntology ontology = null;

		System.out.println();
		System.out
				.println("------------------------------------------------------");

		System.out.println("Reading file " + uri + "...");
		manager.addIRIMapper(new SimpleIRIMapper(IRI.create(uri), IRI
				.create(phyUri)));

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

		System.out
				.println("-------------------------------------------------------");

		List<Clause> clauses = compiler.compile(axioms);
		System.out.println("Compiled Ontoloy:");
		for (Clause clause : clauses) {
			System.out.println(clause);
		}

		OWLDataFactory owlDataFactory = manager.getOWLDataFactory();
		OWLObjectPropertyExpression Super = owlDataFactory
				.getOWLObjectProperty(IRI.create(uri + "#Super"));

		LDLObjectPropertyTransitiveClosureOf TransSuper = owlDataFactory
				.getLDLObjectPropertyTransitiveClosureOf(Super);

		OWLNamedIndividual a = owlDataFactory.getOWLNamedIndividual(IRI
				.create(uri + "#a"));
		OWLNamedIndividual b = owlDataFactory.getOWLNamedIndividual(IRI
				.create(uri + "#b"));
		OWLNamedIndividual c = owlDataFactory.getOWLNamedIndividual(IRI
				.create(uri + "#c"));
		OWLObjectPropertyAssertionAxiom axiom1 = owlDataFactory
				.getOWLObjectPropertyAssertionAxiom(Super, a, c);

		System.out
				.println("-------------------------------------------------------");
		System.out.println("Query " + axiom1);

		LDLPReasoner reasoner = new LDLPReasoner(ontology);
		boolean entailed = reasoner.isEntailed(axiom1);
		System.out.println("Result:" + entailed);

		OWLObjectPropertyAssertionAxiom axiom2 = owlDataFactory
				.getOWLObjectPropertyAssertionAxiom(TransSuper, a, c);

		System.out
				.println("-------------------------------------------------------");
		System.out.println("Query " + axiom2);

		
		entailed = reasoner.isEntailed(axiom2);
		System.out.println("Result:" + entailed);

	}

}
