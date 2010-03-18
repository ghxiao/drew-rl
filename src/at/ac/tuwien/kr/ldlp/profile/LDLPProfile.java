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
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileReport;
import org.semanticweb.owlapi.profiles.OWLProfileViolation;
import org.semanticweb.owlapi.profiles.UseOfIllegalAxiom;
import org.semanticweb.owlapi.profiles.UseOfIllegalDataRange;
import org.semanticweb.owlapi.profiles.UseOfNonEquivalentClassExpression;
import org.semanticweb.owlapi.profiles.UseOfNonSubClassExpression;
import org.semanticweb.owlapi.profiles.UseOfNonSuperClassExpression;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;



public class LDLPProfile implements OWLProfile {

    

    public LDLPProfile() {

    }

    /**
     * Gets the name of the profile.
     * @return A string that represents the name of the profile
     */
    public String getName() {
        return "LDLp";
    }

    /**
     * Checks an ontology and its import closure to see if it is within
     * this profile.
     * @param ontology The ontology to be checked.
     * @return An <code>OWLProfileReport</code> that describes whether or not the
     *         ontology is within this profile.
     */
    public OWLProfileReport checkOntology(OWLOntology ontology) {
        //OWL2DLProfile profile = new OWL2DLProfile();
        //OWLProfileReport report = profile.checkOntology(ontology);
        Set<OWLProfileViolation> violations = new HashSet<OWLProfileViolation>();
        //violations.addAll(report.getViolations());

        OWLOntologyWalker walker = new OWLOntologyWalker(ontology.getImportsClosure());
        LDLPProfileChecker visitor = new LDLPProfileChecker(walker);
        walker.walkStructure(visitor);
        violations.addAll(visitor.getProfileViolations());
        return new OWLProfileReport(this, violations);
    }



}
