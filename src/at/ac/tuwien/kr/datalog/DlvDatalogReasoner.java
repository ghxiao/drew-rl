/*
 * @(#)DlvDatalogReasoner.java 2010-3-28 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.datalog;

import java.util.BitSet;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.kr.ldlp.reasoner.AxiomCompiler;
import at.ac.tuwien.kr.ldlp.reasoner.DatalogObjectFactory;

import edu.stanford.db.lp.Literal;
import edu.stanford.db.lp.ProgramClause;

import DLV.*;

/**
 * TODO describe this class please.
 */
public class DlvDatalogReasoner implements DatalogReasoner {

	final static Logger logger = LoggerFactory.getLogger(AxiomCompiler.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.ac.tuwien.kr.datalog.DatalogReasoner#query(java.util.List,
	 * edu.stanford.db.lp.ProgramClause)
	 */
	@Override
	public boolean query(List<ProgramClause> program, ProgramClause query) {

		boolean result = false;

		Program dlvProgram = new Program(); // creates a new program
		// p.addString(":- a, not b."); // adds a constraint using a String
		// object
		// p.addProgramFile(".\\test\\misc\\simple.dlv"); // adds a text file
		// containing

		StringBuilder lines = new StringBuilder();

		for (ProgramClause programClause : program) {
			logger.debug("Clause added: {}", programClause);
			lines.append(programClause);
		}

		dlvProgram.addString(lines.toString());

		// build a manager (a Dlv instance)

		final String dlvPath = ".\\dlv\\dlv.mingw.exe";
		logger.debug("Using Dlv: {}", dlvPath);
		DlvHandler dlv = new DlvHandler(dlvPath); // builds a
		// manager
		// object and set the
		// dlv executable full
		// pathname

		dlv.setProgram(dlvProgram); // sets input (contained in a Program
		// object)

		// set invocation parameters

		// for positive datalog proram, there is a unique model
		dlv.setNumberOfModels(2); // sets the number of models to be generated
		// by DLV

		final Literal[] queryLiterals = query.getHead();
		final int n = queryLiterals.length;
		String[] filters = new String[n];
		for (int i = 0; i < n; i++) {
			filters[i] = queryLiterals[i].getPredicateSymbol();
			logger.debug("Using Filter: {}", filters[i]);
		}

		// dlv.setFilter(filters); // sets a -filter=a option
		dlv.setIncludeFacts(false); // sets -nofacts option

		BitSet results = new BitSet(n);

		// call DLV
		try {
			logger.debug("run a DLV process");
			dlv.run(DlvHandler.MODEL_SYNCHRONOUS); // run a DLV process and set
			// the output handling
			// method.

			while (dlv.hasMoreModels()) // waits while DLV outputs a new model
			{
				Model m = dlv.nextModel(); // gets a Model object

				logger.debug("Model: {}", m);
				if (!m.isNoModel()) {

					while (m.hasMorePredicates()) {
						Predicate pr = m.nextPredicate();
						
						while(pr.next()){
							
							DLV.Predicate.Literal lit = pr.getLiteral();
							logger.debug("Literal: {}", lit);
							
							if(lit.toString().equals(queryLiterals[0]+".")){
								result = true;
							}
						}
						
//						while (literals.hasMoreElements()) {
//							Object object = (Object) literals.nextElement();
//							logger.debug("Literal: {}", object);
//						}
						
						
					}

					// m.
					//					
					// while (m.hasMorePredicates()) // work with Predicates
					// // contained in m
					// {
					// Predicate pr = m.nextPredicate(); // gets a Predicate
					// // object
					// System.out.println(pr);
					// }
				}

			}

		} catch (DLVException d) {
			d.printStackTrace();
		} catch (DLVExceptionUncheked d) {
			d.printStackTrace();
		}

		return result;
	}

}
