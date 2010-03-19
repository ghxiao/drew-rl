
/**
 * Title:       Term.java <p>
 * Description: Superclass for all Terms in Logic Programs <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */


package edu.stanford.db.lp;
import edu.stanford.db.utils.Visitor;

public abstract class Term {

  public Term() {
  }

  void acceptVisitor(Visitor guest){
    guest.examineHost((Object)this);
  }
}