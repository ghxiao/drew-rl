/*
 * @(#)DatalogEngine.java 2010-3-17 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.datalog;

import java.util.List;

import at.ac.tuwien.kr.dlprogram.Clause;
import at.ac.tuwien.kr.dlprogram.DLProgram;
import at.ac.tuwien.kr.dlprogram.Literal;

/**
 * TODO describe this class please.
 */
public interface DatalogReasoner {
	
	public enum TYPE {
		XSB, DLV
	}
	
	boolean query(List<Clause> program, Clause query);

	boolean query(List<Clause> program, Literal query);

	boolean query(DLProgram program, Clause query);

	boolean query(DLProgram program, Literal query);

}
