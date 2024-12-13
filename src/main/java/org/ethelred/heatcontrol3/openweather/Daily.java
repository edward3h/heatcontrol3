package org.ethelred.heatcontrol3.openweather;

import io.micronaut.serde.annotation.Serdeable;

import java.time.LocalDateTime;
import java.util.List;

@Serdeable
public
record Daily(LocalDateTime dt, DailyTemp temp, List<Weather> weather) {
}
