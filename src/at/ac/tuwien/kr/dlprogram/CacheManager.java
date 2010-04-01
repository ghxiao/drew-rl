package at.ac.tuwien.kr.dlprogram;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class CacheManager {
	private static final CacheManager instance = new CacheManager();

	private Map<String, NormalPredicate> predicates = new HashMap<String, NormalPredicate>();

	private Map<String, Variable> vars = new HashMap<String, Variable>();

	private Map<String, Constant> integers = new HashMap<String, Constant>();

	private Map<String, Constant> varchars = new HashMap<String, Constant>();

	private CacheManager() {
		reset();
	}

	public static final CacheManager getInstance() {
		return instance;
	}

	public NormalPredicate getPredicate(String name, int arity) {
		String key = name + "/" + arity;

		NormalPredicate result = predicates.get(key);
		if (result != null) {
			return result;
		} else {
			NormalPredicate predicate = new NormalPredicate(name, arity);
			predicates.put(key, predicate);
			return predicate;
		}
	}

	public Constant getConstant(String name, int type) {
		Constant result;

		switch (type) {
		case Types.VARCHAR:
			result = varchars.get(name);
			if (result != null) {
				return (Constant) result;
			} else {
				Constant constant = new Constant(name, type);
				varchars.put(name, constant);
				return constant;
			}
		case Types.INTEGER:
			result = integers.get(name);
			if (result != null) {
				return (Constant) result;
			} else {
				Constant constant = new Constant(name, type);
				integers.put(name, constant);
				return constant;
			}
		default:
			throw new IllegalStateException();
		}
	}

	public Variable getVariable(String name) {
		Term result = vars.get(name);
		if (result != null) {
			return (Variable) result;
		} else {
			Variable variable = new Variable(name);
			vars.put(name, variable);
			return variable;
		}
	}

	/**
	 * Reset the predicate manager by cleaning all existing ones and append builtin ones.
	 */
	public void reset() {
		// clear predicate map
		predicates.clear();

		for (NormalPredicate predicate : NormalPredicate.logics) {
			predicates.put(predicate.toString(), predicate);
		}

		for (NormalPredicate predicate : NormalPredicate.builtins) {
			predicates.put(predicate.toString(), predicate);
		}

		// clear term map
		vars.clear();
	}
}