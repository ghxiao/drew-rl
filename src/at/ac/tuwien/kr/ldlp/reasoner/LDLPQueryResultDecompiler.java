package at.ac.tuwien.kr.ldlp.reasoner;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.kr.dlprogram.CacheManager;
import at.ac.tuwien.kr.dlprogram.Constant;
import at.ac.tuwien.kr.dlprogram.Literal;
import at.ac.tuwien.kr.dlprogram.NormalPredicate;
import at.ac.tuwien.kr.dlprogram.Predicate;
import at.ac.tuwien.kr.dlprogram.Term;
import at.ac.tuwien.kr.dlprogram.Variable;

public class LDLPQueryResultDecompiler {
	
	final static Logger logger = LoggerFactory
	.getLogger(LDLPQueryResultDecompiler.class);
	
	public List<Literal> decompileLiterals(List<Literal> lits) {
		List<Literal> newLits = new ArrayList<Literal>();
		for(Literal lit:lits){
			Literal newLit = decompileLiteral(lit);
			newLits.add(newLit);
		}
		return newLits;

	}

	public Literal decompileLiteral(Literal lit) {
		Predicate predicate = lit.getPredicate();
		Predicate decompiledPredicate = decompilePredicate(predicate);
		
		List<Term> terms = lit.getTerms();
		
		List<Term> decompiledTerms = decompileTerms(terms);
		
		return new Literal(decompiledPredicate, decompiledTerms);
	}

	public	List<Term> decompileTerms(List<Term> terms) {

		List<Term> decompiledTerms = new ArrayList<Term>();
		
		for(Term term: terms){
			Term decompiledTerm = decompileTerm(term);
			decompiledTerms.add(decompiledTerm);
		}
		
		return decompiledTerms;
	}

	public Term decompileTerm(Term term) {
		if(term instanceof Variable){
			throw new IllegalStateException("Query Results should not contain Variable");
		}else if(term instanceof Constant){
			Constant constant = (Constant)term;
			Constant decomiledConstant = decompileConstant(constant);
			return decomiledConstant;
		}
		
		throw new IllegalStateException();
	}

	public Constant decompileConstant(Constant constant) {
		String name = constant.getName();
		
		String decompiledConstantName = LDLPCompilerManager.getInstance().decompile(name);
		
		Constant decompiledConstant = CacheManager.getInstance().getConstant(decompiledConstantName);
		
		return decompiledConstant;
	}

	public Predicate decompilePredicate(Predicate predicate) {
		NormalPredicate normalPredicate = (NormalPredicate)predicate;
		String name = normalPredicate.getName();
		int arity = normalPredicate.getArity();
		
		String decompiledName = LDLPCompilerManager.getInstance().decompile(name);
		
		NormalPredicate decompiledPredicate = CacheManager.getInstance().getPredicate(decompiledName, arity);
		return decompiledPredicate;
	}
}
