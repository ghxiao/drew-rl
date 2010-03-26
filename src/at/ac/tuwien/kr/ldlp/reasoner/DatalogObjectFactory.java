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
import java.util.List;
import java.util.Map;

import org.semanticweb.owlapi.model.OWLObject;

/**
 * TODO describe this class please.
 */
public class DatalogObjectFactory {

	private static DatalogObjectFactory instance = new DatalogObjectFactory();

	public static DatalogObjectFactory getInstance() {
		return instance;
	}

	private DatalogObjectFactory() {

	}

	private Map<OWLObject, Integer> owlObject2PredicateMap = new HashMap<OWLObject, Integer>();

	ArrayList<OWLObject> index2Predicate_List = new ArrayList<OWLObject>();

	int currentPredicateMax = -1;

	public String getPredicate(OWLObject owlObject) {

		int index = 0;
		if (owlObject2PredicateMap.containsKey(owlObject)) {
			index = owlObject2PredicateMap.get(owlObject);

		} else {
			encodeOWLObject2Predicate(owlObject);
			index = currentPredicateMax;
		}
		return "p" + (index + 1);
	}

	private void encodeOWLObject2Predicate(OWLObject symbol) {
		if (!owlObject2PredicateMap.containsKey(symbol)) {
			currentPredicateMax++;
			owlObject2PredicateMap.put(symbol, currentPredicateMax);
			index2Predicate_List.add(symbol);
		}
	}

	private Map<OWLObject, Integer> owlObject2ConstMap = new HashMap<OWLObject, Integer>();

	ArrayList<OWLObject> index2Const_List = new ArrayList<OWLObject>();

	int currentConstMax = -1;

	public String getConst(OWLObject owlObject) {

		int index = 0;
		if (owlObject2ConstMap.containsKey(owlObject)) {
			index = owlObject2ConstMap.get(owlObject);

		} else {
			encodeOWLObject2Const(owlObject);
			index = currentConstMax;
		}
		return "o" + (index + 1);
	}

	private void encodeOWLObject2Const(OWLObject symbol) {
		if (!owlObject2ConstMap.containsKey(symbol)) {
			currentConstMax++;
			owlObject2ConstMap.put(symbol, currentConstMax);
			index2Const_List.add(symbol);
		}
	}

}
