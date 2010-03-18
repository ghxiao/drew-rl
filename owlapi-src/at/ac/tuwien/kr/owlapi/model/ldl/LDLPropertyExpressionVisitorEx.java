/*
 * @(#)LDLPropertyExpressionVisitorEx.java 2010-3-11 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.owlapi.model.ldl;

import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitorEx;

import at.ac.tuwien.kr.owlapi.model.ldl.impl.LDLObjectPropertyChainOfImpl;

/**
 * TODO describe this class please.
 */
public interface LDLPropertyExpressionVisitorEx<O> extends OWLPropertyExpressionVisitorEx<O> {
	O visit(LDLObjectPropertyIntersectionOf property);
	
	O visit(LDLObjectPropertyUnionOf property);
	
	O visit(LDLObjectPropertyTransitiveClosureOf property);

	O visit(LDLObjectPropertyChainOf ldlObjectPropertyChainOfImpl);
}
