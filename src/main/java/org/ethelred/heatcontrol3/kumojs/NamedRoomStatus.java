package org.ethelred.heatcontrol3.kumojs;

import io.micronaut.core.annotation.Nullable;
import org.ethelred.heatcontrol3.Temperature;

public record NamedRoomStatus(String name, RoomStatus roomStatus) {
    public String mode() {
        return roomStatus.mode();
    }

    public Temperature spHeat() {
        return roomStatus.spHeat();
    }

    public Temperature roomTemp() {
        return roomStatus.roomTemp();
    }

    public Temperature spCool() {
        return roomStatus.spCool();
    }

    public String setting() {
        return roomStatus.setting();
    }
}
