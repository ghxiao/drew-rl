package DLV.io;

import DLV.InvalidParameterException;
import DLV.ParserException;
import DLV.Predicate.Literal;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

public class LiteralFile extends RandomAccessLiteralFile
{
  private ClockBuffer buffer = new ClockBuffer(200);
  private long fileSize = 0L;
  private RandomAccessCharacterFile rFile = null;
  private InputTokenizer tok = null;
  private StringBuffer currentString = new StringBuffer();
  private boolean firstArg = true;
  private ArrayList arg = null;
  private boolean positive = true;
  private String nomePredicato = null;
  private StringBuffer token = null;
  private byte status = 0;
  private byte oldStatus = 0;
  private static final byte START = 0;
  private static final byte END = 9;
  private static final byte PREDICATE = 1;
  private static final byte FIND_ARGUMENT = 2;
  private static final byte END_PREDICATE = 3;
  private static final byte FIND_OTHER_ARGUMENT = 4;
  private static final byte COMPUTE_STRING = 5;
  private static final byte ERROR = 6;
  private static final byte NEGATIVE_PEDICATE = 7;
  private static final byte COMMENT = 8;
  private static final byte EAT_DOT = 10;

  public LiteralFile(String paramString1, String paramString2, int paramInt1, int paramInt2)
    throws IOException
  {
    if ((paramString1 == null) || (paramString2 == null))
      throw new NullPointerException();
    if (paramInt1 < 1)
      paramInt1 = 1;
    this.buffer = new ClockBuffer(paramInt1);
    this.rFile = new RandomAccessCharacterFile(paramString1, paramString2, paramInt2);
    this.fileSize = computeSize();
    this.tok = new InputTokenizer(this.rFile);
  }

  public String getName()
  {
    return this.rFile.getName();
  }

  public synchronized long size()
  {
    return this.fileSize;
  }

  public synchronized void close()
    throws IOException
  {
    flush();
    this.rFile.close();
  }

  public synchronized LiteralHandler getLiteralHandler(long paramLong)
    throws IOException
  {
    if ((paramLong >= this.fileSize) || (paramLong < 0L))
      throw new IllegalArgumentException();
    LiteralHandler localLiteralHandler = this.buffer.getLiteral(paramLong);
    if (localLiteralHandler != null)
      return localLiteralHandler;
    seek(paramLong);
    localLiteralHandler = parseLiteral(paramLong);
    this.buffer.putLiteral(localLiteralHandler);
    return localLiteralHandler;
  }

  public synchronized void setLiteralHandler(long paramLong, LiteralHandler paramLiteralHandler)
    throws IOException
  {
    if (paramLiteralHandler == null)
      throw new NullPointerException("LiteralHandler cannot be null");
    if ((paramLong >= this.fileSize) || (paramLong < 0L))
      throw new IllegalArgumentException();
    LiteralHandler localLiteralHandler = getLiteralHandler(paramLong);
    if ((localLiteralHandler == paramLiteralHandler) || (localLiteralHandler.externalEquals(paramLiteralHandler)))
    {
      localLiteralHandler.accessed = true;
    }
    else
    {
      localLiteralHandler.name = paramLiteralHandler.name;
      localLiteralHandler.sign = paramLiteralHandler.sign;
      localLiteralHandler.terms = ((String[])paramLiteralHandler.terms.clone());
      localLiteralHandler.modified = true;
      localLiteralHandler.accessed = true;
    }
  }

  public synchronized void addLiteralHandler(LiteralHandler paramLiteralHandler)
    throws IOException
  {
    if (paramLiteralHandler == null)
      throw new NullPointerException("LiteralHandler cannot be null");
    this.rFile.seek(this.rFile.getLength());
    if (this.rFile.getFilePointer() != 0L)
      this.rFile.write('\n');
    paramLiteralHandler.phPosition = this.rFile.getLength();
    paramLiteralHandler.writeLiteralHandler(this.rFile);
    paramLiteralHandler.lgPosition = this.fileSize;
    this.buffer.putLiteral(paramLiteralHandler);
    this.fileSize += 1L;
  }

  public synchronized void removeLiteralHandler(long paramLong)
    throws IOException
  {
    if ((paramLong >= this.fileSize) || (paramLong < 0L))
      throw new IllegalArgumentException();
    LiteralHandler localLiteralHandler = this.buffer.getLiteral(paramLong);
    if (localLiteralHandler != null)
    {
      this.rFile.seek(localLiteralHandler.phPosition);
      removeFromFile();
      this.buffer.removeAndUpdateLiteralPositions(localLiteralHandler);
    }
    else
    {
      seek(paramLong);
      removeFromFile();
    }
  }

