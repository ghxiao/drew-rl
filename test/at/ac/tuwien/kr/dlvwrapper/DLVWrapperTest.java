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

}
