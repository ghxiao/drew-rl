/*
 * @(#)Reasoner.java 2010-4-3 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.ldlpprogram.reasoner;

import java.util.List;

import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;

import at.ac.tuwien.kr.datalog.DatalogReasoner;
import at.ac.tuwien.kr.datalog.DLVReasoner;
import at.ac.tuwien.kr.dlprogram.DLProgram;
import at.ac.tuwien.kr.dlprogram.DLProgramKB;
import at.ac.tuwien.kr.dlprogram.Literal;
import at.ac.tuwien.kr.ldlp.reasoner.LDLPQueryResultDecompiler;

/**
 * Reasoner for DLProgram KB
 */
public class KBReasoner {

	KBCompiler compiler = new KBCompiler();

	DatalogReasoner datalogReasoner = new DLVReasoner();

	DLProgram compiledClauses;

	public KBReasoner(DLProgramKB kb) {
		compiledClauses = compiler.compile(kb);
	}

	public boolean isEntailed(OWLClassAssertionAxiom axiom) {
		Literal query = compiler.compile(axiom);
		return isEntailed(query);
	}

	public boolean isEntailed(Literal query) {
		Literal newQuery = compiler.compileNormalLiteral(query);
		return datalogReasoner.isEntailed(compiledClauses, newQuery);
	}

	public List<Literal> query(Literal q) {

		//LDLPQueryCompiler queryComiler = new LDLPQueryCompiler();
		//Literal query = queryComiler.compileLiteral(q);

		
		List<Literal> result = datalogReasoner.query(compiledClauses, q);
		
		LDLPProgramQueryResultDecompiler decompiler = new LDLPProgramQueryResultDecompiler();
		
		result = decompiler.decompileLiterals(result);
				
		return result;


	}
}
