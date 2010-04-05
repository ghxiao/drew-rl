package DLV.parser;

public abstract interface QueryParserConstants
{
  public static final int EOF = 0;
  public static final int PARAP = 4;
  public static final int PARCH = 5;
  public static final int VIRGOLA = 6;
  public static final int TRATTO = 7;
  public static final int GRAFFAAP = 8;
  public static final int GRAFFACH = 9;
  public static final int CAUT = 10;
  public static final int EV = 11;
  public static final int BRAV = 12;
  public static final int STOPLIST = 13;
  public static final int IDENTIFIER = 14;
  public static final int LETTER = 15;
  public static final int DIGIT = 16;
  public static final int STRING_LITERAL = 17;
  public static final int DEFAULT = 0;
  public static final String[] tokenImage = { "<EOF>", "\" \"", "\"\\t\"", "\"\\f\"", "\"(\"", "\")\"", "\",\"", "\"-\"", "\"{\"", "\"}\"", "\"is cautiously \"", "\", evidenced by\"", "\"is bravely \"", "<STOPLIST>", "<IDENTIFIER>", "<LETTER>", "<DIGIT>", "<STRING_LITERAL>" };
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.parser.QueryParserConstants
 * JD-Core Version:    0.5.4
 */