/*
 * @(#)OWLIndividualPair.java 2010-3-27 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.owlapi.model.ldl;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * Individual Pair <first, second>
 */
public interface LDLIndividualPair extends OWLObjectPropertyExpression{

	OWLIndividual getFirst();

	OWLIndividual getSecond();


}
