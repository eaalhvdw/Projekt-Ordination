package service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import ordination.DagligFast;
import ordination.DagligSkaev;
import ordination.Laegemiddel;
import ordination.Ordination;
import ordination.PN;
import ordination.Patient;
import ordination.Tidspunkt;
import storage.Storage;

public class Service {
    private Storage storage;
    private static Service service;
    
    private Service() {
        storage = new Storage();
    }
    
    public static Service getService() {
        if (service == null) {
            service = new Service();
        }
        return service;
    }
    
    public static Service getTestService() {
        return new Service();
    }

    private void checkTime(LocalDate start, LocalDate slut) {
        if (!checkStartFoerSlut(start, slut)) throw new IllegalArgumentException("Start dato er efter slut dato!");
    }

    /**
     * Hvis startDato er efter slutDato kastes en IllegalArgumentException og
     * ordinationen oprettes ikke
     * Pre: startDen, slutDen, patient og laegemiddel er ikke null
     *
     * @return opretter og returnerer en PN ordination.
     */
    public PN opretPNOrdination(LocalDate startDen, LocalDate slutDen, Patient patient, Laegemiddel laegemiddel, double antal) {
        checkTime(startDen, slutDen);

        PN pn = new PN(startDen, slutDen);
        pn.setLaegemiddel(laegemiddel);
        pn.setAntalEnheder(antal);

        return pn;
    }
    
    /**
     * Opretter og returnerer en DagligFast ordination. Hvis startDato er efter
     * slutDato kastes en IllegalArgumentException og ordinationen oprettes ikke
     * Pre: startDen, slutDen, patient og laegemiddel er ikke null
     */
    public DagligFast opretDagligFastOrdination(LocalDate startDen, LocalDate slutDen, Patient patient, Laegemiddel laegemiddel, double morgenAntal, double middagAntal, double aftenAntal, double natAntal) {
        checkTime(startDen, slutDen);

        DagligFast dagligFast = new DagligFast(startDen, slutDen);
        dagligFast.setLaegemiddel(laegemiddel);
        dagligFast.setDosis(Tidspunkt.MORGEN, morgenAntal);
        dagligFast.setDosis(Tidspunkt.MIDDAG, middagAntal);
        dagligFast.setDosis(Tidspunkt.AFTEN, aftenAntal);
        dagligFast.setDosis(Tidspunkt.NAT, natAntal);

        return dagligFast;
    }
    
    /**
     * Opretter og returnerer en DagligSkæv ordination. Hvis startDato er efter
     * slutDato kastes en IllegalArgumentException og ordinationen oprettes ikke.
     * Hvis antallet af elementer i klokkeSlet og antalEnheder er forskellige kastes også en IllegalArgumentException.
     *
     * Pre: startDen, slutDen, patient og laegemiddel er ikke null
     */
    public DagligSkaev opretDagligSkaevOrdination(LocalDate startDen, LocalDate slutDen, Patient patient, Laegemiddel laegemiddel, LocalTime[] klokkeSlet, double[] antalEnheder) {
        checkTime(startDen, slutDen);
        if (klokkeSlet.length != antalEnheder.length) throw new IllegalArgumentException("Klokkeslet og antal har ikke samme antal elementer.");

        DagligSkaev dagligSkaev = new DagligSkaev(startDen, slutDen);
        dagligSkaev.setLaegemiddel(laegemiddel);

        for (int i = 0; i < klokkeSlet.length; i++) {
            LocalTime time = klokkeSlet[i];
            double antal = antalEnheder[i];

            dagligSkaev.opretDosis(time, antal);
        }

        return dagligSkaev;
    }
    
    /**
     * En dato for hvornår ordinationen anvendes tilføjes ordinationen. Hvis
     * datoen ikke er indenfor ordinationens gyldighedsperiode kastes en
     * IllegalArgumentException
     * Pre: ordination og dato er ikke null
     */
    public void ordinationPNAnvendt(PN ordination, LocalDate dato) {
        if(dato == null) {
        	throw new IllegalArgumentException("Der er ikke valgt en dato.");
        }
        if (dato.isBefore(ordination.getStartDen()) || dato.isAfter(ordination.getSlutDen())) {
            throw new IllegalArgumentException("Den givne dato er uden for den gyldige periode.");
        } else {
        	ordination.givDosis(dato);
        }
    }
    
    /**
     * Den anbefalede dosis for den pågældende patient (der skal tages hensyn
     * til patientens vægt). Det er en forskellig enheds faktor der skal
     * anvendes, og den er afhængig af patientens vægt.
     * Pre: patient og lægemiddel er ikke null
     */
    public double anbefaletDosisPrDoegn(Patient patient, Laegemiddel laegemiddel) {
        double result;
        if (patient.getVaegt() < 25) {
            result = patient.getVaegt() * laegemiddel.getEnhedPrKgPrDoegnLet();
        }
        else if (patient.getVaegt() > 120) {
            result = patient.getVaegt() * laegemiddel.getEnhedPrKgPrDoegnTung();
        }
        else {
            result = patient.getVaegt()
                * laegemiddel.getEnhedPrKgPrDoegnNormal();
        }
        return result;
    }
    
