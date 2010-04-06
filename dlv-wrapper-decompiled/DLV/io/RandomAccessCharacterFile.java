package DLV.io;

import java.io.BufferedReader;
import java.io.CharConversionException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import sun.io.ByteToCharConverter;
import sun.io.CharToByteConverter;
import sun.io.ConversionBufferFullException;

public class RandomAccessCharacterFile
{
  private static final int defaultByteBufferSize = 8192;
  private byte[] bb = new byte[8192];
  private char[] cb = null;
  private RandomAccessFile rFile = null;
  private String fileName = null;
  private int bufferSize = 0;
  private long fileOffset = 0L;
  private long pointer = 0L;
  private ByteToCharConverter btc = null;
  private CharToByteConverter ctb = null;
  private int delta = 1;
  private int byteOrderMarkOffset = 0;
  boolean bufferModified = false;

  public RandomAccessCharacterFile(String paramString1, String paramString2, int paramInt)
    throws IOException
  {
    this.rFile = new RandomAccessFile(paramString1, "rw");
    this.fileName = paramString1;
    if (paramInt < 8192)
      paramInt = 8192;
    this.cb = new char[paramInt];
    this.btc = ByteToCharConverter.getConverter(paramString2);
    this.ctb = CharToByteConverter.getConverter(paramString2);
    if (paramString2.equals("Unicode"))
    {
      this.delta = 2;
      this.byteOrderMarkOffset = 2;
    }
    else if (paramString2.equals("UnicodeBig"))
    {
      this.delta = 4;
      this.byteOrderMarkOffset = 4;
    }
    else
    {
      this.delta = this.ctb.getMaxBytesPerChar();
    }
    if ((paramString2.equals("Unicode")) || (paramString2.equals("UnicodeBig")))
    {
      byte[] arrayOfByte1 = new byte[12];
      byte[] arrayOfByte2 = new byte[12];
      char[] arrayOfChar = { 'a', 'b', 'c' };
      this.rFile.seek(0L);
      int i = this.rFile.read(arrayOfByte1);
      try
      {
        int j = this.ctb.convertAny(arrayOfChar, 0, 1, arrayOfByte2, 0, 12);
        if ((arrayOfByte1[0] != arrayOfByte2[0]) && (arrayOfByte1[1] != arrayOfByte2[1]) && (i != -1))
          throw new IOException("Byte order mark invalid. please convert using allignMark()");
        this.btc.convert(arrayOfByte2, 0, j, arrayOfChar, 0, 3);
      }
      catch (ConversionBufferFullException localConversionBufferFullException)
      {
        throw new IOException("Converter Error Type:" + localConversionBufferFullException.getClass() + " Message:" + localConversionBufferFullException.getMessage());
      }
      this.rFile.seek(0L);
      this.rFile.write(arrayOfByte2, 0, this.byteOrderMarkOffset);
      this.rFile.seek(0L);
    }
    loadBuffer();
  }

  public String getName()
  {
    return this.fileName;
  }

  public int getBufferSize()
  {
    return this.cb.length;
  }

