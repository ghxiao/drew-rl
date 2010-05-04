package at.ac.tuwien.kr.ldlp.reasoner;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
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
import at.ac.tuwien.kr.datalog.DlvDatalogReasoner;
import at.ac.tuwien.kr.datalog.XSBDatalogReasoner;
import at.ac.tuwien.kr.datalog.DatalogReasoner.TYPE;
import at.ac.tuwien.kr.dlprogram.CacheManager;
import at.ac.tuwien.kr.dlprogram.Clause;
import at.ac.tuwien.kr.dlprogram.Constant;
import at.ac.tuwien.kr.dlprogram.Literal;
import at.ac.tuwien.kr.dlprogram.NormalPredicate;
import at.ac.tuwien.kr.dlprogram.Predicate;
import at.ac.tuwien.kr.dlprogram.Term;
import at.ac.tuwien.kr.dlprogram.Variable;
import at.ac.tuwien.kr.ldlpprogram.reasoner.KBCompiler;

public class LDLPReasoner extends OWLReasonerAdapter {
	final static Logger logger = LoggerFactory.getLogger(LDLPClosureCompiler.class);

	List<Clause> program;

	boolean compiled;

	LDLPOntologyCompiler ontologyCompiler;

	LDLPAxiomCompiler axiomCompiler = new LDLPAxiomCompiler();

	DatalogReasoner datalogReasoner;

	protected LDLPReasoner(OWLOntology rootOntology, OWLReasonerConfiguration configuration, BufferingMode bufferingMode) {
		super(rootOntology, configuration, bufferingMode);
		ontologyCompiler = new LDLPOntologyCompiler();
		// datalogReasoner = new XSBDatalogReasoner();
	}

	public LDLPReasoner(OWLOntology rootOntology) {
		this(rootOntology, TYPE.DLV);
	}

	public LDLPReasoner(OWLOntology rootOntology, DatalogReasoner.TYPE type) {

		this(rootOntology, new SimpleConfiguration(), null);
		if (type == TYPE.DLV) {
			datalogReasoner = new DlvDatalogReasoner();
		} else if (type == TYPE.XSB) {
			datalogReasoner = new XSBDatalogReasoner();
		}
	}

	// public OWLIndividual query(OWLClass cls) {
	// program = compiler.complile(this.getRootOntology());
	// final String predicate =
	// LDLPCompilerManager.getInstance().getPredicate(cls);
	//		
	//
	// }

	@Override
	public boolean isEntailed(OWLAxiom axiom) throws ReasonerInterruptedException, UnsupportedEntailmentTypeException, TimeOutException,
			AxiomNotInProfileException, FreshEntitiesException {

		if (!(axiom instanceof OWLClassAssertionAxiom))
			throw new UnsupportedOperationException();

		OWLClassAssertionAxiom classAssertionAxiom = (OWLClassAssertionAxiom)
				axiom;

		final OWLIndividual individual = classAssertionAxiom.getIndividual();

		final OWLOntology rootOntology = super.getRootOntology();

		final OWLOntologyManager manager = rootOntology.getOWLOntologyManager();

		if (!compiled) {
			logger.debug("Program Compiling Started");
			program = ontologyCompiler.complile(this.getRootOntology());
			logger.debug("Program Compiling Finished");
			compiled = true;
		}

		Clause query = axiomCompiler.compileOWLAxiom(axiom);
		// compiler.compileClassAssertionAxiom(classAssertionAxiom);

		return datalogReasoner.query(program, query);

	}

	/**
	 * Conjunctive query <br/>
	 * eg. ans(X):-A(X,c), B(X,Y).
	 * 
	 * @param q
	 *            The conjunctive query
	 */
	public void query(Clause q) {
		program = ontologyCompiler.complile(this.getRootOntology());
		
		LDLPQueryCompiler queryComiler = new LDLPQueryCompiler();
		Clause query = queryComiler.compileQuery(q);
		
		program.add(query);
		
		datalogReasoner.query(program, query.getHead());
	}

}
