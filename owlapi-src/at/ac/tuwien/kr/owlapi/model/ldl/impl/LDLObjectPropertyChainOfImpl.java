/*
 * @(#)LDLObjectPropertyChainOfImpl.java 2010-3-18 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.owlapi.model.ldl.impl;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitor;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitorEx;

import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyChainOf;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLPropertyExpressionVisitorEx;

/**
 * TODO describe this class please.
 */
public class LDLObjectPropertyChainOfImpl extends LDLNaryBooleanPropertyExpressionImpl implements LDLObjectPropertyChainOf {

	public LDLObjectPropertyChainOfImpl(OWLDataFactory factory, Set<? extends OWLObjectPropertyExpression> operands) {
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
	
//	@Override
//	public <O> O accept(LDLPropertyExpressionVisitorEx<O> visitor) {
//		return visitor.visit(this);
//	}
//
//	@Override
//	public void accept(OWLObjectVisitor visitor) {
//		visitor.visit(this);
//	}
//
//	@Override
//	public <O> O accept(OWLPropertyExpressionVisitorEx<O> visitor) {
//		return visitor.visit(this);
//	}
//
//	@Override
//	public <O> O accept(OWLObjectVisitorEx<O> visitor) {
//		return visitor.visit(this);
//	}
//
//	@Override
//	public void accept(OWLPropertyExpressionVisitor visitor) {
//		visitor.visit(this);
//	}

	
}
