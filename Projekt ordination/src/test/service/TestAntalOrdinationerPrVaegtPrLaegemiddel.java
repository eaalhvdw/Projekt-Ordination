package test.service;

import java.time.LocalDate;
import ordination.Laegemiddel;
import ordination.Ordination;
import ordination.Patient;
import org.junit.Before;
import org.junit.Test;
import service.Service;

import static org.junit.Assert.*;

public class TestAntalOrdinationerPrVaegtPrLaegemiddel {
    private Service service;
    private Laegemiddel laegemiddel;

    @Before
    public void setUp() {
        service = Service.getTestService();
        laegemiddel = service.opretLaegemiddel("Test Laegemiddel", 1.2, 3.4, 5.6,  "middel");
    }

    @Test
    public void testCase70() {
        setUpPatienter(10, 20, 30, 100, 120);

        int antal = service.antalOrdinationerPrVægtPrLægemiddel(10, 100, laegemiddel);

        assertEquals(4, antal);
    }

    @Test
    public void testCase71() {
        setUpPatienter(10, 20, 25, 100, 120);

        int antal = service.antalOrdinationerPrVægtPrLægemiddel(20, 25, laegemiddel);

        assertEquals(2, antal);
    }

    @Test
    public void testCase72() {
        setUpPatienter(10, 25, 35, 100, 120);

        int antal = service.antalOrdinationerPrVægtPrLægemiddel(15, 20, laegemiddel);

        assertEquals(0, antal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCase73() {
        setUpPatienter(10, 20, 30, 100, 120);

        service.antalOrdinationerPrVægtPrLægemiddel(75, 50, laegemiddel);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCase74() {
        setUpPatienter(10, 20, 30, 100, 120);

        service.antalOrdinationerPrVægtPrLægemiddel(-10, 20, laegemiddel);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCase75() {
        setUpPatienter(10, 20, 30, 100, 120);

        service.antalOrdinationerPrVægtPrLægemiddel(20, -10, laegemiddel);
    }

    @Test(expected = NullPointerException.class)
    public void testCase76() {
        setUpPatienter(10, 20, 30, 100, 120);

        service.antalOrdinationerPrVægtPrLægemiddel(10, 100, null);
    }

    private void setUpPatienter(double... vaegte) {
        for (int i = 0; i < vaegte.length; i++) {
            Patient patient = service.opretPatient(String.valueOf(i), "Test Navn #" + i, vaegte[i]);
            Ordination ordination = service.opretPNOrdination(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 1), patient, laegemiddel, 0);
            patient.addOrdination(ordination);
        }
    }
}
