package DLV.parser;

import java.io.IOException;
import java.io.PrintStream;

public class QueryParserTokenManager
  implements QueryParserConstants
{
  public PrintStream debugStream = System.out;
  static final long[] jjbitVec0 = { 2301339413881290750L, -16384L, 4294967295L, 432345564227567616L };
  static final long[] jjbitVec2 = { 0L, 0L, 0L, -36028797027352577L };
  static final long[] jjbitVec3 = { 0L, -1L, -1L, -1L };
  static final long[] jjbitVec4 = { -1L, -1L, 65535L, 0L };
  static final long[] jjbitVec5 = { -1L, -1L, 0L, 0L };
  static final long[] jjbitVec6 = { 70368744177663L, 0L, 0L, 0L };
  static final long[] jjbitVec7 = { -2L, -1L, -1L, -1L };
  static final long[] jjbitVec8 = { 0L, 0L, -1L, -1L };
  static final int[] jjnextStates = { 5, 6, 8, 5, 6, 10, 8, 7, 9, 11 };
  public static final String[] jjstrLiteralImages = { "", null, null, null, "(", ")", ",", "-", "{", "}", "is cautiously ", ", evidenced by", "is bravely ", null, null, null, null, null };
  public static final String[] lexStateNames = { "DEFAULT" };
  static final long[] jjtoToken = { 163825L };
  static final long[] jjtoSkip = { 14L };
  protected JavaCharStream input_stream;
  private final int[] jjrounds = new int[13];
  private final int[] jjstateSet = new int[26];
  protected char curChar;
  int curLexState = 0;
  int defaultLexState = 0;
  int jjnewStateCnt;
  int jjround;
  int jjmatchedPos;
  int jjmatchedKind;

  public void setDebugStream(PrintStream paramPrintStream)
  {
    this.debugStream = paramPrintStream;
  }

  private final int jjStopStringLiteralDfa_0(int paramInt, long paramLong)
  {
    switch (paramInt)
    {
    case 0:
      if ((paramLong & 0x80) != 0L)
        return 3;
      if ((paramLong & 0x1400) != 0L)
      {
        this.jjmatchedKind = 14;
        return 3;
      }
      return -1;
    case 1:
      if ((paramLong & 0x1400) != 0L)
      {
        this.jjmatchedKind = 14;
        this.jjmatchedPos = 1;
        return 3;
      }
      return -1;
    case 2:
      if ((paramLong & 0x1400) != 0L)
      {
        if (this.jjmatchedPos < 1)
        {
          this.jjmatchedKind = 14;
          this.jjmatchedPos = 1;
        }
        return -1;
      }
      return -1;
    case 3:
      if ((paramLong & 0x1400) != 0L)
      {
        if (this.jjmatchedPos < 1)
        {
          this.jjmatchedKind = 14;
          this.jjmatchedPos = 1;
        }
        return -1;
      }
      return -1;
    case 4:
      if ((paramLong & 0x1400) != 0L)
      {
        if (this.jjmatchedPos < 1)
        {
          this.jjmatchedKind = 14;
          this.jjmatchedPos = 1;
        }
        return -1;
      }
      return -1;
    case 5:
      if ((paramLong & 0x1400) != 0L)
      {
        if (this.jjmatchedPos < 1)
        {
          this.jjmatchedKind = 14;
          this.jjmatchedPos = 1;
        }
        return -1;
      }
      return -1;
    case 6:
      if ((paramLong & 0x1400) != 0L)
      {
        if (this.jjmatchedPos < 1)
        {
          this.jjmatchedKind = 14;
          this.jjmatchedPos = 1;
        }
        return -1;
      }
      return -1;
    case 7:
      if ((paramLong & 0x1400) != 0L)
      {
        if (this.jjmatchedPos < 1)
        {
          this.jjmatchedKind = 14;
          this.jjmatchedPos = 1;
        }
        return -1;
      }
      return -1;
    case 8:
      if ((paramLong & 0x1400) != 0L)
      {
        if (this.jjmatchedPos < 1)
        {
          this.jjmatchedKind = 14;
          this.jjmatchedPos = 1;
        }
        return -1;
      }
      return -1;
    case 9:
      if ((paramLong & 0x1400) != 0L)
      {
        if (this.jjmatchedPos < 1)
        {
          this.jjmatchedKind = 14;
          this.jjmatchedPos = 1;
        }
        return -1;
      }
      return -1;
    case 10:
      if ((paramLong & 0x1400) != 0L)
      {
        if (this.jjmatchedPos < 1)
        {
          this.jjmatchedKind = 14;
          this.jjmatchedPos = 1;
        }
        return -1;
      }
      return -1;
    case 11:
      if ((paramLong & 0x400) != 0L)
      {
        if (this.jjmatchedPos < 1)
        {
          this.jjmatchedKind = 14;
          this.jjmatchedPos = 1;
        }
        return -1;
      }
      return -1;
    case 12:
      if ((paramLong & 0x400) != 0L)
      {
        if (this.jjmatchedPos < 1)
        {
          this.jjmatchedKind = 14;
          this.jjmatchedPos = 1;
        }
        return -1;
      }
      return -1;
    }
    return -1;
  }

  private final int jjStartNfa_0(int paramInt, long paramLong)
  {
    return jjMoveNfa_0(jjStopStringLiteralDfa_0(paramInt, paramLong), paramInt + 1);
  }

  private final int jjStopAtPos(int paramInt1, int paramInt2)
  {
    this.jjmatchedKind = paramInt2;
    this.jjmatchedPos = paramInt1;
    return paramInt1 + 1;
  }

  private final int jjStartNfaWithStates_0(int paramInt1, int paramInt2, int paramInt3)
  {
    this.jjmatchedKind = paramInt2;
    this.jjmatchedPos = paramInt1;
    try
    {
      this.curChar = this.input_stream.readChar();
    }
    catch (IOException localIOException)
    {
      return paramInt1 + 1;
    }
    return jjMoveNfa_0(paramInt3, paramInt1 + 1);
  }

  private final int jjMoveStringLiteralDfa0_0()
  {
    switch (this.curChar)
    {
    case '(':
      return jjStopAtPos(0, 4);
    case ')':
      return jjStopAtPos(0, 5);
    case ',':
      this.jjmatchedKind = 6;
      return jjMoveStringLiteralDfa1_0(2048L);
    case '-':
      return jjStartNfaWithStates_0(0, 7, 3);
    case 'i':
      return jjMoveStringLiteralDfa1_0(5120L);
    case '{':
      return jjStopAtPos(0, 8);
    case '}':
      return jjStopAtPos(0, 9);
    }
    return jjMoveNfa_0(0, 0);
  }

  private final int jjMoveStringLiteralDfa1_0(long paramLong)
  {
    try
    {
      this.curChar = this.input_stream.readChar();
    }
    catch (IOException localIOException)
    {
      jjStopStringLiteralDfa_0(0, paramLong);
      return 1;
    }
    switch (this.curChar)
    {
    case ' ':
      return jjMoveStringLiteralDfa2_0(paramLong, 2048L);
    case 's':
      return jjMoveStringLiteralDfa2_0(paramLong, 5120L);
    }
    return jjStartNfa_0(0, paramLong);
  }

  private final int jjMoveStringLiteralDfa2_0(long paramLong1, long paramLong2)
  {
    if ((paramLong2 &= paramLong1) == 0L)
      return jjStartNfa_0(0, paramLong1);
    try
    {
      this.curChar = this.input_stream.readChar();
    }
    catch (IOException localIOException)
    {
      jjStopStringLiteralDfa_0(1, paramLong2);
      return 2;
    }
    switch (this.curChar)
    {
    case ' ':
      return jjMoveStringLiteralDfa3_0(paramLong2, 5120L);
    case 'e':
      return jjMoveStringLiteralDfa3_0(paramLong2, 2048L);
    }
    return jjStartNfa_0(1, paramLong2);
  }

  private final int jjMoveStringLiteralDfa3_0(long paramLong1, long paramLong2)
  {
    if ((paramLong2 &= paramLong1) == 0L)
      return jjStartNfa_0(1, paramLong1);
    try
    {
      this.curChar = this.input_stream.readChar();
    }
    catch (IOException localIOException)
    {
      jjStopStringLiteralDfa_0(2, paramLong2);
      return 3;
    }
    switch (this.curChar)
    {
    case 'b':
      return jjMoveStringLiteralDfa4_0(paramLong2, 4096L);
    case 'c':
      return jjMoveStringLiteralDfa4_0(paramLong2, 1024L);
    case 'v':
      return jjMoveStringLiteralDfa4_0(paramLong2, 2048L);
    }
    return jjStartNfa_0(2, paramLong2);
  }

  private final int jjMoveStringLiteralDfa4_0(long paramLong1, long paramLong2)
  {
    if ((paramLong2 &= paramLong1) == 0L)
      return jjStartNfa_0(2, paramLong1);
    try
    {
      this.curChar = this.input_stream.readChar();
    }
    catch (IOException localIOException)
    {
      jjStopStringLiteralDfa_0(3, paramLong2);
      return 4;
    }
    switch (this.curChar)
    {
    case 'a':
      return jjMoveStringLiteralDfa5_0(paramLong2, 1024L);
    case 'i':
      return jjMoveStringLiteralDfa5_0(paramLong2, 2048L);
    case 'r':
      return jjMoveStringLiteralDfa5_0(paramLong2, 4096L);
    }
    return jjStartNfa_0(3, paramLong2);
  }

  private final int jjMoveStringLiteralDfa5_0(long paramLong1, long paramLong2)
  {
    if ((paramLong2 &= paramLong1) == 0L)
      return jjStartNfa_0(3, paramLong1);
    try
    {
      this.curChar = this.input_stream.readChar();
    }
    catch (IOException localIOException)
    {
      jjStopStringLiteralDfa_0(4, paramLong2);
      return 5;
    }
    switch (this.curChar)
    {
    case 'a':
      return jjMoveStringLiteralDfa6_0(paramLong2, 4096L);
    case 'd':
      return jjMoveStringLiteralDfa6_0(paramLong2, 2048L);
    case 'u':
      return jjMoveStringLiteralDfa6_0(paramLong2, 1024L);
    }
    return jjStartNfa_0(4, paramLong2);
  }

  private final int jjMoveStringLiteralDfa6_0(long paramLong1, long paramLong2)
  {
    if ((paramLong2 &= paramLong1) == 0L)
      return jjStartNfa_0(4, paramLong1);
    try
    {
      this.curChar = this.input_stream.readChar();
    }
    catch (IOException localIOException)
    {
      jjStopStringLiteralDfa_0(5, paramLong2);
      return 6;
    }
    switch (this.curChar)
    {
    case 'e':
      return jjMoveStringLiteralDfa7_0(paramLong2, 2048L);
    case 't':
      return jjMoveStringLiteralDfa7_0(paramLong2, 1024L);
    case 'v':
      return jjMoveStringLiteralDfa7_0(paramLong2, 4096L);
    }
    return jjStartNfa_0(5, paramLong2);
  }

  private final int jjMoveStringLiteralDfa7_0(long paramLong1, long paramLong2)
  {
    if ((paramLong2 &= paramLong1) == 0L)
      return jjStartNfa_0(5, paramLong1);
    try
    {
      this.curChar = this.input_stream.readChar();
    }
    catch (IOException localIOException)
    {
      jjStopStringLiteralDfa_0(6, paramLong2);
      return 7;
    }
    switch (this.curChar)
    {
    case 'e':
      return jjMoveStringLiteralDfa8_0(paramLong2, 4096L);
    case 'i':
      return jjMoveStringLiteralDfa8_0(paramLong2, 1024L);
    case 'n':
      return jjMoveStringLiteralDfa8_0(paramLong2, 2048L);
    }
    return jjStartNfa_0(6, paramLong2);
  }

  private final int jjMoveStringLiteralDfa8_0(long paramLong1, long paramLong2)
  {
    if ((paramLong2 &= paramLong1) == 0L)
      return jjStartNfa_0(6, paramLong1);
    try
    {
      this.curChar = this.input_stream.readChar();
    }
    catch (IOException localIOException)
    {
      jjStopStringLiteralDfa_0(7, paramLong2);
      return 8;
    }
    switch (this.curChar)
    {
    case 'c':
      return jjMoveStringLiteralDfa9_0(paramLong2, 2048L);
    case 'l':
      return jjMoveStringLiteralDfa9_0(paramLong2, 4096L);
    case 'o':
      return jjMoveStringLiteralDfa9_0(paramLong2, 1024L);
    }
    return jjStartNfa_0(7, paramLong2);
  }

  private final int jjMoveStringLiteralDfa9_0(long paramLong1, long paramLong2)
  {
    if ((paramLong2 &= paramLong1) == 0L)
      return jjStartNfa_0(7, paramLong1);
    try
    {
      this.curChar = this.input_stream.readChar();
    }
    catch (IOException localIOException)
    {
      jjStopStringLiteralDfa_0(8, paramLong2);
      return 9;
    }
    switch (this.curChar)
    {
    case 'e':
      return jjMoveStringLiteralDfa10_0(paramLong2, 2048L);
    case 'u':
      return jjMoveStringLiteralDfa10_0(paramLong2, 1024L);
    case 'y':
      return jjMoveStringLiteralDfa10_0(paramLong2, 4096L);
    }
    return jjStartNfa_0(8, paramLong2);
  }

  private final int jjMoveStringLiteralDfa10_0(long paramLong1, long paramLong2)
  {
    if ((paramLong2 &= paramLong1) == 0L)
      return jjStartNfa_0(8, paramLong1);
    try
    {
      this.curChar = this.input_stream.readChar();
    }
    catch (IOException localIOException)
    {
      jjStopStringLiteralDfa_0(9, paramLong2);
      return 10;
    }
    switch (this.curChar)
    {
    case ' ':
      if ((paramLong2 & 0x1000) == 0L)
        break label121;
      return jjStopAtPos(10, 12);
    case 'd':
      return jjMoveStringLiteralDfa11_0(paramLong2, 2048L);
    case 's':
      return jjMoveStringLiteralDfa11_0(paramLong2, 1024L);
    }
    label121: return jjStartNfa_0(9, paramLong2);
  }

  private final int jjMoveStringLiteralDfa11_0(long paramLong1, long paramLong2)
  {
    if ((paramLong2 &= paramLong1) == 0L)
      return jjStartNfa_0(9, paramLong1);
    try
    {
      this.curChar = this.input_stream.readChar();
    }
    catch (IOException localIOException)
    {
      jjStopStringLiteralDfa_0(10, paramLong2);
      return 11;
    }
    switch (this.curChar)
    {
    case ' ':
      return jjMoveStringLiteralDfa12_0(paramLong2, 2048L);
    case 'l':
      return jjMoveStringLiteralDfa12_0(paramLong2, 1024L);
    }
    return jjStartNfa_0(10, paramLong2);
  }

  private final int jjMoveStringLiteralDfa12_0(long paramLong1, long paramLong2)
  {
    if ((paramLong2 &= paramLong1) == 0L)
      return jjStartNfa_0(10, paramLong1);
    try
    {
      this.curChar = this.input_stream.readChar();
    }
    catch (IOException localIOException)
    {
      jjStopStringLiteralDfa_0(11, paramLong2);
      return 12;
    }
    switch (this.curChar)
    {
    case 'b':
      return jjMoveStringLiteralDfa13_0(paramLong2, 2048L);
    case 'y':
      return jjMoveStringLiteralDfa13_0(paramLong2, 1024L);
    }
    return jjStartNfa_0(11, paramLong2);
  }

  private final int jjMoveStringLiteralDfa13_0(long paramLong1, long paramLong2)
  {
    if ((paramLong2 &= paramLong1) == 0L)
      return jjStartNfa_0(11, paramLong1);
    try
    {
      this.curChar = this.input_stream.readChar();
    }
    catch (IOException localIOException)
    {
      jjStopStringLiteralDfa_0(12, paramLong2);
      return 13;
    }
    switch (this.curChar)
    {
    case ' ':
      if ((paramLong2 & 0x400) == 0L)
        break label114;
      return jjStopAtPos(13, 10);
    case 'y':
      if ((paramLong2 & 0x800) == 0L)
        break label114;
      return jjStopAtPos(13, 11);
    }
    label114: return jjStartNfa_0(12, paramLong2);
  }

  private final void jjCheckNAdd(int paramInt)
  {
    if (this.jjrounds[paramInt] == this.jjround)
      return;
    this.jjstateSet[(this.jjnewStateCnt++)] = paramInt;
    this.jjrounds[paramInt] = this.jjround;
  }

  private final void jjAddStates(int paramInt1, int paramInt2)
  {
    do
      this.jjstateSet[(this.jjnewStateCnt++)] = jjnextStates[paramInt1];
    while (paramInt1++ != paramInt2);
  }

  private final void jjCheckNAddTwoStates(int paramInt1, int paramInt2)
  {
    jjCheckNAdd(paramInt1);
    jjCheckNAdd(paramInt2);
  }

  private final void jjCheckNAddStates(int paramInt1, int paramInt2)
  {
    do
      jjCheckNAdd(jjnextStates[paramInt1]);
    while (paramInt1++ != paramInt2);
  }

  private final void jjCheckNAddStates(int paramInt)
  {
    jjCheckNAdd(jjnextStates[paramInt]);
    jjCheckNAdd(jjnextStates[(paramInt + 1)]);
  }

  private final int jjMoveNfa_0(int paramInt1, int paramInt2)
  {
    int i = 0;
    this.jjnewStateCnt = 13;
    int j = 1;
    this.jjstateSet[0] = paramInt1;
    int k = 2147483647;
    while (true)
    {
      if (++this.jjround == 2147483647)
        ReInitRounds();
      long l1;
      if (this.curChar < '@')
      {
        l1 = 1L << this.curChar;
        do
          switch (this.jjstateSet[(--j)])
          {
          case 0:
            if ((0x0 & l1) != 0L)
            {
              if (k > 14)
                k = 14;
              jjCheckNAdd(3);
            }
            else if ((0x2400 & l1) != 0L)
            {
              if (k > 13)
                k = 13;
            }
            else if (this.curChar == '"')
            {
              jjCheckNAddStates(0, 2);
            }
            if (this.curChar != '\r')
              continue;
            this.jjstateSet[(this.jjnewStateCnt++)] = 1;
            break;
          case 1:
            if ((this.curChar != '\n') || (k <= 13))
              continue;
            k = 13;
            break;
          case 2:
            if (this.curChar != '\r')
              continue;
            this.jjstateSet[(this.jjnewStateCnt++)] = 1;
            break;
          case 3:
            if ((0x0 & l1) == 0L)
              continue;
            if (k > 14)
              k = 14;
            jjCheckNAdd(3);
            break;
          case 4:
            if (this.curChar != '"')
              continue;
            jjCheckNAddStates(0, 2);
            break;
          case 5:
            if ((0xFFFFDBFF & l1) == 0L)
              continue;
            jjCheckNAddStates(0, 2);
            break;
          case 7:
            if ((0x0 & l1) == 0L)
              continue;
            jjCheckNAddStates(0, 2);
            break;
          case 8:
            if ((this.curChar != '"') || (k <= 17))
              continue;
            k = 17;
            break;
          case 9:
            if ((0x0 & l1) == 0L)
              continue;
            jjCheckNAddStates(3, 6);
            break;
          case 10:
            if ((0x0 & l1) == 0L)
              continue;
            jjCheckNAddStates(0, 2);
            break;
          case 11:
            if ((0x0 & l1) == 0L)
              continue;
            this.jjstateSet[(this.jjnewStateCnt++)] = 12;
            break;
          case 12:
            if ((0x0 & l1) == 0L)
              continue;
            jjCheckNAdd(10);
          case 6:
          }
        while (j != i);
      }
      else if (this.curChar < '')
      {
        l1 = 1L << (this.curChar & 0x3F);
        do
          switch (this.jjstateSet[(--j)])
          {
          case 0:
          case 3:
            if ((0x87FFFFFE & l1) == 0L)
              continue;
            if (k > 14)
              k = 14;
            jjCheckNAdd(3);
            break;
          case 5:
            if ((0xEFFFFFFF & l1) == 0L)
              continue;
            jjCheckNAddStates(0, 2);
            break;
          case 6:
            if (this.curChar != '\\')
              continue;
            jjAddStates(7, 9);
            break;
          case 7:
            if ((0x10000000 & l1) == 0L)
              continue;
            jjCheckNAddStates(0, 2);
          case 1:
          case 2:
          case 4:
          }
        while (j != i);
      }
      else
      {
        int l = this.curChar >> '\b';
        int i1 = l >> 6;
        long l2 = 1L << (l & 0x3F);
        int i2 = (this.curChar & 0xFF) >> '\006';
        long l3 = 1L << (this.curChar & 0x3F);
        do
          switch (this.jjstateSet[(--j)])
          {
          case 0:
          case 3:
            if (!jjCanMove_0(l, i1, i2, l2, l3))
              continue;
            if (k > 14)
              k = 14;
            jjCheckNAdd(3);
            break;
          case 5:
            if (!jjCanMove_1(l, i1, i2, l2, l3))
              continue;
            jjAddStates(0, 2);
          }
        while (j != i);
      }
      if (k != 2147483647)
      {
        this.jjmatchedKind = k;
        this.jjmatchedPos = paramInt2;
        k = 2147483647;
      }
      ++paramInt2;
      if ((j = this.jjnewStateCnt) == (i = 13 - (this.jjnewStateCnt = i)))
        return paramInt2;
      try
      {
        this.curChar = this.input_stream.readChar();
      }
      catch (IOException localIOException)
      {
      }
    }
    return paramInt2;
  }

  private static final boolean jjCanMove_0(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2)
  {
    switch (paramInt1)
    {
    case 0:
      return (jjbitVec2[paramInt3] & paramLong2) != 0L;
    case 48:
      return (jjbitVec3[paramInt3] & paramLong2) != 0L;
    case 49:
      return (jjbitVec4[paramInt3] & paramLong2) != 0L;
    case 51:
      return (jjbitVec5[paramInt3] & paramLong2) != 0L;
    case 61:
      return (jjbitVec6[paramInt3] & paramLong2) != 0L;
    }
    return (jjbitVec0[paramInt2] & paramLong1) != 0L;
  }

  private static final boolean jjCanMove_1(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2)
  {
    switch (paramInt1)
    {
    case 0:
      return (jjbitVec8[paramInt3] & paramLong2) != 0L;
    }
    return (jjbitVec7[paramInt2] & paramLong1) != 0L;
  }

  public QueryParserTokenManager(JavaCharStream paramJavaCharStream)
  {
    this.input_stream = paramJavaCharStream;
  }

  public QueryParserTokenManager(JavaCharStream paramJavaCharStream, int paramInt)
  {
    this(paramJavaCharStream);
    SwitchTo(paramInt);
  }

  public void ReInit(JavaCharStream paramJavaCharStream)
  {
    this.jjmatchedPos = (this.jjnewStateCnt = 0);
    this.curLexState = this.defaultLexState;
    this.input_stream = paramJavaCharStream;
    ReInitRounds();
  }

  private final void ReInitRounds()
  {
    this.jjround = -2147483647;
    int i = 13;
    while (i-- > 0)
      this.jjrounds[i] = -2147483648;
  }

  public void ReInit(JavaCharStream paramJavaCharStream, int paramInt)
  {
    ReInit(paramJavaCharStream);
    SwitchTo(paramInt);
  }

  public void SwitchTo(int paramInt)
  {
    if ((paramInt >= 1) || (paramInt < 0))
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + paramInt + ". State unchanged.", 2);
    this.curLexState = paramInt;
  }

  protected Token jjFillToken()
  {
    Token localToken = Token.newToken(this.jjmatchedKind);
    localToken.kind = this.jjmatchedKind;
    String str = jjstrLiteralImages[this.jjmatchedKind];
    localToken.image = ((str == null) ? this.input_stream.GetImage() : str);
    localToken.beginLine = this.input_stream.getBeginLine();
    localToken.beginColumn = this.input_stream.getBeginColumn();
    localToken.endLine = this.input_stream.getEndLine();
    localToken.endColumn = this.input_stream.getEndColumn();
    return localToken;
  }

  public Token getNextToken()
  {
    Object localObject = null;
    int i = 0;
    do
    {
      while (true)
      {
        try
        {
          this.curChar = this.input_stream.BeginToken();
        }
        catch (IOException localIOException1)
        {
          this.jjmatchedKind = 0;
          localToken = jjFillToken();
          return localToken;
        }
        try
        {
          this.input_stream.backup(0);
          while ((this.curChar <= ' ') && ((0x1200 & 1L << this.curChar) != 0L))
            this.curChar = this.input_stream.BeginToken();
        }
        catch (IOException localIOException2)
        {
        }
      }
      this.jjmatchedKind = 2147483647;
      this.jjmatchedPos = 0;
      i = jjMoveStringLiteralDfa0_0();
      if (this.jjmatchedKind == 2147483647)
        break label173;
      if (this.jjmatchedPos + 1 >= i)
        continue;
      this.input_stream.backup(i - this.jjmatchedPos - 1);
    }
    while ((jjtoToken[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) == 0L);
    Token localToken = jjFillToken();
    return localToken;
    label173: int j = this.input_stream.getEndLine();
    int k = this.input_stream.getEndColumn();
    String str = null;
    boolean bool = false;
    try
    {
      this.input_stream.readChar();
      this.input_stream.backup(1);
    }
    catch (IOException localIOException3)
    {
      bool = true;
      str = (i <= 1) ? "" : this.input_stream.GetImage();
      if ((this.curChar != '\n') && (this.curChar != '\r'))
        break label268;
      ++j;
      k = 0;
    }
    break label271:
    label268: ++k;
    if (!bool)
    {
      label271: this.input_stream.backup(1);
      str = (i <= 1) ? "" : this.input_stream.GetImage();
    }
    throw new TokenMgrError(bool, this.curLexState, j, k, str, this.curChar, 0);
  }
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.parser.QueryParserTokenManager
 * JD-Core Version:    0.5.4
 */