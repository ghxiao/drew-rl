package DLV.parser;

public class Token
{
  public int kind;
  public int beginLine;
  public int beginColumn;
  public int endLine;
  public int endColumn;
  public String image;
  public Token next;
  public Token specialToken;

  public String toString()
  {
    return this.image;
  }

  public static final Token newToken(int paramInt)
  {
    switch (paramInt)
    {
    }
    return new Token();
  }
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.parser.Token
 * JD-Core Version:    0.5.4
 */