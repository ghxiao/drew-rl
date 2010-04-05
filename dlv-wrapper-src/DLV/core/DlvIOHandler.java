package DLV.core;

import DLV.DLVInvocationException;
import DLV.Model;
import DLV.OutputDescriptor;
import DLV.Program;

public abstract interface DlvIOHandler
{
  public abstract String[] getInvocationParameters();

  public abstract byte getMethod();

  public abstract Program getProgram();

  public abstract OutputDescriptor getOutputDescriptor();

  public abstract void addModel(Model paramModel);

  public abstract void run(byte paramByte)
    throws DLVInvocationException;

  public abstract void signalDlvEnd();

  public abstract void signalDlvKill();

  public abstract void signalDlvFreeze();

  public abstract void signalDlvDeFreeze();

  public abstract void signalError(Throwable paramThrowable);

  public abstract String getEncoding();
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.core.DlvIOHandler
 * JD-Core Version:    0.5.4
 */