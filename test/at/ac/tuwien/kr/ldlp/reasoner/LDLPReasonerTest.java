package at.ac.tuwien.kr.ldlp.reasoner;

import static at.ac.tuwien.kr.helper.LDLHelper.assert$;
import static at.ac.tuwien.kr.helper.LDLHelper.cls;
import static at.ac.tuwien.kr.helper.LDLHelper.ind;
import static at.ac.tuwien.kr.helper.LDLHelper.prop;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.kr.dlprogram.CacheManager;
import at.ac.tuwien.kr.dlprogram.Clause;
import at.ac.tuwien.kr.dlprogram.Literal;
import at.ac.tuwien.kr.dlprogram.NormalPredicate;
import at.ac.tuwien.kr.dlprogram.Variable;



public class LDLPReasonerTest {

	static final Logger logger = LoggerFactory.getLogger(LDLPReasonerTest.class);
	
	@Test
	public void test001() throws OWLOntologyCreationException {
		final OWLClass A = cls("A");
		final OWLClass B = cls("B");
		final OWLObjectProperty P = prop("P");

		final OWLNamedIndividual a = ind("a");
		final OWLNamedIndividual b = ind("b");

		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
		axioms.add(assert$(A, a));
		axioms.add(assert$(A, b));
		axioms.add(assert$(B, b));
		axioms.add(assert$(P, a, b));

		
		System.out.println("Axioms in Ontology");
		for(OWLAxiom axiom: axioms) {
			System.out.println(axiom);
		}
		
		
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

		OWLOntology ontology = manager.createOntology(axioms);

		LDLPReasoner reasoner = new LDLPReasoner(ontology);

		Clause query = new Clause();

		NormalPredicate ans = CacheManager.getInstance().getPredicate("ans", 1);
		Variable X = CacheManager.getInstance().getVariable("X");
		Variable Y = CacheManager.getInstance().getVariable("Y");

		Literal head = new Literal(ans, Y);

		NormalPredicate pP = CacheManager.getInstance().getPredicate(
				P.getIRI().toString(), 2);

		System.out.println(pP);
		
		NormalPredicate pA = CacheManager.getInstance().getPredicate(
				A.getIRI().toString(), 1);

		System.out.println(pA);
		
		query.setHead(head);

		Literal body1 = new Literal(pP, X, Y);

		Literal body2 = new Literal(pA, X);

		query.getPositiveBody().add(body1);
		query.getPositiveBody().add(body2);
		
		System.out.println("query is : " + query);
		
		reasoner.query(query);

		// OWLOntology ontology = OWLOntology
	}
//	
//	@Test
//	public void test002() {
//		final OWLClass B = cls("A");
//		final OWLObjectProperty P = prop("P");
//		System.out.println(B);
//		System.out.println(P);
//	}
}
