package test.ordination.pn;

import java.time.LocalDate;
import ordination.Laegemiddel;
import ordination.PN;
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
    public void testCase29() {
        PN pn = opretPN(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 2, 1), 10.0);
        double dosis = pn.doegnDosis();

        assertEquals(0.0, dosis, 0.0);
    }

    @Test
    public void testCase30() {
        PN pn = opretPN(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 1), 1.0);

        for (int i = 0; i < 5; i++) {
            pn.givDosis(LocalDate.of(2018, 1, 1));
        }

        double dosis = pn.doegnDosis();

        assertEquals(5.0, dosis, 0.0);
    }

    @Test
    public void testCase31() {
        PN pn = opretPN(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 4), 1.0);

        pn.givDosis(LocalDate.of(2018, 1, 1));

        pn.givDosis(LocalDate.of(2018, 1, 2));
        pn.givDosis(LocalDate.of(2018, 1, 2));

        pn.givDosis(LocalDate.of(2018, 1, 3));
        pn.givDosis(LocalDate.of(2018, 1, 3));
        pn.givDosis(LocalDate.of(2018, 1, 3));

        pn.givDosis(LocalDate.of(2018, 1, 4));
        pn.givDosis(LocalDate.of(2018, 1, 4));
        pn.givDosis(LocalDate.of(2018, 1, 4));
        pn.givDosis(LocalDate.of(2018, 1, 4));

        double dosis = pn.doegnDosis();

        assertEquals(2.5, dosis, 0.0);
    }

    @Test
    public void testCase32() {
        PN pn = opretPN(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 4), 1.5);

        pn.givDosis(LocalDate.of(2018, 1, 1));

        pn.givDosis(LocalDate.of(2018, 1, 2));
        pn.givDosis(LocalDate.of(2018, 1, 2));

        pn.givDosis(LocalDate.of(2018, 1, 3));
        pn.givDosis(LocalDate.of(2018, 1, 3));
        pn.givDosis(LocalDate.of(2018, 1, 3));

        double dosis = pn.doegnDosis();

        assertEquals(3.0, dosis, 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCase33() {
        opretPN(LocalDate.of(2018, 1, 2), LocalDate.of(2018, 1, 1), 1.0);
    }

    @Test
    public void testCase34() {
        PN pn = opretPN(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 4), 1.0);

        pn.givDosis(LocalDate.of(2018, 1, 1));

        pn.givDosis(LocalDate.of(2018, 1, 2));
        pn.givDosis(LocalDate.of(2018, 1, 2));

        pn.givDosis(LocalDate.of(2018, 1, 5));
        pn.givDosis(LocalDate.of(2018, 1, 5));
        pn.givDosis(LocalDate.of(2018, 1, 5));

        double samlet = pn.doegnDosis();

        assertEquals(1.5, samlet, 0.0);
    }

    @Test
    public void testCase35() {
        PN pn = opretPN(LocalDate.of(2018, 1, 2), LocalDate.of(2018, 1, 4), 1.0);

        pn.givDosis(LocalDate.of(2018, 1, 1));

        pn.givDosis(LocalDate.of(2018, 1, 2));
        pn.givDosis(LocalDate.of(2018, 1, 2));

        pn.givDosis(LocalDate.of(2018, 1, 3));
        pn.givDosis(LocalDate.of(2018, 1, 3));
        pn.givDosis(LocalDate.of(2018, 1, 3));

        double samlet = pn.doegnDosis();

        assertEquals(2.5, samlet, 0.0);
    }

    private PN opretPN(LocalDate start, LocalDate slut, double antal) {
        Patient patient = service.opretPatient("123", "Test Navn", 100);
        Laegemiddel laegemiddel = service.opretLaegemiddel("Laegemiddel", 1.2, 3.4, 5.6, "middel");

        return service.opretPNOrdination(start, slut, patient, laegemiddel, antal);
    }
}
