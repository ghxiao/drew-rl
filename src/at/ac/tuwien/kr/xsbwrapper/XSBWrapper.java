package at.ac.tuwien.kr.xsbwrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.kr.datalog.TruthValue;
import at.ac.tuwien.kr.dlvwrapper.DLVWrapper;

public class XSBWrapper {

	String xsbPath;

	String program;

	static final Logger logger = LoggerFactory.getLogger(XSBWrapper.class);

	private Runtime runtime = Runtime.getRuntime();

	private Process xsb;

	private BufferedReader err;

	private PrintWriter out;

	private BufferedReader in;
	
	private StringBuffer outputBuffer = new StringBuffer();

	public XSBWrapper(String xsbPath) throws XSBInvocationException {
		try {
			xsb = runtime.exec(xsbPath);

			in = new BufferedReader(new InputStreamReader(xsb.getInputStream()));

			out = new PrintWriter(xsb.getOutputStream());

			err = new BufferedReader(new InputStreamReader(xsb.getErrorStream()));
			
			OutputHandler outputHandler = new OutputHandler(in);
			outputHandler.start();

			ErrorHandler errorHandler = new ErrorHandler(err);
			errorHandler.start();

		} catch (IOException e) {
			throw new XSBInvocationException(e);
		}
	}

	/**
	 * @return the xsbPath
	 */
	public String getXsbPath() {
		return xsbPath;
	}

	/**
	 * @param xsbPath
	 *            the xsbPath to set
	 */
	public void setXsbPath(String xsbPath) {
		this.xsbPath = xsbPath;
	}

	/**
	 * @return the program
	 */
	public String getProgram() {
		return program;
	}

	/**
	 * @param program
	 *            the program to set
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public void setProgram(String program) throws IOException, InterruptedException {
		this.program = program;
		//File programFile = File.createTempFile("program", ".P");
		File programFile = new File("program.P");
		FileWriter fileWriter = new FileWriter(programFile);
		fileWriter.write(program);
		fileWriter.close();
		//String problemPath = programFile.getAbsolutePath();
		String problemPath = programFile.getCanonicalPath();
		consult(problemPath);
	}

	public TruthValue consult(String file) throws IOException, InterruptedException {
		String cmd = String.format("consult('%s').", file);
		return command(cmd);
	}

	public TruthValue query(String q) throws IOException, InterruptedException {
		return command(q + ".");

	}

	public TruthValue command(String cmd) throws IOException, InterruptedException {
		logger.debug("command: {}", cmd);

		out.append(cmd);
		out.append("\n");
		out.flush();

		return read();
		
		// StringBuffer buffer = new StringBuffer();
		// String line;
		// while ((line = in.readLine()) != null) {
		// buffer.append(line);
		// logger.debug("XSB Output:\n{}", line);
		// buffer.append("\n");
		// }
		//
		// return buffer.toString();
	}

	public synchronized TruthValue read() throws InterruptedException {

		TruthValue result = null;
		boolean founded = false;

		while (!founded) {
			
			String message = outputBuffer.toString();

			if (message.contains("yes")) {
				result = TruthValue.TRUE;
				founded = true;
			} else if (message.contains("undefined")) {
				result = TruthValue.UNKNOWN;
				founded = true;
			} else if (message.contains("no")) {
				result = TruthValue.FALSE;
				founded = true;

			}

			// Thread.sleep(10);

			Thread.yield();
		}

		logger.debug("got value from \"{}\"", outputBuffer);

		// clearing the buffer		
		outputBuffer.delete(0, outputBuffer.length());

		return result;

		// throw new
		// IllegalStateException("Should be a truth value founded in " +
		// message);

	}
	
	class OutputHandler extends Thread {

		private BufferedReader source;

		OutputHandler(BufferedReader source) {
			this.source = source;
		}
		
		

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			String line;
			try {
				while ((line = source.readLine()) != null) {
					outputBuffer.append(line);
					outputBuffer.append("\n");
					
					logger.debug("XSB output: {}", line);
				}
			} catch (IOException e) {
				throw new XSBInvocationException(e);
			}
		}
	}

	class ErrorHandler extends Thread {

		private BufferedReader source;

		ErrorHandler(BufferedReader source) {
			this.source = source;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			String line;
			try {
				while ((line = source.readLine()) != null) {
					logger.error("XSB error: {}", line);
				}
			} catch (IOException e) {
				throw new XSBInvocationException(e);
			}
		}
	}

	public void close() throws IOException, InterruptedException {
		//command("halt.");
	}
}
