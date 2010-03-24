package at.ac.tuwien.kr.owlapi.model.ldl;

import org.semanticweb.owlapi.model.OWLIndividual;

public class OWLIndividualPair {
	OWLIndividual first, second;

	public OWLIndividual getFirst() {
		return first;
	}

	public void setFirst(OWLIndividual first) {
		this.first = first;
	}

	public OWLIndividual getSecond() {
		return second;
	}

	public void setSecond(OWLIndividual second) {
		this.second = second;
	}

	public OWLIndividualPair(OWLIndividual first, OWLIndividual second) {
		super();
		this.first = first;
		this.second = second;
	}
}
