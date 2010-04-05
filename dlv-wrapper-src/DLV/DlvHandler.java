package DLV;

import DLV.core.AbstractIOImplementation;
import DLV.core.DlvIOHandler;
import DLV.core.JavaPipedIOImpl;
import java.util.ArrayList;
import java.util.StringTokenizer;
import sun.io.ByteToCharConverter;

public class DlvHandler implements DlvIOHandler {
	private AbstractIOImplementation impl = null;
	private OutputDescriptor outputDescriptor = new OutputDescriptor();
	private Program program;
	private String encoding = ByteToCharConverter.getDefault().getCharacterEncoding();
	private boolean[] parameter = { false, false, false, false, false, false, false, false, false, false, false, false };
	private Object[] parameterValue = new Object[12];
	private ArrayList modelList = new ArrayList();
	private int cursor = -1;
	private Throwable error = null;
	private byte method = 1;
	private byte status = 0;
	public static final byte READY = 0;
	public static final byte RUNNING = 1;
	public static final byte FINISHED = 2;
	public static final byte KILLED = 3;
	public static final byte FREEZED = 4;
	public static final byte SYNCHRONOUS = 0;
	public static final byte MODEL_SYNCHRONOUS = 1;
	public static final byte ASYNCHRONOUS = 2;
	private static final byte FRONTEND = 0;
	private static final byte COSTBOUND = 1;
	private static final byte DETERMINISTIC_CONSEQUENCE = 2;
	private static final byte ISTANTIATE = 3;
	private static final byte NUMBER_OF_MODELS = 4;
	private static final byte MAXINT = 5;
	private static final byte OPTIMIZATION_OPTIONS = 6;
	private static final byte FILTER = 7;
	private static final byte PFILTER = 8;
	private static final byte INCLUDE_FACTS = 9;
	private static final byte GENERATE_ALL_POSSIBILY_MODELS = 10;
	private static final byte MM_OPTION = 11;

	public static String getDLVWrapperInfo() {
		return "The Java DLV Wrapper version 3.0beta_2";
	}

	public DlvHandler(String paramString) {
		this.impl = new JavaPipedIOImpl(paramString);
	}

	public DlvHandler(AbstractIOImplementation paramAbstractIOImplementation) {
		this.impl = paramAbstractIOImplementation;
	}

	public String[] getInvocationParameters() {
		return createInvocationParameters();
	}

	public synchronized void addModel(Model paramModel) {
		this.modelList.add(paramModel);
		super.notifyAll();
	}

	public synchronized void signalDlvEnd() {
		this.status = 2;
		if (this.modelList.size() == 0)
			addModel(Model.NO_MODEL);
		super.notifyAll();
	}

	public synchronized void signalDlvKill() {
		this.status = 3;
		if (this.modelList.size() == 0)
			addModel(Model.NO_MODEL);
		super.notifyAll();
	}

	public synchronized void signalDlvFreeze() {
		if (this.status == 1)
			this.status = 4;
		super.notifyAll();
	}

	public synchronized void signalDlvDeFreeze() {
		if (this.status == 4)
			this.status = 1;
		super.notifyAll();
	}

	public synchronized void signalError(Throwable paramThrowable) {
		this.error = paramThrowable;
		super.notifyAll();
	}

	public String getEncoding() {
		return this.encoding;
	}

	public void setEncoding(String paramString) {
		this.encoding = paramString;
	}

	public synchronized void run() throws DLVInvocationException {
		run((byte) 1);
	}

	public synchronized void run(byte method) throws DLVInvocationException {
		long l = System.currentTimeMillis();
		if (this.status == RUNNING)
			throw new DLVInvocationException("DLV is RUNNING, stop current invocation before call this function");
		if (this.status == FREEZED)
			throw new DLVInvocationException("DLV is FREEZED, stop current invocation before call this function");
		this.method = method;
		this.status = 1;
		this.modelList = new ArrayList();
		this.cursor = -1;
		this.error = null;
		if (this.program == null)
			throw new DLVInvocationException("null program");
		this.impl.run(this);
	}

	public synchronized void kill() throws DLVInvocationException {
		if ((this.status != 1) && (this.status != 4))
			return;
		this.impl.kill(this);
		try {
			while ((this.status != 3) && (this.status != 2))
				super.wait();
		} catch (InterruptedException localInterruptedException) {
		}
	}

	public synchronized void freeze() throws DLVInvocationException {
		if (this.status != 1)
			return;
		this.impl.freeze(this);
	}

	public synchronized void deFreeze() throws DLVInvocationException {
		if (this.status != 4)
			return;
		this.impl.deFreeze(this);
	}

	public synchronized void reset() throws DLVInvocationException {
		if ((this.status == 1) || (this.status == 4))
			kill();
		this.status = 0;
		this.program = new Program();
		this.parameter = new boolean[] { false, false, false, false, false, false, false, false, false, false, false, false };
		this.parameterValue = new Object[12];
		this.modelList = new ArrayList();
		this.cursor = -1;
		this.method = 1;
		this.error = null;
		this.impl.reset();
	}

