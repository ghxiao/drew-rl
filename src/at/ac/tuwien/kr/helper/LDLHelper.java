/*
 * @(#)LDLDataFactoryHelper.java 2010-3-26 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.helper;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import at.ac.tuwien.kr.ldlp.reasoner.DatalogObjectFactory;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyChainOf;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyTransitiveClosureOf;

import edu.stanford.db.lp.Literal;
import edu.stanford.db.lp.ProgramClause;
import edu.stanford.db.lp.Term;

/**
 * TODO describe this class please.
 */
public class LDLHelper {

	private static OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	private static OWLDataFactory factory = manager.getOWLDataFactory();

	public static OWLClassExpression and(OWLClassExpression... operands) {
		return factory.getOWLObjectIntersectionOf(operands);
	}

	public static OWLObjectPropertyExpression and(OWLObjectPropertyExpression... operands) {
		return factory.getLDLObjectPropertyIntersectionOf(operands);
	}

	public static OWLClassExpression or(OWLClassExpression... operands) {
		return factory.getOWLObjectUnionOf(operands);
	}

	public static OWLObjectPropertyExpression or(OWLObjectPropertyExpression... operands) {
		return factory.getLDLObjectPropertyUnionOf(operands);
	}

	public static OWLClassAssertionAxiom assert$(OWLClassExpression cls, OWLIndividual ind) {
		return factory.getOWLClassAssertionAxiom(cls, ind);
	}

	public static OWLObjectPropertyAssertionAxiom assert$(OWLObjectPropertyExpression prop, OWLIndividual a, OWLIndividual b) {
		return factory.getOWLObjectPropertyAssertionAxiom(prop, a, b);
	}

	public static OWLObjectSomeValuesFrom some(OWLObjectProperty prop, OWLClassExpression cls) {
		return factory.getOWLObjectSomeValuesFrom(prop, cls);
	}

	public static OWLObjectInverseOf inv(OWLObjectPropertyExpression prop) {
		return factory.getOWLObjectInverseOf(prop);
	}

	public static LDLObjectPropertyTransitiveClosureOf trans(OWLObjectPropertyExpression prop) {
		return factory.getLDLObjectPropertyTransitiveClosureOf(prop);
	}

	public static LDLObjectPropertyChainOf compose(OWLObjectPropertyExpression... props) {
		return factory.getLDLObjectPropertyChainOf(props);
	}

	public static OWLObjectMinCardinality min(int n, OWLObjectPropertyExpression property, OWLClassExpression cls) {
		return factory.getOWLObjectMinCardinality(n, property, cls);
	}

	public static ProgramClause clause(Literal[] head, Literal[] body) {
		return new ProgramClause(head, body);
	}

	public static Literal[] head(Literal... literals) {
		return literals;
	}

	public static Literal[] body(Literal... literals) {
		return literals;
	}

	public static Literal literal(String prediate, Term... terms) {
		return new Literal(prediate, terms);
	}

	public static String p(OWLObject owlObject) {
		DatalogObjectFactory factory = DatalogObjectFactory.getInstance();
		return factory.getPredicate(owlObject);
	}
}
