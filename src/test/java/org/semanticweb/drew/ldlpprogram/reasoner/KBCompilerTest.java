package org.semanticweb.drew.ldlpprogram.reasoner;

//import static at.ac.tuwien.kr.helper.LDLHelper.trans;
import static org.junit.Assert.assertEquals;
import static org.semanticweb.drew.helper.LDLHelper.all;
import static org.semanticweb.drew.helper.LDLHelper.assert$;
import static org.semanticweb.drew.helper.LDLHelper.cls;
import static org.semanticweb.drew.helper.LDLHelper.ind;
import static org.semanticweb.drew.helper.LDLHelper.min;
import static org.semanticweb.drew.helper.LDLHelper.prop;
import static org.semanticweb.drew.helper.LDLHelper.sub;
import static org.semanticweb.drew.helper.LDLHelper.topClass;

import java.io.StringReader;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.drew.dlprogram.Clause;
import org.semanticweb.drew.dlprogram.DLInputSignature;
import org.semanticweb.drew.dlprogram.DLProgram;
import org.semanticweb.drew.dlprogram.parser.DLProgramParser;
import org.semanticweb.drew.dlprogram.parser.ParseException;
import org.semanticweb.drew.ldlp.reasoner.LDLPAxiomCompiler;
import org.semanticweb.drew.ldlp.reasoner.LDLPClosure;
import org.semanticweb.drew.ldlp.reasoner.LDLPClosureBuilder;
import org.semanticweb.drew.ldlp.reasoner.LDLPClosureCompiler;
import org.semanticweb.drew.ldlpprogram.reasoner.KBCompiler;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;


public class KBCompilerTest {

	@Test
	public void testCompileSignature001() throws ParseException {
		String text = "q :- DL[S1 += p1, S2 += p1;S1](a), DL[S1 += p1, E2 += q2;E2](a,b). p1(c). q2(c,d).";
		DLProgram program = new DLProgramParser(new StringReader(text))
				.program();

		Set<DLInputSignature> dlInputSignatures = program
				.getDLInputSignatures();

		assertEquals(2, dlInputSignatures.size());

		KBCompiler compiler = new KBCompiler();

		for (DLInputSignature signature : dlInputSignatures) {
			compiler.compileSignature(signature);
		}
	}

	@Test
	public void testCompileSignature002() throws ParseException {
		String text = "q :- DL[S1 += p1, S2 += p1;S1](a), DL[;E2](a,b). p1(c). q2(c,d).";
		DLProgram program = new DLProgramParser(new StringReader(text))
				.program();

		Set<DLInputSignature> dlInputSignatures = program
				.getDLInputSignatures();

		assertEquals(2, dlInputSignatures.size());

		KBCompiler compiler = new KBCompiler();

		for (DLInputSignature signature : dlInputSignatures) {
			compiler.compileSignature(signature);
		}
	}

	@Test
	public void testSubscript() throws ParseException {
		String text = "q :- DL[S1 += p1, S2 += p1;S1](a), DL[;E2](a,b). p1(c). q2(c,d).";
		DLProgram program = new DLProgramParser(new StringReader(text))
				.program();

		Set<DLInputSignature> dlInputSignatures = program
				.getDLInputSignatures();

		assertEquals(2, dlInputSignatures.size());

		final OWLClass Over = cls("Over");
		final OWLObjectProperty PapToRev = prop("PapToRev");
		final OWLObjectProperty Super = prop("Super");
		final OWLNamedIndividual a = ind("a");
		final OWLNamedIndividual b = ind("b");
		final OWLNamedIndividual c = ind("c");
		OWLAxiom axioms[] = new OWLAxiom[3];
		axioms[0] = sub(min(2, PapToRev, topClass()), Over);
		//axioms[1] = sub(Over, all(trans(Super), Over));
		axioms[1] = assert$(Super, a, b);
		axioms[2] = assert$(Super, b, c);
		LDLPClosureBuilder builder = new LDLPClosureBuilder();
		LDLPClosure closure = builder.build(axioms);

		LDLPClosureCompiler closureCompiler = new LDLPClosureCompiler();
		List<Clause> clauses = closureCompiler.compile(closure);

		LDLPAxiomCompiler axiomCompiler = new LDLPAxiomCompiler();
		clauses = axiomCompiler.compile(axioms);
		System.out.println("Compiled Axioms:");
		for (Clause clause : clauses) {
			System.out.println(clause);
		}

		KBCompiler kbCompiler = new KBCompiler();

		for (DLInputSignature signature : dlInputSignatures) {
			kbCompiler.subscript(clauses, signature);
		}
		
		kbCompiler.compileProgram(program);
	}



}
