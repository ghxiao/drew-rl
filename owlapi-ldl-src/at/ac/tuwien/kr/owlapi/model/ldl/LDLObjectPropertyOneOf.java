package at.ac.tuwien.kr.owlapi.model.ldl;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

public interface LDLObjectPropertyOneOf extends OWLObjectPropertyExpression {
	Set<OWLIndividualPair> getOperands();

	/**
	 * Gets the class expressions returned by {@link #getOperands()} as a list
	 * of class expressions.
	 * 
	 * @return The property expressions as a list.
	 */
	List<OWLIndividualPair> getOperandsAsList();

}
