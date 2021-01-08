package ordination;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class PN extends Ordination {
   
	private double antalEnheder;
    private Map<LocalDate, Integer> doser = new HashMap<>();

    /**
     * Constructor
     * Precondition: startDen må ikke være efter slutDen.
     * @param startDen startdatoen for ordinationens gyldighedsperiode.
     * @param slutDen slutdatoen for ordinationens gyldighedsperiode.
     */
    public PN(LocalDate startDen, LocalDate slutDen) {
        super(startDen, slutDen);
    }

    /**
     * Registrerer at der er givet en dosis paa dagen givesDen
     * Returnerer true hvis givesDen er inden for ordinationens gyldighedsperiode og datoen huskes
     * Retrurner false ellers og datoen givesDen ignoreres
     * @param givesDen
     * @return
     */
    public boolean givDosis(LocalDate givesDen) {
        if (givesDen.isBefore(getStartDen())) {
            return false;
        }

        if (givesDen.isAfter(getSlutDen())) {
            return false;
        }

        int antal = doser.getOrDefault(givesDen, 0) + 1;
        doser.put(givesDen, antal);

        return true;
    }

    /**
     * Returnerer antal gange ordinationen er anvendt
     * @return
     */
    public int getAntalGangeGivet() {
    	int sum = 0;
    	
    	for (Integer antal : doser.values()) {
    		sum += antal;
    	}
    	
    	return sum;
    }
    
    /**
     * Beregner den samlede dosis, en patient har fået i hele ordineringens gyldighedsperiode.
     */
    @Override
    public double samletDosis() {
    	return (double) getAntalGangeGivet() * antalEnheder;
    }
    
    /**
     * Beregner den daglige dosis, som en patient har fået i ordineringens gyldighedsperiode.
     */
    @Override
    public double doegnDosis() {
        if (doser.size() == 0) {
            return 0.0;
        }
        return samletDosis() / antalDage();
    }

    @Override
    public String getType() {
        return "PN";
    }

    public void setAntalEnheder(double antalEnheder) {
        if (antalEnheder < 0) throw new IllegalArgumentException("Antal må ikke være negativ!");

        this.antalEnheder = antalEnheder;
    }

    public double getAntalEnheder() {
        return antalEnheder;
    }

//    @Override
//    public int antalDage() {
//        LocalDate start = aktuelStart();
//        LocalDate slut = aktuelSlut();
//
//        if (start == null || slut == null) {
//            return 0;
//        }
//        return (int) ChronoUnit.DAYS.between(start, slut) + 1;
//    }

    private LocalDate aktuelStart() {
        return doser.keySet().stream().min(LocalDate::compareTo).orElse(null);
    }

    private LocalDate aktuelSlut() {
        return doser.keySet().stream().max(LocalDate::compareTo).orElse(null);
    }
}
