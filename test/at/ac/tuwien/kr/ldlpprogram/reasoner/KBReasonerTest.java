package at.ac.tuwien.kr.ldlpprogram.reasoner;

import static at.ac.tuwien.kr.helper.LDLHelper.cls;
import static at.ac.tuwien.kr.helper.LDLHelper.sub;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import at.ac.tuwien.kr.dlprogram.DLProgram;
import at.ac.tuwien.kr.dlprogram.DLProgramKB;
import at.ac.tuwien.kr.dlprogram.DLProgramKBLoader;
import at.ac.tuwien.kr.dlprogram.Literal;
import at.ac.tuwien.kr.dlprogram.parser.DLProgramParser;
import at.ac.tuwien.kr.dlprogram.parser.ParseException;

public class KBReasonerTest {

	@Test
	public void testIsEntailed001() throws OWLOntologyCreationException,
			ParseException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLClass C = cls("C");
		OWLClass D = cls("D");
		OWLAxiom axiom = sub(C, D);
		OWLOntology ontology = manager.createOntology(Collections
				.singleton(axiom));

		String text = "p(a). s(a). s(b). q:-DL[C+=s;D](a), not DL[C+=p;D](b).";
		DLProgramParser parser = new DLProgramParser(new StringReader(text));
		DLProgram program = parser.program();

		DLProgramKB kb = new DLProgramKB();
		kb.setOntology(ontology);
		kb.setProgram(program);

		KBReasoner reasoner = new KBReasoner(kb);

		String queryText = "q";

		Literal query = new DLProgramParser(new StringReader(queryText))
				.literal();

		boolean entailed = reasoner.isEntailed(query);

		assertTrue(entailed);

		query = new DLProgramParser(new StringReader("s(a)")).literal();

		entailed = reasoner.isEntailed(query);

		assertTrue(entailed);
	}

	@Test
	public void testIsEntailed002() throws FileNotFoundException,
			ParseException {
		
		String path = "test/at/ac/tuwien/kr/ldlpprogram/reasoner/super";
		DLProgramKBLoader loader = new DLProgramKBLoader();
		DLProgramKB kb = loader.load(path);
		KBReasoner reasoner = new KBReasoner(kb);

		String queryText = "over(a)";

		Literal query = new DLProgramParser(new StringReader(queryText))
				.literal();

		boolean entailed = reasoner.isEntailed(query);

		assertTrue(entailed);
	}
	
	@Test
	public void testIsEntailed003() throws OWLOntologyCreationException,
			ParseException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLClass C = cls("C");
		OWLClass D = cls("D");
		OWLAxiom axiom = sub(C, D);
		OWLOntology ontology = manager.createOntology(Collections
				.singleton(axiom));

		String text = "p(a). s(a). s(b). q(X,Y):-DL[C+=s;D](X), DL[C+=p;D](Y).";
		DLProgramParser parser = new DLProgramParser(new StringReader(text));
		DLProgram program = parser.program();

		DLProgramKB kb = new DLProgramKB();
		kb.setOntology(ontology);
		kb.setProgram(program);

		KBReasoner reasoner = new KBReasoner(kb);

		String queryText = "q";

		Literal query = new DLProgramParser(new StringReader(queryText))
				.literal();

		boolean entailed = reasoner.isEntailed(query);

//		assertTrue(entailed);

		query = new DLProgramParser(new StringReader("q(X,Y)")).literal();

		List<Literal> result = reasoner.query(query);
		
		System.out.println(result);
	}

}
