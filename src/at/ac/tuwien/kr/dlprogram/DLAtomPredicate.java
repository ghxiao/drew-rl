/*
 * @(#)DLAtom.java 2010-4-1 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.dlprogram;

import org.semanticweb.owlapi.model.OWLObject;

/**
 * TODO describe this class please.
 */
public class DLAtomPredicate extends Predicate{

	DLAtomPredicate(String name, int arity) {
		super(name, arity);
	}

	DLInputSignature inputSigature;
	
	//only DL concept or role name allowed 
	OWLObject query;
	
	
}
