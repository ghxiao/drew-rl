package at.ac.tuwien.kr.dlprogram;

import org.semanticweb.owlapi.model.OWLLogicalEntity;

public class DLInputAddOperation extends DLInputOperation {

	public DLInputAddOperation(OWLLogicalEntity dlPredicate, Predicate inputPredicate) {
		super(dlPredicate, inputPredicate);	
	}

	public DLInputAddOperation() {
		super();
	}

	@Override
	public String toString() {
		return String.format("%s+=%s", getDLPredicate().getIRI().toString(), getInputPredicate().getName());

	}
}
