import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;
import org.semanticweb.drew.dlprogram.Clause;
import org.semanticweb.drew.ldlp.reasoner.LDLPOntologyCompiler;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

@Ignore
public class EDITest {
	public static void main(String[] args) throws URISyntaxException, OWLOntologyCreationException, IOException {
		String bifile = "file:src/resources/MessageBI.owl";
		//URL url = EDITest.class.getResource("/MessageBI.owl");
		IRI iri = IRI.create(bifile);
		
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = manager.loadOntologyFromOntologyDocument(iri);
		
		
		LDLPOntologyCompiler compiler = new LDLPOntologyCompiler();
		System.out.println("Ontology:");
		Set<OWLAxiom> axioms = ontology.getAxioms();
		

		System.out.println("-------------------------------------------------------");

		List<Clause> clauses = compiler.compile(axioms);
		
		FileWriter writer = new FileWriter("./edi.dlv");
		
		System.out.println("Compiled Ontoloy:");
		for (Clause clause : clauses) {
			//writer.write(clause);
			writer.write(clause.toString());
			writer.write("\n");			
		}
		writer.close();
		
	}
}
