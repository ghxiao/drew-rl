/*
 * @(#)TPObjectPropertyIntersectionOfHandler.java 2010-3-17 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.owlapi.rdfxml.parser;

import org.coode.owlapi.rdfxml.parser.OWLRDFConsumer;
import org.coode.owlapi.rdfxml.parser.TriplePredicateHandler;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * TODO describe this class please.
 */
public class TPObjectPropertyIntersectionOfHandler extends AbstractNamedEquivalentObjectPropertyAxiomHandler {

	public TPObjectPropertyIntersectionOfHandler(OWLRDFConsumer consumer) {
		super(consumer, OWLRDFVocabulary.LDL_OBJECT_PROPERTY_INTERSECTION_OF.getIRI());
	
	}

	@Override
	protected OWLObjectPropertyExpression translateEquivalentObjectProperty(IRI mainNode) {
		return getDataFactory().getLDLObjectPropertyIntersectionOf(getConsumer().translateToObjectPropertyExpressionSet(mainNode));
	}


}
