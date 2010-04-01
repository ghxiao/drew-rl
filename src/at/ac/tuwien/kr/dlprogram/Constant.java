package at.ac.tuwien.kr.dlprogram;

import java.sql.Types;

/**
 * Constant implementation.
 * 
 * @author Samuel
 */
public class Constant implements Term {
	int type = Types.VARCHAR;

	String name;

	String string;

	int hash;

	/**
	 * Constructor that is visible inside the package. Customer should use {@link CacheManager} to create process unique
	 * constants.
	 * 
	 * @param name
	 * @param type
	 */
	Constant(String name, int type) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		this.name = name;
		this.type = type;

		update();
	}

	/**
	 * Get the name of the term.
	 * 
	 * @return name of the term
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get constant type.
	 * 
	 * @return constant type
	 */
	public int getType() {
		return type;
	}

	/**
	 * Update string form and its hash code for caching.
	 */
	private void update() {
		switch (type) {
		case Types.VARCHAR:
			if (OptionManager.getInstance().getCompatibleMode()) {
				string = name;
			} else {
				StringBuilder result = new StringBuilder();
				result.append("\"").append(name).append("\"");
				string = result.toString();
			}
			break;
		case Types.INTEGER:
			string = name;
			break;
		default:
			throw new IllegalStateException();
		}

		if (string != null) {
			hash = string.hashCode();
		}
	}

	@Override
	public String toString() {
		return string;
	}

	@Override
	public Constant clone() {
		return this;
	}

	@Override
	public boolean equals(Object that) {
		return this == that;
	}

	@Override
	public int hashCode() {
		return hash;
	}
}