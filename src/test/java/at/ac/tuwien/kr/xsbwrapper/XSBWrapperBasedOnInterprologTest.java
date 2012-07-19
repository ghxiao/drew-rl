package at.ac.tuwien.kr.xsbwrapper;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import at.ac.tuwien.kr.datalog.TruthValue;

@Ignore
public class XSBWrapperBasedOnInterprologTest {

	String program;

	@Before
	public void setUp() throws Exception {
		program = ":- table shaves/2.\n"
				+ "shaves(barber,Person):- person(Person), tnot(shaves(Person,Person)).\n"
				+ "person(barber).\n"
				+ "person(mayor).\n";
	}

	@Test
	public void testSetProgram() throws IOException, InterruptedException {
		XSBWrapperBasedOnInterprolog xsb = new XSBWrapperBasedOnInterprolog();
		xsb.setProgram(program);
	}

	@Test
	public void testClose() {
		XSBWrapperBasedOnInterprolog xsb = new XSBWrapperBasedOnInterprolog();
		xsb.close();
	}

	@Test
	public void testQuery001() throws IOException, InterruptedException {
		XSBWrapperBasedOnInterprolog xsb = new XSBWrapperBasedOnInterprolog();
		xsb.setProgram(program);
		assertEquals(TruthValue.TRUE, xsb.query("person(barber)"));
		assertEquals(TruthValue.TRUE, xsb.query("shaves(barber,mayor)"));
		xsb.close();
	}

	@Test
	public void testQuery002() throws IOException, InterruptedException {
		XSBWrapperBasedOnInterprolog xsb = new XSBWrapperBasedOnInterprolog();
		xsb.setProgram(program);
		assertEquals(TruthValue.UNKNOWN, xsb.query("shaves(barber,barber)"));
		xsb.close();
	}

	@Test
	public void testQuery003() throws IOException, InterruptedException {
		XSBWrapperBasedOnInterprolog xsb = new XSBWrapperBasedOnInterprolog();
		xsb.setProgram(program);
		assertEquals(TruthValue.FALSE, xsb.query("shaves(mayor,barber)"));
		xsb.close();
	}

}
