package test.ordination.pn;

import java.time.LocalDate;
import ordination.Laegemiddel;
import ordination.PN;
import ordination.Patient;
import org.junit.Before;
import org.junit.Test;
import service.Service;

import static org.junit.Assert.*;

public class TestSamletDosis {
    private Service service;

    @Before
    public void setUp() {
        service = Service.getTestService();
    }

    @Test
    public void testCase26() {
        PN pn = opretPN(1);
        double samlet = pn.samletDosis();

        assertEquals(0.0, samlet, 0.0);
    }

    @Test
    public void testCase27() {
        PN pn = opretPN(0);

        pn.givDosis(LocalDate.of(2018, 1, 1));
        pn.givDosis(LocalDate.of(2018, 1, 1));
        pn.givDosis(LocalDate.of(2018, 1, 1));

        double samlet = pn.samletDosis();
        assertEquals(0.0, samlet, 0.0);
    }

    @Test
    public void testCase28() {
        PN pn = opretPN(10);

        for (int i = 0; i < 3; i++) {
            pn.givDosis(LocalDate.of(2018, 1, 1));
        }

        for (int i = 0; i < 10; i++) {
            pn.givDosis(LocalDate.of(2018, 1, 2));
        }

        pn.givDosis(LocalDate.of(2018, 1, 3));

        double samlet = pn.samletDosis();
        assertEquals(140.0, samlet, 0.0);
    }

    private PN opretPN(double antal) {
        final Patient patient = service.opretPatient("123", "Test Navn", 100);
        final Laegemiddel laegemiddel = service.opretLaegemiddel("Laegemiddel", 1.2, 3.4, 5.6, "middel");

        return service.opretPNOrdination(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 2, 1), patient, laegemiddel, antal);
    }
}
