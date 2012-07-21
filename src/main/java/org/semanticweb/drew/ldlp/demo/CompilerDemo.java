package org.semanticweb.drew.ldlp.demo;

import static org.semanticweb.drew.helper.LDLHelper.all;
import static org.semanticweb.drew.helper.LDLHelper.assert$;
import static org.semanticweb.drew.helper.LDLHelper.cls;
import static org.semanticweb.drew.helper.LDLHelper.ind;
import static org.semanticweb.drew.helper.LDLHelper.min;
import static org.semanticweb.drew.helper.LDLHelper.prop;
import static org.semanticweb.drew.helper.LDLHelper.sub;
import static org.semanticweb.drew.helper.LDLHelper.topClass;
//import static at.ac.tuwien.kr.helper.LDLHelper.trans;

import java.util.List;

import org.semanticweb.drew.dlprogram.Clause;
import org.semanticweb.drew.ldlp.reasoner.LDLPAxiomCompiler;
import org.semanticweb.drew.ldlp.reasoner.LDLPClosure;
import org.semanticweb.drew.ldlp.reasoner.LDLPClosureBuilder;
import org.semanticweb.drew.ldlp.reasoner.LDLPClosureCompiler;
import org.semanticweb.drew.ldlp.reasoner.LDLPOntologyCompiler;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;


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
		//axioms[1] = sub(Over, all(trans(Super), Over));
		axioms[2] = assert$(Super, a, b);
		axioms[3] = assert$(Super, b, c);
		LDLPClosureBuilder builder = new LDLPClosureBuilder();
		LDLPClosure closure = builder.build(axioms);

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

		LDLPClosureCompiler closureCompiler = new LDLPClosureCompiler();
		List<Clause> clauses = closureCompiler.compile(closure);

		System.out.println("Compiled Closure:");
		for (Clause clause : clauses) {
			System.out.println(clause);
		}
		System.out.println("-------------------------------------------------------");

		LDLPAxiomCompiler axiomCompiler = new LDLPAxiomCompiler();
		clauses = axiomCompiler.compile(axioms);
		System.out.println("Compiled Axioms:");
		for (Clause clause : clauses) {
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
		//axioms[1] = sub(Over, all(trans(Super), Over));
		axioms[2] = assert$(Super, a, b);
		axioms[3] = assert$(Super, b, c);

		LDLPOntologyCompiler compiler = new LDLPOntologyCompiler();
		System.out.println("Ontology:");
		for (int i = 0; i < axioms.length; i++) {
			System.out.println(axioms[i]);
		}

		System.out.println("-------------------------------------------------------");

		List<Clause> clauses = compiler.compile(axioms);
		System.out.println("Compiled Ontoloy:");
		for (Clause clause : clauses) {
			System.out.println(clause);
		}
	}

}
