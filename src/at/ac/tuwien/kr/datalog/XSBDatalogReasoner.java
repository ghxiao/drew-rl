/*
 * @(#)XSBDatalogEngine.java 2010-3-17 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.datalog;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.stanford.db.lp.ConstTerm;
import edu.stanford.db.lp.Literal;
import edu.stanford.db.lp.ProgramClause;
import edu.stanford.db.lp.Term;
import edu.stanford.db.lp.VariableTerm;
import edu.stanford.db.xsb.XSBCore;

/**
 * TODO describe this class please.
 */
public class XSBDatalogReasoner implements DatalogReasoner {

	final static Logger logger = LoggerFactory.getLogger(XSBDatalogReasoner.class);

	@Override
	public boolean query(List<ProgramClause> program, ProgramClause query) {
		// create engine (there can only be one engine loaded!)
		XSBCore core = new XSBCore();

		// initialization parameter
		// String[] xsbargs = { "/local/xsb/XSB", "-n", "--quietload" };
		String[] xsbargs = { "/home/staff/xiao/local/xsb/xsb3.2", "--noprompt",
				"--quietload" };

		logger.debug("initilizing XSB with args: [{} {} {}]", xsbargs);

		int i;

		// initialize XSB
		i = core.xsb_init(xsbargs);

		logger.debug("XSB initialized (i={})", i);

		// assert rules defining the predicate members (using
		// XSB-ASCII-representation of rules)
		// (since Java uses unicode, the glue code translates to to ASCII)

		for (ProgramClause clause : program) {
			String command = String.format("assert(%s).", clause
					.toStringWithoutDot());
			logger.debug("XSB Command: {} ", command);
			i = core.xsb_assert_rule(clause);
			
			//i = core.xsb_command_string(command);
		}

		logger.debug("Query: {} ", query);
		int t = core.xsb_query_string(query.toString() + ".");

		logger.debug("XSB Query Result: {} ", t);
		System.out.println(t);

		// thats it....
		i = core.xsb_close();
		logger.debug("XSB Closed: {} ", i);
		return t == 0;
	}

	public static void main(String[] args) {

		// create engine (there can only be one engine loaded!)
		XSBCore core = new XSBCore();

		// initialization parameter
		// String[] xsbargs = { "/local/xsb/XSB", "-n", "--quietload" };
		String[] xsbargs = { "/home/staff/xiao/local/xsb/xsb3.2", "--noprompt",
				"--quietload" };

		int i;

		// initialize XSB
		i = core.xsb_init(xsbargs);

		System.out.println("initilized");

		// p(a).
		Term a = new ConstTerm("a");

		Literal[] head = new Literal[1];
		Literal pa = new Literal("p", new Term[] { a });
		head[0] = pa;
		Literal[] body = new Literal[] {};
		ProgramClause p_a = new ProgramClause(head, body);

		List<ProgramClause> program = Collections.singletonList(p_a);
		Literal query = pa;

		// i = core.xsb_command_string("table(p/1).");
		// core.xsb_command_string("assert(p(a)).");
		//	
		// // assert rules defining the predicate members (using
		// // XSB-ASCII-representation of rules)
		// // (since Java uses unicode, the glue code translates to to ASCII)
		//
		for (ProgramClause clause : program) {
			// i = core.xsb_assert_rule(clause);
			String command = String.format("assert(%s).", clause
					.toStringWithoutDot());
			System.out.println(command);
			i = core.xsb_command_string(command);
		}

		int t = core.xsb_query_string(query.toString() + ".");

		// core.xsb_query_java(new ProgramClause(new Literal[] {},
		// new Literal[] { query }));
		// // get answers and print them..
		// while (i == 0) {
		// Term[] term = core.xsb_getAnswerSubstitution();
		// int k = term.length;
		// for (int l = 0; l < k; l++) {
		// System.out.print(term[l].toString());
		// }
		// System.out.println();
		// i = core.xsb_next();
		// }
		//
		// // thats it....
		i = core.xsb_close();
	}

}
