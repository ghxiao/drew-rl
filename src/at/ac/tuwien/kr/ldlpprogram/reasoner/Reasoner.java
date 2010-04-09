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
import at.ac.tuwien.kr.dlprogram.DLProgramKB;
import at.ac.tuwien.kr.dlprogram.DLProgram;

/**
 * TODO describe this class please.
 */
public class Reasoner {

	private DLProgramKB dlProgram;

	DatalogReasoner datalogReasoner = new XSBDatalogReasoner();
	
	public Reasoner(DLProgramKB dlProgram) {
		this.dlProgram = dlProgram;
	}

	public boolean isEntailed(OWLAxiom axiom) {
		KBCompiler dlProgramCompiler=new KBCompiler();
		DLProgram program = dlProgramCompiler.compile(dlProgram);
		Clause query = dlProgramCompiler.compile(axiom);
		//return datalogReasoner.query(program, query);
		return false;
	}
}
