package at.ac.tuwien.kr.dlvwrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.regex.NamedMatcher;
import com.google.code.regex.NamedPattern;

import DLV.DLVInvocationException;

public class DLVWrapper {
	String dlvPath;

	String program;

	static final Logger logger = LoggerFactory.getLogger(DLVWrapper.class);

	private Runtime runtime = Runtime.getRuntime();

	String predicateRegex = "(?<predicate>[a-z][a-zA-Z\\d_]*)";

	String termRegex = "(?<term>[a-z][a-zA-Z\\d_]*)";

	String literalRegex = String.format("(?<literal>%s(\\((%s(,%s)*)?\\))?)",
			predicateRegex, termRegex, termRegex);

	String literalListRegex = String.format("(?<literalList>%s(,\\s*%s)*)",
			literalRegex, literalRegex);

	String lineRegex = String.format("True:\\s*(\\{%s\\})\\s*",
			literalListRegex);

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

				NamedPattern linePattern = NamedPattern.compile(lineRegex);
				NamedMatcher lineMatcher = linePattern.matcher(line);
				if (lineMatcher.find()) {
					String lits = lineMatcher.group("literalList");
//					System.out.println("literalList " + lits);
					NamedPattern literalPattern = NamedPattern
							.compile(literalRegex);
					NamedMatcher literalMatcher = literalPattern.matcher(lits);
					while (literalMatcher.find()) {
						String lit = literalMatcher.group("literal");
//						System.out.println("literal " + lit);
						if (lit.equals(queryStr)) {
							logger.debug("Query \"{}\" found", queryStr);
							result = true;
							break;
						}
					}
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
