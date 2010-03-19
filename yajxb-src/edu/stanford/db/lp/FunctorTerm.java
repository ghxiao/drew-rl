
/**
 * Title:        FunctorTerm<p>
 * Description:  Class Representing n-ary Terms<p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author       Stefan Decker
 * @version 1.0
 */
package edu.stanford.db.lp;

public class FunctorTerm extends Term{
  Term[] args;
  String functor;

  public FunctorTerm(String functor, Term[] args){
    this.args=args;
    this.functor=functor;
  }

  public Term[] getArgs(){
    return args;
  }

  public int getArity(){
    return args.length;
  }

  public String getFunctor(){
    return functor;
  }

  public String toString(){
    String out = functor;
    if((args!=null)&&(args.length>0)){
      out = out.concat("(");
      for(int i=0; i< args.length; i++){
        out=out.concat(args[i].toString());
        if(i< args.length-1) out = out.concat(",");
      }
      out = out.concat(")");
    }
    return out;
  }
}
