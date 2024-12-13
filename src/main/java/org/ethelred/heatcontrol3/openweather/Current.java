package org.ethelred.heatcontrol3.openweather;

import io.micronaut.serde.annotation.Serdeable;
import org.ethelred.heatcontrol3.Humidity;
import org.ethelred.heatcontrol3.Temperature;

import java.time.LocalDateTime;
import java.util.List;

@Serdeable
public
record Current(LocalDateTime dt, Temperature temp, /*Humidity humidity,*/ List<Weather> weather) {
}
