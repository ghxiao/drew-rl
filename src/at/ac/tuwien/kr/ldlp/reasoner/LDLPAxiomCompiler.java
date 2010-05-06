/*
 * @(#)AxiomCompiler.java 2010-3-24 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.ldlp.reasoner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.util.OWLAxiomVisitorAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.kr.dlprogram.CacheManager;
import at.ac.tuwien.kr.dlprogram.Clause;
import at.ac.tuwien.kr.dlprogram.Literal;
import at.ac.tuwien.kr.dlprogram.Term;
import at.ac.tuwien.kr.dlprogram.Variable;



public class LDLPAxiomCompiler extends OWLAxiomVisitorAdapter {

	LDLPCompilerManager datalogObjectFactory = LDLPCompilerManager
			.getInstance();
	final static Logger logger = LoggerFactory
			.getLogger(LDLPAxiomCompiler.class);
	Variable X = CacheManager.getInstance().getVariable("X");
	Variable Y = CacheManager.getInstance().getVariable("Y");
	Variable Z = CacheManager.getInstance().getVariable("Z");

	private List<Clause> clauses;

	public List<Clause> getClauses() {
		return clauses;
	}

	public LDLPAxiomCompiler() {
		this.clauses = new ArrayList<Clause>();
	}

	public Clause compileOWLAxiom(OWLAxiom axiom) {
		this.clauses = new ArrayList<Clause>();

		axiom.accept(this);

		return clauses.get(0);
	}

	public List<Clause> compile(OWLAxiom... axioms) {
		this.clauses = new ArrayList<Clause>();
		for (OWLAxiom owlAxiom : axioms) {
			owlAxiom.accept(this);
		}
		return clauses;
	}

	@Override
	public void visit(OWLObjectPropertyAssertionAxiom axiom) {
		OWLObjectPropertyExpression property = axiom.getProperty();
		final OWLIndividual subject = axiom.getSubject();
		final OWLIndividual object = axiom.getObject();
		Literal[] head = null;
		Literal[] body = null;
		head = new Literal[1];

		String predicate = datalogObjectFactory.getPredicate(property);
		String a = datalogObjectFactory.getConstant(subject);
		String b = datalogObjectFactory.getConstant(object);

		head[0] = new Literal(predicate, new Term[] {
				CacheManager.getInstance().getConstant(a),
				CacheManager.getInstance().getConstant(b) });
		body = new Literal[0];
		Clause clause = new Clause(head, body);
		clauses.add(clause);
		logger.debug("{}\n\t->\n{}", axiom, clause);
	}

	@Override
	public void visit(OWLClassAssertionAxiom axiom) {
		OWLClassExpression cls = axiom.getClassExpression();
		OWLIndividual individual = axiom.getIndividual();
		Literal[] head = new Literal[1];
		Literal[] body = null;

		String predicate = datalogObjectFactory.getPredicate(cls);
		String a = datalogObjectFactory.getConstant(individual);
		head[0] = new Literal(predicate, new Term[] { CacheManager
				.getInstance().getConstant(a) });
		body = new Literal[0];
		Clause clause = new Clause(head, body);
		clauses.add(clause);
		logger.debug("{}\n\t->\n{}", axiom, clause);
	}

	@Override
	public void visit(OWLSubClassOfAxiom axiom) {
		final OWLClassExpression subClass = axiom.getSubClass();
		final OWLClassExpression superClass = axiom.getSuperClass();
		Literal[] head = null;
		Literal[] body = null;

		if (!(superClass instanceof OWLObjectAllValuesFrom)) {
			head = new Literal[1];
			head[0] = new Literal(
					datalogObjectFactory.getPredicate(superClass),
					new Term[] { X });
			body = new Literal[1];
			body[0] = new Literal(datalogObjectFactory.getPredicate(subClass),
					new Term[] { X });
		} else {
			OWLObjectAllValuesFrom E_only_A = (OWLObjectAllValuesFrom) superClass;
			final OWLClassExpression A = E_only_A.getFiller();
			final OWLObjectPropertyExpression E = E_only_A.getProperty();
			head = new Literal[1];
			head[0] = new Literal(datalogObjectFactory.getPredicate(A),
					new Term[] { Y });
			body = new Literal[2];
			body[0] = new Literal(datalogObjectFactory.getPredicate(subClass),
					new Term[] { X });
			body[1] = new Literal(datalogObjectFactory.getPredicate(E),
					new Term[] { X, Y });
		}

		Clause clause = new Clause(head, body);
		clauses.add(clause);
		logger.debug("{}\n\t->\n{}", axiom, clause);
	}

	@Override
	public void visit(OWLSubObjectPropertyOfAxiom axiom) {
		final OWLObjectPropertyExpression subProperty = axiom.getSubProperty();
		final OWLObjectPropertyExpression superProperty = axiom
				.getSuperProperty();
		Literal[] head = new Literal[1];
		head[0] = new Literal(datalogObjectFactory.getPredicate(superProperty),
				new Term[] { X, Y });
		Literal[] body = new Literal[1];
		body[0] = new Literal(datalogObjectFactory.getPredicate(subProperty),
				new Term[] { X, Y });
		Clause clause = new Clause(head, body);
		clauses.add(clause);
		logger.debug("{}\n\t->\n{}", axiom, clause);
	}

	@Override
	public void visit(OWLObjectPropertyDomainAxiom axiom) {
		OWLObjectPropertyExpression property = axiom.getProperty();
		OWLClassExpression domain = axiom.getDomain();

		Literal[] head = new Literal[1];
		head[0] = new Literal(datalogObjectFactory.getPredicate(domain),
				new Term[] { X });
		Literal[] body = new Literal[1];
		body[0] = new Literal(datalogObjectFactory.getPredicate(property),
				new Term[] { X, Y });

		Clause clause = new Clause(head, body);
		clauses.add(clause);
		logger.debug("{}\n\t->\n{}", axiom, clause);
	}

	@Override
	public void visit(OWLObjectPropertyRangeAxiom axiom) {
		OWLObjectPropertyExpression property = axiom.getProperty();
		OWLClassExpression range = axiom.getRange();

		Literal[] head = new Literal[1];
		head[0] = new Literal(datalogObjectFactory.getPredicate(range),
				new Term[] { Y });
		Literal[] body = new Literal[1];
		body[0] = new Literal(datalogObjectFactory.getPredicate(property),
				new Term[] { X, Y });

		Clause clause = new Clause(head, body);
		clauses.add(clause);
		logger.debug("{}\n\t->\n{}", axiom, clause);
	}

	public List<Clause> compile(Set<OWLAxiom> axioms) {
		this.clauses = new ArrayList<Clause>();
		for (OWLAxiom owlAxiom : axioms) {
			owlAxiom.accept(this);
		}
		return clauses;

	}
	
	@Override
	public void visit(OWLDataPropertyAssertionAxiom axiom) {
		OWLDataPropertyExpression property = axiom.getProperty();
		final OWLIndividual subject = axiom.getSubject();
		
		final OWLLiteral object = axiom.getObject();
		Literal[] head = null;
		Literal[] body = null;
		head = new Literal[1];

		String predicate = datalogObjectFactory.getPredicate(property);
		String a = datalogObjectFactory.getConstant(subject);
		String b = datalogObjectFactory.getConstant(object);

		head[0] = new Literal(predicate, new Term[] {
				CacheManager.getInstance().getConstant(a),
				CacheManager.getInstance().getConstant(b) });
		body = new Literal[0];
		Clause clause = new Clause(head, body);
		clauses.add(clause);
		logger.debug("{}\n\t->\n{}", axiom, clause);
		
	}

	@Override
	public void visit(OWLDataPropertyDomainAxiom axiom) {
		// TODO Auto-generated method stub
		super.visit(axiom);
	}

	@Override
	public void visit(OWLDataPropertyRangeAxiom axiom) {
		// TODO Auto-generated method stub
		super.visit(axiom);
	}
}
