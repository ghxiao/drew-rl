/*
 * @(#)LDLPProfileChecker.java 2010-3-17 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.ldlp.profile;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.profiles.OWLProfileViolation;
import org.semanticweb.owlapi.profiles.UseOfIllegalAxiom;
import org.semanticweb.owlapi.profiles.UseOfIllegalDataRange;
import org.semanticweb.owlapi.profiles.UseOfNonEquivalentClassExpression;
import org.semanticweb.owlapi.profiles.UseOfNonSubClassExpression;
import org.semanticweb.owlapi.profiles.UseOfNonSuperClassExpression;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;

/**
 * LDLPProfileChecker
 */

class LDLPProfileChecker extends OWLOntologyWalkerVisitor {

		private Set<IRI> allowedDatatypes = new HashSet<IRI>();
	
    private Set<OWLProfileViolation> profileViolations = new HashSet<OWLProfileViolation>();


    private LDLPSubClassExpressionChecker subClassExpressionChecker = new LDLPSubClassExpressionChecker();

    private boolean isLDLPSubClassExpression(OWLClassExpression ce) {
        return ce.accept(subClassExpressionChecker);
    }


    private LDLPSuperClassExpressionChecker superClassExpressionChecker = new LDLPSuperClassExpressionChecker();

    public boolean isLDLPSuperClassExpression(OWLClassExpression ce) {
        return ce.accept(superClassExpressionChecker);
    }

    private LDLPEquivalentClassExpressionChecker equivalentClassExpressionChecker = new LDLPEquivalentClassExpressionChecker();

    public boolean isLDLPEquivalentClassExpression(OWLClassExpression ce) {
        return ce.accept(equivalentClassExpressionChecker);
    }

    private LDLPSubObjectPropertyExpressionChecker subObjectPropertyExpressionChecker = new LDLPSubObjectPropertyExpressionChecker();

    boolean isLDLPSubObjectPropertyExpression(OWLObjectPropertyExpression ope) {
        return ope.accept(subObjectPropertyExpressionChecker);
    }


    private LDLPSuperObjectPropertyExpressionChecker superObjectPropertyExpressionChecker = new LDLPSuperObjectPropertyExpressionChecker();

    boolean isLDLPSuperObjectPropertyExpression(OWLObjectPropertyExpression ope) {
        return ope.accept(superObjectPropertyExpressionChecker);
    }
    
    LDLPProfileChecker(OWLOntologyWalker walker) {
        super(walker);
    }

    public Set<OWLProfileViolation> getProfileViolations() {
        return new HashSet<OWLProfileViolation>(profileViolations);
    }

    public Object visit(OWLClassAssertionAxiom axiom) {
        if(!isLDLPSuperClassExpression(axiom.getClassExpression())) {
            profileViolations.add(new UseOfNonSuperClassExpression(getCurrentOntology(), axiom, axiom.getClassExpression()));
        }
        return null;
    }

    public Object visit(OWLDataPropertyDomainAxiom axiom) {
    	profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        return null;
    }

    public Object visit(OWLDisjointClassesAxiom axiom) {
    	profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        return null;
    }

    public Object visit(OWLDisjointDataPropertiesAxiom axiom) {
        profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        return null;
    }

    public Object visit(OWLDisjointUnionAxiom axiom) {
        profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        return null;
    }

    public Object visit(OWLEquivalentClassesAxiom axiom) {
        for(OWLClassExpression ce : axiom.getClassExpressions()) {
            if(!isLDLPEquivalentClassExpression(ce)) {
                profileViolations.add(new UseOfNonEquivalentClassExpression(getCurrentOntology(), axiom, ce));
            }
        }
        return null;
    }

    public Object visit(OWLEquivalentDataPropertiesAxiom axiom) {
        profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        return null;
    }

    public Object visit(OWLFunctionalDataPropertyAxiom axiom) {
    	profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        return null;
    }

    public Object visit(OWLHasKeyAxiom axiom) {
    	profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        return null;
    }

    public Object visit(OWLObjectPropertyDomainAxiom axiom) {
    	profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        return null;
    }

    public Object visit(OWLObjectPropertyRangeAxiom axiom) {
    	profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        return null;
    }

    public Object visit(OWLSubClassOfAxiom axiom) {
        if(!isLDLPSubClassExpression(axiom.getSubClass())) {
            profileViolations.add(new UseOfNonSubClassExpression(getCurrentOntology(), axiom, axiom.getSubClass()));
        }
        if(!isLDLPSuperClassExpression(axiom.getSuperClass())) {
            profileViolations.add(new UseOfNonSuperClassExpression(getCurrentOntology(), axiom, axiom.getSuperClass()));
        }
        return null;
    }
    
    @Override
	public Object visit(OWLSubObjectPropertyOfAxiom axiom) {
    	if(!isLDLPSubObjectPropertyExpression(axiom.getSubProperty())) {
            profileViolations.add(new UseOfNonLDLPSubPropertyExpression(getCurrentOntology(), axiom, axiom.getSubProperty()));
        }
        if(!isLDLPSuperObjectPropertyExpression(axiom.getSuperProperty())) {
            profileViolations.add(new UseOfNonLDLPSuperObjectPropertyExpression(getCurrentOntology(), axiom, axiom.getSuperProperty()));
        }
        return null;
	}

    public Object visit(OWLSubDataPropertyOfAxiom axiom) {
        profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        return null;
    }

    public Object visit(SWRLRule rule) {
        profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), rule));
        return super.visit(rule);
    }

    public Object visit(OWLDataComplementOf node) {
        return super.visit(node);
    }

    public Object visit(OWLDataIntersectionOf node) {
        profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
        return null;
    }

    public Object visit(OWLDataOneOf node) {
        profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
        return null;
    }

    public Object visit(OWLDatatype node) {
        if(!allowedDatatypes.contains(node.getIRI())) {
            profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
        }
        return null;
    }

    public Object visit(OWLDatatypeRestriction node) {
        profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
        return null;
    }

    public Object visit(OWLDataUnionOf node) {
        profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
        return null;
    }

    public Object visit(OWLDatatypeDefinitionAxiom axiom) {
        profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), getCurrentAxiom()));
        return null;
    }
}
