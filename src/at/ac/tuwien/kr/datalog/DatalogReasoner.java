/*
 * @(#)DatalogEngine.java 2010-3-17 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.datalog;

import java.util.List;

import edu.stanford.db.lp.ProgramClause;
import edu.unika.aifb.kaon.datalog.program.Program;

/**
 * TODO describe this class please.
 */
public interface DatalogReasoner {

	boolean query(List<ProgramClause> program, DatalogQuery query);

}
