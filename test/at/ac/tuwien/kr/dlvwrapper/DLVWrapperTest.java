package at.ac.tuwien.kr.dlvwrapper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import DLV.DLVInvocationException;

public class DLVWrapperTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetVersion() throws DLVInvocationException {
		DLVWrapper dlv = new DLVWrapper();
		dlv.setDlvPath(".\\dlv\\dlv.mingw.exe");
		String version = dlv.getVersion();
		System.out.println(version);
	}

	@Test
	public void testRun() throws DLVInvocationException {
		String program = "q(X,Y):-p(X,Y)."//
				+ "q(X,Z):-p(X,Y),q(Y,Z)."//
				+ "p(a,b)."//
				+ "p(b,c).";
		DLVWrapper dlv = new DLVWrapper();
		dlv.setProgram(program);
		dlv.setDlvPath(".\\dlv\\dlv.mingw.exe");
		dlv.run();
		
	}
}
