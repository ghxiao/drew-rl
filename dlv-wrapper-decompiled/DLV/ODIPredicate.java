package DLV;

public class ODIPredicate extends OutputDescriptorItem
{
  public Predicate buildPredicate(String paramString, int paramInt1, Model paramModel, int paramInt2)
  {
    return new Predicate(paramString, paramInt1, paramModel);
  }

  public void flushAndClean()
  {
  }
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.ODIPredicate
 * JD-Core Version:    0.5.4
 */