package DLV;

import DLV.core.DlvIOHandler;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Vector;

public class Parser
{
  private DlvIOHandler dm = null;
  static final byte START = 0;
  static final byte END = 1;
  private static final byte BUILD_NAME = 2;
  private static final byte COMMENT = 3;
  private static final byte IGNORE = 4;
  private static final byte IGNORE_QUAD = 5;
  private int modelNumber = 0;
  private StringBuffer currentString = new StringBuffer();
  private boolean firstArg = true;
  private ArrayList arg = null;
  private ArrayList vectRis = null;
  private boolean bestModel = false;
  private int[][] costAndPriority = (int[][])null;
  private boolean positive = true;
  private Model modello = null;
  private String nomePredicato = null;
  private ArrayList coppie = null;
  private int[] coppia = null;
  private StringBuffer token = null;
  private byte status = 0;
  private static final byte PREDICATE = 6;
  private static final byte FIND_ARGUMENT = 7;
  private static final byte END_PREDICATE = 8;
  private static final byte FIND_OTHER_ARGUMENT = 9;
  private static final byte COMPUTE_STRING = 10;
  private static final byte ERROR = 11;
  private static final byte NEGATIVE_PEDICATE = 21;
  private static final byte FIRST = 12;
  private static final byte BEST_MODEL = 13;
  private static final byte BUILD_MODEL = 14;
  private static final byte FIND_COST = 15;
  private static final byte EAT_COST = 16;
  private static final byte BUILD_COST = 17;
  private static final byte COST = 18;
  private static final byte PRIO = 19;
  private static final byte PRE_END = 20;
  private static final StringBuffer best = new StringBuffer("Best");
  private static final StringBuffer cost = new StringBuffer("Cost");
  private static final StringBuffer weigth = new StringBuffer("Weight");
  private static final StringBuffer level = new StringBuffer("Level");
  private static final StringBuffer model = new StringBuffer("model");
  private long parsingTime = 0L;
  private Vector modelTimes = new Vector();

  public long elapsedTime()
  {
    return this.parsingTime;
  }

  public Vector modelElapsedTime()
  {
    return this.modelTimes;
  }

  private boolean isChar(char paramChar)
  {
    return (paramChar != ' ') && (paramChar != '\n') && (paramChar != '\r') && (paramChar != '\t') && (paramChar != '-') && (paramChar != '~') && (paramChar != ':') && (paramChar != '.') && (paramChar != '"') && (paramChar != ',') && (paramChar != ')') && (paramChar != '(') && (paramChar != '+') && (paramChar != '*') && (paramChar != '%') && (paramChar != '[') && (paramChar != ']');
  }

  public void parse(BufferedReader paramBufferedReader, DlvIOHandler paramDlvIOHandler)
  {
    this.parsingTime = System.currentTimeMillis();
    this.dm = paramDlvIOHandler;
    int i = 0;
    for (int j = 0; (j < paramDlvIOHandler.getInvocationParameters().length) && (i == 0); ++j)
    {
      if ((!paramDlvIOHandler.getInvocationParameters()[j].equals("-FB")) && (!paramDlvIOHandler.getInvocationParameters()[j].equals("-FC")) && (!paramDlvIOHandler.getInvocationParameters()[j].equals("-cautious")) && (!paramDlvIOHandler.getInvocationParameters()[j].equals("-brave")))
        continue;
      i = 1;
    }
    if (i != 0)
      startQueryParser(new InputHandler(paramBufferedReader));
    else
      startDatalogParser(new InputHandler(paramBufferedReader));
    this.dm = null;
    this.parsingTime = (System.currentTimeMillis() - this.parsingTime);
  }

  private void startQueryParser(InputHandler paramInputHandler)
  {
    throw new UnsupportedOperationException("feature not implemented yet");
  }

