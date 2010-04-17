/*
 * @(#)LDLObjectPropertyChainOfTranslator.java 2010-3-18 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.owlapi.rdfxml.parser;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.coode.owlapi.rdfxml.parser.OWLRDFConsumer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import at.ac.tuwien.kr.owlapi.model.ldl.LDLIndividualPair;

/**
 * TODO describe this class please.
 */
public class LDLIndividualPairTranslator extends AbstractNaryBooleanObjectPropertyExpressionTranslator {

	public LDLIndividualPairTranslator(OWLRDFConsumer consumer) {
		super(consumer);
	}

	@Override
	protected OWLObjectPropertyExpression createObjectPropertyExpression(Set<OWLObjectPropertyExpression> operands) {

		final Iterator<OWLObjectPropertyExpression> iterator = operands.iterator();

		final OWLIndividual first = (OWLIndividual) iterator.next();

		final OWLIndividual second = (OWLIndividual) iterator.next();
		
		return getDataFactory().getLDLIndiviualPair(first,second);
	}

	@Override
	protected IRI getPredicateIRI() {
		return OWLRDFVocabulary.LDL_INDIVIDUAL_PAIR.getIRI();
	}

}