  public synchronized int indexOfLiteral(LiteralHandler paramLiteralHandler)
    throws IOException
  {
    if (paramLiteralHandler == null)
      throw new NullPointerException("LiteralHandler cannot be null");
    LiteralHandler localLiteralHandler = null;
    this.rFile.seek(0L);
    long l = 0L;
    int i = -1;
    while (l < this.fileSize)
    {
      localLiteralHandler = parseLiteral(l);
      if ((localLiteralHandler.getName().equals(paramLiteralHandler.getName())) && (localLiteralHandler.getTerms().length == paramLiteralHandler.getTerms().length))
      {
        ++i;
        if (localLiteralHandler.externalEquals(paramLiteralHandler))
          return i;
      }
      l += 1L;
    }
    return -1;
  }

  public synchronized LiteralHandler indexOfLiteralHandler(LiteralHandler paramLiteralHandler)
    throws IOException
  {
    if (paramLiteralHandler == null)
      throw new NullPointerException("LiteralHandler cannot be null");
    LiteralHandler localLiteralHandler = this.buffer.findInBuffer(paramLiteralHandler);
    if (localLiteralHandler != null)
      return localLiteralHandler;
    this.rFile.seek(0L);
    long l = 0L;
    while (l < this.fileSize)
    {
      localLiteralHandler = parseLiteral(l);
      if (localLiteralHandler.externalEquals(paramLiteralHandler))
        return localLiteralHandler;
      l += 1L;
    }
    return null;
  }

  public synchronized void flush()
    throws IOException
  {
    new ExtFlusher(2 * this.rFile.getBufferSize()).flush();
  }

  private void removeFromFile()
    throws IOException
  {
    int i = 0;
    int j = 0;
    int k = 0;
    int l;
    do
    {
      l = this.rFile.read();
      if ((k == 0) && (i == 0) && (l == 37))
        i = 1;
      if (i == 0)
      {
        this.rFile.seek(this.rFile.getFilePointer() - 1L);
        this.rFile.write(' ');
        if ((k == 0) && (l == 46))
          j = 1;
        if (l == 34)
          k = (k == 0) ? 1 : 0;
      }
      if ((((i == 0) || (l != 10))) && (l != 13))
        continue;
      i = 0;
    }
    while ((j == 0) && (l != -1));
    if ((l != -1) || (j != 0))
      return;
    throw new ParserException("unexpected end of file");
  }

  private void seek(long paramLong)
    throws IOException
  {
    if (paramLong == 0L)
    {
      this.rFile.seek(0L);
      return;
    }
    LiteralHandler localLiteralHandler = this.buffer.getNearInBuffer(paramLong);
    long l = paramLong;
    if (localLiteralHandler != null)
    {
      this.rFile.seek(localLiteralHandler.phPosition);
      l -= localLiteralHandler.lgPosition;
    }
    else
    {
      this.rFile.seek(0L);
    }
    jumpLiterals(l);
  }

  private void jumpLiterals(long paramLong)
    throws IOException
  {
    int i = 0;
    int j = 0;
    long l = paramLong;
    while (l != 0L)
    {
      int k = this.rFile.read();
      if (k == -1)
        throw new IOException("Unexpected EOF");
      if ((k == 37) && (j == 0))
        i = 1;
      if ((i != 0) && (((k == 10) || (k == 13))))
        i = 0;
      if ((k == 34) && (i == 0) && (j == 0))
        j = 1;
      else if ((j != 0) && (k == 34))
        j = 0;
      if ((i != 0) || (j != 0) || (k != 46))
        continue;
      l -= 1L;
    }
  }

