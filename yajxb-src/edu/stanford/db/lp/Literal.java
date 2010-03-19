
/**
 * Title:        Literal.java<p>
 * Description:  Representation of Atoms and Literals for Program Clauses<p>
 * Copyright:    Copyright (c) 2000 <p>
 * @author       Stefan Decker
 * @version 1.0
 */

package edu.stanford.db.lp;

public class Literal {
  String predicateSymbol;
  Term[] args;
  boolean polarity;
  public Literal(boolean polarity, String predicateSymbol, Term[] args) {
    this.predicateSymbol = predicateSymbol;
    this.args = args;
    this.polarity = polarity;
  }


  public Literal(String predicateSymbol, Term[] args){
      this(true, predicateSymbol, args);
  }

  public boolean isAtom(){
      return polarity;     
  }

  public boolean getPolarity(){
    return polarity;
  }

  public Term[] getArgs(){
    return args;
  }

  public String getPredicateSymbol(){
    return predicateSymbol;
  }

  public int getArity(){
    return args.length;
  }

  final public String toString(){
    String out = "";
    if(!polarity) out = out.concat("not ");
    out = out.concat(predicateSymbol);
    if((args!=null) && (args.length>0)){
      out = out.concat("(");
      for(int i=0; i< args.length; i++) {
          out=out.concat(args[i].toString());
           if(i< args.length-1) out = out.concat(",");
      }
      out=out.concat(")");
    }
    return out;
  }
}

