/*
 * @(#)LDLObjectPropertyUnionOfTranslator.java 2010-3-16 
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
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * TODO describe this class please.
 */
public class LDLObjectPropertyUnionOfTranslator extends AbstractNaryBooleanObjectPropertyExpressionTranslator {

	public LDLObjectPropertyUnionOfTranslator(OWLRDFConsumer consumer) {
		super(consumer);
	}

	@Override
	protected OWLObjectPropertyExpression createObjectPropertyExpression(Set<OWLObjectPropertyExpression> operands) {
		return getDataFactory().getLDLObjectPropertyUnionOf(operands);
	}

	@Override
	protected IRI getPredicateIRI() {
		return OWLRDFVocabulary.LDL_OBJECT_PROPERTY_UNION_OF.getIRI();
	}

	

	

}
