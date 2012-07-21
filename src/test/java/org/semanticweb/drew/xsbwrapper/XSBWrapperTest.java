package org.semanticweb.drew.xsbwrapper;
//package at.ac.tuwien.kr.xsbwrapper;
//
//import static org.junit.Assert.*;
//
//import java.io.IOException;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import at.ac.tuwien.kr.datalog.TruthValue;
//
//public class XSBWrapperTest {
//
//	String program;
//
//	// String path =
//	// "G:\\Reasoner\\xsb-3.1-win32\\config\\x86-pc-windows\\bin\\xsb.exe";
//
////	String path = "G:\\Reasoner\\xsb-3.1-win32\\config\\x86-pc-windows\\bin\\xsb.exe";
//
//	String path = "/usr/local/XSB/3.2/bin/xsb";
//	
//	@Before
//	public void setUp() throws Exception {
//		program = ":- table shaves/2.\n"
//				+ "shaves(barber,Person):- person(Person), tnot(shaves(Person,Person)).\n"
//				+ "person(barber).\n"
//				+ "person(mayor).\n";
//	}
//
//	@Test
//	public void testXSBWrapper() throws XSBInvocationException {
//		XSBWrapper xsb = new XSBWrapper(path);
//	}
//
//	@Test
//	public void testSetProgram() throws XSBInvocationException, IOException, InterruptedException {
//		XSBWrapper xsb = new XSBWrapper(path);
//		xsb.setProgram(program);
//	}
//
//	@Test
//	public void testSetQuery001() throws XSBInvocationException, IOException, InterruptedException {
//		XSBWrapper xsb = new XSBWrapper(path);
//		xsb.setProgram(program);
//		assertEquals(TruthValue.TRUE, xsb.query("person(barber)"));
//		assertEquals(TruthValue.TRUE, xsb.query("shaves(barber,mayor)"));
//		xsb.close();
//	}
//	
//	@Test
//	public void testQuery002() throws IOException, InterruptedException {
//		XSBWrapper xsb = new XSBWrapper(path);
//		xsb.setProgram(program);
//		assertEquals(TruthValue.UNKNOWN, xsb.query("shaves(barber,barber)"));
//		xsb.close();
//	}
//
//	@Test
//	public void testQuery003() throws IOException, InterruptedException {
//		XSBWrapper xsb = new XSBWrapper(path);
//		xsb.setProgram(program);
//		assertEquals(TruthValue.FALSE, xsb.query("shaves(mayor,barber)"));
//		xsb.close();
//	}
//}
