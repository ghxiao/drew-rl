package DLV;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ODIJDBCPredicate extends OutputDescriptorItem
{
  private ResultSet rs = null;
  private PredicateMetaData pmd = null;
  private boolean[] quoted = null;

  public ODIJDBCPredicate(String paramString, Statement paramStatement)
    throws SQLException
  {
    this.rs = paramStatement.executeQuery("select * from " + paramString);
    this.rs = this.rs;
  }

  public ODIJDBCPredicate(ResultSet paramResultSet)
  {
    this.rs = paramResultSet;
  }

  public ODIJDBCPredicate(ResultSet paramResultSet, PredicateMetaData paramPredicateMetaData)
  {
    this.rs = paramResultSet;
    this.pmd = paramPredicateMetaData;
  }

  public Predicate buildPredicate(String paramString, int paramInt1, Model paramModel, int paramInt2)
  {
    if (paramInt2 > 1)
      throw new DLVExceptionUncheked("multiple model not allowed");
    try
    {
      JDBCPredicate localJDBCPredicate = null;
      if (this.pmd == null)
        localJDBCPredicate = new JDBCPredicate(paramString, this.rs, paramModel);
      else
        localJDBCPredicate = new JDBCPredicate(paramString, this.rs, this.pmd, paramModel);
      if (this.quoted != null)
        for (int i = 0; i < Math.min(this.quoted.length, localJDBCPredicate.arity()); ++i)
          localJDBCPredicate.setQuoted(i, this.quoted[i]);
      return localJDBCPredicate;
    }
    catch (SQLException localSQLException)
    {
      throw new DLVExceptionUncheked("An error occurred redirecting output type: " + localSQLException.getClass() + " message: " + localSQLException.getMessage());
    }
  }

  public void setQuoted(boolean[] paramArrayOfBoolean)
  {
    this.quoted = paramArrayOfBoolean;
  }

  public boolean[] getQuoted()
  {
    return this.quoted;
  }

  public boolean multipleModelAllowed()
  {
    return false;
  }

  public void flushAndClean()
  {
  }
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.ODIJDBCPredicate
 * JD-Core Version:    0.5.4
 */