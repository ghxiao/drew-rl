/*
 * @(#)LDLPCompiler.java 2010-3-17 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.ldlp.reasoner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

import at.ac.tuwien.kr.dlprogram.Clause;


/**
 * LDLPCompiler: compile an LDLp KB to a datalog program
 */
public class LDLPCompiler {

	List<Clause> clauses;

	public List<Clause> complile(OWLOntology ontology) {
		final Set<OWLAxiom> axioms = ontology.getAxioms();
		return compile(axioms);
	}

	public List<Clause> compile(final Set<OWLAxiom> axioms) {
		reset();

		AxiomCompiler axiomCompiler = new AxiomCompiler();
		final List<Clause> clauses = axiomCompiler.compile(axioms);

		LDLPObjectClosureBuilder closureBuilder = new LDLPObjectClosureBuilder();
		final LDLPObjectClosure closure = closureBuilder.build(axioms);
		ClosureCompiler closureCompiler = new ClosureCompiler();
		final List<Clause> clauses1 = closureCompiler.compile(closure);
		clauses.addAll(clauses1);
		return clauses;
	}
	
	public List<Clause> compile(OWLAxiom... axioms) {
		reset();

		AxiomCompiler axiomCompiler = new AxiomCompiler();
		final List<Clause> clauses = axiomCompiler.compile(axioms);

		LDLPObjectClosureBuilder closureBuilder = new LDLPObjectClosureBuilder();
		final LDLPObjectClosure closure = closureBuilder.build(axioms);
		ClosureCompiler closureCompiler = new ClosureCompiler();
		final List<Clause> clauses1 = closureCompiler.compile(closure);
		clauses.addAll(clauses1);
		return clauses;
	}	

	private void reset() {

		clauses = new ArrayList<Clause>();
	}

}
