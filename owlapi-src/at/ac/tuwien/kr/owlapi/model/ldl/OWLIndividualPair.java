/*
 * @(#)OWLIndividualPair.java 2010-3-27 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.owlapi.model.ldl;

import org.semanticweb.owlapi.model.OWLIndividual;

/**
 * TODO describe this class please.
 */
public class OWLIndividualPair {

	public OWLIndividualPair(OWLIndividual first, OWLIndividual second) {
		super();
		this.first = first;
		this.second = second;
	}

	OWLIndividual first, second;

	/**
	 * @return the first
	 */
	public OWLIndividual getFirst() {
		return first;
	}

	/**
	 * @return the second
	 */
	public OWLIndividual getSecond() {
		return second;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof OWLIndividualPair)) {
			return false;
		}
		OWLIndividualPair other = (OWLIndividualPair) obj;
		if (first == null) {
			if (other.first != null) {
				return false;
			}
		} else if (!first.equals(other.first)) {
			return false;
		}
		if (second == null) {
			if (other.second != null) {
				return false;
			}
		} else if (!second.equals(other.second)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "{" + first.toString() + "," + second.toString() + "}";
	}
}
