package ordination;

import java.time.LocalTime;

public enum Tidspunkt {
    MORGEN(LocalTime.parse("08:00")),
    MIDDAG(LocalTime.NOON),
    AFTEN(LocalTime.parse("19:00")),
    NAT(LocalTime.MIDNIGHT);

    private LocalTime time;

    Tidspunkt(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }
}
