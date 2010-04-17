/*
 * @(#)LDLObjectPropertyTransitiveClosureOfTranslator.java 2010-3-16 
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
public class LDLObjectPropertyTransitiveClosureOfTranslator extends AbstractObjectPropertyExpressionTranslator{

	public LDLObjectPropertyTransitiveClosureOfTranslator(OWLRDFConsumer consumer){
		super(consumer);
	}

	@Override
	public OWLObjectPropertyExpression translate(IRI mainNode) {
		IRI transitiveClosureOfProperty = getResourceObject(mainNode, OWLRDFVocabulary.LDL_OBJECT_PROPERTY_TRANSITIVE_CLOSURE_OF.getIRI(), true);
		OWLObjectPropertyExpression operand = translateToObjectPropertyExpression(transitiveClosureOfProperty);
		return getDataFactory().getLDLObjectPropertyTransitiveClosureOf(operand);
	}
	
}