  private void startDatalogParser(InputHandler paramInputHandler)
  {
    this.status = 0;
    try
    {
      InputTokenizer localInputTokenizer = new InputTokenizer(paramInputHandler);
      while ((localInputTokenizer.hasMoreTokens()) && (this.status != 1) && (this.status != 11))
      {
        this.token = localInputTokenizer.nextToken();
        switch (this.status)
        {
        case 0:
        case 12:
          if (equals(this.token, best))
          {
            this.bestModel = true;
            this.status = 13;
          }
          if (this.token.charAt(0) == '{')
          {
            parseModel();
            this.status = 14;
          }
          break;
        case 13:
          if (this.token.charAt(0) == '{')
          {
            parseModel();
            this.status = 14;
          }
          break;
        case 15:
          if (equals(this.token, cost))
            this.status = 16;
          if (this.token.charAt(0) == '{')
          {
            insertModel();
            parseModel();
            this.status = 14;
          }
          break;
        case 16:
          if (this.token.charAt(0) == '<')
          {
            resetCoppie();
            this.status = 17;
          }
          break;
        case 17:
          if (this.token.charAt(0) == '[')
          {
            this.status = 18;
          }
          else
          {
            this.coppia = new int[2];
            this.coppia[0] = Integer.parseInt(this.token.toString());
            this.coppia[1] = 0;
            this.coppie.add(this.coppia);
            this.coppia = null;
            this.status = 20;
          }
          break;
        case 18:
          if (this.token.charAt(0) == ':')
          {
            this.status = 19;
          }
          else
          {
            this.coppia = new int[2];
            this.coppia[0] = Integer.parseInt(this.token.toString());
            this.coppia[1] = 0;
            this.status = 18;
          }
          break;
        case 19:
          if (this.token.charAt(0) == ']')
          {
            this.status = 20;
          }
          else
          {
            if (this.coppia == null)
            {
              this.coppia = new int[2];
              this.coppia[0] = 0;
            }
            this.coppia[1] = Integer.parseInt(this.token.toString());
            this.coppie.add(this.coppia);
            this.coppia = null;
            this.status = 19;
          }
          break;
        case 20:
          if (this.token.charAt(0) == ',')
            this.status = 17;
          if (this.token.charAt(0) == '>')
          {
            computeCost();
            insertModel();
            this.status = 12;
          }
          break;
        case 14:
          if (this.token.charAt(0) == '-')
            this.status = 21;
          if (isToken(this.token))
          {
            selectTuple(this.token);
            this.status = 6;
          }
          if (this.token.charAt(0) == '}')
            this.status = 15;
          break;
        case 21:
          this.positive = false;
          if (isToken(this.token))
          {
            selectTuple(this.token);
            this.status = 6;
          }
          else
          {
            this.status = 11;
          }
          break;
        case 6:
          if (this.token.charAt(0) != ' ')
            if (this.token.charAt(0) == '(')
            {
              this.status = 7;
            }
            else if ((this.token.charAt(0) == ',') || (this.token.equals(".")))
            {
              this.status = 14;
              insertTuple(this.token);
            }
            else if (this.token.charAt(0) == '}')
            {
              insertTuple(this.token);
              this.status = 15;
            }
            else
            {
              this.status = 11;
            }
          break;
        case 7:
          if (this.token.charAt(0) != ' ')
            if (this.token.charAt(0) == '"')
            {
              this.status = 10;
              insertNewString(true, this.token);
            }
            else if (this.token.charAt(0) == ')')
            {
              this.status = 14;
              insertTuple(this.token);
            }
            else if (isToken(this.token))
            {
              this.status = 8;
              insertFirstArgument(this.token);
            }
            else
            {
              this.status = 11;
            }
          break;
        case 8:
          if (this.token.charAt(0) != ' ')
            if (this.token.charAt(0) == ',')
            {
              this.status = 9;
            }
            else if (this.token.charAt(0) == ')')
            {
              this.status = 14;
              insertTuple(this.token);
            }
            else
            {
              this.status = 11;
            }
          break;
        case 9:
          if (this.token.charAt(0) != ' ')
            if (this.token.charAt(0) == '"')
            {
              this.status = 10;
              insertNewString(false, this.token);
            }
            else if (isToken(this.token))
            {
              this.status = 8;
              insertArgument(this.token);
            }
            else
            {
              this.status = 11;
            }
          break;
        case 10:
          insertString(this.token);
          if (this.token.charAt(0) == '"')
          {
            this.status = 8;
            if (this.firstArg)
              insertFirstArgument(this.currentString);
            else
              insertArgument(this.currentString);
          }
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 11:
        }
      }
      switch (this.status)
      {
      case 15:
        insertModel();
      case 1:
      case 12:
        break;
      case 0:
        break;
      default:
        throwError();
      }
    }
    catch (Throwable localThrowable)
    {
      throw new ParserException("ParserError Type: " + localThrowable.getClass() + " message: " + localThrowable.getMessage());
    }
  }

  private void resetCoppie()
  {
    this.coppie = new ArrayList();
    this.coppia = null;
  }

  private void computeCost()
  {
    this.costAndPriority = new int[this.coppie.size()][2];
    for (int i = 0; i < this.coppie.size(); ++i)
      this.costAndPriority[i] = ((int[])this.coppie.get(i));
    if (this.coppie.size() != 0)
      return;
    throw new DLVExceptionUncheked("An error occurred durin model cost parsing");
  }

  private void parseModel()
  {
    this.modelTimes.add(0, new Long(System.currentTimeMillis()));
    this.modelNumber += 1;
    this.modello = new Model();
  }

  private void insertModel()
  {
    if (this.dm == null)
      return;
    if (this.costAndPriority != null)
    {
      this.modello.setBest(this.bestModel);
      this.modello.setCostAndPriority(this.costAndPriority);
    }
    this.dm.addModel(this.modello);
    this.modello = null;
    this.bestModel = false;
    this.costAndPriority = ((int[][])null);
    this.modelTimes.setElementAt(new Long(System.currentTimeMillis() - ((Long)this.modelTimes.elementAt(0)).longValue()), 0);
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
    if ((i == 45) || (i == 58) || (i == 32) || (i == 40) || (i == 41) || (i == 44) || (i == 46) || (i == 126) || (i == 34) || (i == 125))
      return false;
    return !paramStringBuffer.toString().equals("not");
  }

  private void selectTuple(StringBuffer paramStringBuffer)
  {
    String str = paramStringBuffer.toString();
    this.nomePredicato = str;
  }

  private void throwError()
  {
    throw new ParserException("An error occurred parsing a program.");
  }

  private void insertTuple(StringBuffer paramStringBuffer)
  {
    if (this.arg == null)
      this.arg = new ArrayList();
    addPredicate(this.modello, this.nomePredicato, this.arg, this.positive);
    this.positive = true;
    this.arg = null;
  }

  private void addPredicate(Model paramModel, String paramString, ArrayList paramArrayList, boolean paramBoolean)
  {
    Predicate localPredicate = paramModel.getPredicate(paramString);
    if (localPredicate == null)
    {
      localPredicate = this.dm.getOutputDescriptor().getDescriptor(paramString).buildPredicate(paramString, paramArrayList.size(), paramModel, this.modelNumber);
      paramModel.addPredicate(localPredicate);
    }
    Predicate tmp54_52 = localPredicate;
    tmp54_52.getClass();
    localPredicate.addLiteral(new Predicate.Literal(tmp54_52, Tools.arrayListToStringArray(paramArrayList), paramBoolean));
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
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.Parser
 * JD-Core Version:    0.5.4
 */