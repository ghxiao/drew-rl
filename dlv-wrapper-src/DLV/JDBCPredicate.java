package DLV;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashSet;

public class JDBCPredicate extends Predicate
{
  private ResultSet rs = null;
  private boolean[] quoted = null;

  public JDBCPredicate(ResultSet paramResultSet)
    throws SQLException
  {
    super(buildName(paramResultSet), buildArity(paramResultSet));
    this.rs = paramResultSet;
    super.setPredicateMetaData(new PredicateMetaData(paramResultSet.getMetaData()));
    this.impl = new JDBCPI(arity());
    this.quoted = new boolean[arity()];
    for (int i = 0; i < arity(); ++i)
      this.quoted[i] = false;
  }

  public JDBCPredicate(String paramString, ResultSet paramResultSet)
    throws SQLException
  {
    super(paramString, buildArity(paramResultSet));
    this.rs = paramResultSet;
    super.setPredicateMetaData(new PredicateMetaData(paramResultSet.getMetaData()));
    this.impl = new JDBCPI(arity());
    this.quoted = new boolean[arity()];
    for (int i = 0; i < arity(); ++i)
      this.quoted[i] = false;
  }

  JDBCPredicate(String paramString, ResultSet paramResultSet, Model paramModel)
    throws SQLException
  {
    super(paramString, buildArity(paramResultSet), paramModel);
    this.rs = paramResultSet;
    super.setPredicateMetaData(new PredicateMetaData(paramResultSet.getMetaData()));
    this.impl = new JDBCPI(arity());
    this.quoted = new boolean[arity()];
    for (int i = 0; i < arity(); ++i)
      this.quoted[i] = false;
  }

  public JDBCPredicate(ResultSet paramResultSet, PredicateMetaData paramPredicateMetaData)
    throws SQLException
  {
    super(buildName(paramResultSet), buildArity(paramResultSet));
    this.rs = paramResultSet;
    super.setPredicateMetaData(new PredicateMetaData(paramResultSet.getMetaData()));
    setPredicateMetaData(paramPredicateMetaData);
    this.impl = new JDBCPI(arity());
    this.quoted = new boolean[arity()];
    for (int i = 0; i < arity(); ++i)
      this.quoted[i] = false;
  }

  public JDBCPredicate(String paramString, ResultSet paramResultSet, PredicateMetaData paramPredicateMetaData)
    throws SQLException
  {
    super(paramString, buildArity(paramResultSet));
    this.rs = paramResultSet;
    super.setPredicateMetaData(new PredicateMetaData(paramResultSet.getMetaData()));
    setPredicateMetaData(paramPredicateMetaData);
    this.impl = new JDBCPI(arity());
    this.quoted = new boolean[arity()];
    for (int i = 0; i < arity(); ++i)
      this.quoted[i] = false;
  }

  JDBCPredicate(String paramString, ResultSet paramResultSet, PredicateMetaData paramPredicateMetaData, Model paramModel)
    throws SQLException
  {
    super(paramString, buildArity(paramResultSet), paramModel);
    this.rs = paramResultSet;
    super.setPredicateMetaData(new PredicateMetaData(paramResultSet.getMetaData()));
    setPredicateMetaData(paramPredicateMetaData);
    this.impl = new JDBCPI(arity());
    this.quoted = new boolean[arity()];
    for (int i = 0; i < arity(); ++i)
      this.quoted[i] = false;
  }

  public ResultSet getResultSet()
  {
    return this.rs;
  }

  public void setQuoted(int paramInt, boolean paramBoolean)
  {
    if ((paramInt < 0) || (paramInt >= arity()))
      throw new IllegalArgumentException();
    this.quoted[paramInt] = paramBoolean;
  }

