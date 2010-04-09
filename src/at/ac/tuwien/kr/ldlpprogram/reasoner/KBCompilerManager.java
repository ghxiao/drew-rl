package at.ac.tuwien.kr.ldlpprogram.reasoner;

import at.ac.tuwien.kr.dlprogram.DLInputSignature;
import at.ac.tuwien.kr.utils.SymbolEncoder;

public class KBCompilerManager {

	final static KBCompilerManager instance = new KBCompilerManager();

	SymbolEncoder<DLInputSignature> signatureEncoder = new SymbolEncoder<DLInputSignature>();

	public static KBCompilerManager getInstance() {
		return instance;
	}

	private KBCompilerManager() {

	}

	public String getSubscript(DLInputSignature signature) {
		return String.valueOf(signatureEncoder.getValueBySymbol(signature));
	}
}
