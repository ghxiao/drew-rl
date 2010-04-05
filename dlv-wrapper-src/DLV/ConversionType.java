package DLV;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

public final class ConversionType
{
  public static final DBNull DBNULL_CLASS = new DBNull(null);
  public static final String DBNULL = "null";
  public static final byte UNIMPLEMENTED = 0;
  public static final byte STRING = 2;
  public static final byte BOOLEAN = 3;
  public static final byte BIGDECIMAL = 4;
  public static final byte DATE = 5;
  public static final byte TIME = 6;
  public static final byte QUOTED_DOUBLE = 7;
  public static final byte TRUNC_DOUBLE = 8;
  public static final byte ROUND_DOUBLE = 9;
  public static final byte QUOTED_FLOAT = 10;
  public static final byte TRUNC_FLOAT = 11;
  public static final byte ROUND_FLOAT = 12;
  public static final byte QUOTED_INT = 13;
  public static final byte CONST_INT = 14;
  public static final byte QUOTED_LONG = 15;
  public static final byte CONST_LONG = 16;
  public static final byte QUOTED_SHORT = 17;
  public static final byte CONST_SHORT = 18;
  public static final byte QUOTED_BYTE = 19;
  public static final byte CONST_BYTE = 20;
  public static final byte OBJECT = 21;
  public static final byte NULL = 22;

  public static byte sqlToDlvTypes(int paramInt)
  {
    switch (paramInt)
    {
    case -7:
    case -2:
      return 2;
    case 1:
      return 2;
    case 91:
      return 5;
    case 6:
      return 12;
    case 2:
    case 3:
    case 7:
    case 8:
      return 9;
    case -5:
    case -4:
    case -3:
      return 16;
    case -6:
    case 4:
    case 5:
      return 14;
    case 2000:
      return 21;
    case 0:
      return 22;
    case 93:
    case 1111:
    case 2001:
    case 2002:
    case 2003:
    case 2004:
    case 2005:
    case 2006:
      return 0;
    case 92:
      return 6;
    case -1:
    case 12:
      return 2;
    }
    return 0;
  }

  public static boolean isValid(String paramString)
  {
    if (isQuoted(paramString))
      return true;
    return isConst(paramString);
  }

  public static boolean isConst(String paramString)
  {
    try
    {
      int i = 0;
      char c = paramString.charAt(i);
      if ((Character.isUpperCase(c)) || (c == '_'))
        return false;
      while (i < paramString.length())
      {
        c = paramString.charAt(i);
        if ((c == ' ') || (c == '\n') || (c == '\r') || (c == '#') || (c == '+') || (c == '-') || (c == '*') || (c == '/') || (c == '%') || (c == '"') || (c == '.'))
          return false;
        ++i;
      }
      return true;
    }
    catch (Throwable localThrowable)
    {
    }
    return false;
  }

  public static boolean isQuoted(String paramString)
  {
    if (paramString.length() < 2)
      return false;
    return (paramString.charAt(0) == '"') && (paramString.charAt(paramString.length() - 1) == '"');
  }

  public static String cutQuotes(String paramString)
  {
    if (isQuoted(paramString))
      return paramString.substring(1, paramString.length() - 1);
    return paramString;
  }

  static String addQuotes(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer("\"");
    localStringBuffer.append(paramString);
    localStringBuffer.append('"');
    return localStringBuffer.toString();
  }

  static String ckeckStringAndCutQuotes(String paramString1, boolean paramBoolean, String paramString2)
  {
    if (paramBoolean)
    {
      if (isQuoted(paramString1))
        return paramString1.substring(1, paramString1.length() - 1);
      throw new MalformedTermException(paramString2);
    }
    if (isConst(paramString1))
      return paramString1;
    throw new MalformedTermException(paramString2);
  }

