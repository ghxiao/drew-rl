
/**
 * Title:        IntegerTerm<p>
 * Description:  Represents Integer Terms<p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author        Stefan Decker
 * @version 1.0
 */
package edu.stanford.db.lp;

public class IntegerTerm extends Term{
  int value;
  public IntegerTerm(int value) {
    this.value = value;
  }

  public int getValue(){
    return value;
  }

  public String toString(){
    return Integer.toString(value);
  }
}
