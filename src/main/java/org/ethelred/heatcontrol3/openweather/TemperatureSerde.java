package org.ethelred.heatcontrol3.openweather;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Deserializer;
import jakarta.inject.Singleton;
import org.ethelred.heatcontrol3.Temperature;

import java.io.IOException;

@Singleton
public class TemperatureSerde implements Deserializer<Temperature> {
    @Override
    public @Nullable Temperature deserialize(@NonNull Decoder decoder, DecoderContext context, @NonNull Argument<? super Temperature> type) throws IOException {
        return Temperature.fromFahrenheit(decoder.decodeDouble());
    }
}
