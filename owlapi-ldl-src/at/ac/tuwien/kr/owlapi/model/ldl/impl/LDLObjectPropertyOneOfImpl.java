/*
 * @(#)LDLObjectPropertyOneOfImpl.java 2010-3-27 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.owlapi.model.ldl.impl;

import java.util.Collections;
import java.util.HashSet;
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

import uk.ac.manchester.cs.owl.owlapi.OWLObjectImpl;

import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyChainOf;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyOneOf;
import at.ac.tuwien.kr.owlapi.model.ldl.OWLIndividualPair;

/**
 * TODO describe this class please.
 */
public class LDLObjectPropertyOneOfImpl extends OWLObjectImpl implements LDLObjectPropertyOneOf {

	private Set<OWLIndividualPair> values;

	public LDLObjectPropertyOneOfImpl(OWLDataFactory dataFactory, Set<OWLIndividualPair> values) {
		super(dataFactory);
		this.values = new HashSet<OWLIndividualPair>(values);
	}

	@Override
	protected int compareObjectOfSameType(OWLObject object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<OWLIndividualPair> getOperands() {
		return Collections.unmodifiableSet(values);
	}

	@Override
	public List<OWLIndividualPair> getOperandsAsList() {
		throw new UnsupportedOperationException();
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
	public void accept(OWLPropertyExpressionVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public <O> O accept(OWLPropertyExpressionVisitorEx<O> visitor) {
		return visitor.visit(this);
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
		throw new UnsupportedOperationException();
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
		return true;
	}

	@Override
	public void accept(OWLObjectVisitor visitor) {
		visitor.visit(this);

	}

	@Override
	public <O> O accept(OWLObjectVisitorEx<O> visitor) {
		return visitor.visit(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((values == null) ? 0 : values.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		if (!(obj instanceof LDLObjectPropertyOneOfImpl)) {
			return false;
		}
		LDLObjectPropertyOneOfImpl other = (LDLObjectPropertyOneOfImpl) obj;
		if (values == null) {
			if (other.values != null) {
				return false;
			}
		} else if (!values.equals(other.values)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("LDLObjectPropertyOneOf(");
		boolean first = true;
		for (OWLIndividualPair pair : this.getOperands()) {
			if (!first) {
				sb.append(",");
			}
			sb.append(pair);
			first = false;
		}
		sb.append(")");
		return sb.toString();
	}

}
