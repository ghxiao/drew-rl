package at.ac.tuwien.kr.ldlpprogram.eval;

import java.io.StringReader;
import java.util.List;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import at.ac.tuwien.kr.dlprogram.DLProgram;
import at.ac.tuwien.kr.dlprogram.DLProgramKB;
import at.ac.tuwien.kr.dlprogram.Literal;
import at.ac.tuwien.kr.dlprogram.parser.DLProgramParser;
import at.ac.tuwien.kr.dlprogram.parser.ParseException;
import at.ac.tuwien.kr.ldlpprogram.reasoner.KBReasoner;

public class LUBMQuery {
	public static void main(String[] args) throws OWLOntologyCreationException,
			ParseException {

		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = manager.loadOntologyFromOntologyDocument(IRI
				.create("file:benchmark/uba/University0_0.owl"));

		long t0 = System.currentTimeMillis();
		// String text =
		// "p(a). s(a). s(b). q:-DL[C+=s;D](a), not DL[C+=p;D](b).";

		String[] queries = {
		// queries[0]
				"#namespace(\"ub\",\"http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#\").\n"
						+ "f (X, Y) :- DL[;ub:Faculty](X), DL[;ub:Faculty](Y), D1 = D2 , U1 != U2 ,\n"
						+ "DL[;ub:doctoralDegreeFrom](X, U1), DL[;ub:worksFor](X, D1 ),\n"
						+ "DL[;ub:doctoralDegreeFrom](Y, U2), DL[;ub:worksFor](Y, D2 ).\n",
						
				// queries[1]
				"#namespace(\"ub\",\"http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#\").\n"
						+ "f(X, Y) :-  DL[;ub:GraduateStudent](X), DL[;ub:takesCourse](X, Y), \n"
						+ "Y=\"<http://www.Department0.University0.edu/GraduateCourse0>\".",
						
				// queries[2]
				"#namespace(\"ub\",\"http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#\").\n"
						+ "f(X, Y, Z) :-  DL[;ub:GraduateStudent](X), DL[;ub:University](Y), \n"
						+ "DL[;ub:Department](Z), DL[;ub:memberOf](X,Z), DL[;ub:subOrganizationOf](Z,Y), "
						+ "DL[;ub:undergraduateDegreeFrom](X,Y).\n",
						
				// queries[3]
				"#namespace(\"ub\",\"http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#\").\n"
						+ "f(X, Y) :-  DL[;ub:Publication](X), DL[;ub:publicationAuthor](X,Y), \n"
						+ "Y=\"<http://www.Department0.University0.edu/AssistantProfessor0>\".",
						
				// queries[4]
				"#namespace(\"ub\",\"http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#\").\n"
						+ "f(X, Y1, Y2, Y3) :-  DL[;ub:Professor](X), DL[;ub:worksFor](X,Z), \n"
						+ "Z=\"<http://www.Department0.University0.edu>\",\n"
						+ "DL[;ub:name](X,Y1),\n"
						+ "DL[;ub:emailAddress](X,Y2),\n"
						+ "DL[;ub:telephone](X,Y3).\n"

		};

		DLProgramParser parser = new DLProgramParser(new StringReader(
				queries[3]));

		DLProgram program = parser.program();

		DLProgramKB kb = new DLProgramKB();
		kb.setOntology(ontology);
		kb.setProgram(program);

		KBReasoner reasoner = new KBReasoner(kb);

		String queryText = "f(X,Y)";

		Literal query = new DLProgramParser(new StringReader(queryText))
				.literal();

		List<Literal> results = reasoner.query(query);

		System.out.println(results.size() + " Query Results");
		for (Literal result : results) {
			System.out.println(result);
		}
		System.out.println(results.size() + " Query Results");
		long t1 = System.currentTimeMillis();
		long dt = t1 - t0;
		System.out.println("Time: " + dt + " ms");

		//
		// boolean entailed = reasoner.isEntailed(query);
		//
		// assertTrue(entailed);
	}
	//
	// private OWLOntology loadOntology(String uri, String phyUri) {
	//
	// OWLOntology ontology = null;
	//
	// System.out.println();
	// System.out
	// .println("------------------------------------------------------");
	//
	// logger.info("Reading file " + uri + "...");
	//
	// manager.addIRIMapper(new SimpleIRIMapper(IRI.create(uri), IRI
	// .create(phyUri)));
	//
	// try {
	// ontology = manager.loadOntology(IRI.create(uri));
	//
	// // for (OWLAxiom axiom : ontology.getAxioms()) {
	// // System.out.println(axiom);
	// // }
	// logger.info("Loading complete");
	// System.out.println(ontology);
	// } catch (OWLOntologyCreationException e) {
	// e.printStackTrace();
	// }
	//
	// LDLPProfile profile = new LDLPProfile();
	//
	// OWLProfileReport report = profile.checkOntology(ontology);
	//
	// if (report.isInProfile()) {
	// System.out.println("The ontology is in LDL+ profile");
	// } else {
	// System.out.println("The ontology is not in LDL+ profile:");
	// Set<OWLProfileViolation> violations = report.getViolations();
	// System.out.println("The following " + violations.size()
	// + " axioms are violated");
	// for (OWLProfileViolation v : violations) {
	// System.out.println(v);
	// }
	// }
	// return ontology;
	// }
}
