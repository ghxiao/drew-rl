package at.ac.tuwien.kr.dlvwrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import misc.Slf4jExample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import DLV.DLVInvocationException;

public class DLVWrapper {
	String dlvPath;

	String program;

	static final Logger logger = LoggerFactory.getLogger(DLVWrapper.class);

	private Runtime runtime = Runtime.getRuntime();

	String predicate ="[a-zA-Z]([a-zA-Z\\d])*";
	
	String term ="[a-zA-Z]([a-zA-Z\\d])*";
	
	String literal = String.format("%s\\{%s\\}", predicate,term);
	
	final String regex = "$True:\\s*()\\s*"; 
	
	public DLVWrapper() {

	}

	/**
	 * @return the dlvPath
	 */
	public String getDlvPath() {
		return dlvPath;
	}

	/**
	 * @param dlvPath
	 *            the dlvPath to set
	 */
	public void setDlvPath(String dlvPath) {
		this.dlvPath = dlvPath;
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
	 */
	public void setProgram(String program) {
		this.program = program;
	}

	public String getVersion() throws DLVInvocationException {
		String version = "The following warning(s) occurred:\n";
		try {
			Process dlv = this.runtime.exec(this.dlvPath);
			BufferedReader localBufferedReader = new BufferedReader(
					new InputStreamReader(dlv.getInputStream()));
			String str = localBufferedReader.readLine();
			if (str == null)
				throw new DLVInvocationException(
						"An error is occurred calling DLV.");
			version = str;
			dlv.waitFor();
		} catch (IOException localIOException) {
			throw new DLVInvocationException(
					"An error is occurred calling DLV: "
							+ localIOException.getMessage());
		} catch (InterruptedException localInterruptedException) {
			throw new DLVInvocationException(
					"An error is occurred calling DLV: "
							+ localInterruptedException.getMessage());
		}
		return version;
	}

	public boolean queryWFS(String queryStr) throws DLVInvocationException {
		boolean result = false;
		try {
			String[] params = { dlvPath, "-wf", "--" };
			Process dlv = this.runtime.exec(params);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					dlv.getInputStream()));

			PrintWriter writer = new PrintWriter(dlv.getOutputStream());
			writer.write(program);
			writer.flush();
			writer.close();

			String line;

			while ((line = reader.readLine()) != null) {
				logger.debug("DLV Output: {}", line);

				
				
				boolean contains = line.contains("True:");
				boolean contains2 = line.contains(queryStr);
				if (contains && contains2) {
					logger.debug("Query \"{}\" found", queryStr);
					result = true;
				}
			}

			dlv.waitFor();
		} catch (IOException ex) {
			throw new DLVInvocationException(
					"An error is occurred calling DLV: " + ex.getMessage());
		} catch (InterruptedException ex) {
			throw new DLVInvocationException(
					"An error is occurred calling DLV: " + ex.getMessage());
		}

		return result;

	}

	public void run() throws DLVInvocationException {
		String[] params = { dlvPath, "--" };
		run(params);
	}

	public void runWFS() throws DLVInvocationException {
		String[] params = { dlvPath, "-wf", "--" };
		run(params);
	}

	private void run(String[] params) throws DLVInvocationException {
		try {
			Process dlv = this.runtime.exec(params);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					dlv.getInputStream()));

			PrintWriter writer = new PrintWriter(dlv.getOutputStream());
			writer.write(program);
			writer.flush();
			writer.close();

			String str;

			while ((str = reader.readLine()) != null) {
				System.out.println(str);
			}

			dlv.waitFor();
		} catch (IOException ex) {
			throw new DLVInvocationException(
					"An error is occurred calling DLV: " + ex.getMessage());
		} catch (InterruptedException ex) {
			throw new DLVInvocationException(
					"An error is occurred calling DLV: " + ex.getMessage());
		}
	}

}
