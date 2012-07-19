package misc;

//import DLV.*;

public class DlvTest {

//	/**
//	 * @param args
//	 * @throws DLVInvocationException
//	 */
//	public static void main(String[] args) throws DLVInvocationException {
//		// prepare input
//		testFromString();
//
//	}
//
//	private static void testFromFile() throws DLVInvocationException {
//		Program p = new Program(); // creates a new program
//		// p.addString(":- a, not b."); // adds a constraint using a String
//		// object
//		// p.addProgramFile(".\\test\\misc\\simple.dlv"); // adds a text file
//		// containing
//		p.addProgramFile(".\\test\\misc\\super.dlv"); // adds a text file
//		// containing
//
//		// build a manager (a Dlv instance)
//
//		DlvHandler dlv = new DlvHandler(".\\dlv\\dlv.mingw.exe"); // builds a
//		// manager
//		// object and set the
//		// dlv executable full
//		// pathname
//
//		dlv.setProgram(p); // sets input (contained in a Program object)
//
//		// set invocation parameters
//
//		dlv.setNumberOfModels(2); // sets the number of models to be generated
//		// by DLV
//		// dlv.setFilter(new String[] { "q" }); // sets a -filter=a option
//		dlv.setIncludeFacts(false); // sets -nofacts option
//
//		// call DLV
//		try {
//			dlv.run(DlvHandler.MODEL_SYNCHRONOUS); // run a DLV process and set
//			// the output handling
//			// method.
//
//			while (dlv.hasMoreModels()) // waits while DLV outputs a new model
//			{
//				Model m = dlv.nextModel(); // gets a Model object
//				if (!m.isNoModel()) {
//					while (m.hasMorePredicates()) // work with Predicates
//					// contained in m
//					{
//						Predicate pr = m.nextPredicate(); // gets a Predicate
//						// object
//						System.out.println(pr);
//					}
//				}
//
//			}
//
//		} catch (DLVException d) {
//			d.printStackTrace();
//		} catch (DLVExceptionUncheked d) {
//			d.printStackTrace();
//		} finally {
//			System.err.println(dlv.getWarnings()); // handle DLV errors
//
//		}
//	}
//
//	private static void testFromString() throws DLVInvocationException {
//		Program p = new Program(); // creates a new program
//
//		 String ps = "q(X,Y):-p(X,Y)."//
//		 + "q(X,Z):-p(X,Y),q(Y,Z)."//
//		 + "p(a,b)."//
//		 + "p(b,c).";
//		String[] lines = { "q(X,Y):-p(X,Y).",//
//				"q(X,Z):-p(X,Y),q(Y,Z).",//
//				"p(a,b).",//
//				"p(b,c)." };
//
//		p.addString(ps); // adds a constraint using a String
////		for (String line : lines) {
////			p.addString(line);
////		}
//		// object
//		// p.addProgramFile(".\\test\\misc\\simple.dlv"); // adds a text file
//		// containing
//		// p.addProgramFile(".\\test\\misc\\super.dlv"); // adds a text file
//		// containing
//
//		// build a manager (a Dlv instance)
//
//		DlvHandler dlv = new DlvHandler(".\\dlv\\dlv.mingw.exe"); // builds a
//		// manager
//		// object and set the
//		// dlv executable full
//		// pathname
//
//		dlv.setProgram(p); // sets input (contained in a Program object)
//
//		// set invocation parameters
//
//		dlv.setNumberOfModels(2); // sets the number of models to be generated
//		// by DLV
//		// dlv.setFilter(new String[] { "q" }); // sets a -filter=a option
//		dlv.setIncludeFacts(false); // sets -nofacts option
//
//		// call DLV
//		try {
//			dlv.run(DlvHandler.MODEL_SYNCHRONOUS); // run a DLV process and set
//			// the output handling
//			// method.
//
//			while (dlv.hasMoreModels()) // waits while DLV outputs a new model
//			{
//				Model m = dlv.nextModel(); // gets a Model object
//				if (!m.isNoModel()) {
//					while (m.hasMorePredicates()) // work with Predicates
//					// contained in m
//					{
//						Predicate pr = m.nextPredicate(); // gets a Predicate
//						// object
//						System.out.println(pr);
//					}
//				}
//
//			}
//
//		} catch (DLVException d) {
//			d.printStackTrace();
//		} catch (DLVExceptionUncheked d) {
//			d.printStackTrace();
//		} finally {
//			System.err.println(dlv.getWarnings()); // handle DLV errors
//
//		}
//	}
//
//	private static void testFromString2() throws DLVInvocationException {
//		Program p = new Program(); // creates a new program
//
//		String[] lines = { "p1(X):-p2(X).",//
//				"top1(o1).",//
//				"top1(o2).",//
//				"p3(o3,o1).",//
//				"top1(o3).",//
//				"p1(Y):-p1(X),p4(X,Y).",//
//				"p3(o1,o2).",//
//				"top1(X):-p1(X).",//
//				"p2(X):-p5(X,Y1),top1(Y1),p5(X,Y2),top1(Y2),Y1!=Y2.",//
//				"top2(X,Y):-p5(X,Y).",//
//				"top2(X,Y):-p3(X,Y).",//
//				"p4(X,Y):-p3(X,Y).",//
//				"p4(X,Z):-p3(X,Y),p4(Y,Z)." };
//
//		for (String line : lines) {
//			p.addString(line);
//		}
//
//		// p.addString(ps); // adds a constraint using a String
//		// object
//		// p.addProgramFile(".\\test\\misc\\simple.dlv"); // adds a text file
//		// containing
//		// p.addProgramFile(".\\test\\misc\\super.dlv"); // adds a text file
//		// containing
//
//		// build a manager (a Dlv instance)
//
//		DlvHandler dlv = new DlvHandler(".\\dlv\\dlv.mingw.exe"); // builds a
//		// manager
//		// object and set the
//		// dlv executable full
//		// pathname
//
//		dlv.setProgram(p); // sets input (contained in a Program object)
//
//		// set invocation parameters
//
//		dlv.setNumberOfModels(2); // sets the number of models to be generated
//		// by DLV
//		// dlv.setFilter(new String[] { "q" }); // sets a -filter=a option
//		dlv.setIncludeFacts(false); // sets -nofacts option
//
//		// call DLV
//		try {
//			dlv.run(DlvHandler.MODEL_SYNCHRONOUS); // run a DLV process and set
//			// the output handling
//			// method.
//
//			while (dlv.hasMoreModels()) // waits while DLV outputs a new model
//			{
//				Model m = dlv.nextModel(); // gets a Model object
//				if (!m.isNoModel()) {
//					while (m.hasMorePredicates()) // work with Predicates
//					// contained in m
//					{
//						Predicate pr = m.nextPredicate(); // gets a Predicate
//						// object
//						System.out.println(pr);
//					}
//				}
//
//			}
//
//		} catch (DLVException d) {
//			d.printStackTrace();
//		} catch (DLVExceptionUncheked d) {
//			d.printStackTrace();
//		} finally {
//			System.err.println(dlv.getWarnings()); // handle DLV errors
//
//		}
//	}
}
