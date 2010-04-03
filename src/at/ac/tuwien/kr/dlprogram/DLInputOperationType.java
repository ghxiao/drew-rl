/*
 * @(#)DLInputOperationType.java 2010-4-2 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.dlprogram;

/**
 * TODO describe this class please.
 */
public enum DLInputOperationType {
	U_PLUS("+="), U_MINUS("-=");
	
	String symbol;
	
	DLInputOperationType(String symbol){
		this.symbol = symbol;
	}
	
	@Override
	public String toString(){
		return symbol;
	}
}
