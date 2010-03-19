
/**
 * Title:        ProgramClause.java<p>
 * Description:  Represents Program Clauses and Queries<p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package edu.stanford.db.lp;

public class ProgramClause extends FormulaObject{
  Literal [] head;
  Literal [] body;

  public ProgramClause(Literal [] head, Literal [] body) {
    this.head = head;
    this.body = body;
  }

  public ProgramClause(Literal [] body){
    this.head = null;
    this.body = body;
  }

  public boolean isQuery(){
    return ((head == null) || (head.length==0));
  }

  public Literal[] getBody(){
    return body;
  }

  public Literal[] getHead(){
    return head;
  }

}