  public static void allignMark(String paramString1, String paramString2, String paramString3)
    throws IOException
  {
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(paramString1), paramString3));
    PrintWriter localPrintWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(paramString2), paramString3));
    while ((str = localBufferedReader.readLine()) != null)
    {
      String str;
      localPrintWriter.println(str);
    }
    localPrintWriter.close();
    localBufferedReader.close();
  }

  public RandomAccessCharacterFile(String paramString1, String paramString2)
    throws IOException
  {
    this(paramString1, paramString2, 8192);
  }

  public String getEncoding()
  {
    if (this.btc != null)
      return this.btc.getCharacterEncoding();
    throw new NullPointerException("Internal error: byte to char converter null");
  }

  public void close()
    throws IOException
  {
    flush();
    this.rFile.close();
  }

  public void writeString(String paramString)
    throws IOException
  {
    int i = paramString.length();
    if (i == 0)
      return;
    for (int j = 0; i > j; ++j)
      write(paramString.charAt(j));
  }

  public long getFilePointer()
    throws IOException
  {
    return this.pointer;
  }

  public int read()
    throws IOException
  {
    if ((this.pointer >= this.fileOffset) && (this.pointer < this.fileOffset + this.cb.length))
    {
      int i = (int)(this.pointer - this.fileOffset);
      if (i < this.bufferSize)
      {
        this.pointer += 1L;
        return this.cb[i];
      }
      return -1;
    }
    flush();
    long l = this.fileOffset;
    this.fileOffset = (this.pointer / this.cb.length * this.cb.length);
    if (loadBuffer())
    {
      int j = (int)(this.pointer - this.fileOffset);
      if (j < this.bufferSize)
      {
        this.pointer += 1L;
        return this.cb[j];
      }
      return -1;
    }
    if (l > this.fileOffset)
      throw new IOException("an error occurrer reading a buffer");
    return -1;
  }

  public void write(char paramChar)
    throws IOException
  {
    int i;
    if ((this.pointer >= this.fileOffset) && (this.pointer < this.fileOffset + this.cb.length))
    {
      i = (int)(this.pointer - this.fileOffset);
      if (i <= this.bufferSize)
      {
        this.cb[i] = paramChar;
        if (i == this.bufferSize)
          this.bufferSize += 1;
        this.bufferModified = true;
        this.pointer += 1L;
        return;
      }
      throw new IOException("cannot write over the eof");
    }
    flush();
    this.fileOffset = (this.pointer / this.cb.length * this.cb.length);
    if (loadBuffer())
    {
      i = (int)(this.pointer - this.fileOffset);
      if (i <= this.bufferSize)
      {
        this.cb[i] = paramChar;
        if (i == this.bufferSize)
          this.bufferSize += 1;
        this.bufferModified = true;
        this.pointer += 1L;
        return;
      }
      throw new IOException("cannot write over the eof");
    }
    if ((this.pointer == this.fileOffset) && (this.fileOffset == getFileLength()))
    {
      this.cb[0] = paramChar;
      this.bufferSize = 1;
      this.bufferModified = true;
      this.pointer += 1L;
    }
    else
    {
      throw new IOException("an error occurred loading a buffer or cannot write over the eof");
    }
  }

  public void seek(long paramLong)
    throws IOException
  {
    if (paramLong < 0L)
      throw new IllegalArgumentException("argument must be positive");
    this.pointer = paramLong;
  }

  private boolean loadBuffer()
    throws IOException
  {
    this.rFile.seek(this.fileOffset * this.delta + this.byteOrderMarkOffset);
    int i = readAndConvert(0, this.cb.length);
    if (i == -1)
    {
      this.bufferSize = 0;
      return false;
    }
    this.bufferSize = i;
    return true;
  }

  public void flush()
    throws IOException
  {
    if (!this.bufferModified)
      return;
    this.rFile.seek(this.fileOffset * this.delta + this.byteOrderMarkOffset);
    convertAndWrite(0, this.bufferSize);
    this.bufferModified = false;
  }

  public long getLength()
    throws IOException
  {
    long l = 0L;
    if ((getEncoding().equals("UnicodeBig")) || (getEncoding().equals("Unicode")))
      l = (this.rFile.length() - this.byteOrderMarkOffset) / this.delta;
    else
      l = this.rFile.length() / this.delta;
    if (l < this.fileOffset + this.bufferSize)
      return this.fileOffset + this.bufferSize;
    return l;
  }

  private long getFileLength()
    throws IOException
  {
    if ((getEncoding().equals("UnicodeBig")) || (getEncoding().equals("Unicode")))
      return (this.rFile.length() - this.byteOrderMarkOffset) / this.delta;
    return this.rFile.length() / this.delta;
  }

  public void setLength(long paramLong)
    throws IOException
  {
    flush();
    long l = this.byteOrderMarkOffset + paramLong / this.delta;
    this.rFile.setLength(l);
  }

  private void convertAndWrite(int paramInt1, int paramInt2)
    throws IOException
  {
    int i = paramInt2 + paramInt1;
    int j = 0;
    while (paramInt1 < i)
      try
      {
        int k = this.ctb.convertAny(this.cb, paramInt1, paramInt2, this.bb, 0, this.bb.length);
        paramInt1 = i;
        this.rFile.write(this.bb, 0, k);
        j = 1;
      }
      catch (ConversionBufferFullException localConversionBufferFullException)
      {
        int l = this.ctb.nextCharIndex();
        if ((l == paramInt1) && (j != 0))
          throw new CharConversionException("Output buffer too small");
        paramInt1 = this.ctb.nextCharIndex();
        this.rFile.write(this.bb, 0, this.ctb.nextByteIndex());
        j = 1;
      }
  }

  private int readAndConvert(int paramInt1, int paramInt2)
    throws IOException
  {
    int i = paramInt2 + paramInt1;
    int j = 0;
    while (paramInt1 < i)
    {
      int k = this.rFile.read(this.bb);
      if (k == -1)
        break;
      int l = 0;
      try
      {
        l = this.btc.convert(this.bb, 0, k, this.cb, paramInt1, paramInt2);
      }
      catch (ConversionBufferFullException localConversionBufferFullException)
      {
        l = this.btc.nextCharIndex() - paramInt1;
      }
      paramInt1 += l;
      j += l;
    }
    if (j == 0)
      return -1;
    return j;
  }
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.io.RandomAccessCharacterFile
 * JD-Core Version:    0.5.4
 */