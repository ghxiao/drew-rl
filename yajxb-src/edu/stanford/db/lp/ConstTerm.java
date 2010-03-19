
/**
 * Title:        ConstTerm<p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author      Stefan Decker
 * @version 1.0
 */
package edu.stanford.db.lp;

public class ConstTerm extends Term{
  String functor;

  public ConstTerm(String functor) {
    this.functor=functor;
  }

  public String getFunctor(){
    return functor;
  }

  public String toString(){
    return functor;
  }
}