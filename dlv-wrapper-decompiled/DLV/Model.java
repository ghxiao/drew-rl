package DLV;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Model
{
  public static final Model NO_MODEL = new Model();
  private ArrayList predicates = new ArrayList();
  private HashMap modelTable = new HashMap();
  private boolean isBest = false;
  private int[][] costAndPriority = (int[][])null;
  private int cursor = -1;

  Model()
  {
  }

  Model(boolean paramBoolean)
  {
    this.isBest = paramBoolean;
  }

  public boolean isEmpty()
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    return this.predicates.size() == 0;
  }

  public boolean isBest()
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    return this.isBest;
  }

  public boolean isNoModel()
  {
    return this == NO_MODEL;
  }

  public int getCost()
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    if (this.costAndPriority != null)
      return this.costAndPriority[0][0];
    return 0;
  }

  public int[][] getCostWithPriority()
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    return this.costAndPriority;
  }

  public Enumeration getPredicates()
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    return new Enumeration()
    {
      int count = 0;

      public boolean hasMoreElements()
      {
        return this.count < Model.this.predicates.size();
      }

      public Object nextElement()
      {
        if (this.count < Model.this.predicates.size())
        {
          this.count += 1;
          return Model.this.predicates.get(this.count - 1);
        }
        throw new NoSuchElementException("ArrayList Enumeration");
      }
    };
  }

  public String[] getPredicateNames()
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    Iterator localIterator = this.predicates.iterator();
    String[] arrayOfString = new String[this.predicates.size()];
    for (int i = 0; localIterator.hasNext(); ++i)
    {
      Predicate localPredicate = (Predicate)localIterator.next();
      arrayOfString[i] = localPredicate.name();
    }
    return arrayOfString;
  }

  public Predicate getPredicate(String paramString)
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    return (Predicate)this.modelTable.get(paramString);
  }

  public int size()
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    return this.predicates.size();
  }

  public Predicate getPredicate()
  {
    if (isNoModel())
      throw new IsNoModelException();
    return getPredicate(this.cursor);
  }

  public Predicate getPredicate(int paramInt)
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    if ((paramInt >= this.predicates.size()) || (paramInt < 0))
      throw new NoSuchPredicateException();
    return (Predicate)this.predicates.get(paramInt);
  }

  public boolean hasMorePredicates()
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    if (this.predicates.size() != 0)
      return this.cursor < this.predicates.size() - 1;
    return false;
  }

  public Predicate nextPredicate()
  {
    if (next())
      return getPredicate();
    throw new NoSuchPredicateException();
  }

  public Predicate previousPredicate()
  {
    if (previous())
      return getPredicate();
    throw new NoSuchPredicateException();
  }

  public Predicate firstPredicate()
  {
    first();
    return getPredicate();
  }

  public Predicate lastPredicate()
  {
    last();
    return getPredicate();
  }

  public int getCursor()
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    return this.cursor;
  }

  public boolean first()
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    if (this.predicates.size() > 0)
    {
      this.cursor = 0;
      return true;
    }
    return false;
  }

  public boolean last()
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    if (this.predicates.size() > 0)
    {
      this.cursor = (this.predicates.size() - 1);
      return true;
    }
    return false;
  }

  public void beforeFirst()
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    this.cursor = -1;
  }

  public void afterLast()
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    if (this.predicates.size() <= 0)
      return;
    this.cursor = this.predicates.size();
  }

  public boolean isFirst()
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    return (this.cursor == 0) && (this.predicates.size() > 0);
  }

  public boolean isLast()
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    int i = this.predicates.size();
    return (this.cursor == i - 1) && (i > 0);
  }

  public boolean relative(int paramInt)
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    int i = this.predicates.size();
    if (i <= 0)
      return false;
    this.cursor += paramInt;
    if (this.cursor < -1)
      this.cursor = -1;
    if (this.cursor > i)
      this.cursor = i;
    return (this.cursor > -1) && (this.cursor < i);
  }

  public boolean previous()
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    this.cursor += -1;
    if (this.cursor < -1)
      this.cursor = -1;
    int i = this.predicates.size();
    if (this.cursor > i)
      this.cursor = i;
    return (this.cursor > -1) && (this.cursor < i);
  }

  public boolean next()
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    int i = this.predicates.size();
    if (this.cursor < -1)
    {
      this.cursor = -1;
      return false;
    }
    this.cursor += 1;
    if (this.cursor > i)
    {
      this.cursor = i;
      return false;
    }
    return (this.cursor > -1) && (this.cursor < i) && (i != 0);
  }

  public boolean absolute(int paramInt)
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    int i = this.predicates.size();
    int j;
    if (paramInt < 0)
    {
      j = i + paramInt;
      if (j < 0)
        this.cursor = -1;
      else
        this.cursor = j;
    }
    else
    {
      j = paramInt - 1;
      if (j > i)
        this.cursor = i;
      else
        this.cursor = j;
    }
    return (this.cursor > -1) && (this.cursor < i);
  }

  public void saveTo(String paramString)
    throws IOException
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    PrintWriter localPrintWriter = new PrintWriter(new FileWriter(paramString));
    if (this.predicates.size() == 0)
    {
      localPrintWriter.close();
      return;
    }
    appendTo(localPrintWriter);
    localPrintWriter.close();
  }

  public void appendTo(PrintWriter paramPrintWriter)
    throws IOException
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    Iterator localIterator = this.predicates.iterator();
    while (localIterator.hasNext())
    {
      Predicate localPredicate = (Predicate)localIterator.next();
      paramPrintWriter.println("");
      localPredicate.appendTo(paramPrintWriter);
    }
  }

  public String toString()
  {
    if (this == NO_MODEL)
      throw new IsNoModelException();
    Iterator localIterator = this.predicates.iterator();
    StringBuffer localStringBuffer = new StringBuffer();
    while (localIterator.hasNext())
      localStringBuffer.append(((Predicate)localIterator.next()).toString());
    return localStringBuffer.toString();
  }

  void addPredicate(Predicate paramPredicate)
  {
    if (paramPredicate == null)
      throw new NullPointerException();
    if (this.modelTable.containsKey(paramPredicate.name()))
      return;
    this.modelTable.put(paramPredicate.name(), paramPredicate);
    this.predicates.add(paramPredicate);
  }

  void setBest(boolean paramBoolean)
  {
    this.isBest = paramBoolean;
  }

  void setCostAndPriority(int[][] paramArrayOfInt)
  {
    this.costAndPriority = paramArrayOfInt;
  }
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.Model
 * JD-Core Version:    0.5.4
 */