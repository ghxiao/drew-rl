package at.ac.tuwien.kr.dlprogram.parser;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { ConstantParserTest.class, VariableParserTest.class, FunctorParserTest.class, FunctionParserTest.class,
		TermParserTest.class, LiteralParserTest.class, ClauseParserTest.class, DLProgramParserTest.class })
public class ParserTestSuite {

}
