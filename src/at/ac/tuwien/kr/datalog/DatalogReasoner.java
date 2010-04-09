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
import at.ac.tuwien.kr.dlprogram.Term;


/**
 * TODO describe this class please.
 */
public interface DatalogReasoner {

	boolean query(List<Clause> program, Clause query);

	public enum TYPE {
		XSB, DLV
	}

	

	boolean query(List<Clause> program, Term query);



	

	
}
