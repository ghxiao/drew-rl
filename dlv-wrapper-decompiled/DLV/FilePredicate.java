package DLV;

import DLV.io.LiteralFile;
import DLV.io.LiteralFile.LiteralHandler;
import DLV.io.RandomAccessLiteralFile;
import java.io.IOException;
import sun.io.ByteToCharConverter;

public class FilePredicate extends Predicate
{
  public FilePredicate(String paramString1, int paramInt, String paramString2)
    throws IOException
  {
    super(paramString1, paramInt);
    this.impl = new FilePI(RandomAccessLiteralFile.buildLiteralFile(paramString2, ByteToCharConverter.getDefault().getCharacterEncoding(), 512, 8192));
  }

  public FilePredicate(String paramString1, int paramInt1, String paramString2, String paramString3, int paramInt2, int paramInt3)
    throws IOException
  {
    super(paramString1, paramInt1);
    this.impl = new FilePI(RandomAccessLiteralFile.buildLiteralFile(paramString2, paramString3, paramInt2, paramInt3));
  }

  public FilePredicate(String paramString, int paramInt, RandomAccessLiteralFile paramRandomAccessLiteralFile)
    throws IOException
  {
    super(paramString, paramInt);
    this.impl = new FilePI(paramRandomAccessLiteralFile);
  }

  FilePredicate(String paramString, int paramInt, RandomAccessLiteralFile paramRandomAccessLiteralFile, Model paramModel)
    throws IOException
  {
    super(paramString, paramInt, paramModel);
    this.impl = new FilePI(paramRandomAccessLiteralFile);
  }

  public RandomAccessLiteralFile getRandomAccessLiteralFile()
  {
    return ((FilePI)this.impl).getFile();
  }

  public void setRandomAccessLiteralFile(RandomAccessLiteralFile paramRandomAccessLiteralFile)
    throws IOException
  {
    if (paramRandomAccessLiteralFile == null)
      throw new NullPointerException();
    this.impl = new FilePI(paramRandomAccessLiteralFile);
  }

  public void flush()
    throws IOException
  {
    ((FilePI)this.impl).flush();
  }

  class FilePI extends PredicateImplementation
  {
    private LiteralFile rFile = null;
    private int size = 0;
    private int lastPredPos = 0;
    private long lastFilePos = 0L;

    FilePI(RandomAccessLiteralFile arg2)
      throws IOException
    {
      super(FilePredicate.this.arity());
      Object localObject;
      if (localObject == null)
        throw new NullPointerException();
      this.rFile = ((LiteralFile)localObject);
      long l = 0L;
      while (l < this.rFile.size())
      {
        LiteralFile.LiteralHandler localLiteralHandler = this.rFile.getLiteralHandler(l);
        if ((localLiteralHandler.getName().equals(FilePredicate.this.name())) && (localLiteralHandler.getTerms().length == FilePredicate.this.arity()))
          this.size += 1;
        l += 1L;
      }
    }

    RandomAccessLiteralFile getFile()
    {
      return this.rFile;
    }

    void flush()
      throws IOException
    {
      this.rFile.flush();
    }

    void updateImplementation(int paramInt, Object[] paramArrayOfObject, Boolean paramBoolean)
    {
      if ((paramInt < 0) || (paramInt >= size()))
        throw new NoSuchLiteralException();
      String[] arrayOfString = new String[FilePredicate.this.arity()];
      boolean bool = true;
      if (paramBoolean != null)
        bool = paramBoolean.booleanValue();
      if (paramArrayOfObject != null)
      {
        if (FilePredicate.this.getPredicateMetaData() == null)
          for (i = 0; ; ++i)
          {
            if (i >= FilePredicate.this.arity())
              break label148;
            arrayOfString[i] = ConversionType.objectToString(paramArrayOfObject[i], 2);
          }
        for (int i = 0; ; ++i)
        {
          if (i >= FilePredicate.this.arity())
            break label148;
          arrayOfString[i] = ConversionType.objectToString(paramArrayOfObject[i], FilePredicate.this.getPredicateMetaData().getType(i + 1));
        }
      }
      throw new NullPointerException();
      label148: intSet(paramInt, arrayOfString, bool);
    }

    Object getTerm(int paramInt1, int paramInt2)
    {
      if ((paramInt1 < 0) || (paramInt1 >= size()))
        throw new NoSuchLiteralException();
      if ((paramInt2 < 0) || (paramInt2 >= FilePredicate.this.arity()))
        throw new NoSuchTermException();
      LiteralFile.LiteralHandler localLiteralHandler = intGet(paramInt1);
      if (FilePredicate.this.getPredicateMetaData() == null)
        return ConversionType.stringToObject(localLiteralHandler.getTermAt(paramInt2), 2);
      return ConversionType.stringToObject(localLiteralHandler.getTermAt(paramInt2), FilePredicate.this.getPredicateMetaData().getType(paramInt2 + 1));
    }

    boolean getSign(int paramInt)
    {
      if ((paramInt < 0) || (paramInt >= size()))
        throw new NoSuchLiteralException();
      LiteralFile.LiteralHandler localLiteralHandler = intGet(paramInt);
      return localLiteralHandler.getSign();
    }

    void removeFromImplementaion(int paramInt)
    {
      if ((paramInt < 0) || (paramInt >= size()))
        throw new NoSuchLiteralException();
      intRemove(paramInt);
    }