  private LiteralHandler parseLiteral(long paramLong)
    throws IOException
  {
    LiteralHandler localLiteralHandler = null;
    long l = 0L;
    this.status = 0;
    try
    {
      while ((this.tok.hasMoreTokens()) && (this.status != 9) && (this.status != 6))
      {
        this.token = this.tok.nextToken();
        switch (this.status)
        {
        case 0:
          if (this.token.charAt(0) == '-')
          {
            l = this.tok.getStartPosition();
            changeStatus(7);
          }
          if (this.token.charAt(0) == '%')
          {
            changeStatus(8);
          }
          else if (isToken(this.token))
          {
            l = this.tok.getStartPosition();
            selectTuple(this.token);
            changeStatus(1);
          }
          break;
        case 7:
          this.positive = false;
          if (this.token.charAt(0) == '%')
          {
            changeStatus(8);
          }
          else if (isToken(this.token))
          {
            selectTuple(this.token);
            changeStatus(1);
          }
          else
          {
            changeStatus(6);
          }
          break;
        case 1:
          if (this.token.charAt(0) == '%')
            changeStatus(8);
          else if (this.token.charAt(0) != ' ')
            if (this.token.charAt(0) == '(')
            {
              changeStatus(2);
            }
            else if (this.token.charAt(0) == '.')
            {
              changeStatus(9);
              insertTuple(this.token);
            }
            else
            {
              changeStatus(6);
            }
          break;
        case 2:
          if (this.token.charAt(0) == '%')
            changeStatus(8);
          else if (this.token.charAt(0) != ' ')
            if (this.token.charAt(0) == '"')
            {
              changeStatus(5);
              insertNewString(true, this.token);
            }
            else if (this.token.charAt(0) == ')')
            {
              changeStatus(10);
              insertTuple(this.token);
            }
            else if (isToken(this.token))
            {
              changeStatus(3);
              insertFirstArgument(this.token);
            }
            else
            {
              changeStatus(6);
            }
          break;
        case 3:
          if (this.token.charAt(0) == '%')
            changeStatus(8);
          else if (this.token.charAt(0) != ' ')
            if (this.token.charAt(0) == ',')
            {
              changeStatus(4);
            }
            else if (this.token.charAt(0) == ')')
            {
              changeStatus(10);
              insertTuple(this.token);
            }
            else
            {
              changeStatus(6);
            }
          break;
        case 4:
          if (this.token.charAt(0) == '%')
            changeStatus(8);
          else if (this.token.charAt(0) != ' ')
            if (this.token.charAt(0) == '"')
            {
              changeStatus(5);
              insertNewString(false, this.token);
            }
            else if (isToken(this.token))
            {
              changeStatus(3);
              insertArgument(this.token);
            }
            else
            {
              this.status = 6;
            }
          break;
        case 5:
          insertString(this.token);
          if (this.token.charAt(0) == '"')
          {
            changeStatus(3);
            if (this.firstArg)
              insertFirstArgument(this.currentString);
            else
              insertArgument(this.currentString);
          }
          break;
        case 8:
          if ((this.token.charAt(0) == '\n') || (this.token.charAt(0) == '\r'))
            changeStatus(this.oldStatus);
          break;
        case 10:
          if (this.token.charAt(0) == '.')
            changeStatus(9);
        case 6:
        case 9:
        }
      }
      switch (this.status)
      {
      case 8:
        if (this.nomePredicato == null)
          return null;
        throwError();
      case 0:
        return null;
      case 9:
        if (this.arg == null)
          this.arg = new ArrayList();
        localLiteralHandler = new LiteralHandler(paramLong, this.nomePredicato, arrayListToStringArray(this.arg), this.positive);
        localLiteralHandler.phPosition = l;
        this.positive = true;
        this.arg = null;
        break;
      default:
        throwError();
      }
      return localLiteralHandler;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
      throw new ParserException("ParserError Type: " + localThrowable.getClass() + " message: " + localThrowable.getMessage());
    }
  }

  private static String[] arrayListToStringArray(ArrayList paramArrayList)
  {
    if (paramArrayList == null)
      return null;
    int i = paramArrayList.size();
    String[] arrayOfString = new String[i];
    for (int j = 0; j < i; ++j)
      arrayOfString[j] = ((String)paramArrayList.get(j));
    return arrayOfString;
  }

  private void changeStatus(byte paramByte)
  {
    this.oldStatus = this.status;
    this.status = paramByte;
  }

  private boolean equals(StringBuffer paramStringBuffer1, StringBuffer paramStringBuffer2)
  {
    if (paramStringBuffer1 == paramStringBuffer2)
      return true;
    int i = paramStringBuffer1.length();
    int j = paramStringBuffer2.length();
    if (i != j)
      return false;
    i = 0;
    int k = 0;
    while ((i < j) && (k == 0))
    {
      if (paramStringBuffer1.charAt(i) != paramStringBuffer2.charAt(i))
        k = 1;
      ++i;
    }
    return k == 0;
  }

  private boolean isToken(StringBuffer paramStringBuffer)
  {
    int i = paramStringBuffer.charAt(0);
    if ((i == 45) || (i == 58) || (i == 32) || (i == 40) || (i == 41) || (i == 44) || (i == 46) || (i == 126) || (i == 34) || (i == 125) || (i == 10) || (i == 13) || (i == 37))
      return false;
    return !paramStringBuffer.toString().equals("not");
  }

  private void selectTuple(StringBuffer paramStringBuffer)
  {
    this.nomePredicato = paramStringBuffer.toString();
  }

  private void throwError()
  {
    throw new ParserException("An error occurred parsing a literal.");
  }

  private void insertTuple(StringBuffer paramStringBuffer)
  {
  }

  private void insertFirstArgument(StringBuffer paramStringBuffer)
  {
    this.arg = new ArrayList();
    this.arg.add(paramStringBuffer.toString());
  }

