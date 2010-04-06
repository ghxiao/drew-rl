package DLV;

public abstract class OutputDescriptorItem
{
  private OutputDescriptor oDes = null;
  private boolean replace = false;

  public boolean getReplace()
  {
    return this.replace;
  }

  public void setReplace(boolean paramBoolean)
  {
    this.replace = paramBoolean;
  }

  void setOutputDescriptor(OutputDescriptor paramOutputDescriptor)
  {
    this.oDes = paramOutputDescriptor;
  }

  OutputDescriptor getOutputDescriptor()
  {
    return this.oDes;
  }

  public boolean multipleModelAllowed()
  {
    return true;
  }

  public abstract Predicate buildPredicate(String paramString, int paramInt1, Model paramModel, int paramInt2);

  public abstract void flushAndClean();
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.OutputDescriptorItem
 * JD-Core Version:    0.5.4
 */