    void add(Object[] paramArrayOfObject, boolean paramBoolean)
    {
      String[] arrayOfString = new String[FilePredicate.this.arity()];
      if (paramArrayOfObject != null)
      {
        if (FilePredicate.this.getPredicateMetaData() == null)
          for (i = 0; ; ++i)
          {
            if (i >= FilePredicate.this.arity())
              break label112;
            arrayOfString[i] = ConversionType.objectToString(paramArrayOfObject[i], 2);
          }
        for (int i = 0; ; ++i)
        {
          if (i >= FilePredicate.this.arity())
            break label112;
          arrayOfString[i] = ConversionType.objectToString(paramArrayOfObject[i], FilePredicate.this.getPredicateMetaData().getType(i + 1));
        }
      }
      throw new NullPointerException();
      label112: intAdd(arrayOfString, paramBoolean);
    }

    int indexOf(Object[] paramArrayOfObject, boolean paramBoolean)
    {
      String[] arrayOfString = new String[FilePredicate.this.arity()];
      if (paramArrayOfObject != null)
      {
        if (FilePredicate.this.getPredicateMetaData() == null)
          for (i = 0; ; ++i)
          {
            if (i >= FilePredicate.this.arity())
              break label112;
            arrayOfString[i] = ConversionType.objectToString(paramArrayOfObject[i], 2);
          }
        for (int i = 0; ; ++i)
        {
          if (i >= FilePredicate.this.arity())
            break label112;
          arrayOfString[i] = ConversionType.objectToString(paramArrayOfObject[i], FilePredicate.this.getPredicateMetaData().getType(i + 1));
        }
      }
      throw new NullPointerException();
      try
      {
        label112: return this.rFile.indexOfLiteral(new LiteralFile.LiteralHandler(0L, FilePredicate.this.name(), arrayOfString, paramBoolean));
      }
      catch (IOException localIOException)
      {
        throw new DLVExceptionUncheked("Error Type: " + localIOException.getClass() + " Message: " + localIOException.getMessage());
      }
    }

    int size()
    {
      return this.size;
    }

    private void intSet(int paramInt, String[] paramArrayOfString, boolean paramBoolean)
    {
      try
      {
        LiteralFile.LiteralHandler localLiteralHandler = intGet(paramInt);
        if (localLiteralHandler != null)
          this.rFile.setLiteralHandler(localLiteralHandler.getLogicalPosition(), new LiteralFile.LiteralHandler(localLiteralHandler.getLogicalPosition(), FilePredicate.this.name(), paramArrayOfString, paramBoolean));
        else
          throw new NoSuchLiteralException("Unexpected literal miss. FilePredicate position: " + paramInt);
      }
      catch (IOException localIOException)
      {
        throw new DLVExceptionUncheked("Error class: " + localIOException.getClass() + " message: " + localIOException.getMessage());
      }
    }

    private void intAdd(String[] paramArrayOfString, boolean paramBoolean)
    {
      try
      {
        this.rFile.addLiteralHandler(new LiteralFile.LiteralHandler(0L, FilePredicate.this.name(), paramArrayOfString, paramBoolean));
      }
      catch (IOException localIOException)
      {
        throw new DLVExceptionUncheked("Error class: " + localIOException.getClass() + " message: " + localIOException.getMessage());
      }
      this.size += 1;
    }

    private void intRemove(int paramInt)
    {
      try
      {
        LiteralFile.LiteralHandler localLiteralHandler = intGet(paramInt);
        if (localLiteralHandler != null)
        {
          this.rFile.removeLiteralHandler(localLiteralHandler.getLogicalPosition());
          this.size -= 1;
          this.lastFilePos = 0L;
          this.lastPredPos = 0;
        }
      }
      catch (IOException localIOException)
      {
        throw new DLVExceptionUncheked("Error class: " + localIOException.getClass() + " message: " + localIOException.getMessage());
      }
    }

    private LiteralFile.LiteralHandler intGet(int paramInt)
    {
      if ((paramInt < 0) || (paramInt >= this.size))
        return null;
      try
      {
        if (this.lastPredPos == paramInt)
          return this.rFile.getLiteralHandler(this.lastFilePos);
        long l = this.lastFilePos;
        int i = this.lastPredPos;
        if (paramInt < this.lastPredPos)
        {
          i = 0;
          l = 0L;
        }
        while (i < this.size)
        {
          LiteralFile.LiteralHandler localLiteralHandler = this.rFile.getLiteralHandler(l);
          if ((localLiteralHandler.getName().equals(FilePredicate.this.name())) && (localLiteralHandler.getTerms().length == FilePredicate.this.arity()))
          {
            if (i == paramInt)
            {
              this.lastFilePos = localLiteralHandler.getLogicalPosition();
              this.lastPredPos = paramInt;
              return localLiteralHandler;
            }
            ++i;
          }
          l += 1L;
        }
        return null;
      }
      catch (IOException localIOException)
      {
        throw new DLVExceptionUncheked("Error class: " + localIOException.getClass() + " message: " + localIOException.getMessage());
      }
    }

    private boolean equalTo(String[] paramArrayOfString, boolean paramBoolean1, Object[] paramArrayOfObject, boolean paramBoolean2)
    {
      if (paramBoolean2 != paramBoolean1)
        return false;
      for (int i = 0; i < paramArrayOfString.length; ++i)
      {
        String str;
        if (FilePredicate.this.getPredicateMetaData() == null)
          str = ConversionType.objectToString(paramArrayOfObject[i], 2);
        else
          str = ConversionType.objectToString(paramArrayOfObject[i], FilePredicate.this.getPredicateMetaData().getType(i + 1));
        if (!str.equals(paramArrayOfString[i]))
          return false;
      }
      return true;
    }
  }
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.FilePredicate
 * JD-Core Version:    0.5.4
 */