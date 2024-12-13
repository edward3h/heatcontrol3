package org.ethelred.heatcontrol3.sensors;

import io.micronaut.context.annotation.Secondary;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Deserializer;
import org.ethelred.heatcontrol3.Temperature;

import java.io.IOException;

@Secondary
public class ScaledFahrenheitSerde implements Deserializer<Temperature> {
    @Override
    public @Nullable Temperature deserialize(@NonNull Decoder decoder, DecoderContext context, @NonNull Argument<? super Temperature> type) throws IOException {
        return Temperature.fromScaledInt(decoder.decodeInt(), 1, Temperature.Unit.FAHRENHEIT);
    }
}
