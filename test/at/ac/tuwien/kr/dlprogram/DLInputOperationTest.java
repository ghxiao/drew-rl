/*
 * @(#)DLInputAddOperationTest.java 2010-4-1 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.dlprogram;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLogicalEntity;
import org.semanticweb.owlapi.model.OWLNamedObject;
import org.semanticweb.owlapi.model.OWLObject;

/**
 * TODO describe this class please.
 */
public class DLInputOperationTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link at.ac.tuwien.kr.dlprogram.DLInputAddOperation#toString()}.
	 */
	@Test
	public void testToString() {
		OWLLogicalEntity S1 = OWLManager.getOWLDataFactory().getOWLClass(IRI.create("S1"));
		NormalPredicate p1 = new NormalPredicate("p1", 1);
		DLInputOperation S1_uplus_p1 = new DLInputOperation(S1, p1);
		assertEquals("S1 += p1", S1_uplus_p1.toString());
	}

}
