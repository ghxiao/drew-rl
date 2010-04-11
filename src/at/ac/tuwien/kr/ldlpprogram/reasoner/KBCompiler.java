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
import org.semanticweb.owlapi.model.OWLLogicalEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.kr.dlprogram.CacheManager;
import at.ac.tuwien.kr.dlprogram.Clause;
import at.ac.tuwien.kr.dlprogram.DLAtomPredicate;
import at.ac.tuwien.kr.dlprogram.DLInputOperation;
import at.ac.tuwien.kr.dlprogram.DLInputSignature;
import at.ac.tuwien.kr.dlprogram.DLProgram;
import at.ac.tuwien.kr.dlprogram.DLProgramKB;
import at.ac.tuwien.kr.dlprogram.Literal;
import at.ac.tuwien.kr.dlprogram.NormalPredicate;
import at.ac.tuwien.kr.dlprogram.Predicate;
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
	List<Clause> compileProgram(DLProgram program) {

		List<Clause> result = new ArrayList<Clause>();

		for (Clause clause : program.getClauses()) {
			Clause newClause = new Clause();
			newClause.setHead(clause.getHead());
			newClause.getPositiveBody().addAll(clause.getNormalPositiveBody());
			newClause.getNegativeBody().addAll(clause.getNegativeBody());
			for (Literal lit : clause.getDLAtoms()) {
				DLAtomPredicate p = (DLAtomPredicate) (lit.getPredicate());
				DLInputSignature inputSigature = p.getInputSigature();
				OWLLogicalEntity query = p.getQuery();
				String predicate = DatalogObjectFactory.getInstance()
						.getPredicate(query);
				String sub = KBCompilerManager.getInstance().getSubscript(
						inputSigature);
				NormalPredicate newPredicate = CacheManager.getInstance()
						.getPredicate(predicate + "_" + sub, p.getArity());
				Literal newLit = new Literal(newPredicate, lit.getTerms());
				newClause.getPositiveBody().add(newLit);
			}
			result.add(newClause);
			logger.debug("{} -> {}", clause, newClause);
		}

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

	List<Clause> subscript(List<Clause> clauses, DLInputSignature signature) {

		List<Clause> newClauses = new ArrayList<Clause>();
		String sub = KBCompilerManager.getInstance().getSubscript(signature);

		for (Clause clause : clauses) {
			Literal head = clause.getHead();
			List<Literal> body = clause.getPositiveBody();
			// Note: In LDLp, we only have positive body
			Literal newHead = subscript(head, sub);
			Clause newClause = new Clause();
			newClause.setHead(newHead);
			for (Literal lit : body) {
				Literal newLit = subscript(lit, sub);
				newClause.getPositiveBody().add(newLit);
			}
			newClauses.add(newClause);

			logger.debug("{}\n  ->\n{}", clause, newClause);
		}

		return newClauses;
	}

	private Literal subscript(Literal literal, String sub) {
		NormalPredicate predicate = (NormalPredicate) literal.getPredicate();
		int arity = predicate.getArity();
		String name = predicate.getName();
		String newName = name + "_" + sub;
		NormalPredicate newPredicate = CacheManager.getInstance().getPredicate(
				newName, arity);
		Literal newLiteral = new Literal(newPredicate, literal.getTerms());

		return newLiteral;
	}

}
