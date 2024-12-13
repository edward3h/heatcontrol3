package org.ethelred.heatcontrol3.kumojs;

import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilderFull;
import org.ethelred.heatcontrol3.Temperature;

@RecordBuilderFull
@Serdeable
public
record RoomStatus(Temperature roomTemp, String mode, Temperature spCool, Temperature spHeat) {
    public String setting() {
        if ("heat".equalsIgnoreCase(mode())) {
            return spHeat().display();
        }
        if ("cool".equalsIgnoreCase(mode())) {
            return spCool().display();
        }
        return "--";
    }

    public int sp() {
        if ("heat".equalsIgnoreCase(mode())) {
            return toInt(spHeat);
        }
        if ("cool".equalsIgnoreCase(mode())) {
            return toInt(spCool);
        }
        return toInt(roomTemp);
    }

    private int toInt(Temperature temperature) {
        return (int) Math.round(temperature.fahrenheit());
    }
}
