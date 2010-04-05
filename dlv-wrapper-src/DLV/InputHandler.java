package DLV;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class InputHandler {
	private StringBuffer program = new StringBuffer();
	private String[] file = new String[0];
	private BufferedReader in = null;
	private boolean closed = false;
	private int countFile = 0;
	private int countBuffer = 0;
	private int buffer = -5;

	InputHandler(StringBuffer paramStringBuffer, String[] paramArrayOfString) {
		this.program = paramStringBuffer;
		this.file = paramArrayOfString;
	}

	InputHandler(BufferedReader paramBufferedReader) {
		this.in = paramBufferedReader;
	}

	BufferedReader getReader() {
		return this.in;
	}

	protected void finalize() {
		close();
	}

	char getChar() throws IOException, ParserException {
		if (this.in != null)
			return next();
		if ((this.file.length == 0) || (this.countFile >= this.file.length))
			this.closed = true;
		if (this.countFile < this.file.length) {
			this.closed = false;
			this.in = new BufferedReader(new FileReader(this.file[this.countFile]));
			this.countFile += 1;
			return next();
		}
		if (this.countBuffer < this.program.length()) {
			this.closed = false;
			int i = this.program.charAt(this.countBuffer);
			this.countBuffer += 1;
			if (this.countBuffer == this.program.length())
				this.closed = true;
			return (char) i;
		}
		if (this.countBuffer == 0) {
			this.closed = true;
			return '\000';
		}
		this.closed = true;
		throw new RuntimeException("Input closed or EOF");
	}

	boolean end() {
		return (this.closed) && (this.countBuffer >= this.program.length()) && (this.countFile >= this.file.length) && (this.in == null);
	}

	private char next() throws IOException {
		this.closed = false;
		this.buffer = this.in.read();
		if (this.buffer == -1) {
			this.closed = true;
			this.buffer = -5;
			this.in.close();
			this.in = null;
			return '\n';
		}
		return (char) this.buffer;
	}

	void close() {
		if (this.in != null)
			try {
				this.in.close();
			} catch (Throwable localThrowable) {
			}
		this.closed = true;
	}
}

/*
 * Location: G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar Qualified Name:
 * DLV.InputHandler JD-Core Version: 0.5.4
 */