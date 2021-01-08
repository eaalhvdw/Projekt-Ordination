package test.ordination.pn;

import java.time.LocalDate;
import ordination.Laegemiddel;
import ordination.PN;
import ordination.Patient;
import org.junit.Before;
import org.junit.Test;
import service.Service;

import static org.junit.Assert.*;

public class TestAntalGangeGivet {
    private PN pn;

    @Before
    public void setUp() {
        Service service = Service.getTestService();
        Patient patient = service.opretPatient("123", "Test Navn", 100);
        Laegemiddel laegemiddel = service.opretLaegemiddel("Laegemiddel", 1.2, 3.4, 5.6, "middel");

        pn = service.opretPNOrdination(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 2, 1), patient, laegemiddel, 1.0);
    }

    @Test
    public void testCase36() {
        int antal = pn.getAntalGangeGivet();

        assertEquals(0, antal);
    }

    @Test
    public void testCase37() {
        pn.givDosis(LocalDate.of(2018, 1, 1));
        pn.givDosis(LocalDate.of(2018, 1, 1));
        pn.givDosis(LocalDate.of(2018, 1, 1));

        int antal = pn.getAntalGangeGivet();

        assertEquals(3, antal);
    }

    @Test
    public void testCase38() {
        for (int i = 0; i < 3; i++) {
            pn.givDosis(LocalDate.of(2018, 1, 1));
        }

        for (int i = 0; i < 10; i++) {
            pn.givDosis(LocalDate.of(2018, 1, 2));
        }

        pn.givDosis(LocalDate.of(2018, 1, 3));

        int antal = pn.getAntalGangeGivet();

        assertEquals(14, antal);
    }
}
