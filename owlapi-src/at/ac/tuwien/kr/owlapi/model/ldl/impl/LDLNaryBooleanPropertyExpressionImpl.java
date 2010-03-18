package at.ac.tuwien.kr.owlapi.model.ldl.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

import uk.ac.manchester.cs.owl.owlapi.OWLObjectImpl;


import at.ac.tuwien.kr.owlapi.model.ldl.LDLNaryBooleanPropertyExpression;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLPropertyExpressionVisitorEx;

public abstract class LDLNaryBooleanPropertyExpressionImpl extends OWLObjectImpl implements LDLNaryBooleanPropertyExpression {

	public LDLNaryBooleanPropertyExpressionImpl(OWLDataFactory factory, Set<? extends OWLObjectPropertyExpression> operands) {
		super(factory);
		this.operands = Collections.unmodifiableSet(new TreeSet<OWLObjectPropertyExpression>(operands)); 
	}

	Set<OWLObjectPropertyExpression> operands;

	@Override
	public Set<OWLObjectPropertyExpression> getOperands() {
		return operands;
	}

	@Override
	public List<OWLObjectPropertyExpression> getOperandsAsList() {
		return new ArrayList<OWLObjectPropertyExpression>(operands);
	}

	@Override
	public OWLObjectProperty asOWLObjectProperty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public OWLObjectPropertyExpression getInverseProperty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<OWLObjectPropertyExpression> getInverses(OWLOntology ontology) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<OWLObjectPropertyExpression> getInverses(Set<OWLOntology> ontologies) {
		throw new UnsupportedOperationException();
	}

	@Override
	public OWLObjectProperty getNamedProperty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public OWLObjectPropertyExpression getSimplified() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isAsymmetric(OWLOntology ontology) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isAsymmetric(Set<OWLOntology> ontologies) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isInverseFunctional(OWLOntology ontology) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isInverseFunctional(Set<OWLOntology> ontologies) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isIrreflexive(OWLOntology ontology) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isIrreflexive(Set<OWLOntology> ontologies) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isReflexive(OWLOntology ontology) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isReflexive(Set<OWLOntology> ontologies) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSymmetric(OWLOntology ontology) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSymmetric(Set<OWLOntology> ontologies) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isTransitive(OWLOntology ontology) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isTransitive(Set<OWLOntology> ontologies) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<OWLObjectPropertyExpression> getDisjointProperties(OWLOntology ontology) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<OWLObjectPropertyExpression> getDisjointProperties(Set<OWLOntology> ontologies) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<OWLClassExpression> getDomains(OWLOntology ontology) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<OWLClassExpression> getDomains(Set<OWLOntology> ontologies) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<OWLObjectPropertyExpression> getEquivalentProperties(OWLOntology ontology) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<OWLObjectPropertyExpression> getEquivalentProperties(Set<OWLOntology> ontologies) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<OWLClassExpression> getRanges(OWLOntology ontology) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<OWLClassExpression> getRanges(Set<OWLOntology> ontologies) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<OWLObjectPropertyExpression> getSubProperties(OWLOntology ontology) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<OWLObjectPropertyExpression> getSubProperties(Set<OWLOntology> ontologies) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<OWLObjectPropertyExpression> getSuperProperties(OWLOntology ontology) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<OWLObjectPropertyExpression> getSuperProperties(Set<OWLOntology> ontologies) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isAnonymous() {
		return true;
	}

	@Override
	public boolean isDataPropertyExpression() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isFunctional(OWLOntology ontology) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isFunctional(Set<OWLOntology> ontologies) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isOWLBottomDataProperty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isOWLBottomObjectProperty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isOWLTopDataProperty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isOWLTopObjectProperty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isObjectPropertyExpression() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<OWLClass> getClassesInSignature() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<OWLDataProperty> getDataPropertiesInSignature() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<OWLDatatype> getDatatypesInSignature() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<OWLNamedIndividual> getIndividualsInSignature() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<OWLObjectProperty> getObjectPropertiesInSignature() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<OWLEntity> getSignature() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected int compareObjectOfSameType(OWLObject object) {
		throw new UnsupportedOperationException();
	}
}
