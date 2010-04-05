package at.ac.tuwien.kr.dlvwrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import DLV.DLVInvocationException;

public class DLVWrapper {
	String dlvPath;

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

	public String getVersion() throws DLVInvocationException {
		String version = "The following warning(s) occurred:\n";
		try {
			Process localProcess = this.runtime.exec(this.dlvPath);
			BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
			String str = localBufferedReader.readLine();
			if (str == null)
				throw new DLVInvocationException("An error is occurred calling DLV.");
			version = str;
			localProcess.waitFor();
		} catch (IOException localIOException) {
			throw new DLVInvocationException("An error is occurred calling DLV: " + localIOException.getMessage());
		} catch (InterruptedException localInterruptedException) {
			throw new DLVInvocationException("An error is occurred calling DLV: " + localInterruptedException.getMessage());
		}
		return version;
	}

}
