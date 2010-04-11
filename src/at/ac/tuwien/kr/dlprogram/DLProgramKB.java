/*
 * @(#)DLProgram.java 2010-4-3 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.dlprogram;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

import at.ac.tuwien.kr.dlprogram.parser.DLProgramParser;
import at.ac.tuwien.kr.dlprogram.parser.ParseException;

/**
 * TODO describe this class please.
 */
public class DLProgramKB {

	public DLProgramKB() {
	}

	OWLOntology ontology;
	DLProgram program;

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
	public DLProgram getProgram() {
		return program;
	}

	/**
	 * @param program
	 *            the program to set
	 */
	public void setProgram(DLProgram program) {
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
		result = prime * result
				+ ((ontology == null) ? 0 : ontology.hashCode());
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
		if (!(obj instanceof DLProgramKB)) {
			return false;
		}
		DLProgramKB other = (DLProgramKB) obj;
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
		builder.append("DLProgram\n [ontology=");
		builder.append(ontology);
		builder.append(",\n program=");
		builder.append(program);
		builder.append("]");
		return builder.toString();
	}
}
