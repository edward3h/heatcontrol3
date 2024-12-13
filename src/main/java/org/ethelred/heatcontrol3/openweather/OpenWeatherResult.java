package org.ethelred.heatcontrol3.openweather;

import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

@Serdeable
public
record OpenWeatherResult(Current current, List<Daily> daily) {
    public String minMax() {
        if (daily.isEmpty()) {
            return "";
        }
        var first = daily.getFirst();
        return first.temp().display();
    }

    public String summary() {
        if (current.weather().isEmpty()) {
            return "";
        }
        var first = current.weather().getFirst();
        return first.main();
    }
}
