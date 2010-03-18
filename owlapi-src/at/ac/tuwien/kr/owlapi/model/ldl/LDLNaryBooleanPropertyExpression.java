/*
 * @(#)OWLNaryBooleanPropertyExpression.java 2010-3-10 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.owlapi.model.ldl;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyExpression;

/**
 * NaryBooleanPropertyExpression
 */
public interface LDLNaryBooleanPropertyExpression extends OWLObjectPropertyExpression{
    Set<OWLObjectPropertyExpression> getOperands();

    /**
     * Gets the class expressions returned by {@link #getOperands()} as a list of class expressions.
     * @return The property expressions as a list.
     */
    List<OWLObjectPropertyExpression> getOperandsAsList();
    
    public <O> O accept(LDLPropertyExpressionVisitorEx<O> visitor);
}
