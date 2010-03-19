
/**
 * Title:        NonEmptyListTerm <p>
 * Description:  <p>
 * Copyright:    Copyright (c) 2000 <p>
 * Company:      <p>
 * @author       Stefan Decker
 * @version 1.0
 */
package edu.stanford.db.lp;

public class NonEmptyListTerm extends ListTerm{
  Term head;
  Term rest;

  public NonEmptyListTerm(){
    }

  public NonEmptyListTerm(Term head, Term rest) {
    this.head = head;
    this.rest = rest;
  }

  public Term getHead(){
    return head;
  }

  public Term getRest(){
    return rest;
  }

  public void setRest(Term rest){
    this.rest = rest;
  }

  public void setHead(Term head){
    this.head = head;
  }

  public String toString(){
      String result = "[" + head.toString();
      if(rest instanceof NonEmptyListTerm){
	  result += ((NonEmptyListTerm) rest).toString1();
      }
      else if(rest instanceof NilTerm) {
	  result += "]";
      }
      else result += "|" + rest.toString() + "]";
      return result;
  }

  public String toString1(){
      String result = "," + head.toString();
      if(rest instanceof NonEmptyListTerm){
	  result += ((NonEmptyListTerm) rest).toString1();
      }
      else if(rest instanceof NilTerm) {
	  result += "]";
      }
      else result += "|" + rest.toString() + "]";
      return result;
      }
}
