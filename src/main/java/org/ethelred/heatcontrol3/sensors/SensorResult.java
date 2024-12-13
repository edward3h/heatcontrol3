package org.ethelred.heatcontrol3.sensors;

import io.micronaut.serde.annotation.Serdeable;
import org.ethelred.heatcontrol3.Temperature;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.Temporal;

@Serdeable
public record SensorResult(OffsetDateTime time, String channel,
                           @Serdeable.Deserializable(using = ScaledFahrenheitSerde.class) Temperature temperature, boolean batteryOk) {
    public String age(Temporal now) {
        var duration = Duration.between(time, now).abs();
        long n = duration.toDays();
        if (n > 0) {
            return n + " days";
        }
        n = duration.toHours();
        if (n > 0) {
            return n + " hours";
        }
        n = duration.toMinutes();
        if (n > 0) {
            return n + " minutes";
        }
        return duration.toSeconds() + " seconds";
    }
}
