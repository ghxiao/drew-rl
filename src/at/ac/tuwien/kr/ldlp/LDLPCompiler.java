/*
 * @(#)LDLPCompiler.java 2010-3-17 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.ldlp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLIndividual;
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
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitor;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.util.OWLAxiomVisitorAdapter;

import at.ac.tuwien.kr.datalog.DatalogQuery;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyChainOf;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyIntersectionOf;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyTransitiveClosureOf;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLObjectPropertyUnionOf;
import edu.stanford.db.lp.ConstTerm;
import edu.stanford.db.lp.Literal;
import edu.stanford.db.lp.ProgramClause;
import edu.stanford.db.lp.Term;
import edu.stanford.db.lp.VariableTerm;

/**
 * LDLPCompiler: compile an LDLp KB to a datalog program
 */
public class LDLPCompiler {

	VariableTerm X = new VariableTerm("X");
	VariableTerm Y = new VariableTerm("Y");
	VariableTerm Z = new VariableTerm("Z");

	AxiomCompiler axiomCompiler = new AxiomCompiler();

	ClosureBuilder closureBuilder = new ClosureBuilder();

	List<ProgramClause> clauses;

	Set<OWLClassExpression> classExpressionsClosure;

	Set<OWLObjectPropertyExpression> objectPropertyExpressionsClosure;

	Set<OWLIndividual> individualsClosure;

	private OWLOntology ontology;

	OWLDataFactory dataFactory;

	public List<ProgramClause> complileLDLPOntology(OWLOntology ontology) {
		this.ontology = ontology;
		reset();
		for (OWLAxiom axiom : ontology.getAxioms()) {
			complieAxiom(axiom);
		}

		for (OWLAxiom axiom : ontology.getAxioms()) {
			updateClosure(axiom);
		}

		compileClasses();

		complieProperties();

		return clauses;
	}

	private void updateClosure(OWLAxiom axiom) {

		axiom.accept(closureBuilder);
	}

	private void complieAxiom(OWLAxiom axiom) {
		axiom.accept(axiomCompiler);
	}

	private void reset() {
		dataFactory = ontology.getOWLOntologyManager().getOWLDataFactory();
		clauses = new ArrayList<ProgramClause>();
		classExpressionsClosure = new HashSet<OWLClassExpression>();
		objectPropertyExpressionsClosure = new HashSet<OWLObjectPropertyExpression>();
		individualsClosure = new HashSet<OWLIndividual>();
	}

	private void complieProperties() {

	}

	private void compileClasses() {

	}

	public DatalogQuery compileClassAssertionAxiom(OWLClassAssertionAxiom classAssertionAxiom) {
		return null;
	}

	class AxiomCompiler extends OWLAxiomVisitorAdapter {
		@Override
		public void visit(OWLObjectPropertyAssertionAxiom axiom) {
			OWLObjectPropertyExpression cls = axiom.getProperty();
			final OWLIndividual subject = axiom.getSubject();
			final OWLIndividual object = axiom.getObject();
			Literal[] head = null;
			Literal[] body = null;
			head = new Literal[1];
			head[0] = new Literal(cls.toString(), new Term[] { new ConstTerm(subject.toString()), new ConstTerm(object.toString()) });
			body = new Literal[0];
			ProgramClause clause = new ProgramClause(head, body);
			clauses.add(clause);
		}

		@Override
		public void visit(OWLClassAssertionAxiom axiom) {
			OWLClassExpression cls = axiom.getClassExpression();
			OWLIndividual individual = axiom.getIndividual();
			Literal[] head = null;
			Literal[] body = null;
			head = new Literal[1];
			head[0] = new Literal(cls.toString(), new Term[] { new ConstTerm(individual.toString()) });
			body = new Literal[0];
			ProgramClause clause = new ProgramClause(head, body);
			clauses.add(clause);
		}

		@Override
		public void visit(OWLSubClassOfAxiom axiom) {
			final OWLClassExpression subClass = axiom.getSubClass();
			final OWLClassExpression superClass = axiom.getSuperClass();
			Literal[] head = null;
			Literal[] body = null;

			if (!(superClass instanceof OWLObjectAllValuesFrom)) {
				head = new Literal[1];
				head[0] = new Literal(superClass.toString(), new Term[] { X });
				body = new Literal[1];
				body[0] = new Literal(subClass.toString(), new Term[] { X });
			} else {
				OWLObjectAllValuesFrom E_only_A = (OWLObjectAllValuesFrom) superClass;
				final OWLClassExpression A = E_only_A.getFiller();
				final OWLObjectPropertyExpression E = E_only_A.getProperty();
				head = new Literal[1];
				head[0] = new Literal(A.toString(), new Term[] { Y });
				body = new Literal[2];
				body[0] = new Literal(subClass.toString(), new Term[] { X });
				body[1] = new Literal(E.toString(), new Term[] { X, Y });
			}

			ProgramClause clause = new ProgramClause(head, body);
			clauses.add(clause);
		}

		@Override
		public void visit(OWLSubObjectPropertyOfAxiom axiom) {
			final OWLObjectPropertyExpression subProperty = axiom.getSubProperty();
			final OWLObjectPropertyExpression superProperty = axiom.getSuperProperty();
			Literal[] head = new Literal[1];
			head[0] = new Literal(superProperty.toString(), new Term[] { X, Y });
			Literal[] body = new Literal[1];
			body[0] = new Literal(subProperty.toString(), new Term[] { X, Y });
			ProgramClause clause = new ProgramClause(head, body);
			clauses.add(clause);
		}
	}

