/*
 * @(#)LDLObjectPropertyIntersectionOfImpl.java 2010-3-11 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.owlapi.model.ldl.impl;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;

import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyUnionOf;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLPropertyExpressionVisitorEx;

/**
 * TODO describe this class please.
 */
public class LDLObjectPropertyUnionOfImpl extends LDLNaryBooleanPropertyExpressionImpl implements LDLObjectPropertyUnionOf {

	public LDLObjectPropertyUnionOfImpl(OWLDataFactory factory, Set<? extends OWLObjectPropertyExpression> operands) {
		super(factory, operands);
	}

	@Override
	public <O> O accept(LDLPropertyExpressionVisitorEx<O> visitor) {
		return visitor.visit(this);
	}

	@Override
	public void accept(OWLObjectVisitor visitor) {
		visitor.visit(this);
	}

}
