package at.ac.tuwien.kr.ldlp.demo;

import static at.ac.tuwien.kr.helper.LDLHelper.*;

import java.util.List;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import edu.stanford.db.lp.ProgramClause;

import at.ac.tuwien.kr.ldlp.reasoner.AxiomCompiler;
import at.ac.tuwien.kr.ldlp.reasoner.ClosureCompiler;
import at.ac.tuwien.kr.ldlp.reasoner.ClosureCompilerTest;
import at.ac.tuwien.kr.ldlp.reasoner.LDLPCompiler;
import at.ac.tuwien.kr.ldlp.reasoner.LDLPObjectClosure;
import at.ac.tuwien.kr.ldlp.reasoner.LDLPObjectClosureBuilder;

public class CompilerDemo {

	public static void main(String[] args) {
		demo2();
	}

	private static void demo1() {
		final OWLClass Over = cls("Over");
		final OWLObjectProperty PapToRev = prop("PapToRev");
		final OWLObjectProperty Super = prop("Super");
		final OWLNamedIndividual a = ind("a");
		final OWLNamedIndividual b = ind("b");
		final OWLNamedIndividual c = ind("c");
		OWLAxiom axioms[] = new OWLAxiom[4];
		axioms[0] = sub(min(2, PapToRev, topClass()), Over);
		axioms[1] = sub(Over, all(trans(Super), Over));
		axioms[2] = assert$(Super, a, b);
		axioms[3] = assert$(Super, b, c);
		LDLPObjectClosureBuilder builder = new LDLPObjectClosureBuilder();
		LDLPObjectClosure closure = builder.build(axioms);

		System.out.println("Ontology:");
		for (int i = 0; i < axioms.length; i++) {
			System.out.println(axioms[i]);
		}

		System.out.println("-------------------------------------------------------");

		System.out.println("Closure:");
		for (OWLObject o : closure.getNamedClasses()) {
			System.out.println(o);
		}

		for (OWLObject o : closure.getNamedIndividuals()) {
			System.out.println(o);
		}

		for (OWLObject o : closure.getNamedProperties()) {
			System.out.println(o);
		}

		for (OWLObject o : closure.getComplexClassExpressions()) {
			System.out.println(o);
		}

		for (OWLObject o : closure.getComplexPropertyExpressions()) {
			System.out.println(o);
		}

		System.out.println("-------------------------------------------------------");

		ClosureCompiler closureCompiler = new ClosureCompiler();
		List<ProgramClause> clauses = closureCompiler.compile(closure);

		System.out.println("Compiled Closure:");
		for (ProgramClause clause : clauses) {
			System.out.println(clause);
		}
		System.out.println("-------------------------------------------------------");

		AxiomCompiler axiomCompiler = new AxiomCompiler();
		clauses = axiomCompiler.compile(axioms);
		System.out.println("Compiled Axioms:");
		for (ProgramClause clause : clauses) {
			System.out.println(clause);
		}
	}

	private static void demo2() {
		final OWLClass Over = cls("Over");
		final OWLObjectProperty PapToRev = prop("PapToRev");
		final OWLObjectProperty Super = prop("Super");
		final OWLNamedIndividual a = ind("a");
		final OWLNamedIndividual b = ind("b");
		final OWLNamedIndividual c = ind("c");
		OWLAxiom axioms[] = new OWLAxiom[4];
		axioms[0] = sub(min(2, PapToRev, topClass()), Over);
		axioms[1] = sub(Over, all(trans(Super), Over));
		axioms[2] = assert$(Super, a, b);
		axioms[3] = assert$(Super, b, c);

		LDLPCompiler compiler = new LDLPCompiler();
		System.out.println("Ontology:");
		for (int i = 0; i < axioms.length; i++) {
			System.out.println(axioms[i]);
		}

		System.out.println("-------------------------------------------------------");

		List<ProgramClause> clauses = compiler.compile(axioms);
		System.out.println("Compiled Ontoloy:");
		for (ProgramClause clause : clauses) {
			System.out.println(clause);
		}
	}
}
