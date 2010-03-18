/*
 * @(#)TPObjectPropertyTransitiveClosureOfHandler.java 2010-3-16 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.owlapi.rdfxml.parser;

import org.coode.owlapi.rdfxml.parser.OWLRDFConsumer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * TODO describe this class please.
 */
public class TPObjectPropertyTransitiveClosureOfHandler extends AbstractNamedEquivalentObjectPropertyAxiomHandler {

	public TPObjectPropertyTransitiveClosureOfHandler(OWLRDFConsumer consumer) {
		super(consumer, OWLRDFVocabulary.LDL_OBJECT_PROPERTY_TRANSITIVE_CLOSURE_OF.getIRI());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeat.ac.tuwien.kr.owlapi.rdfxml.parser.
	 * AbstractNamedEquivalentObjectPropertyAxiomHandler
	 * #translateEquivalentObjectProperty(org.semanticweb.owlapi.model.IRI)
	 */
	@Override
	protected OWLObjectPropertyExpression translateEquivalentObjectProperty(IRI mainNode) {
		return getDataFactory().getLDLObjectPropertyTransitiveClosureOf(getConsumer().translateObjectPropertyExpression(mainNode));
	}

}
