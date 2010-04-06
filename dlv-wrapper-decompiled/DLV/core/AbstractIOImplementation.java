package DLV.core;

import DLV.DLVInvocationException;

public abstract interface AbstractIOImplementation
{
  public abstract void run(DlvIOHandler paramDlvIOHandler)
    throws DLVInvocationException;

  public abstract void kill(DlvIOHandler paramDlvIOHandler)
    throws DLVInvocationException;

  public abstract void freeze(DlvIOHandler paramDlvIOHandler)
    throws DLVInvocationException;

  public abstract void deFreeze(DlvIOHandler paramDlvIOHandler)
    throws DLVInvocationException;

  public abstract String getVersion()
    throws DLVInvocationException;

  public abstract String getWarnings()
    throws DLVInvocationException;

  public abstract void reset();
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.core.AbstractIOImplementation
 * JD-Core Version:    0.5.4
 */