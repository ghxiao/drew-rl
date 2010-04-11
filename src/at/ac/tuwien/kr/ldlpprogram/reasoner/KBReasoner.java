/*
 * @(#)Reasoner.java 2010-4-3 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.ldlpprogram.reasoner;

import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;

import at.ac.tuwien.kr.datalog.DatalogReasoner;
import at.ac.tuwien.kr.datalog.DlvDatalogReasoner;
import at.ac.tuwien.kr.dlprogram.DLProgram;
import at.ac.tuwien.kr.dlprogram.DLProgramKB;
import at.ac.tuwien.kr.dlprogram.Literal;

/**
 * TODO describe this class please.
 */
public class KBReasoner {

	private DLProgramKB kb;
	KBCompiler compiler = new KBCompiler();
	DatalogReasoner datalogReasoner = new DlvDatalogReasoner();

	DLProgram compiledClauses;

	public KBReasoner(DLProgramKB kb) {
		this.kb = kb;

		compiledClauses = compiler.compile(kb);
	}

	public boolean isEntailed(OWLClassAssertionAxiom axiom) {
		Literal query = compiler.compile(axiom);
		return isEntailed(query);

	}

	public boolean isEntailed(Literal query) {
		Literal newQuery = compiler.compileNormalLiteral(query);
		
		return datalogReasoner.query(compiledClauses, newQuery);
	}
}
