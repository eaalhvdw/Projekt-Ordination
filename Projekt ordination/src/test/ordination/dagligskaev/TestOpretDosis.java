package test.ordination.dagligskaev;

import java.time.LocalDate;
import java.time.LocalTime;
import ordination.DagligSkaev;
import ordination.Laegemiddel;
import ordination.Patient;
import org.junit.Before;
import org.junit.Test;
import service.Service;

public class TestOpretDosis {
    private DagligSkaev dagligSkaev;

    @Before
    public void setUp() {
        Service service = Service.getTestService();

        Patient patient = service.opretPatient("123", "Test Navn", 100);
        Laegemiddel laegemiddel = service.opretLaegemiddel("Laegemiddel", 1.2, 3.4, 5.6, "middel");

        dagligSkaev = service.opretDagligSkaevOrdination(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 1), patient, laegemiddel, new LocalTime[0], new double[0]);
    }

    @Test
    public void testCase22() {
        dagligSkaev.opretDosis(LocalTime.of(8, 0), 0.0);
    }

    @Test
    public void testCase23() {
        dagligSkaev.opretDosis(LocalTime.of(19, 0), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCase24() {
        dagligSkaev.opretDosis(LocalTime.of(0, 0), -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCase25() {
        dagligSkaev.opretDosis(null, 10);
    }
}
