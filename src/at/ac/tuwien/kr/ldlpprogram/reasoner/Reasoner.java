/*
 * @(#)Reasoner.java 2010-4-3 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.ldlpprogram.reasoner;

import org.semanticweb.owlapi.model.OWLAxiom;

import at.ac.tuwien.kr.datalog.DatalogReasoner;
import at.ac.tuwien.kr.datalog.XSBDatalogReasoner;
import at.ac.tuwien.kr.dlprogram.Clause;
import at.ac.tuwien.kr.dlprogram.DLProgram;
import at.ac.tuwien.kr.dlprogram.Program;

/**
 * TODO describe this class please.
 */
public class Reasoner {

	private DLProgram dlProgram;

	DatalogReasoner datalogReasoner = new XSBDatalogReasoner();
	
	public Reasoner(DLProgram dlProgram) {
		this.dlProgram = dlProgram;
	}

	public boolean isEntailed(OWLAxiom axiom) {
		DLProgramCompiler dlProgramCompiler=new DLProgramCompiler();
		Program program = dlProgramCompiler.compile(dlProgram);
		Clause query = dlProgramCompiler.compile(axiom);
		//return datalogReasoner.query(program, query);
		return false;
	}
}
