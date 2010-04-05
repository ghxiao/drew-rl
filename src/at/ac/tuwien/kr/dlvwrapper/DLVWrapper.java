package at.ac.tuwien.kr.dlvwrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import DLV.DLVInvocationException;

public class DLVWrapper {
	String dlvPath;

	String program;

	private Runtime runtime = Runtime.getRuntime();

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
