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
public abstract class DLInputOperation {

	// only DL concept or role name allowed
	OWLLogicalEntity dlPredicate;

	NormalPredicate inputPredicate;

	/**
	 * @return the dlPredicate
	 */
	public OWLLogicalEntity getDLPredicate() {
		return dlPredicate;
	}

	public DLInputOperation() {
	}

	public DLInputOperation(OWLLogicalEntity dlPredicate, NormalPredicate inputPredicate) {
		super();
		this.dlPredicate = dlPredicate;
		this.inputPredicate = inputPredicate;
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
}
