/**
 * Title:        ProgramClause.java<p>
 * Description:  Represents Program Clauses and Queries<p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package edu.stanford.db.lp;

import java.util.Arrays;

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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(body);
		result = prime * result + Arrays.hashCode(head);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ProgramClause)) {
			return false;
		}
		ProgramClause other = (ProgramClause) obj;
		if (!Arrays.equals(body, other.body)) {
			return false;
		}
		if (!Arrays.equals(head, other.head)) {
			return false;
		}
		return true;
	}
}