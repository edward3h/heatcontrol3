package org.ethelred.heatcontrol3.kumojs;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Deserializer;
import jakarta.inject.Singleton;
import org.ethelred.heatcontrol3.Temperature;
import org.ethelred.heatcontrol3.kumojs.RoomStatusBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Singleton
public class RoomStatusSerde implements Deserializer<RoomStatus> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomStatusSerde.class);
    /*
{
  "r": {
    "indoorUnit": {
      "status": {
        "roomTemp": 19,
        "mode": "heat",
        "spCool": 21.5,
        "spHeat": 20,
        "vaneDir": "auto",
        "fanSpeed": "auto",
        "tempSource": "unset",
        "activeThermistor": "unset",
        "filterDirty": false,
        "hotAdjust": false,
        "defrost": false,
        "standby": false,
        "runTest": 0,
        "humidTest": 0
      }
    }
  }
}

     */
    @Override
    public @Nullable RoomStatus deserialize(@NonNull Decoder decoder, DecoderContext context, @NonNull Argument<? super RoomStatus> type) throws IOException {
        var builder = RoomStatusBuilder.builder();
        try (var r = decoder.decodeObject()) {
            if ("r".equals(r.decodeKey())) {
                r.decodeObject();
                if ("indoorUnit".equals(r.decodeKey())) {
                    r.decodeObject();
                    if ("status".equals(r.decodeKey())) {
                        r.decodeObject();
                        for (String k = r.decodeKey(); k != null; k = r.decodeKey()) {
                            switch (k) {
                                case "roomTemp" -> builder.roomTemp(new Temperature(r.decodeDouble()));
                                case "mode" -> builder.mode(r.decodeString());
                                case "spCool" -> builder.spCool(new Temperature(r.decodeDouble()));
                                case "spHeat" -> builder.spHeat(new Temperature(r.decodeDouble()));
                                default -> r.skipValue();
                            }
                        }
                        r.finishStructure();
                    }
                    r.finishStructure();
                }
                r.finishStructure();
            }
        }
        return builder.build();
    }
}
