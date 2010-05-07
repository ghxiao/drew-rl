package at.ac.tuwien.kr.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolEncoder<K> {
	Map<K, Integer> symbolToInt_Map = new HashMap<K, Integer>();

	List<K> intToSymbol_List = new ArrayList<K>();

	public K getSymbolByValue(int value) {
		value--;
		if (1 <= value && value <= currentMax)
			return intToSymbol_List.get(value);
		else {
			throw new IllegalArgumentException();
		}
	}

	public int getValueBySymbol(K symbol) {

		if (symbolToInt_Map.containsKey(symbol)) {
			return symbolToInt_Map.get(symbol);
		} else {
			encodeSybmol(symbol);
			return currentMax;
		}
	}

	int currentMax = 0;

	private void encodeSybmol(K symbol) {
		if (!symbolToInt_Map.containsKey(symbol)) {
			currentMax++;
			symbolToInt_Map.put(symbol, currentMax);
			intToSymbol_List.add(symbol);
		}
	}

}
