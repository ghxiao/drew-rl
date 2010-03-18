package at.ac.tuwien.kr.ldlp.profile;

import static org.junit.Assert.*;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.profiles.OWLProfileReport;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

public class LDLPProfileTest {

	@Test
	public void testCheckOntology() {
		String uri = "http://www.kr.tuwien.ac.at/staff/xiao/ldl/role_intersection_and_union.ldl";

		String phyUri = "file:kb/role_intersection_and_union.ldl";
		
		OWLOntology ontology = loadOntology(uri, phyUri);
		
		LDLPProfile profile = new LDLPProfile();
		
		OWLProfileReport report = profile.checkOntology(ontology);
		
		assertFalse(report.isInProfile());

	}

	private static OWLOntology loadOntology(String uri,String phyUri) {
		OWLOntology ontology = null;

		System.out.println();
		System.out.println("------------------------------------------------------");

		System.out.println("Reading file " + uri + "...");
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		
		manager.addIRIMapper(new SimpleIRIMapper(IRI.create(uri), IRI.create(phyUri)));

		try {
			ontology = manager.loadOntology(IRI.create(uri));

			for (OWLAxiom axiom : ontology.getAxioms()) {
				System.out.println(axiom);
			}

			return ontology;
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
		return ontology;
	}
}
