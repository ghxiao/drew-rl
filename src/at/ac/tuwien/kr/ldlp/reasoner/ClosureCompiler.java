package at.ac.tuwien.kr.ldlp.reasoner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLIndividualVisitor;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyChainOf;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyIntersectionOf;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyTransitiveClosureOf;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyUnionOf;
import edu.stanford.db.lp.Literal;
import edu.stanford.db.lp.ProgramClause;
import edu.stanford.db.lp.Term;
import edu.stanford.db.lp.VariableTerm;

public class ClosureCompiler implements OWLClassExpressionVisitor, OWLPropertyExpressionVisitor, OWLIndividualVisitor {

	final static Logger logger = LoggerFactory.getLogger(ClosureCompiler.class);

	DatalogObjectFactory datalogObjectFactory = DatalogObjectFactory.getInstance();

	VariableTerm X = new VariableTerm("X");
	VariableTerm Y = new VariableTerm("Y");
	VariableTerm Z = new VariableTerm("Z");

	String TOP = datalogObjectFactory.getTopPrediate();
	String NOTEQUAL = datalogObjectFactory.getNotEqual();

	private List<ProgramClause> clauses;

	public ClosureCompiler() {
		this.clauses = new ArrayList<ProgramClause>();
	}

	public List<ProgramClause> compile(LDLPObjectClosure closure) {

		for (OWLClass cls : closure.getNamedClasses()) {
			cls.accept(this);
		}

		for (OWLClassExpression cls : closure.getComplexClassExpressions()) {
			cls.accept(this);
		}

		for (OWLObjectProperty prop : closure.getNamedProperties()) {
			prop.accept(this);
		}

		for (OWLObjectPropertyExpression prop : closure.getComplexPropertyExpressions()) {
			prop.accept(this);
		}

		return clauses;
	}

	/**
	 * <pre>
	 * 	top(X):-A(X).
	 * </pre>
	 */
	@Override
	public void visit(OWLClass ce) {
		if (ce.isTopEntity()) {
			return;
		}

		Literal[] head = new Literal[1];
		head[0] = new Literal(TOP, X);
		Literal[] body = new Literal[1];
		String predicate = datalogObjectFactory.getPredicate(ce);
		body[0] = new Literal(predicate, X);
		ProgramClause clause = new ProgramClause(head, body);
		logger.debug("{}\n\t->\n{}", ce, clause);
		clauses.add(clause);
	}

	/**
	 * <pre>
	 * (A and B and C)(X):-A(X),B(X),C(X).
	 * </pre>
	 */
	@Override
	public void visit(OWLObjectIntersectionOf ce) {
		final Set<OWLClassExpression> operands = ce.getOperands();
		int n = operands.size();
		Literal[] head = new Literal[1];
		head[0] = new Literal(datalogObjectFactory.getPredicate(ce), new Term[] { X });
		Literal[] body = new Literal[n];

		int i = 0;
		for (OWLClassExpression operand : operands) {
			body[i] = new Literal(datalogObjectFactory.getPredicate(operand), new Term[] { X });
			i++;
		}

		final ProgramClause clause = new ProgramClause(head, body);

		logger.debug("{}\n\t->\n{}", ce, clause);

		clauses.add(clause);

	}

	/**
	 * <pre>
	 * 	(A or B or C)(X):-A(X).
	 * 	(A or B or C)(X):-B(X).
	 * 	(A or B or C)(X):-C(X).
	 * </pre>
	 * 
	 */
	@Override
	public void visit(OWLObjectUnionOf ce) {
		final Set<OWLClassExpression> operands = ce.getOperands();
		int n = operands.size();

		int i = 0;
		for (OWLClassExpression operand : operands) {
			Literal[] head = new Literal[1];
			head[0] = new Literal(datalogObjectFactory.getPredicate(ce), new Term[] { X });
			Literal[] body = new Literal[1];

			body[0] = new Literal(datalogObjectFactory.getPredicate(operand), new Term[] { X });

			final ProgramClause clause = new ProgramClause(head, body);
			clauses.add(clause);
			logger.debug("{}\n\t->\n{}", ce, clause);
			i++;
		}

	}

