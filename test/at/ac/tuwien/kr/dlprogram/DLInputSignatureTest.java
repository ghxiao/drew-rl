package at.ac.tuwien.kr.dlprogram;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLogicalEntity;

import com.sun.org.apache.xml.internal.utils.ListingErrorHandler;
import com.sun.xml.internal.ws.wsdl.writer.document.OpenAtts;

public class DLInputSignatureTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testToString() {
		DLInputSignature signature = new DLInputSignature();

		OWLLogicalEntity S1 = OWLManager.getOWLDataFactory().getOWLClass(IRI.create("S1"));
		NormalPredicate p1 = new NormalPredicate("p1", 1);
		DLInputOperation S1_uplus_p1 = new DLInputOperation(S1, p1);

		OWLLogicalEntity S2 = OWLManager.getOWLDataFactory().getOWLClass(IRI.create("S2"));
		NormalPredicate p2 = new NormalPredicate("p2", 1);
		DLInputOperation S2_uplus_p2 = new DLInputOperation(S2, p2);

		List<DLInputOperation> operations = new ArrayList<DLInputOperation>();
		operations.add(S1_uplus_p1);
		operations.add(S2_uplus_p2);
		signature.setOperations(operations);

		assertEquals("S1 += p1,S2 += p2", signature.toString());

	}

}
