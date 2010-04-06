package DLV;

public class IsNoModelException extends DLVExceptionUncheked
{
  public IsNoModelException()
  {
    super("You cannot call a method over an object representing no model");
  }
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.IsNoModelException
 * JD-Core Version:    0.5.4
 */