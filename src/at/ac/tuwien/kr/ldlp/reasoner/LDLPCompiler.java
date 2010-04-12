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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.kr.dlprogram.Clause;


/**
 * LDLPCompiler: compile an LDLp KB to a datalog program
 */
public class LDLPCompiler {

	final static Logger logger = LoggerFactory.getLogger(LDLPCompiler.class);
	
	List<Clause> clauses;

	public List<Clause> complile(OWLOntology ontology) {
		final Set<OWLAxiom> axioms = ontology.getAxioms();
		return compile(axioms);
	}

	public List<Clause> compile(final Set<OWLAxiom> axioms) {
		reset();

		logger.debug("-------------------compiling axioms:--------------------");
		LDLPAxiomCompiler axiomCompiler = new LDLPAxiomCompiler();
		final List<Clause> clauses = axiomCompiler.compile(axioms);

		logger.debug("-------------------building closure:--------------------");
		LDLPClosureBuilder closureBuilder = new LDLPClosureBuilder();
		final LDLPClosure closure = closureBuilder.build(axioms);
		
		
		logger.debug("------------------compiling closure:--------------------");
		LDLPClosureCompiler closureCompiler = new LDLPClosureCompiler();
		
		final List<Clause> clauses1 = closureCompiler.compile(closure);
		clauses.addAll(clauses1);
		return clauses;
	}
	
	public List<Clause> compile(OWLAxiom... axioms) {
		reset();

		LDLPAxiomCompiler axiomCompiler = new LDLPAxiomCompiler();
		final List<Clause> clauses = axiomCompiler.compile(axioms);

		LDLPClosureBuilder closureBuilder = new LDLPClosureBuilder();
		final LDLPClosure closure = closureBuilder.build(axioms);
		LDLPClosureCompiler closureCompiler = new LDLPClosureCompiler();
		final List<Clause> clauses1 = closureCompiler.compile(closure);
		clauses.addAll(clauses1);
		return clauses;
	}	

	private void reset() {

		clauses = new ArrayList<Clause>();
	}

}
