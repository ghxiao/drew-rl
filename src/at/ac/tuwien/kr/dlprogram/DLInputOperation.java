/*
 * @(#)DLQuery.java 2010-4-1 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.dlprogram;

import org.semanticweb.owlapi.model.OWLLogicalEntity;

/**
 * TODO describe this class please.
 */
public class DLInputOperation {

	// only DL concept or role name allowed
	OWLLogicalEntity dlPredicate;

	NormalPredicate inputPredicate;

	DLInputOperationType type;

	/**
	 * @return the dlPredicate
	 */
	public OWLLogicalEntity getDLPredicate() {
		return dlPredicate;
	}

	public DLInputOperation() {
	}

	public DLInputOperation(OWLLogicalEntity dlPredicate, NormalPredicate inputPredicate, DLInputOperationType type) {
		super();
		this.dlPredicate = dlPredicate;
		this.inputPredicate = inputPredicate;
		this.type = type;
	}

	public DLInputOperation(OWLLogicalEntity dlPredicate, NormalPredicate inputPredicate) {
		this(dlPredicate, inputPredicate, DLInputOperationType.U_PLUS);
	}

	/**
	 * @param dlPredicate
	 *            the dlPredicate to set
	 */
	public void setDLPredicate(OWLLogicalEntity dlPredicate) {
		this.dlPredicate = dlPredicate;
	}

	/**
	 * @return the inputPredicate
	 */
	public NormalPredicate getInputPredicate() {
		return inputPredicate;
	}

	/**
	 * @param inputPredicate
	 *            the inputPredicate to set
	 */
	public void setInputPredicate(NormalPredicate inputPredicate) {
		this.inputPredicate = inputPredicate;
	}

	/**
	 * @return the type
	 */
	public DLInputOperationType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(DLInputOperationType type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(dlPredicate.getIRI());
		builder.append(" ");
		builder.append(type);
		builder.append(" ");
		builder.append(inputPredicate.getName());
		return builder.toString();
	}
}
