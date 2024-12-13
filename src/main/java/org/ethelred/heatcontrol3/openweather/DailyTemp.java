package org.ethelred.heatcontrol3.openweather;

import io.micronaut.serde.annotation.Serdeable;
import org.ethelred.heatcontrol3.Temperature;

@Serdeable
public
record DailyTemp(Temperature min, Temperature max) {
    public String display() {
        return """
    <span class="low">%s</span>/<span class="high">%s</span>
    """.formatted(min.display(), max.display());
    }
}
