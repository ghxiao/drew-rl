package at.ac.tuwien.kr.ldlp.eval;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.profiles.OWLProfileReport;
import org.semanticweb.owlapi.profiles.OWLProfileViolation;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.kr.datalog.DatalogReasoner.TYPE;
import at.ac.tuwien.kr.dlprogram.CacheManager;
import at.ac.tuwien.kr.dlprogram.Clause;
import at.ac.tuwien.kr.dlprogram.Literal;
import at.ac.tuwien.kr.dlprogram.NormalPredicate;
import at.ac.tuwien.kr.dlprogram.Term;
import at.ac.tuwien.kr.dlprogram.Variable;
import at.ac.tuwien.kr.ldlp.profile.LDLPProfile;
import at.ac.tuwien.kr.ldlp.reasoner.LDLPReasoner;

public class VICODI_QueryEvaluation {

	 public final static String phyUri =
	 "file:benchmark/vicodi_2/vicodi_2.owl";
	//public final static String phyUri = "file:benchmark/vicodi_2/vicodi_2.owl";

	final static Logger logger = LoggerFactory.getLogger(VICODI_QueryEvaluation.class);
	private OWLOntologyManager manager = OWLManager
			.createOWLOntologyManager();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		VICODI_QueryEvaluation app = new VICODI_QueryEvaluation();
		app.run();

	}

	List<Clause> clauses = new ArrayList<Clause>();

	public VICODI_QueryEvaluation() {
	}

	public void run() {

		long t0 = System.currentTimeMillis();
		OWLOntology ontology = loadOntology("", phyUri);
		Clause query = getQuery2();

		// LDLPReasoner reasoner = new LDLPReasoner(ontology, TYPE.XSB);
		LDLPReasoner reasoner = new LDLPReasoner(ontology, TYPE.DLV);
		List<Literal> results = reasoner.query(query);

		System.out.println(results.size() + " Query Results");
//		for (Literal result : results) {
//			System.out.println(result);
//		}
		System.out.println(results.size() + " Query Results");
		long t1 = System.currentTimeMillis();
		long dt = t1 - t0;
		System.out.println("Time: " + dt + " ms");

	}

	/**
	 * http://vicodi.org/ontology#Individual
	 * 
	 * ans(X) :- Individual(X).
	 * 
	 * @return
	 */
	Clause getQuery1() {

		Clause query = new Clause();
		NormalPredicate ans = CacheManager.getInstance().getPredicate("ans", 1);
		Variable X = CacheManager.getInstance().getVariable("X");
		Literal head = new Literal(ans, X);
		query.setHead(head);
		query.getPositiveBody().add(createVICODILiteral("Individual", "X"));

		return query;

	}

	/**
	 * http://vicodi.org/ontology#Individual
	 * 
	 * ans(x, y, z) :- Military-Person(x), hasRole(y, x), related(x, z)
	 * 
	 * @return
	 */
	Clause getQuery2() {

		Clause query = new Clause();
		NormalPredicate ans = CacheManager.getInstance().getPredicate("ans", 3);
		Variable X = CacheManager.getInstance().getVariable("X");
		Variable Y = CacheManager.getInstance().getVariable("Y");
		Variable Z = CacheManager.getInstance().getVariable("Z");
		Literal head = new Literal(ans, X, Y, Z);
		query.setHead(head);
		query.getPositiveBody().add(createVICODILiteral("Military-Person", "X"));
		query.getPositiveBody().add(createVICODILiteral("hasRole", "Y", "X"));
		query.getPositiveBody().add(createVICODILiteral("related", "X", "Z"));

		return query;

	}

	public Literal createVICODILiteral(String predicateIRI, String... args) {

		NormalPredicate pred = CacheManager.getInstance()
				.getPredicate(IRI.create("http://vicodi.org/ontology#" + predicateIRI)
						.toQuotedString(), args.length);

		int n = args.length;
		Term[] terms = new Term[n];
		for (int i = 0; i < n; i++) {
			String name = args[i];
			if (name.startsWith("<")) {
				terms[i] = CacheManager.getInstance().getConstant(name);
			} else {
				terms[i] = CacheManager.getInstance().getVariable(name);
			}
		}

		Literal literal = new Literal(pred, terms);
		return literal;
	}

	private OWLOntology loadOntology(String uri, String phyUri) {

		OWLOntology ontology = null;

		System.out.println();
		System.out
				.println("------------------------------------------------------");

		logger.info("Reading file " + uri + "...");

		manager.addIRIMapper(new SimpleIRIMapper(IRI.create(uri), IRI
				.create(phyUri)));

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
			Set<OWLProfileViolation> violations = report.getViolations();
			System.out.println("The following " + violations.size()
					+ " axioms are violated");
			for (OWLProfileViolation v : violations) {
				System.out.println(v);
			}
		}
		return ontology;
	}


}
