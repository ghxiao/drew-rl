package DLV.parser;

import DLV.Model;
import DLV.OutputDescriptor;
import DLV.OutputDescriptorItem;
import DLV.ParserException;
import DLV.Predicate;
import DLV.Predicate.Literal;
import DLV.Tools;
import DLV.core.DlvIOHandler;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

public class QueryParser
  implements QueryParserConstants
{
  private boolean firstRow = true;
  private ArrayList temp = new ArrayList();
  private DlvIOHandler dm = null;
  private Predicate queryResult = null;
  private Model m = null;
  public QueryParserTokenManager token_source;
  JavaCharStream jj_input_stream;
  public Token token;
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos;
  private Token jj_lastpos;
  private int jj_la;
  public boolean lookingAhead = false;
  private boolean jj_semLA;
  private int jj_gen;
  private final int[] jj_la1 = new int[11];
  private static int[] jj_la1_0;
  private final JJCalls[] jj_2_rtns = new JJCalls[2];
  private boolean jj_rescan = false;
  private int jj_gc = 0;
  private final LookaheadSuccess jj_ls = new LookaheadSuccess(null);
  private Vector jj_expentries = new Vector();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  public static void main(String[] paramArrayOfString)
  {
    String str = null;
    QueryParser localQueryParser;
    if (paramArrayOfString.length == 0)
    {
      System.out.println("DLP+ Parser Version 0.1 :  Reading from standard input . . .\n\n");
      localQueryParser = new QueryParser(System.in);
    }
    else if (paramArrayOfString.length == 1)
    {
      str = paramArrayOfString[0];
      System.out.println("DLP+ Parser Version 0.1 :  Reading from file " + str + " . . .\n\n");
      try
      {
        localQueryParser = new QueryParser(new FileInputStream(str));
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        System.out.println("DLP+ Parser Version 0.1 :  File " + str + " not found.\n\n");
        return;
      }
    }
    else
    {
      System.out.println("DLP+ Parser Version 0.1:  Usage is one of:");
      System.out.println("         java DLP+Parser < inputfile");
      System.out.println("OR");
      System.out.println("         java DLP+Parser inputfile");
      return;
    }
    try
    {
      localQueryParser.Program();
      System.out.println("DLP+ Parser Version 0.1: Results \n\n");
    }
    catch (ParseException localParseException)
    {
      System.out.println(localParseException.getMessage());
    }
    catch (TokenMgrError localTokenMgrError)
    {
      System.out.println(localTokenMgrError.getMessage());
      System.out.println(":  Encountered lexical errors during parse.");
    }
  }

  public final Predicate getQueryResult()
  {
    return null;
  }

  public final void start()
    throws ParserException
  {
    Program();
  }

  public final void Program()
    throws ParseException
  {
    switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk)
    {
    case 14:
    case 17:
      while (true)
      {
        Statement();
        switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk)
        {
        case 14:
        case 17:
        }
      }
      this.jj_la1[0] = this.jj_gen;
      jj_consume_token(0);
      break;
    case 0:
      jj_consume_token(0);
      this.queryResult = this.dm.getOutputDescriptor().getDescriptor("result").buildPredicate("result", 0, this.m, 0);
      break;
    default:
      this.jj_la1[1] = this.jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  public final void Statement()
    throws ParseException
  {
    if (jj_2_1(2))
      nonGroundQuery();
    else
      switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk)
      {
      case 14:
      case 17:
        groundQuery();
        break;
      default:
        this.jj_la1[2] = this.jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
  }

  public final String value()
    throws ParseException
  {
    switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk)
    {
    case 14:
      jj_consume_token(14);
      return getToken(0).image;
    case 17:
      jj_consume_token(17);
      return getToken(0).image;
    }
    this.jj_la1[3] = this.jj_gen;
    jj_consume_token(-1);
    throw new ParseException();
  }

  public final void nonGroundLine()
    throws ParseException
  {
    this.temp.clear();
    this.temp.add(value());
    while (true)
    {
      switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk)
      {
      case 6:
        break;
      default:
        this.jj_la1[4] = this.jj_gen;
        break;
      }
      jj_consume_token(6);
      this.temp.add(value());
    }
    if (this.firstRow)
    {
      this.queryResult = this.dm.getOutputDescriptor().getDescriptor("result").buildPredicate("result", this.temp.size(), this.m, 0);
      this.firstRow = false;
    }
    String[] arrayOfString = Tools.arrayListToStringArray(this.temp);
    Predicate tmp161_158 = this.queryResult;
    tmp161_158.getClass();
    this.queryResult.addLiteral(new Predicate.Literal(tmp161_158, arrayOfString));
  }

  public final void nonGroundQuery()
    throws ParseException
  {
    try
    {
      nonGroundLine();
      jj_consume_token(13);
    }
    catch (ParseException localParseException)
    {
      System.out.println(localParseException);
    }
  }

  public final void groundAtom()
    throws ParseException
  {
    value();
    jj_consume_token(4);
    value();
    while (true)
    {
      switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk)
      {
      case 6:
        break;
      default:
        this.jj_la1[5] = this.jj_gen;
        break;
      }
      jj_consume_token(6);
      value();
    }
    jj_consume_token(5);
  }

  public final void bodyEvidence()
    throws ParseException
  {
    if (jj_2_2(2147483647))
    {
      value();
      jj_consume_token(4);
      value();
      while (true)
      {
        switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk)
        {
        case 6:
          break;
        default:
          this.jj_la1[6] = this.jj_gen;
          break;
        }
        jj_consume_token(6);
        value();
      }
      jj_consume_token(5);
    }
    else
    {
      switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk)
      {
      case 14:
      case 17:
        value();
        break;
      default:
        this.jj_la1[7] = this.jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  public final void trueOrFalse()
    throws ParseException
  {
    String str = value();
    this.queryResult = this.dm.getOutputDescriptor().getDescriptor("result").buildPredicate("result", 1, this.m, 0);
    Predicate tmp46_43 = this.queryResult;
    tmp46_43.getClass();
    this.queryResult.addLiteral(new Predicate.Literal(tmp46_43, new String[] { str }));
  }

  public final void groundQuery()
    throws ParseException
  {
    try
    {
      value();
      jj_consume_token(4);
      nonGroundLine();
      jj_consume_token(5);
      while (true)
      {
        switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk)
        {
        case 6:
          break;
        default:
          this.jj_la1[8] = this.jj_gen;
          break;
        }
        jj_consume_token(6);
        value();
        jj_consume_token(4);
        nonGroundLine();
        jj_consume_token(5);
      }
      switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk)
      {
      case 10:
        jj_consume_token(10);
        break;
      case 12:
        jj_consume_token(12);
        break;
      default:
        this.jj_la1[9] = this.jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      trueOrFalse();
      jj_consume_token(11);
      jj_consume_token(8);
      bodyEvidence();
      while (true)
      {
        switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk)
        {
        case 6:
          break;
        default:
          this.jj_la1[10] = this.jj_gen;
          break;
        }
        jj_consume_token(6);
        bodyEvidence();
      }
      jj_consume_token(9);
      jj_consume_token(13);
    }
    catch (ParseException localParseException)
    {
      System.out.println(localParseException);
    }
  }

  private final boolean jj_2_1(int paramInt)
  {
    this.jj_la = paramInt;
    this.jj_lastpos = (this.jj_scanpos = this.token);
    try
    {
      int i = (!jj_3_1()) ? 1 : 0;
      return i;
    }
    catch (LookaheadSuccess localLookaheadSuccess)
    {
      int j = 1;
      return j;
    }
    finally
    {
      jj_save(0, paramInt);
    }
  }

  private final boolean jj_2_2(int paramInt)
  {
    this.jj_la = paramInt;
    this.jj_lastpos = (this.jj_scanpos = this.token);
    try
    {
      int i = (!jj_3_2()) ? 1 : 0;
      return i;
    }
    catch (LookaheadSuccess localLookaheadSuccess)
    {
      int j = 1;
      return j;
    }
    finally
    {
      jj_save(1, paramInt);
    }
  }

  private final boolean jj_3R_7()
  {
    if (jj_3R_9())
      return true;
    return jj_scan_token(13);
  }

  private final boolean jj_3_1()
  {
    return jj_3R_7();
  }

  private final boolean jj_3R_10()
  {
    return jj_scan_token(6);
  }

  private final boolean jj_3R_8()
  {
    Token localToken = this.jj_scanpos;
    if (jj_scan_token(14))
    {
      this.jj_scanpos = localToken;
      if (jj_scan_token(17))
        return true;
    }
    return false;
  }

  private final boolean jj_3_2()
  {
    if (jj_3R_8())
      return true;
    return jj_scan_token(4);
  }

  private final boolean jj_3R_9()
  {
    if (jj_3R_8())
      return true;
    Token localToken;
    do
      localToken = this.jj_scanpos;
    while (!jj_3R_10());
    this.jj_scanpos = localToken;
    return false;
  }

  private static void jj_la1_0()
  {
    jj_la1_0 = new int[] { 147456, 147457, 147456, 147456, 64, 64, 64, 147456, 64, 5120, 64 };
  }

  public QueryParser(InputStream paramInputStream)
  {
    this.jj_input_stream = new JavaCharStream(paramInputStream, 1, 1);
    this.token_source = new QueryParserTokenManager(this.jj_input_stream);
    this.token = new Token();
    this.jj_ntk = -1;
    this.jj_gen = 0;
    for (int i = 0; i < 11; ++i)
      this.jj_la1[i] = -1;
    for (i = 0; i < this.jj_2_rtns.length; ++i)
      this.jj_2_rtns[i] = new JJCalls();
  }

  public void ReInit(InputStream paramInputStream)
  {
    this.jj_input_stream.ReInit(paramInputStream, 1, 1);
    this.token_source.ReInit(this.jj_input_stream);
    this.token = new Token();
    this.jj_ntk = -1;
    this.jj_gen = 0;
    for (int i = 0; i < 11; ++i)
      this.jj_la1[i] = -1;
    for (i = 0; i < this.jj_2_rtns.length; ++i)
      this.jj_2_rtns[i] = new JJCalls();
  }

  public QueryParser(Reader paramReader, DlvIOHandler paramDlvIOHandler, Model paramModel)
  {
    if (paramDlvIOHandler == null)
      throw new NullPointerException("Null DlvHandler");
    if (paramModel == null)
      throw new NullPointerException("Null Model");
    this.dm = paramDlvIOHandler;
    this.m = paramModel;
    this.jj_input_stream = new JavaCharStream(paramReader, 1, 1);
    this.token_source = new QueryParserTokenManager(this.jj_input_stream);
    this.token = new Token();
    this.jj_ntk = -1;
    this.jj_gen = 0;
    for (int i = 0; i < 11; ++i)
      this.jj_la1[i] = -1;
    for (i = 0; i < this.jj_2_rtns.length; ++i)
      this.jj_2_rtns[i] = new JJCalls();
  }

  public void ReInit(Reader paramReader)
  {
    this.jj_input_stream.ReInit(paramReader, 1, 1);
    this.token_source.ReInit(this.jj_input_stream);
    this.token = new Token();
    this.jj_ntk = -1;
    this.jj_gen = 0;
    for (int i = 0; i < 11; ++i)
      this.jj_la1[i] = -1;
    for (i = 0; i < this.jj_2_rtns.length; ++i)
      this.jj_2_rtns[i] = new JJCalls();
  }

  public QueryParser(QueryParserTokenManager paramQueryParserTokenManager)
  {
    this.token_source = paramQueryParserTokenManager;
    this.token = new Token();
    this.jj_ntk = -1;
    this.jj_gen = 0;
    for (int i = 0; i < 11; ++i)
      this.jj_la1[i] = -1;
    for (i = 0; i < this.jj_2_rtns.length; ++i)
      this.jj_2_rtns[i] = new JJCalls();
  }

  public void ReInit(QueryParserTokenManager paramQueryParserTokenManager)
  {
    this.token_source = paramQueryParserTokenManager;
    this.token = new Token();
    this.jj_ntk = -1;
    this.jj_gen = 0;
    for (int i = 0; i < 11; ++i)
      this.jj_la1[i] = -1;
    for (i = 0; i < this.jj_2_rtns.length; ++i)
      this.jj_2_rtns[i] = new JJCalls();
  }

  private final Token jj_consume_token(int paramInt)
    throws ParseException
  {
    Token localToken;
    if ((localToken = this.token).next != null)
      this.token = this.token.next;
    else
      this.token = (this.token.next = this.token_source.getNextToken());
    this.jj_ntk = -1;
    if (this.token.kind == paramInt)
    {
      this.jj_gen += 1;
      if (++this.jj_gc > 100)
      {
        this.jj_gc = 0;
        for (int i = 0; i < this.jj_2_rtns.length; ++i)
          for (JJCalls localJJCalls = this.jj_2_rtns[i]; localJJCalls != null; localJJCalls = localJJCalls.next)
          {
            if (localJJCalls.gen >= this.jj_gen)
              continue;
            localJJCalls.first = null;
          }
      }
      return this.token;
    }
    this.token = localToken;
    this.jj_kind = paramInt;
    throw generateParseException();
  }

  private final boolean jj_scan_token(int paramInt)
  {
    if (this.jj_scanpos == this.jj_lastpos)
    {
      this.jj_la -= 1;
      if (this.jj_scanpos.next == null)
        this.jj_lastpos = (this.jj_scanpos = this.jj_scanpos.next = this.token_source.getNextToken());
      else
        this.jj_lastpos = (this.jj_scanpos = this.jj_scanpos.next);
    }
    else
    {
      this.jj_scanpos = this.jj_scanpos.next;
    }
    if (this.jj_rescan)
    {
      int i = 0;
      for (Token localToken = this.token; (localToken != null) && (localToken != this.jj_scanpos); localToken = localToken.next)
        ++i;
      if (localToken != null)
        jj_add_error_token(paramInt, i);
    }
    if (this.jj_scanpos.kind != paramInt)
      return true;
    if ((this.jj_la == 0) && (this.jj_scanpos == this.jj_lastpos))
      throw this.jj_ls;
    return false;
  }

  public final Token getNextToken()
  {
    if (this.token.next != null)
      this.token = this.token.next;
    else
      this.token = (this.token.next = this.token_source.getNextToken());
    this.jj_ntk = -1;
    this.jj_gen += 1;
    return this.token;
  }

  public final Token getToken(int paramInt)
  {
    Token localToken = (this.lookingAhead) ? this.jj_scanpos : this.token;
    for (int i = 0; i < paramInt; ++i)
      if (localToken.next != null)
        localToken = localToken.next;
      else
        localToken = localToken.next = this.token_source.getNextToken();
    return localToken;
  }

  private final int jj_ntk()
  {
    if ((this.jj_nt = this.token.next) == null)
      return this.jj_ntk = (this.token.next = this.token_source.getNextToken()).kind;
    return this.jj_ntk = this.jj_nt.kind;
  }

  private void jj_add_error_token(int paramInt1, int paramInt2)
  {
    if (paramInt2 >= 100)
      return;
    if (paramInt2 == this.jj_endpos + 1)
    {
      this.jj_lasttokens[(this.jj_endpos++)] = paramInt1;
    }
    else
    {
      if (this.jj_endpos == 0)
        return;
      this.jj_expentry = new int[this.jj_endpos];
      for (int i = 0; i < this.jj_endpos; ++i)
        this.jj_expentry[i] = this.jj_lasttokens[i];
      i = 0;
      Enumeration localEnumeration = this.jj_expentries.elements();
      do
      {
        int[] arrayOfInt;
        do
        {
          if (!localEnumeration.hasMoreElements())
            break label174;
          arrayOfInt = (int[])localEnumeration.nextElement();
        }
        while (arrayOfInt.length != this.jj_expentry.length);
        i = 1;
        for (int j = 0; j < this.jj_expentry.length; ++j)
        {
          if (arrayOfInt[j] == this.jj_expentry[j])
            continue;
          i = 0;
          break;
        }
      }
      while (i == 0);
      if (i == 0)
        label174: this.jj_expentries.addElement(this.jj_expentry);
      if (paramInt2 == 0)
        return;
      int tmp199_198 = paramInt2;
      this.jj_endpos = tmp199_198;
      this.jj_lasttokens[(tmp199_198 - 1)] = paramInt1;
    }
  }

  public ParseException generateParseException()
  {
    this.jj_expentries.removeAllElements();
    boolean[] arrayOfBoolean = new boolean[18];
    for (int i = 0; i < 18; ++i)
      arrayOfBoolean[i] = false;
    if (this.jj_kind >= 0)
    {
      arrayOfBoolean[this.jj_kind] = true;
      this.jj_kind = -1;
    }
    for (i = 0; i < 11; ++i)
    {
      if (this.jj_la1[i] != this.jj_gen)
        continue;
      for (j = 0; j < 32; ++j)
      {
        if ((jj_la1_0[i] & 1 << j) == 0)
          continue;
        arrayOfBoolean[j] = true;
      }
    }
    for (i = 0; i < 18; ++i)
    {
      if (arrayOfBoolean[i] == 0)
        continue;
      this.jj_expentry = new int[1];
      this.jj_expentry[0] = i;
      this.jj_expentries.addElement(this.jj_expentry);
    }
    this.jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] arrayOfInt = new int[this.jj_expentries.size()][];
    for (int j = 0; j < this.jj_expentries.size(); ++j)
      arrayOfInt[j] = ((int[])this.jj_expentries.elementAt(j));
    return new ParseException(this.token, arrayOfInt, tokenImage);
  }

  public final void enable_tracing()
  {
  }

  public final void disable_tracing()
  {
  }

  private final void jj_rescan_token()
  {
    this.jj_rescan = true;
    for (int i = 0; i < 2; ++i)
    {
      JJCalls localJJCalls = this.jj_2_rtns[i];
      do
      {
        if (localJJCalls.gen > this.jj_gen)
        {
          this.jj_la = localJJCalls.arg;
          this.jj_lastpos = (this.jj_scanpos = localJJCalls.first);
          switch (i)
          {
          case 0:
            jj_3_1();
            break;
          case 1:
            jj_3_2();
          }
        }
        localJJCalls = localJJCalls.next;
      }
      while (localJJCalls != null);
    }
    this.jj_rescan = false;
  }

  private final void jj_save(int paramInt1, int paramInt2)
  {
    for (JJCalls localJJCalls = this.jj_2_rtns[paramInt1]; localJJCalls.gen > this.jj_gen; localJJCalls = localJJCalls.next)
    {
      if (localJJCalls.next != null)
        continue;
      localJJCalls = localJJCalls.next = new JJCalls();
      break;
    }
    localJJCalls.gen = (this.jj_gen + paramInt2 - this.jj_la);
    localJJCalls.first = this.token;
    localJJCalls.arg = paramInt2;
  }

  static
  {
    jj_la1_0();
  }

  static final class JJCalls
  {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

  private static final class LookaheadSuccess extends Error
  {
    private LookaheadSuccess()
    {
    }

    LookaheadSuccess(QueryParser.1 param1)
    {
    }
  }
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.parser.QueryParser
 * JD-Core Version:    0.5.4
 */