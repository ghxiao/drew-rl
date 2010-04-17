/*
 * @(#)AbstractNaryBooleanObjectPropertyExpressionTranslator.java 2010-3-17 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.owlapi.rdfxml.parser;

import java.util.Set;
import java.util.logging.Logger;

import org.coode.owlapi.rdfxml.parser.OWLRDFConsumer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * A base class for translators that translate a set of triples to an Nary
 * boolean object propety expressions - i.e. an LDLObjectPropertyIntersectionOf
 * or LDLObjectPropertyUnionOf class expression.
 */
public abstract class AbstractNaryBooleanObjectPropertyExpressionTranslator extends AbstractObjectPropertyExpressionTranslator {

	private Logger logger = Logger.getLogger(OWLRDFConsumer.class.getName());
	
	@Override
	public OWLObjectPropertyExpression translate(IRI mainNode) {
		IRI object = getResourceObject(mainNode, getPredicateIRI(), true);
        Set<OWLObjectPropertyExpression> operands = translateToObjectPropertyExpressionSet(object);
        if(operands.size() < 2) {
            logger.fine("Number of operands is less than 2");
            if(operands.size() == 1) {
                return operands.iterator().next();
            }
            else {
                // Zero - just return thing
                logger.fine("Number of operands is zero! Translating as top object property");
                return getDataFactory().getOWLTopObjectProperty();
            }
        }
        return createObjectPropertyExpression(operands);
	}

	public AbstractNaryBooleanObjectPropertyExpressionTranslator(OWLRDFConsumer consumer) {
		super(consumer);
	}
	
    protected abstract OWLObjectPropertyExpression createObjectPropertyExpression(Set<OWLObjectPropertyExpression> operands);

    protected abstract IRI getPredicateIRI();

}
