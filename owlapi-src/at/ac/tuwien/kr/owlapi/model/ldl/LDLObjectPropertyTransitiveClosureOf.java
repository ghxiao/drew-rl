/*
 * @(#)LDLObjectPropertyTransitiveClosure.java 2010-3-10 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.owlapi.model.ldl;

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * Represent ObjectPropertyTransitiveClosure E^+
 */
public interface LDLObjectPropertyTransitiveClosureOf extends OWLObjectPropertyExpression {
	OWLObjectPropertyExpression getOperand();
}
