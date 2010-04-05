package DLV.core;

import DLV.DLVInvocationException;
import DLV.Parser;
import DLV.Predicate;
import DLV.Program;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class JavaPipedIOImpl implements AbstractIOImplementation {
	private Runtime runtime = Runtime.getRuntime();
	private String dlvPath = "dl";
	private Process dlv = null;
	private BufferedReader input = null;
	private BufferedReader error = null;
	private PrintWriter output = null;
	private byte status = 0;
	private WarningReader warningReader = null;
	private StringBuffer warnings = new StringBuffer();
	private boolean lockWarning = false;
	private InputReader inputReader = null;
	public static final byte READY = 0;
	public static final byte RUNNING = 1;
	public static final byte FINISHED = 2;
	public static final byte FREEZED = 3;

	public JavaPipedIOImpl(String paramString) {
		this.dlvPath = paramString;
	}

	public void setPathName(String paramString) {
		this.dlvPath = paramString;
	}

	public String getPathName() {
		return this.dlvPath;
	}

	public synchronized void run(DlvIOHandler dlvIOHandler) throws DLVInvocationException {
		reset();
		try {
			String[] params = new String[dlvIOHandler.getInvocationParameters().length + 1];
			for (int i = 0; i < dlvIOHandler.getInvocationParameters().length; ++i)
				params[i] = dlvIOHandler.getInvocationParameters()[i];
			params[dlvIOHandler.getInvocationParameters().length] = "--";
			params[0] = this.dlvPath;
			this.dlv = this.runtime.exec(params);
			this.status = RUNNING;
			this.input = new BufferedReader(new InputStreamReader(this.dlv.getInputStream(), dlvIOHandler.getEncoding()));
			this.error = new BufferedReader(new InputStreamReader(this.dlv.getErrorStream(), dlvIOHandler.getEncoding()));
			this.output = new PrintWriter(new OutputStreamWriter(this.dlv.getOutputStream(), dlvIOHandler.getEncoding()));
			this.output.print(dlvIOHandler.getProgram().getProgramStrings().toString());
			Predicate[] predicates = dlvIOHandler.getProgram().getPredicates();
			if (predicates != null)
				for (int j = 0; j < predicates.length; ++j)
					predicates[j].appendTo(this.output);
			this.output.print(' ');
			this.output.close();
			this.output = null;
			this.warningReader = new WarningReader();
			this.warningReader.start();
			this.inputReader = new InputReader(dlvIOHandler);
			this.inputReader.start();
		} catch (Throwable localThrowable) {
			throw new DLVInvocationException("An error is occurred calling DLV: " + localThrowable.getMessage());
		}
	}

	public synchronized void kill(DlvIOHandler paramDlvIOHandler) throws DLVInvocationException {
		if ((this.inputReader == null) || ((this.status != 1) && (this.status != 3)))
			return;
		this.inputReader.kill();
	}

	public String getWarnings() throws DLVInvocationException {
		String str = "";
		synchronized (this.warnings) {
			str = this.warnings.toString();
		}
		return str;
	}

	public void setWarnings(String paramString) throws DLVInvocationException {
		synchronized (this.warnings) {
			this.warnings.append(paramString);
		}
	}

	public synchronized void reset() {
		if ((this.inputReader != null) && (((this.status == 1) || (this.status == 3))))
			this.inputReader.kill();
		this.warningReader = null;
		this.inputReader = null;
		switch (this.status) {
		case RUNNING:
		case FREEZED:
		}
		this.status = READY;
		try {
			if (this.input != null)
				this.input.close();
		} catch (Throwable localThrowable1) {
		}
		try {
			if (this.output != null)
				this.output.close();
		} catch (Throwable localThrowable2) {
		}
		try {
			if (this.error != null)
				this.error.close();
		} catch (Throwable localThrowable3) {
		}
		try {
			if (this.dlv != null)
				this.dlv.destroy();
		} catch (Throwable localThrowable4) {
		}
		this.input = null;
		this.output = null;
		this.error = null;
		this.dlv = null;
		this.warnings = new StringBuffer();
	}

	public void freeze(DlvIOHandler paramDlvIOHandler) throws DLVInvocationException {
		throw new UnsupportedOperationException("freeze feature not implemented");
	}

	public void deFreeze(DlvIOHandler paramDlvIOHandler) throws DLVInvocationException {
		throw new UnsupportedOperationException("defreeze feature not implemented");
	}

	public String getVersion() throws DLVInvocationException {
		Object localObject = "The following warning(s) occurred:\n";
		try {
			Process localProcess = this.runtime.exec(this.dlvPath);
			BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
			String str = localBufferedReader.readLine();
			if (str == null)
				throw new DLVInvocationException("An error is occurred calling DLV.");
			localObject = str;
			localProcess.waitFor();
		} catch (IOException localIOException) {
			throw new DLVInvocationException("An error is occurred calling DLV: " + localIOException.getMessage());
		} catch (InterruptedException localInterruptedException) {
			throw new DLVInvocationException("An error is occurred calling DLV: " + localInterruptedException.getMessage());
		}
		return (String) localObject;
	}

	private class WarningReader extends Thread {
		private boolean running;

		// private final JavaPipedIOImpl this$0;

		private WarningReader() {
			// this.this$0 = this$1;
			this.running = true;
		}

		public void run() {
			try {
				String str = "";
				// while (((str = this.this$0.error.readLine()) != null) &&
				// (!interrupted()))
				while (((str = error.readLine()) != null) && (!interrupted()))
					try {
						JavaPipedIOImpl.this.setWarnings(str);
					} catch (DLVInvocationException localDLVInvocationException1) {
					}
			} catch (Throwable localThrowable) {
				try {
					JavaPipedIOImpl.this.setWarnings("Error Type: " + localThrowable.getClass() + " Message: " + localThrowable.getMessage());
				} catch (DLVInvocationException localDLVInvocationException2) {
				}
			} finally {
				synchronized (this) {
					this.running = false;
					super.notifyAll();
				}
			}
		}

		void kill() {
			interrupt();
		}

		synchronized void waitFor() {
			try {
				if (this.running)
					super.wait();
			} catch (InterruptedException localInterruptedException) {
			}
		}

		// WarningReader(JavaPipedIOImpl.1 arg2)
		// {
		// this(this$1);
		// }
	}

	private class InputReader extends Thread {
		private DlvIOHandler handler = null;
		private boolean killed = false;

		InputReader(DlvIOHandler handler) {
			// Object localObject;
			// this.handler = localObject;
			this.handler = handler;
		}

		void kill() {
			synchronized (this) {
				this.killed = true;
			}
			JavaPipedIOImpl.this.warningReader.kill();
			try {
				JavaPipedIOImpl.this.dlv.destroy();
			} catch (Throwable localThrowable) {
			}
		}

		public void run() {
			try {
				Parser localParser = new Parser();
				localParser.parse(JavaPipedIOImpl.this.input, this.handler);
			} catch (Throwable localThrowable1) {
				this.handler.signalError(localThrowable1);
			} finally {
				synchronized (this) {
					try {
						JavaPipedIOImpl.this.warningReader.waitFor();
						JavaPipedIOImpl.this.dlv.waitFor();
					} catch (InterruptedException localInterruptedException) {
					}
					try {
						JavaPipedIOImpl.this.input.close();
						JavaPipedIOImpl.this.error.close();
					} catch (Throwable localThrowable2) {
					}
					if (!this.killed) {
						// JavaPipedIOImpl.access$502(JavaPipedIOImpl.this, 2);
						JavaPipedIOImpl.this.status = FINISHED;
						this.handler.signalDlvEnd();
					} else {
						// JavaPipedIOImpl.access$502(JavaPipedIOImpl.this, 2);
						JavaPipedIOImpl.this.status = FINISHED;
						this.handler.signalDlvKill();
					}
				}
			}
		}
	}
}

/*
 * Location: G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar Qualified Name:
 * DLV.core.JavaPipedIOImpl JD-Core Version: 0.5.4
 */