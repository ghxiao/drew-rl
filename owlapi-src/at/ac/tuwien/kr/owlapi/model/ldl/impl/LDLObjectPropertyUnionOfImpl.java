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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((operands == null) ? 0 : operands.hashCode()) + 2;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof LDLObjectPropertyUnionOfImpl)) {
			return false;
		}
		LDLObjectPropertyUnionOfImpl other = (LDLObjectPropertyUnionOfImpl) obj;
		if (operands == null) {
			if (other.operands != null) {
				return false;
			}
		} else if (!operands.equals(other.operands)) {
			return false;
		}
		return true;
	}
}
