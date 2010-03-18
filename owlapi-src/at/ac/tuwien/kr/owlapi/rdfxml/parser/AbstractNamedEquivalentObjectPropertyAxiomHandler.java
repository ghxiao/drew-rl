/*
 * @(#)AbstractNamedEquivalentObjectPropertyAxiomHandler.java 2010-3-16 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.owlapi.rdfxml.parser;

import org.coode.owlapi.rdfxml.parser.OWLRDFConsumer;
import org.coode.owlapi.rdfxml.parser.TriplePredicateHandler;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * TODO describe this class please.
 */
public abstract class AbstractNamedEquivalentObjectPropertyAxiomHandler extends TriplePredicateHandler {

	public AbstractNamedEquivalentObjectPropertyAxiomHandler(OWLRDFConsumer consumer, IRI predicateIRI) {
		super(consumer, predicateIRI);
	}
	
    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return false;
    }


    public boolean canHandle(IRI subject, IRI predicate, IRI object) {
        return super.canHandle(subject, predicate, object) && !isAnonymous(subject);
    }
    
	/* (non-Javadoc)
	 * @see org.coode.owlapi.rdfxml.parser.AbstractResourceTripleHandler#handleTriple(org.semanticweb.owlapi.model.IRI, org.semanticweb.owlapi.model.IRI, org.semanticweb.owlapi.model.IRI)
	 */
	@Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
		throw new UnsupportedOperationException();

	}
    
    protected abstract OWLObjectPropertyExpression translateEquivalentObjectProperty(IRI mainNode);

}
