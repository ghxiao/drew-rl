/*
 * @(#)AxiomCompiler.java 2010-3-24 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.ldlp.reasoner;

import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.util.OWLAxiomVisitorAdapter;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxObjectRenderer;

import edu.stanford.db.lp.ConstTerm;
import edu.stanford.db.lp.Literal;
import edu.stanford.db.lp.ProgramClause;
import edu.stanford.db.lp.Term;
import edu.stanford.db.lp.VariableTerm;

class AxiomCompiler extends OWLAxiomVisitorAdapter {

	DatalogObjectFactory datalogObjectFactory = DatalogObjectFactory.getInstance();

	VariableTerm X = new VariableTerm("X");
	VariableTerm Y = new VariableTerm("Y");
	VariableTerm Z = new VariableTerm("Z");

	private List<ProgramClause> clauses;

	public List<ProgramClause> getClauses() {
		return clauses;
	}

	public AxiomCompiler(List<ProgramClause> clauses) {
		this.clauses = clauses;
	}

	@Override
	public void visit(OWLObjectPropertyAssertionAxiom axiom) {
		OWLObjectPropertyExpression property = axiom.getProperty();
		final OWLIndividual subject = axiom.getSubject();
		final OWLIndividual object = axiom.getObject();
		Literal[] head = null;
		Literal[] body = null;
		head = new Literal[1];

		String predicate = datalogObjectFactory.getPredicate(property);
		String a = datalogObjectFactory.getConst(subject);
		String b = datalogObjectFactory.getConst(object);

		head[0] = new Literal(predicate, new Term[] { new ConstTerm(a), new ConstTerm(b) });
		body = new Literal[0];
		ProgramClause clause = new ProgramClause(head, body);
		clauses.add(clause);
	}

	@Override
	public void visit(OWLClassAssertionAxiom axiom) {
		OWLClassExpression cls = axiom.getClassExpression();
		OWLIndividual individual = axiom.getIndividual();
		Literal[] head = new Literal[1];
		Literal[] body = null;

		String predicate = datalogObjectFactory.getPredicate(cls);
		String a = datalogObjectFactory.getConst(individual);
		head[0] = new Literal(predicate, new Term[] { new ConstTerm(a) });
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
			head[0] = new Literal(datalogObjectFactory.getPredicate(superClass), new Term[] { X });
			body = new Literal[1];
			body[0] = new Literal(datalogObjectFactory.getPredicate(subClass), new Term[] { X });
		} else {
			OWLObjectAllValuesFrom E_only_A = (OWLObjectAllValuesFrom) superClass;
			final OWLClassExpression A = E_only_A.getFiller();
			final OWLObjectPropertyExpression E = E_only_A.getProperty();
			head = new Literal[1];
			head[0] = new Literal(datalogObjectFactory.getPredicate(A), new Term[] { Y });
			body = new Literal[2];
			body[0] = new Literal(datalogObjectFactory.getPredicate(subClass), new Term[] { X });
			body[1] = new Literal(datalogObjectFactory.getPredicate(E), new Term[] { X, Y });
		}

		ProgramClause clause = new ProgramClause(head, body);
		clauses.add(clause);
	}

	@Override
	public void visit(OWLSubObjectPropertyOfAxiom axiom) {
		final OWLObjectPropertyExpression subProperty = axiom.getSubProperty();
		final OWLObjectPropertyExpression superProperty = axiom.getSuperProperty();
		Literal[] head = new Literal[1];
		head[0] = new Literal(datalogObjectFactory.getPredicate(superProperty), new Term[] { X, Y });
		Literal[] body = new Literal[1];
		body[0] = new Literal(datalogObjectFactory.getPredicate(subProperty), new Term[] { X, Y });
		ProgramClause clause = new ProgramClause(head, body);
		clauses.add(clause);
	}
}
