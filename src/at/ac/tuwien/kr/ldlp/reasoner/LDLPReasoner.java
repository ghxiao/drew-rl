package at.ac.tuwien.kr.ldlp.reasoner;

import java.util.List;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.AxiomNotInProfileException;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.FreshEntitiesException;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.ReasonerInterruptedException;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.TimeOutException;
import org.semanticweb.owlapi.reasoner.UnsupportedEntailmentTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.kr.datalog.DatalogReasoner;
import at.ac.tuwien.kr.datalog.DatalogQuery;
import at.ac.tuwien.kr.datalog.XSBDatalogReasoner;

import edu.stanford.db.lp.Literal;
import edu.stanford.db.lp.ProgramClause;
import edu.unika.aifb.kaon.datalog.program.Program;

public class LDLPReasoner extends OWLReasonerAdapter {
	final static Logger logger = LoggerFactory.getLogger(ClosureCompiler.class);
	
	List<ProgramClause> program;

	boolean compiled;

	LDLPCompiler compiler;

	AxiomCompiler axiomCompiler = new AxiomCompiler();

	DatalogReasoner datalogReasoner;

	protected LDLPReasoner(OWLOntology rootOntology, OWLReasonerConfiguration configuration, BufferingMode bufferingMode) {
		super(rootOntology, configuration, bufferingMode);
		compiler = new LDLPCompiler();
		datalogReasoner = new XSBDatalogReasoner();
	}

	public LDLPReasoner(OWLOntology rootOntology) {
		this(rootOntology, new SimpleConfiguration(), null);
	}

	@Override
	public boolean isEntailed(OWLAxiom axiom) throws ReasonerInterruptedException, UnsupportedEntailmentTypeException, TimeOutException,
			AxiomNotInProfileException, FreshEntitiesException {

		// if (!(axiom instanceof OWLClassAssertionAxiom))
		// throw new UnsupportedOperationException();

		// OWLClassAssertionAxiom classAssertionAxiom = (OWLClassAssertionAxiom)
		// axiom;

		if (!compiled) {
			logger.debug("Program Compiling Started");
			program = compiler.complile(this.getRootOntology());
			logger.debug("Program Compiling Finished");
			compiled = true;
			
		}

		ProgramClause query = axiomCompiler.compileOWLAxiom(axiom);
		// compiler.compileClassAssertionAxiom(classAssertionAxiom);

		return datalogReasoner.query(program, query);

	}

}
