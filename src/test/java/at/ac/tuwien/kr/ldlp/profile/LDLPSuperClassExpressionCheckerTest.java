package at.ac.tuwien.kr.ldlp.profile;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import at.ac.tuwien.kr.ldlp.profile.LDLPSuperClassExpressionChecker;

public class LDLPSuperClassExpressionCheckerTest {

	static LDLPSuperClassExpressionChecker checker;

	static OWLOntologyManager manager;

	static String root = "http://www.kr.tuwien.ac.at/";

	static OWLDataFactory factory;

	static OWLClass A;
	static OWLClass B;
	static OWLClass C;

	static OWLObjectProperty E;
	static OWLObjectProperty F;

	static OWLClassExpression A_and_B;
	static OWLClassExpression A_or_B;
	static OWLClassExpression not_A;

	static OWLClassExpression E_some_C;
	static OWLClassExpression E_only_C;

	static OWLClassExpression E_min_2_C;
	static OWLObjectMaxCardinality E_max_2_C;
	static OWLObjectExactCardinality E_exact_2_C;

	static OWLIndividual o1;
	static OWLIndividual o2;

	static OWLObjectOneOf O;

	static OWLClassExpression E_value_o1;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		checker = new LDLPSuperClassExpressionChecker();
		manager = OWLManager.createOWLOntologyManager();
		factory = manager.getOWLDataFactory();
		A = factory.getOWLClass(IRI.create(root + "#A"));
		B = factory.getOWLClass(IRI.create(root + "#B"));
		C = factory.getOWLClass(IRI.create(root + "#C"));
		A_and_B = factory.getOWLObjectIntersectionOf(A, B);
		A_or_B = factory.getOWLObjectUnionOf(A, B);
		not_A = factory.getOWLObjectComplementOf(A);

		E = factory.getOWLObjectProperty(IRI.create(root + "#E"));
		F = factory.getOWLObjectProperty(IRI.create(root + "#F"));

		E_some_C = factory.getOWLObjectSomeValuesFrom(E, C);
		E_only_C = factory.getOWLObjectAllValuesFrom(E, C);

		E_min_2_C = factory.getOWLObjectMinCardinality(2, E, C);
		E_max_2_C = factory.getOWLObjectMaxCardinality(2, E, C);
		E_exact_2_C = factory.getOWLObjectExactCardinality(2, E, C);

		o1 = factory.getOWLNamedIndividual(IRI.create(root + "#o1"));
		o2 = factory.getOWLNamedIndividual(IRI.create(root + "#o2"));

		O = factory.getOWLObjectOneOf(o1, o2);

		E_value_o1 = factory.getOWLObjectHasValue(E, o1);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testVisitOWLClass() {
		assertTrue(A.accept(checker));
		assertTrue(B.accept(checker));
		assertTrue(C.accept(checker));
	}

	@Test
	public void testVisitOWLObjectIntersectionOf() {
		assertTrue(A_and_B.accept(checker));
	}

	@Test
	public void testVisitOWLObjectIntersectionOfEx() {
		assertFalse(factory.getOWLObjectIntersectionOf(E_min_2_C, O, A_or_B).accept(checker));
	}

	@Test
	public void testVisitOWLObjectUnionOf() {
		assertFalse(A_or_B.accept(checker));
	}

	@Test
	public void testVisitOWLObjectComplementOf() {
		assertFalse(not_A.accept(checker));
	}

	@Test
	public void testVisitOWLObjectSomeValuesFrom() {
		assertFalse(E_some_C.accept(checker));
	}

	/*
	 * this is right!
	 * 	*/	
	@Test
	public void testVisitOWLObjectAllValuesFrom() {
		assertTrue(E_only_C.accept(checker));
	}

	@Test
	public void testVisitOWLObjectHasValue() {
		assertFalse(E_value_o1.accept(checker));
	}

	@Test
	public void testVisitOWLObjectMinCardinality() {
		assertFalse(E_min_2_C.accept(checker));
	}

	@Test
	public void testVisitOWLObjectExactCardinality() {
		assertFalse(E_exact_2_C.accept(checker));
	}

	@Test
	public void testVisitOWLObjectMaxCardinality() {
		assertFalse(E_max_2_C.accept(checker));
	}

	@Test
	public void testVisitOWLObjectHasSelf() {

	}

	@Test
	public void testVisitOWLObjectOneOf() {
		assertFalse(O.accept(checker));
	}

	@Test
	public void testVisitOWLDataSomeValuesFrom() {

	}

	@Test
	public void testVisitOWLDataAllValuesFrom() {

	}

	@Test
	public void testVisitOWLDataHasValue() {

	}

	@Test
	public void testVisitOWLDataMinCardinality() {

	}

	@Test
	public void testVisitOWLDataExactCardinality() {

	}

	@Test
	public void testVisitOWLDataMaxCardinality() {

	}

}
