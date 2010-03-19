
/**
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package edu.stanford.db.lp;

public class VariableTerm extends Term{
  String variable;

  public VariableTerm(String variable) {
    this.variable = variable;
  }

  public String getVariable(){
    return variable;
  }

  public String toString(){
    return variable;
  }
}