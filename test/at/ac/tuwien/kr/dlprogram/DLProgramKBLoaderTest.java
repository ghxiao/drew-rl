package at.ac.tuwien.kr.dlprogram;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLAxiom;

import at.ac.tuwien.kr.dlprogram.parser.ParseException;

public class DLProgramKBLoaderTest {

	@Test
	public void testLoad() throws FileNotFoundException, ParseException {
		String path = "kb/super";
		DLProgramKBLoader loader = new DLProgramKBLoader();
		DLProgramKB kb = loader.load(path);
		
		System.out.println("-------------------------------------");
		System.out.println("Ontology:");
		
		for(OWLAxiom axiom: kb.getOntology().getAxioms()){
			System.out.println(axiom);
		}
		
		System.out.println("-------------------------------------");
		System.out.println("Program:");
		
		for(Clause clasue: kb.getProgram().getClauses()){
			System.out.println(clasue);
		}
	}

}
