package at.ac.tuwien.kr.ldlp.reasoner;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyChainOf;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyTransitiveClosureOf;

import edu.stanford.db.lp.ProgramClause;
import edu.stanford.db.lp.VariableTerm;

import static at.ac.tuwien.kr.helper.LDLHelper.*;

public class ClosureCompilerTest {
	VariableTerm X = new VariableTerm("X");
	VariableTerm Y = new VariableTerm("Y");
	VariableTerm Z = new VariableTerm("Z");
	VariableTerm X1 = new VariableTerm("X1");
	VariableTerm X2 = new VariableTerm("X2");
	VariableTerm X3 = new VariableTerm("X3");
	VariableTerm Y1 = new VariableTerm("Y1");
	VariableTerm Y2 = new VariableTerm("Y2");
	VariableTerm Y3 = new VariableTerm("Y3");
	private OWLOntologyManager manager;
	private OWLDataFactory factory;
	private OWLIndividual a;
	private OWLIndividual b;
	private OWLIndividual c;
	private OWLClass A;
	private OWLClass B;
	private OWLClass C;
	private OWLObjectProperty E;
	private OWLObjectProperty F;
	LDLPObjectClosureBuilder builder;
	OWLOntology ontology;
	public ClosureCompiler closureCompiler;
	LDLPObjectClosure closure;
	private String TOP = DatalogObjectFactory.getInstance().getTopPrediate();
	private String NOTEQUAL = DatalogObjectFactory.getInstance().getNotEqual();

	@Before
	public void setUp() {

		manager = OWLManager.createOWLOntologyManager();
		factory = manager.getOWLDataFactory();
		A = factory.getOWLClass(IRI.create("A"));
		B = factory.getOWLClass(IRI.create("B"));
		C = factory.getOWLClass(IRI.create("C"));
		a = factory.getOWLNamedIndividual(IRI.create("a"));
		b = factory.getOWLNamedIndividual(IRI.create("b"));
		c = factory.getOWLNamedIndividual(IRI.create("c"));
		E = factory.getOWLObjectProperty(IRI.create("E"));
		F = factory.getOWLObjectProperty(IRI.create("F"));
		builder = new LDLPObjectClosureBuilder();
		closureCompiler = new ClosureCompiler();

	}

	@Test
	public void testClosureCompiler() {

	}

	@Test
	public void testCompile() {

	}

	// a:A
	@Test
	public void testVisitOWLClass() throws OWLOntologyCreationException {
		final OWLClassAssertionAxiom a_is_A = factory.getOWLClassAssertionAxiom(A, a);
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
		axioms.add(a_is_A);
		ontology = manager.createOntology(axioms);
		final LDLPObjectClosure closure = builder.build(ontology);
		final List<ProgramClause> clauses = closureCompiler.compile(closure);

		assertEquals(1, clauses.size());
		assertEquals("top(X):-p1(X).", clauses.get(0).toString());

	}

	// a:A and B and C
	// A(a).
	// (A and B and C)(X):-A(X),B(X),C(X)
	@Test
	public void testVisitOWLObjectIntersectionOf() {
		final OWLClassExpression A_and_B_and_C = and(A, B, C);
		closure = builder.build(assert$(A_and_B_and_C, a));
		final List<ProgramClause> clauses = closureCompiler.compile(closure);
		assertTrue(clauses.contains(clause(head(literal(TOP, X)), body(literal(p(A), X)))));
		assertTrue(clauses.contains(clause(head(literal(p(A_and_B_and_C), X)), body(literal(p(A), X), literal(p(B), X), literal(p(C), X)))));
	}

	// a:A or B or C
	// (A or B or C)(X):-A(X).
	// (A or B or C)(X):-B(X).
	// (A or B or C)(X):-C(X).
	@Test
	public void testVisitOWLObjectUnionOf() {
		final OWLClassExpression A_or_B_or_C = or(A, B, C);
		closure = builder.build(assert$(A_or_B_or_C, a));
		final List<ProgramClause> clauses = closureCompiler.compile(closure);
		assertTrue(clauses.contains(clause(head(literal(TOP, X)), body(literal(p(A), X)))));
		assertTrue(clauses.contains(clause(head(literal(p(A_or_B_or_C), X)), body(literal(p(A), X)))));
	}

	// a:E some A
	// (E some A)(X):-E(X,Y),A(Y).
	@Test
	public void testVisitOWLObjectSomeValuesFrom() {
		final OWLClassExpression E_some_A = some(E, A);
		closure = builder.build(assert$(E_some_A, a));
		final List<ProgramClause> clauses = closureCompiler.compile(closure);
		assertTrue(clauses.contains(clause(head(literal(TOP, X)), body(literal(p(A), X)))));
		assertTrue(clauses.contains(clause(head(literal(p(E_some_A), X)), body(literal(p(E), X, Y), literal(p(A), Y)))));
	}

