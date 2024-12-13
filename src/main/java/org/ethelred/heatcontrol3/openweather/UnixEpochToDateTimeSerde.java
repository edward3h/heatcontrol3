package org.ethelred.heatcontrol3.openweather;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Deserializer;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Singleton
public class UnixEpochToDateTimeSerde implements Deserializer<LocalDateTime> {
    @Override
    public @Nullable LocalDateTime deserialize(@NonNull Decoder decoder, DecoderContext context, @NonNull Argument<? super LocalDateTime> type) throws IOException {
        var value = decoder.decodeLong();
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(value), ZoneId.systemDefault());
    }
}
