package DLV;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Program
{
  private StringBuffer program = new StringBuffer();
  private ArrayList<String> file = new ArrayList<String>();
  private HashSet<Predicate> predicates = new HashSet<Predicate>();

  public Program()
  {
  }

  public Program(StringBuffer paramStringBuffer)
  {
    if (paramStringBuffer == null)
      throw new NullPointerException();
    this.program = paramStringBuffer;
  }

  public void addPredicate(Predicate paramPredicate)
  {
    if (paramPredicate == null)
      throw new NullPointerException();
    this.predicates.add(paramPredicate);
  }

  public void removePredicate(Predicate paramPredicate)
  {
    if (paramPredicate == null)
      throw new NullPointerException();
    this.predicates.remove(paramPredicate);
  }

  public void addString(StringBuffer paramStringBuffer)
  {
    if (paramStringBuffer == null)
      throw new NullPointerException();
    this.program = paramStringBuffer;
  }

  public void addString(String paramString)
  {
    if (paramString == null)
      throw new NullPointerException();
    this.program = new StringBuffer(paramString);
  }

  public void appendString(StringBuffer paramStringBuffer)
  {
    if (paramStringBuffer == null)
      throw new NullPointerException();
    this.program.append(paramStringBuffer);
  }

  public void appendString(String paramString)
  {
    if (paramString == null)
      throw new NullPointerException();
    this.program.append(paramString);
  }

  public void addProgramFile(String file)
  {
    if (file == null)
      throw new NullPointerException();
    int i = this.file.indexOf(file);
    if (i != -1)
      return;
    this.file.add(file);
  }

  public void removeProgramFile(String file)
  {
    if (file == null)
      throw new NullPointerException();
    this.file.remove(file);
  }

  public void removeProgramFiles()
  {
    this.file.clear();
  }

  public void removeStrings()
  {
    this.program.delete(0, this.program.length());
  }

  public void removePredicates()
  {
    this.predicates.clear();
  }

  public void clearMemory()
  {
    this.predicates.clear();
    this.program.delete(0, this.program.length());
  }

  public void reset()
  {
    this.file.clear();
    this.predicates.clear();
    this.program.delete(0, this.program.length());
  }

  public StringBuffer getProgramStrings()
  {
    return this.program;
  }

  public Predicate[] getPredicates()
  {
    Predicate[] arrayOfPredicate = new Predicate[this.predicates.size()];
    Iterator<Predicate> localIterator = this.predicates.iterator();
    for (int i = 0; localIterator.hasNext(); ++i)
      arrayOfPredicate[i] = localIterator.next();
    return arrayOfPredicate;
  }

  public String[] getProgramsPathNames()
  {
    String[] arrayOfString = Tools.arrayListToStringArray(this.file);
    if (arrayOfString.length == 0)
      return null;
    return arrayOfString;
  }
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.Program
 * JD-Core Version:    0.5.4
 */