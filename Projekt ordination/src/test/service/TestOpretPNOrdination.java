package test.service;

import java.time.LocalDate;
import ordination.Laegemiddel;
import ordination.PN;
import ordination.Patient;
import org.junit.Before;
import org.junit.Test;
import service.Service;

import static org.junit.Assert.*;

public class TestOpretPNOrdination {
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
    public void testCase43() {
        PN pn = service.opretPNOrdination(LocalDate.of(2018, 2, 1), LocalDate.of(2018, 2, 2), patient, laegemiddel, 2);

        assertNotNull(pn);
    }

    @Test
    public void testCase44() {
        PN pn = service.opretPNOrdination(LocalDate.of(2018, 2, 1), LocalDate.of(2018, 2, 1), patient, laegemiddel, 0);

        assertNotNull(pn);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCase45() {
        service.opretPNOrdination(LocalDate.of(2018, 2, 2), LocalDate.of(2018, 2, 1), patient, laegemiddel, 0);
    }

    @Test(expected = NullPointerException.class)
    public void testCase46() {
        service.opretPNOrdination(LocalDate.of(2018, 2, 1), LocalDate.of(2018, 2, 1), null, laegemiddel, 0);
    }

    @Test(expected = NullPointerException.class)
    public void testCase47() {
        service.opretPNOrdination(LocalDate.of(2018, 2, 1), LocalDate.of(2018, 2, 1), patient, null, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCase48() {
        service.opretPNOrdination(LocalDate.of(2018, 2, 1), LocalDate.of(2018, 2, 1), patient, laegemiddel, -1);
    }
}
