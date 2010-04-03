/*
 * @(#)DLProgramCompiler.java 2010-4-3 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.ldlpprogram.reasoner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

import edu.stanford.db.lp.ProgramClause;

import at.ac.tuwien.kr.dlprogram.Clause;
import at.ac.tuwien.kr.dlprogram.DLInputSignature;
import at.ac.tuwien.kr.dlprogram.DLProgram;
import at.ac.tuwien.kr.dlprogram.Program;
import at.ac.tuwien.kr.ldlp.reasoner.LDLPCompiler;

/**
 * TODO describe this class please.
 */
public class DLProgramCompiler {

	public Program compile(DLProgram dlProgram) {
		List<Clause> result = new ArrayList<Clause>();
		
		final OWLOntology ontology = dlProgram.getOntology();
		LDLPCompiler ldlpCompiler = new LDLPCompiler();
		final List<ProgramClause> compiledOntology = ldlpCompiler.complile(ontology);
		final Program program = dlProgram.getProgram();
		final Set<DLInputSignature> dlInputSignatures = program.getIDlInputSignatures();
		for (DLInputSignature signature : dlInputSignatures) {
			result.addAll(subscript(compiledOntology,signature));
		}
		
		for (DLInputSignature signature : dlInputSignatures) {
			result.addAll(compileSignature(signature));
		}
		
		result.addAll(compileProgram(program));
		
		return null;
	}

	

	private Collection<? extends Clause> compileProgram(Program program) {
		// TODO Auto-generated method stub
		return null;
	}



	private Collection<? extends Clause> compileSignature(DLInputSignature signature) {
		return null;
	}

	private Collection<? extends Clause> subscript(List<ProgramClause> clauses, DLInputSignature signature) {

		return null;
	}

	public Clause compile(OWLAxiom axiom) {
		// TODO Auto-generated method stub
		return null;
	}

}
