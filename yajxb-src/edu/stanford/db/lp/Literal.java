/**
 * Title:        Literal.java<p>
 * Description:  Representation of Atoms and Literals for Program Clauses<p>
 * Copyright:    Copyright (c) 2000 <p>
 * @author       Stefan Decker
 * @version 1.0
 */

package edu.stanford.db.lp;

import java.util.Arrays;

import at.ac.tuwien.kr.ldlp.reasoner.LDLPCompilerManager;

public class Literal {
	String predicateSymbol;
	Term[] args;
	boolean polarity;

	public Literal(boolean polarity, String predicateSymbol, Term[] args) {
		this.predicateSymbol = predicateSymbol;
		this.args = args;
		this.polarity = polarity;
	}

	// public Literal(String predicateSymbol, Term[] args) {
	// this(true, predicateSymbol, args);
	// }

	public Literal(String predicateSymbol, Term... args) {
		this(true, predicateSymbol, args);
	}

	public boolean isAtom() {
		return polarity;
	}

	public boolean getPolarity() {
		return polarity;
	}

	public Term[] getArgs() {
		return args;
	}

	public String getPredicateSymbol() {
		return predicateSymbol;
	}

	public int getArity() {
		return args.length;
	}

	final public String toString() {
		String out = "";
		if (!polarity)
			out = out.concat("not ");

		LDLPCompilerManager factory = new LDLPCompilerManager();
		final String notEqual = factory.getNotEqual();

		if (!predicateSymbol.equals(notEqual)) {
			out = out.concat(predicateSymbol);
			if ((args != null) && (args.length > 0)) {
				out = out.concat("(");
				for (int i = 0; i < args.length; i++) {
					out = out.concat(args[i].toString());
					if (i < args.length - 1)
						out = out.concat(",");
				}
				out = out.concat(")");
			}
		} else {
			if (args.length != 2) {
				throw new IllegalArgumentException("args.length != 2");
			}
			out = out.concat(args[0].toString());
			out = out.concat(notEqual);
			out = out.concat(args[1].toString());
		}

		return out;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(args);
		result = prime * result + (polarity ? 1231 : 1237);
		result = prime * result + ((predicateSymbol == null) ? 0 : predicateSymbol.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		if (!(obj instanceof Literal)) {
			return false;
		}
		Literal other = (Literal) obj;
		if (!Arrays.equals(args, other.args)) {
			return false;
		}
		if (polarity != other.polarity) {
			return false;
		}
		if (predicateSymbol == null) {
			if (other.predicateSymbol != null) {
				return false;
			}
		} else if (!predicateSymbol.equals(other.predicateSymbol)) {
			return false;
		}
		return true;
	}
}