  static Object stringToObject(String paramString, byte paramByte)
  {
    switch (paramByte)
    {
    case 2:
      if (isValid(paramString))
        return paramString;
      throw new MalformedTermException("the object is not a valid string");
    case 4:
      try
      {
        return new BigDecimal(ckeckStringAndCutQuotes(paramString, true, "the object is not a big decimal"));
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        throw new MalformedTermException("the object is not a big decimal");
      }
    case 3:
      if (paramString.equalsIgnoreCase("true"))
        return new Boolean(true);
      return new Boolean(false);
    case 19:
      paramString = ckeckStringAndCutQuotes(paramString, true, "the object is not a quoted byte");
    case 20:
      try
      {
        return new Byte(paramString);
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        throw new MalformedTermException("the object is not a (quoted) byte");
      }
    case 13:
      paramString = ckeckStringAndCutQuotes(paramString, true, "the object is not a (quoted) integer");
    case 14:
      try
      {
        return new Integer(paramString);
      }
      catch (NumberFormatException localNumberFormatException3)
      {
        throw new MalformedTermException("the object is not an integer");
      }
    case 15:
      paramString = ckeckStringAndCutQuotes(paramString, true, "the object is not a (quoted) long");
    case 16:
      try
      {
        return new Long(paramString);
      }
      catch (NumberFormatException localNumberFormatException4)
      {
        throw new MalformedTermException("the object is not a long");
      }
    case 17:
      paramString = ckeckStringAndCutQuotes(paramString, true, "the object is not a (quoted) short");
    case 18:
      try
      {
        return new Short(paramString);
      }
      catch (NumberFormatException localNumberFormatException5)
      {
        throw new MalformedTermException("the object is not an short");
      }
    case 5:
      try
      {
        return Date.valueOf(ckeckStringAndCutQuotes(paramString, true, "the object is not a date"));
      }
      catch (IllegalArgumentException localIllegalArgumentException1)
      {
        throw new MalformedTermException("the object is not a date");
      }
    case 6:
      try
      {
        return Time.valueOf(ckeckStringAndCutQuotes(paramString, true, "the object is not an time"));
      }
      catch (IllegalArgumentException localIllegalArgumentException2)
      {
        throw new MalformedTermException("the object is not an time");
      }
    case 10:
      try
      {
        return new Float(ckeckStringAndCutQuotes(paramString, true, "the object is not a (quoted) float"));
      }
      catch (NumberFormatException localNumberFormatException6)
      {
        throw new MalformedTermException("the object is not a float");
      }
    case 12:
      try
      {
        return new Float(Math.round(Math.abs(Float.parseFloat(paramString))));
      }
      catch (NumberFormatException localNumberFormatException7)
      {
        throw new MalformedTermException("the object is not a float");
      }
    case 11:
      try
      {
        return new Float(Math.floor(Math.abs(Float.parseFloat(paramString))));
      }
      catch (NumberFormatException localNumberFormatException8)
      {
        throw new MalformedTermException("the object is not a float");
      }
    case 7:
      try
      {
        return new Double(ckeckStringAndCutQuotes(paramString, true, "the object is not a (quoted) double"));
      }
      catch (NumberFormatException localNumberFormatException9)
      {
        throw new MalformedTermException("the object is not a double");
      }
    case 9:
      try
      {
        return new Double(Math.round(Math.abs(Double.parseDouble(paramString))));
      }
      catch (NumberFormatException localNumberFormatException10)
      {
        throw new MalformedTermException("the object is not a double");
      }
    case 8:
      try
      {
        return new Double(Math.floor(Math.abs(Double.parseDouble(paramString))));
      }
      catch (NumberFormatException localNumberFormatException11)
      {
        throw new MalformedTermException("the object is not a double");
      }
    case 22:
      if (paramString.equalsIgnoreCase("null"))
        return DBNULL_CLASS;
      throw new MalformedTermException("the object is not a database null");
    case 0:
    case 21:
      throw new MalformedTermException("not implemented yet");
    case 1:
    }
    throw new MalformedTermException();
  }

