package at.ac.tuwien.kr.datalog;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

import edu.stanford.db.lp.ConstTerm;
import edu.stanford.db.lp.Literal;
import edu.stanford.db.lp.ProgramClause;
import edu.stanford.db.lp.Term;

public class XSBDatalogReasonerTest {

	@Test
	public void testQuery() {
		DatalogReasoner reasoner = new XSBDatalogReasoner();
		
		//p(a).
		Term a = new ConstTerm("a");
		
		Literal[] head = new Literal[1];
		Literal pa = new Literal("p",new Term[]{a});
		head[0] = pa;
		Literal[] body = new Literal[0];
		ProgramClause pa_program = new ProgramClause(head,body);
		
		boolean q = reasoner.query(Collections.singletonList(pa_program), pa_program);
		assertTrue(q);
		}

	@Test
	public void testQuery1() {
		DatalogReasoner reasoner = new XSBDatalogReasoner();
		
		//p(a).
		Term a = new ConstTerm("a");
		Term b = new ConstTerm("b");
		
		Literal[] head = new Literal[1];
		Literal pa = new Literal("p",new Term[]{a});
		Literal pb = new Literal("p",new Term[]{b});
		head[0] = pa;
		Literal[] body = new Literal[0];
		ProgramClause pa_program = new ProgramClause(head,body);
		
		boolean q = reasoner.query(Collections.singletonList(pa_program), pa_program);
		assertFalse(q);
	}
}
