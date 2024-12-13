package org.ethelred.heatcontrol3;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record Humidity(double percentage) {}
