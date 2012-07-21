//package edu.stanford.db.lp;
//
//import static org.junit.Assert.*;
//
//import org.junit.Test;
//
//public class ProgramClauseTest {
//
//	VariableTerm X = new VariableTerm("X");
//	VariableTerm Y = new VariableTerm("Y");
//	VariableTerm Z = new VariableTerm("Z");
//		
//	ConstTerm a = new ConstTerm("a");
//	ConstTerm b = new ConstTerm("b");
//
//	@Test
//	public void testToString_Fact() {
//		
//		Literal[] head = null;
//		Literal[] body = null;
//		head = new Literal[1];
//		head[0] = new Literal("p", new Term[] { a });
//		body = new Literal[0];
//		ProgramClause clause = new ProgramClause(head, body);
//		assertEquals("p(a).", clause.toString());
//	}
//
//	@Test
//	public void testToString_Constraint() {		
//		Literal[] head = null;
//		Literal[] body = null;
//		head = new Literal[0];
//		body = new Literal[1];
//		body[0] = new Literal("p", new Term[] { a,b });
//		ProgramClause clause = new ProgramClause(head, body);
//		assertEquals(":-p(a,b).", clause.toString());
//	}
//	
//	@Test
//	public void testToString_Rule() {		
//		Literal[] head = null;
//		Literal[] body = null;
//		head = new Literal[1];
//		head[0] = new Literal("p", new Term[] { a });		
//		body = new Literal[2];
//		body[0] = new Literal("p", new Term[] { a,b });
//		body[1] = new Literal("q", new Term[] { Y });
//		ProgramClause clause = new ProgramClause(head, body);
//		assertEquals("p(a):-p(a,b),q(Y).", clause.toString());
//	}
//}
