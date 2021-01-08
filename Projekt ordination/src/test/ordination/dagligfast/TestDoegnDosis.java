package test.ordination.dagligfast;

import org.junit.Before;
import org.junit.Test;

import ordination.DagligFast;
import ordination.Tidspunkt;

import static org.junit.Assert.*;

import java.time.LocalDate;

public class TestDoegnDosis {
	private DagligFast dagligFast;

	@Before
	public void setUp() {
		dagligFast = new DagligFast(LocalDate.of(2018, 2, 5), LocalDate.of(2018, 2, 20));
	}

	@Test
	public void testCase8() {
		double samlet = dagligFast.doegnDosis();

		assertEquals(0.0, samlet, 0.0);	
	}
	
	@Test
	public void testCase9() {
		dagligFast.setDosis(Tidspunkt.MIDDAG, 0.2);
		dagligFast.setDosis(Tidspunkt.AFTEN, 2.0);
		dagligFast.setDosis(Tidspunkt.NAT, 0.0);

		double samlet = dagligFast.doegnDosis();

		assertEquals(2.2, samlet, 0.0);
	}
	
	@Test
	public void testCase10() {
		dagligFast.setDosis(Tidspunkt.MORGEN, 1.0);
		dagligFast.setDosis(Tidspunkt.MIDDAG, 3.5);
		dagligFast.setDosis(Tidspunkt.AFTEN, 5.5);
		dagligFast.setDosis(Tidspunkt.NAT, 0.0);

		double samlet = dagligFast.doegnDosis();

		assertEquals(10, samlet, 0.0);
	}
}
