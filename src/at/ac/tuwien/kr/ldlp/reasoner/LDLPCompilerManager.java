/*
 * @(#)PredicateManager.java 2010-3-25 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.ldlp.reasoner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.kr.utils.SymbolEncoder;

/**
 * TODO describe this class please.
 */
public class LDLPCompilerManager {

	private static LDLPCompilerManager instance = new LDLPCompilerManager();

	final static Logger logger = LoggerFactory
			.getLogger(LDLPCompilerManager.class);

	public static LDLPCompilerManager getInstance() {
		return instance;
	}

	public LDLPCompilerManager() {

	}

	SymbolEncoder<OWLObject> owlObject2Predicate = new SymbolEncoder<OWLObject>();

	SymbolEncoder<OWLObject> owlObject2Const = new SymbolEncoder<OWLObject>();

	
//	private Map<OWLObject, Integer> owlObject2PredicateMap = new HashMap<OWLObject, Integer>();

//	ArrayList<OWLObject> index2Predicate_List = new ArrayList<OWLObject>();

	int currentPredicateMax = -1;

	final String TOP = "top";

	public String getTop1() {
		return TOP + "1";
	}

	public String getTop2() {
		return TOP + "2";
	}

	public String getEqual() {
		return "=";
	}

	public String getNotEqual() {
		return "!=";
	}

	public String getPredicate(OWLObject owlObject) {

		String predicate;

		if (owlObject instanceof OWLClass
				&& ((OWLEntity) owlObject).isTopEntity()) {
			predicate = getTop1();
		} else if (owlObject instanceof OWLObjectProperty
				&& ((OWLEntity) owlObject).isTopEntity()) {
			predicate = getTop2();
		}

		predicate = "p" + owlObject2Predicate.getValueBySymbol(owlObject);

		logger.debug("{}  ->  {}", owlObject, "p" + (currentPredicateMax + 1));

		return predicate;

		// int index = 0;
		// if (owlObject2PredicateMap.containsKey(owlObject)) {
		// index = owlObject2PredicateMap.get(owlObject);
		//
		// } else {
		// encodeOWLObject2Predicate(owlObject);
		// index = currentPredicateMax;
		// }
		// return "p" + (index + 1);
	}

//	private void encodeOWLObject2Predicate(OWLObject symbol) {
//		if (!owlObject2PredicateMap.containsKey(symbol)) {
//			currentPredicateMax++;
//			owlObject2PredicateMap.put(symbol, currentPredicateMax);
//			index2Predicate_List.add(symbol);
//			logger.debug("{}  ->  {}", symbol, "p" + (currentPredicateMax + 1));
//		}
//	}

//	private Map<OWLObject, Integer> owlObject2ConstMap = new HashMap<OWLObject, Integer>();
//
//	ArrayList<OWLObject> index2Const_List = new ArrayList<OWLObject>();
//
//	int currentConstMax = -1;
//
	public String getConst(OWLObject owlObject) {
		
		String constant = "o" + owlObject2Const.getValueBySymbol(owlObject);

		return constant;
		
//		int index = 0;
//		if (owlObject2ConstMap.containsKey(owlObject)) {
//			index = owlObject2ConstMap.get(owlObject);
//
//		} else {
//			encodeOWLObject2Const(owlObject);
//			index = currentConstMax;
//		}
//		return "o" + (index + 1);
	}
//
//	private void encodeOWLObject2Const(OWLObject symbol) {
//		if (!owlObject2ConstMap.containsKey(symbol)) {
//			currentConstMax++;
//			owlObject2ConstMap.put(symbol, currentConstMax);
//			index2Const_List.add(symbol);
//			logger.debug("{}  ->  {}", symbol, "o" + (currentConstMax + 1));
//		}
//	}
}
