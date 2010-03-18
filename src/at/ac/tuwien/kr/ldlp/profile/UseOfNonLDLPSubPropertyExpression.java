/*
 * @(#)UseOfNonSubObjectPropertyExpression.java 2010-3-17 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.ldlp.profile;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.profiles.OWLProfileViolation;

/**
 * TODO describe this class please.
 */
public class UseOfNonLDLPSubPropertyExpression extends OWLProfileViolation {

	private OWLObjectPropertyExpression propertyExpression;

	public UseOfNonLDLPSubPropertyExpression(OWLOntology ontology, OWLAxiom axiom, OWLObjectPropertyExpression propertyExpression) {
		super(ontology, axiom);
		this.propertyExpression = propertyExpression;
	}

	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Use of non-subproperty expression in position that requires a subproperty expression: ");
        sb.append(propertyExpression);
        sb.append(" [");
        sb.append(getAxiom());
        sb.append(" in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
	
}
