/*
 * @(#)PropertyExpressionTranslator.java 2010-3-16 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * TODO describe this class please.
 */
public interface ObjectPropertyExpressionTranslator {

	OWLObjectPropertyExpression translate(IRI mainNode);
}