	@Test
	public void testVisitOWLObjectAllValuesFrom() {

	}

	/**
	 * <pre>
	 * (E min n D)(X):- E(X,Y1),D(Y1),...,E(X,Yn),D(Yn), 
	 * 					Y1 != Y2, Y1 != Y3, ..., Yn-1 != Yn
	 * </pre>
	 */
	@Test
	public void testVisitOWLObjectMinCardinality() {
		final OWLObjectMinCardinality E_min_2_C = min(2, E, C);
		closure = builder.build(assert$(E_min_2_C, a));
		final List<ProgramClause> clauses = closureCompiler.compile(closure);
		assertTrue(clauses.contains(clause(head(literal(p(E_min_2_C), X)), //
				body(literal(p(E), X, Y1), literal(p(C), Y1), literal(p(E), X, Y2), //
					literal(p(C), Y2), literal(NOTEQUAL, Y1, Y2)))));
	}

	@Test
	public void testVisitOWLObjectOneOf() {
		fail("Not yet implemented"); // TODO
	}

	// E(X,Y):-inv(E)(Y,X).
	@Test
	public void testVisitOWLObjectInverseOf() {
		final OWLObjectInverseOf inv_E = inv(E);
		final OWLObjectPropertyAssertionAxiom axiom = assert$(inv_E, a, b);
		closure = builder.build(axiom);
		final List<ProgramClause> clauses = closureCompiler.compile(closure);
		assertTrue(clauses.contains(clause(head(literal(p(E), X, Y)), body(literal(p(inv_E), Y, X)))));
	}

	// and(E,F)(X,Y):-E(X,Y),F(X,Y).
	@Test
	public void testVisitLDLObjectPropertyIntersectionOf() {
		final OWLObjectPropertyExpression E_and_F = and(E, F);
		closure = builder.build(assert$(E_and_F, a, b));
		final List<ProgramClause> clauses = closureCompiler.compile(closure);
		assertTrue(clauses.contains(clause(head(literal(TOP, X, Y)), body(literal(p(E), X, Y)))));
		assertTrue(clauses.contains(clause(head(literal(p(E_and_F), X, Y)), body(literal(p(E), X, Y), literal(p(F), X, Y)))));
	}

	@Test
	public void testVisitLDLObjectPropertyUnionOf() {
		final OWLObjectPropertyExpression E_or_F = or(E, F);
		closure = builder.build(assert$(E_or_F, a, b));
		final List<ProgramClause> clauses = closureCompiler.compile(closure);
		assertTrue(clauses.contains(clause(head(literal(TOP, X, Y)), body(literal(p(E), X, Y)))));
		assertTrue(clauses.contains(clause(head(literal(p(E_or_F), X, Y)), body(literal(p(E), X, Y)))));
	}

	// trans(E)(X,Y):-E(X,Y)
	// trans(E)(X,Y):-E(X),trans(X,Y).
	@Test
	public void testVisitLDLObjectPropertyTransitiveClosureOf() {
		final LDLObjectPropertyTransitiveClosureOf trans_E = trans(E);
		final OWLObjectPropertyAssertionAxiom axiom = assert$(trans_E, a, b);
		closure = builder.build(axiom);
		final List<ProgramClause> clauses = closureCompiler.compile(closure);
		assertTrue(clauses.contains(clause(head(literal(p(trans_E), X, Y)), body(literal(p(E), X, Y)))));
		assertTrue(clauses.contains(clause(head(literal(p(trans_E), X, Y)), body(literal(p(E), X, Y), literal(p(trans_E), Y, Z)))));
	}

	// compose(E1, E2, ... En)(X1,Xn+1):- E1(X1,X2), E2(X2,X3), ... ,
	// En(Xn,Xn+1)
	@Test
	public void testVisitLDLObjectPropertyChainOf() {
		final LDLObjectPropertyChainOf E_o_F = compose(E, F);
		final OWLObjectPropertyAssertionAxiom axiom = assert$(E_o_F, a, b);
		closure = builder.build(axiom);
		final List<ProgramClause> clauses = closureCompiler.compile(closure);
		assertTrue(clauses.contains(clause(head(literal(p(E_o_F), X1, X3)), body(literal(p(E), X1, X2), literal(p(F), X2, X3)))));

	}

	@Test
	public void testVisitOWLNamedIndividual() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testVisitOWLAnonymousIndividual() {
		fail("Not yet implemented"); // TODO
	}

}
