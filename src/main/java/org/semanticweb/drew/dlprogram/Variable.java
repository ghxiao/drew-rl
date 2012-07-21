package org.semanticweb.drew.dlprogram;

/**
 * Variable implementation.
 * 
 */
public class Variable implements Term {
	String name;

	int hash;

	/**
	 * Constructor that is only visible inside the package. Customer should use {@link CacheManager} to create process unique
	 * variables.
	 * 
	 * @param name name of the term
	 */
	Variable(String name) {
		this.name = name;
		hash = name.hashCode();
	}

	/**
	 * Get the name of the term.
	 * 
	 * @return name of the term
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public Variable clone() {
		return this;
	}

	@Override
	public boolean equals(Object that) {
		return this == that;
	}

	@Override
	public int hashCode() {
		return hash;
	}
}