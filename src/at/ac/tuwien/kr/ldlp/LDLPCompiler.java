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

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.util.OWLAxiomVisitorAdapter;

import at.ac.tuwien.kr.datalog.DatalogQuery;

import edu.stanford.db.lp.Literal;
import edu.stanford.db.lp.ProgramClause;
import edu.stanford.db.lp.Term;
import edu.stanford.db.lp.VariableTerm;
import edu.unika.aifb.kaon.datalog.program.Program;
import edu.unika.aifb.kaon.datalog.program.Rule;

/**
 * LDLPCompiler: compile an LDLp KB to a datalog program
 */
public class LDLPCompiler {

	VariableTerm X = new VariableTerm("X");
	VariableTerm Y = new VariableTerm("Y");
	VariableTerm Z = new VariableTerm("Z");
	
	AxiomCompiler axiomCompiler =new AxiomCompiler();

	List<ProgramClause> clauses;

	Set<OWLClassExpression> classExpressions;

	Set<OWLObjectPropertyExpression> objectPropertyExpressions;

	public List<ProgramClause> complileLDLPOntology(OWLOntology ontology) {
		reset();
		for (OWLAxiom axiom : ontology.getAxioms()) {
			axiom.accept(axiomCompiler);
		}

		compileClasses();

		complieProperties();

		return clauses;
	}

	private void reset() {
		clauses = new ArrayList<ProgramClause>();
		classExpressions = new HashSet<OWLClassExpression>();
		objectPropertyExpressions = new HashSet<OWLObjectPropertyExpression>();
	}

	private void complieProperties() {

	}

	private void compileClasses() {

	}

	public DatalogQuery compileClassAssertionAxiom(OWLClassAssertionAxiom classAssertionAxiom) {
		return null;
	}

	class AxiomCompiler extends OWLAxiomVisitorAdapter{
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
}
