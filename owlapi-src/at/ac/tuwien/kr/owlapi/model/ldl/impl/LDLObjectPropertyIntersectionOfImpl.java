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
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitor;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitorEx;

import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyIntersectionOf;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLPropertyExpressionVisitorEx;

/**
 * TODO describe this class please.
 */
public class LDLObjectPropertyIntersectionOfImpl extends LDLNaryBooleanPropertyExpressionImpl implements LDLObjectPropertyIntersectionOf {

	public LDLObjectPropertyIntersectionOfImpl(OWLDataFactory factory, Set<? extends OWLObjectPropertyExpression> operands) {
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

	@Override
	public <O> O accept(OWLPropertyExpressionVisitorEx<O> visitor) {
		return visitor.visit(this);
	}

	@Override
	public <O> O accept(OWLObjectVisitorEx<O> visitor) {
		return visitor.visit(this);
	}

	@Override
	public void accept(OWLPropertyExpressionVisitor visitor) {
		visitor.visit(this);
	}

}
