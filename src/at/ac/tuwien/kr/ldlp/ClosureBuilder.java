/*
 * @(#)ClosureBuilder.java 2010-3-24 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.ldlp;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLIndividualVisitor;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitor;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.util.OWLAxiomVisitorAdapter;

import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyChainOf;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyIntersectionOf;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyTransitiveClosureOf;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyUnionOf;

/**
 * TODO describe this class please.
 */

class ClosureBuilder extends OWLAxiomVisitorAdapter implements OWLClassExpressionVisitor, OWLPropertyExpressionVisitor, OWLIndividualVisitor {
	Set<OWLClassExpression> classExpressionsClosure;

	Set<OWLObjectPropertyExpression> objectPropertyExpressionsClosure;

	Set<OWLIndividual> individualsClosure;

	public ClosureBuilder(Set<OWLClassExpression> classExpressionsClosure, Set<OWLObjectPropertyExpression> objectPropertyExpressionsClosure,
			Set<OWLIndividual> individualsClosure) {
		this.classExpressionsClosure = classExpressionsClosure;
		this.objectPropertyExpressionsClosure = objectPropertyExpressionsClosure;
		this.individualsClosure = individualsClosure;
	}

	@Override
	public void visit(OWLClassAssertionAxiom axiom) {
		final OWLClassExpression cls = axiom.getClassExpression();
		final OWLIndividual individual = axiom.getIndividual();
		cls.accept(this);
		individual.accept(this);
	}

	@Override
	public void visit(OWLObjectPropertyAssertionAxiom axiom) {
		final OWLObjectPropertyExpression property = axiom.getProperty();
		final OWLIndividual subject = axiom.getSubject();
		final OWLIndividual object = axiom.getObject();
		property.accept(this);
		subject.accept(this);
		object.accept(this);
	}

	@Override
	public void visit(OWLSubClassOfAxiom axiom) {
		final OWLClassExpression subClass = axiom.getSubClass();
		final OWLClassExpression superClass = axiom.getSuperClass();

		if (!(superClass instanceof OWLObjectAllValuesFrom)) {
			superClass.accept(this);
			subClass.accept(this);
		} else {
			OWLObjectAllValuesFrom E_only_A = (OWLObjectAllValuesFrom) superClass;
			final OWLClassExpression A = E_only_A.getFiller();
			final OWLObjectPropertyExpression E = E_only_A.getProperty();
			A.accept(this);
			E.accept(this);
			subClass.accept(this);
		}
	}

	@Override
	public void visit(OWLSubObjectPropertyOfAxiom axiom) {
		final OWLObjectPropertyExpression subProperty = axiom.getSubProperty();
		final OWLObjectPropertyExpression superProperty = axiom.getSuperProperty();
		subProperty.accept(this);
		superProperty.accept(this);
	}

	@Override
	public void visit(OWLClass ce) {
		classExpressionsClosure.add(ce);
	}

	@Override
	public void visit(OWLObjectIntersectionOf ce) {
		classExpressionsClosure.add(ce);
		for (OWLClassExpression op : ce.getOperands()) {
			op.accept(this);
		}
	}

	@Override
	public void visit(OWLObjectUnionOf ce) {
		classExpressionsClosure.add(ce);
		for (OWLClassExpression op : ce.getOperands()) {
			op.accept(this);
		}
	}

	@Override
	public void visit(OWLObjectComplementOf ce) {
		classExpressionsClosure.add(ce);
		ce.getOperand().accept(this);

	}

	@Override
	public void visit(OWLObjectSomeValuesFrom ce) {
		classExpressionsClosure.add(ce);
		ce.getProperty().accept(this);
		ce.getFiller().accept(this);
	}

	@Override
	public void visit(OWLObjectAllValuesFrom ce) {
		// TODO: Fix me if the expression is not in normal form

	}

	@Override
	public void visit(OWLObjectHasValue ce) {
		classExpressionsClosure.add(ce);
		ce.getProperty().accept(this);
		ce.getValue().accept(this);
	}

	@Override
	public void visit(OWLObjectMinCardinality ce) {
		classExpressionsClosure.add(ce);
		ce.getProperty().accept(this);
		ce.getFiller().accept(this);
	}

	@Override
	public void visit(OWLObjectExactCardinality ce) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void visit(OWLObjectMaxCardinality ce) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void visit(OWLObjectHasSelf ce) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void visit(OWLObjectOneOf ce) {
		// TODO:
		for (OWLIndividual i : ce.getIndividuals()) {
			i.accept(this);
		}
	}

	@Override
	public void visit(OWLDataSomeValuesFrom ce) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void visit(OWLDataAllValuesFrom ce) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void visit(OWLDataHasValue ce) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void visit(OWLDataMinCardinality ce) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void visit(OWLDataExactCardinality ce) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void visit(OWLDataMaxCardinality ce) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void visit(OWLObjectProperty property) {
		objectPropertyExpressionsClosure.add(property);
	}

	@Override
	public void visit(OWLObjectInverseOf property) {
		objectPropertyExpressionsClosure.add(property);
		property.getInverse().accept(this);
	}

	@Override
	public void visit(OWLDataProperty property) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void visit(LDLObjectPropertyIntersectionOf property) {
		objectPropertyExpressionsClosure.add(property);
		for (OWLObjectPropertyExpression operand : property.getOperands()) {
			operand.accept(this);
		}
	}

	@Override
	public void visit(LDLObjectPropertyUnionOf property) {
		objectPropertyExpressionsClosure.add(property);
		for (OWLObjectPropertyExpression operand : property.getOperands()) {
			operand.accept(this);
		}
	}

	@Override
	public void visit(LDLObjectPropertyTransitiveClosureOf property) {
		objectPropertyExpressionsClosure.add(property);
		property.getOperand().accept(this);

	}

	@Override
	public void visit(LDLObjectPropertyChainOf property) {
		objectPropertyExpressionsClosure.add(property);
		for (OWLObjectPropertyExpression operand : property.getOperands()) {
			operand.accept(this);
		}
	}

	@Override
	public void visit(OWLNamedIndividual individual) {
		individualsClosure.add(individual);
		// OWLObjectOneOf oneOf = dataFactory.getOWLObjectOneOf(individual);

	}

	@Override
	public void visit(OWLAnonymousIndividual individual) {
		throw new UnsupportedOperationException();
	}
}
