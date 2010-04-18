/*
 * @(#)AbstractPropertyExpressionTranslator.java 2010-3-16 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.owlapi.rdfxml.parser;

import java.util.Set;

import org.coode.owlapi.rdfxml.parser.OWLRDFConsumer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * TODO describe this class please.
 */
public abstract class AbstractObjectPropertyExpressionTranslator implements ObjectPropertyExpressionTranslator{

	private OWLRDFConsumer consumer;
	
	public AbstractObjectPropertyExpressionTranslator(OWLRDFConsumer consumer) {
		this.consumer = consumer;
	}

	public OWLRDFConsumer getConsumer() {
        return consumer;
    }

    protected IRI getResourceObject(IRI subject, IRI predicate, boolean consume) {
        return consumer.getResourceObject(subject, predicate, consume);
    }
    
    protected Set<OWLObjectPropertyExpression> translateToObjectPropertyExpressionSet(IRI mainNode) {
        return consumer.translateToObjectPropertyExpressionSet(mainNode);
    }

    protected OWLObjectPropertyExpression translateToObjectPropertyExpression(IRI mainNode) {
        return consumer.translateObjectPropertyExpression(mainNode);
    }
    
    protected OWLDataFactory getDataFactory() {
        return consumer.getDataFactory();
    }
}