	class ClosureBuilder extends OWLAxiomVisitorAdapter implements OWLClassExpressionVisitor, OWLPropertyExpressionVisitor, OWLIndividualVisitor {
		@Override
		public void visit(OWLClassAssertionAxiom axiom) {
			final OWLClassExpression cls = axiom.getClassExpression();
			final OWLIndividual individual = axiom.getIndividual();
			cls.accept(this);
			individual.accept(this);
		}

		@Override
		public void visit(OWLObjectPropertyAssertionAxiom axiom) {
			final OWLObjectPropertyExpression property = axiom.getProperty();
			final OWLIndividual subject = axiom.getSubject();
			final OWLIndividual object = axiom.getObject();
			property.accept(this);
			subject.accept(this);
			object.accept(this);
		}

		@Override
		public void visit(OWLSubClassOfAxiom axiom) {
			final OWLClassExpression subClass = axiom.getSubClass();
			final OWLClassExpression superClass = axiom.getSuperClass();

			if (!(superClass instanceof OWLObjectAllValuesFrom)) {
				superClass.accept(this);
				subClass.accept(this);
			} else {
				OWLObjectAllValuesFrom E_only_A = (OWLObjectAllValuesFrom) superClass;
				final OWLClassExpression A = E_only_A.getFiller();
				final OWLObjectPropertyExpression E = E_only_A.getProperty();
				A.accept(this);
				E.accept(this);
				subClass.accept(this);
			}
		}

		@Override
		public void visit(OWLSubObjectPropertyOfAxiom axiom) {
			final OWLObjectPropertyExpression subProperty = axiom.getSubProperty();
			final OWLObjectPropertyExpression superProperty = axiom.getSuperProperty();
			subProperty.accept(this);
			superProperty.accept(this);
		}

		@Override
		public void visit(OWLClass ce) {
			classExpressionsClosure.add(ce);
		}

		@Override
		public void visit(OWLObjectIntersectionOf ce) {
			classExpressionsClosure.add(ce);
			for (OWLClassExpression op : ce.getOperands()) {
				op.accept(this);
			}
		}

		@Override
		public void visit(OWLObjectUnionOf ce) {
			classExpressionsClosure.add(ce);
			for (OWLClassExpression op : ce.getOperands()) {
				op.accept(this);
			}
		}

		@Override
		public void visit(OWLObjectComplementOf ce) {
			classExpressionsClosure.add(ce);
			ce.getOperand().accept(this);

		}

		@Override
		public void visit(OWLObjectSomeValuesFrom ce) {
			classExpressionsClosure.add(ce);
			ce.getProperty().accept(this);
			ce.getFiller().accept(this);
		}

		@Override
		public void visit(OWLObjectAllValuesFrom ce) {
			// TODO: Fix me if the expression is not in normal form

		}

		@Override
		public void visit(OWLObjectHasValue ce) {
			classExpressionsClosure.add(ce);
			ce.getProperty().accept(this);
			ce.getValue().accept(this);
		}

		@Override
		public void visit(OWLObjectMinCardinality ce) {
			classExpressionsClosure.add(ce);
			ce.getProperty().accept(this);
			ce.getFiller().accept(this);
		}

		@Override
		public void visit(OWLObjectExactCardinality ce) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void visit(OWLObjectMaxCardinality ce) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void visit(OWLObjectHasSelf ce) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void visit(OWLObjectOneOf ce) {
			// TODO:
			for (OWLIndividual i : ce.getIndividuals()) {
				i.accept(this);
			}
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
			objectPropertyExpressionsClosure.add(property);
		}

		@Override
		public void visit(OWLObjectInverseOf property) {
			objectPropertyExpressionsClosure.add(property);
			property.getInverse().accept(this);
		}

		@Override
		public void visit(OWLDataProperty property) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void visit(LDLObjectPropertyIntersectionOf property) {
			objectPropertyExpressionsClosure.add(property);
			for (OWLObjectPropertyExpression operand : property.getOperands()) {
				operand.accept(this);
			}
		}

		@Override
		public void visit(LDLObjectPropertyUnionOf property) {
			objectPropertyExpressionsClosure.add(property);
			for (OWLObjectPropertyExpression operand : property.getOperands()) {
				operand.accept(this);
			}
		}

		@Override
		public void visit(LDLObjectPropertyTransitiveClosureOf property) {
			objectPropertyExpressionsClosure.add(property);
			property.getOperand().accept(this);

		}

		@Override
		public void visit(LDLObjectPropertyChainOf property) {
			objectPropertyExpressionsClosure.add(property);
			for (OWLObjectPropertyExpression operand : property.getOperands()) {
				operand.accept(this);
			}
		}

		@Override
		public void visit(OWLNamedIndividual individual) {
			individualsClosure.add(individual);
			// OWLObjectOneOf oneOf = dataFactory.getOWLObjectOneOf(individual);

		}

		@Override
		public void visit(OWLAnonymousIndividual individual) {
			throw new UnsupportedOperationException();
		}
	}

	class ClosureProcesser implements OWLClassExpressionVisitor, OWLPropertyExpressionVisitor, OWLIndividualVisitor {

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
}
