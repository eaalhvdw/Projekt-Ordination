package test.ordination.dagligfast;

import org.junit.Before;
import org.junit.Test;

import ordination.DagligFast;
import ordination.Laegemiddel;
import ordination.Patient;
import ordination.Tidspunkt;
import service.Service;

import static org.junit.Assert.*;

import java.time.LocalDate;

public class TestSamletDosis {
	private Service service;
	private Patient patient;
	private Laegemiddel laegemiddel;

	@Before
	public void setUp() {
		service = Service.getTestService();
		patient = new Patient("456", "testperson", 80);
		laegemiddel = new Laegemiddel("Test laegemiddel", 1.2, 2.3, 4.5, "middel");
	}

	@Test
	public void testCase1() {
		DagligFast dagligFast = new DagligFast(LocalDate.of(2018, 1, 5), LocalDate.of(2018, 1, 5));

		double samlet = dagligFast.samletDosis();

		assertEquals(0.0, samlet, 0.0);
	}
	
	@Test
	public void testCase2() {
		DagligFast dagligFast = new DagligFast(LocalDate.of(2018, 1, 5), LocalDate.of(2018, 1, 5));

		dagligFast.setDosis(Tidspunkt.MIDDAG, 0.2);
		dagligFast.setDosis(Tidspunkt.AFTEN, 2.0);
		dagligFast.setDosis(Tidspunkt.NAT, 0.0);

		double samlet = dagligFast.samletDosis();

		assertEquals(2.2, samlet, 0.0);
	}
	
	@Test
	public void tesCase3() {
		DagligFast dagligFast = service.opretDagligFastOrdination(LocalDate.of(2018, 1, 5), LocalDate.of(2018, 1, 5), patient, laegemiddel, 1.0, 3.0, 5.5, 0.0);
		double samlet = dagligFast.samletDosis();

		assertEquals(9.5, samlet, 0.0);
	}
	
	@Test
	public void testCase4() {
		DagligFast dagligFast = new DagligFast(LocalDate.of(2018, 1, 5), LocalDate.of(2018, 1, 10));
		double samlet = dagligFast.samletDosis();

		assertEquals(0.0, samlet, 0.0);
	}
	
	@Test
	public void testCase5() {
		DagligFast dagligFast = new DagligFast(LocalDate.of(2018, 1, 5), LocalDate.of(2018, 1, 10));

		dagligFast.setDosis(Tidspunkt.MIDDAG, 0.2);
		dagligFast.setDosis(Tidspunkt.AFTEN, 2.0);
		dagligFast.setDosis(Tidspunkt.NAT, 0.0);

		double samlet = dagligFast.samletDosis();

		assertEquals(13.2, samlet, 0.01);
	}
	
	@Test
	public void testCase6() {
		DagligFast dagligFast = service.opretDagligFastOrdination(LocalDate.of(2018, 1, 5), LocalDate.of(2018, 1, 10), patient, laegemiddel, 1.0, 3.0, 5.5, 0.0);
		double samlet = dagligFast.samletDosis();

		assertEquals(57, samlet, 0.0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCase7() {
		service.opretDagligFastOrdination(LocalDate.of(2018, 01, 10), LocalDate.of(2018, 01, 05), patient, laegemiddel, 1.0, 3.0, 5.5, 0.0);
	}
}
