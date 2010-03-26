/*
 * @(#)LDLPObjectClosure 2010-3-26 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.ldlp.reasoner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * TODO describe this class please.
 */
public class LDLPObjectClosure {

	private Set<OWLIndividual> namedIndividuals;
	private Set<OWLClass> namedClasses;
	private Set<OWLObjectProperty> namedProperties;
	private Set<OWLClassExpression> complexClassExpressions;
	private Set<OWLObjectPropertyExpression> complexPropertyExpressions;

	public LDLPObjectClosure() {
		namedClasses = new HashSet<OWLClass>();
		namedIndividuals = new HashSet<OWLIndividual>();
		namedProperties = new HashSet<OWLObjectProperty>();
		complexClassExpressions = new HashSet<OWLClassExpression>();
		complexPropertyExpressions = new HashSet<OWLObjectPropertyExpression>();
	}

	/**
	 * @return the namedIndividuals
	 */
	public Set<OWLIndividual> getNamedIndividuals() {
		return namedIndividuals;
	}

	/**
	 * @return the namedClasses
	 */
	public Set<OWLClass> getNamedClasses() {
		return namedClasses;
	}

	/**
	 * @return the namedProperties
	 */
	public Set<OWLObjectProperty> getNamedProperties() {
		return namedProperties;
	}

	/**
	 * @return the complexClassExpressions
	 */
	public Set<OWLClassExpression> getComplexClassExpressions() {
		return complexClassExpressions;
	}

	/**
	 * @return the complexPropertyExpressions
	 */
	public Set<OWLObjectPropertyExpression> getComplexPropertyExpressions() {
		return complexPropertyExpressions;
	}

	public LDLPObjectClosure addNamedClasses(OWLClass cls) {
		this.namedClasses.add(cls);
		return this;
	}

	public LDLPObjectClosure addNamedProperty(OWLObjectProperty property) {
		this.namedProperties.add(property);
		return this;
	}

	public LDLPObjectClosure addNamedIndividual(OWLIndividual individual) {
		this.namedIndividuals.add(individual);
		return this;
	}

	public LDLPObjectClosure addComplexClass(OWLClassExpression classExpression) {
		this.complexClassExpressions.add(classExpression);
		return this;
	}

	public LDLPObjectClosure addComplexProperty(OWLObjectPropertyExpression propertyExpression) {
		this.complexPropertyExpressions.add(propertyExpression);
		return this;
	}
}
