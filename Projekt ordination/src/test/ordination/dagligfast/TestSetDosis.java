package test.ordination.dagligfast;

import org.junit.Before;
import org.junit.Test;

import ordination.DagligFast;
import ordination.Tidspunkt;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class TestSetDosis {

	private DagligFast dagligFast;

	@Before
	public void setUp() {
		dagligFast = new DagligFast(LocalDate.of(2018, 2, 5), LocalDate.of(2018, 2, 20));
	}

	@Test
	public void testCase11() {
		dagligFast.setDosis(Tidspunkt.MORGEN, 0);
	}
	
	@Test
	public void testCase12() {
		dagligFast.setDosis(Tidspunkt.AFTEN, 1);
	}

	@Test
	public void testCase13() {
		dagligFast.setDosis(Tidspunkt.NAT, -1);

		assertEquals(0.0, dagligFast.samletDosis(), 0.0);
	}
	
	@Test(expected = NullPointerException.class)
	public void testCase14() {
		dagligFast.setDosis(null, 10);
	}
}
