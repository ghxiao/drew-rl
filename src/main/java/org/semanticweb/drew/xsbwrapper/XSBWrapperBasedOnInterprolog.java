package org.semanticweb.drew.xsbwrapper;
// TODO: maybe support in future

//package at.ac.tuwien.kr.xsbwrapper;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import at.ac.tuwien.kr.datalog.TruthValue;
//
//import com.declarativa.interprolog.PrologOutputListener;
//import com.declarativa.interprolog.XSBSubprocessEngine;
//
//public class XSBWrapperBasedOnInterprolog {
//
//	private class TruthValueReader implements PrologOutputListener {
//		private final StringBuffer buffer;
//
//		private TruthValueReader(StringBuffer buffer) {
//			this.buffer = buffer;
//		}
//
//		public void print(String s) {
//			buffer.append(s);
//			System.err.print(s);
//		}
//
//		public synchronized TruthValue read() throws InterruptedException {
//
//			TruthValue result = null;
//			boolean founded = false;
//
//			while (!founded) {
//				
//				String message = buffer.toString();
//	
//				if (message.contains("yes")) {
//					result = TruthValue.TRUE;
//					founded = true;
//				} else if (message.contains("undefined")) {
//					result = TruthValue.UNKNOWN;
//					founded = true;
//				} else if (message.contains("no")) {
//					result = TruthValue.FALSE;
//					founded = true;
//
//				}
//
//				// Thread.sleep(10);
//
//				Thread.yield();
//			}
//
//			logger.debug("got value from \"{}\"", buffer);
//
//			// clearing the buffer
//
//			buffer.delete(0, buffer.length());
//
//			return result;
//
//			// throw new
//			// IllegalStateException("Should be a truth value founded in " +
//			// message);
//
//		}
//	}
//
//	final Logger logger = LoggerFactory.getLogger(XSBWrapperBasedOnInterprolog.class);
//	private XSBSubprocessEngine engine;
//	final String XSB_BIN_DIR = "G:\\Reasoner\\xsb-3.1-win32\\config\\x86-pc-windows\\bin";
//	private TruthValueReader valueReader;
//
//	public XSBWrapperBasedOnInterprolog() {
//
//		engine = new XSBSubprocessEngine(XSB_BIN_DIR + "/xsb");
//
//		final StringBuffer buffer = new StringBuffer();
//
//		valueReader = new TruthValueReader(buffer);
//
//		engine.addPrologOutputListener(valueReader);
//	}
//
//	public void setProgram(String program) throws IOException, InterruptedException {
//		File programFile = File.createTempFile("program", ".P");
//		FileWriter fileWriter = new FileWriter(programFile);
//		fileWriter.write(program);
//		fileWriter.close();
//		final String path = programFile.getAbsolutePath();
//		logger.debug("Program has been saved as {}", path);
//		logger.debug("consulting file ...");
//		engine.consultAbsolute(programFile);
//		engine.waitUntilIdle();
//
//		// skip this truth value;
//		// valueReader.read();
//	}
//
//	public void close() {
//		engine.command("halt");
//	}
//
//	public TruthValue query(String q) throws InterruptedException {
//		logger.debug("Query: {}", q);
//		boolean result = engine.deterministicGoal(q);
//
//		logger.debug("Result: {}", result);
//
//		engine.waitUntilIdle();
//
//		final TruthValue resultFromOutput = valueReader.read();
//
//		// DON'T ASK ME WHY!!!!
//		// JUST BY OBSERVATION!!!
//		if (resultFromOutput == TruthValue.TRUE && result == false) {
//			return TruthValue.FALSE;
//		}
//
//		return resultFromOutput;
//
//		//		
//		// if (result == true) {
//		// return TruthValue.TRUE;
//		// } else
//		// return TruthValue.FALSE;
//
//		// return result;
//	}
//
//	public void test() {
//
//		// PrologEngine engine = new NativeEngine();
//
//		// final String XSB_BIN_DIR =
//		// "/usr/local/XSB/3.2/config/i686-pc-linux-gnu/bin";
//		// PrologEngine engine = new NativeEngine(XSB_BIN_DIR);
//
//		// G:\Reasoner\xsb-3.1-win32\config\x86-pc-windows
//		final String XSB_BIN_DIR = "G:/Reasoner/xsb-3.1-win32/config/x86-pc-windows/bin";
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
//		String goal = "shaves(barber,barber)";
//
//		logger.info("Query {}", goal);
//		boolean q1 = engine.deterministicGoal(goal);
//		// (notice the ' surrounding the user name, unnecessary in the first
//
//		goal = "shaves(barber,mayor)";
//		logger.info("Query {}", goal);
//		boolean q2 = engine.deterministicGoal(goal);
//
//		goal = "shaves(mayor,barber)";
//		logger.info("Query {}", goal);
//		boolean q3 = engine.deterministicGoal(goal);
//
//		System.out.println(buffer.toString());
//		// case)
//		// System.out.println("Same:" + bindings[0]);
//		engine.shutdown();
//	}
//
//}
