package org.ethelred.heatcontrol3.sensors;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Deserializer;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Singleton
public class ParseDateTimeSerde implements Deserializer<OffsetDateTime> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("u-M-d H:m:sZ");
    @Override
    public @Nullable OffsetDateTime deserialize(@NonNull Decoder decoder, DecoderContext context, @NonNull Argument<? super OffsetDateTime> type) throws IOException {
        var value = decoder.decodeString();
        return OffsetDateTime.parse(value, formatter);
    }
}
