/*
 * @(#)LDLObjectPropertyChainOfTranslator.java 2010-3-18 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.owlapi.rdfxml.parser;

import java.util.Set;

import org.coode.owlapi.rdfxml.parser.OWLRDFConsumer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * TODO describe this class please.
 */
public class LDLObjectPropertyChainOfTranslator extends AbstractNaryBooleanObjectPropertyExpressionTranslator {

	public LDLObjectPropertyChainOfTranslator(OWLRDFConsumer consumer) {
		super(consumer);
	}

	@Override
	protected OWLObjectPropertyExpression createObjectPropertyExpression(Set<OWLObjectPropertyExpression> operands) {
		return getDataFactory().getLDLObjectPropertyChainOf(operands);
	}

	@Override
	protected IRI getPredicateIRI() {
		// TODO Auto-generated method stub
		return null;
	}

}
