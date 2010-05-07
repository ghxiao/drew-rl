package at.ac.tuwien.kr.ldlp.benchmark;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
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

import at.ac.tuwien.kr.datalog.DatalogReasoner.TYPE;
import at.ac.tuwien.kr.dlprogram.CacheManager;
import at.ac.tuwien.kr.dlprogram.Clause;
import at.ac.tuwien.kr.dlprogram.Constant;
import at.ac.tuwien.kr.dlprogram.Literal;
import at.ac.tuwien.kr.dlprogram.NormalPredicate;
import at.ac.tuwien.kr.dlprogram.Variable;
import at.ac.tuwien.kr.ldlp.profile.LDLPProfile;
import at.ac.tuwien.kr.ldlp.reasoner.LDLPReasoner;

public class LUBM_Query {
	public final static String uri = "";

	public final static String phyUri = "file:benchmark/uba/University0_0.owl";

	final static Logger logger = LoggerFactory.getLogger(LUBM_Query.class);
	private static OWLOntologyManager manager = OWLManager
			.createOWLOntologyManager();

	public static void main(String[] args) {
		loadOntology(uri, phyUri);
	}

	private static void loadOntology(String uri, String phyUri) {

		// setOut("out.txt");

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

		Clause query = getQuery4();

		//LDLPReasoner reasoner = new LDLPReasoner(ontology, TYPE.XSB);
		LDLPReasoner reasoner = new LDLPReasoner(ontology, TYPE.DLV);
		List<Literal> results = reasoner.query(query);
		
		System.out.println("Query Results");
		for(Literal result:results){
			System.out.println(result);
		}

	}

	/**
	 * *Query1* (type GraduateStudent ?X)<br/>
	 * (takesCourse ?X http://www.Department0.University0.edu/GraduateCourse0)<br/>
	 * /- This query bears large input and high selectivity. It queries about<br/>
	 * just one class and one property and does not assume any hierarchy<br/>
	 * information or inference./<br/>
	 * 
	 * DL Query: [GraduateStudent and takesCourse value GraduateCourse0]<br/>
	 * 
	 * 4 results: GraduateStudent44, 101, 124, 142<br/>
	 */
	private static Clause getQuery1() {
		Clause query = new Clause();
		NormalPredicate ans = CacheManager.getInstance().getPredicate("ans", 1);
		Variable X = CacheManager.getInstance().getVariable("X");
		Literal head = new Literal(ans, X);

		String GraduateStudentClass = IRI
				.create(
						"http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#GraduateStudent")
				.toQuotedString();
		NormalPredicate GraduateStudentPredicate = CacheManager.getInstance()
				.getPredicate(GraduateStudentClass, 1);
		Literal body1 = new Literal(GraduateStudentPredicate, X);

		String takesCourse = IRI
				.create(
						"http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#takesCourse")
				.toQuotedString();
		NormalPredicate takesCoursePredicate = CacheManager.getInstance()
				.getPredicate(takesCourse, 2);
		String GraduateCourse0 = IRI.create(
				"http://www.Department0.University0.edu/GraduateCourse0")
				.toQuotedString();

		Constant GraduateCourse0Constant = CacheManager.getInstance()
				.getConstant(GraduateCourse0);

		Literal body2 = new Literal(takesCoursePredicate, X,
				GraduateCourse0Constant);

		query.setHead(head);
		query.getPositiveBody().add(body1);
		query.getPositiveBody().add(body2);
		return query;
	}

	/**
	 * /*Query3* (type Publication ?X) <br/>
	 * (publicationAuthor ?X
	 * http://www.Department0.University0.edu/AssistantProfessor0) <br/>
	 * /- This query is similar to Query 1 but class Publication has a wide
	 * hierarchy./<br/>
	 * 
	 * DL Query: [Publication and publicationAuthor value AssistantProfessor0]
	 * 
	 * Answer:
	 * 
	 * <ul>
	 * <li>X</li>
	 * <li>
	 * http://www.Department0.University0.edu/AssistantProfessor0/Publication0</li>
	 * <li>
	 * http://www.Department0.University0.edu/AssistantProfessor0/Publication1</li>
	 * <li>
	 * http://www.Department0.University0.edu/AssistantProfessor0/Publication2</li>
	 * <li>
	 * http://www.Department0.University0.edu/AssistantProfessor0/Publication3</li>
	 * <li>
	 * http://www.Department0.University0.edu/AssistantProfessor0/Publication4</li>
	 * <li>
	 * http://www.Department0.University0.edu/AssistantProfessor0/Publication5</li>
	 * </ul>
	 */
	@SuppressWarnings("unused")
	private static Clause getQuery3() {
		Clause query = new Clause();
		NormalPredicate ans = CacheManager.getInstance().getPredicate("ans", 1);
		Variable X = CacheManager.getInstance().getVariable("X");
		Literal head = new Literal(ans, X);

		String Publication = IRI
				.create(
						"http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#Publication")
				.toQuotedString();
		NormalPredicate PublicationPredicate = CacheManager.getInstance()
				.getPredicate(Publication, 1);
		Literal body1 = new Literal(PublicationPredicate, X);

		String publicationAuthor = IRI
				.create(
						"http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#publicationAuthor")
				.toQuotedString();
		NormalPredicate publicationAuthorPredicate = CacheManager.getInstance()
				.getPredicate(publicationAuthor, 2);
		String AssistantProfessor0 = IRI.create(
				"http://www.Department0.University0.edu/AssistantProfessor0")
				.toQuotedString();

		Constant GraduateCourse0Constant = CacheManager.getInstance()
				.getConstant(AssistantProfessor0);

		Literal body2 = new Literal(publicationAuthorPredicate, X,
				GraduateCourse0Constant);

		query.setHead(head);
		query.getPositiveBody().add(body1);
		query.getPositiveBody().add(body2);
		return query;
	}

