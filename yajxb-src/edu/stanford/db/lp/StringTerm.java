
/**
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package edu.stanford.db.lp;

public class StringTerm extends Term{
  String content;

  public StringTerm(String content) {
    this.content=content;
  }

  public String getContent(){
    return content;
  }

  public String toString(){
  	return "StringTerm:" + "\""+content+"\"";
  }
}
