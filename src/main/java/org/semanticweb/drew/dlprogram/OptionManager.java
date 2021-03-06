package org.semanticweb.drew.dlprogram;

public class OptionManager {
	private static final OptionManager instance = new OptionManager();

	//private boolean compatible = false;
	private boolean compatible = true;

	private OptionManager() {

	}

	public static final OptionManager getInstance() {
		return instance;
	}

	/**
	 * Returns whether the constant is in compatible mode which does not provide quotation marks around string constants.
	 * 
	 * @return the compatible flag
	 */
	public boolean getCompatibleMode() {
		return compatible;
	}

	/**
	 * Set the compatible mode.
	 * 
	 * @param value whether in compatible mode
	 */
	public void setCompatibleMode(boolean value) {
		compatible = value;
	}
}
