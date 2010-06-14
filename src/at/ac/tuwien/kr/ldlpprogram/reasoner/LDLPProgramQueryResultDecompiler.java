package at.ac.tuwien.kr.ldlpprogram.reasoner;

import at.ac.tuwien.kr.dlprogram.Predicate;
import at.ac.tuwien.kr.ldlp.reasoner.LDLPQueryResultDecompiler;

public class LDLPProgramQueryResultDecompiler extends LDLPQueryResultDecompiler {

	@Override
	public Predicate decompilePredicate(Predicate predicate) {
		return predicate;
	}

}
