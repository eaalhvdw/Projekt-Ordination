package test.ordination.dagligskaev;

import java.time.LocalDate;
import java.time.LocalTime;
import ordination.DagligSkaev;
import ordination.Laegemiddel;
import ordination.Patient;
import org.junit.Before;
import org.junit.Test;
import service.Service;

import static org.junit.Assert.assertEquals;

public class TestDoegnDosis {
    private Service service;

    @Before
    public void setUp() {
        service = Service.getTestService();
    }

    @Test
    public void testCase18() {
        DagligSkaev dagligSkaev = opretDagligSkaev(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 2, 1));

        double dosis = dagligSkaev.doegnDosis();

        assertEquals(0.0, dosis, 0.0);
    }

    @Test
    public void testCase19() {
        DagligSkaev dagligSkaev = opretDagligSkaev(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 1), 2.0, 1.0);

        double dosis = dagligSkaev.doegnDosis();

        assertEquals(3.0, dosis, 0.0);
    }

    @Test
    public void testCase20() {
        DagligSkaev dagligSkaev = opretDagligSkaev(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 4), 2.0, 1.0, 0.0, 7.0);

        double dosis = dagligSkaev.doegnDosis();

        assertEquals(2.5, dosis, 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCase21() {
        opretDagligSkaev(LocalDate.of(2018, 1, 2), LocalDate.of(2018, 1, 1));
    }

    private DagligSkaev opretDagligSkaev(LocalDate start, LocalDate slut, double... antal) {
        Patient patient = service.opretPatient("123", "Test Navn", 100);
        Laegemiddel laegemiddel = service.opretLaegemiddel("Laegemiddel", 1.2, 3.4, 5.6, "middel");
        LocalTime[] tidspunkter = new LocalTime[antal.length];

        for (int i = 0; i < tidspunkter.length; i++) {
            tidspunkter[i] = LocalTime.of(12, i);
        }

        return service.opretDagligSkaevOrdination(start, slut, patient, laegemiddel, tidspunkter, antal);
    }
}
