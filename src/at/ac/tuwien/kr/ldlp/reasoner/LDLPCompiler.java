/*
 * @(#)LDLPCompiler.java 2010-3-17 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.ldlp.reasoner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLIndividualVisitor;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitor;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.util.OWLAxiomVisitorAdapter;

import at.ac.tuwien.kr.datalog.DatalogQuery;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyChainOf;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyIntersectionOf;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyTransitiveClosureOf;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyUnionOf;
import edu.stanford.db.lp.ConstTerm;
import edu.stanford.db.lp.Literal;
import edu.stanford.db.lp.ProgramClause;
import edu.stanford.db.lp.Term;
import edu.stanford.db.lp.VariableTerm;

/**
 * LDLPCompiler: compile an LDLp KB to a datalog program
 */
public class LDLPCompiler {

	VariableTerm X = new VariableTerm("X");
	VariableTerm Y = new VariableTerm("Y");
	VariableTerm Z = new VariableTerm("Z");

	AxiomCompiler axiomCompiler;

	LDLPObjectClosure closure = new LDLPObjectClosure();
	
	LDLPObjectClosureBuilder closureBuilder;

	List<ProgramClause> clauses;

	Set<OWLClassExpression> classExpressionsClosure;

	Set<OWLObjectPropertyExpression> objectPropertyExpressionsClosure;

	Set<OWLIndividual> individualsClosure;

	private OWLOntology ontology;

	OWLDataFactory dataFactory;

	public List<ProgramClause> complileLDLPOntology(OWLOntology ontology) {
		this.ontology = ontology;
		reset();
		for (OWLAxiom axiom : ontology.getAxioms()) {
			complieAxiom(axiom);
		}

		for (OWLAxiom axiom : ontology.getAxioms()) {
			updateClosure(axiom);
		}

		compileClasses();

		complieProperties();

		return clauses;
	}

	private void updateClosure(OWLAxiom axiom) {

		axiom.accept(closureBuilder);
	}

	private void complieAxiom(OWLAxiom axiom) {
		axiom.accept(axiomCompiler);
	}

	private void reset() {
		dataFactory = ontology.getOWLOntologyManager().getOWLDataFactory();
		clauses = new ArrayList<ProgramClause>();
		axiomCompiler = new AxiomCompiler(clauses);
		classExpressionsClosure = new HashSet<OWLClassExpression>();
		objectPropertyExpressionsClosure = new HashSet<OWLObjectPropertyExpression>();
		individualsClosure = new HashSet<OWLIndividual>();
		//closureBuilder = new LDLPObjectClosureBuilder(closure);
		//ClosureCompiler closureProcesser = new ClosureCompiler(clauses);
		
	}

	private void complieProperties() {

	}

	private void compileClasses() {

	}

	public Literal compileClassAssertionAxiom(OWLClassAssertionAxiom classAssertionAxiom) {
		return null;
	}
}
