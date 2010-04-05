package DLV.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class JavaCharStream
{
  public static final boolean staticFlag = false;
  public int bufpos = -1;
  int bufsize;
  int available;
  int tokenBegin;
  protected int[] bufline;
  protected int[] bufcolumn;
  protected int column = 0;
  protected int line = 1;
  protected boolean prevCharIsCR = false;
  protected boolean prevCharIsLF = false;
  protected Reader inputStream;
  protected char[] nextCharBuf;
  protected char[] buffer;
  protected int maxNextCharInd = 0;
  protected int nextCharInd = -1;
  protected int inBuf = 0;

  static final int hexval(char paramChar)
    throws IOException
  {
    switch (paramChar)
    {
    case '0':
      return 0;
    case '1':
      return 1;
    case '2':
      return 2;
    case '3':
      return 3;
    case '4':
      return 4;
    case '5':
      return 5;
    case '6':
      return 6;
    case '7':
      return 7;
    case '8':
      return 8;
    case '9':
      return 9;
    case 'A':
    case 'a':
      return 10;
    case 'B':
    case 'b':
      return 11;
    case 'C':
    case 'c':
      return 12;
    case 'D':
    case 'd':
      return 13;
    case 'E':
    case 'e':
      return 14;
    case 'F':
    case 'f':
      return 15;
    case ':':
    case ';':
    case '<':
    case '=':
    case '>':
    case '?':
    case '@':
    case 'G':
    case 'H':
    case 'I':
    case 'J':
    case 'K':
    case 'L':
    case 'M':
    case 'N':
    case 'O':
    case 'P':
    case 'Q':
    case 'R':
    case 'S':
    case 'T':
    case 'U':
    case 'V':
    case 'W':
    case 'X':
    case 'Y':
    case 'Z':
    case '[':
    case '\\':
    case ']':
    case '^':
    case '_':
    case '`':
    }
    throw new IOException();
  }

  protected void ExpandBuff(boolean paramBoolean)
  {
    char[] arrayOfChar = new char[this.bufsize + 2048];
    int[] arrayOfInt1 = new int[this.bufsize + 2048];
    int[] arrayOfInt2 = new int[this.bufsize + 2048];
    try
    {
      if (paramBoolean)
      {
        System.arraycopy(this.buffer, this.tokenBegin, arrayOfChar, 0, this.bufsize - this.tokenBegin);
        System.arraycopy(this.buffer, 0, arrayOfChar, this.bufsize - this.tokenBegin, this.bufpos);
        this.buffer = arrayOfChar;
        System.arraycopy(this.bufline, this.tokenBegin, arrayOfInt1, 0, this.bufsize - this.tokenBegin);
        System.arraycopy(this.bufline, 0, arrayOfInt1, this.bufsize - this.tokenBegin, this.bufpos);
        this.bufline = arrayOfInt1;
        System.arraycopy(this.bufcolumn, this.tokenBegin, arrayOfInt2, 0, this.bufsize - this.tokenBegin);
        System.arraycopy(this.bufcolumn, 0, arrayOfInt2, this.bufsize - this.tokenBegin, this.bufpos);
        this.bufcolumn = arrayOfInt2;
        this.bufpos += this.bufsize - this.tokenBegin;
      }
      else
      {
        System.arraycopy(this.buffer, this.tokenBegin, arrayOfChar, 0, this.bufsize - this.tokenBegin);
        this.buffer = arrayOfChar;
        System.arraycopy(this.bufline, this.tokenBegin, arrayOfInt1, 0, this.bufsize - this.tokenBegin);
        this.bufline = arrayOfInt1;
        System.arraycopy(this.bufcolumn, this.tokenBegin, arrayOfInt2, 0, this.bufsize - this.tokenBegin);
        this.bufcolumn = arrayOfInt2;
        this.bufpos -= this.tokenBegin;
      }
    }
    catch (Throwable localThrowable)
    {
      throw new Error(localThrowable.getMessage());
    }
    this.available = (this.bufsize += 2048);
    this.tokenBegin = 0;
  }

  protected void FillBuff()
    throws IOException
  {
    if (this.maxNextCharInd == 4096)
      this.maxNextCharInd = (this.nextCharInd = 0);
    try
    {
      int i;
      if ((i = this.inputStream.read(this.nextCharBuf, this.maxNextCharInd, 4096 - this.maxNextCharInd)) == -1)
      {
        this.inputStream.close();
        throw new IOException();
      }
      this.maxNextCharInd += i;
      return;
    }
    catch (IOException localIOException)
    {
      if (this.bufpos != 0)
      {
        this.bufpos -= 1;
        backup(0);
      }
      else
      {
        this.bufline[this.bufpos] = this.line;
        this.bufcolumn[this.bufpos] = this.column;
      }
      throw localIOException;
    }
  }

  protected char ReadByte()
    throws IOException
  {
    if (++this.nextCharInd >= this.maxNextCharInd)
      FillBuff();
    return this.nextCharBuf[this.nextCharInd];
  }

  public char BeginToken()
    throws IOException
  {
    if (this.inBuf > 0)
    {
      this.inBuf -= 1;
      if (++this.bufpos == this.bufsize)
        this.bufpos = 0;
      this.tokenBegin = this.bufpos;
      return this.buffer[this.bufpos];
    }
    this.tokenBegin = 0;
    this.bufpos = -1;
    return readChar();
  }

  protected void AdjustBuffSize()
  {
    if (this.available == this.bufsize)
      if (this.tokenBegin > 2048)
      {
        this.bufpos = 0;
        this.available = this.tokenBegin;
      }
      else
      {
        ExpandBuff(false);
      }
    else if (this.available > this.tokenBegin)
      this.available = this.bufsize;
    else if (this.tokenBegin - this.available < 2048)
      ExpandBuff(true);
    else
      this.available = this.tokenBegin;
  }

  protected void UpdateLineColumn(char paramChar)
  {
    this.column += 1;
    if (this.prevCharIsLF)
    {
      this.prevCharIsLF = false;
      this.line += (this.column = 1);
    }
    else if (this.prevCharIsCR)
    {
      this.prevCharIsCR = false;
      if (paramChar == '\n')
        this.prevCharIsLF = true;
      else
        this.line += (this.column = 1);
    }
    switch (paramChar)
    {
    case '\r':
      this.prevCharIsCR = true;
      break;
    case '\n':
      this.prevCharIsLF = true;
      break;
    case '\t':
      this.column -= 1;
      this.column += 8 - (this.column & 0x7);
    case '\013':
    case '\f':
    }
    this.bufline[this.bufpos] = this.line;
    this.bufcolumn[this.bufpos] = this.column;
  }

  public char readChar()
    throws IOException
  {
    if (this.inBuf > 0)
    {
      this.inBuf -= 1;
      if (++this.bufpos == this.bufsize)
        this.bufpos = 0;
      return this.buffer[this.bufpos];
    }
    if (++this.bufpos == this.available)
      AdjustBuffSize();
    char c;
    if ((this.buffer[this.bufpos] = c = ReadByte()) == '\\')
    {
      UpdateLineColumn(c);
      for (int i = 1; ; ++i)
      {
        if (++this.bufpos == this.available)
          AdjustBuffSize();
        try
        {
          if ((this.buffer[this.bufpos] = c = ReadByte()) != '\\')
          {
            UpdateLineColumn(c);
            if ((c == 'u') && ((i & 0x1) == 1))
            {
              if (--this.bufpos < 0)
                this.bufpos = (this.bufsize - 1);
              break label224:
            }
            backup(i);
            return '\\';
          }
        }
        catch (IOException localIOException1)
        {
          if (i > 1)
            backup(i);
          return '\\';
        }
        UpdateLineColumn(c);
      }
      try
      {
        while ((c = ReadByte()) == 'u')
          label224: this.column += 1;
        char tmp293_292 = (char)(hexval(c) << 12 | hexval(ReadByte()) << 8 | hexval(ReadByte()) << 4 | hexval(ReadByte()));
        c = tmp293_292;
        this.buffer[this.bufpos] = tmp293_292;
        this.column += 4;
      }
      catch (IOException localIOException2)
      {
        throw new Error("Invalid escape character at line " + this.line + " column " + this.column + ".");
      }
      if (i == 1)
        return c;
      backup(i - 1);
      return '\\';
    }
    UpdateLineColumn(c);
    return c;
  }

  /** @deprecated */
  public int getColumn()
  {
    return this.bufcolumn[this.bufpos];
  }

  /** @deprecated */
  public int getLine()
  {
    return this.bufline[this.bufpos];
  }

  public int getEndColumn()
  {
    return this.bufcolumn[this.bufpos];
  }

  public int getEndLine()
  {
    return this.bufline[this.bufpos];
  }

  public int getBeginColumn()
  {
    return this.bufcolumn[this.tokenBegin];
  }

  public int getBeginLine()
  {
    return this.bufline[this.tokenBegin];
  }

  public void backup(int paramInt)
  {
    this.inBuf += paramInt;
    if (this.bufpos -= paramInt >= 0)
      return;
    this.bufpos += this.bufsize;
  }

  public JavaCharStream(Reader paramReader, int paramInt1, int paramInt2, int paramInt3)
  {
    this.inputStream = paramReader;
    this.line = paramInt1;
    this.column = (paramInt2 - 1);
    this.available = (this.bufsize = paramInt3);
    this.buffer = new char[paramInt3];
    this.bufline = new int[paramInt3];
    this.bufcolumn = new int[paramInt3];
    this.nextCharBuf = new char[4096];
  }

  public JavaCharStream(Reader paramReader, int paramInt1, int paramInt2)
  {
    this(paramReader, paramInt1, paramInt2, 4096);
  }

  public JavaCharStream(Reader paramReader)
  {
    this(paramReader, 1, 1, 4096);
  }

  public void ReInit(Reader paramReader, int paramInt1, int paramInt2, int paramInt3)
  {
    this.inputStream = paramReader;
    this.line = paramInt1;
    this.column = (paramInt2 - 1);
    if ((this.buffer == null) || (paramInt3 != this.buffer.length))
    {
      this.available = (this.bufsize = paramInt3);
      this.buffer = new char[paramInt3];
      this.bufline = new int[paramInt3];
      this.bufcolumn = new int[paramInt3];
      this.nextCharBuf = new char[4096];
    }
    this.prevCharIsLF = (this.prevCharIsCR = 0);
    this.tokenBegin = (this.inBuf = this.maxNextCharInd = 0);
    this.nextCharInd = (this.bufpos = -1);
  }

  public void ReInit(Reader paramReader, int paramInt1, int paramInt2)
  {
    ReInit(paramReader, paramInt1, paramInt2, 4096);
  }

  public void ReInit(Reader paramReader)
  {
    ReInit(paramReader, 1, 1, 4096);
  }

  public JavaCharStream(InputStream paramInputStream, int paramInt1, int paramInt2, int paramInt3)
  {
    this(new InputStreamReader(paramInputStream), paramInt1, paramInt2, 4096);
  }

  public JavaCharStream(InputStream paramInputStream, int paramInt1, int paramInt2)
  {
    this(paramInputStream, paramInt1, paramInt2, 4096);
  }

  public JavaCharStream(InputStream paramInputStream)
  {
    this(paramInputStream, 1, 1, 4096);
  }

  public void ReInit(InputStream paramInputStream, int paramInt1, int paramInt2, int paramInt3)
  {
    ReInit(new InputStreamReader(paramInputStream), paramInt1, paramInt2, 4096);
  }

  public void ReInit(InputStream paramInputStream, int paramInt1, int paramInt2)
  {
    ReInit(paramInputStream, paramInt1, paramInt2, 4096);
  }

  public void ReInit(InputStream paramInputStream)
  {
    ReInit(paramInputStream, 1, 1, 4096);
  }

  public String GetImage()
  {
    if (this.bufpos >= this.tokenBegin)
      return new String(this.buffer, this.tokenBegin, this.bufpos - this.tokenBegin + 1);
    return new String(this.buffer, this.tokenBegin, this.bufsize - this.tokenBegin) + new String(this.buffer, 0, this.bufpos + 1);
  }

  public char[] GetSuffix(int paramInt)
  {
    char[] arrayOfChar = new char[paramInt];
    if (this.bufpos + 1 >= paramInt)
    {
      System.arraycopy(this.buffer, this.bufpos - paramInt + 1, arrayOfChar, 0, paramInt);
    }
    else
    {
      System.arraycopy(this.buffer, this.bufsize - (paramInt - this.bufpos - 1), arrayOfChar, 0, paramInt - this.bufpos - 1);
      System.arraycopy(this.buffer, 0, arrayOfChar, paramInt - this.bufpos - 1, this.bufpos + 1);
    }
    return arrayOfChar;
  }

  public void Done()
  {
    this.nextCharBuf = null;
    this.buffer = null;
    this.bufline = null;
    this.bufcolumn = null;
  }

  public void adjustBeginLineColumn(int paramInt1, int paramInt2)
  {
    int i = this.tokenBegin;
    int j;
    if (this.bufpos >= this.tokenBegin)
      j = this.bufpos - this.tokenBegin + this.inBuf + 1;
    else
      j = this.bufsize - this.tokenBegin + this.bufpos + 1 + this.inBuf;
    int k = 0;
    int l = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    while (k < j)
    {
      int tmp92_91 = (i % this.bufsize);
      l = tmp92_91;
      int tmp109_108 = (++i % this.bufsize);
      i1 = tmp109_108;
      if (this.bufline[tmp92_91] != this.bufline[tmp109_108])
        break;
      this.bufline[l] = paramInt1;
      i2 = i3 + this.bufcolumn[i1] - this.bufcolumn[l];
      this.bufcolumn[l] = (paramInt2 + i3);
      i3 = i2;
      ++k;
    }
    if (k < j)
    {
      this.bufline[l] = (paramInt1++);
      this.bufcolumn[l] = (paramInt2 + i3);
      while (k++ < j)
      {
        int tmp214_213 = (i % this.bufsize);
        l = tmp214_213;
        if (this.bufline[tmp214_213] != this.bufline[(++i % this.bufsize)])
          this.bufline[l] = (paramInt1++);
        this.bufline[l] = paramInt1;
      }
    }
    this.line = this.bufline[l];
    this.column = this.bufcolumn[l];
  }
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.parser.JavaCharStream
 * JD-Core Version:    0.5.4
 */