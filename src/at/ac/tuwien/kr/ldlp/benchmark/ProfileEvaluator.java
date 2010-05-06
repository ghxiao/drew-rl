package at.ac.tuwien.kr.ldlp.benchmark;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

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

public class ProfileEvaluator {
	public final static String phyUri = "file:benchmark/uba/univ-bench.owl";

	final static Logger logger = LoggerFactory.getLogger(UBATest.class);
	private static OWLOntologyManager manager = OWLManager
			.createOWLOntologyManager();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// loadAndCheck("","file:benchmark/lubm_1/lubm_1.owl");
		loadAndCheck("", "file:benchmark/galen/galen.owl");
		loadAndCheck("", "file:benchmark/dolce/dolce.owl");
		loadAndCheck("", "file:benchmark/wine_0/wine_0.owl");
		loadAndCheck("", "file:benchmark/vicodi_0/vicodi_0.owl");
		loadAndCheck("", "file:benchmark/semintec_0/semintec_0.owl");
		// loadAndCheck("",
		// "file:benchmark/dolce_no_transitivity/dolce_no_transitivity.owl");
	}

	private static void loadAndCheck(String uri, String phyUri) {

		// setOut("out.txt");

		OWLOntology ontology = null;

		System.out.println();
		System.out
				.println("------------------------------------------------------");

		// logger.info("Reading file " + uri + "...");

		manager.addIRIMapper(new SimpleIRIMapper(IRI.create(uri), IRI
				.create(phyUri)));

		try {
			ontology = manager.loadOntology(IRI.create(uri));

			// for (OWLAxiom axiom : ontology.getAxioms()) {
			// System.out.println(axiom);
			// }
			// logger.info("Loading complete");
			System.out.println(ontology);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}

		LDLPProfile profile = new LDLPProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		reportViolations(report);

		// System.out.println(report);

		// if (report.isInProfile()) {
		// System.out.println("The ontology is in LDL+ profile");
		// } else {
		// System.out.println("The ontology is not in LDL+ profile:");
		// Set<OWLProfileViolation> violations = report.getViolations();
		// System.out.println("The following " + violations.size() +
		// " axioms are violated");
		// for (OWLProfileViolation v : violations) {
		// System.out.println(v.getAxiom().getClass().getSimpleName());
		// // System.out.println(v);
		// }
		// }
	}

	static void reportViolations(OWLProfileReport report) {
		Map<Class<?>, Integer> clsCount = new HashMap<Class<?>, Integer>();

		Map<OWLAxiom, Integer> axiomCount = new HashMap<OWLAxiom, Integer>();

		for (OWLProfileViolation v : report.getViolations()) {
			final OWLAxiom axiom = v.getAxiom();
			inc(axiomCount, axiom);
		}

		for (OWLAxiom axiom : axiomCount.keySet()) {
			final Class<? extends OWLAxiom> cls = axiom.getClass();
			inc(clsCount, cls);
		}

		if (report.isInProfile()) {
			System.out.println("The ontology is in LDL+ profile.");
		} else {
			System.out.println("The ontology is not in LDL+ profile:");

			System.out.println(axiomCount.size() + " axioms are violated ");

			for (Entry<Class<?>, Integer> entry : clsCount.entrySet()) {
				System.out.println(String.format("%s => %s", entry.getKey().getSimpleName(), entry.getValue()));
			}

			// System.out.println("The following " + violations.size() +
			// " axioms are violated");
			// for (OWLProfileViolation v : violations) {
			// System.out.println(v.getAxiom().getClass().getSimpleName());
			// // System.out.println(v);
			// }
		}

	}

	private static <K> void inc(Map<K, Integer> map, final K key) {
		if (map.containsKey(key)) {
			map.put(key, map.get(key) + 1);
		} else {
			map.put(key, 1);
		}
	}
}
