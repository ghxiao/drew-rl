package at.ac.tuwien.kr.ldlp;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

import edu.stanford.db.lp.ProgramClause;

public class AxiomCompilerTest {

	private List<ProgramClause> clauses;
	private AxiomCompiler axiomCompiler;
	private OWLOntologyManager manager;
	private OWLDataFactory factory;
	private OWLIndividual a;
	private OWLIndividual b;
	private OWLIndividual c;
	private OWLClass A;
	private OWLClass B;
	private OWLClass C;
	
	@Before
	public void setUp() {
		clauses = new ArrayList<ProgramClause>();
		axiomCompiler = new AxiomCompiler(clauses);
		manager = OWLManager.createOWLOntologyManager();
		factory = manager.getOWLDataFactory();
		A = factory.getOWLClass(IRI.create("A"));
		B = factory.getOWLClass(IRI.create("B"));
		C = factory.getOWLClass(IRI.create("C"));
		a = factory.getOWLNamedIndividual(IRI.create("a"));
		b = factory.getOWLNamedIndividual(IRI.create("b"));
		c = factory.getOWLNamedIndividual(IRI.create("c"));
	}

	@Test
	public void testVisitOWLClassAssertionAxiom() {
		final OWLClassAssertionAxiom a_is_A = factory.getOWLClassAssertionAxiom(A, a);
		a_is_A.accept(axiomCompiler);
		assertEquals(1, clauses.size());
		assertEquals("A(a).", clauses.get(0).toString());
	}

	@Test
	public void testVisitOWLObjectPropertyAssertionAxiom() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testVisitOWLSubClassOfAxiom() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testVisitOWLSubObjectPropertyOfAxiom() {
		fail("Not yet implemented"); // TODO
	}

}
