/*
 * @(#)DLInputOpreations.java 2010-4-1 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.dlprogram;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO describe this class please.
 */
public class DLInputSignature {

	List<DLInputOperation> operations = new ArrayList<DLInputOperation>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;

		for (DLInputOperation op : operations) {
			if (!first)
				sb.append(",");
			sb.append(op);
			first = false;
		}

		return sb.toString();
	}

	/**
	 * @return the operations
	 */
	public List<DLInputOperation> getOperations() {
		return operations;
	}

	/**
	 * @param operations
	 *            the operations to set
	 */
	public void setOperations(List<DLInputOperation> operations) {
		this.operations = operations;
	}

}
