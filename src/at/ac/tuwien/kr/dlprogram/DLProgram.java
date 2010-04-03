/*
 * @(#)DLProgram.java 2010-4-3 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.dlprogram;

import org.semanticweb.owlapi.model.OWLOntology;

/**
 * TODO describe this class please.
 */
public class DLProgram {
	OWLOntology ontology;
	Program program;

	/**
	 * @return the ontology
	 */
	public OWLOntology getOntology() {
		return ontology;
	}

	/**
	 * @param ontology
	 *            the ontology to set
	 */
	public void setOntology(OWLOntology ontology) {
		this.ontology = ontology;
	}

	/**
	 * @return the program
	 */
	public Program getProgram() {
		return program;
	}

	/**
	 * @param program
	 *            the program to set
	 */
	public void setProgram(Program program) {
		this.program = program;
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
		result = prime * result + ((ontology == null) ? 0 : ontology.hashCode());
		result = prime * result + ((program == null) ? 0 : program.hashCode());
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
		if (!(obj instanceof DLProgram)) {
			return false;
		}
		DLProgram other = (DLProgram) obj;
		if (ontology == null) {
			if (other.ontology != null) {
				return false;
			}
		} else if (!ontology.equals(other.ontology)) {
			return false;
		}
		if (program == null) {
			if (other.program != null) {
				return false;
			}
		} else if (!program.equals(other.program)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DLProgram [ontology=");
		builder.append(ontology);
		builder.append(", program=");
		builder.append(program);
		builder.append("]");
		return builder.toString();
	}
}