  public boolean getQuoted(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= arity()))
      throw new IllegalArgumentException();
    return this.quoted[paramInt];
  }

  private static String buildName(ResultSet paramResultSet)
  {
    try
    {
      ResultSetMetaData localResultSetMetaData = paramResultSet.getMetaData();
      HashSet localHashSet = new HashSet();
      String str = localResultSetMetaData.getTableName(1);
      localHashSet.add(str);
      for (int i = 2; i <= localResultSetMetaData.getColumnCount(); ++i)
      {
        if (localHashSet.contains(localResultSetMetaData.getTableName(i)))
          continue;
        str = str + "_" + localResultSetMetaData.getTableName(i);
        localHashSet.add(localResultSetMetaData.getTableName(i));
      }
      return str;
    }
    catch (SQLException localSQLException)
    {
      throw new JDBCException("Class: " + localSQLException.getClass() + " Message: " + localSQLException.getMessage());
    }
  }

  private static int buildArity(ResultSet paramResultSet)
  {
    try
    {
      ResultSetMetaData localResultSetMetaData = paramResultSet.getMetaData();
      return localResultSetMetaData.getColumnCount();
    }
    catch (SQLException localSQLException)
    {
      throw new JDBCException("Class: " + localSQLException.getClass() + " Message: " + localSQLException.getMessage());
    }
  }

  class JDBCPI extends PredicateImplementation
  {
    JDBCPI(int arg2)
    {
      super(i);
    }

    void updateImplementation(int paramInt, Object[] paramArrayOfObject, Boolean paramBoolean)
    {
      try
      {
        if (!paramBoolean.booleanValue())
          throw new TrueNegationNotSupportedException();
        if (!JDBCPredicate.this.rs.absolute(paramInt + 1))
          throw new NoSuchLiteralException();
        updateRow(paramArrayOfObject);
        JDBCPredicate.this.rs.updateRow();
      }
      catch (SQLException localSQLException)
      {
        throw new JDBCException("Class: " + localSQLException.getClass() + " Message: " + localSQLException.getMessage());
      }
    }

    Object getTerm(int paramInt1, int paramInt2)
    {
      try
      {
        if (!JDBCPredicate.this.rs.absolute(paramInt1 + 1))
          throw new NoSuchLiteralException();
        if (paramInt2 >= JDBCPredicate.this.arity())
          throw new NoSuchTermException();
        switch (JDBCPredicate.this.getPredicateMetaData().getType(paramInt2 + 1))
        {
        case 4:
          return JDBCPredicate.this.rs.getBigDecimal(paramInt2 + 1);
        case 3:
          return new Boolean(JDBCPredicate.this.rs.getBoolean(paramInt2 + 1));
        case 19:
        case 20:
          return new Byte(JDBCPredicate.this.rs.getByte(paramInt2 + 1));
        case 13:
        case 14:
          return new Integer(JDBCPredicate.this.rs.getInt(paramInt2 + 1));
        case 15:
        case 16:
          return new Long(JDBCPredicate.this.rs.getLong(paramInt2 + 1));
        case 17:
        case 18:
          return new Short(JDBCPredicate.this.rs.getShort(paramInt2 + 1));
        case 5:
          return JDBCPredicate.this.rs.getDate(paramInt2 + 1);
        case 6:
          return JDBCPredicate.this.rs.getTime(paramInt2 + 1);
        case 0:
        case 21:
          throw new DLVExceptionUncheked("not implemented yet");
        case 7:
          return new Double(JDBCPredicate.this.rs.getDouble(paramInt2 + 1));
        case 9:
          return new Double(Math.round(Math.abs(JDBCPredicate.this.rs.getDouble(paramInt2 + 1))));
        case 8:
          return new Double(Math.floor(Math.abs(JDBCPredicate.this.rs.getDouble(paramInt2 + 1))));
        case 10:
          return new Float(JDBCPredicate.this.rs.getFloat(paramInt2 + 1));
        case 12:
          return new Float(Math.round(Math.abs(JDBCPredicate.this.rs.getFloat(paramInt2 + 1))));
        case 11:
          return new Float(Math.floor(Math.abs(JDBCPredicate.this.rs.getFloat(paramInt2 + 1))));
        case 22:
          return ConversionType.DBNULL_CLASS;
        case 2:
          String str = null;
          switch (JDBCPredicate.this.rs.getMetaData().getColumnType(paramInt2 + 1))
          {
          case -7:
          case -2:
            str = ConversionType.objectToString(new Integer(JDBCPredicate.this.rs.getInt(paramInt2 + 1)), JDBCPredicate.this.getPredicateMetaData().getType(paramInt2 + 1));
            break;
          case -1:
          case 1:
          case 12:
            str = ConversionType.objectToString(JDBCPredicate.this.rs.getString(paramInt2 + 1), JDBCPredicate.this.getPredicateMetaData().getType(paramInt2 + 1));
            break;
          case 91:
            return ConversionType.objectToString(JDBCPredicate.this.rs.getDate(paramInt2 + 1), JDBCPredicate.this.getPredicateMetaData().getType(paramInt2 + 1));
          case 6:
            str = ConversionType.objectToString(new Float(JDBCPredicate.this.rs.getFloat(paramInt2 + 1)), JDBCPredicate.this.getPredicateMetaData().getType(paramInt2 + 1));
            break;
          case 2:
          case 3:
          case 7:
          case 8:
            str = ConversionType.objectToString(new Double(JDBCPredicate.this.rs.getDouble(paramInt2 + 1)), JDBCPredicate.this.getPredicateMetaData().getType(paramInt2 + 1));
            break;
          case -5:
          case -4:
          case -3:
            str = ConversionType.objectToString(new Long(JDBCPredicate.this.rs.getLong(paramInt2 + 1)), JDBCPredicate.this.getPredicateMetaData().getType(paramInt2 + 1));
            break;
          case -6:
          case 4:
          case 5:
            str = ConversionType.objectToString(new Integer(JDBCPredicate.this.rs.getInt(paramInt2 + 1)), JDBCPredicate.this.getPredicateMetaData().getType(paramInt2 + 1));
            break;
          case 2000:
            throw new JDBCException("not implemented yet");
          case 0:
            return "null";
          case 93:
          case 1111:
          case 2001:
          case 2002:
          case 2003:
          case 2004:
          case 2005:
          case 2006:
            throw new JDBCException("not implemented yet");
          case 92:
            return ConversionType.objectToString(JDBCPredicate.this.rs.getTime(paramInt2 + 1), JDBCPredicate.this.getPredicateMetaData().getType(paramInt2 + 1));
          default:
            str = ConversionType.objectToString(JDBCPredicate.this.rs.getString(paramInt2 + 1), JDBCPredicate.this.getPredicateMetaData().getType(paramInt2 + 1));
          }
          if (JDBCPredicate.this.quoted[paramInt2] != 0)
          {
            if (ConversionType.isQuoted(str))
              return str;
            str = ConversionType.addQuotes(str);
            if (ConversionType.isValid(str))
              return str;
            throw new MalformedTermException("an error occurred applying quotes, quoted string not valid, may contain double quotes");
          }
          return str;
        case 1:
        }
        throw new DLVExceptionUncheked();
      }
      catch (SQLException localSQLException)
      {
        throw new JDBCException("Class: " + localSQLException.getClass() + " Message: " + localSQLException.getMessage());
      }
    }

    boolean getSign(int paramInt)
    {
      return true;
    }

    int size()
    {
      try
      {
        if (JDBCPredicate.this.rs.last())
          return JDBCPredicate.this.rs.getRow();
        return 0;
      }
      catch (SQLException localSQLException)
      {
        throw new JDBCException("Class: " + localSQLException.getClass() + " Message: " + localSQLException.getMessage());
      }
    }

    void removeFromImplementaion(int paramInt)
    {
      try
      {
        if (JDBCPredicate.this.rs.absolute(paramInt + 1))
          JDBCPredicate.this.rs.deleteRow();
        else
          throw new NoSuchLiteralException();
      }
      catch (SQLException localSQLException)
      {
        throw new JDBCException("Class: " + localSQLException.getClass() + " Message: " + localSQLException.getMessage());
      }
    }

    int indexOf(Object[] paramArrayOfObject, boolean paramBoolean)
    {
      try
      {
        if (!paramBoolean)
          return -1;
        JDBCPredicate.this.rs.beforeFirst();
        for (int i = 0; JDBCPredicate.this.rs.next(); ++i)
        {
          int j = 0;
          for (int k = 0; k < JDBCPredicate.this.arity(); ++k)
          {
            if ((paramArrayOfObject[k].equals(getTerm(i, k))) || (ConversionType.objectToString(paramArrayOfObject[k], JDBCPredicate.this.getPredicateMetaData().getType(k + 1)).equals(ConversionType.objectToString(getTerm(i, k), JDBCPredicate.this.getPredicateMetaData().getType(k + 1)))))
              continue;
            j = 1;
            break;
          }
          if (j == 0)
            return i;
        }
        return -1;
      }
      catch (SQLException localSQLException)
      {
        throw new JDBCException("Class: " + localSQLException.getClass() + " Message: " + localSQLException.getMessage());
      }
    }

    void add(Object[] paramArrayOfObject, boolean paramBoolean)
    {
      if (!paramBoolean)
        throw new TrueNegationNotSupportedException();
      try
      {
        JDBCPredicate.this.rs.moveToInsertRow();
        updateRow(paramArrayOfObject);
        JDBCPredicate.this.rs.insertRow();
        JDBCPredicate.this.rs.moveToCurrentRow();
      }
      catch (SQLException localSQLException)
      {
        throw new JDBCException("Class: " + localSQLException.getClass() + " Message: " + localSQLException.getMessage());
      }
    }

    boolean trueNegationSupported()
    {
      return false;
    }

    private void updateRow(Object[] paramArrayOfObject)
      throws SQLException
    {
      for (int i = 0; i < JDBCPredicate.this.arity(); ++i)
      {
        int j = 0;
        Object localObject = paramArrayOfObject[i];
        if (localObject instanceof String)
        {
          if (JDBCPredicate.this.quoted[i] != 0)
            localObject = ConversionType.cutQuotes((String)localObject);
          localObject = ConversionType.stringToObject((String)localObject, JDBCPredicate.this.getPredicateMetaData().getType(i + 1));
          if (localObject instanceof String)
          {
            j = 1;
            JDBCPredicate.this.rs.updateString(i + 1, (String)localObject);
          }
        }
        if (localObject instanceof BigDecimal)
          switch (JDBCPredicate.this.getPredicateMetaData().getType(i + 1))
          {
          case 2:
          case 4:
            JDBCPredicate.this.rs.updateBigDecimal(i + 1, (BigDecimal)localObject);
            j = 1;
            break;
          default:
            throw new MalformedTermException("Found class: " + paramArrayOfObject[i].getClass() + " Required: " + JDBCPredicate.this.getPredicateMetaData().getType(i + 1));
          }
        if (localObject instanceof Boolean)
          switch (JDBCPredicate.this.getPredicateMetaData().getType(i + 1))
          {
          case 2:
          case 3:
            JDBCPredicate.this.rs.updateBoolean(i + 1, ((Boolean)localObject).booleanValue());
            j = 1;
            break;
          default:
            throw new MalformedTermException("Found class: " + paramArrayOfObject[i].getClass() + " Required: " + JDBCPredicate.this.getPredicateMetaData().getType(i + 1));
          }
        if (localObject instanceof Byte)
          switch (JDBCPredicate.this.getPredicateMetaData().getType(i + 1))
          {
          case 2:
          case 19:
          case 20:
            JDBCPredicate.this.rs.updateByte(i + 1, ((Byte)localObject).byteValue());
            j = 1;
            break;
          default:
            throw new MalformedTermException("Found class: " + paramArrayOfObject[i].getClass() + " Required: " + JDBCPredicate.this.getPredicateMetaData().getType(i + 1));
          }
        if (localObject instanceof Integer)
          switch (JDBCPredicate.this.getPredicateMetaData().getType(i + 1))
          {
          case 2:
          case 13:
          case 14:
            JDBCPredicate.this.rs.updateInt(i + 1, ((Integer)localObject).intValue());
            j = 1;
            break;
          default:
            throw new MalformedTermException("Found class: " + paramArrayOfObject[i].getClass() + " Required: " + JDBCPredicate.this.getPredicateMetaData().getType(i + 1));
          }
        if (localObject instanceof Long)
          switch (JDBCPredicate.this.getPredicateMetaData().getType(i + 1))
          {
          case 2:
          case 15:
          case 16:
            JDBCPredicate.this.rs.updateInt(i + 1, ((Long)localObject).intValue());
            j = 1;
            break;
          default:
            throw new MalformedTermException("Found class: " + paramArrayOfObject[i].getClass() + " Required: " + JDBCPredicate.this.getPredicateMetaData().getType(i + 1));
          }
        if (localObject instanceof Short)
          switch (JDBCPredicate.this.getPredicateMetaData().getType(i + 1))
          {
          case 2:
          case 17:
          case 18:
            JDBCPredicate.this.rs.updateInt(i + 1, ((Short)localObject).intValue());
            j = 1;
            break;
          default:
            throw new MalformedTermException("Found class: " + paramArrayOfObject[i].getClass() + " Required: " + JDBCPredicate.this.getPredicateMetaData().getType(i + 1));
          }
        if (localObject instanceof Date)
          switch (JDBCPredicate.this.getPredicateMetaData().getType(i + 1))
          {
          case 2:
          case 5:
            JDBCPredicate.this.rs.updateDate(i + 1, (Date)localObject);
            j = 1;
            break;
          default:
            throw new MalformedTermException("Found class: " + paramArrayOfObject[i].getClass() + " Required: " + JDBCPredicate.this.getPredicateMetaData().getType(i + 1));
          }
        if (localObject instanceof Time)
          switch (JDBCPredicate.this.getPredicateMetaData().getType(i + 1))
          {
          case 2:
          case 6:
            JDBCPredicate.this.rs.updateTime(i + 1, (Time)localObject);
            j = 1;
            break;
          default:
            throw new MalformedTermException("Found class: " + paramArrayOfObject[i].getClass() + " Required: " + JDBCPredicate.this.getPredicateMetaData().getType(i + 1));
          }
        if (localObject instanceof Float)
          switch (JDBCPredicate.this.getPredicateMetaData().getType(i + 1))
          {
          case 2:
          case 10:
            JDBCPredicate.this.rs.updateFloat(i + 1, ((Float)localObject).floatValue());
            j = 1;
            break;
          case 12:
            JDBCPredicate.this.rs.updateFloat(i + 1, Math.round(Math.abs(((Float)localObject).floatValue())));
            j = 1;
            break;
          case 11:
            JDBCPredicate.this.rs.updateFloat(i + 1, (int)Math.floor(Math.abs(((Float)localObject).floatValue())));
            j = 1;
            break;
          default:
            throw new MalformedTermException("Found class: " + paramArrayOfObject[i].getClass() + " Required: " + JDBCPredicate.this.getPredicateMetaData().getType(i + 1));
          }
        if (localObject instanceof Double)
          switch (JDBCPredicate.this.getPredicateMetaData().getType(i + 1))
          {
          case 2:
          case 7:
            JDBCPredicate.this.rs.updateDouble(i + 1, ((Double)localObject).floatValue());
            j = 1;
            break;
          case 9:
            JDBCPredicate.this.rs.updateDouble(i + 1, (int)Math.round(Math.abs(((Double)localObject).doubleValue())));
            j = 1;
            break;
          case 8:
            JDBCPredicate.this.rs.updateDouble(i + 1, (int)Math.floor(Math.abs(((Double)localObject).doubleValue())));
            j = 1;
            break;
          case 3:
          case 4:
          case 5:
          case 6:
          default:
            throw new MalformedTermException("Found class: " + paramArrayOfObject[i].getClass() + " Required: " + JDBCPredicate.this.getPredicateMetaData().getType(i + 1));
          }
        if (localObject instanceof ConversionType.DBNull)
        {
          j = 1;
          JDBCPredicate.this.rs.updateNull(i + 1);
        }
        if (j != 0)
          continue;
        throw new MalformedTermException("Found class: " + paramArrayOfObject[i].getClass() + " Required: " + JDBCPredicate.this.getPredicateMetaData().getType(i + 1));
      }
    }
  }
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.JDBCPredicate
 * JD-Core Version:    0.5.4
 */