	public String getWarnings() throws DLVInvocationException {
		if (this.status == 0)
			return null;
		return this.impl.getWarnings();
	}

	public synchronized void setPath(String paramString) {
		if (!(this.impl instanceof JavaPipedIOImpl))
			return;
		((JavaPipedIOImpl) this.impl).setPathName(paramString);
	}

	public synchronized String getPath() {
		if (this.impl instanceof JavaPipedIOImpl)
			return ((JavaPipedIOImpl) this.impl).getPathName();
		return null;
	}

	public synchronized String getVersion() throws DLVInvocationException {
		return this.impl.getVersion();
	}

	public synchronized byte getStatus() {
		return this.status;
	}

	public synchronized byte getMethod() {
		return this.method;
	}

	public synchronized boolean hasMoreModels() throws DLVInvocationException {
		if (this.status == 0)
			throw new NoModelsComputed();
		if ((this.status == 1) || (this.status == 4))
			try {
				switch (this.method) {
				case 2:
					break;
				case 1:
					if (this.cursor + 1 < this.modelList.size())
						return true;
					while (true) {
						if ((this.cursor + 1 < this.modelList.size()) || (this.status != 1))
							// break label142;
							break;
						super.wait();
					}
				case 0:
					while ((this.error == null) && (((this.status == 1) || (this.status == 4))))
						super.wait();
				}
			} catch (InterruptedException localInterruptedException) {
				//label142: 
					throw new DLVInvocationException(localInterruptedException.getMessage());
			}
		if (this.error != null)
			throw new DLVInvocationException("Error Type: " + this.error.getClass() + " Message: " + this.error.getMessage());
		return this.cursor + 1 < this.modelList.size();
	}

	public synchronized Model nextModel() throws DLVInvocationException {
		if (hasMoreModels()) {
			this.cursor += 1;
			return getModel();
		}
		if ((this.status == 2) || (this.status == 3))
			throw new NoSuchModelException();
		return null;
	}

	public synchronized Model previousModel() {
		if (this.status == 0)
			throw new NoModelsComputed();
		if (this.cursor > 0) {
			this.cursor -= 1;
			return getModel();
		}
		throw new NoSuchModelException();
	}

	public synchronized Model getModel(int paramInt) {
		if (this.status == 0)
			throw new NoModelsComputed();
		if (paramInt < 0)
			throw new NoSuchModelException();
		if (paramInt >= this.modelList.size())
			return null;
		return (Model) this.modelList.get(paramInt);
	}

	public synchronized Model getModel() {
		return getModel(this.cursor);
	}

	public synchronized void removeModel(int paramInt) {
		if (this.status == 0)
			throw new NoModelsComputed();
		if (this.modelList.size() == 0)
			throw new NoSuchModelException();
		if ((paramInt < 0) || (paramInt >= this.modelList.size()))
			throw new NoSuchModelException();
		if (paramInt <= this.cursor)
			this.cursor -= 1;
		this.modelList.remove(paramInt);
	}

	public synchronized void removeModel() {
		removeModel(this.cursor);
	}

	public synchronized int getCursor() {
		return this.cursor;
	}

	public synchronized void setCursor(int paramInt) {
		if (this.status == 0)
			throw new NoModelsComputed();
		if (paramInt < 0) {
			this.cursor = -1;
		} else {
			if (paramInt >= this.modelList.size())
				throw new NoSuchModelException();
			this.cursor = paramInt;
		}
	}

	public synchronized boolean isFirst() {
		if (this.status == 0)
			throw new NoModelsComputed();
		if (this.modelList.size() == 0)
			return false;
		return this.cursor == 0;
	}

	public synchronized boolean isLast() {
		if (this.status == 0)
			throw new NoModelsComputed();
		if (this.modelList.size() == 0)
			return false;
		return this.cursor == this.modelList.size() - 1;
	}

	public synchronized int getSize() {
		if (this.status == 0)
			throw new NoModelsComputed();
		return this.modelList.size();
	}

	public OutputDescriptor getOutputDescriptor() {
		return this.outputDescriptor;
	}

	public void setOutputDescriptor(OutputDescriptor paramOutputDescriptor) {
		this.outputDescriptor = paramOutputDescriptor;
	}

