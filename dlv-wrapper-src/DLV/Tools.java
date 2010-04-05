package DLV;

import java.util.ArrayList;

public class Tools
{
  public static String[] arrayListToStringArray(ArrayList paramArrayList)
  {
    if (paramArrayList == null)
      return null;
    int i = paramArrayList.size();
    String[] arrayOfString = new String[i];
    for (int j = 0; j < i; ++j)
      arrayOfString[j] = ((String)paramArrayList.get(j));
    return arrayOfString;
  }
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.Tools
 * JD-Core Version:    0.5.4
 */