/** Author: Stefan Decker
 *
 */

//Class XSBCore is the main interface class to XSB. Look there for a list of all methods
import edu.stanford.db.xsb.XSBCore;
import edu.stanford.db.lp.*;

public class XSBHello {
	public static void main(String[] args) {

		// create engine (there can only be one engine loaded!)
		XSBCore core = new XSBCore();

		// initialization parameter
		String[] xsbargs = { "/local/xsb/XSB", "-n", "--quietload" };

		int i;

		// initialize XSB
		i = core.xsb_init(xsbargs);

		// send commands to XSB
		// define p as a tabled predicate
		i = core.xsb_command_string("table(p/1).");

		// define member as a tabled predicate
		i = core.xsb_command_string("table(member/2).");

		// assert rules defining the predicate members (using
		// XSB-ASCII-representation of rules)
		// (since Java uses unicode, the glue code translates to to ASCII)

		// assert the usual member rules
		i = core.xsb_command_string("assert(member(H,[H|T])).");
		i = core.xsb_command_string("assert(:-(member(H,[_|T]), member(H,T))).");

		// assert a fact
		i = core.xsb_command_string("assert(p([hello,world])).");

		// use the other part of the interface - show how
		// rules, facts and queries can be assembled using Java Classes.

		// assemble q(X)
		Literal[] head = new Literal[1];
		Term[] a1 = new Term[1];
		a1[0] = new VariableTerm("X");
		head[0] = new Literal("q", a1);

		// assemble p(Y)
		Literal[] body = new Literal[2];
		Term[] a2 = new Term[1];
		a2[0] = new VariableTerm("Y");
		body[0] = new Literal("p", a2);

		// assemble member(X,Y)
		Term[] a3 = new Term[2];
		a3[0] = new VariableTerm("X");
		a3[1] = new VariableTerm("Y");
		body[1] = new Literal("member", a3);
		// built rule
		ProgramClause c = new ProgramClause(head, body);
		// assert rule to XSB rulebase
		core.xsb_assert_rule(c);

		// assemble q(X)
		Term[] b = new Term[1];
		b[0] = new VariableTerm("X");
		Literal[] q = { new Literal("q", b) };
		core.xsb_query_java(new ProgramClause(null, q));
		// get answers and print them..
		while (i == 0) {
			Term[] term = core.xsb_getAnswerSubstitution();
			int k = term.length;
			for (int l = 0; l < k; l++) {
				System.out.print(term[l].toString());
			}
			System.out.println();
			i = core.xsb_next();
		}

		// thats it....
		i = core.xsb_close();
	}

}
