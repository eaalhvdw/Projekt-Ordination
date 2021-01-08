package test.ordination.dagligskaev;

import java.time.LocalDate;
import java.time.LocalTime;
import ordination.DagligSkaev;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestSamletDosis {
    private DagligSkaev dagligSkaev;

	@Before
	public void setUp() {
		dagligSkaev = new DagligSkaev(LocalDate.of(2018, 3, 7), LocalDate.of(2018, 3, 15));
	}
	
	@Test
	public void testCase15() {
		double samlet = dagligSkaev.samletDosis();
		assertEquals(0.0, samlet, 0.0);
	}
	
	@Test
	public void testCase16() {
		dagligSkaev.opretDosis(LocalTime.of(10, 0), 2.0);
		dagligSkaev.opretDosis(LocalTime.of(0, 0), 1.0);

		double samlet = dagligSkaev.samletDosis();

		assertEquals(3.0, samlet, 0.0);
	}
	
	@Test
	public void testCase17() {
		dagligSkaev.opretDosis(LocalTime.of(10, 0), 2.0);
		dagligSkaev.opretDosis(LocalTime.of(0, 0), 1.0);
		dagligSkaev.opretDosis(LocalTime.of(0, 0), 0.0);
		dagligSkaev.opretDosis(LocalTime.of(0, 0), 10.2);

		double samlet = dagligSkaev.samletDosis();

		assertEquals(13.2, samlet, 0.0);
	}
}
