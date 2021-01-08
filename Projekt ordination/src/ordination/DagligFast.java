package ordination;

import java.time.LocalDate;
import java.util.Arrays;

public class DagligFast extends Ordination {
    
	private Dosis[] doser = new Dosis[4];

    /**
     * Constructor
     * Precondition: startDen må ikke være efter slutDen.
     * @param startDen startdatoen for ordinationens gyldighedsperiode.
     * @param slutDen slutdatoen for ordinationens gyldighedsperiode.
     */
    public DagligFast(LocalDate startDen, LocalDate slutDen) {
        super(startDen, slutDen);
    }

    public void setDosis(Tidspunkt tidspunkt, double antal) {
    	Dosis dosis = new Dosis(tidspunkt.getTime(), antal);

    	if (antal >= 0) {
            doser[tidspunkt.ordinal()] = dosis;
        }
    }

    public Dosis[] getDoser() {
        return Arrays.copyOfRange(doser, 0, doser.length);
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
            if (dosis != null) {
                sum += dosis.getAntal();
            }
        }
        return sum;
    }

    @Override
    public String getType() {
        return "Fast";
    }
}
