package DLV.io;

import java.io.IOException;

public abstract class RandomAccessLiteralFile
{
  public static RandomAccessLiteralFile buildLiteralFile(String paramString1, String paramString2, int paramInt1, int paramInt2)
    throws IOException
  {
    return new LiteralFile(paramString1, paramString2, paramInt1, paramInt2);
  }

  public abstract void close()
    throws IOException;

  public abstract void flush()
    throws IOException;

  public abstract String getName();
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.io.RandomAccessLiteralFile
 * JD-Core Version:    0.5.4
 */