  static String objectToString(Object paramObject, byte paramByte)
  {
    if ((paramByte == 21) || (paramByte == 0))
      throw new MalformedTermException("not implemented yet");
    if (paramObject instanceof String)
    {
      String str = (String)paramObject;
      switch (paramByte)
      {
      case 2:
        if (isValid(str))
          return str;
        throw new MalformedTermException("the string is not valid");
      case 4:
        try
        {
          new BigDecimal(ckeckStringAndCutQuotes(str, true, "the object is not a big decimal"));
          return str;
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          throw new MalformedTermException("the object is not a big decimal");
        }
      case 3:
        if (str.equalsIgnoreCase("true"))
          return str;
        return "false";
      case 20:
        try
        {
          new Byte(str);
          return str;
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          throw new MalformedTermException("the object is not a byte");
        }
      case 19:
        try
        {
          new Byte(ckeckStringAndCutQuotes(str, true, "the object is not a (quoted) byte"));
          return str;
        }
        catch (NumberFormatException localNumberFormatException3)
        {
          throw new MalformedTermException("the object is not a byte");
        }
      case 14:
        try
        {
          new Integer(str);
          return str;
        }
        catch (NumberFormatException localNumberFormatException4)
        {
          throw new MalformedTermException("the object is not an integer");
        }
      case 13:
        try
        {
          new Integer(ckeckStringAndCutQuotes(str, true, "the object is not a (quoted) integer"));
          return str;
        }
        catch (NumberFormatException localNumberFormatException5)
        {
          throw new MalformedTermException("the object is not an integer");
        }
      case 16:
        try
        {
          new Long(str);
          return str;
        }
        catch (NumberFormatException localNumberFormatException6)
        {
          throw new MalformedTermException("the object is not a long");
        }
      case 15:
        try
        {
          new Long(ckeckStringAndCutQuotes(str, true, "the object is not a (quoted) long"));
          return str;
        }
        catch (NumberFormatException localNumberFormatException7)
        {
          throw new MalformedTermException("the object is not a long");
        }
      case 18:
        try
        {
          new Short(str);
          return str;
        }
        catch (NumberFormatException localNumberFormatException8)
        {
          throw new MalformedTermException("the object is not a short");
        }
      case 17:
        try
        {
          new Short(ckeckStringAndCutQuotes(str, true, "the object is not a (quoted) short"));
          return str;
        }
        catch (NumberFormatException localNumberFormatException9)
        {
          throw new MalformedTermException("the object is not a short");
        }
      case 5:
        try
        {
          Date.valueOf(ckeckStringAndCutQuotes(str, true, "the object is not an date"));
          return str;
        }
        catch (IllegalArgumentException localIllegalArgumentException1)
        {
          throw new MalformedTermException("the object is not a date");
        }
      case 6:
        try
        {
          Time.valueOf(ckeckStringAndCutQuotes(str, true, "the object is not a time"));
          return str;
        }
        catch (IllegalArgumentException localIllegalArgumentException2)
        {
          throw new MalformedTermException("the object is not a time");
        }
      case 10:
        try
        {
          new Float(ckeckStringAndCutQuotes(str, true, "the object is not a (quoted) float"));
          return str;
        }
        catch (NumberFormatException localNumberFormatException10)
        {
          throw new MalformedTermException("the object is not a float");
        }
      case 12:
        try
        {
          return "" + (int)new Float(Math.round(Math.abs(Float.parseFloat(str)))).floatValue();
        }
        catch (NumberFormatException localNumberFormatException11)
        {
          throw new MalformedTermException("the object is not a float");
        }
      case 11:
        try
        {
          return "" + (int)new Float(Math.floor(Math.abs(Float.parseFloat(str)))).floatValue();
        }
        catch (NumberFormatException localNumberFormatException12)
        {
          throw new MalformedTermException("the object is not a float");
        }
      case 7:
        try
        {
          new Double(ckeckStringAndCutQuotes(str, true, "the object is not a (quoted) double"));
          return str;
        }
        catch (NumberFormatException localNumberFormatException13)
        {
          throw new MalformedTermException("the object is not a double");
        }
      case 9:
        try
        {
          return "" + (int)new Double(Math.round(Math.abs(Double.parseDouble(str)))).doubleValue();
        }
        catch (NumberFormatException localNumberFormatException14)
        {
          throw new MalformedTermException("the object is not a double");
        }
      case 8:
        try
        {
          return "" + (int)new Double(Math.floor(Math.abs(Double.parseDouble(str)))).doubleValue();
        }
        catch (NumberFormatException localNumberFormatException15)
        {
          throw new MalformedTermException("the object is not a double");
        }
      case 22:
        if (str.equalsIgnoreCase("null"))
          return "null";
        throw new MalformedTermException("the object is not a database null");
      case 0:
      case 21:
        throw new MalformedTermException("not implemented yet");
      case 1:
      }
    }
    if (paramObject instanceof BigDecimal)
    {
      if ((paramByte == 4) || (paramByte == 2))
        return addQuotes(((BigDecimal)paramObject).toString());
      throw new MalformedTermException("Required type: " + paramByte + " Found: " + paramObject.getClass());
    }
    if (paramObject instanceof Boolean)
    {
      if ((paramByte == 3) || (paramByte == 2))
        return "" + ((Boolean)paramObject).booleanValue();
      throw new MalformedTermException("Required type: " + paramByte + " Found: " + paramObject.getClass());
    }
    if (paramObject instanceof Byte)
    {
      if ((paramByte == 20) || (paramByte == 2))
        return "" + ((Byte)paramObject).byteValue();
      if (paramByte == 19)
        return addQuotes("" + ((Byte)paramObject).byteValue());
      throw new MalformedTermException("Required type: " + paramByte + " Found: " + paramObject.getClass());
    }
    if (paramObject instanceof Integer)
    {
      if ((paramByte == 14) || (paramByte == 2))
        return "" + ((Integer)paramObject).intValue();
      if (paramByte == 13)
        return addQuotes("" + ((Integer)paramObject).intValue());
      throw new MalformedTermException("Required type: " + paramByte + " Found: " + paramObject.getClass());
    }
    if (paramObject instanceof Long)
    {
      if ((paramByte == 16) || (paramByte == 2))
        return "" + ((Long)paramObject).longValue();
      if (paramByte == 15)
        return addQuotes("" + ((Long)paramObject).longValue());
      throw new MalformedTermException("Required type: " + paramByte + " Found: " + paramObject.getClass());
    }
    if (paramObject instanceof Short)
    {
      if ((paramByte == 18) || (paramByte == 2))
        return "" + ((Short)paramObject).shortValue();
      if (paramByte == 17)
        return addQuotes("" + ((Short)paramObject).shortValue());
      throw new MalformedTermException("Required type: " + paramByte + " Found: " + paramObject.getClass());
    }
    if (paramObject instanceof Date)
    {
      if ((paramByte == 5) || (paramByte == 2))
        return addQuotes("" + ((Date)paramObject).toString());
      throw new MalformedTermException("Required type: " + paramByte + " Found: " + paramObject.getClass());
    }
    if (paramObject instanceof Time)
    {
      if ((paramByte == 6) || (paramByte == 2))
        return addQuotes("" + ((Time)paramObject).toString());
      throw new MalformedTermException("Required type: " + paramByte + " Found: " + paramObject.getClass());
    }
    if (paramObject instanceof Float)
    {
      switch (paramByte)
      {
      case 10:
        return addQuotes("" + ((Float)paramObject).floatValue());
      case 11:
        return "" + (int)Math.floor(Math.abs(((Float)paramObject).floatValue()));
      case 2:
      case 12:
        return "" + Math.round(Math.abs(((Float)paramObject).floatValue()));
      }
      throw new MalformedTermException("Required type: " + paramByte + " Found: " + paramObject.getClass());
    }
    if (paramObject instanceof Double)
    {
      switch (paramByte)
      {
      case 7:
        return addQuotes("" + ((Double)paramObject).doubleValue());
      case 8:
        return "" + (int)Math.floor(Math.abs(((Double)paramObject).doubleValue()));
      case 2:
      case 9:
        return "" + (int)Math.round(Math.abs(((Double)paramObject).doubleValue()));
      case 3:
      case 4:
      case 5:
      case 6:
      }
      throw new MalformedTermException("Required type: " + paramByte + " Found: " + paramObject.getClass());
    }
    if (paramObject instanceof DBNull)
      return "null";
    throw new MalformedTermException();
  }

  public static final class DBNull
  {
    private DBNull()
    {
    }

    public String toString()
    {
      return "null";
    }

    DBNull(ConversionType.1 param1)
    {
    }
  }
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.ConversionType
 * JD-Core Version:    0.5.4
 */