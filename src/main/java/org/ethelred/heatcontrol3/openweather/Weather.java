package org.ethelred.heatcontrol3.openweather;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record Weather(int id, String main, String icon) {
}
