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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.kr.dlprogram.CacheManager;
import at.ac.tuwien.kr.dlprogram.Clause;
import at.ac.tuwien.kr.dlprogram.DLInputOperation;
import at.ac.tuwien.kr.dlprogram.DLInputSignature;
import at.ac.tuwien.kr.dlprogram.DLProgram;
import at.ac.tuwien.kr.dlprogram.DLProgramKB;
import at.ac.tuwien.kr.dlprogram.Literal;
import at.ac.tuwien.kr.dlprogram.NormalPredicate;
import at.ac.tuwien.kr.dlprogram.Variable;
import at.ac.tuwien.kr.ldlp.reasoner.DatalogObjectFactory;
import at.ac.tuwien.kr.ldlp.reasoner.LDLPCompiler;

/**
 * KBCompiler: compile the dl-program KB to a datalog^n program.
 */
public class KBCompiler {

	final static Logger logger = LoggerFactory.getLogger(KBCompiler.class);

	Variable X = CacheManager.getInstance().getVariable("X");
	Variable Y = CacheManager.getInstance().getVariable("Y");

	public DLProgram compile(DLProgramKB kb) {
		List<Clause> result = new ArrayList<Clause>();

		final OWLOntology ontology = kb.getOntology();
		LDLPCompiler ldlpCompiler = new LDLPCompiler();
		final List<Clause> compiledOntology = ldlpCompiler.complile(ontology);
		final DLProgram program = kb.getProgram();
		final Set<DLInputSignature> dlInputSignatures = program
				.getDLInputSignatures();
		for (DLInputSignature signature : dlInputSignatures) {
			result.addAll(subscript(compiledOntology, signature));
		}

		for (DLInputSignature signature : dlInputSignatures) {
			result.addAll(compileSignature(signature));
		}

		result.addAll(compileProgram(program));

		DLProgram resultProgram = new DLProgram();
		resultProgram.getClauses().addAll(result);

		return resultProgram;
	}

	/**
	 * P -> P^{ord} replace all the DLAtom with a ordinary atom
	 * 
	 */
	private List<Clause> compileProgram(DLProgram program) {

		List<Clause> result = new ArrayList<Clause>();

		return result;
	}

	List<Clause> compileSignature(DLInputSignature signature) {
		String sub = KBCompilerManager.getInstance().getSubscript(signature);
		List<Clause> clauses = new ArrayList<Clause>();
		for (DLInputOperation op : signature.getOperations()) {
			String name = DatalogObjectFactory.getInstance().getPredicate(
					op.getDLPredicate())
					+ "_" + sub;
			NormalPredicate inputPredicate = op.getInputPredicate();
			int arity = inputPredicate.getArity();
			NormalPredicate predicate = CacheManager.getInstance()
					.getPredicate(name, arity);
			Literal head = null;
			Literal body = null;
			if (arity == 1) {
				head = new Literal(predicate, X);
				body = new Literal(inputPredicate, X);
			} else if (arity == 2) {
				head = new Literal(predicate, X, Y);
				body = new Literal(inputPredicate, X, Y);
			} else {
				throw new IllegalArgumentException();
			}

			Clause clause = new Clause();
			clause.setHead(head);
			clause.getPositiveBody().add(body);
			clauses.add(clause);
			logger.debug("{} -> {}", signature, clause);
		}

		return clauses;
	}

	private Collection<? extends Clause> subscript(
			List<Clause> compiledOntology, DLInputSignature signature) {

		return null;
	}

	public Clause compile(OWLAxiom axiom) {
		// TODO Auto-generated method stub
		return null;
	}

}
