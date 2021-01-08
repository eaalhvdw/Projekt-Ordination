package ordination;

import java.time.*;
import java.time.temporal.ChronoUnit;

public abstract class Ordination {
    
	private LocalDate startDen;
    private LocalDate slutDen;
    private Laegemiddel laegemiddel;

    /**
     * Constructor
     * Precondition: startDen må ikke være efter slutDen.
     * @param startDen startdatoen for ordinationens gyldighedsperiode.
     * @param slutDen slutdatoen for ordinationens gyldighedsperiode.
     */
    public Ordination(LocalDate startDen, LocalDate slutDen) {
        this.startDen = startDen;
        this.slutDen = slutDen;
    }

    public LocalDate getStartDen() {
        return startDen;
    }	

    public LocalDate getSlutDen() {
        return slutDen;
    }

    public Laegemiddel getLaegemiddel() {
        return laegemiddel;
    }

    public void setLaegemiddel(Laegemiddel laegemiddel) {
        if (this.laegemiddel == null) {
            this.laegemiddel = laegemiddel;
        }
    }

    /**
     * Antal hele dage mellem startdato og slutdato. Begge dage inklusive.
     * @return antal dage ordinationen gælder for
     */
    public int antalDage() {
        return (int) ChronoUnit.DAYS.between(startDen, slutDen) + 1;
    }

    @Override
    public String toString() {
        return startDen.toString();
    }

    /**
     * Returnerer den totale dosis der er givet i den periode ordinationen er gyldig.
     * @return
     */
    public abstract double samletDosis();

    /**
     * Returnerer den gennemsnitlige dosis givet pr dag i den periode ordinationen er gyldig.
     * @return
     */
    public abstract double doegnDosis();

    /**
     * Returnerer ordinationstypen som en String.
     * @return
     */
    public abstract String getType();
}
