package at.ac.tuwien.kr.ldlp.benchmark;

import misc.Slf4jExample;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.profiles.OWLProfileReport;
import org.semanticweb.owlapi.profiles.OWLProfileViolation;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.kr.ldlp.profile.LDLPProfile;

public class UBATest {
	public final static String uri = "";

	public final static String phyUri = "file:benchmark/uba/University0_1.owl";
	final static Logger logger = LoggerFactory.getLogger(UBATest.class);
	private static OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

	public static void main(String[] args) {
		loadOntology(uri, phyUri);
	}

	private static void loadOntology(String uri, String phyUri) {
		OWLOntology ontology = null;

		System.out.println();
		System.out.println("------------------------------------------------------");

		logger.info("Reading file " + uri + "...");

		manager.addIRIMapper(new SimpleIRIMapper(IRI.create(uri), IRI.create(phyUri)));

		try {
			ontology = manager.loadOntology(IRI.create(uri));

			// for (OWLAxiom axiom : ontology.getAxioms()) {
			// System.out.println(axiom);
			// }
			logger.info("Loading complete");
			System.out.println(ontology);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}

		LDLPProfile profile = new LDLPProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		if (report.isInProfile()) {
			System.out.println("The ontology is in LDL+ profile");
		} else {
			System.out.println("The ontology is not in LDL+ profile:");
			for (OWLProfileViolation v : report.getViolations()) {
				System.out.println(v);
			}
		}
	}
}
