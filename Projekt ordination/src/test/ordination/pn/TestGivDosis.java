package test.ordination.pn;

import java.time.LocalDate;
import ordination.Laegemiddel;
import ordination.PN;
import ordination.Patient;
import org.junit.Before;
import org.junit.Test;
import service.Service;

import static org.junit.Assert.*;

public class TestGivDosis {
    private Service service;

    @Before
    public void setUp() {
        service = Service.getTestService();
    }

    @Test
    public void testCase39() {
        PN pn = opretPN(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 1));
        boolean success = pn.givDosis(LocalDate.of(2018, 1, 1));

        assertTrue(success);
    }

    @Test
    public void testCase40() {
        PN pn = opretPN(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 5));
        boolean success = pn.givDosis(LocalDate.of(2018, 1, 2));

        assertTrue(success);
    }

    @Test
    public void testCase41() {
        PN pn = opretPN(LocalDate.of(2018, 1, 2), LocalDate.of(2018, 1, 5));
        boolean success = pn.givDosis(LocalDate.of(2018, 1, 1));

        assertFalse(success);
    }

    @Test
    public void testCase42() {
        PN pn = opretPN(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 4));
        boolean success = pn.givDosis(LocalDate.of(2018, 1, 5));

        assertFalse(success);
    }

    private PN opretPN(LocalDate start, LocalDate slut) {
        Patient patient = service.opretPatient("123", "Test Navn", 100);
        Laegemiddel laegemiddel = service.opretLaegemiddel("Laegemiddel", 1.2, 3.4, 5.6, "middel");

        return service.opretPNOrdination(start, slut, patient, laegemiddel, 0.0);
    }
}
