package DLV;

import java.io.IOException;

class InputTokenizer
{
  private InputHandler input = null;
  private long cursor = 0L;
  private StringBuffer buffer = new StringBuffer();
  private boolean fine = false;
  private boolean delim = false;
  private char tmp = '0';

  InputTokenizer(InputHandler paramInputHandler)
  {
    this.input = paramInputHandler;
  }

  boolean hasMoreTokens()
  {
    return (!this.input.end()) || (this.delim);
  }

  StringBuffer nextToken()
    throws IOException
  {
    this.buffer.delete(0, this.buffer.length());
    if (this.delim)
    {
      this.buffer.append(this.tmp);
      this.delim = false;
      return this.buffer;
    }
    this.fine = false;
    while ((!this.input.end()) && (!this.fine))
    {
      this.tmp = this.input.getChar();
      switch (this.tmp)
      {
      case '\n':
      case '\r':
      case ' ':
      case '"':
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
        if (this.buffer.length() > 0)
          this.delim = true;
        else
          this.buffer.append(this.tmp);
        this.fine = true;
        break;
      }
      this.buffer.append(this.tmp);
    }
    return this.buffer;
  }
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.InputTokenizer
 * JD-Core Version:    0.5.4
 */