package org.semanticweb.drew.datalog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.dlv.program.Literal;
import it.unical.mat.dlv.program.Program;
import it.unical.mat.wrapper.DLVError;
import it.unical.mat.wrapper.DLVInputProgram;
import it.unical.mat.wrapper.DLVInputProgramImpl;
import it.unical.mat.wrapper.DLVInvocation;
import it.unical.mat.wrapper.DLVInvocationException;
import it.unical.mat.wrapper.DLVWrapper;
import it.unical.mat.wrapper.Model;
import it.unical.mat.wrapper.ModelBufferedHandler;
import it.unical.mat.wrapper.ModelHandler;
import it.unical.mat.wrapper.ModelResult;
import it.unical.mat.wrapper.Predicate;
import it.unical.mat.wrapper.PredicateHandlerWithName;
import it.unical.mat.wrapper.PredicateResult;

public class OfficalDLVWrapperTest {
	private static final String PATH_DLV = "/Users/xiao/bin/dlv";

	public static void main(String[] args) throws DLVInvocationException, IOException {
		/* I create a new instance of DLVInputProgram */
		DLVInputProgram inputProgram = new DLVInputProgramImpl();

		/* I can add some file to the DLVInputProgram */
		// File file1=new File(PATH_1);
		// File file2=new File(PATH_2);

		// inputProgram.addFile(file1);
		// inputProgram.addFile(file2);

		/* I can specify a part of DLV program using simple strings */
		inputProgram.addText(" a :- not b. b:- not a. ");

		/*
		 * I can specify a part of DLV program using an entire Program or an
		 * Expression
		 */
		Program program = new Program();
		// program.add(new Rule(" A STRING "));
		inputProgram.includeProgram(program);

		/*
		 * I can add directly an Expression that will be added into the default
		 * program stored in the DLVInvocation
		 */
		// inputProgram.addExpression(new Rule(" A STRING "));
		// The next step is to prepare a new DLV invocation, the class
		// DLVWrapper provides a new instance of DLVInvocation, using the
		// default path or specifying another path of DLV executable:
		/*
		 * I create a new instance of DLVInvocation using the DLVWrapper class
		 * specifying a path for DLV executable
		 */
		DLVInvocation invocation = DLVWrapper.getInstance().createInvocation(PATH_DLV);
		// The DLVInvocation object is configurable using different parameters
		// before the real execution. The DLVInvocation class provides methos
		// necessary to set the query and to set the execution options.
		/*
		 * Set the query of DLV invocation. If the Query in the DLVInvocation is
		 * not null, any queries in the inputProgram(in the text, files or
		 * defaultProgram) is ignored.
		 */
		// invocation.setQuery(new Query( QUERY STRING );

		/*
		 * The class DLVInvocation have a method to specify the execution
		 * options by simple strings
		 */
		//invocation.addOption(" OPTION STRING ");

		/*
		 * Alternatively DLVInvocation provides specified methods to set the
		 * execution options
		 */

		/*
		 * Set the max-int '-N' option. Set the limit of the integer to [0,N] in
		 * the DLV invocation. This function remove automatically any existing
		 * max-int '-N' option.
		 */
		invocation.setMaxint(10);

		/*
		 * Sets the max number of models computed in the DLV execution. If n is
		 * 0, all models are computed. This method deletes each option that
		 * contains the string "-n=" and adds a new option "-n='n'.
		 */
		invocation.setNumberOfModels(10);

		/*
		 * Includes only instances of the predicate specified in the filter
		 * parameter in output. This method deletes each option that contains
		 * the string filter= or -pfilter=, and adds a new option with the new
		 * filter
		 */
		List<String> filters = new ArrayList<String>();
		// filters.add( FILTER STRING );
		// invocation.setFilter(filter, true);
		// The options can be eliminated with the respective methods:
		/* Remove any n° of models option */
		invocation.resetNumberOfModels();

		/* Remove any max-int option */
		invocation.resetMaxint();

		/* Reset any filter option */
		invocation.resetFilter();

		/* Reset all options */
		invocation.resetFilter();
		// When the invocation setting is complete, can be create the observers
		// that receive the notifications about the results of execution. Every
		// DLVHandler (the observer) must be registered on the DLVInvocation
		// (the observable) to receive the notifications.
		/*
		 * I create a new observer that receive a notification for the models
		 * computed and store them in a list
		 */
		final List<ModelResult> models = new ArrayList<ModelResult>();
		ModelHandler modelHandler = new ModelHandler() {
			@Override
			final public void handleResult(DLVInvocation obsd, ModelResult res) {
				models.add(res);
			}
		};

		/* Subscribe the handler from the DLVInvocation */
		invocation.subscribe(modelHandler);

		/*
		 * I create a new observer that receive a notification for the
		 * predicates computed with a specified name and store them in a list
		 */
		final List<PredicateResult> predicates = new ArrayList<PredicateResult>();
		PredicateHandlerWithName predicateHandler = new PredicateHandlerWithName() {
			public void handleResult(DLVInvocation obsd, PredicateResult res) {
				predicates.add(res);
			}

			public List<String> getPredicateNames() {
				List<String> predicates = new ArrayList<String>();
				// predicates.add( PREDICATE NAME1 );
				// predicates.add( PREDICATE NAME2 );
				return predicates;
			}
		};

		/* Subscribe the handler from the DLVInvocation */
		invocation.subscribe(predicateHandler);

		/*
		 * Another mode to scroll the computed models is to use the
		 * ModelBufferedHandler that is a concrete observer that work like
		 * Enumeration. NOTE: the ModelBufferedHandler subscribe itself to the
		 * DLVInvocation
		 */
		ModelBufferedHandler modelBufferedHandler = new ModelBufferedHandler(invocation);

		/* In this moment I can start the DLV execution */
		invocation.run();

		/* Scroll all models computed */
		while (modelBufferedHandler.hasMoreModels()) {
			Model model = modelBufferedHandler.nextModel();
			while (model.hasMorePredicates()) {
				Predicate predicate = model.nextPredicate();
				while (predicate.hasMoreLiterals()) {
					Literal literal = predicate.nextLiteral();
				}
			}
		}

		/* If i wont to wait the finish of execution, i can use thi method */
		invocation.waitUntilExecutionFinishes();

		/*
		 * At the term of execution, I can control the errors created by DLV
		 * invocation
		 */
		List<DLVError> errors = invocation.getErrors();

	}
}
