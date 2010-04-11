/*
 * @(#)ComipleOntologyFromFileDemo.java 2010-3-27 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.ldlpprogram.demo;

import static at.ac.tuwien.kr.helper.LDLHelper.cls;
import static at.ac.tuwien.kr.helper.LDLHelper.sub;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.kr.dlprogram.Clause;
import at.ac.tuwien.kr.dlprogram.DLProgram;
import at.ac.tuwien.kr.dlprogram.DLProgramKB;
import at.ac.tuwien.kr.dlprogram.DLProgramKBLoader;
import at.ac.tuwien.kr.dlprogram.Literal;
import at.ac.tuwien.kr.dlprogram.parser.DLProgramParser;
import at.ac.tuwien.kr.dlprogram.parser.ParseException;
import at.ac.tuwien.kr.ldlp.reasoner.LDLPCompiler;
import at.ac.tuwien.kr.ldlp.reasoner.LDLPReasoner;
import at.ac.tuwien.kr.ldlpprogram.reasoner.KBReasoner;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyTransitiveClosureOf;

/**
 * TODO describe this class please.
 */
public class ECAI2010Demo {

	final static Logger logger = LoggerFactory.getLogger(ECAI2010Demo.class);

	public static void demo1() throws OWLOntologyCreationException, ParseException {
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
	}

	public static void demo2() throws FileNotFoundException, ParseException{
		String path = "kb/super";
		DLProgramKBLoader loader = new DLProgramKBLoader();
		DLProgramKB kb = loader.load(path);
		KBReasoner reasoner = new KBReasoner(kb);

		String queryText = "over(a)";

		Literal query = new DLProgramParser(new StringReader(queryText))
				.literal();

		boolean entailed = reasoner.isEntailed(query);

		assertTrue(entailed);

	}
	
	
	/**
	 * @param args
	 * @throws ParseException 
	 * @throws OWLOntologyCreationException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws OWLOntologyCreationException, ParseException, FileNotFoundException {
		demo1();

		demo2();
	}

}
