/*
 * @(#)DatalogEngine.java 2010-3-17 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package org.semanticweb.drew.datalog;

import java.util.List;

import org.semanticweb.drew.dlprogram.Clause;
import org.semanticweb.drew.dlprogram.DLProgram;
import org.semanticweb.drew.dlprogram.Literal;


/**
 * Interface for Datalog Reasoner
 */
public interface DatalogReasoner {
	
	public enum TYPE {
		XSB, DLV
	}
	
//	boolean query(List<Clause> program, Clause query);

	/**
	 * Query
	 */
	List<Literal> query(List<Clause> program, Literal query);
	
	/**
	 * Instance Checking
	 * @param program
	 * @param query
	 * @return
	 */
	boolean isEntailed(List<Clause> program, Literal query);

//	boolean query(DLProgram program, Clause query);
//
	List<Literal> query(DLProgram program, Literal query);

	
	/**
	 * Instance Checking
	 * @param program
	 * @param query
	 * @return
	 */
	boolean isEntailed(DLProgram program, Literal query);

}