	public Program getProgram() {
		return this.program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public void setFrontEnd(String paramString) {
		if ((paramString == null) || (paramString.length() == 0))
			this.parameter[0] = false;
		this.parameter[0] = true;
		this.parameterValue[0] = paramString;
		throw new UnsupportedOperationException("feature not implemented yet");
	}

	public void setCostBound(String paramString) {
		if ((paramString == null) || (paramString.length() == 0))
			this.parameter[1] = false;
		this.parameter[1] = true;
		this.parameterValue[1] = paramString;
	}

	public void setDeterministicConsequences(boolean paramBoolean) {
		this.parameter[2] = paramBoolean;
		this.parameterValue[2] = new Boolean(paramBoolean);
	}

	public void setInstantiate(boolean paramBoolean) {
		this.parameter[3] = paramBoolean;
		this.parameterValue[3] = new Boolean(paramBoolean);
	}

	public void setNumberOfModels(int paramInt) {
		this.parameter[4] = ((paramInt > 0) ? true : false);
		this.parameterValue[4] = new Integer(paramInt);
	}

	public void setMaxint(int paramInt) {
		this.parameter[5] = ((paramInt > 0) ? true : false);
		this.parameterValue[5] = new Integer(paramInt);
	}

	public void setOptimizationOptions(String paramString) {
		if ((paramString == null) || (paramString.length() == 0))
			this.parameter[6] = false;
		this.parameter[6] = true;
		this.parameterValue[6] = parseParameters(paramString);
	}

	public void setFilter(String[] paramArrayOfString) {
		if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
			this.parameter[7] = false;
		this.parameter[7] = true;
		this.parameterValue[7] = paramArrayOfString;
	}

	public void setPFilter(String[] paramArrayOfString) {
		if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
			this.parameter[8] = false;
		this.parameter[8] = true;
		this.parameterValue[8] = paramArrayOfString;
	}

	public void setIncludeFacts(boolean paramBoolean) {
		this.parameter[9] = true;
		this.parameterValue[9] = new Boolean(paramBoolean);
	}

	public void setGenerateAllPossibilyModels(boolean paramBoolean) {
		this.parameter[10] = paramBoolean;
		this.parameterValue[10] = new Boolean(paramBoolean);
	}

	private String[] createInvocationParameters() {
		int i = 0;
		for (int j = 0; j < 12; ++j) {
			if (this.parameter[j] != true)
				continue;
			if ((j == 6) || (j == 7) || (j == 8)) {
				String[] arrayOfString2 = (String[]) this.parameterValue[j];
				i += arrayOfString2.length;
			} else {
				++i;
			}
		}
		String[] arrayOfString1 = this.program.getProgramsPathNames();
		int k = i + 2;
		if (arrayOfString1 != null)
			k += arrayOfString1.length;
		String[] arrayOfString3 = new String[k];
		arrayOfString3[0] = getPath();
		int l = 1;
		for (int i1 = 0; i1 < 12; ++i1) {
			if (this.parameter[i1] != true)
				continue;
			switch (i1) {
			case 0:
				arrayOfString3[l] = ((String) this.parameterValue[i1]);
				++l;
				break;
			case 1:
				arrayOfString3[l] = ("-costbound=" + (String) this.parameterValue[i1]);
				++l;
				break;
			case 2:
				arrayOfString3[l] = "-det";
				++l;
				break;
			case 3:
				arrayOfString3[l] = "-istantiate";
				++l;
				break;
			case 4:
				arrayOfString3[l] = ("-n=" + ((Integer) this.parameterValue[i1]).toString());
				++l;
				break;
			case 5:
				arrayOfString3[l] = ("-N=" + ((Integer) this.parameterValue[i1]).toString());
				++l;
				break;
			case 6:
				String[] arrayOfString4 = (String[]) this.parameterValue[i1];
				for (int i2 = 0;; ++i2) {
					if (i2 >= arrayOfString4.length)
						//break label583;
						break;
					arrayOfString3[l] = arrayOfString4[i2];
					++l;
				}
			case 8:
				String[] arrayOfString5 = (String[]) this.parameterValue[i1];
				for (int i3 = 0;; ++i3) {
					if (i3 >= arrayOfString5.length)
						//break label583;
						break;
					arrayOfString3[l] = ("-pfilter=" + arrayOfString5[i3]);
					++l;
				}
			case 7:
				String[] arrayOfString6 = (String[]) this.parameterValue[i1];
				for (int i4 = 0;; ++i4) {
					if (i4 >= arrayOfString6.length)
						//break label583;
						break;
					arrayOfString3[l] = ("-filter=" + arrayOfString6[i4]);
					++l;
				}
			case 9:
				if (((Boolean) this.parameterValue[i1]).booleanValue() == true)
					arrayOfString3[l] = "-facts";
				else
					arrayOfString3[l] = "-nofacts";
				++l;
				break;
			case 10:
				arrayOfString3[l] = "-wctrace";
				++l;
				break;
			case 11:
				arrayOfString3[l] = "--";
				label583: ++l;
			}
		}
		arrayOfString3[l] = "-silent";
		++l;
		int i1 = 0;
		while (l < k) {
			arrayOfString3[l] = arrayOfString1[i1];
			++i1;
			++l;
		}
		return arrayOfString3;
	}

	private String[] parseParameters(String paramString) {
		StringTokenizer localStringTokenizer = new StringTokenizer(paramString);
		int i = localStringTokenizer.countTokens();
		if (i > 0) {
			String[] arrayOfString = new String[i];
			for (int j = 0; localStringTokenizer.hasMoreTokens(); ++j) {
				String str = localStringTokenizer.nextToken();
				if ((str.length() == 1) || (str.charAt(0) != '-'))
					throw new InvalidParameterException("InvalidParameter");
				arrayOfString[j] = str;
			}
			return arrayOfString;
		}
		throw new InvalidParameterException("Invalid Parameter");
	}
}

/*
 * Location: G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar Qualified Name:
 * DLV.DlvHandler JD-Core Version: 0.5.4
 */