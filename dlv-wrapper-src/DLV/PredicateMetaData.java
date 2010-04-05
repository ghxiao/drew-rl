package DLV;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class PredicateMetaData
{
  private byte[] type = null;
  private String[] name = null;

  public PredicateMetaData(int paramInt)
  {
    this.type = new byte[paramInt];
    this.name = new String[paramInt];
    for (int i = 0; i < paramInt; ++i)
    {
      this.type[i] = 2;
      this.name[i] = null;
    }
  }

  public PredicateMetaData(ResultSetMetaData paramResultSetMetaData)
    throws SQLException
  {
    int i = paramResultSetMetaData.getColumnCount();
    this.type = new byte[i];
    this.name = new String[i];
    for (int j = 0; j < i; ++j)
    {
      this.type[j] = ConversionType.sqlToDlvTypes(paramResultSetMetaData.getColumnType(j + 1));
      this.name[j] = paramResultSetMetaData.getColumnName(j + 1);
    }
  }

  public int getArity()
  {
    return this.type.length;
  }

  public void setType(int paramInt, byte paramByte)
  {
    this.type[(--paramInt)] = paramByte;
  }

  public byte getType(int paramInt)
  {
    return this.type[(--paramInt)];
  }

  public void setName(int paramInt, String paramString)
  {
    this.name[(--paramInt)] = paramString;
  }

  public String getName(int paramInt)
  {
    return this.name[(--paramInt)];
  }

  public int getArgumentByName(String paramString)
  {
    int i = 0;
    while (i < this.name.length)
      if (this.name[i].equals(paramString))
        return i + 1;
    throw new BadColumnNameException("The name \"" + paramString + "\" does not correspond to a column name");
  }
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.PredicateMetaData
 * JD-Core Version:    0.5.4
 */