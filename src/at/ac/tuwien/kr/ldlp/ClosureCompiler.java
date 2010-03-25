package at.ac.tuwien.kr.ldlp;

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

import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyChainOf;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyIntersectionOf;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyTransitiveClosureOf;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyUnionOf;
import edu.stanford.db.lp.Literal;
import edu.stanford.db.lp.ProgramClause;
import edu.stanford.db.lp.Term;
import edu.stanford.db.lp.VariableTerm;


class ClosureCompiler implements OWLClassExpressionVisitor, OWLPropertyExpressionVisitor, OWLIndividualVisitor {

	VariableTerm X = new VariableTerm("X");
	VariableTerm Y = new VariableTerm("Y");
	VariableTerm Z = new VariableTerm("Z");
	
	private List<ProgramClause> clauses;

	public ClosureCompiler(List<ProgramClause> clauses) {
		this.clauses = clauses;
	}

	@Override
	public void visit(OWLClass ce) {
		// Do nothing
	}

	@Override
	public void visit(OWLObjectIntersectionOf ce) {
		final Set<OWLClassExpression> operands = ce.getOperands();
		int n = operands.size();
		Literal[] head = new Literal[1];
		head[0] = new Literal(ce.toString(), new Term[] { X });
		Literal[] body = new Literal[n];

		int i = 0;
		for (OWLClassExpression operand : operands) {
			body[i] = new Literal(operand.toString(), new Term[] { X });
			i++;
		}

		clauses.add(new ProgramClause(head, body));

	}

	@Override
	public void visit(OWLObjectUnionOf ce) {
		final Set<OWLClassExpression> operands = ce.getOperands();
		int n = operands.size();

		int i = 0;
		for (OWLClassExpression operand : operands) {
			Literal[] head = new Literal[1];
			head[0] = new Literal(ce.toString(), new Term[] { X });
			Literal[] body = new Literal[1];

			body[0] = new Literal(operand.toString(), new Term[] { X });

			clauses.add(new ProgramClause(head, body));
			i++;
		}

	}

	@Override
	public void visit(OWLObjectComplementOf ce) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void visit(OWLObjectSomeValuesFrom ce) {
		Literal[] head = new Literal[1];
		head[0] = new Literal(ce.toString(), new Term[] { X });
		Literal[] body = new Literal[2];
		body[0] = new Literal(ce.getProperty().toString(), new Term[] { X, Y });
		body[1] = new Literal(ce.getFiller().toString(), new Term[] { Y });
		clauses.add(new ProgramClause(head, body));

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

	@Override
	public void visit(OWLObjectMinCardinality ce) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void visit(OWLObjectExactCardinality ce) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void visit(OWLObjectMaxCardinality ce) {
		// TODO Auto-generated method stub
		// Xiao: Take care of it!!!!
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
		throw new UnsupportedOperationException();
	}

	@Override
	public void visit(OWLObjectInverseOf property) {
		throw new UnsupportedOperationException();
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
		head[0] = new Literal(property.toString(), new Term[] { X, Y });
		Literal[] body = new Literal[n];

		int i = 0;
		for (OWLObjectPropertyExpression operand : operands) {
			body[i] = new Literal(operand.toString(), new Term[] { X, Y });
			i++;
		}

		clauses.add(new ProgramClause(head, body));
	}

	@Override
	public void visit(LDLObjectPropertyUnionOf property) {
		final Set<OWLObjectPropertyExpression> operands = property.getOperands();

		for (OWLObjectPropertyExpression operand : operands) {
			Literal[] head = new Literal[1];
			head[0] = new Literal(property.toString(), new Term[] { X, Y });
			Literal[] body = new Literal[1];

			body[0] = new Literal(operand.toString(), new Term[] { X, Y });

			clauses.add(new ProgramClause(head, body));
		}

	}

	@Override
	public void visit(LDLObjectPropertyTransitiveClosureOf property) {
		final OWLObjectPropertyExpression operand = property.getOperand();

		Literal[] head = new Literal[1];
		head[0] = new Literal(property.toString(), new Term[] { X, Y });
		Literal[] body1 = new Literal[1];
		body1[0] = new Literal(operand.toString(), new Term[] { X, Y });
		clauses.add(new ProgramClause(head, body1));
		Literal[] body2 = new Literal[2];
		body2[0] = new Literal(operand.toString(), new Term[] { X, Y });
		body2[1] = new Literal(property.toString(), new Term[] { Y, Z });
		clauses.add(new ProgramClause(head, body2));
	}

	@Override
	public void visit(LDLObjectPropertyChainOf property) {
		final Set<OWLObjectPropertyExpression> operands = property.getOperands();
		int n = operands.size();
		VariableTerm[] Xs = new VariableTerm[n + 1];
		for (int i = 0; i < n + 1; i++) {
			Xs[i] = new VariableTerm("X" + i);
		}

		Literal[] head = new Literal[1];
		head[0] = new Literal(property.toString(), new Term[] { Xs[0], Xs[n] });
		Literal[] body = new Literal[n];
		int i = 0;
		for (OWLObjectPropertyExpression operand : operands) {
			body[i] = new Literal(operand.toString(), new Term[] { Xs[i], Xs[i + 1] });
			i++;
		}
		clauses.add(new ProgramClause(head, body));

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

