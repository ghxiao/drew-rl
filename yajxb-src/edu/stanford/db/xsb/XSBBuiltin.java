package edu.stanford.db.xsb;

import edu.stanford.db.lp.Term;

/**
 * Classes that want to act as builtins have to implement this 
 * interface.
 */

public interface XSBBuiltin {
	public int call_builtin(Term args);
}
