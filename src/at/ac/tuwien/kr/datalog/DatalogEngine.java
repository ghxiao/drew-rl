/*
 * @(#)DatalogEngine.java 2010-3-17 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.datalog;

import edu.unika.aifb.kaon.datalog.program.Program;

/**
 * TODO describe this class please.
 */
public interface DatalogEngine {

	boolean query(Program program, DatalogQuery query);

}
