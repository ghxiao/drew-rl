/*
 * @(#)LDLObjectPropertyChainOfTranslator.java 2010-3-18 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.owlapi.rdfxml.parser;

import java.util.HashSet;
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
public class LDLObjectPropertyOneOfTranslator extends AbstractNaryBooleanObjectPropertyExpressionTranslator {

	public LDLObjectPropertyOneOfTranslator(OWLRDFConsumer consumer) {
		super(consumer);
	}

	@Override
	protected OWLObjectPropertyExpression createObjectPropertyExpression(Set<OWLObjectPropertyExpression> operands) {

		Set<LDLIndividualPair> pairs = new HashSet<LDLIndividualPair>();

		for (OWLObjectPropertyExpression op : operands) {
			if(op instanceof LDLIndividualPair){
				LDLIndividualPair pair = (LDLIndividualPair)op;
				pairs.add(pair);
			}else{
				throw new IllegalArgumentException("Expected that "+ op + " should be OWLIndividualPair");
			}
		}

		return getDataFactory().getLDLObjectPropertyOneOf(pairs);
	}

	@Override
	protected IRI getPredicateIRI() {
		return OWLRDFVocabulary.LDL_OBJECT_PROPERTY_ONE_OF.getIRI();
	}

}
