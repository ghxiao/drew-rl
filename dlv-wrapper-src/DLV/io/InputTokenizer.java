package DLV.io;

import java.io.IOException;

class InputTokenizer
{
  private RandomAccessCharacterFile input = null;
  private long cursor = 0L;
  private StringBuffer buffer = new StringBuffer();
  private boolean fine = false;
  private boolean delim = false;
  private char tmp = '0';
  private boolean eof = false;
  private long startPosition = 0L;

  InputTokenizer(RandomAccessCharacterFile paramRandomAccessCharacterFile)
  {
    this.input = paramRandomAccessCharacterFile;
  }

  boolean hasMoreTokens()
  {
    return (!this.eof) || (this.delim);
  }

  long getStartPosition()
  {
    return this.startPosition;
  }

  StringBuffer nextToken()
    throws IOException
  {
    this.buffer.delete(0, this.buffer.length());
    if (this.delim)
    {
      this.startPosition = (this.input.getFilePointer() - 1L);
      this.buffer.append(this.tmp);
      this.delim = false;
      if (this.input.read() == -1)
        this.eof = true;
      else
        this.input.seek(this.input.getFilePointer() - 1L);
      return this.buffer;
    }
    this.fine = false;
    this.startPosition = this.input.getFilePointer();
    do
    {
      int i = this.input.read();
      if (i == -1)
      {
        this.eof = true;
        return this.buffer;
      }
      this.tmp = (char)i;
      if (delimiter(this.tmp))
      {
        if (this.buffer.length() == 0)
          this.buffer.append(this.tmp);
        else
          this.delim = true;
        this.fine = true;
      }
      else
      {
        this.buffer.append(this.tmp);
      }
    }
    while (!this.fine);
    return this.buffer;
  }

  private boolean delimiter(char paramChar)
  {
    switch (paramChar)
    {
    case '\n':
    case '\r':
    case ' ':
    case '"':
    case '%':
    case '(':
    case ')':
    case ',':
    case '-':
    case '.':
    case ':':
    case '<':
    case '>':
    case '[':
    case ']':
    case '{':
    case '}':
    case '~':
      return true;
    }
    return false;
  }
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.io.InputTokenizer
 * JD-Core Version:    0.5.4
 */