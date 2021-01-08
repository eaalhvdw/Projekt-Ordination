package test.service;

import java.time.LocalDate;
import ordination.DagligFast;
import ordination.Laegemiddel;
import ordination.Patient;
import org.junit.Before;
import org.junit.Test;
import service.Service;

import static org.junit.Assert.*;

public class TestDagligFastOrdination {
    private Service service;
    private Patient patient;
    private Laegemiddel laegemiddel;

    @Before
    public void setUp() {
        service = Service.getTestService();
        patient = new Patient("123", "Test navn", 100);
        laegemiddel = new Laegemiddel("Test laegemiddel", 1.2, 2.3, 4.5, "middel");
    }

    @Test
    public void testCase49() {
        DagligFast dagligFast = service.opretDagligFastOrdination(LocalDate.of(2018, 2, 1), LocalDate.of(2018, 2, 1), patient, laegemiddel, 0, 0, 0, 0);

        assertNotNull(dagligFast);
    }

    @Test(expected = NullPointerException.class)
    public void testCase50() {
        service.opretDagligFastOrdination(LocalDate.of(2018, 2, 1), LocalDate.of(2018, 2, 1), null, laegemiddel, 1, 0, 1, 1);
    }

    @Test(expected = NullPointerException.class)
    public void testCase51() {
        service.opretDagligFastOrdination(LocalDate.of(2018, 2, 1), LocalDate.of(2018, 2, 1), patient, null, 1, 2, 1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCase52() {
        DagligFast dagligFast = service.opretDagligFastOrdination(LocalDate.of(2018, 2, 2), LocalDate.of(2018, 2, 1), patient, laegemiddel, 0, 0, 0, 0);
    }

    @Test()
    public void testCase53() {
        DagligFast dagligFast = service.opretDagligFastOrdination(LocalDate.of(2018, 2, 1), LocalDate.of(2018, 2, 1), patient, laegemiddel, -1, 0, 0, 1);

        assertEquals(1.0, dagligFast.samletDosis(), 0.0);
    }

    @Test
    public void testCase54() {
        DagligFast dagligFast = service.opretDagligFastOrdination(LocalDate.of(2018, 2, 1), LocalDate.of(2018, 2, 1), patient, laegemiddel, -1, -1, -1, -1);

        assertEquals(0.0, dagligFast.samletDosis(), 0.0);
    }
}
