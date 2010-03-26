/**
 * Title:        ConstTerm<p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author      Stefan Decker
 * @version 1.0
 */
package edu.stanford.db.lp;

public class ConstTerm extends Term {
	String functor;

	public ConstTerm(String functor) {
		this.functor = functor;
	}

	public String getFunctor() {
		return functor;
	}

	public String toString() {
		return functor;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((functor == null) ? 0 : functor.hashCode());
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
		if (!(obj instanceof ConstTerm)) {
			return false;
		}
		ConstTerm other = (ConstTerm) obj;
		if (functor == null) {
			if (other.functor != null) {
				return false;
			}
		} else if (!functor.equals(other.functor)) {
			return false;
		}
		return true;
	}
}