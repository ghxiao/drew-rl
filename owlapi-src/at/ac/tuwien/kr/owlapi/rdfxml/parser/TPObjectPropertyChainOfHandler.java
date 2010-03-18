/*
 * @(#)TPObjectPropertyCahineOfHandler.java 2010-3-18 
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
public class TPObjectPropertyChainOfHandler extends AbstractNamedEquivalentObjectPropertyAxiomHandler {

	public TPObjectPropertyChainOfHandler(OWLRDFConsumer consumer) {
		super(consumer, OWLRDFVocabulary.LDL_OBJECT_PROPERTY_CHAIN_OF.getIRI());
	}

	@Override
	protected OWLObjectPropertyExpression translateEquivalentObjectProperty(IRI mainNode) {
		return getDataFactory().getLDLObjectPropertyChainOf(getConsumer().translateToObjectPropertyExpressionSet(mainNode));
	}

}
