package at.ac.tuwien.kr.ldlp.benchmark;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Set;

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

import at.ac.tuwien.kr.dlprogram.CacheManager;
import at.ac.tuwien.kr.dlprogram.Clause;
import at.ac.tuwien.kr.dlprogram.Constant;
import at.ac.tuwien.kr.dlprogram.Literal;
import at.ac.tuwien.kr.dlprogram.NormalPredicate;
import at.ac.tuwien.kr.dlprogram.Variable;
import at.ac.tuwien.kr.ldlp.profile.LDLPProfile;
import at.ac.tuwien.kr.ldlp.reasoner.LDLPReasoner;

public class UBATest {
	public final static String uri = "";

	public final static String phyUri = "file:benchmark/uba/University0_0.owl";
	
	//public final static String phyUri = "file:benchmark/uba/univ-bench.owl";
	
	
	
	final static Logger logger = LoggerFactory.getLogger(UBATest.class);
	private static OWLOntologyManager manager = OWLManager
			.createOWLOntologyManager();

	public static void main(String[] args) {
		loadOntology(uri, phyUri);
	}

	private static void loadOntology(String uri, String phyUri) {
		
		//setOut("out.txt");
		
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
			System.out.println("The following "+ violations.size() + " axioms are violated");
			for (OWLProfileViolation v : violations) {
				System.out.println(v);
			}
		}

		Clause query = new Clause();
		NormalPredicate ans = CacheManager.getInstance().getPredicate("ans", 1);
		Variable X = CacheManager.getInstance().getVariable("X");
		Literal head = new Literal(ans, X);

		String GraduateStudentClass = IRI.create(
				"http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#GraduateStudent").toQuotedString();
		NormalPredicate GraduateStudentPredicate = CacheManager.getInstance()
				.getPredicate(GraduateStudentClass, 1);
		Literal body1 = new Literal(GraduateStudentPredicate, X);

		String takesCourse = IRI.create("http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#takesCourse")
				.toQuotedString();
		NormalPredicate takesCoursePredicate = CacheManager.getInstance()
				.getPredicate(takesCourse, 2);
		String GraduateCourse0 = IRI.create(				
				
				//"http://www.Department0.University0.edu/GraduateCourse0"
				"http://www.Department0.University0.edu/GraduateCourse0"
				)
				.toQuotedString();

		Constant GraduateCourse0Constant = CacheManager.getInstance()
				.getConstant(GraduateCourse0);

		Literal body2 = new Literal(takesCoursePredicate, X,
				GraduateCourse0Constant);
		
		query.setHead(head);
		query.getPositiveBody().add(body1);
		query.getPositiveBody().add(body2);
		
		LDLPReasoner reasoner = new LDLPReasoner(ontology);
		reasoner.query(query);

	}
	
	private PrintStream setOut(PrintStream out) {
		System.out.flush();
		PrintStream oldOut = System.out;
		System.setOut(out);
		return oldOut;

	}

	private static PrintStream setOut(String outFileName) {
		System.out.flush();
		PrintStream oldOut = System.out;
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(outFileName, false);
		} catch (FileNotFoundException e) {

		}
		PrintStream ps = new PrintStream(out);
		System.setOut(ps);
		return oldOut;

	}
}
