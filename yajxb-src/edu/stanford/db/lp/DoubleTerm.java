
/**
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package edu.stanford.db.lp;

public class DoubleTerm extends Term{
  double value;

  public DoubleTerm(double value) {
    this.value = value;
  }

  public double getValue(){
    return value;
  }

  public String toString(){
    return Double.toString(value);
  }
}