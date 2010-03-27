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

	List<ProgramClause> clauses;

	public List<ProgramClause> complile(OWLOntology ontology) {
		final Set<OWLAxiom> axioms = ontology.getAxioms();
		return compile(axioms);
	}

	public List<ProgramClause> compile(final Set<OWLAxiom> axioms) {
		reset();

		AxiomCompiler axiomCompiler = new AxiomCompiler();
		final List<ProgramClause> clauses = axiomCompiler.compile(axioms);

		LDLPObjectClosureBuilder closureBuilder = new LDLPObjectClosureBuilder();
		final LDLPObjectClosure closure = closureBuilder.build(axioms);
		ClosureCompiler closureCompiler = new ClosureCompiler();
		final List<ProgramClause> clauses1 = closureCompiler.compile(closure);
		clauses.addAll(clauses1);
		return clauses;
	}
	
	public List<ProgramClause> compile(OWLAxiom... axioms) {
		reset();

		AxiomCompiler axiomCompiler = new AxiomCompiler();
		final List<ProgramClause> clauses = axiomCompiler.compile(axioms);

		LDLPObjectClosureBuilder closureBuilder = new LDLPObjectClosureBuilder();
		final LDLPObjectClosure closure = closureBuilder.build(axioms);
		ClosureCompiler closureCompiler = new ClosureCompiler();
		final List<ProgramClause> clauses1 = closureCompiler.compile(closure);
		clauses.addAll(clauses1);
		return clauses;
	}	

	private void reset() {

		clauses = new ArrayList<ProgramClause>();
	}

}
