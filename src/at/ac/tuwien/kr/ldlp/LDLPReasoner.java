package at.ac.tuwien.kr.ldlp;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.AxiomNotInProfileException;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.FreshEntitiesException;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.ReasonerInterruptedException;
import org.semanticweb.owlapi.reasoner.TimeOutException;
import org.semanticweb.owlapi.reasoner.UnsupportedEntailmentTypeException;

import at.ac.tuwien.kr.datalog.DatalogEngine;
import at.ac.tuwien.kr.datalog.DatalogQuery;
import at.ac.tuwien.kr.datalog.XSBDatalogEngine;

import edu.unika.aifb.kaon.datalog.program.Program;

public class LDLPReasoner extends OWLReasonerAdapter {

	Program program;

	boolean compiled;

	LDLPCompiler compiler;
	
	DatalogEngine datalogEngine;

	protected LDLPReasoner(OWLOntology rootOntology, OWLReasonerConfiguration configuration, BufferingMode bufferingMode) {
		super(rootOntology, configuration, bufferingMode);
		compiler = new LDLPCompiler();
		datalogEngine = new XSBDatalogEngine();
	}

	@Override
	public boolean isEntailed(OWLAxiom axiom) throws ReasonerInterruptedException, UnsupportedEntailmentTypeException, TimeOutException,
			AxiomNotInProfileException, FreshEntitiesException {

		if (!(axiom instanceof OWLClassAssertionAxiom))
			throw new UnsupportedOperationException();

		OWLClassAssertionAxiom classAssertionAxiom = (OWLClassAssertionAxiom) axiom;

		

		if (!compiled) {
			program = compiler.complileLDLPOntology(this.getRootOntology());
			compiled = true;
		}
		DatalogQuery query = compiler.compileClassAssertionAxiom(classAssertionAxiom);
		
		return datalogEngine.query(program, query);

		

	}

}
