package at.ac.tuwien.kr.ldlp.reasoner;

import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.kr.dlprogram.Literal;
import at.ac.tuwien.kr.dlprogram.NormalPredicate;
import at.ac.tuwien.kr.dlprogram.Predicate;

public class LDLPQueryResultDecompiler {
	List<Literal> decompile(List<Literal> lits) {
		List<Literal> newLits = new ArrayList<Literal>();
		for(Literal lit:lits){
			Literal newLit = decompile(lit);
			newLits.add(newLit);
		}
		return newLits;

	}

	Literal decompile(Literal lit) {
		Predicate predicate = lit.getPredicate();
		Predicate newPredicate = decompile(predicate);
		return null;
	}

	Predicate decompile(Predicate predicate) {
		String name = ((NormalPredicate)predicate).getName();
		String originalName;
		
		int index = Integer.parseInt(name.substring(1));
		
		
		return null;
	}
}