	@Override
	public void visit(OWLObjectComplementOf ce) {
		throw new UnsupportedOperationException();

	}

	/**
	 * <pre>
	 * (E some A)(X):-E(X,Y),A(Y).
	 * </pre>
	 */
	@Override
	public void visit(OWLObjectSomeValuesFrom ce) {
		Literal[] head = new Literal[1];
		head[0] = new Literal(datalogObjectFactory.getPredicate(ce), new Term[] { X });
		Literal[] body = new Literal[2];
		body[0] = new Literal(datalogObjectFactory.getPredicate(ce.getProperty()), new Term[] { X, Y });
		body[1] = new Literal(datalogObjectFactory.getPredicate(ce.getFiller()), new Term[] { Y });
		final ProgramClause clause = new ProgramClause(head, body);
		clauses.add(clause);
		logger.debug("{}\n\t->\n{}", ce, clause);

	}

	@Override
	public void visit(OWLObjectAllValuesFrom ce) {
		// TODO: non normal form?
		throw new UnsupportedOperationException();

	}

	@Override
	public void visit(OWLObjectHasValue ce) {
		// TODO Auto-generated method stub

	}

	/**
	 * <pre>
	 * (E min n D)(X):- E(X,Y1),D(Y1),...,E(X,Yn),D(Yn), 
	 * 					Y1 != Y2, Y1 != Y3, ..., Yn-1 != Yn
	 * </pre>
	 */
	@Override
	public void visit(OWLObjectMinCardinality ce) {
		// Xiao: Take care of it!!!!

		final OWLObjectPropertyExpression E = ce.getProperty();
		final int n = ce.getCardinality();
		final OWLClassExpression D = ce.getFiller();
		Literal[] head = new Literal[1];
		head[0] = new Literal(datalogObjectFactory.getPredicate(ce), new Term[] { X });
		List<Literal> bodyLiterals = new ArrayList<Literal>();
		VariableTerm[] Ys = new VariableTerm[n];
		for (int i = 0; i < Ys.length; i++) {
			Ys[i] = new VariableTerm("Y" + (i + 1));
		}

		final String pE = datalogObjectFactory.getPredicate(E);
		final String pD = datalogObjectFactory.getPredicate(D);

		for (int i = 0; i < n; i++) {
			bodyLiterals.add(new Literal(pE, X, Ys[i]));
			bodyLiterals.add(new Literal(pD, Ys[i]));
		}

		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				bodyLiterals.add(new Literal(NOTEQUAL, Ys[i], Ys[j]));
			}
		}
		Literal[] body = new Literal[0];
		body = bodyLiterals.toArray(body);
		final ProgramClause clause = new ProgramClause(head, body);
		clauses.add(clause);
		logger.debug("{}\n\t->\n{}", ce, clause);
	}

	@Override
	public void visit(OWLObjectExactCardinality ce) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void visit(OWLObjectMaxCardinality ce) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void visit(OWLObjectHasSelf ce) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void visit(OWLObjectOneOf ce) {
		// TODO Auto-generated method stub
		// Xiao:Take care of it!!!!
	}

	@Override
	public void visit(OWLDataSomeValuesFrom ce) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void visit(OWLDataAllValuesFrom ce) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void visit(OWLDataHasValue ce) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void visit(OWLDataMinCardinality ce) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void visit(OWLDataExactCardinality ce) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void visit(OWLDataMaxCardinality ce) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void visit(OWLObjectProperty property) {
		if (property.isTopEntity()) {
			return;
		}

		Literal[] head = new Literal[1];
		head[0] = new Literal(TOP, X, Y);
		Literal[] body = new Literal[1];
		String predicate = datalogObjectFactory.getPredicate(property);
		body[0] = new Literal(predicate, X, Y);
		ProgramClause clause = new ProgramClause(head, body);
		clauses.add(clause);
		logger.debug("{}\n\t->\n{}", property, clause);
	}

	@Override
	public void visit(OWLObjectInverseOf property) {
		Literal[] head = new Literal[1];
		final OWLObjectPropertyExpression inverse = property.getInverse();
		head[0] = new Literal(datalogObjectFactory.getPredicate(inverse), X, Y);
		Literal[] body = new Literal[1];
		String predicate = datalogObjectFactory.getPredicate(property);
		body[0] = new Literal(predicate, Y, X);
		ProgramClause clause = new ProgramClause(head, body);
		clauses.add(clause);
		logger.debug("{}\n\t->\n{}", property, clause);
	}

	@Override
	public void visit(OWLDataProperty property) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void visit(LDLObjectPropertyIntersectionOf property) {
		final Set<OWLObjectPropertyExpression> operands = property.getOperands();
		int n = operands.size();
		Literal[] head = new Literal[1];
		head[0] = new Literal(datalogObjectFactory.getPredicate(property), new Term[] { X, Y });
		Literal[] body = new Literal[n];

		int i = 0;
		for (OWLObjectPropertyExpression operand : operands) {
			body[i] = new Literal(datalogObjectFactory.getPredicate(operand), new Term[] { X, Y });
			i++;
		}

		final ProgramClause clause = new ProgramClause(head, body);
		clauses.add(clause);
		logger.debug("{}\n\t->\n{}", property, clause);
	}

	@Override
	public void visit(LDLObjectPropertyUnionOf property) {
		final Set<OWLObjectPropertyExpression> operands = property.getOperands();

		for (OWLObjectPropertyExpression operand : operands) {
			Literal[] head = new Literal[1];
			head[0] = new Literal(datalogObjectFactory.getPredicate(property), new Term[] { X, Y });
			Literal[] body = new Literal[1];

			body[0] = new Literal(datalogObjectFactory.getPredicate(operand), new Term[] { X, Y });

			final ProgramClause clause = new ProgramClause(head, body);
			clauses.add(clause);
			logger.debug("{}\n\t->\n{}", property, clause);
		}

	}

	// trans(E)(X,Y):-E(X,Y)
	// trans(E)(X,Y):-E(X),trans(X,Y).
	@Override
	public void visit(LDLObjectPropertyTransitiveClosureOf property) {
		final OWLObjectPropertyExpression operand = property.getOperand();

		Literal[] head = new Literal[1];
		head[0] = new Literal(datalogObjectFactory.getPredicate(property), new Term[] { X, Y });
		Literal[] body1 = new Literal[1];
		body1[0] = new Literal(datalogObjectFactory.getPredicate(operand), new Term[] { X, Y });
		final ProgramClause clause1 = new ProgramClause(head, body1);
		logger.debug("{}\n\t->\n{}", property, clause1);
		clauses.add(clause1);
		Literal[] body2 = new Literal[2];
		body2[0] = new Literal(datalogObjectFactory.getPredicate(operand), new Term[] { X, Y });
		body2[1] = new Literal(datalogObjectFactory.getPredicate(property), new Term[] { Y, Z });
		final ProgramClause clause2 = new ProgramClause(head, body2);
		clauses.add(clause2);
		logger.debug("{}\n\t->\n{}", property, clause2);
	}

	// compose(E1, E2, ... En)(X1,Xn+1):- E1(X1,X2), E2(X2,X3), ... ,
	// En(Xn,Xn+1)
	@Override
	public void visit(LDLObjectPropertyChainOf property) {
		final Set<OWLObjectPropertyExpression> operands = property.getOperands();
		int n = operands.size();
		VariableTerm[] Xs = new VariableTerm[n + 1];
		for (int i = 0; i < n + 1; i++) {
			Xs[i] = new VariableTerm("X" + (i + 1));
		}

		Literal[] head = new Literal[1];
		head[0] = new Literal(datalogObjectFactory.getPredicate(property), new Term[] { Xs[0], Xs[n] });
		Literal[] body = new Literal[n];
		int i = 0;
		for (OWLObjectPropertyExpression operand : operands) {
			body[i] = new Literal(datalogObjectFactory.getPredicate(operand), new Term[] { Xs[i], Xs[i + 1] });
			i++;
		}
		final ProgramClause clause = new ProgramClause(head, body);
		clauses.add(clause);
		logger.debug("{}\n\t->\n{}", property, clause);

	}

	@Override
	public void visit(OWLNamedIndividual individual) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OWLAnonymousIndividual individual) {
		// TODO Auto-generated method stub

	}

}