  private void insertArgument(StringBuffer paramStringBuffer)
  {
    this.arg.add(paramStringBuffer.toString());
  }

  private void insertNewString(boolean paramBoolean, StringBuffer paramStringBuffer)
  {
    this.firstArg = paramBoolean;
    this.currentString.delete(0, this.currentString.length());
    this.currentString.append(paramStringBuffer.toString());
  }

  private void insertString(StringBuffer paramStringBuffer)
  {
    this.currentString.append(paramStringBuffer.toString());
  }

  private long computeSize()
    throws IOException
  {
    this.rFile.seek(0L);
    long l1 = 0L;
    long l2 = 0L;
    int i = this.rFile.read();
    int j = 0;
    int k = 0;
    while (i != -1)
    {
      if ((k == 0) && (i == 37))
        j = 1;
      if ((k == 0) && (j == 0) && (i == 46))
        l1 += 1L;
      if (((k == 0) && (i == 10)) || (i == 13))
        j = 0;
      if ((j == 0) && (i == 34))
        k = (k == 0) ? 1 : 0;
      i = this.rFile.read();
    }
    this.rFile.seek(0L);
    return l1;
  }

  public static void main(String[] paramArrayOfString)
  {
    try
    {
      LiteralFile localLiteralFile = new LiteralFile("/Users/ciccio/Desktop/cazzo.dl", "MacRoman", 5, 1024);
      for (int i = 0; i < 100; ++i)
        localLiteralFile.addLiteralHandler(new LiteralHandler(i, "ciccio", new String[] { "a", "" + i }, true));
      localLiteralFile.flush();
      for (int j = 50; j < 90; ++j)
        localLiteralFile.setLiteralHandler(j, new LiteralHandler(j, "ciccio", new String[] { "an", "" + j }, true));
      localLiteralFile.flush();
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }

  class IntegerBuffer
  {
    private ArrayList buffer = null;
    private long insertCount = 0L;

    public IntegerBuffer()
    {
      this.buffer = new ArrayList();
    }

    public IntegerBuffer(int arg2)
    {
      int i;
      this.buffer = new ArrayList(i);
    }

    public long getInsertCount()
    {
      return this.insertCount;
    }

    public void put(int paramInt)
    {
      this.buffer.add(this.buffer.size(), new Integer(paramInt));
      this.insertCount += 1L;
    }

    public void put(Integer paramInteger)
    {
      if (paramInteger == null)
        throw new NullPointerException();
      this.buffer.add(paramInteger);
      this.insertCount += 1L;
    }

    public int get()
    {
      if (this.buffer.size() == 0)
        return -1;
      Integer localInteger = (Integer)this.buffer.get(0);
      this.buffer.remove(0);
      return localInteger.intValue();
    }

    public Integer getInteger()
    {
      if (this.buffer.size() == 0)
        throw new RuntimeException("buffer empty");
      Integer localInteger = (Integer)this.buffer.get(0);
      this.buffer.remove(0);
      return localInteger;
    }

    public int size()
    {
      return this.buffer.size();
    }

    public void clear()
    {
      this.buffer.clear();
    }

    public void getAndPutIn(IntegerBuffer paramIntegerBuffer)
    {
      while (size() > 0)
        paramIntegerBuffer.put(getInteger());
    }
  }

  class ExtFlusher
  {
    private static final byte NON_LITERAL = 0;
    private static final byte COMMENT_NL = 1;
    private static final byte LITERAL = 2;
    private static final byte COMMENT_LI = 3;
    private static final byte QUOTE = 4;
    private static final byte LF_CR_ENCODING = 5;
    private long literalCount = -1L;
    private long readPointer = 0L;
    private long writePointer = 0L;
    private LiteralFile.IntegerBuffer outputBuffer = null;
    private LiteralFile.IntegerBuffer tempBuffer = new LiteralFile.IntegerBuffer(LiteralFile.this);
    private byte status = 0;
    private boolean literalRead = false;

    public ExtFlusher(int arg2)
    {
      int i;
      this.outputBuffer = new LiteralFile.IntegerBuffer(LiteralFile.this, i);
    }

    public void flush()
      throws IOException
    {
      int i = -2;
      LiteralFile.this.rFile.seek(0L);
      while (i != -1)
      {
        i = LiteralFile.this.rFile.read();
        this.readPointer += 1L;
        if (i != -1)
          write((char)i, false);
        else
          write((char)i, true);
        chechAndWrite();
      }
      LiteralFile.this.rFile.seek(this.writePointer);
      do
      {
        i = this.outputBuffer.get();
        if (i == -1)
          continue;
        LiteralFile.this.rFile.write((char)i);
        this.writePointer += 1L;
      }
      while (i != -1);
      if (LiteralFile.this.rFile.getFilePointer() != this.writePointer)
        System.out.print("strange");
      LiteralFile.this.rFile.setLength(LiteralFile.this.rFile.getFilePointer());
    }

    void write(char paramChar, boolean paramBoolean)
      throws IOException
    {
      if (this.literalRead)
      {
        LiteralFile.LiteralHandler localLiteralHandler = LiteralFile.this.buffer.getLiteral(this.literalCount);
        if (localLiteralHandler == null)
        {
          this.tempBuffer.getAndPutIn(this.outputBuffer);
        }
        else
        {
          localLiteralHandler.modified = false;
          localLiteralHandler.phPosition = this.outputBuffer.getInsertCount();
          localLiteralHandler.writeLiteralHandlerToBuffer(this.outputBuffer);
          this.tempBuffer.clear();
        }
        this.literalRead = false;
      }
      if (paramBoolean)
        return;
      switch (this.status)
      {
      case 0:
        if (paramChar == '%')
        {
          this.status = 1;
          this.outputBuffer.put(paramChar);
          return;
        }
        if (paramChar == ' ')
          return;
        if ((Character.isLetter(paramChar)) || (paramChar == '-'))
        {
          this.status = 2;
          this.tempBuffer.put(paramChar);
          return;
        }
        this.outputBuffer.put(paramChar);
        break;
      case 1:
        this.outputBuffer.put(paramChar);
        if ((paramChar != '\n') && (paramChar != '\r'))
          return;
        this.status = 0;
        break;
      case 2:
        if (paramChar == '"')
        {
          this.tempBuffer.put(paramChar);
          this.status = 4;
          return;
        }
        if (paramChar == '%')
        {
          this.outputBuffer.put(paramChar);
          this.status = 3;
          return;
        }
        if (paramChar == '.')
        {
          this.tempBuffer.put(paramChar);
          this.literalRead = true;
          this.literalCount += 1L;
          this.status = 0;
          return;
        }
        this.tempBuffer.put(paramChar);
        break;
      case 4:
        this.tempBuffer.put(paramChar);
        if (paramChar != '"')
          return;
        this.status = 2;
        break;
      case 3:
        this.outputBuffer.put(paramChar);
        if ((paramChar != '\n') && (paramChar != '\r'))
          return;
        this.status = 2;
        break;
      default:
        throw new ParserException("parser error: method flush() status: " + this.status);
      }
    }

    void chechAndWrite()
      throws IOException
    {
      while (((this.readPointer - 1L) % LiteralFile.this.rFile.getBufferSize() == 0L) && (this.outputBuffer.size() - LiteralFile.this.rFile.getBufferSize() >= 0) && (this.readPointer - 1L - this.writePointer >= LiteralFile.this.rFile.getBufferSize()))
      {
        LiteralFile.this.rFile.seek(this.writePointer);
        for (int i = 0; i < LiteralFile.this.rFile.getBufferSize(); ++i)
          LiteralFile.this.rFile.write((char)this.outputBuffer.get());
        this.writePointer += LiteralFile.this.rFile.getBufferSize();
      }
      LiteralFile.this.rFile.seek(this.readPointer);
    }
  }

  class ClockBuffer
  {
    private HashMap clockIndex = null;
    private LiteralFile.LiteralHandler[] clock = null;
    private int pointer = 0;

    ClockBuffer(int arg2)
    {
      int i;
      if (i < 0)
        throw new IllegalArgumentException("buffer size must be > 0");
      this.clockIndex = new HashMap(i);
      this.clock = new LiteralFile.LiteralHandler[i];
      for (int j = 0; j < i; ++j)
        this.clock[j] = null;
    }

    void removeLiteral(long paramLong)
    {
      Long localLong = new Long(paramLong);
      LiteralFile.LiteralHandler localLiteralHandler = (LiteralFile.LiteralHandler)this.clockIndex.get(localLong);
      if (localLiteralHandler == null)
        return;
      this.clockIndex.remove(localLong);
      this.clock[localLiteralHandler.bfPosition] = null;
    }

    void removeAndUpdateLiteralPositions(LiteralFile.LiteralHandler paramLiteralHandler)
    {
      if (paramLiteralHandler == null)
        return;
      Long localLong = new Long(paramLiteralHandler.lgPosition);
      this.clockIndex.remove(localLong);
      this.clock[paramLiteralHandler.bfPosition] = null;
      for (int i = 0; i < this.clock.length; ++i)
      {
        if ((this.clock[i] == null) || (this.clock[i].lgPosition <= paramLiteralHandler.lgPosition))
          continue;
        this.clockIndex.remove(new Long(this.clock[i].lgPosition));
        this.clock[i].lgPosition -= 1L;
        this.clockIndex.put(new Long(this.clock[i].lgPosition), this.clock[i]);
      }
    }

    void putLiteral(LiteralFile.LiteralHandler paramLiteralHandler)
      throws IOException
    {
      Long localLong = new Long(paramLiteralHandler.lgPosition);
      LiteralFile.LiteralHandler localLiteralHandler = (LiteralFile.LiteralHandler)this.clockIndex.get(localLong);
      if (localLiteralHandler == null)
      {
        int i = getPosition();
        if (this.clock[i] != null)
          this.clockIndex.remove(new Long(this.clock[i].lgPosition));
        this.clockIndex.put(localLong, paramLiteralHandler);
        paramLiteralHandler.bfPosition = i;
        paramLiteralHandler.accessed = true;
        this.clock[i] = paramLiteralHandler;
      }
      else
      {
        localLiteralHandler.name = paramLiteralHandler.name;
        localLiteralHandler.terms = paramLiteralHandler.terms;
        localLiteralHandler.sign = paramLiteralHandler.sign;
        localLiteralHandler.phPosition = paramLiteralHandler.phPosition;
        localLiteralHandler.accessed = true;
      }
    }

    LiteralFile.LiteralHandler getLiteral(long paramLong)
    {
      LiteralFile.LiteralHandler localLiteralHandler = (LiteralFile.LiteralHandler)this.clockIndex.get(new Long(paramLong));
      if (localLiteralHandler != null)
        localLiteralHandler.accessed = true;
      return localLiteralHandler;
    }

    LiteralFile.LiteralHandler getNearInBuffer(long paramLong)
    {
      LiteralFile.LiteralHandler localLiteralHandler = null;
      for (int i = 0; i < this.clock.length; ++i)
      {
        if ((this.clock[i] == null) || (this.clock[i].lgPosition > paramLong))
          continue;
        if (localLiteralHandler == null)
        {
          localLiteralHandler = this.clock[i];
        }
        else
        {
          if (this.clock[i].lgPosition <= localLiteralHandler.lgPosition)
            continue;
          localLiteralHandler = this.clock[i];
        }
      }
      return localLiteralHandler;
    }

    LiteralFile.LiteralHandler findInBuffer(LiteralFile.LiteralHandler paramLiteralHandler)
    {
      for (int i = 0; i < this.clock.length; ++i)
        if ((this.clock[i] != null) && (this.clock[i].externalEquals(paramLiteralHandler)))
          return this.clock[i];
      return null;
    }

    private int getPosition()
      throws IOException
    {
      int i = 0;
      int j = 1;
      int k = this.pointer;
      while (true)
      {
        if (this.clock[k] == null)
        {
          this.pointer = k;
          return this.pointer;
        }
        if ((!this.clock[k].accessed) && (!this.clock[k].modified))
        {
          this.pointer = k;
          return this.pointer;
        }
        if (!this.clock[k].modified)
          j = 0;
        this.clock[k].accessed = false;
        k = (k + 1) % this.clock.length;
        if ((k != this.pointer) || (j == 0))
          continue;
        LiteralFile.this.flush();
      }
    }

    void resetAllModified()
    {
      for (int i = 0; i < this.clock.length; ++i)
      {
        if (this.clock[i] == null)
          continue;
        this.clock[i].modified = false;
      }
    }
  }

  static class FlushBuffer
  {
    private long virtualWritePointer = 0L;
    private long blockSize = 0L;
    private ArrayList buffer = null;
    private boolean comment = false;
    private boolean quote = false;
    private boolean endLiteral = false;

    FlushBuffer(int paramInt)
    {
      this.blockSize = paramInt;
      int i = 2 * paramInt;
      if (i < 16)
        i = 16;
      this.buffer = new ArrayList(i);
    }

    long getVirtualWritePointer()
    {
      return this.virtualWritePointer;
    }

    void put(int paramInt)
    {
      this.endLiteral = false;
      if ((paramInt == 37) && (!this.quote))
        this.comment = true;
      if ((this.comment) && (((paramInt == 10) || (paramInt == 13))))
        this.comment = false;
      if ((paramInt == 34) && (!this.comment))
        this.quote = (!this.quote);
      if ((paramInt == 32) && (!this.comment) && (!this.quote))
        return;
      if ((!this.comment) && (!this.quote) && (paramInt == 46))
        this.endLiteral = true;
      this.buffer.add(this.buffer.size(), new Integer(paramInt));
      this.virtualWritePointer += 1L;
    }

    boolean endLiteral()
    {
      return this.endLiteral;
    }

    int get()
    {
      if (this.buffer.size() == 0)
        return -1;
      Integer localInteger = (Integer)this.buffer.get(0);
      this.buffer.remove(0);
      return localInteger.intValue();
    }

    boolean blockBuilt()
    {
      return this.buffer.size() - this.blockSize >= 0L;
    }

    long blockAvailables()
    {
      return this.buffer.size() / this.blockSize;
    }
  }

  public static class LiteralHandler
  {
    String name = "";
    String[] terms = new String[0];
    boolean sign = true;
    boolean accessed = false;
    boolean modified = false;
    long lgPosition = -1L;
    long phPosition = -1L;
    int bfPosition = -1;

    public String getName()
    {
      return this.name;
    }

    public boolean getSign()
    {
      return this.sign;
    }

    public String getTermAt(int paramInt)
    {
      return this.terms[paramInt];
    }

    public String[] getTerms()
    {
      return (String[])this.terms.clone();
    }

    public long getLogicalPosition()
    {
      return this.lgPosition;
    }

    public LiteralHandler(long paramLong, String paramString, String[] paramArrayOfString, boolean paramBoolean)
    {
      if ((paramLong < 0L) || (paramString == null) || (paramString.length() <= 0))
        throw new InvalidParameterException("cannot build a LiteralHandler");
      this.lgPosition = paramLong;
      this.name = paramString;
      this.terms = paramArrayOfString;
      this.sign = paramBoolean;
    }

    public LiteralHandler(long paramLong, Predicate.Literal paramLiteral)
    {
      this(paramLong, paramLiteral.name(), null, paramLiteral.isPositive());
      this.terms = new String[paramLiteral.arity()];
      for (int i = 0; i < paramLiteral.arity(); ++i)
        this.terms[i] = paramLiteral.getTermAt(i);
    }

    boolean externalEquals(LiteralHandler paramLiteralHandler)
    {
      if (paramLiteralHandler == null)
        throw new NullPointerException();
      if ((this.name.equals(paramLiteralHandler.name)) && (this.sign == paramLiteralHandler.sign) && (this.terms.length == paramLiteralHandler.terms.length))
      {
        int i = 1;
        for (int j = 0; j < paramLiteralHandler.terms.length; ++j)
        {
          if (paramLiteralHandler.terms[j].equals(this.terms[j]))
            continue;
          i = 0;
          break;
        }
        if (i != 0)
          return true;
      }
      return false;
    }

    void writeLiteralHandler(RandomAccessCharacterFile paramRandomAccessCharacterFile)
      throws IOException
    {
      if (!this.sign)
        paramRandomAccessCharacterFile.write('-');
      paramRandomAccessCharacterFile.writeString(this.name);
      for (int i = 0; i < this.terms.length; ++i)
      {
        if (i == 0)
          paramRandomAccessCharacterFile.write('(');
        paramRandomAccessCharacterFile.writeString(this.terms[i]);
        if (i == this.terms.length - 1)
          paramRandomAccessCharacterFile.write(')');
        else
          paramRandomAccessCharacterFile.write(',');
      }
      paramRandomAccessCharacterFile.write('.');
    }

    void writeLiteralHandlerToFlushBuffer(LiteralFile.FlushBuffer paramFlushBuffer)
    {
      if (!this.sign)
        paramFlushBuffer.put(45);
      for (int i = 0; i < this.name.length(); ++i)
        paramFlushBuffer.put(this.name.charAt(i));
      for (i = 0; i < this.terms.length; ++i)
      {
        if (i == 0)
          paramFlushBuffer.put(40);
        for (int j = 0; j < this.terms[i].length(); ++j)
          paramFlushBuffer.put(this.terms[i].charAt(j));
        if (i == this.terms.length - 1)
          paramFlushBuffer.put(41);
        else
          paramFlushBuffer.put(44);
      }
      paramFlushBuffer.put(46);
    }

    void writeLiteralHandlerToBuffer(LiteralFile.IntegerBuffer paramIntegerBuffer)
    {
      if (!this.sign)
        paramIntegerBuffer.put(45);
      for (int i = 0; i < this.name.length(); ++i)
        paramIntegerBuffer.put(this.name.charAt(i));
      for (i = 0; i < this.terms.length; ++i)
      {
        if (i == 0)
          paramIntegerBuffer.put(40);
        for (int j = 0; j < this.terms[i].length(); ++j)
          paramIntegerBuffer.put(this.terms[i].charAt(j));
        if (i == this.terms.length - 1)
          paramIntegerBuffer.put(41);
        else
          paramIntegerBuffer.put(44);
      }
      paramIntegerBuffer.put(46);
    }
  }

  private class Flusher
  {
    LiteralFile.FlushBuffer fBuffer = new LiteralFile.FlushBuffer(LiteralFile.this.rFile.getBufferSize());
    long literalCount = 0L;
    long writePointer = 0L;
    long readPointer = 0L;
    byte status = 0;
    boolean end = false;
    int ch = -1;

    private Flusher()
    {
    }

    public void flush()
      throws IOException
    {
      long l1 = LiteralFile.this.rFile.getLength();
      LiteralFile.this.rFile.seek(0L);
      LiteralFile.LiteralHandler localLiteralHandler = LiteralFile.this.buffer.getLiteral(0L);
      if (localLiteralHandler != null)
      {
        long l2 = 0L;
        while (l2 < localLiteralHandler.phPosition)
        {
          this.ch = LiteralFile.this.rFile.read();
          if (this.ch == -1)
            throw new IOException("Unexpected EOF");
          this.fBuffer.put(this.ch);
          l2 += 1L;
        }
        this.readPointer = localLiteralHandler.phPosition;
      }
      this.ch = -1;
      while (!this.end)
        switch (this.status)
        {
        case 0:
          localLiteralHandler = LiteralFile.this.buffer.getLiteral(this.literalCount);
          if (localLiteralHandler != null)
          {
            clearLiteral();
            if (this.literalCount != 0L)
              this.fBuffer.put(10);
            localLiteralHandler.phPosition = this.fBuffer.getVirtualWritePointer();
            localLiteralHandler.writeLiteralHandlerToFlushBuffer(this.fBuffer);
            localLiteralHandler.modified = false;
            this.literalCount += 1L;
          }
          else
          {
            this.status = 1;
          }
          if (this.literalCount == LiteralFile.this.fileSize)
            this.end = true;
          break;
        case 1:
          this.ch = LiteralFile.this.rFile.read();
          this.readPointer += 1L;
          if (writeCharAndTestEndLiteral(this.ch))
            this.status = 0;
          if (this.ch == -1)
            this.end = true;
        }
      while (this.ch != -1)
      {
        this.ch = LiteralFile.this.rFile.read();
        if (this.ch == -1)
          continue;
        this.fBuffer.put(this.ch);
      }
      LiteralFile.this.rFile.seek(this.writePointer);
      do
      {
        this.ch = this.fBuffer.get();
        if (this.ch == -1)
          continue;
        LiteralFile.this.rFile.write((char)this.ch);
      }
      while (this.ch != -1);
      LiteralFile.this.rFile.setLength(LiteralFile.this.rFile.getFilePointer());
    }

    private void clearLiteral()
      throws IOException
    {
      int i = 0;
      int j = 0;
      int k = 0;
      do
      {
        this.ch = LiteralFile.this.rFile.read();
        this.readPointer += 1L;
        if ((i == 0) && (j == 0) && (this.ch == 46))
          k = 1;
        if ((j == 0) && (this.ch == 37))
          i = 1;
        if ((i != 0) && (((this.ch == 10) || (this.ch == 13))))
          i = 0;
        if ((i == 0) && (this.ch == 34))
          j = (j == 0) ? 1 : 0;
        if (i != 0)
          this.fBuffer.put(this.ch);
        writeAvailableBlocks();
      }
      while ((this.ch != -1) && (k == 0));
      if (k != 0)
        return;
      throw new IOException("Unexpected EOF");
    }

    private boolean writeCharAndTestEndLiteral(int paramInt)
      throws IOException
    {
      if (paramInt == -1)
        return false;
      this.fBuffer.put(paramInt);
      writeAvailableBlocks();
      if (this.fBuffer.endLiteral())
      {
        this.literalCount += 1L;
        return true;
      }
      return false;
    }

    private void writeAvailableBlocks()
      throws IOException
    {
      while ((this.fBuffer.blockBuilt()) && ((this.readPointer + 1L) % LiteralFile.this.rFile.getBufferSize() == 0L) && ((1L + this.readPointer - this.writePointer) / LiteralFile.this.rFile.getBufferSize() > 0L))
      {
        do
        {
          LiteralFile.this.rFile.seek(this.writePointer);
          for (int i = 0; i < LiteralFile.this.rFile.getBufferSize(); ++i)
            LiteralFile.this.rFile.write((char)this.fBuffer.get());
          this.writePointer += LiteralFile.this.rFile.getBufferSize();
        }
        while ((this.fBuffer.blockBuilt()) && ((this.readPointer + 1L) % LiteralFile.this.rFile.getBufferSize() == 0L) && ((1L + this.readPointer - this.writePointer) / LiteralFile.this.rFile.getBufferSize() > 0L));
        LiteralFile.this.rFile.seek(this.readPointer);
      }
    }
  }
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.io.LiteralFile
 * JD-Core Version:    0.5.4
 */