    /**
     * For et givent vægtinterval og et givent lægemiddel, hentes antallet af
     * ordinationer.
     * Pre: laegemiddel er ikke null
     */
    public int antalOrdinationerPrVægtPrLægemiddel(double vægtStart, double vægtSlut, Laegemiddel laegemiddel) {
        if (vægtSlut < vægtStart) throw new IllegalArgumentException("Slut vægten må ikke være før start vægten!");
        if (vægtStart < 0) throw new IllegalArgumentException("Start vægten kan ikke være negativ.");
        if (vægtSlut < 0) throw new IllegalArgumentException("Slut vægten kan ikke være negativ.");

        int antal = 0;
        /*
        for (Ordination ordination : laegemiddel.getOrdinationer()) {
            // Der er ikke en metode getPatient() på Ordination.
            // Designklassediagrammerne som systemet er baseret 
            // på, er gået tabt så klassesammenhængene er ikke tilgængelige.
            double vaegt = ordination.getPatient().getVaegt();

            if (vægtStart <= vaegt && vaegt <= vægtSlut) {
                antal++;
            }
        }
		*/
        return antal;
    }
    
    public List<Patient> getAllPatienter() {
        return storage.getAllPatienter();
    }
    
    public List<Laegemiddel> getAllLaegemidler() {
        return storage.getAllLaegemidler();
    }
    
    /**
     * Metode der kan bruges til at checke at en startDato ligger før en
     * slutDato.
     *
     * @return true hvis startDato er før slutDato, false ellers.
     */
    private boolean checkStartFoerSlut(LocalDate startDato, LocalDate slutDato) {
        boolean result = true;
        if (slutDato.compareTo(startDato) < 0) {
            result = false;
        }
        return result;
    }
    
    public Patient opretPatient(String cpr, String navn, double vaegt) {
        Patient p = new Patient(cpr, navn, vaegt);
        storage.addPatient(p);
        return p;
    }
    
    public Laegemiddel opretLaegemiddel(String navn,
        double enhedPrKgPrDoegnLet, double enhedPrKgPrDoegnNormal,
        double enhedPrKgPrDoegnTung, String enhed) {
        Laegemiddel lm = new Laegemiddel(navn, enhedPrKgPrDoegnLet,
            enhedPrKgPrDoegnNormal, enhedPrKgPrDoegnTung, enhed);
        storage.addLaegemiddel(lm);
        return lm;
    }
    
    public void createSomeObjects() {
        opretPatient("121256-0512", "Jane Jensen", 63.4);
        opretPatient("070985-1153", "Finn Madsen", 83.2);
        opretPatient("050972-1233", "Hans Jørgensen", 89.4);
        opretPatient("011064-1522", "Ulla Nielsen", 59.9);
        opretPatient("090149-2529", "Ib Hansen", 87.7);
        
        opretLaegemiddel("Acetylsalicylsyre", 0.1, 0.15, 0.16, "Styk");
        opretLaegemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        opretLaegemiddel("Fucidin", 0.025, 0.025, 0.025, "Styk");
        opretLaegemiddel("Methotrexat", 0.01, 0.015, 0.02, "Styk");
        
        opretPNOrdination(LocalDate.of(2015, 1, 1), LocalDate.of(2015, 1, 12),
            storage.getAllPatienter().get(0), storage.getAllLaegemidler()
                .get(1),
            123);
        
        opretPNOrdination(LocalDate.of(2015, 2, 12), LocalDate.of(2015, 2, 14),
            storage.getAllPatienter().get(0), storage.getAllLaegemidler()
                .get(0),
            3);
        
        opretPNOrdination(LocalDate.of(2015, 1, 20), LocalDate.of(2015, 1, 25),
            storage.getAllPatienter().get(3), storage.getAllLaegemidler()
                .get(2),
            5);
        
        opretPNOrdination(LocalDate.of(2015, 1, 1), LocalDate.of(2015, 1, 12),
            storage.getAllPatienter().get(0), storage.getAllLaegemidler()
                .get(1),
            123);
        
        opretDagligFastOrdination(LocalDate.of(2015, 1, 10),
            LocalDate.of(2015, 1, 12), storage.getAllPatienter().get(1),
            storage.getAllLaegemidler().get(1), 2, -1, 1, -1);
        
        LocalTime[] kl = { LocalTime.of(12, 0), LocalTime.of(12, 40),
            LocalTime.of(16, 0), LocalTime.of(18, 45) };
        double[] an = { 0.5, 1, 2.5, 3 };
        
        opretDagligSkaevOrdination(LocalDate.of(2015, 1, 23),
            LocalDate.of(2015, 1, 24), storage.getAllPatienter().get(1),
            storage.getAllLaegemidler().get(2), kl, an);
    }  
}
