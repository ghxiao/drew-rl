/*
 * @(#)LDLObjectPropertyInverseOfTranslator.java 2010-3-19 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.owlapi.rdfxml.parser;

import org.coode.owlapi.rdfxml.parser.OWLRDFConsumer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * TODO describe this class please.
 */
public class OWLObjectPropertyInverseOfTranslator extends AbstractObjectPropertyExpressionTranslator {

	public OWLObjectPropertyInverseOfTranslator(OWLRDFConsumer consumer) {
		super(consumer);	
	}

	@Override
	public OWLObjectPropertyExpression translate(IRI mainNode) {
		IRI inverseOfProperty = getResourceObject(mainNode, OWLRDFVocabulary.OWL_INVERSE_OF.getIRI(), true);
		OWLObjectPropertyExpression operand = translateToObjectPropertyExpression(inverseOfProperty);
		return getDataFactory().getOWLObjectInverseOf(operand);
	}
}
