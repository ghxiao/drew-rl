/*
 * @(#)DLAtom.java 2010-4-1 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package org.semanticweb.drew.dlprogram;

import org.semanticweb.owlapi.model.OWLLogicalEntity;

/**
 * TODO describe this class please.
 */
public class DLAtomPredicate implements Predicate {

	DLInputSignature inputSigature;

	// only DL concept or role name allowed
	OWLLogicalEntity query;

	private int arity;

	public DLAtomPredicate(DLInputSignature inputSigature, OWLLogicalEntity query) {
		super();
		this.inputSigature = inputSigature;
		this.query = query;
	}

	public DLAtomPredicate() {

	}

	/**
	 * @return the inputSigature
	 */
	public DLInputSignature getInputSigature() {
		return inputSigature;
	}

	/**
	 * @param inputSigature
	 *            the inputSigature to set
	 */
	public void setInputSigature(DLInputSignature inputSigature) {
		this.inputSigature = inputSigature;
	}

	/**
	 * @return the query
	 */
	public OWLLogicalEntity getQuery() {
		return query;
	}

	/**
	 * @param query
	 *            the query to set
	 */
	public void setQuery(OWLLogicalEntity query) {
		this.query = query;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DL[");
		builder.append(inputSigature);
		builder.append(";");
		builder.append(query.getIRI());
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int compareTo(Predicate o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setArity(int arity) {
		this.arity = arity;
//		for (DLInputOperation op : inputSigature.operations) {
//			op.getInputPredicate().setArity(arity);
//		}
	}

	public int getArity() {
		return arity;
	}

}
