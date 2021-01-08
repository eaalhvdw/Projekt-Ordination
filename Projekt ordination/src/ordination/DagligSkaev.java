package ordination;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DagligSkaev extends Ordination {
    
	private List<Dosis> doser = new ArrayList<>();

    /**
     * Constructor
     * Precondition: startDen må ikke være efter slutDen.
     * @param startDen startdatoen for ordinationens gyldighedsperiode.
     * @param slutDen slutdatoen for ordinationens gyldighedsperiode.
     */
    public DagligSkaev(LocalDate startDen, LocalDate slutDen) {
        super(startDen, slutDen);
    }

    public void opretDosis(LocalTime tid, double antal) {
        if (antal < 0) throw new IllegalArgumentException("Antal må ikke være negativt!");
        if (tid == null) throw new IllegalArgumentException("Tid må ikke være null!");

        Dosis dosis = new Dosis(tid, antal);

        doser.add(dosis);
    }

    public List<Dosis> getDoser() {
        return new ArrayList<>(doser);
    }

    /**
     * Beregner den samlede dosis, en patient har fået i hele ordineringens gyldighedsperiode.
     * Precondition: doser må ikke være null.
     */
    @Override
    public double samletDosis() {
    	return doegnDosis() * antalDage();
    }

    /**
     * Beregner den daglige dosis, som en patient har fået i ordineringens gyldighedsperiode.
     */
    @Override
    public double doegnDosis() {
    	double sum = 0.0;
    	
    	for (Dosis dosis : doser) {
    		sum += dosis.getAntal();
    	}    	
    	return sum;
    }

    @Override
    public String getType() {
        return "Skaev";
    }
}
