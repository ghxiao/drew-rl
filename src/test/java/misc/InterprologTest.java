//package misc;
//
//import java.io.File;
//
//import org.junit.Ignore;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.declarativa.interprolog.PrologOutputListener;
//import com.declarativa.interprolog.XSBSubprocessEngine;
//
//@Ignore
//public class InterprologTest {
//
//	public static void main(String args[]) {
//
//		final Logger logger = LoggerFactory.getLogger(InterprologTest.class);
//
//		// PrologEngine engine = new NativeEngine();
//
//		// final String XSB_BIN_DIR =
//		// "/usr/local/XSB/3.2/config/i686-pc-linux-gnu/bin";
//		// PrologEngine engine = new NativeEngine(XSB_BIN_DIR);
//
//		// On Windows
//		final String XSB_BIN_DIR = "G:\\Reasoner\\xsb-3.1-win32\\config\\x86-pc-windows\\bin";
//
//		final StringBuffer buffer = new StringBuffer();
//
//		PrologOutputListener listener = new PrologOutputListener() {
//
//			public void print(String s) {
//				buffer.append(s);
//				System.err.println(s);
//			}
//		};
//
//		XSBSubprocessEngine engine = new XSBSubprocessEngine(XSB_BIN_DIR + "/xsb");
//
//		engine.addPrologOutputListener(listener);
//
//		engine.consultAbsolute(new File("./test/misc/barber.P"));
//
//		// the above demonstrates object passing both ways;
//		// since we may simply concatenate strings, an alternative coding would
//		// be:
//		// String goal = "name('" + System.getProperty("user.name")
//		// + "',UL),append(\"Hello,\", UL, ML), name(Message,ML)";
//
//		// System.out.println("goal:" + goal);
//
//		String goal1 = "shaves(barber,barber)";
//
//		logger.info("Query {}", goal1);
//		boolean q1 = engine.deterministicGoal(goal1);
//		logger.info("Query Result {}", q1);
//
//		// goal = "shaves(barber,mayor)";
//		// logger.info("Query {}",goal);
//		// boolean q2 = engine.deterministicGoal(goal);
//		// logger.info("Query Result {}",q2);
//		//		
//		String goal3 = "shaves(mayor,barber)";
//		logger.info("Query {}", goal3);
//		boolean q3 = engine.deterministicGoal(goal3);
//		logger.info("Query Result {}", q3);
//
//		String goal4 = "shaves(mayor,mayor)";
//		logger.info("Query {}", goal4);
//		boolean q4 = engine.deterministicGoal(goal4);
//		logger.info("Query Result {}", q4);
//
//		String goal5 = "p";
//		logger.info("Query {}", goal5);
//		boolean q5 = engine.deterministicGoal(goal5);
//		logger.info("Query Result {}", q5);
//
//		
//		// System.out.println(buffer.toString());
//		// case)
//		// System.out.println("Same:" + bindings[0]);
//		engine.shutdown();
//
//	}
//
//	@Test
//	public void test(){
//		main(null);
//	}
//	
//}
