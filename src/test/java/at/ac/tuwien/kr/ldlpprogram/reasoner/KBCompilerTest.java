package at.ac.tuwien.kr.ldlpprogram.reasoner;

import static at.ac.tuwien.kr.helper.LDLHelper.all;
import static at.ac.tuwien.kr.helper.LDLHelper.assert$;
import static at.ac.tuwien.kr.helper.LDLHelper.cls;
import static at.ac.tuwien.kr.helper.LDLHelper.ind;
import static at.ac.tuwien.kr.helper.LDLHelper.min;
import static at.ac.tuwien.kr.helper.LDLHelper.prop;
import static at.ac.tuwien.kr.helper.LDLHelper.sub;
import static at.ac.tuwien.kr.helper.LDLHelper.topClass;
import static at.ac.tuwien.kr.helper.LDLHelper.trans;
import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import at.ac.tuwien.kr.dlprogram.Clause;
import at.ac.tuwien.kr.dlprogram.DLInputSignature;
import at.ac.tuwien.kr.dlprogram.DLProgram;
import at.ac.tuwien.kr.dlprogram.parser.DLProgramParser;
import at.ac.tuwien.kr.dlprogram.parser.ParseException;
import at.ac.tuwien.kr.ldlp.reasoner.LDLPAxiomCompiler;
import at.ac.tuwien.kr.ldlp.reasoner.LDLPClosure;
import at.ac.tuwien.kr.ldlp.reasoner.LDLPClosureBuilder;
import at.ac.tuwien.kr.ldlp.reasoner.LDLPClosureCompiler;

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
		OWLAxiom axioms[] = new OWLAxiom[4];
		axioms[0] = sub(min(2, PapToRev, topClass()), Over);
		axioms[1] = sub(Over, all(trans(Super), Over));
		axioms[2] = assert$(Super, a, b);
		axioms[3] = assert$(Super, b, c);
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
