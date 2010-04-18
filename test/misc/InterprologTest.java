package misc;

import java.io.File;

import com.declarativa.interprolog.PrologEngine;
import com.declarativa.interprolog.XSBSubprocessEngine;
import com.xsb.interprolog.NativeEngine;

public class InterprologTest {

	public static void main(String args[]) {

		// PrologEngine engine = new NativeEngine();

		final String XSB_BIN_DIR = "/usr/local/XSB/3.2/config/i686-pc-linux-gnu/bin";
		//PrologEngine engine = new NativeEngine(XSB_BIN_DIR);
		PrologEngine engine = new XSBSubprocessEngine(XSB_BIN_DIR+"/xsb");

		engine.consultAbsolute(new File("./test/misc/barber.P"));
		
		
		// the above demonstrates object passing both ways;
		// since we may simply concatenate strings, an alternative coding would
		// be:
//		String goal = "name('" + System.getProperty("user.name")
//				+ "',UL),append(\"Hello,\", UL, ML), name(Message,ML)";
		
		//System.out.println("goal:" + goal);
		
		String goal = "shaves(barber,barber)";
		
		
		boolean q1 = engine.deterministicGoal(goal);
		// (notice the ' surrounding the user name, unnecessary in the first
		
		goal = "shaves(barber,mayor)";
		
		boolean q2 = engine.deterministicGoal(goal);
		
		goal = "shaves(mayor,barber)";
		
		boolean q3 = engine.deterministicGoal(goal);
		
		
		// case)
		//System.out.println("Same:" + bindings[0]);
		engine.shutdown();
		
	}

}
