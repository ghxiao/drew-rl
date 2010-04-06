package DLV;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Tester
{
  static Connection conn = null;

  public static boolean startMySql()
  {
    try
    {
      String str1 = System.getProperty("com.mysql.jdbc.testsuite.url");
      String str2 = "jdbc:mysql:///test?user=root&password=ciccio";
      if ((str1 != null) && (str1.trim().length() != 0))
        str2 = str1;
      System.out.println(str2);
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      conn = DriverManager.getConnection(str2);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
      return false;
    }
    return true;
  }

  public static boolean stopMySql()
  {
    try
    {
      conn.close();
    }
    catch (SQLException localSQLException)
    {
      return false;
    }
    return true;
  }

  public static void main(String[] paramArrayOfString)
  {
    try
    {
      Program localProgram1 = new Program();
      localProgram1.addString("a v b v g. a:-d. c. aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.");
      DlvHandler localDlvHandler1 = new DlvHandler("/Users/ciccio/bin/dlv");
      localDlvHandler1.setProgram(localProgram1);
      localDlvHandler1.run();
      int i = 0;
      if (i == 0)
        return;
      Program localProgram2 = new Program();
      localProgram2.addString("ciccio.");
      localProgram2.addProgramFile("/Users/ciccio/Desktop/first.dl");
      localProgram2.addProgramFile("/Users/ciccio/Desktop/ha.dat");
      localProgram2.addProgramFile("/Users/ciccio/Desktop/ha");
      int j = 0;
      int k = 0;
      DlvHandler localDlvHandler2 = new DlvHandler("/Users/ciccio/bin/dlv");
      while (j < 10000)
      {
        localDlvHandler2.reset();
        localDlvHandler2.setProgram(localProgram2);
        localDlvHandler2.setNumberOfModels(2);
        localDlvHandler2.run(0);
        synchronized (localDlvHandler2)
        {
          localDlvHandler2.wait(2000L);
        }
        localDlvHandler2.kill();
        while (localDlvHandler2.hasMoreModels())
        {
          ??? = localDlvHandler2.nextModel();
          if (!((Model)???).isNoModel())
            continue;
          ++k;
        }
        ??? = localDlvHandler2.getWarnings();
        if (((String)???).length() > 0)
          throw new RuntimeException("warnings: " + (String)???);
        if (localDlvHandler2.getSize() != 2)
          ++k;
        if (++j % 1000 != 0)
          continue;
        System.out.println(j);
      }
      System.out.println("ERRORS: " + k);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }

  public static void main2(String[] paramArrayOfString)
  {
    try
    {
      Program localProgram = new Program();
      localProgram.addString("ciccio.");
      localProgram.addProgramFile("/Users/ciccio/Desktop/test.dl");
      DlvHandler localDlvHandler = new DlvHandler("/Users/ciccio/bin/dl");
      localDlvHandler.setProgram(localProgram);
      localDlvHandler.setOptimizationOptions("null");
      localDlvHandler.setNumberOfModels(4);
      localDlvHandler.run(1);
      while (localDlvHandler.hasMoreModels())
      {
        System.out.println("--------------------------------------------------");
        localObject = localDlvHandler.nextModel();
        if (((Model)localObject).isNoModel())
          System.out.println("no model");
        System.out.println(((Model)localObject).toString().trim());
      }
      Object localObject = localDlvHandler.getWarnings();
      if (((String)localObject).length() > 0)
        throw new RuntimeException("warnings: " + (String)localObject);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }

  public static void main1(String[] paramArrayOfString)
  {
    try
    {
      startMySql();
      long l = System.currentTimeMillis();
      FilePredicate localFilePredicate = new FilePredicate("ciccio", 2, "/Users/ciccio/Desktop/ciccio.dl", "MacRoman", 200, 1024);
      Statement localStatement = conn.createStatement(1004, 1008);
      DlvHandler localDlvHandler = new DlvHandler("/Users/ciccio/bin/dl");
      Program localProgram = new Program();
      localProgram.addProgramFile("/Users/ciccio/Desktop/minsubgraph");
      localProgram.addProgramFile("/Users/ciccio/Desktop/prova");
      localDlvHandler.setGenerateAllPossibilyModels(true);
      localDlvHandler.setPFilter(new String[] { "arc" });
      localDlvHandler.setProgram(localProgram);
      boolean[] arrayOfBoolean = new boolean[2];
      arrayOfBoolean[0] = true;
      arrayOfBoolean[1] = false;
      ODIJDBCPredicate localODIJDBCPredicate = new ODIJDBCPredicate("arc", localStatement);
      localODIJDBCPredicate.setQuoted(arrayOfBoolean);
      localDlvHandler.run();
      System.out.println("running");
      while (localDlvHandler.hasMoreModels())
      {
        Model localModel = localDlvHandler.nextModel();
        if (localModel != Model.NO_MODEL)
        {
          l = (System.currentTimeMillis() - l) / 60000L;
          System.out.println("Execution time= " + l);
          System.out.print("----------------------");
          while (localModel.hasMorePredicates())
          {
            Predicate localPredicate = localModel.nextPredicate();
            while (localPredicate.hasMoreLiterals())
            {
              Predicate.Literal localLiteral = localPredicate.nextLiteral();
              System.out.println(localLiteral.toString());
            }
            Predicate tmp272_270 = localPredicate;
            tmp272_270.getClass();
            localPredicate.addLiteral(new Predicate.Literal(tmp272_270, new String[] { "ggg", "jjjj" }));
          }
          System.out.println("---" + localModel.getPredicate(0).getLiteral(localModel.getPredicate(0).size() - 1).toString());
        }
        System.out.println("No model found!!");
      }
      System.out.println(localDlvHandler.getWarnings());
      stopMySql();
      l = (System.currentTimeMillis() - l) / 60000L;
      System.out.println("Execution time= " + l);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
}

/* Location:           G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar
 * Qualified Name:     DLV.Tester
 * JD-Core Version:    0.5.4
 */