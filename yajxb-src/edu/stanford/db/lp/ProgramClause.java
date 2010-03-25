/**
 * Title:        ProgramClause.java<p>
 * Description:  Represents Program Clauses and Queries<p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package edu.stanford.db.lp;

public class ProgramClause extends FormulaObject {

	Literal[] head;
	Literal[] body;

	public ProgramClause(Literal[] head, Literal[] body) {
		this.head = head;
		this.body = body;
	}

	public ProgramClause(Literal[] body) {
		this.head = null;
		this.body = body;
	}

	public boolean isQuery() {
		return ((head == null) || (head.length == 0));
	}

	public Literal[] getBody() {
		return body;
	}

	public Literal[] getHead() {
		return head;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		boolean start = true;
		for (Literal literal : head) {
			if (!start) {
				sb.append(",");
			}
			sb.append(literal);
			start = false;
		}

		if (body.length != 0) {
			sb.append(":-");
		}

		start = true;
		for (Literal literal : body) {
			if (!start) {
				sb.append(",");
			}
			sb.append(literal);
			start = false;
		}
		sb.append(".");

		return sb.toString();
	}
	
	
	public String toStringWithoutDot() {
		StringBuilder sb = new StringBuilder();
		boolean start = true;
		for (Literal literal : head) {
			if (!start) {
				sb.append(",");
			}
			sb.append(literal);
			start = false;
		}

		if (body.length != 0) {
			sb.append(":-");
		}

		start = true;
		for (Literal literal : body) {
			if (!start) {
				sb.append(",");
			}
			sb.append(literal);
			start = false;
		}
		

		return sb.toString();
	}
}