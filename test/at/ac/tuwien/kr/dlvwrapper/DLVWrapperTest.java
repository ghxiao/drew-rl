package at.ac.tuwien.kr.dlvwrapper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import DLV.DLVInvocationException;

public class DLVWrapperTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetVersion() throws DLVInvocationException {
		DLVWrapper dlv = new DLVWrapper();
		// dlv.setDlvPath(".\\dlv\\dlv.mingw.exe");
		dlv.setDlvPath("./dlv/dlv_magic");
		String version = dlv.getVersion();
		System.out.println(version);
	}

	@Test
	@Ignore("Only for Windows")
	public void testRunOnWindows() throws DLVInvocationException {
		String program = "q(X,Y):-p(X,Y)."//
				+ "q(X,Z):-p(X,Y),q(Y,Z)."//
				+ "p(a,b)."//
				+ "p(b,c).";
		DLVWrapper dlv = new DLVWrapper();
		dlv.setProgram(program);
		dlv.setDlvPath("./dlv/dlv.mingw.exe");
		dlv.run();
	}

	@Test
	public void testRunOnLinux_WFS() throws DLVInvocationException {
		String program = "p:-not p. q. r. s:-r. t(a):-p. u(a,b).";
		DLVWrapper dlv = new DLVWrapper();
		dlv.setProgram(program);
		dlv.setDlvPath("./dlv/dlv_magic");
		dlv.runWFS();
	}

	@Test
	public void testQueryWFS() throws DLVInvocationException {
		String program = "p:-not p. q. r. s:-r. t(a):-p. u(a,b).";
		DLVWrapper dlv = new DLVWrapper();
		dlv.setProgram(program);
		dlv.setDlvPath("./dlv/dlv_magic");
		String q1 = "p";
		String q2 = "u(a,b)";
		String q3 = "t(a)";
		String q4 = "e";
		assertFalse(dlv.queryWFS(q1));
		assertTrue(dlv.queryWFS(q2));
		assertFalse(dlv.queryWFS(q3));
		assertFalse(dlv.queryWFS(q4));
		

	}
}
