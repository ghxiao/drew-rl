package at.ac.tuwien.kr.dlprogram;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLogicalEntity;

public class DLAtomPredicateTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testToString(){
		DLInputSignature signature = new DLInputSignature();

		final OWLDataFactory owlDataFactory = OWLManager.getOWLDataFactory();
		OWLLogicalEntity S1 = owlDataFactory.getOWLClass(IRI.create("S1"));
		NormalPredicate p1 = new NormalPredicate("p1", 1);
		DLInputOperation S1_uplus_p1 = new DLInputOperation(S1, p1);

		OWLLogicalEntity S2 = owlDataFactory.getOWLClass(IRI.create("S2"));
		NormalPredicate p2 = new NormalPredicate("p2", 1);
		DLInputOperation S2_uplus_p2 = new DLInputOperation(S2, p2);

		List<DLInputOperation> operations = new ArrayList<DLInputOperation>();
		operations.add(S1_uplus_p1);
		operations.add(S2_uplus_p2);
		signature.setOperations(operations);

		OWLClass Q = owlDataFactory.getOWLClass(IRI.create("Q"));
		
		DLAtomPredicate predicate = new DLAtomPredicate(signature, Q);
		
		assertEquals("DL[S1 += p1,S2 += p2;Q]", predicate.toString());
		
	}
}
