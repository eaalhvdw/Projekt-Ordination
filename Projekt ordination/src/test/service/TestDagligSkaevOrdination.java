package test.service;

import ordination.DagligSkaev;
import ordination.Laegemiddel;
import ordination.Patient;
import org.junit.Before;
import org.junit.Test;

import service.Service;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class TestDagligSkaevOrdination {
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
    public void testCase55() {
    	LocalTime[] tid = new LocalTime[] { LocalTime.of(8, 0), LocalTime.of(10, 0), LocalTime.of(12, 0), LocalTime.of(0, 0) };
    	double[] antalEnheder = new double[] { 1.0, 3.0, 0.2, 2.0 }; 	
    	DagligSkaev dagligSkaev = service.opretDagligSkaevOrdination(LocalDate.of(2018, 2, 1), LocalDate.of(2018, 2, 1), patient, laegemiddel, tid, antalEnheder);
    	
    	assertNotNull(dagligSkaev);
    }
    
    @Test(expected = NullPointerException.class)
    public void testCase56() {
    	LocalTime[] tid = new LocalTime[] { LocalTime.of(8, 0), LocalTime.of(10, 0), LocalTime.of(12, 0), LocalTime.of(0, 0) };
    	double[] antalEnheder = new double[] { 1.0, 3.0, 0.2, 2.0 };

    	service.opretDagligSkaevOrdination(LocalDate.of(2018, 2, 1), LocalDate.of(2018, 2, 2), patient, null, tid, antalEnheder);
    }
    
    @Test(expected = NullPointerException.class)
    public void testCase57() {
    	LocalTime[] tid = new LocalTime[] { LocalTime.of(8, 0), LocalTime.of(10, 0), LocalTime.of(12, 0), LocalTime.of(0, 0) };
    	double[] antalEnheder = new double[] { 1.0, 3.0, 0.2, 2.0 }; 

    	service.opretDagligSkaevOrdination(LocalDate.of(2018,  2,  1), LocalDate.of(2018, 2, 2), null, laegemiddel, tid, antalEnheder);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCase58() {
    	LocalTime[] tid = new LocalTime[] { LocalTime.of(8, 0), LocalTime.of(10, 0), LocalTime.of(12, 0), LocalTime.of(0, 0) };
    	double[] antalEnheder = new double[] { 1.0, 3.0, 0.2, 2.0 };

    	service.opretDagligSkaevOrdination(LocalDate.of(2018, 2, 2), LocalDate.of(2018, 2, 1), patient, laegemiddel, tid, antalEnheder);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCase59() {
    	LocalTime[] tid = new LocalTime[] { LocalTime.of(8, 0), LocalTime.of(10, 0), LocalTime.of(12, 0), LocalTime.of(0, 0) };
    	double[] antalEnheder = new double[] { 1.0, 3.0, 0.2, 2.0, 7.0 }; 

    	service.opretDagligSkaevOrdination(LocalDate.of(2018,  2,  1), LocalDate.of(2008, 2, 1), patient, laegemiddel, tid, antalEnheder);
    }
    
    @Test
    public void testCase60() {
    	LocalTime[] tid = new LocalTime[] { };
    	double[] antalEnheder = new double[] { };
    	DagligSkaev dagligSkaev = service.opretDagligSkaevOrdination(LocalDate.of(2018,  02,  01), LocalDate.of(2018, 02, 01), patient, laegemiddel, tid, antalEnheder);
    	
    	assertNotNull(dagligSkaev);
    }
}