	/**
	 * *Query4*
	 * 
	 * <pre>
	 * (type Professor ?X)
	 * 	(worksFor ?X http://www.Department0.University0.edu)
	 * 	(name ?X ?Y1)
	 * 	(emailAddress ?X ?Y2)
	 * 	(telephone ?X ?Y3)
	 * </pre>
	 * 
	 * /- This query has small input and high selectivity. It assumes subClassOf
	 * relationship between Professor and its subclasses. Class Professor has a
	 * wide hierarchy. Another feature is that it queries about multiple
	 * properties of a single class. 
	 * 
	 * Results:
	 * <pre>
http://www.Department0.University0.edu/AssistantProfessor0	AssistantProfessor0	AssistantProfessor0@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssistantProfessor1	AssistantProfessor1	AssistantProfessor1@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssistantProfessor2	AssistantProfessor2	AssistantProfessor2@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssistantProfessor3	AssistantProfessor3	AssistantProfessor3@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssistantProfessor4	AssistantProfessor4	AssistantProfessor4@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssistantProfessor5	AssistantProfessor5	AssistantProfessor5@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssistantProfessor6	AssistantProfessor6	AssistantProfessor6@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssistantProfessor7	AssistantProfessor7	AssistantProfessor7@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssistantProfessor8	AssistantProfessor8	AssistantProfessor8@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssistantProfessor9	AssistantProfessor9	AssistantProfessor9@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssociateProfessor0	AssociateProfessor0	AssociateProfessor0@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssociateProfessor1	AssociateProfessor1	AssociateProfessor1@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssociateProfessor10	AssociateProfessor10	AssociateProfessor10@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssociateProfessor11	AssociateProfessor11	AssociateProfessor11@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssociateProfessor12	AssociateProfessor12	AssociateProfessor12@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssociateProfessor13	AssociateProfessor13	AssociateProfessor13@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssociateProfessor2	AssociateProfessor2	AssociateProfessor2@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssociateProfessor3	AssociateProfessor3	AssociateProfessor3@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssociateProfessor4	AssociateProfessor4	AssociateProfessor4@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssociateProfessor5	AssociateProfessor5	AssociateProfessor5@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssociateProfessor6	AssociateProfessor6	AssociateProfessor6@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssociateProfessor7	AssociateProfessor7	AssociateProfessor7@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssociateProfessor8	AssociateProfessor8	AssociateProfessor8@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/AssociateProfessor9	AssociateProfessor9	AssociateProfessor9@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/FullProfessor0	FullProfessor0	FullProfessor0@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/FullProfessor1	FullProfessor1	FullProfessor1@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/FullProfessor2	FullProfessor2	FullProfessor2@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/FullProfessor3	FullProfessor3	FullProfessor3@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/FullProfessor4	FullProfessor4	FullProfessor4@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/FullProfessor5	FullProfessor5	FullProfessor5@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/FullProfessor6	FullProfessor6	FullProfessor6@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/FullProfessor7	FullProfessor7	FullProfessor7@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/FullProfessor8	FullProfessor8	FullProfessor8@Department0.University0.edu	xxx-xxx-xxxx
http://www.Department0.University0.edu/FullProfessor9	FullProfessor9	FullProfessor9@Department0.University0.edu	xxx-xxx-xxxx
</pre>
	 * 
	 */
	private static Clause getQuery4() {
		Clause query = new Clause();
		NormalPredicate ans = CacheManager.getInstance().getPredicate("ans", 1);
		Variable X = CacheManager.getInstance().getVariable("X");
		Variable Y1 = CacheManager.getInstance().getVariable("Y1");
		Variable Y2 = CacheManager.getInstance().getVariable("Y2");
		Variable Y3 = CacheManager.getInstance().getVariable("Y3");

		Literal head = new Literal(ans, X, Y1, Y2, Y3);

		NormalPredicate professor = CacheManager
				.getInstance()
				.getPredicate(
						"<http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#Professor>",
						1);
		Literal body1 = new Literal(professor, X);

		NormalPredicate worksFor = CacheManager
				.getInstance()
				.getPredicate(
						"<http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#worksFor>",
						2);

		Constant department0 = CacheManager.getInstance().getConstant(
				"<http://www.Department0.University0.edu>");

		Literal body2 = new Literal(worksFor, X, department0);

		NormalPredicate name = CacheManager.getInstance().getPredicate(
				"<http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#name>",
				2);
		Literal body3 = new Literal(name, X, Y1);
		
		NormalPredicate emailAddress = CacheManager.getInstance().getPredicate(
				"<http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#emailAddress>",
				2);
		Literal body4 = new Literal(emailAddress, X, Y2);
		
		NormalPredicate telephone = CacheManager.getInstance().getPredicate(
				"<http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#telephone>",
				2);
		Literal body5 = new Literal(telephone, X, Y3);

		query.setHead(head);
		query.getPositiveBody().add(body1);
		query.getPositiveBody().add(body2);
		query.getPositiveBody().add(body3);
		query.getPositiveBody().add(body4);
		query.getPositiveBody().add(body5);
		return query;

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