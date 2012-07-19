package at.ac.tuwien.kr.ldlp.eval;

import java.util.HashMap;
import java.util.Map;

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

	final static Logger logger = LoggerFactory.getLogger(ProfileEvaluator.class);
	private static OWLOntologyManager manager = OWLManager
			.createOWLOntologyManager();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// loadAndCheck("","file:benchmark/lubm_1/lubm_1.owl");
		loadAndCheck("", "file:benchmark/galen/galen.owl","Galen");
		loadAndCheck("", "file:benchmark/dolce/dolce.owl","Dolce");
		loadAndCheck("", "file:benchmark/wine_0/wine_0.owl","Wine");
		loadAndCheck("", "file:benchmark/vicodi_0/vicodi_0.owl","Vicodi_0");
		//loadAndCheck("", "file:benchmark/vicodi_1/vicodi_1.owl","Vicodi_1");
		//loadAndCheck("", "file:benchmark/vicodi_2/vicodi_2.owl","Vicodi_2");
		loadAndCheck("", "file:benchmark/semintec_0/semintec_0.owl","Seminitec");
		loadAndCheck("", "file:benchmark/uba/University0_0.owl","LUBM");
		// loadAndCheck("",
		// "file:benchmark/dolce_no_transitivity/dolce_no_transitivity.owl");
	}

	private static void loadAndCheck(String uri, String phyUri, String name) {

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
			
			System.out.println("Ontology : " + name);
			
			//System.out.println(ontology);
			
			System.out.print ("Logical Axioms : " + ontology.getLogicalAxiomCount() + "  ");
			
			System.out.print("individuls : "+ ontology.getIndividualsInSignature().size()+ "  ");
			
			System.out.print("classes : "+ ontology.getClassesInSignature().size()+ "  ");
			
			System.out.print("object properties : " + ontology.getObjectPropertiesInSignature().size()+ "  ");
			
			System.out.print("data propterties : " + ontology.getDataPropertiesInSignature().size()+ "  ");
			
			
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}

		LDLPProfile profile = new LDLPProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		reportViolations(report, ontology.getLogicalAxiomCount());
		
		manager.removeOntology(ontology);

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

	static void reportViolations(OWLProfileReport report, int logicalAxioms) {
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

			System.out.println(String.format("%d / %d = %f", axiomCount.size(), logicalAxioms, axiomCount.size() / (double)logicalAxioms));
			
//			for (Entry<Class<?>, Integer> entry : clsCount.entrySet()) {
//				System.out.println(String.format("%s => %s", entry.getKey().getSimpleName(), entry.getValue()));
//			}

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
