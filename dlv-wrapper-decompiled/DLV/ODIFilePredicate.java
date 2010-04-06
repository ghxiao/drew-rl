package DLV;

import DLV.io.LiteralFile;
import DLV.io.RandomAccessLiteralFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import sun.io.ByteToCharConverter;

public class ODIFilePredicate extends OutputDescriptorItem
{
  private RandomAccessLiteralFile rFile = null;
  private String fileName = null;
  private String encoding = ByteToCharConverter.getDefault().getCharacterEncoding();
  private int cacheSize = 512;
  private int lowLevelBufferSize = 0;

  public ODIFilePredicate(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    this.lowLevelBufferSize = paramInt2;
    this.cacheSize = paramInt1;
    this.encoding = paramString2;
    this.fileName = paramString1;
  }

  public ODIFilePredicate(String paramString1, String paramString2, int paramInt)
  {
    this.cacheSize = paramInt;
    this.encoding = paramString2;
    this.fileName = paramString1;
  }

  public ODIFilePredicate(String paramString1, String paramString2)
  {
    this.encoding = paramString2;
    this.fileName = paramString1;
  }

  public ODIFilePredicate(String paramString)
  {
    this.fileName = paramString;
  }

  public ODIFilePredicate(RandomAccessLiteralFile paramRandomAccessLiteralFile)
  {
    this.rFile = paramRandomAccessLiteralFile;
  }

  public boolean multipleModelAllowed()
  {
    return this.fileName != null;
  }

  public Predicate buildPredicate(String paramString, int paramInt1, Model paramModel, int paramInt2)
  {
    if (this.fileName == null)
    {
      if (paramInt2 > 1)
        throw new DLVExceptionUncheked("multiple model not allowed");
      try
      {
        return new FilePredicate(paramString, paramInt1, this.rFile, paramModel);
      }
      catch (IOException localIOException1)
      {
        throw new DLVExceptionUncheked("I cannot build predicate name: " + paramString + " arity: " + paramInt1 + " - An error occurred redirecting output type: " + localIOException1.getClass() + " message: " + localIOException1.getMessage());
      }
    }
    try
    {
      String str = this.fileName;
      if (paramInt2 > 1)
        str = str + new String(new StringBuffer().append(".").append(paramInt2).toString());
      Object localObject = (RandomAccessLiteralFile)getOutputDescriptor().getDataStore().get(str);
      if (localObject == null)
      {
        localObject = new LiteralFile(str, this.encoding, this.cacheSize, this.lowLevelBufferSize);
        getOutputDescriptor().getDataStore().put(str, localObject);
      }
      return new FilePredicate(paramString, paramInt1, (RandomAccessLiteralFile)localObject, paramModel);
    }
    catch (IOException localIOException2)
    {
      throw new DLVExceptionUncheked("An error occurred redirecting output type: " + localIOException2.getClass() + " message: " + localIOException2.getMessage());
    }
  }

  public void flushAndClean()
  {
    try
    {
      if (this.fileName == null)
      {
        if (this.rFile != null)
          this.rFile.flush();
      }
      else
      {
        Iterator localIterator = getOutputDescriptor().getDataStore().keySet().iterator();
        ArrayList localArrayList = new ArrayList();
        while (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          Object localObject = getOutputDescriptor().getDataStore().get(str);
          if ((localObject == null) || (!localObject instanceof RandomAccessLiteralFile))
            continue;
          ((RandomAccessLiteralFile)localObject).flush();
          localArrayList.add(str);
        }
        localIterator = localArrayList.iterator();
        while (localIterator.hasNext())
          getOutputDescriptor().getDataStore().remove(localIterator.next());
      }
    }
    catch (IOException localIOException)
    {
      throw new DLVExceptionUncheked("An error occurred redirecting output type: " + localIOException.getClass() + " message: " + localIOException.getMessage());
    }
  }
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.ODIFilePredicate
 * JD-Core Version:    0.5.4
 */