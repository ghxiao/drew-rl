package DLV;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class OutputDescriptor
{
  private OutputDescriptorItem defaultDescriptor = new ODIPredicate();
  private HashMap outputTable = new HashMap();
  private HashMap dataStore = new HashMap();

  public OutputDescriptor()
  {
  }

  public OutputDescriptor(OutputDescriptorItem paramOutputDescriptorItem)
  {
    this.defaultDescriptor = paramOutputDescriptorItem;
    this.defaultDescriptor.setOutputDescriptor(this);
  }

  public void addDescriptor(String paramString, OutputDescriptorItem paramOutputDescriptorItem)
  {
    if (this.outputTable.containsKey(paramString))
      this.outputTable.remove(paramString);
    this.outputTable.put(paramString, paramOutputDescriptorItem);
    paramOutputDescriptorItem.setOutputDescriptor(this);
  }

  public OutputDescriptorItem getDescriptor(String paramString)
  {
    OutputDescriptorItem localOutputDescriptorItem = (OutputDescriptorItem)this.outputTable.get(paramString);
    if (localOutputDescriptorItem == null)
      return this.defaultDescriptor;
    return localOutputDescriptorItem;
  }

  public void removeDescriptor(String paramString)
  {
    this.outputTable.remove(paramString);
  }

  public void reset()
  {
    this.defaultDescriptor = new ODIPredicate();
    this.outputTable.clear();
    this.dataStore.clear();
  }

  public void clear()
  {
    this.outputTable.clear();
    this.dataStore.clear();
  }

  public boolean multipleModelAllowed()
  {
    Iterator localIterator = this.outputTable.entrySet().iterator();
    while (localIterator.hasNext())
      if (!((OutputDescriptorItem)localIterator.next()).multipleModelAllowed())
        return false;
    return true;
  }

  HashMap getDataStore()
  {
    return this.dataStore;
  }

  void flushAndClean()
  {
    Iterator localIterator = this.outputTable.values().iterator();
    while (localIterator.hasNext())
      ((OutputDescriptorItem)localIterator.next()).flushAndClean();
    this.dataStore.clear();
  }
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.OutputDescriptor
 * JD-Core Version:    0.5.4
 */