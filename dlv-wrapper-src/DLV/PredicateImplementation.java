package DLV;

import java.util.HashMap;

abstract class PredicateImplementation
{
  private HashMap modifiedTable = new HashMap();
  private int arity;

  PredicateImplementation(int paramInt)
  {
    this.arity = paramInt;
  }

  void update(int paramInt)
  {
    Integer localInteger = new Integer(paramInt);
    LiteralHandler localLiteralHandler = (LiteralHandler)this.modifiedTable.get(localInteger);
    if (localLiteralHandler == null)
      return;
    updateImplementation(paramInt, localLiteralHandler.term, localLiteralHandler.sign);
    this.modifiedTable.remove(localInteger);
  }

  void setSign(int paramInt, boolean paramBoolean)
  {
    Integer localInteger = new Integer(paramInt);
    LiteralHandler localLiteralHandler = (LiteralHandler)this.modifiedTable.get(localInteger);
    if (localLiteralHandler != null)
    {
      localLiteralHandler.sign = new Boolean(paramBoolean);
    }
    else
    {
      localLiteralHandler = new LiteralHandler(null, new Boolean(paramBoolean));
      this.modifiedTable.put(localInteger, localLiteralHandler);
    }
  }

  void setTerm(int paramInt1, int paramInt2, Object paramObject)
  {
    Integer localInteger = new Integer(paramInt1);
    LiteralHandler localLiteralHandler = (LiteralHandler)this.modifiedTable.get(localInteger);
    if (localLiteralHandler != null)
    {
      if (localLiteralHandler.term == null)
        localLiteralHandler.term = new Object[this.arity];
      localLiteralHandler.term[paramInt2] = paramObject;
    }
    else
    {
      localLiteralHandler = new LiteralHandler(new Object[this.arity], null);
      localLiteralHandler.term[paramInt2] = paramObject;
      this.modifiedTable.put(localInteger, localLiteralHandler);
    }
  }

  void remove(int paramInt)
  {
    removeFromImplementaion(paramInt);
    this.modifiedTable.remove(new Integer(paramInt));
  }

  abstract void updateImplementation(int paramInt, Object[] paramArrayOfObject, Boolean paramBoolean);

  abstract Object getTerm(int paramInt1, int paramInt2);

  abstract boolean getSign(int paramInt);

  abstract int size();

  abstract void removeFromImplementaion(int paramInt);

  abstract int indexOf(Object[] paramArrayOfObject, boolean paramBoolean);

  abstract void add(Object[] paramArrayOfObject, boolean paramBoolean);

  boolean trueNegationSupported()
  {
    return true;
  }

  void setTerms(int paramInt, Object[] paramArrayOfObject)
  {
    for (int i = 0; i < paramArrayOfObject.length; ++i)
      setTerm(paramInt, i, paramArrayOfObject[i]);
  }

  Object[] getTerms(int paramInt, Object[] paramArrayOfObject)
  {
    for (int i = 0; i < paramArrayOfObject.length; ++i)
      paramArrayOfObject[i] = getTerm(paramInt, i);
    return paramArrayOfObject;
  }

  private class LiteralHandler
  {
    Boolean sign = null;
    Object[] term = null;

    LiteralHandler(Object[] paramBoolean, Boolean arg3)
    {
      Object localObject;
      this.sign = localObject;
      this.term = paramBoolean;
    }
  }
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.PredicateImplementation
 * JD-Core Version:    0.5.4
 */