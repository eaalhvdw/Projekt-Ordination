package test.service;

import java.time.LocalDate;
import ordination.Laegemiddel;
import ordination.PN;
import ordination.Patient;
import org.junit.Before;
import org.junit.Test;
import service.Service;

public class TestOrdinationPNAnvendt {
    private Service service;

    @Before
    public void setUp() {
        service = Service.getTestService();
    }

    @Test
    public void testCase61() {
        PN pn = opretPN(1);

        service.ordinationPNAnvendt(pn, LocalDate.of(2018, 2, 1));
    }

    @Test
    public void testCase62() {
        PN pn = opretPN(3);

        service.ordinationPNAnvendt(pn, LocalDate.of(2018, 2, 2));
    }

    @Test(expected = NullPointerException.class)
    public void testCase63() {
        service.ordinationPNAnvendt(null, LocalDate.of(2018, 2, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCase64() {
        PN pn = opretPN(3);

        service.ordinationPNAnvendt(pn, LocalDate.of(2018, 2, 4));
    }

    @Test
    public void testCase65() {
        PN pn = opretPN(6);

        service.ordinationPNAnvendt(pn, LocalDate.of(2018, 2, 6));
    }

    private PN opretPN(int dagSlut) {
        return service.opretPNOrdination(LocalDate.of(2018, 2, 1), LocalDate.of(2018, 2, dagSlut), new Patient("123", "Test Navn", 100), new Laegemiddel("Test Laegemiddel", 1.2, 3.4, 5.6, "middel"), 0);
    }
}
